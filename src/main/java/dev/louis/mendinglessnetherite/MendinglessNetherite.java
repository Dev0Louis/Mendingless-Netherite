package dev.louis.mendinglessnetherite;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import dev.louis.mendinglessnetherite.mixin.EnchantmentHelperMixin;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.command.argument.RegistryEntryArgumentType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.command.CommandManager;
import net.minecraft.util.Identifier;

import static com.mojang.brigadier.arguments.IntegerArgumentType.integer;
import static net.minecraft.item.ItemStack.ENCHANTMENTS_KEY;
import static net.minecraft.server.command.CommandManager.argument;

public class MendinglessNetherite implements ModInitializer {

    public static final TagKey<Item> NETHERITE = tagOf("netherite");


    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(
                    CommandManager.literal("removeEnchantment")
                            .requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(4))
                            .then(argument("enchantments", RegistryEntryArgumentType.registryEntry(registryAccess, RegistryKeys.ENCHANTMENT))
                            .executes(context -> {
                                var enchantment = RegistryEntryArgumentType.getEnchantment(context, "enchantments").value();
                                remove(context.getSource().getPlayer().getMainHandStack(), enchantment);
                                return 1;
                            }))
            );
        });
    }

    public void remove(ItemStack itemStack, Enchantment enchantment) {
        var nbt = itemStack.getOrCreateNbt();
        if (!nbt.contains(ENCHANTMENTS_KEY, NbtElement.LIST_TYPE)) {
            nbt.put(ENCHANTMENTS_KEY, new NbtList());
        }
        NbtList nbtList = nbt.getList(ENCHANTMENTS_KEY, NbtElement.COMPOUND_TYPE);
        nbtList.removeIf(nbtElement -> ((NbtCompound)nbtElement).getString("id").equals(EnchantmentHelper.getEnchantmentId(enchantment).toString()));
    }

    public static boolean isNetherite(ItemStack stack) {
        return stack.isIn(NETHERITE);
    }
    private static TagKey<Item> tagOf(String id) {
        return TagKey.of(RegistryKeys.ITEM, new Identifier("mendinglessnetherite", id));
    }
}
