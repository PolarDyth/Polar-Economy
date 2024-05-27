package me.polardyth.polareconomy.utils.config;

import org.bukkit.configuration.file.FileConfiguration;

public interface FileHandler {
    FileConfiguration options();
    void saveFile();
    void reload();
    String getFileName();
}
