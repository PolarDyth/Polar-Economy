package me.polardyth.polareconomy.economy.interest;

import me.polardyth.polareconomy.PolarSettings;
import me.polardyth.polareconomy.economy.balances.BalanceType;
import me.polardyth.polareconomy.economy.balances.interfaces.IEconomyManager;
import me.polardyth.polareconomy.utils.config.interfaces.FileHandler;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.logging.Logger;

public class Interest {

    private final IEconomyManager economyManager;
    private final Plugin plugin = PolarSettings.getPlugin();
    private final FileHandler dataFile;
    private final FileConfiguration dataConfig;
    private final FileConfiguration configFile;
    private final int intervalInterest;

    public Interest(IEconomyManager economyManager, FileHandler dataFile, FileConfiguration configFile) {
        this.economyManager = economyManager;
        this.dataFile = dataFile;
        this.configFile = configFile;
        dataConfig = dataFile.getConfig();

        intervalInterest = configFile.getInt("interest.interval");
    }

    public void scheduleInterest() {
        if (dataConfig.getLong("time-until-interest") > 0) {
            long timeUntilInterest = dataConfig.getLong("time-until-interest");
            plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, this::applyInterest, timeUntilInterest / 1000L * 20L, intervalInterest * 20L);
            return;
        } else {
            dataConfig.set("last-interest-time", System.currentTimeMillis());
            dataConfig.set("time-until-interest", intervalInterest * 1000L);
            dataFile.saveFile();
        }
        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, this::applyInterest, intervalInterest * 20L, intervalInterest * 20L);
    }

    public void saveRemainingTime() {
        long lastInterestTime = dataConfig.getLong("last-interest-time");
        long getCurrentTime = System.currentTimeMillis();
        long intervalMillis = intervalInterest * 1000L;
        long timeUntilNextInterest = (lastInterestTime + intervalMillis) - getCurrentTime;
        dataConfig.set("time-until-interest", timeUntilNextInterest);
        dataFile.saveFile();
    }

    private void saveLastInterestTime() {
        dataConfig.set("last-interest-time", System.currentTimeMillis());
        dataFile.saveFile();
    }

    private void applyInterest() {
        int interestRate = configFile.getInt("interest.rate");

        for (Player player : Bukkit.getOnlinePlayers()) {
            long balance = economyManager.getBalanceManager(BalanceType.BANK).getBalance(player.getUniqueId());
            Logger.getLogger("Minecraft").info("debug: " + balance);
            long interest = Math.round(balance * ((float) interestRate / 100));
            economyManager.getBalanceManager(BalanceType.BANK).setBalance(player.getUniqueId(), balance + interest);
            player.sendRichMessage(configFile.getString("interest.message").replace("{amount}", Long.toString(interest)));
        }

        saveLastInterestTime();
        Logger.getLogger("Minecraft").info("Interest applied to all players.");
    }

    public String getTimeUntilInterest() {
        long timeUntilInterestMillis = dataConfig.getLong("time-until-interest");
        long timeUntilInterestSeconds = timeUntilInterestMillis / 1000;
        long minutes = timeUntilInterestSeconds / 60;
        long seconds = timeUntilInterestSeconds % 60;
        return String.format("%d minutes, %d seconds", minutes, seconds);
    }
}
