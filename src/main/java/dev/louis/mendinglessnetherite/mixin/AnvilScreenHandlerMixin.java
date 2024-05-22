package dev.louis.mendinglessnetherite.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.ForgingScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.HashSet;
import java.util.Set;

import static dev.louis.mendinglessnetherite.MendinglessNetherite.isNetherite;

@Mixin(AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin extends ForgingScreenHandler {
    public AnvilScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(type, syncId, playerInventory, context);
    }
    @WrapOperation(
            method = "updateResult",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/component/type/ItemEnchantmentsComponent;getEnchantmentsMap()Ljava/util/Set;")
    )
    public Set<Object2IntMap.Entry<RegistryEntry<Enchantment>>> removeMendingFromNetheriteInAnvil(ItemEnchantmentsComponent instance, Operation<Set<Object2IntMap.Entry<RegistryEntry<Enchantment>>>> original) {
        Set<Object2IntMap.Entry<RegistryEntry<Enchantment>>> set = new HashSet<>(original.call(instance));

        if(isNetherite(this.input.getStack(0)) || isNetherite(this.input.getStack(1))) {
            set.removeIf(registryEntryEntry -> registryEntryEntry.getKey().equals(Enchantments.MENDING.getRegistryEntry()));
        }

        return set;
    }
}
