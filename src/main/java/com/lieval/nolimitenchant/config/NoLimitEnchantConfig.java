package com.lieval.nolimitenchant.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Path;

public class NoLimitEnchantConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger("no_limit_enchant");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("no_limit_enchant.json");

    public enum LevelFormat {
        ROMAN,
        NUMBER
    }

    // Anvil "Too Expensive!" removal (always active)
    public boolean removeTooExpensive = true;

    // Anvil penalty removal:
    // true = No penalty at all (penalty = 0 always)
    // false = Vanilla exponential penalty without limit (unlimited)
    public boolean removeAnvilPenalty = true;

    // Infinite enchantment leveling (always active)
    public boolean allowInfiniteEnchantLevels = true;
    public int maxEnchantmentLevel = 255;

    // Allow /enchant command to exceed vanilla max level
    public boolean allowCommandOverlevel = true;

    // Enchantment level display format (ROMAN vs NUMBER)
    public LevelFormat levelFormat = LevelFormat.ROMAN;

    public static NoLimitEnchantConfig load() {
        File file = CONFIG_PATH.toFile();
        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                NoLimitEnchantConfig config = GSON.fromJson(reader, NoLimitEnchantConfig.class);
                if (config != null) {
                    config.removeTooExpensive = true;
                    config.allowInfiniteEnchantLevels = true;
                    if (config.levelFormat == null) {
                        config.levelFormat = LevelFormat.ROMAN;
                    }
                    if (config.maxEnchantmentLevel <= 0) {
                        config.maxEnchantmentLevel = 255;
                    }
                    return config;
                }
            } catch (Exception e) {
                LOGGER.error("Failed to load No Limit Enchant configuration! Using default settings.", e);
            }
        }
        NoLimitEnchantConfig defaultConfig = new NoLimitEnchantConfig();
        defaultConfig.save();
        return defaultConfig;
    }

    public void save() {
        this.removeTooExpensive = true;
        this.allowInfiniteEnchantLevels = true;
        if (this.levelFormat == null) {
            this.levelFormat = LevelFormat.ROMAN;
        }
        if (this.maxEnchantmentLevel <= 0) {
            this.maxEnchantmentLevel = 255;
        }
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
            LOGGER.error("Failed to save No Limit Enchant configuration!", e);
        }
    }
}
