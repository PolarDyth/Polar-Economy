package me.polardyth.polareconomy;

import me.polardyth.polareconomy.utils.config.interfaces.IConfigFiles;
import me.polardyth.polareconomy.utils.config.interfaces.IDataFiles;
import org.jetbrains.annotations.NotNull;

public class PolarSettings {

    private static PolarEconomy plugin;
    private static IConfigFiles configFiles;
    private static IDataFiles dataFiles;

    static void onLoad(@NotNull final PolarEconomy plugin, @NotNull final IDataFiles dataFiles, IConfigFiles configFiles) {
        PolarSettings.plugin = plugin;
        PolarSettings.configFiles = configFiles;
        PolarSettings.dataFiles = dataFiles;
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
