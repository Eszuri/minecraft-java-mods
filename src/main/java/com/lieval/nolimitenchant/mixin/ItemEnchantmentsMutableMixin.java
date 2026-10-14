package com.lieval.nolimitenchant.mixin;

import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ItemEnchantments.Mutable.class)
public abstract class ItemEnchantmentsMutableMixin {

    @ModifyConstant(method = "set", constant = @Constant(intValue = 255))
    private int noLimitEnchant_allowLargeLevelsInSet(int constant) {
        return Integer.MAX_VALUE;
    }

    @ModifyConstant(method = "upgrade", constant = @Constant(intValue = 255))
    private int noLimitEnchant_allowLargeLevelsInUpgrade(int constant) {
        return Integer.MAX_VALUE;
    }
}
