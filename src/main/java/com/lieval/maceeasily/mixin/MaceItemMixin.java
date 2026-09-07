package com.lieval.maceeasily.mixin;

import com.lieval.maceeasily.MaceEasilyMod;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MaceItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MaceItem.class)
public abstract class MaceItemMixin {

    /**
     * Memodifikasi pemicu smash attack berdasarkan opsi smashTrigger:
     * - 1.5 Blocks (Default): fallDistance > 1.5 && !isFallFlying
     * - 1.0 Block: fallDistance >= 1.0 && !isFallFlying
     * - 0 Blocks: bahkan dalam kondisi diam / 0 blok
     */
    @Inject(method = "canSmashAttack", at = @At("HEAD"), cancellable = true)
    private static void maceEasily_canSmashAttack(LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
        if (entity == null) return;

        com.lieval.maceeasily.config.TriggerDistance mode = com.lieval.maceeasily.MaceEasilyMod.CONFIG != null
                ? com.lieval.maceeasily.MaceEasilyMod.CONFIG.smashTrigger
                : com.lieval.maceeasily.config.TriggerDistance.BLOCKS_1_5;

        if (mode == com.lieval.maceeasily.config.TriggerDistance.BLOCKS_0) {
            // 0 blok: bisa smash attack kapan saja selama tidak di dalam air dan tidak memanjat tangga
            if (!entity.isInWater() && !entity.onClimbable()) {
                cir.setReturnValue(true);
            }
        } else if (mode == com.lieval.maceeasily.config.TriggerDistance.BLOCKS_1_0) {
            // 1.0 blok: minimal fall distance 1.0 blok
            if (entity.fallDistance >= 1.0 && !entity.isFallFlying()) {
                cir.setReturnValue(true);
            } else {
                cir.setReturnValue(false);
            }
        }
        // Mode BLOCKS_1_5: biarkan vanilla mengeksekusi fallDistance > 1.5 && !isFallFlying
    }

    /**
     * Menjamin nilai bonus damage smash minimal +4.0 (2 hearts ekstra)
     * saat smash attack terpicu dari lompatan rendah.
     */
    @Inject(method = "getAttackDamageBonus", at = @At("RETURN"), cancellable = true)
    private void maceEasily_minimumSmashDamage(Entity target, float baseDamage, DamageSource damageSource, CallbackInfoReturnable<Float> cir) {
        if (damageSource != null && damageSource.getDirectEntity() instanceof LivingEntity attacker) {
            if (MaceItem.canSmashAttack(attacker)) {
                float bonus = cir.getReturnValue();
                if (bonus < com.lieval.maceeasily.config.MaceEasilyConfig.MIN_SMASH_BONUS_DAMAGE) {
                    cir.setReturnValue(com.lieval.maceeasily.config.MaceEasilyConfig.MIN_SMASH_BONUS_DAMAGE);
                }
            }
        }
    }

    @Inject(method = "hurtEnemy", at = @At("HEAD"))
    private void maceEasily_onHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker, CallbackInfo ci) {
        if (attacker != null) {
            attacker.resetFallDistance();
            if (MaceEasilyMod.hasWindBurst(stack)) {
                MaceEasilyMod.onWindBurstTriggered(attacker);
            }
        }
    }

    @Inject(method = "postHurtEnemy", at = @At("HEAD"))
    private void maceEasily_onPostHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker, CallbackInfo ci) {
        if (attacker != null) {
            attacker.resetFallDistance();
            if (MaceEasilyMod.hasWindBurst(stack)) {
                MaceEasilyMod.onWindBurstTriggered(attacker);
            }
        }
    }
}
