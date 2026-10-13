package com.lieval.nolimitenchant.mixin;

import com.lieval.nolimitenchant.NoLimitEnchantMod;
import com.lieval.nolimitenchant.config.NoLimitEnchantConfig;
import com.lieval.nolimitenchant.util.RomanNumeralHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public abstract class EnchantmentMixin {

    /**
     * Memperbaiki dan menyesuaikan tampilan tooltip level enchantment.
     * Mendukung dua mode format:
     * - ROMAN: Angka Romawi (I..X vanilla, > 10 menggunakan RomanNumeralHelper misal XI..CCLV).
     * - NUMBER: Angka biasa / desimal 0-9 (1, 2, 3, 5, 10, 255).
     */
    @Inject(method = "getFullname", at = @At("HEAD"), cancellable = true)
    private static void noLimitEnchant_formatLevelFullname(Holder<Enchantment> holder, int level, CallbackInfoReturnable<Component> cir) {
        if (NoLimitEnchantMod.CONFIG == null) {
            return;
        }

        NoLimitEnchantConfig.LevelFormat format = NoLimitEnchantMod.CONFIG.levelFormat;

        if (format == NoLimitEnchantConfig.LevelFormat.NUMBER) {
            MutableComponent mutableComponent = holder.value().description().copy();
            if (holder.is(EnchantmentTags.CURSE)) {
                ComponentUtils.mergeStyles(mutableComponent, Style.EMPTY.withColor(ChatFormatting.RED));
            } else {
                ComponentUtils.mergeStyles(mutableComponent, Style.EMPTY.withColor(ChatFormatting.GRAY));
            }

            // Jika level == 1 dan enchant hanya punya 1 level (seperti Mending, Silk Touch),
            // vanilla tidak menampilkan angka. Hanya tampilkan angka jika level != 1 atau maxLevel != 1.
            if (level != 1 || holder.value().getMaxLevel() != 1) {
                mutableComponent.append(CommonComponents.SPACE).append(Component.literal(String.valueOf(level)));
            }

            cir.setReturnValue(mutableComponent);
        } else if (format == NoLimitEnchantConfig.LevelFormat.ROMAN) {
            // Mode ROMAN: Vanilla sudah memiliki translasi hingga enchantment.level.10 ("I" .. "X").
            // Untuk level > 10, kita format agar tidak muncul teks mentah 'enchantment.level.11'.
            if (level > 10) {
                MutableComponent mutableComponent = holder.value().description().copy();
                if (holder.is(EnchantmentTags.CURSE)) {
                    ComponentUtils.mergeStyles(mutableComponent, Style.EMPTY.withColor(ChatFormatting.RED));
                } else {
                    ComponentUtils.mergeStyles(mutableComponent, Style.EMPTY.withColor(ChatFormatting.GRAY));
                }

                String levelText = RomanNumeralHelper.toRomanOrNumber(level);
                mutableComponent.append(CommonComponents.SPACE).append(Component.literal(levelText));
                cir.setReturnValue(mutableComponent);
            }
        }
    }
}
