package me.polardyth.polareconomy;

import me.polardyth.polareconomy.commands.BalanceCommand;
import me.polardyth.polareconomy.commands.BankCommand;
import me.polardyth.polareconomy.commands.PayCommand;
import me.polardyth.polareconomy.commands.SetBalanceCommand;
import me.polardyth.polareconomy.listeners.MenuListener;
import me.polardyth.polareconomy.systems.Interest;
import me.polardyth.polareconomy.utils.EconomyManager;
import me.polardyth.polareconomy.utils.MessageUtil;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class PolarEconomy extends JavaPlugin implements Listener {

    private Interest interest;

    @Override
    public void onEnable() {

        EconomyManager economyManager = new EconomyManager(this);
        getCommand("balance").setExecutor(new BalanceCommand(economyManager));
        getCommand("pay").setExecutor(new PayCommand(economyManager));
        getCommand("setbalance").setExecutor(new SetBalanceCommand(economyManager));
        getCommand("bank").setExecutor(new BankCommand(economyManager));

        getServer().getPluginManager().registerEvents(new MenuListener(), this);

        getLogger().info("PolarEconomy enabled!");

        interest = new Interest(economyManager, this);

        FileConfiguration config = loadConfig();

        this.getServer().getScheduler().scheduleSyncDelayedTask(this, () -> {
            assert config != null;
            MessageUtil.loadMessages(config);
            getLogger().info("Messages loaded successfully.");
        });

        if (MessageUtil.isInterestEnabled()) {
            try {
                Interest interest = new Interest(economyManager, this);
                interest.scheduleInterest();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    @Override
    public void onDisable() {
        interest.saveRemainingTime();
        getLogger().info("Interest time saved.");
        getLogger().info("PolarEconomy Disabled");
    }

    public FileConfiguration loadConfig() {
        try {
            FileConfiguration config = this.getConfig();
            config.options().copyDefaults(true);
            saveConfig();
            MessageUtil.loadMessages(config);
            getLogger().info("Configuration loaded successfully.");
            return config;
        } catch (Exception e) {
            getLogger().severe("Failed to load configuration.");
            e.printStackTrace();
            return null;
        }

    }
}
