package me.polardyth.polareconomy.commands;

import me.polardyth.polareconomy.PolarSettings;
import me.polardyth.polareconomy.economy.balances.parents.BalanceManager;
import me.polardyth.polareconomy.systems.transactionhistory.TransactionType;
import me.polardyth.polareconomy.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PayCommand implements CommandExecutor {


    private final BalanceManager purse;
    private final FileConfiguration config;

    public PayCommand(BalanceManager purse) {
        this.purse = purse;
        config = PolarSettings.getPlugin().getConfig();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (!(commandSender instanceof Player player)) {
            commandSender.sendRichMessage(MessageUtil.getNotPlayerMessage());
            return true;
        }

        if (strings.length != 2) {
            player.sendRichMessage(config.getString("pay.usage"));
            return true;
        }

        String targetName = strings[0];
        if (player.getName().equalsIgnoreCase(targetName)) {
            player.sendRichMessage(config.getString("pay.error.self-payment"));
            return true;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayerIfCached(targetName);
        if (target == null || !target.hasPlayedBefore()) {
            player.sendRichMessage(MessageUtil.getPlayerNotFoundMessage());
            return true;
        }

        int amount;
        try {
            amount = Integer.parseInt(strings[1]);
        } catch (NumberFormatException e) {
            player.sendRichMessage(MessageUtil.getNotNumberMessage());
            return true;
        }

        purse.removeBalance(player, amount, TransactionType.TRANSFER, player.getUniqueId().toString(), target.getUniqueId().toString());
        purse.addBalance(target.getUniqueId(), amount, TransactionType.TRANSFER, target.getUniqueId().toString(), player.getUniqueId().toString());
        player.sendRichMessage(config.getString("pay.payment-success").replace("{target}", targetName).replace("{amount}", Double.toString(amount)));
        if (target.isOnline()) {
            ((Player) target).sendMessage(config.getString("pay.payment-received").replace("{player}", player.getName()).replace("{amount}", Double.toString(amount)));
        }

        return true;
    }
}
