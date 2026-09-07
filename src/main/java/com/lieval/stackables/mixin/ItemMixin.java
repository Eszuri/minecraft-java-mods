package com.lieval.stackables.mixin;

import com.lieval.stackables.StackableItemsMod;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class ItemMixin {
    @Inject(method = "getDefaultMaxStackSize", at = @At("HEAD"), cancellable = true)
    private void stackable_getDefaultMaxStackSize(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(StackableItemsMod.getCustomStackSize());
    }
}
