package me.polardyth.polareconomy.systems;

import me.polardyth.polareconomy.utils.EconomyManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.text.DecimalFormat;
import java.util.logging.Logger;

public class Interest {

    private final EconomyManager economyManager;
    private final Plugin plugin;
    private final FileConfiguration dataFile;
    private final FileConfiguration configFile;
    private final int intervalInterest;

    public Interest(EconomyManager economyManager, Plugin plugin) {
        this.economyManager = economyManager;
        this.plugin = plugin;
        dataFile = economyManager.getData().getConfig("interest");
        configFile = economyManager.getConfigs().getConfig("interest");

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
        double interestRate = configFile.getDouble("interest.rate");
        final DecimalFormat df = new DecimalFormat("0.00");

        for (Player player : Bukkit.getOnlinePlayers()) {
            double balance = economyManager.getPurseBalance(player.getUniqueId());
            double interest = balance * (interestRate / 100);
            double interestRounded = Double.parseDouble(df.format(interest));
            Logger.getLogger("Minecraft").info("debug: " + interestRounded);
            economyManager.addPurseBalance(player.getUniqueId(), interestRounded);
            player.sendRichMessage(configFile.getString("interest.message").replace("{amount}", Double.toString(interestRounded)));
        }

        saveLastInterestTime();
        Logger.getLogger("Minecraft").info("Interest applied to all players.");
    }
}
