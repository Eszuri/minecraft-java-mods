package com.lieval.stackables.mixin;

import com.lieval.stackables.StackableItemsMod;
import com.lieval.stackables.util.StackFallbackHelper;
import net.minecraft.world.item.ItemInstance;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements ItemInstance {
    @Override
    public int getMaxStackSize() {
        return StackableItemsMod.getCustomStackSize();
    }

    @ModifyConstant(
        method = "lambda$static$1",
        constant = @Constant(intValue = 99),
        require = 0
    )
    private static int stackable_modifyItemStackMaxCount(int original) {
        return Integer.MAX_VALUE;
    }

    @Inject(method = "lambda$static$0", at = @At("RETURN"), cancellable = true, require = 0)
    private static void stackable_wrapMapCodec(com.mojang.serialization.Codec<ItemStack> recursiveCodec, CallbackInfoReturnable<com.mojang.serialization.MapCodec<ItemStack>> cir) {
        cir.setReturnValue(cir.getReturnValue().xmap(StackFallbackHelper::decodeSafeStack, StackFallbackHelper::encodeSafeStack));
    }

    @Inject(method = "validateStrict", at = @At("HEAD"), cancellable = true)
    private static void stackable_validateStrict(ItemStack itemStack, CallbackInfoReturnable<com.mojang.serialization.DataResult<ItemStack>> cir) {
        cir.setReturnValue(com.mojang.serialization.DataResult.success(itemStack));
    }

    @Inject(method = "isStackable", at = @At("HEAD"), cancellable = true)
    private void stackable_isStackable(CallbackInfoReturnable<Boolean> cir) {
        if (this.getMaxStackSize() > 1) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "getTooltipLines", at = @At("RETURN"))
    private void stackable_addTooltip(net.minecraft.world.item.Item.TooltipContext context, net.minecraft.world.entity.player.Player player, net.minecraft.world.item.TooltipFlag tooltipFlag, CallbackInfoReturnable<java.util.List<net.minecraft.network.chat.Component>> cir) {
        ItemStack self = (ItemStack) (Object) this;
        if (self.getCount() > 999 && cir.getReturnValue() != null) {
            try {
                cir.getReturnValue().add(net.minecraft.network.chat.Component.literal("§7Amount: §e" + String.format(java.util.Locale.US, "%,d", self.getCount())));
            } catch (UnsupportedOperationException ignored) {
            }
        }
    }

    @Inject(method = "applyDamage", at = @At("TAIL"))
    private void stackable_resetDurabilityOnBreak(int amount, net.minecraft.server.level.ServerPlayer player, java.util.function.Consumer<net.minecraft.world.item.Item> onBreak, org.spongepowered.asm.mixin.injection.callback.CallbackInfo ci) {
        ItemStack self = (ItemStack) (Object) this;
        if (self.isBroken() && !self.isEmpty()) {
            self.setDamageValue(0);
        }
    }
}
