package me.polardyth.polareconomy;

import me.polardyth.polareconomy.commands.BalanceCommand;
import me.polardyth.polareconomy.commands.PayCommand;
import me.polardyth.polareconomy.commands.SetBalanceCommand;
import me.polardyth.polareconomy.utils.EconomyManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class PolarEconomy extends JavaPlugin {

    @Override
    public void onEnable() {

        EconomyManager economyManager = new EconomyManager(this);
        getCommand("balance").setExecutor(new BalanceCommand(economyManager));
        getCommand("pay").setExecutor(new PayCommand(economyManager));
        getCommand("setbalance").setExecutor(new SetBalanceCommand(economyManager));
        getLogger().info("PolarEconomy enabled!");

        FileConfiguration config = this.getConfig();
        config.options().copyDefaults(true);
        saveConfig();
    }
    @Override
    public void onDisable() {
        getLogger().info("PolarEconomy Disabled");
    }
}
