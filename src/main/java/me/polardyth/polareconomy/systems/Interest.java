package me.polardyth.polareconomy.systems;

import me.polardyth.polareconomy.utils.EconomyManager;
import me.polardyth.polareconomy.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.logging.Logger;

public class Interest {

    private final EconomyManager economyManager;
    private final Plugin plugin;

    public Interest(EconomyManager economyManager, Plugin plugin) {
        this.economyManager = economyManager;
        this.plugin = plugin;
    }

    public void scheduleInterest() {
        int interval = MessageUtil.getInterestInterval();
        if (economyManager.getSettingsManager().getInterestConfig().getLong("time-until-interest") > 0) {
            long timeUntilInterest = economyManager.getSettingsManager().getInterestConfig().getLong("time-until-interest");
            plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, this::applyInterest, timeUntilInterest / 1000L * 20L, interval * 20L);
            return;
        } else {
            economyManager.getSettingsManager().getInterestConfig().set("time-until-interest", interval * 1000L);
            economyManager.getSettingsManager().saveInterestConfig();
        }
        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, this::applyInterest, interval * 20L, interval * 20L);
    }

    public void saveRemainingTime() {
        long lastInterestTime = economyManager.getSettingsManager().getInterestConfig().getLong("last-interest-time");
        long getCurrentTime = System.currentTimeMillis();
        int interval = MessageUtil.getInterestInterval();
        long intervalMillis = interval * 1000L;
        long timeUntilNextInterest = (lastInterestTime + intervalMillis) - getCurrentTime;
        economyManager.getSettingsManager().getInterestConfig().set("time-until-interest", timeUntilNextInterest);
        economyManager.getSettingsManager().saveInterestConfig();
    }

    private void saveLastInterestTime() {
        economyManager.getSettingsManager().getInterestConfig().set("last-interest-time", System.currentTimeMillis());
        economyManager.getSettingsManager().saveInterestConfig();
    }

    private void applyInterest() {
        double interestRate = MessageUtil.getInterestRate();

        for (Player player : Bukkit.getOnlinePlayers()) {
            double balance = economyManager.getBalance(player.getUniqueId());
            double interest = balance * (interestRate / 100);
            economyManager.addBalance(player.getUniqueId(), interest);
            player.sendRichMessage(MessageUtil.getInterestMessage().replace("{amount}", Double.toString(interest)));
        }

        saveLastInterestTime();
        Logger.getLogger("Minecraft").info("Interest applied to all players.");
    }
}
