package me.polardyth.polareconomy.utils.config;

import me.polardyth.polareconomy.utils.config.interfaces.FileHandler;
import me.polardyth.polareconomy.utils.config.interfaces.IDataFiles;
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

    public void save(String fileName) {
        configs.get(fileName).saveFile();
    }

    public @NotNull FileConfiguration getConfig(String fileName) {
        return configs.get(fileName).getConfig();
    }
}