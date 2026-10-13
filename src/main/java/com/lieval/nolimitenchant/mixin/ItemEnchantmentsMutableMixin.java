package com.lieval.nolimitenchant.mixin;

import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ItemEnchantments.Mutable.class)
public abstract class ItemEnchantmentsMutableMixin {

    /**
     * Mengizinkan method set() menetapkan level di atas 255 tanpa di-clamp Math.min(level, 255).
     */
    @ModifyConstant(method = "set", constant = @Constant(intValue = 255))
    private int noLimitEnchant_allowLargeLevelsInSet(int constant) {
        return Integer.MAX_VALUE;
    }

    /**
     * Mengizinkan method upgrade() menetapkan level di atas 255 tanpa di-clamp Math.min(level, 255).
     */
    @ModifyConstant(method = "upgrade", constant = @Constant(intValue = 255))
    private int noLimitEnchant_allowLargeLevelsInUpgrade(int constant) {
        return Integer.MAX_VALUE;
    }
}
