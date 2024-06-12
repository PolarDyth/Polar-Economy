package me.polardyth.polareconomy;

import me.polardyth.polareconomy.economy.EconomyManager;
import me.polardyth.polareconomy.systems.transactionhistory.TransactionHandler;
import me.polardyth.polareconomy.utils.files.ConfigFiles;
import me.polardyth.polareconomy.utils.files.DataFiles;
import org.jetbrains.annotations.NotNull;

public class PolarSettings {

    private static PolarEconomy plugin;
    private static EconomyManager economyManager;
    private static TransactionHandler transactionHandler;
    private static ConfigFiles configFiles;
    private static DataFiles dataFiles;

    static void onLoad(@NotNull final PolarEconomy plugin) {

        PolarSettings.plugin = plugin;
    }

    static void loadEconomy(@NotNull final EconomyManager economyManager, @NotNull final TransactionHandler transactionHandler) {
        PolarSettings.transactionHandler = transactionHandler;
        PolarSettings.economyManager = economyManager;
    }

    static void loadFiles(@NotNull final DataFiles dataFiles, @NotNull ConfigFiles configFiles) {
        PolarSettings.configFiles = configFiles;
        PolarSettings.dataFiles = dataFiles;
    }

    public static EconomyManager getEconomyManager() {
        return PolarSettings.economyManager;
    }

    public static TransactionHandler getTransactionHandler() {
        return PolarSettings.transactionHandler;
    }

    public static PolarEconomy getPlugin() {
        return plugin;
    }

    public static DataFiles getDataFiles() {
        return dataFiles;
    }

    public static ConfigFiles getConfigFiles() {
        return configFiles;
    }
}
