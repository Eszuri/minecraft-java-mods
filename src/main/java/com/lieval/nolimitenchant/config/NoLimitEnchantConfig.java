package com.lieval.nolimitenchant.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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

    public boolean anvilPenalty = true;
    public LevelFormat levelFormat = LevelFormat.ROMAN;
    public int maxLevel = 255;

    public static NoLimitEnchantConfig load() {
        File file = CONFIG_PATH.toFile();
        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
                NoLimitEnchantConfig config = new NoLimitEnchantConfig();

                if (json.has("anvilPenalty")) {
                    config.anvilPenalty = json.get("anvilPenalty").getAsBoolean();
                }

                if (json.has("levelFormat")) {
                    try {
                        config.levelFormat = LevelFormat.valueOf(json.get("levelFormat").getAsString().toUpperCase());
                    } catch (Exception ignored) {
                        config.levelFormat = LevelFormat.ROMAN;
                    }
                }

                if (json.has("maxLevel")) {
                    config.maxLevel = json.get("maxLevel").getAsInt();
                }

                if (config.maxLevel <= 0) {
                    config.maxLevel = 255;
                }
                if (config.levelFormat == null) {
                    config.levelFormat = LevelFormat.ROMAN;
                }
                return config;
            } catch (Exception e) {
                LOGGER.error("Failed to load No Limit Enchant configuration! Using default settings.", e);
            }
        }
        NoLimitEnchantConfig defaultConfig = new NoLimitEnchantConfig();
        defaultConfig.save();
        return defaultConfig;
    }

    public void save() {
        if (this.levelFormat == null) {
            this.levelFormat = LevelFormat.ROMAN;
        }
        if (this.maxLevel <= 0) {
            this.maxLevel = 255;
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
