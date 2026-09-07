package com.lieval.maceeasily.client.gui;

import com.lieval.maceeasily.MaceEasilyMod;
import com.lieval.maceeasily.config.MaceEasilyConfig;
import com.lieval.maceeasily.config.TriggerDistance;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class MaceEasilyConfigScreen extends Screen {
    private final Screen parent;
    private final MaceEasilyConfig config;

    private TriggerDistance smashTrigger;
    private TriggerDistance windBurstTrigger;
    private boolean fallDamageMace;
    private boolean easilyCriticalDamage;
    private boolean noCooldown;

    private Button smashTriggerButton;
    private Button windBurstTriggerButton;
    private Button fallDamageMaceButton;
    private Button easilyCriticalButton;
    private Button noCooldownButton;

    public MaceEasilyConfigScreen(Screen parent) {
        super(Component.literal("Easily Critical Settings"));
        this.parent = parent;
        this.config = MaceEasilyMod.CONFIG != null ? MaceEasilyMod.CONFIG : MaceEasilyConfig.load();
        this.smashTrigger = this.config.smashTrigger;
        this.windBurstTrigger = this.config.windBurstTrigger;
        this.fallDamageMace = this.config.fallDamageMace;
        this.easilyCriticalDamage = this.config.easilyCriticalDamage;
        this.noCooldown = this.config.noCooldown;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;
        int btnWidth = 145;
        int btnHeight = 20;
        int leftX = centerX - 150;
        int rightX = centerX + 5;
        int startY = centerY - 50;

        // Row 1: Smash Trigger & Wind Burst Trigger
        this.smashTriggerButton = Button.builder(
                getSmashTriggerComponent(),
                button -> {
                    this.smashTrigger = this.smashTrigger.next();
                    button.setMessage(getSmashTriggerComponent());
                })
                .bounds(leftX, startY, btnWidth, btnHeight)
                .tooltip(Tooltip.create(Component.literal("Smash Attack Trigger: 1.5 Blocks (Default), 1.0 Block, or 0 Blocks. In 0 Blocks mode, critical hit on Mace is replaced by Smash Attack.")))
                .build();
        addRenderableWidget(this.smashTriggerButton);

        this.windBurstTriggerButton = Button.builder(
                getWindBurstTriggerComponent(),
                button -> {
                    this.windBurstTrigger = this.windBurstTrigger.next();
                    button.setMessage(getWindBurstTriggerComponent());
                })
                .bounds(rightX, startY, btnWidth, btnHeight)
                .tooltip(Tooltip.create(Component.literal("Wind Burst Enchantment Trigger: 1.5 Blocks (Default), 1.0 Block, or 0 Blocks.")))
                .build();
        addRenderableWidget(this.windBurstTriggerButton);

        // Row 2: Easily Critical Damage & No Cooldown
        this.easilyCriticalButton = Button.builder(
                getEasilyCriticalComponent(),
                button -> {
                    this.easilyCriticalDamage = !this.easilyCriticalDamage;
                    button.setMessage(getEasilyCriticalComponent());
                })
                .bounds(leftX, startY + 26, btnWidth, btnHeight)
                .tooltip(Tooltip.create(Component.literal("Allows critical hits on all weapons without jumping, even while standing still on the ground.")))
                .build();
        addRenderableWidget(this.easilyCriticalButton);

        this.noCooldownButton = Button.builder(
                getNoCooldownComponent(),
                button -> {
                    this.noCooldown = !this.noCooldown;
                    button.setMessage(getNoCooldownComponent());
                })
                .bounds(rightX, startY + 26, btnWidth, btnHeight)
                .tooltip(Tooltip.create(Component.literal("Removes cooldown for all items (Ender Pearl, Wind Charge, Shield, Chorus Fruit, etc.) and attack swing delay.")))
                .build();
        addRenderableWidget(this.noCooldownButton);

        // Row 3: Fall Damage Mace (Khusus saat terbang dengan Wind Burst)
        this.fallDamageMaceButton = Button.builder(
                getFallDamageMaceComponent(),
                button -> {
                    this.fallDamageMace = !this.fallDamageMace;
                    button.setMessage(getFallDamageMaceComponent());
                })
                .bounds(centerX - 100, startY + 52, 200, btnHeight)
                .tooltip(Tooltip.create(Component.literal("Fall Damage Mace: Protects from fall damage specifically when launched into the air by Wind Burst.")))
                .build();
        addRenderableWidget(this.fallDamageMaceButton);

        // Bottom: Save & Apply / Cancel
        int bottomY = this.height - 35;
        addRenderableWidget(Button.builder(
                Component.literal("§aSave & Apply"),
                button -> saveAndClose())
                .bounds(centerX - 105, bottomY, 100, 20)
                .build());

        addRenderableWidget(Button.builder(
                Component.literal("§cCancel"),
                button -> onClose())
                .bounds(centerX + 5, bottomY, 100, 20)
                .build());
    }

    private Component getSmashTriggerComponent() {
        String val = switch (this.smashTrigger) {
            case BLOCKS_1_5 -> "§e1.5 Blocks";
            case BLOCKS_1_0 -> "§b1.0 Block";
            case BLOCKS_0 -> "§a0 Block";
        };
        return Component.literal("Smash: " + val);
    }

    private Component getWindBurstTriggerComponent() {
        String val = switch (this.windBurstTrigger) {
            case BLOCKS_1_5 -> "§e1.5 Blocks";
            case BLOCKS_1_0 -> "§b1.0 Block";
            case BLOCKS_0 -> "§a0 Block";
        };
        return Component.literal("Wind Burst: " + val);
    }

    private Component getEasilyCriticalComponent() {
        return Component.literal("Easily Crit: " + (this.easilyCriticalDamage ? "§aON" : "§cOFF"));
    }

    private Component getNoCooldownComponent() {
        return Component.literal("No Cooldown: " + (this.noCooldown ? "§aON" : "§cOFF"));
    }

    private Component getFallDamageMaceComponent() {
        return Component.literal("Fall Damage Mace: " + (this.fallDamageMace ? "§aON" : "§cOFF"));
    }

    private void saveAndClose() {
        this.config.smashTrigger = this.smashTrigger;
        this.config.windBurstTrigger = this.windBurstTrigger;
        this.config.fallDamageMace = this.fallDamageMace;
        this.config.easilyCriticalDamage = this.easilyCriticalDamage;
        this.config.noCooldown = this.noCooldown;
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
        graphics.centeredText(this.font, Component.literal("§6§lEasily Critical Settings"), this.width / 2, this.height / 2 - 75, 0xFFFFFF);
    }
}
