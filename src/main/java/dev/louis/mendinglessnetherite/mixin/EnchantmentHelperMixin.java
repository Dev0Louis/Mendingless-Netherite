package dev.louis.mendinglessnetherite.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

import static dev.louis.mendinglessnetherite.MendinglessNetherite.isNetherite;

@Debug(export = true)
@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {


    @Inject(
            method = "getPossibleEntries",
            at = @At("RETURN"), locals = LocalCapture.CAPTURE_FAILEXCEPTION
    )
    private static void removeMendingForEnchanter(FeatureSet enabledFeatures, int level, ItemStack stack, boolean treasureAllowed, CallbackInfoReturnable<List<EnchantmentLevelEntry>> cir, List<EnchantmentLevelEntry> list) {
        if(isNetherite(stack)) {
            list.removeIf(enchantmentLevelEntry -> enchantmentLevelEntry.enchantment == Enchantments.MENDING);
        }
    }

    @Inject(
            method = "generateEnchantments",
            at = @At("RETURN")
    )
    private static void removeMendingForEnchanter(FeatureSet enabledFeatures, Random random, ItemStack stack, int level, boolean treasureAllowed, CallbackInfoReturnable<List<EnchantmentLevelEntry>> cir) {
        if(isNetherite(stack)) {
            cir.getReturnValue().removeIf(enchantmentLevelEntry -> enchantmentLevelEntry.enchantment == Enchantments.MENDING);
        }
    }

}
