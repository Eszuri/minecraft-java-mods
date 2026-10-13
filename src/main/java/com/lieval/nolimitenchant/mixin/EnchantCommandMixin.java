package com.lieval.nolimitenchant.mixin;

import com.lieval.nolimitenchant.NoLimitEnchantMod;
import net.minecraft.server.commands.EnchantCommand;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EnchantCommand.class)
public abstract class EnchantCommandMixin {

    /**
     * Mengizinkan perintah /enchant memberikan level melampaui maxLevel vanilla
     * hingga batas maxEnchantmentLevel yang diizinkan oleh mod.
     */
    @Redirect(
        method = "enchant",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/item/enchantment/Enchantment;getMaxLevel()I"
        )
    )
    private static int noLimitEnchant_redirectCommandMaxLevel(Enchantment enchantment) {
        if (NoLimitEnchantMod.CONFIG != null && NoLimitEnchantMod.CONFIG.allowCommandOverlevel) {
            return NoLimitEnchantMod.CONFIG.maxEnchantmentLevel;
        }
        return enchantment.getMaxLevel();
    }
}
