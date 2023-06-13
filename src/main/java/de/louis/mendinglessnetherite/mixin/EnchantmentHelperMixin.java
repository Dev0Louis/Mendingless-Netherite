package de.louis.mendinglessnetherite.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

import static de.louis.mendinglessnetherite.MendinglessNetherite.isNetherite;

@Debug(export = true)
@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {


    @Inject(
            method = "getPossibleEntries",
            at = @At("RETURN"), locals = LocalCapture.CAPTURE_FAILEXCEPTION
    )
    private static void removeMendingForEnchanter(int power, ItemStack stack, boolean treasureAllowed, CallbackInfoReturnable<List<EnchantmentLevelEntry>> cir, List list) {
        if(isNetherite(stack)) {
            list.remove(Enchantments.MENDING);
        }
    }

}
