package me.polardyth.polareconomy.utils;

import me.polardyth.polareconomy.PolarEconomy;
import me.polardyth.polareconomy.utils.config.SettingsManager;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.UUID;

public class EconomyManager {

    private final FileConfiguration balanceConfig;
    private final SettingsManager configFiles;
    private final SettingsManager dataFiles;

    public EconomyManager(PolarEconomy plugin, SettingsManager configFiles, SettingsManager dataFiles) {

        this.configFiles = configFiles;
        this.dataFiles = dataFiles;
        balanceConfig = dataFiles.getConfig("balances");
    }


    public double getBalance(UUID playerUUID) {
        return balanceConfig.getDouble(playerUUID.toString(), 0);
    }

    public void setBalance(UUID playerUUID, double amount) {
        balanceConfig.set(playerUUID.toString(), amount);
        dataFiles.save("balances");
    }

    public void addBalance(UUID playerUUID, double amount) {
        balanceConfig.set(playerUUID.toString(), getBalance(playerUUID) + amount);
        dataFiles.save("balances");
    }

    public SettingsManager getConfigs() {
        return configFiles;
    }

    public SettingsManager getData() {
        return dataFiles;
    }

    public boolean removeBalance(UUID playerUUID, double amount) {
        double currentBalance = getBalance(playerUUID);
        if (currentBalance >= amount) {
            setBalance(playerUUID, currentBalance - amount);
            return true;
        } else {
            return false;
        }
    }
}
