package com.lieval.reflectiveshield.mixin;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Projectile.class)
public abstract class ProjectileDeflectMixin {

    @Shadow
    public abstract void setOwner(Entity entity);

    @Shadow
    public abstract Entity getOwner();

    /**
     * Intercepts projectile collisions. When a projectile hits a blocking entity from ANY angle
     * (360-degree full body shield protection), it plays the authentic vanilla shield impact sound
     * and deflects the projectile directly back to the shooter.
     */
    @Inject(method = "onHit", at = @At("HEAD"), cancellable = true)
    private void reflectiveShield_onProjectileHit(HitResult hitResult, CallbackInfo ci) {
        if (hitResult.getType() != HitResult.Type.ENTITY) {
            return;
        }

        Projectile projectile = (Projectile) (Object) this;
        Level level = projectile.level();

        if (level.isClientSide()) {
            return;
        }

        EntityHitResult entityHit = (EntityHitResult) hitResult;
        Entity hitEntity = entityHit.getEntity();

        if (!(hitEntity instanceof LivingEntity defender)) {
            return;
        }

        if (!defender.isBlocking()) {
            return;
        }

        // Shield durability damage (standard shield behavior)
        ItemStack shield = defender.getItemBlockingWith();
        if (shield != null && !shield.isEmpty()) {
            shield.hurtAndBreak(1, defender, defender.getUsedItemHand());
        }

        // Play authentic vanilla shield impact sound
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

        // Deflected speed equals incoming speed (1:1 ratio)
        double incomingSpeed = projectile.getDeltaMovement().length();
        double speed = Math.max(incomingSpeed, 0.8);

        Entity shooter = this.getOwner();
        Vec3 projPos = projectile.position();
        Vec3 newVelocity;

        if (shooter != null && shooter.isAlive()) {
            // Target shooter's eye/chest position directly
            Vec3 targetPos = shooter.getEyePosition().subtract(0, 0.15, 0);
            Vec3 dir = targetPos.subtract(projPos).normalize();
            newVelocity = dir.scale(speed);
        } else {
            // If shooter is null or dead, bounce directly backwards along incoming trajectory
            newVelocity = projectile.getDeltaMovement().scale(-1.0);
            if (newVelocity.lengthSqr() < 1e-4) {
                newVelocity = defender.getLookAngle().normalize().scale(speed);
            }
        }

        // Slightly step projectile away from defender to avoid re-collision
        projectile.setPos(
                projectile.getX() + newVelocity.x * 0.1,
                projectile.getY() + newVelocity.y * 0.1,
                projectile.getZ() + newVelocity.z * 0.1
        );

        projectile.setDeltaMovement(newVelocity);
        this.setOwner(defender);
        projectile.needsSync = true;

        if (projectile instanceof AbstractArrow arrow) {
            arrow.setCritArrow(true);
        }

        // Cancel onHit so projectile does not damage the defender or discard itself
        ci.cancel();
    }
}
