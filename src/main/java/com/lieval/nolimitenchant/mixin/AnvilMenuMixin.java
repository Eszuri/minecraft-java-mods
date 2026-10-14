package com.lieval.nolimitenchant.mixin;

import com.lieval.nolimitenchant.NoLimitEnchantMod;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AnvilMenu.class)
public abstract class AnvilMenuMixin {

    @Redirect(
        method = "createResult",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/item/enchantment/Enchantment;getMaxLevel()I"
        )
    )
    private int noLimitEnchant_redirectGetMaxLevel(Enchantment enchantment) {
        int max = NoLimitEnchantMod.CONFIG != null ? NoLimitEnchantMod.CONFIG.maxLevel : 255;
        return Math.max(enchantment.getMaxLevel(), max);
    }

    @Redirect(
        method = "createResult",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/player/Player;hasInfiniteMaterials()Z",
            ordinal = 1
        )
    )
    private boolean noLimitEnchant_bypassTooExpensiveEmpty(Player player) {
        return true;
    }

    @Redirect(
        method = "createResult",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/item/ItemStack;getOrDefault(Lnet/minecraft/core/component/DataComponentType;Ljava/lang/Object;)Ljava/lang/Object;"
        )
    )
    private <T> Object noLimitEnchant_ignorePriorWorkPenalty(ItemStack stack, DataComponentType<? extends T> componentType, T defaultValue) {
        if (NoLimitEnchantMod.CONFIG != null && NoLimitEnchantMod.CONFIG.anvilPenalty && componentType == DataComponents.REPAIR_COST) {
            return 0;
        }
        return stack.getOrDefault(componentType, defaultValue);
    }

    @Inject(method = "calculateIncreasedRepairCost", at = @At("HEAD"), cancellable = true)
    private static void noLimitEnchant_calculateIncreasedRepairCost(int cost, CallbackInfoReturnable<Integer> cir) {
        if (NoLimitEnchantMod.CONFIG != null && NoLimitEnchantMod.CONFIG.anvilPenalty) {
            cir.setReturnValue(0);
        }
    }
}
