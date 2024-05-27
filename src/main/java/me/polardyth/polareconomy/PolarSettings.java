package me.polardyth.polareconomy;

import org.jetbrains.annotations.NotNull;

public class PolarSettings {

    private static PolarEconomy plugin;

    static void onLoad(@NotNull final PolarEconomy plugin) {
        PolarSettings.plugin = plugin;
    }

    public static PolarEconomy getPlugin() {
        return plugin;
    }
}
