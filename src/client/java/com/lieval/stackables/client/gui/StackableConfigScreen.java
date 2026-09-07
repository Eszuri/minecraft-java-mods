package com.lieval.stackables.client.gui;

import com.lieval.stackables.StackableItemsMod;
import com.lieval.stackables.config.StackableConfig;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class StackableConfigScreen extends Screen {
    private final Screen parent;
    private final StackableConfig config;
    private EditBox maxStackBox;

    public StackableConfigScreen(Screen parent) {
        super(Component.literal("Stackable Items Settings"));
        this.parent = parent;
        this.config = StackableItemsMod.CONFIG != null ? StackableItemsMod.CONFIG : StackableConfig.load();
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;

        addRenderableWidget(new StringWidget(centerX - 130, centerY - 20, 160, 20, Component.literal("Max Stack Size:"), this.font));
        this.maxStackBox = new EditBox(this.font, centerX + 35, centerY - 20, 95, 20, Component.literal("Max Stack"));
        this.maxStackBox.setValue(String.valueOf(this.config.maxStackSize));
        this.maxStackBox.setMaxLength(6);
        this.maxStackBox.setTooltip(Tooltip.create(Component.literal("Enter any number (e.g. 64, 99, 128, 256, 1000, 64000).")));
        addRenderableWidget(this.maxStackBox);

        int bottomY = this.height - 35;
        addRenderableWidget(Button.builder(
                Component.literal("§aApply"),
                button -> saveAndClose())
                .bounds(centerX - 135, bottomY, 130, 20)
                .build());

        addRenderableWidget(Button.builder(
                Component.literal("§cCancel"),
                button -> onClose())
                .bounds(centerX + 5, bottomY, 130, 20)
                .build());
    }

    private void saveAndClose() {
        try {
            int maxVal = Integer.parseInt(this.maxStackBox.getValue().trim());
            if (maxVal > 0) {
                this.config.maxStackSize = maxVal;
            }
        } catch (NumberFormatException ignored) {}

        this.config.save();
        onClose();
    }

    @Override
    public void onClose() {
        if (this.minecraft != null && this.minecraft.gui != null) {
            this.minecraft.gui.setScreen(this.parent);
        }
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        super.extractRenderState(graphics, mouseX, mouseY, partialTick);
        graphics.centeredText(this.font, Component.literal("§6§lStackable Items Settings"), this.width / 2, this.height / 2 - 50, 0xFFFFFF);
    }
}
