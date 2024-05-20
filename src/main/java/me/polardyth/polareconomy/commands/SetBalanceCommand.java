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
        config =
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (!commandSender.hasPermission("polareconomy.setbalance")) {
            commandSender.sendMessage("You do not have permission to use this command.");
            return true;
        }

        if (strings.length != 2) {
            commandSender.sendMessage("Usage: /setbalance <player> <amount>");
            return true;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(strings[0]);
        double amount;

        try {
            amount = Double.parseDouble(strings[1]);
        } catch (NumberFormatException e) {
            commandSender.sendMessage("Invalid amount.");
            return true;
        }

        if (amount < 0) {
            commandSender.sendMessage("Amount cannot be negative");
            return true;
        }

        economyManager.setBalance(target.getUniqueId(), amount);
        commandSender.sendMessage("Set " + target.getName() + "'s balance to " + amount + ".");
        if (target.isOnline()) {
            ((Player) target).sendMessage("Your balance has been set to " + amount + ".");
        }

        return true;
    }
}
