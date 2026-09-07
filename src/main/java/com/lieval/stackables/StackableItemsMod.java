package com.lieval.stackables;

import com.lieval.stackables.config.StackableConfig;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StackableItemsMod implements ModInitializer {
    public static final String MOD_ID = "stackable_items";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static StackableConfig CONFIG;

    @Override
    public void onInitialize() {
        CONFIG = StackableConfig.load();
        StackableCommands.register();
        LOGGER.info("[Stackable Items] Initialized. Max Stack Size: {}", CONFIG.maxStackSize);
    }

    public static int getCustomStackSize() {
        if (CONFIG == null) {
            CONFIG = StackableConfig.load();
        }
        return Math.max(1, CONFIG.maxStackSize);
    }
}
