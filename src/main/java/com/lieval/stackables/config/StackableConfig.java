package com.lieval.stackables.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Path;

public class StackableConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger("stackable_items");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("stackable_items.json");

    public int maxStackSize = 64;

    public static StackableConfig load() {
        File file = CONFIG_PATH.toFile();
        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                StackableConfig config = GSON.fromJson(reader, StackableConfig.class);
                if (config != null) {
                    return config;
                }
            } catch (Exception e) {
                LOGGER.error("Failed to load stackable_items.json, using default configuration", e);
            }
        }
        StackableConfig defaultConfig = new StackableConfig();
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
            LOGGER.error("Failed to save stackable_items.json", e);
        }
    }
}