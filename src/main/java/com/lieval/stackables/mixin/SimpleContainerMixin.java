package com.lieval.stackables.mixin;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SimpleContainer.class)
public abstract class SimpleContainerMixin implements Container {
    @Override
    public int getMaxStackSize(ItemStack stack) {
        return stack.getMaxStackSize();
    }
}
