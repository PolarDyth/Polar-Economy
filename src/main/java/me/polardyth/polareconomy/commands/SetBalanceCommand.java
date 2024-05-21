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

public class SetBalanceCommand implements CommandExecutor {

    private final EconomyManager economyManager;
    private final FileConfiguration config;

    public SetBalanceCommand(EconomyManager economyManager) {
        this.economyManager = economyManager;
        config = economyManager.getSettingsManager().getConfig();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (!commandSender.hasPermission("polareconomy.setbalance")) {
            commandSender.sendMessage(config.getString("error-messages.permission.error"));
            return true;
        }

        if (strings.length != 2) {
            commandSender.sendMessage("Usage: /setbalance <player> <amount>");
            return true;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(strings[0]);
        double amount;
        boolean successMessage = config.getBoolean("setbalance.send-message-to-target");

        try {
            amount = Double.parseDouble(strings[1]);
        } catch (NumberFormatException e) {
            commandSender.sendMessage(config.getString("error-messages.not-number"));
            return true;
        }

        if (amount < 0) {
            commandSender.sendMessage(config.getString("error-messages.negative-number"));
            return true;
        }

        economyManager.setBalance(target.getUniqueId(), amount);
        commandSender.sendMessage(config.getString("set-balance.success-to-player").replace("{target}", target.getName()).replace("{amount}", Double.toString(amount)));
        if (target.isOnline() && successMessage) {
            ((Player) target).sendMessage(config.getString("set-balance.success-to-target").replace("{amount}", Double.toString(amount)));
        }

        return true;
    }
}
