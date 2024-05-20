package me.polardyth.polareconomy.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class SettingsManager {

    private final Plugin plugin;
    private final File balancesFile;
    private final FileConfiguration balancesConfig;
    private final FileConfiguration config;

    public SettingsManager(Plugin plugin) {

        this.plugin = plugin;
        balancesFile = new File(plugin.getDataFolder(), "balances.yml");
        config = this.plugin.getConfig();

        if (!balancesFile.exists()) {
            try {
                balancesFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        balancesConfig = YamlConfiguration.loadConfiguration(balancesFile);
    }

    public FileConfiguration getBalancesConfig() {
        return balancesConfig;
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void saveConfig() {
        try {
            balancesConfig.save(balancesFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

