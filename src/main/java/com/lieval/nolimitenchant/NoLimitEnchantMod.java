package com.lieval.nolimitenchant;

import com.lieval.nolimitenchant.config.NoLimitEnchantConfig;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoLimitEnchantMod implements ModInitializer {
    public static final String MOD_ID = "no_limit_enchant";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static NoLimitEnchantConfig CONFIG;

    @Override
    public void onInitialize() {
        CONFIG = NoLimitEnchantConfig.load();
        LOGGER.info("No Limit Enchant loaded successfully! (Max Level: {}, Remove Too Expensive: {})",
                CONFIG.maxEnchantmentLevel, CONFIG.removeTooExpensive);
    }
}
