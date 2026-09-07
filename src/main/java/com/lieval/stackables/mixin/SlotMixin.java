package com.lieval.stackables.mixin;

import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Slot.class)
public abstract class SlotMixin {
    @Inject(method = "getMaxStackSize()I", at = @At("HEAD"), cancellable = true)
    private void stackable_getMaxStackSize(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(Integer.MAX_VALUE);
    }

    @Inject(method = "getMaxStackSize(Lnet/minecraft/world/item/ItemStack;)I", at = @At("HEAD"), cancellable = true)
    private void stackable_getMaxStackSizeForStack(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(stack.getMaxStackSize());
    }
}
