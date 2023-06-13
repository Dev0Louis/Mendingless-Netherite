package de.louis.mendinglessnetherite.mixin;

import de.louis.mendinglessnetherite.MendinglessNetherite;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ForgingScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.SmithingScreenHandler;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import static de.louis.mendinglessnetherite.MendinglessNetherite.isNetherite;

@Debug(export = true)
@Mixin(SmithingScreenHandler.class)
public abstract class SmithingScreenHandlerMixin extends ForgingScreenHandler {
    public SmithingScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(type, syncId, playerInventory, context);
    }
    @ModifyVariable(
            method = "updateResult",
            at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/recipe/SmithingRecipe;craft(Lnet/minecraft/inventory/Inventory;Lnet/minecraft/registry/DynamicRegistryManager;)Lnet/minecraft/item/ItemStack;"),
            index = 3
    )
    public ItemStack removeMendingFromNetherite(ItemStack value) {
        if(isNetherite(this.input.getStack(0)) || isNetherite(this.input.getStack(1))) {
            var a = EnchantmentHelper.get(value);
            a.remove(Enchantments.MENDING);
            EnchantmentHelper.set(a, value);
        }
        return value;
    }
}
