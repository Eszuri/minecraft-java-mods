package com.lieval.nolimitenchant.client.mixin;

import net.minecraft.client.gui.screens.inventory.AnvilScreen;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AnvilScreen.class)
public abstract class AnvilScreenMixin {

    /**
     * Mencegah tampilan teks merah "Too Expensive!" pada GUI Anvil.
     * Mengarahkan rendering agar selalu menampilkan jumlah level yang dibutuhkan
     * (berwarna hijau jika level pemain cukup, atau merah jika level kurang).
     */
    @Redirect(
        method = "extractLabels",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/player/LocalPlayer;hasInfiniteMaterials()Z"
        )
    )
    private boolean noLimitEnchant_bypassTooExpensiveText(LocalPlayer player) {
        return true;
    }
}
