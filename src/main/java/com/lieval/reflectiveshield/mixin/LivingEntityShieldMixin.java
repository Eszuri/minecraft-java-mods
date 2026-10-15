package com.lieval.reflectiveshield.mixin;

import java.util.Optional;
import net.minecraft.core.HolderSet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.component.BlocksAttacks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityShieldMixin {

    @Shadow
    protected abstract void blockUsingItem(ServerLevel level, LivingEntity attacker, DamageSource damageSource, float amount, boolean hasBlocked);

    @Redirect(
        method = "applyItemBlocking",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/item/component/BlocksAttacks;bypassedBy()Ljava/util/Optional;"
        )
    )
    private Optional<HolderSet<DamageType>> reflectiveShield_filterBypassedBy(
        BlocksAttacks instance,
        ServerLevel level,
        DamageSource damageSource,
        float amount
    ) {
        if ((Object) this instanceof Player) {
            if ((damageSource.getEntity() != null || damageSource.getDirectEntity() != null)
                    && !damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
                return Optional.empty();
            }
        }
        return instance.bypassedBy();
    }

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

            if ((Object) this instanceof Player && !damageSource.is(DamageTypeTags.IS_PROJECTILE)) {
                if (!(damageSource.getDirectEntity() instanceof LivingEntity) && damageSource.getEntity() instanceof LivingEntity attacker) {
                    this.blockUsingItem(level, attacker, damageSource, amount, cir.getReturnValue() >= amount);
                }
            }
        }
    }

    @Redirect(
        method = "hurtServer",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/item/component/BlocksAttacks;onBlocked(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/LivingEntity;)V"
        )
    )
    private void reflectiveShield_suppressDuplicateVanillaOnBlocked(BlocksAttacks instance, ServerLevel level, LivingEntity entity) {
    }

    @Inject(method = "blockUsingItem", at = @At("HEAD"))
    private void reflectiveShield_onBlockUsingItem(ServerLevel level, LivingEntity attacker, DamageSource damageSource, float amount, boolean hasBlocked, CallbackInfo ci) {
        if (damageSource.is(DamageTypes.THORNS)) {
            return;
        }

        LivingEntity defender = (LivingEntity) (Object) this;

        if (attacker != null && attacker.isAlive() && amount > 0.0f) {
            DamageSource counterSource = level.damageSources().thorns(defender);
            attacker.hurtServer(level, counterSource, amount);

            double dx = attacker.getX() - defender.getX();
            double dz = attacker.getZ() - defender.getZ();
            attacker.knockback(0.5D, dx, dz, counterSource, amount);
        }
    }
}
