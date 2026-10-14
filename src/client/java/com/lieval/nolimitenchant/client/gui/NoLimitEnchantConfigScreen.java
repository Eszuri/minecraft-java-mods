package com.lieval.nolimitenchant.client.gui;

import com.lieval.nolimitenchant.NoLimitEnchantMod;
import com.lieval.nolimitenchant.config.NoLimitEnchantConfig;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class NoLimitEnchantConfigScreen extends Screen {
    private final Screen parent;
    private final NoLimitEnchantConfig config;

    private boolean removeAnvilPenalty;
    private NoLimitEnchantConfig.LevelFormat levelFormat;
    private EditBox maxLevelBox;

    public NoLimitEnchantConfigScreen(Screen parent) {
        super(Component.literal("No Limit Enchant"));
        this.parent = parent;
        this.config = NoLimitEnchantMod.CONFIG != null ? NoLimitEnchantMod.CONFIG : NoLimitEnchantConfig.load();

        this.removeAnvilPenalty = this.config.removeAnvilPenalty;
        this.levelFormat = this.config.levelFormat != null ? this.config.levelFormat : NoLimitEnchantConfig.LevelFormat.ROMAN;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;
        int startY = centerY - 45;
        int rowGap = 26;
        int controlX = centerX + 5;
        int controlWidth = 135;
        int controlHeight = 20;

        // Row 1: Anvil Penalty Toggle Button (Right side)
        int row1Y = startY;
        Button penaltyBtn = Button.builder(
                getPenaltyComponent(),
                btn -> {
                    this.removeAnvilPenalty = !this.removeAnvilPenalty;
                    btn.setMessage(getPenaltyComponent());
                })
                .bounds(controlX, row1Y, controlWidth, controlHeight)
                .tooltip(Tooltip.create(Component.literal("None: 0 penalty cost. Unlimited: Vanilla exponential penalty.")))
                .build();
        addRenderableWidget(penaltyBtn);

        // Row 2: Level Format Toggle Button (Right side)
        int row2Y = startY + rowGap;
        Button formatBtn = Button.builder(
                getLevelFormatComponent(),
                btn -> {
                    this.levelFormat = (this.levelFormat == NoLimitEnchantConfig.LevelFormat.ROMAN)
                            ? NoLimitEnchantConfig.LevelFormat.NUMBER
                            : NoLimitEnchantConfig.LevelFormat.ROMAN;
                    btn.setMessage(getLevelFormatComponent());
                })
                .bounds(controlX, row2Y, controlWidth, controlHeight)
                .tooltip(Tooltip.create(Component.literal("Display style for enchantment levels in tooltips.")))
                .build();
        addRenderableWidget(formatBtn);

        // Row 3: Max Level EditBox (Right side)
        int row3Y = startY + rowGap * 2;
        int currentMax = this.config.maxEnchantmentLevel;
        if (currentMax <= 0) {
            currentMax = 255;
        }

        this.maxLevelBox = new EditBox(this.font, controlX, row3Y, controlWidth, controlHeight, Component.literal("Max Level"));
        this.maxLevelBox.setMaxLength(9);
        this.maxLevelBox.setHint(Component.literal("255"));
        this.maxLevelBox.setValue(String.valueOf(currentMax));
        this.maxLevelBox.setTooltip(Tooltip.create(Component.literal("Maximum enchantment level limit (default: 255).")));
        this.maxLevelBox.setResponder(text -> {
            String digitsOnly = text.replaceAll("[^0-9]", "");
            if (!digitsOnly.equals(text)) {
                this.maxLevelBox.setValue(digitsOnly);
            }
        });
        addRenderableWidget(this.maxLevelBox);

        // Row 4: Action Buttons (Apply & Cancel)
        int actionY = startY + rowGap * 3 + 12;
        int actionWidth = 135;

        Button applyBtn = Button.builder(
                Component.literal("§aApply"),
                btn -> applyAndClose())
                .bounds(centerX - 140, actionY, actionWidth, controlHeight)
                .build();
        addRenderableWidget(applyBtn);

        Button cancelBtn = Button.builder(
                Component.literal("§cCancel"),
                btn -> onClose())
                .bounds(controlX, actionY, actionWidth, controlHeight)
                .build();
        addRenderableWidget(cancelBtn);
    }

    private Component getPenaltyComponent() {
        return Component.literal(this.removeAnvilPenalty ? "§aNone" : "§cUnlimited");
    }

    private Component getLevelFormatComponent() {
        if (this.levelFormat == NoLimitEnchantConfig.LevelFormat.NUMBER) {
            return Component.literal("§eNumbers");
        } else {
            return Component.literal("§eRoman");
        }
    }

    private void applyAndClose() {
        this.config.removeTooExpensive = true;
        this.config.allowInfiniteEnchantLevels = true;
        this.config.removeAnvilPenalty = this.removeAnvilPenalty;
        this.config.levelFormat = this.levelFormat;

        if (this.maxLevelBox != null) {
            String val = this.maxLevelBox.getValue().trim();
            try {
                int parsed = Integer.parseInt(val);
                if (parsed > 0) {
                    this.config.maxEnchantmentLevel = parsed;
                } else {
                    this.config.maxEnchantmentLevel = 255;
                }
            } catch (NumberFormatException e) {
                this.config.maxEnchantmentLevel = 255;
            }
        }

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
        this.extractTransparentBackground(graphics);
        super.extractRenderState(graphics, mouseX, mouseY, partialTick);
        int centerX = this.width / 2;
        int centerY = this.height / 2;
        int startY = centerY - 45;
        int rowGap = 26;
        int labelX = centerX - 140;

        // Title
        graphics.centeredText(this.font, Component.literal("§6§lNo Limit Enchant"), centerX, centerY - 68, 0xFFFFFFFF);

        // Feature Labels (Left column, aligned with controls)
        graphics.text(this.font, Component.literal("§fAnvil Penalty"), labelX, startY + 6, 0xFFFFFFFF);
        graphics.text(this.font, Component.literal("§fLevel Format"), labelX, startY + rowGap + 6, 0xFFFFFFFF);
        graphics.text(this.font, Component.literal("§fMax Level"), labelX, startY + rowGap * 2 + 6, 0xFFFFFFFF);
    }
}
