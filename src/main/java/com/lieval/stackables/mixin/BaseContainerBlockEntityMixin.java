package com.lieval.stackables.mixin;

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BaseContainerBlockEntity.class)
public abstract class BaseContainerBlockEntityMixin implements Container {
    @Override
    public int getMaxStackSize(ItemStack stack) {
        return stack.getMaxStackSize();
    }
}
