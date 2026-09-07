package com.lieval.stackables.client.mixin;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.world.item.ItemStack;
import org.joml.Matrix3x2fStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiGraphicsExtractor.class)
public abstract class GuiGraphicsExtractorMixin {
    @Shadow @Final private Matrix3x2fStack pose;
    @Shadow public abstract void text(Font font, String str, int x, int y, int color, boolean dropShadow);

    @Inject(method = "itemCount", at = @At("HEAD"), cancellable = true)
    private void onItemCount(Font font, ItemStack itemStack, int x, int y, String countText, CallbackInfo ci) {
        int count = itemStack.getCount();
        if (count <= 1 && countText == null) {
            ci.cancel();
            return;
        }

        String text;
        boolean forceSmallFont = false;

        if (countText != null) {
            text = countText;
        } else if (count < 100) {
            text = String.valueOf(count);
        } else if (count < 1000) {
            text = String.valueOf(count);
            forceSmallFont = true;
        } else if (count < 1000000) {
            int mainPart = count / 1000;
            int fraction = (count % 1000) / 100;
            if (fraction > 0) {
                text = mainPart + "." + fraction + "k";
            } else {
                text = mainPart + "k";
            }
        } else {
            int mainPart = count / 1000000;
            int fraction = (count % 1000000) / 100000;
            if (fraction > 0) {
                text = mainPart + "." + fraction + "M";
            } else {
                text = mainPart + "M";
            }
        }

        int textWidth = font.width(text);

        if (forceSmallFont) {
            float targetWidth = 13.5f;
            float scale = targetWidth / (float) textWidth;
            float xLeft = (x + 16.5f) - (textWidth * scale);
            float yTop = (y + 17.0f) - (9.0f * scale);

            this.pose.pushMatrix();
            this.pose.translate(xLeft, yTop);
            this.pose.scale(scale, scale);
            this.text(font, text, 0, 0, -1, true);
            this.pose.popMatrix();
        } else if (textWidth <= 15) {
            this.text(font, text, x + 17 - textWidth, y + 9, -1, true);
        } else {
            float scale = 15.0f / (float) textWidth;
            float xLeft = (x + 16.5f) - (textWidth * scale);
            float yTop = (y + 17.0f) - (9.0f * scale);

            this.pose.pushMatrix();
            this.pose.translate(xLeft, yTop);
            this.pose.scale(scale, scale);
            this.text(font, text, 0, 0, -1, true);
            this.pose.popMatrix();
        }

        ci.cancel();
    }
}
