package me.polardyth.polareconomy.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class EconomyManager {

    private final FileConfiguration balancesConfig;
    private final SettingsManager settingsManager;

    public EconomyManager(Plugin plugin) {
        settingsManager = new SettingsManager(plugin);
        balancesConfig = settingsManager.getBalancesConfig();
    }


    public double getBalance(UUID playerUUID) {
        return balancesConfig.getDouble(playerUUID.toString(), 0);
    }

    public void setBalance(UUID playerUUID, double amount) {
        balancesConfig.set(playerUUID.toString(), amount);
        settingsManager.saveConfig();
    }

    public void addBalance(UUID playerUUID, double amount) {
        balancesConfig.set(playerUUID.toString(), getBalance(playerUUID) + amount);
        settingsManager.saveConfig();
    }

    public boolean removeBalance(UUID playerUUID, double amount) {
        double currentBalance = getBalance(playerUUID);
        if (currentBalance >= amount) {
            setBalance(playerUUID, currentBalance - amount);
            settingsManager.saveConfig();
            return true;
        } else {
            return false;
        }
    }
}
