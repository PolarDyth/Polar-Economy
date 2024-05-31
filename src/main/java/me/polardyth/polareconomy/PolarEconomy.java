package me.polardyth.polareconomy;

import me.polardyth.polareconomy.commands.BalanceCommand;
import me.polardyth.polareconomy.commands.BankCommand;
import me.polardyth.polareconomy.commands.PayCommand;
import me.polardyth.polareconomy.commands.SetBalanceCommand;
import me.polardyth.polareconomy.listeners.MenuListener;
import me.polardyth.polareconomy.systems.Interest;
import me.polardyth.polareconomy.utils.economy.Bank;
import me.polardyth.polareconomy.utils.economy.EconomyManager;
import me.polardyth.polareconomy.utils.MessageUtil;
import me.polardyth.polareconomy.utils.config.SettingsManager;
import me.polardyth.polareconomy.utils.economy.Purse;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class PolarEconomy extends JavaPlugin implements Listener {

    private Interest interest;


    @Override
    public void onEnable() {

        PolarSettings.onLoad(this);

        SettingsManager configFiles = new SettingsManager(false, "banker", "config", "interest");
        SettingsManager dataFiles = new SettingsManager(true, "balances", "interest");

        FileConfiguration config = configFiles.getConfig("config");
        FileConfiguration interestConfig = configFiles.getConfig("interest");

        EconomyManager economyManager = new EconomyManager(new Purse(dataFiles.getConfig("balances"), "purse"),
                new Bank(dataFiles.getConfig("balances"), "bank"));
        getCommand("balance").setExecutor(new BalanceCommand(economyManager));
        getCommand("pay").setExecutor(new PayCommand(economyManager));
        getCommand("setbalance").setExecutor(new SetBalanceCommand(economyManager));
        getCommand("bank").setExecutor(new BankCommand(economyManager));

        getServer().getPluginManager().registerEvents(new MenuListener(), this);

        getLogger().info("PolarEconomy enabled!");
        this.getServer().getScheduler().scheduleSyncDelayedTask(this, () -> {
            assert config != null;
            MessageUtil.loadMessages(config);
            getLogger().info("Messages loaded successfully.");
        });

        if (interestConfig.getBoolean("interest.enabled")) {
            try {
                interest = new Interest(economyManager, this);
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
}
