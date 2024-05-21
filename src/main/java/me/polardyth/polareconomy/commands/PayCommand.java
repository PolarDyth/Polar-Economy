package me.polardyth.polareconomy.commands;

import me.polardyth.polareconomy.utils.EconomyManager;
import me.polardyth.polareconomy.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PayCommand implements CommandExecutor {


    private final EconomyManager economyManager;

    public PayCommand(EconomyManager economyManager) {
        this.economyManager = economyManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (!(commandSender instanceof Player player)) {
            commandSender.sendRichMessage(MessageUtil.getNotPlayerMessage());
            return true;
        }

        if (strings.length != 2) {
            player.sendRichMessage(MessageUtil.getUsages("pay"));
            return true;
        }

        String targetName = strings[0];
        if (player.getName().equalsIgnoreCase(targetName)) {
            player.sendRichMessage(MessageUtil.getSelfPaymentErrorMessage());
            return true;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayerIfCached(targetName);
        if (target == null || !target.hasPlayedBefore()) {
            player.sendRichMessage(MessageUtil.getPlayerNotFoundMessage());
            return true;
        }

        double amount;
        try {
            amount = Double.parseDouble(strings[1]);
        } catch (NumberFormatException e) {
            player.sendRichMessage(MessageUtil.getNotNumberMessage());
            return true;
        }

        if (amount <= 0) {
            player.sendRichMessage(MessageUtil.getNegativeNumberMessage());
            return true;
        }

        if (economyManager.removeBalance(player.getUniqueId(), amount)) {
            economyManager.addBalance(target.getUniqueId(), amount);
            player.sendRichMessage(MessageUtil.getPaymentSuccessMessage(target.getName(), amount));
            if (target.isOnline()) {
                ((Player) target).sendMessage(MessageUtil.getPaymentReceivedMessage(player.getName(), amount));
            }
        } else {
            player.sendRichMessage(MessageUtil.getInsufficientFundsMessage());
        }

        return true;
    }
}
