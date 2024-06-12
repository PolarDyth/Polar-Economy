package me.polardyth.polareconomy;

import me.polardyth.polareconomy.commands.BalanceCommand;
import me.polardyth.polareconomy.commands.BankCommand;
import me.polardyth.polareconomy.commands.PayCommand;
import me.polardyth.polareconomy.commands.SetBalanceCommand;
import me.polardyth.polareconomy.economy.balances.BalanceType;
import me.polardyth.polareconomy.economy.balances.Bank;
import me.polardyth.polareconomy.economy.EconomyManager;
import me.polardyth.polareconomy.economy.balances.Purse;
import me.polardyth.polareconomy.listeners.MenuListener;
import me.polardyth.polareconomy.economy.interest.Interest;
import me.polardyth.polareconomy.listeners.PlayerJoinBalanceCorrection;
import me.polardyth.polareconomy.systems.transactionhistory.TransactionHandler;
import me.polardyth.polareconomy.utils.MessageUtil;
import me.polardyth.polareconomy.utils.files.ConfigFiles;
import me.polardyth.polareconomy.utils.files.DataFiles;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class PolarEconomy extends JavaPlugin implements Listener {

    private Interest interest;


    @Override
    public void onEnable() {

        PolarSettings.onLoad(this);

        ConfigFiles configFiles = new ConfigFiles("banker", "config", "interest");
        DataFiles dataFiles = new DataFiles("balances", "interest", "transactions");

        PolarSettings.loadFiles(dataFiles, configFiles);

        FileConfiguration config = configFiles.getFile("config").getConfig();
        FileConfiguration interestConfig = configFiles.getFile("interest").getConfig();

        EconomyManager economyManager = new EconomyManager(new Purse(dataFiles.getFile("balances"), "purse"),
                new Bank(dataFiles.getFile("balances"), "bank"));

        PolarSettings.loadEconomy(economyManager, new TransactionHandler(dataFiles.getFile("transactions")));
        getCommand("balance").setExecutor(new BalanceCommand(economyManager.getBalanceManager(BalanceType.PURSE)));
        getCommand("pay").setExecutor(new PayCommand(economyManager.getBalanceManager(BalanceType.PURSE)));
        getCommand("setbalance").setExecutor(new SetBalanceCommand(economyManager));
        getCommand("bank").setExecutor(new BankCommand(economyManager));

        getServer().getPluginManager().registerEvents(new MenuListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinBalanceCorrection(dataFiles.getFile("balances").getConfig(), economyManager), this);

        getLogger().info("PolarEconomy enabled!");
        this.getServer().getScheduler().scheduleSyncDelayedTask(this, () -> {
            assert config != null;
            MessageUtil.loadMessages(config);
            getLogger().info("Messages loaded successfully.");
        });

        if (interestConfig.getBoolean("interest.enabled")) {
            try {
                interest = new Interest(economyManager, dataFiles.getFile("interest"), interestConfig);
                interest.scheduleInterest();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        getServer().getScheduler().scheduleSyncRepeatingTask(this, interest::saveRemainingTime, 0L, 20L);

    }
    @Override
    public void onDisable() {
        interest.saveRemainingTime();
        getLogger().info("Interest time saved.");
        getLogger().info("PolarEconomy Disabled");
    }
}
