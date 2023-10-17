package dev.louis.mendinglessnetherite;

import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class MendinglessNetherite implements ModInitializer {

    public static final TagKey<Item> NETHERITE = tagOf("netherite");


    @Override
    public void onInitialize() {
    }

    public static boolean isNetherite(ItemStack stack) {
        return stack.isIn(NETHERITE);
    }
    private static TagKey<Item> tagOf(String id) {
        return TagKey.of(RegistryKeys.ITEM, new Identifier("mendinglessnetherite", id));
    }
}
