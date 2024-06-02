package me.polardyth.polareconomy;

import me.polardyth.polareconomy.economy.balances.interfaces.IEconomyManager;
import me.polardyth.polareconomy.utils.config.interfaces.IConfigFiles;
import me.polardyth.polareconomy.utils.config.interfaces.IDataFiles;
import org.jetbrains.annotations.NotNull;

public class PolarSettings {

    private static PolarEconomy plugin;
    private static IEconomyManager economyManager;
    private static IConfigFiles configFiles;
    private static IDataFiles dataFiles;

    static void onLoad(@NotNull final PolarEconomy plugin) {

        PolarSettings.plugin = plugin;
    }

    static void loadEconomy(@NotNull final IEconomyManager economyManager) {
        PolarSettings.economyManager = economyManager;
    }

    static void loadFiles(@NotNull final IDataFiles dataFiles, @NotNull IConfigFiles configFiles) {
        PolarSettings.configFiles = configFiles;
        PolarSettings.dataFiles = dataFiles;
    }

    public static IEconomyManager getEconomyManager() {
        return PolarSettings.economyManager;
    }

    public static PolarEconomy getPlugin() {
        return plugin;
    }

    public static IDataFiles getDataFiles() {
        return dataFiles;
    }

    public static IConfigFiles getConfigFiles() {
        return configFiles;
    }
}
