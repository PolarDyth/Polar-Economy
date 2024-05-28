package me.polardyth.polareconomy.commands;

import me.polardyth.polareconomy.utils.EconomyManager;
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

public class PayCommand implements CommandExecutor {


    private final EconomyManager economyManager;
    private final FileConfiguration config;

    public PayCommand(EconomyManager economyManager) {
        this.economyManager = economyManager;
        SettingsManager configFiles = economyManager.getConfigs();
        config = configFiles.getConfig("config");
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

        if (hasTooManyDecimals(amount)) {
            player.sendRichMessage(MessageUtil.getTooManyDecimalsMessage());
            return true;
        }

        if (economyManager.removePurseBalance(player.getUniqueId(), amount)) {
            economyManager.addPurseBalance(target.getUniqueId(), amount);
            player.sendRichMessage(config.getString("pay.payment-success").replace("{target}", targetName).replace("{amount}", Double.toString(amount)));
            if (target.isOnline()) {
                ((Player) target).sendMessage(config.getString("pay.payment-received").replace("{player}", player.getName()).replace("{amount}", Double.toString(amount)));
            }
        } else {
            player.sendRichMessage(config.getString("pay.error.insufficient-funds"));
        }

        return true;
    }

    private boolean hasTooManyDecimals(double amount) {
        String text = Double.toString(amount);
        int integerPlace = text.indexOf('.');
        int decimalPlace = text.length() - integerPlace - 1;

        if (decimalPlace > 2) {
            return true;
        }

        return false;
    }
}
