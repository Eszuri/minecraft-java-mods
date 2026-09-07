package com.lieval.maceeasily.mixin;

import com.lieval.maceeasily.MaceEasilyMod;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.TargetedConditionalEffect;
import net.minecraft.world.item.enchantment.effects.ExplodeEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Enchantment.class)
public abstract class EnchantmentMixin {

    /**
     * Mengontrol pemicu ledakan Wind Burst (ExplodeEffect) berdasarkan opsi windBurstTrigger:
     * - 1.5 Blocks (Default): fallDistance > 1.5 && !isFallFlying
     * - 1.0 Block: fallDistance >= 1.0 && !isFallFlying
     * - 0 Blocks: bahkan dalam posisi diam
     */
    @Inject(method = "doPostAttack(Lnet/minecraft/world/item/enchantment/TargetedConditionalEffect;Lnet/minecraft/server/level/ServerLevel;ILnet/minecraft/world/item/enchantment/EnchantedItemInUse;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/damagesource/DamageSource;)V", at = @At("HEAD"), cancellable = true)
    private static void maceEasily_controlWindBurstTrigger(TargetedConditionalEffect<?> effect, ServerLevel serverLevel, int enchantmentLevel, EnchantedItemInUse item, Entity victim, DamageSource damageSource, CallbackInfo ci) {
        if (effect != null && effect.effect() instanceof ExplodeEffect) {
            Entity attacker = item != null ? item.owner() : null;
            if (attacker == null && damageSource != null) {
                attacker = damageSource.getDirectEntity();
                if (attacker == null) {
                    attacker = damageSource.getEntity();
                }
            }
            if (attacker instanceof LivingEntity living) {
                if (!MaceEasilyMod.shouldTriggerWindBurst(living)) {
                    ci.cancel();
                } else {
                    MaceEasilyMod.onWindBurstTriggered(living);
                }
            }
        }
    }
}
