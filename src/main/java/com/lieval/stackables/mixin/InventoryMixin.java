package com.lieval.stackables.mixin;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Inventory.class)
public abstract class InventoryMixin implements Container {
    @Override
    public int getMaxStackSize(ItemStack stack) {
        return stack.getMaxStackSize();
    }
}
