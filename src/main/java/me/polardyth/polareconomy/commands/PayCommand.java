package me.polardyth.polareconomy.commands;

import me.polardyth.polareconomy.utils.EconomyManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PayCommand implements CommandExecutor {

    private final EconomyManager economyManager;
    private final FileConfiguration config;

    public PayCommand(EconomyManager economyManager) {
        this.economyManager = economyManager;
        config = economyManager.getSettingsManager().getConfig();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (!(commandSender instanceof Player player)) {
            commandSender.sendMessage("Only a player can use this command");
            return true;
        }

        if (strings.length != 2) {
            commandSender.sendMessage("Usage: /pay <player> <amount>");
            return true;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(strings[0]);
        double amount;

        try {
            amount = Double.parseDouble(strings[1]);
        } catch (NumberFormatException e) {
            player.sendMessage("Invalid amount.");
            return true;
        }

        if (amount <= 0) {
            player.sendMessage("Amount must be positive");
        }

        if (economyManager.removeBalance(player.getUniqueId(), amount)) {
            economyManager.addBalance(target.getUniqueId(), amount);
            player.sendMessage(config.getString("pay.success-to-player").replace("{target}", target.getName()).replace("{amount}", Double.toString(amount)));
            if (target.isOnline()) {
                ((Player) target).sendMessage(config.getString("pay.success-to-target").replace("{player}", player.getName()).replace("{amount}", Double.toString(amount)));
            }
        } else {
            player.sendMessage("You do not have the required funds.");
        }

        return true;
    }
}
