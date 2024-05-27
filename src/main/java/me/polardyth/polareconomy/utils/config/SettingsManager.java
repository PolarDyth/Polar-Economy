package me.polardyth.polareconomy.utils.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class SettingsManager {

    private final Map<String, FileHandler> configs;

    public SettingsManager(boolean isData, String... fileNames) {
        configs = new HashMap<>();
        for (String fileName : fileNames) {
            if (isData) {
                configs.put(fileName, new DataManager(fileName));
            } else {
                configs.put(fileName, new ConfigManager(fileName));
            }
        }
    }

    public FileHandler getFileHandler(String fileName) {
        return configs.get(fileName);
    }

    public void save(String fileName) {
        configs.get(fileName).saveFile();
    }

    public void saveAll() {
        for (FileHandler fileHandler : configs.values()) {
            fileHandler.saveFile();
        }
    }

    public @NotNull FileConfiguration getConfig(String fileName) {
        return configs.get(fileName).options();
    }

    public void copyDefaults() {
        for (FileHandler fileHandler : configs.values()) {
            fileHandler.options().options().copyDefaults();
            fileHandler.saveFile();
        }
    }
}