package me.polardyth.polareconomy.utils.config.interfaces;

import org.bukkit.configuration.file.FileConfiguration;

public interface ISettingsManager {
    FileHandler getFile(String fileName);
}
