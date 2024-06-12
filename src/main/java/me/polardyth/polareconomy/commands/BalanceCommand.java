package me.polardyth.polareconomy.commands;

import me.polardyth.polareconomy.PolarSettings;
import me.polardyth.polareconomy.economy.balances.parents.BalanceManager;
import me.polardyth.polareconomy.utils.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BalanceCommand implements CommandExecutor {

    private final BalanceManager purse;
    private final FileConfiguration config;

    public BalanceCommand(BalanceManager purse) {
        this.purse = purse;
        config = PolarSettings.getPlugin().getConfig();

    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (!(commandSender instanceof Player player)) {
            commandSender.sendRichMessage(MessageUtil.getNotPlayerMessage());
            return true;
        }

        long balance = purse.getBalance(player.getUniqueId());
        player.sendRichMessage(config.getString("balance.balance-message").replace("{balance}", Long.toString(balance)));
        return true;
     }
}
