package de.louis.mendinglessnetherite;

import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.EnchantmentScreenHandler;
import org.spongepowered.asm.mixin.Unique;

import java.util.ArrayList;
import java.util.List;

public class MendinglessNetherite implements ModInitializer {

    static List<Item> netheriteItems = new ArrayList<>();


    @Override
    public void onInitialize() {
        netheriteItems.add(Items.NETHERITE_SWORD);
        netheriteItems.add(Items.NETHERITE_HOE);
        netheriteItems.add(Items.NETHERITE_AXE);
        netheriteItems.add(Items.NETHERITE_PICKAXE);
        netheriteItems.add(Items.NETHERITE_SHOVEL);
        netheriteItems.add(Items.NETHERITE_SCRAP);
        netheriteItems.add(Items.NETHERITE_HELMET);
        netheriteItems.add(Items.NETHERITE_CHESTPLATE);
        netheriteItems.add(Items.NETHERITE_LEGGINGS);
        netheriteItems.add(Items.NETHERITE_BOOTS);
        netheriteItems.add(Items.NETHERITE_INGOT);
        netheriteItems.add(Items.NETHERITE_BLOCK);
        netheriteItems.add(Items.ANCIENT_DEBRIS);
    }

    public static boolean isNetherite(ItemStack stack) {
        return isNetherite(stack.getItem());
    }

    public static boolean isNetherite(Item item) {
        return netheriteItems.contains(item);
    }
}
