package me.polardyth.polareconomy.utils;

import me.polardyth.polareconomy.utils.config.SettingsManager;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.UUID;

public class EconomyManager {

    private final FileConfiguration balanceConfig;
    private final SettingsManager configFiles;
    private final SettingsManager dataFiles;

    public EconomyManager(SettingsManager configFiles, SettingsManager dataFiles) {

        this.configFiles = configFiles;
        this.dataFiles = dataFiles;
        balanceConfig = dataFiles.getConfig("balances");
    }


    public double getPurseBalance(UUID playerUUID) {
        return balanceConfig.getDouble(playerUUID.toString() + ".purse", 0);
    }

    public void setPurseBalance(UUID playerUUID, double amount) {
        balanceConfig.set(playerUUID.toString() + ".purse", amount);
        dataFiles.save("balances");
    }

    public void addPurseBalance(UUID playerUUID, double amount) {
        balanceConfig.set(playerUUID.toString() + ".purse", getPurseBalance(playerUUID) + amount);
        dataFiles.save("balances");
    }

    public boolean removePurseBalance(UUID playerUUID, double amount) {
        double currentBalance = getPurseBalance(playerUUID);
        if (currentBalance >= amount) {
            setPurseBalance(playerUUID, currentBalance - amount);
            return true;
        } else {
            return false;
        }
    }

    public double getBankBalance(UUID playerUUID) {
        return balanceConfig.getDouble(playerUUID.toString() + ".bank", 0);
    }

    public void setBankBalance(UUID playerUUID, double amount) {
        balanceConfig.set(playerUUID.toString() + ".bank", amount);
        dataFiles.save("balances");
    }

    public void addBankBalance(UUID playerUUID, double amount) {
        balanceConfig.set(playerUUID.toString() + ".bank", getBankBalance(playerUUID) + amount);
        dataFiles.save("balances");
    }

    public boolean removeBankBalance(UUID playerUUID, double amount) {
        double currentBalance = getBankBalance(playerUUID);
        if (currentBalance >= amount) {
            setBankBalance(playerUUID, currentBalance - amount);
            return true;
        } else {
            return false;
        }
    }

    public SettingsManager getConfigs() {
        return configFiles;
    }

    public SettingsManager getData() {
        return dataFiles;
    }
}
