package com.lieval.reflectiveshield;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReflectiveShieldMod implements ModInitializer {
    public static final String MOD_ID = "reflective_shield";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Reflective Shield mod initialized!");
    }
}
