package me.polardyth.polareconomy.commands;

import me.polardyth.polareconomy.PolarSettings;
import me.polardyth.polareconomy.economy.balances.BalanceType;
import me.polardyth.polareconomy.economy.balances.interfaces.IBalanceManager;
import me.polardyth.polareconomy.economy.balances.interfaces.IEconomyManager;
import me.polardyth.polareconomy.utils.MessageUtil;
import me.polardyth.polareconomy.utils.config.SettingsManager;
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

    private final FileConfiguration config;
    private final IBalanceManager purse;

    public SetBalanceCommand(IEconomyManager economyManager) {
        purse = economyManager.getBalanceManager(BalanceType.PURSE);
        config = PolarSettings.getConfigFiles().getConfig("config");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!commandSender.hasPermission("polareconomy.setbalance")) {
            commandSender.sendRichMessage(MessageUtil.getPermissionErrorMessage());
            return true;
        }

        if (strings.length != 2) {
            commandSender.sendRichMessage(config.getString("set-balance.usage"));
            return true;
        }

        String playerName = strings[0];
        OfflinePlayer target = Bukkit.getOfflinePlayerIfCached(playerName);
        if (target == null || !target.hasPlayedBefore()) {
            commandSender.sendRichMessage(MessageUtil.getPlayerNotFoundMessage());
            return true;
        }

        int amount;
        try {
            amount = Integer.parseInt(strings[1]);
        } catch (NumberFormatException e) {
            commandSender.sendRichMessage(MessageUtil.getNotNumberMessage());
            Logger.getLogger("Minecraft").log(Level.WARNING, "Failed to parse amount: " + strings[1]);
            return true;
        }

        purse.setBalance(target.getUniqueId(), amount);
        commandSender.sendRichMessage(config.getString("set-balance.success-to-player").replace("{amount}", Double.toString(amount)).replace("{target}", target.getName()));

        if (target.isOnline() && config.getBoolean("set-balance.send-message-to-target")) {
            ((Player) target).sendRichMessage(config.getString("set-balance.success-to-target").replace("{amount}", Double.toString(amount)));
        }

        return true;
    }
}
