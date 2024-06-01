package me.polardyth.polareconomy.utils.config.interfaces;

import org.bukkit.configuration.file.FileConfiguration;

public interface ISettingsManager {
    FileConfiguration getConfig(String fileName);
    void save(String fileName);
}
