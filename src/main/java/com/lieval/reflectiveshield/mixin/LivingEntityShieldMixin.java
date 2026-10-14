package com.lieval.reflectiveshield.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.component.BlocksAttacks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityShieldMixin {

    /**
     * Expands shield coverage to 360 degrees (covering the player's entire body from all sides),
     * by setting the attack angle to 0 radians during damage reduction resolution.
     */
    @ModifyArg(
        method = "applyItemBlocking",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/item/component/BlocksAttacks;resolveBlockedDamage(Lnet/minecraft/world/damagesource/DamageSource;FD)F"
        ),
        index = 2
    )
    private double reflectiveShield_360ShieldAngle(double angle) {
        if ((Object) this instanceof Player) {
            return 0.0D;
        }
        return angle;
    }

    /**
     * Reliably produces the authentic vanilla shield block sound on ANY blocked attack
     * (including rapid consecutive hits, zero residual damage hits, explosions, and 360-degree hits).
     */
    @Inject(method = "applyItemBlocking", at = @At("RETURN"))
    private void reflectiveShield_onApplyItemBlockingSound(ServerLevel level, DamageSource damageSource, float amount, CallbackInfoReturnable<Float> cir) {
        if (cir.getReturnValue() > 0.0F) {
            LivingEntity defender = (LivingEntity) (Object) this;
            level.playSound(
                null,
                defender.getX(),
                defender.getY(),
                defender.getZ(),
                SoundEvents.SHIELD_BLOCK.value(),
                defender.getSoundSource(),
                1.0F,
                0.8F + defender.getRandom().nextFloat() * 0.4F
            );
        }
    }

    /**
     * Suppresses vanilla's conditional onBlocked sound in hurtServer to prevent duplicate sounds.
     */
    @Redirect(
        method = "hurtServer",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/item/component/BlocksAttacks;onBlocked(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/LivingEntity;)V"
        )
    )
    private void reflectiveShield_suppressDuplicateVanillaOnBlocked(BlocksAttacks instance, ServerLevel level, LivingEntity entity) {
        // Handled reliably by reflectiveShield_onApplyItemBlockingSound on every blocked hit
    }

    /**
     * Intercepts successful melee shield blocks and reflects 100% of damage back to the attacker,
     * applying standard player-equivalent knockback from any direction.
     */
    @Inject(method = "blockUsingItem", at = @At("HEAD"))
    private void reflectiveShield_onBlockUsingItem(ServerLevel level, LivingEntity attacker, DamageSource damageSource, float amount, boolean hasBlocked, CallbackInfo ci) {
        // Prevent counter looping if damage is already reflected / thorns
        if (damageSource.is(DamageTypes.THORNS)) {
            return;
        }

        LivingEntity defender = (LivingEntity) (Object) this;

        if (attacker != null && attacker.isAlive() && amount > 0.0f) {
            // Reflected damage equals incoming damage (1:1)
            DamageSource counterSource = level.damageSources().thorns(defender);
            attacker.hurtServer(level, counterSource, amount);

            // Knockback equivalent to standard player knockback (0.5D), pushing attacker away from defender
            double dx = attacker.getX() - defender.getX();
            double dz = attacker.getZ() - defender.getZ();
            attacker.knockback(0.5D, dx, dz, counterSource, amount);
        }
    }
}
