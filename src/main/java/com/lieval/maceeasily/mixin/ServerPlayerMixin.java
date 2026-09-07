package com.lieval.maceeasily.mixin;

import com.lieval.maceeasily.MaceEasilyMod;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin {

    /**
     * Mencegah penerimaan fall damage pada ServerPlayer saat dalam perlindungan Wind Burst.
     * Lapisan pertahanan kedua jika causeFallDamage terlewati.
     */
    @Inject(method = "hurtServer", at = @At("HEAD"), cancellable = true)
    private void maceEasily_preventWindBurstFallHurt(ServerLevel level, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        ServerPlayer player = (ServerPlayer) (Object) this;
        if (source.is(DamageTypes.FALL) || source.is(DamageTypeTags.IS_FALL)) {
            if (MaceEasilyMod.isWindBurstProtected(player.getUUID())) {
                player.resetFallDistance();
                MaceEasilyMod.onPlayerFallDamageChecked(player, amount);
                cir.setReturnValue(false);
            }
        }
    }
}
