package me.polardyth.polareconomy.commands;

import me.polardyth.polareconomy.utils.EconomyManager;
import me.polardyth.polareconomy.utils.MessageUtil;
import me.polardyth.polareconomy.utils.config.SettingsManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BalanceCommand implements CommandExecutor {

    private final EconomyManager economyManager;
    private final FileConfiguration config;

    public BalanceCommand(EconomyManager economyManager) {
        this.economyManager = economyManager;
        SettingsManager configFiles = economyManager.getConfigs();
        this.config = configFiles.getConfig("config");

    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (!(commandSender instanceof Player player)) {
            commandSender.sendRichMessage(MessageUtil.getNotPlayerMessage());
            return true;
        }

        double balance = economyManager.getBalance(player.getUniqueId());
        player.sendRichMessage(config.getString("balance.balance-message").replace("{balance}", Double.toString(balance)));
        return true;
     }
}
