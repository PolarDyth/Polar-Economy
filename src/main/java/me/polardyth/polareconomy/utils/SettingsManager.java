package me.polardyth.polareconomy.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class SettingsManager {

    private final Plugin plugin;
    private final File dataFolder;

    private final File interestFile;
    private final FileConfiguration interestConfig;

    private final File balancesFile;
    private final FileConfiguration balancesConfig;

    private final FileConfiguration config;

    public SettingsManager(Plugin plugin) {

        this.plugin = plugin;
        dataFolder = new File(plugin.getDataFolder(), "data");


        balancesFile = new File(dataFolder, "balances.yml");
        interestFile = new File(dataFolder, "interest.yml");
        config = this.plugin.getConfig();

        if (!balancesFile.exists()) {
            try {
                balancesFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        balancesConfig = YamlConfiguration.loadConfiguration(balancesFile);

        if (!dataFolder.exists()) {
            try {
                dataFolder.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        interestConfig = YamlConfiguration.loadConfiguration(interestFile);
    }

    public FileConfiguration getInterestConfig() {
        return interestConfig;
    }

    public FileConfiguration getBalancesConfig() {
        return balancesConfig;
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void saveBalanceConfig() {
        try {
            balancesConfig.save(balancesFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveInterestConfig() {
        try {
            interestConfig.save(interestFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

