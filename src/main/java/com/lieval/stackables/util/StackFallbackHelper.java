package com.lieval.stackables.util;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.component.CustomData;

public final class StackFallbackHelper {
    public static final String NBT_REAL_COUNT = "stackable_real_count";
    public static final String NBT_SAVED_COUNT = "stackable_saved_count";

    private StackFallbackHelper() {}

    // Default vanilla max stack size (1 for damageable, 16 for pearls, 64 for normal)
    public static int getSafeVanillaMaxStackSize(Holder<Item> itemHolder) {
        if (itemHolder == null) return 64;
        try {
            DataComponentMap baseComponents = itemHolder.components();
            if (baseComponents != null) {
                if (baseComponents.has(DataComponents.MAX_DAMAGE)) {
                    return 1;
                }
                int maxStack = baseComponents.getOrDefault(DataComponents.MAX_STACK_SIZE, 64);
                return Math.max(1, Math.min(maxStack, 64));
            }
        } catch (Throwable ignored) {
        }
        return 64;
    }

    // Caps saved count to vanilla limit and stores real count in custom_data
    public static ItemStack encodeSafeStack(ItemStack stack) {
        if (stack == null || stack.isEmpty()) {
            return stack;
        }
        int count = stack.getCount();
        int safeLimit = getSafeVanillaMaxStackSize(stack.typeHolder());
        if (count <= safeLimit) {
            CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
            if (customData != null) {
                CompoundTag tag = customData.copyTag();
                if (tag.contains(NBT_REAL_COUNT) || tag.contains(NBT_SAVED_COUNT)) {
                    ItemStack copy = stack.copy();
                    CustomData.update(DataComponents.CUSTOM_DATA, copy, t -> {
                        t.remove(NBT_REAL_COUNT);
                        t.remove(NBT_SAVED_COUNT);
                    });
                    return copy;
                }
            }
            return stack;
        }

        ItemStack safeStack = stack.copy();
        safeStack.setCount(safeLimit);
        CustomData.update(DataComponents.CUSTOM_DATA, safeStack, tag -> {
            tag.putInt(NBT_REAL_COUNT, count);
            tag.putInt(NBT_SAVED_COUNT, safeLimit);
        });
        return safeStack;
    }

    // Restores real count from custom_data with delta adjustment
    public static ItemStack decodeSafeStack(ItemStack stack) {
        if (stack == null || stack.isEmpty()) {
            return stack;
        }
        CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
        if (customData != null) {
            CompoundTag tag = customData.copyTag();
            int realCount = tag.getIntOr(NBT_REAL_COUNT, 0);
            if (realCount > 0) {
                int currentCount = stack.getCount();
                int safeLimit = getSafeVanillaMaxStackSize(stack.typeHolder());
                int savedCount = tag.getIntOr(NBT_SAVED_COUNT, safeLimit);

                int finalCount = realCount;
                if (currentCount < savedCount) {
                    int consumed = savedCount - currentCount;
                    finalCount = Math.max(currentCount, realCount - consumed);
                } else if (currentCount > savedCount) {
                    int added = currentCount - savedCount;
                    finalCount = realCount + added;
                }

                stack.setCount(finalCount);
                CustomData.update(DataComponents.CUSTOM_DATA, stack, t -> {
                    t.remove(NBT_REAL_COUNT);
                    t.remove(NBT_SAVED_COUNT);
                });
            }
        }
        return stack;
    }

    // Safe encoding for ItemStackTemplate (e.g. shulker boxes)
    public static ItemStackTemplate encodeSafeTemplate(ItemStackTemplate template) {
        if (template == null) {
            return null;
        }
        try {
            int count = template.count();
            int safeLimit = getSafeVanillaMaxStackSize(template.item());
            if (count <= safeLimit) {
                return template;
            }
            ItemStack stack = template.create();
            if (stack.isEmpty()) {
                return template;
            }
            ItemStack safeStack = encodeSafeStack(stack);
            return ItemStackTemplate.fromNonEmptyStack(safeStack);
        } catch (Throwable ignored) {
            return template;
        }
    }

    // Safe decoding for ItemStackTemplate
    public static ItemStackTemplate decodeSafeTemplate(ItemStackTemplate template) {
        if (template == null) {
            return null;
        }
        try {
            CustomData customData = template.components().split().added().get(DataComponents.CUSTOM_DATA);
            if (customData != null && customData.copyTag().contains(NBT_REAL_COUNT)) {
                ItemStack stack = template.create();
                if (!stack.isEmpty()) {
                    ItemStack decodedStack = decodeSafeStack(stack);
                    return ItemStackTemplate.fromNonEmptyStack(decodedStack);
                }
            }
        } catch (Throwable ignored) {
        }
        return template;
    }
}
