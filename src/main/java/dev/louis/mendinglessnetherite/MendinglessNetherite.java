package dev.louis.mendinglessnetherite;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.argument.RegistryEntryArgumentType;
import net.minecraft.command.argument.RegistryEntryReferenceArgumentType;
import net.minecraft.component.ComponentChanges;
import net.minecraft.component.DataComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.command.CommandManager;
import net.minecraft.util.Identifier;

import static net.minecraft.server.command.CommandManager.argument;

public class MendinglessNetherite implements ModInitializer {

    public static final TagKey<Item> NETHERITE = tagOf("netherite");


    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(
                    CommandManager.literal("removeEnchantment")
                            .requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(4))
                            .then(argument("enchantment", RegistryEntryReferenceArgumentType.registryEntry(registryAccess, RegistryKeys.ENCHANTMENT))
                            .executes(context -> {
                                var enchantment = RegistryEntryReferenceArgumentType.getEnchantment(context, "enchantment");
                                remove(context.getSource().getPlayer().getMainHandStack(), enchantment);
                                return 1;
                            }))
            );
        });
    }

    public static void remove(ItemStack itemStack, RegistryEntry<Enchantment> enchantment) {
        var component = itemStack.getComponents().get(DataComponentTypes.ENCHANTMENTS);
        if (component == null) return;
        ItemEnchantmentsComponent.Builder builder = new ItemEnchantmentsComponent.Builder(component);
        builder.remove(enchantmentRegistryEntry -> enchantmentRegistryEntry.equals(enchantment));
        itemStack.applyChanges(ComponentChanges.builder().add(DataComponentTypes.ENCHANTMENTS, builder.build()).build());
    }

    public static boolean isNetherite(ItemStack stack) {
        return stack.isIn(NETHERITE);
    }
    private static TagKey<Item> tagOf(String id) {
        return TagKey.of(RegistryKeys.ITEM, new Identifier("mendinglessnetherite", id));
    }
}
