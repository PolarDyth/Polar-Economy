package me.polardyth.polareconomy.utils.config;

import org.bukkit.configuration.file.FileConfiguration;

public interface FileHandler {
    FileConfiguration getConfig();
    void saveFile();
    void reload();
    String getFileName();
}
