package me.polardyth.polareconomy.commands;

import me.polardyth.polareconomy.utils.EconomyManager;
import me.polardyth.polareconomy.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SetBalanceCommand implements CommandExecutor {

    private final EconomyManager economyManager;

    public SetBalanceCommand(EconomyManager economyManager) {
        this.economyManager = economyManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!commandSender.hasPermission("polareconomy.setbalance")) {
            commandSender.sendRichMessage(MessageUtil.getPermissionErrorMessage());
            return true;
        }

        if (strings.length != 2) {
            commandSender.sendRichMessage(MessageUtil.getUsages("setbalance"));
            return true;
        }

        String playerName = strings[0];
        OfflinePlayer target = Bukkit.getOfflinePlayerIfCached(playerName);
        if (target == null || !target.hasPlayedBefore()) {
            commandSender.sendRichMessage(MessageUtil.getPlayerNotFoundMessage());
            return true;
        }

        double amount;
        try {
            amount = Double.parseDouble(strings[1]);
        } catch (NumberFormatException e) {
            commandSender.sendRichMessage(MessageUtil.getNotNumberMessage());
            Logger.getLogger("Minecraft").log(Level.WARNING, "Failed to parse amount: " + strings[1]);
            return true;
        }

        if (amount < 0) {
            commandSender.sendRichMessage(MessageUtil.getNegativeNumberMessage());
            return true;
        }

        economyManager.setBalance(target.getUniqueId(), amount);
        commandSender.sendRichMessage(MessageUtil.getSuccessToPlayerMessage().replace("{target}", target.getName()).replace("{amount}", Double.toString(amount)));

        if (target.isOnline() && MessageUtil.isSendSuccessMessageToTarget()) {
            ((Player) target).sendRichMessage(MessageUtil.getSuccessToTargetMessage().replace("{amount}", Double.toString(amount)).replace("{target}", target.getName()));
        }

        return true;
    }
}
