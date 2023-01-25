package de.louis.mendinglessnetherite.mixin;

import de.louis.mendinglessnetherite.MendinglessNetherite;
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

@Debug(export = true)
@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {


    @Inject(
            method = "getPossibleEntries",
            at = @At("RETURN"), locals = LocalCapture.CAPTURE_FAILEXCEPTION
    )
    private static void changeList(int power, ItemStack stack, boolean treasureAllowed, CallbackInfoReturnable<List<EnchantmentLevelEntry>> cir, List list) {
        if(MendinglessNetherite.isNetherite(stack.getItem())) {
            list.remove(Enchantments.MENDING);
        }
    }

}
