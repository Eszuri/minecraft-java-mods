package com.lieval.maceeasily.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Path;

public class MaceEasilyConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger("easily_critical");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("easily_critical.json");

    public enum TriggerMode {
        MACE_AND_ENCHANT, // Triger rendah aktif untuk mace polosan dan enchant
        ENCHANT_ONLY      // Triger rendah hanya aktif jika mace memiliki enchantment (misal Wind Burst)
    }

    public TriggerDistance smashTrigger = TriggerDistance.BLOCKS_1_5;
    public TriggerDistance windBurstTrigger = TriggerDistance.BLOCKS_1_5;
    public boolean fallDamageMace = true;
    public boolean easilyCriticalDamage = true;
    public boolean noCooldown = true;

    // Fixed constant so it does not serialize into easily_critical.json
    public static final float MIN_SMASH_BONUS_DAMAGE = 4.0f;

    // Legacy fields for backward compatibility with older config files
    public Boolean alwaysCritical = null;
    public Boolean noItemCooldown = null;
    public Boolean noAttackCooldown = null;

    public static MaceEasilyConfig load() {
        File file = CONFIG_PATH.toFile();
        if (!file.exists()) {
            File legacyFile = FabricLoader.getInstance().getConfigDir().resolve("mace_easily.json").toFile();
            if (legacyFile.exists()) {
                file = legacyFile;
            }
        }
        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                MaceEasilyConfig config = GSON.fromJson(reader, MaceEasilyConfig.class);
                if (config != null) {
                    if (config.alwaysCritical != null) {
                        config.easilyCriticalDamage = config.alwaysCritical;
                    }
                    if (config.noItemCooldown != null) {
                        config.noCooldown = config.noItemCooldown;
                    }
                    if (config.smashTrigger == null) {
                        config.smashTrigger = TriggerDistance.BLOCKS_1_5;
                    }
                    if (config.windBurstTrigger == null) {
                        config.windBurstTrigger = TriggerDistance.BLOCKS_1_5;
                    }
                    return config;
                }
            } catch (Exception e) {
                LOGGER.error("Failed to load easily_critical.json, using default configuration", e);
            }
        }
        MaceEasilyConfig defaultConfig = new MaceEasilyConfig();
        defaultConfig.save();
        return defaultConfig;
    }

    public void save() {
        try {
            File file = CONFIG_PATH.toFile();
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            try (FileWriter writer = new FileWriter(file)) {
                GSON.toJson(this, writer);
            }
        } catch (Exception e) {
            LOGGER.error("Failed to save easily_critical.json", e);
        }
    }
}
