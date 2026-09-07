package com.lieval.maceeasily.mixin;

import com.lieval.maceeasily.MaceEasilyMod;
import com.lieval.maceeasily.config.TriggerDistance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerCriticalMixin {

    /**
     * Memungkinkan serangan critical untuk semua item/senjata tanpa harus melompat,
     * bahkan saat pemain berdiri diam di atas tanah.
     * 
     * Khusus senjata Mace pada opsi smashTrigger BLOCKS_0 (0 blok):
     * Fitur easily critical dinonaktifkan khusus untuk Mace agar serangannya selalu menjadi Smash Attack!
     */
    @Inject(method = "canCriticalAttack", at = @At("HEAD"), cancellable = true)
    private void maceEasily_canCriticalAttack(Entity target, CallbackInfoReturnable<Boolean> cir) {
        Player player = (Player) (Object) this;

        // Periksa apakah pemain memegang Mace di tangan utama
        boolean holdingMace = player.getMainHandItem().is(Items.MACE);

        // Jika smash trigger BLOCKS_0 dan pemain memegang Mace:
        // Matikan easily critical KHUSUS untuk Mace, digantikan dengan Smash Attack
        if (holdingMace && MaceEasilyMod.CONFIG != null && MaceEasilyMod.CONFIG.smashTrigger == TriggerDistance.BLOCKS_0) {
            cir.setReturnValue(false);
            return;
        }

        if (MaceEasilyMod.CONFIG == null || MaceEasilyMod.CONFIG.easilyCriticalDamage) {
            if (target instanceof LivingEntity && !player.isPassenger()) {
                cir.setReturnValue(true);
            }
        }
    }

    /**
     * Menghilangkan cooldown ayunan senjata (Attack strength selalu 100% / 1.0F).
     * Memungkinkan serangan bertubi-tubi dengan damage penuh seketika saat noCooldown aktif.
     */
    @Inject(method = "getAttackStrengthScale", at = @At("HEAD"), cancellable = true)
    private void maceEasily_noAttackCooldown(float partialTick, CallbackInfoReturnable<Float> cir) {
        if (MaceEasilyMod.CONFIG == null || MaceEasilyMod.CONFIG.noCooldown) {
            cir.setReturnValue(1.0F);
        }
    }

    /**
     * Meniadakan fall damage khusus ketika pemain terlontar ke udara oleh enchantment Wind Burst (sedang terbang dengan enchant nya)
     * jika opsi fallDamageMace aktif.
     */
    @Inject(method = "causeFallDamage", at = @At("HEAD"), cancellable = true)
    private void maceEasily_negateWindBurstFallDamage(double fallDistance, float damageMultiplier, DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        Player player = (Player) (Object) this;
        if (MaceEasilyMod.isWindBurstProtected(player.getUUID())) {
            player.resetFallDistance();
            MaceEasilyMod.onPlayerFallDamageChecked(player, fallDistance);
            cir.setReturnValue(false);
        }
    }
}
