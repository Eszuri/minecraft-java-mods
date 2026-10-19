package com.lieval.reflectiveshield.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BlocksAttacks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Player.class)
public abstract class PlayerShieldMixin {

    @Redirect(
        method = "blockUsingItem",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/LivingEntity;getSecondsToDisableBlocking()F"
        )
    )
    private float reflectiveShield_preventAxeDisable(LivingEntity attacker) {
        return 0.0f;
    }

    @Redirect(
        method = "blockUsingItem",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/item/component/BlocksAttacks;disable(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/LivingEntity;FLnet/minecraft/world/item/ItemStack;)V"
        )
    )
    private void reflectiveShield_cancelDisable(BlocksAttacks instance, ServerLevel level, LivingEntity entity, float seconds, ItemStack stack) {
        // Prevent shield from ever being disabled on the player
    }
}
