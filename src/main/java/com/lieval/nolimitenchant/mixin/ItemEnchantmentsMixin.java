package com.lieval.nolimitenchant.mixin;

import com.mojang.serialization.Codec;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ItemEnchantments.class)
public abstract class ItemEnchantmentsMixin {

    /**
     * Mengizinkan level enchantment di atas 255 pada konstruktor ItemEnchantments
     * tanpa melempar IllegalArgumentException.
     */
    @ModifyConstant(method = "<init>(Lit/unimi/dsi/fastutil/objects/Object2IntOpenHashMap;)V", constant = @Constant(intValue = 255))
    private static int noLimitEnchant_allowLargeLevelsInConstructor(int constant) {
        return Integer.MAX_VALUE;
    }

    /**
     * Memperluas batasan Codec.intRange(1, 255) menjadi Codec.intRange(1, Integer.MAX_VALUE)
     * agar level tinggi bisa disimpan dan dimuat dari data dunia/NBT.
     */
    @ModifyArg(
        method = "<clinit>",
        at = @At(
            value = "INVOKE",
            target = "Lcom/mojang/serialization/Codec;intRange(II)Lcom/mojang/serialization/Codec;"
        ),
        index = 1
    )
    private static int noLimitEnchant_expandLevelCodecRange(int max) {
        return Integer.MAX_VALUE;
    }
}
