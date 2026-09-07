package com.lieval.maceeasily.mixin;

import com.lieval.maceeasily.MaceEasilyMod;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemCooldowns.class)
public abstract class ItemCooldownsMixin {

    /**
     * Mencegah item mendapatkan cooldown saat digunakan (Ender Pearl, Wind Charge, Chorus Fruit, Shield, dll.)
     */
    @Inject(method = "addCooldown(Lnet/minecraft/world/item/ItemStack;I)V", at = @At("HEAD"), cancellable = true)
    private void maceEasily_preventCooldownStack(ItemStack stack, int duration, CallbackInfo ci) {
        if (MaceEasilyMod.CONFIG == null || MaceEasilyMod.CONFIG.noCooldown) {
            ci.cancel();
        }
    }

    @Inject(method = "addCooldown(Lnet/minecraft/resources/Identifier;I)V", at = @At("HEAD"), cancellable = true)
    private void maceEasily_preventCooldownIdentifier(Identifier group, int duration, CallbackInfo ci) {
        if (MaceEasilyMod.CONFIG == null || MaceEasilyMod.CONFIG.noCooldown) {
            ci.cancel();
        }
    }

    /**
     * Memastikan status cooldown selalu false
     */
    @Inject(method = "isOnCooldown", at = @At("HEAD"), cancellable = true)
    private void maceEasily_isOnCooldown(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (MaceEasilyMod.CONFIG == null || MaceEasilyMod.CONFIG.noCooldown) {
            cir.setReturnValue(false);
        }
    }

    /**
     * Memastikan persentase cooldown selalu 0.0 (tidak ada animasi abu-abu/cooldown pada slot item)
     */
    @Inject(method = "getCooldownPercent", at = @At("HEAD"), cancellable = true)
    private void maceEasily_getCooldownPercent(ItemStack stack, float partialTick, CallbackInfoReturnable<Float> cir) {
        if (MaceEasilyMod.CONFIG == null || MaceEasilyMod.CONFIG.noCooldown) {
            cir.setReturnValue(0.0F);
        }
    }
}
