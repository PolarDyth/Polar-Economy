package me.polardyth.polareconomy.economy.interest;

import me.polardyth.polareconomy.PolarSettings;
import me.polardyth.polareconomy.economy.balances.BalanceType;
import me.polardyth.polareconomy.economy.balances.interfaces.IEconomyManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.logging.Logger;

public class Interest {

    private final IEconomyManager economyManager;
    private final Plugin plugin = PolarSettings.getPlugin();
    private final FileConfiguration dataFile;
    private final FileConfiguration configFile;
    private final int intervalInterest;

    public Interest(IEconomyManager economyManager, FileConfiguration dataFile, FileConfiguration configFile) {
        this.economyManager = economyManager;
        this.dataFile = dataFile;
        this.configFile = configFile;

        intervalInterest = configFile.getInt("interest.interval");
    }

    public void scheduleInterest() {
        if (dataFile.getLong("time-until-interest") > 0) {
            long timeUntilInterest = dataFile.getLong("time-until-interest");
            plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, this::applyInterest, timeUntilInterest / 1000L * 20L, intervalInterest * 20L);
            return;
        } else {
            dataFile.set("time-until-interest", intervalInterest * 1000L);
        }
        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, this::applyInterest, intervalInterest * 20L, intervalInterest * 20L);
    }

    public void saveRemainingTime() {
        long lastInterestTime = dataFile.getLong("last-interest-time");
        long getCurrentTime = System.currentTimeMillis();
        long intervalMillis = intervalInterest * 1000L;
        long timeUntilNextInterest = (lastInterestTime + intervalMillis) - getCurrentTime;
        dataFile.set("time-until-interest", timeUntilNextInterest);
    }

    private void saveLastInterestTime() {
        dataFile.set("last-interest-time", System.currentTimeMillis());
    }

    private void applyInterest() {
        int interestRate = configFile.getInt("interest.rate");

        for (Player player : Bukkit.getOnlinePlayers()) {
            int balance = economyManager.getBalanceManager(BalanceType.BANK).getBalance(player.getUniqueId());
            int interest = balance * (interestRate / 100);
            Logger.getLogger("Minecraft").info("debug: " + interest);
            economyManager.getBalanceManager(BalanceType.BANK).setBalance(player.getUniqueId(), interest);
            player.sendRichMessage(configFile.getString("interest.message").replace("{amount}", Integer.toString(interest)));
        }

        saveLastInterestTime();
        Logger.getLogger("Minecraft").info("Interest applied to all players.");
    }
}
