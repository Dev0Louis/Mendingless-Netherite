package de.louis.mendinglessnetherite.mixin;

import de.louis.mendinglessnetherite.MendinglessNetherite;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.ForgingScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.Map;

import static de.louis.mendinglessnetherite.MendinglessNetherite.isNetherite;

@Debug(export = true)
@Mixin(AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin extends ForgingScreenHandler {
    public AnvilScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(type, syncId, playerInventory, context);
    }
    @ModifyArg(
            method = "updateResult",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;set(Ljava/util/Map;Lnet/minecraft/item/ItemStack;)V"),
            index = 0
    )
    public Map removeMendingFromNetheriteInAnvil(Map value) {
        if(isNetherite(this.input.getStack(0)) || isNetherite(this.input.getStack(1))) {
            //System.out.println(value);
            value.remove(Enchantments.MENDING);
        }
        return value;
    }
}
