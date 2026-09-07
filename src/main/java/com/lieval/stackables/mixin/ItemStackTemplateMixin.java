package com.lieval.stackables.mixin;

import com.lieval.stackables.StackableItemsMod;
import com.lieval.stackables.util.StackFallbackHelper;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemInstance;
import net.minecraft.world.item.ItemStackTemplate;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStackTemplate.class)
public abstract class ItemStackTemplateMixin implements ItemInstance {
    @Mutable
    @Shadow
    @Final
    public static MapCodec<ItemStackTemplate> MAP_CODEC;

    @Mutable
    @Shadow
    @Final
    public static Codec<ItemStackTemplate> CODEC;

    @Override
    public int getMaxStackSize() {
        return StackableItemsMod.getCustomStackSize();
    }

    @ModifyConstant(
        method = "lambda$static$0",
        constant = @Constant(intValue = 99),
        require = 0
    )
    private static int stackable_modifyTemplateMaxCount(int original) {
        return Integer.MAX_VALUE;
    }

    @Inject(method = "<clinit>", at = @At("RETURN"))
    private static void stackable_wrapTemplateCodecs(CallbackInfo ci) {
        MAP_CODEC = MAP_CODEC.xmap(StackFallbackHelper::decodeSafeTemplate, StackFallbackHelper::encodeSafeTemplate);
        CODEC = Codec.withAlternative(
            MAP_CODEC.codec(),
            Item.CODEC,
            item -> new ItemStackTemplate(item.value())
        );
    }
}
