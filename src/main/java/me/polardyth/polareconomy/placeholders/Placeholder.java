package me.polardyth.polareconomy.placeholders;

import me.polardyth.polareconomy.PolarSettings;
import me.polardyth.polareconomy.economy.balances.BalanceType;
import me.polardyth.polareconomy.economy.balances.parents.BalanceManager;
import me.polardyth.polareconomy.economy.balances.parents.StoredMoneyManager;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public class Placeholder {

    private static final BalanceManager purse = PolarSettings.getEconomyManager().getBalanceManager(BalanceType.PURSE);
    private static final StoredMoneyManager bank = PolarSettings.getEconomyManager().getStoredMoneyManager(BalanceType.BANK);
    private static final FileConfiguration interest = PolarSettings.getConfigFiles().getFile("interest").getConfig();
    private static final FileConfiguration interestData = PolarSettings.getDataFiles().getFile("interest").getConfig();

    public static String applyPlaceholders(@NotNull final OfflinePlayer player, @NotNull final String text) {

        return text
                .replace("{player}", player.getName())
                .replace("{player_uuid}", player.getUniqueId().toString())
                .replace("{purse_balance}", Format.formatCurrency(purse.getBalance(player.getUniqueId())))
                .replace("{purse_balance_formatted}", Format.formatCurrencyUnits(purse.getBalance(player.getUniqueId())))
                .replace("{bank_balance}", Format.formatCurrency(bank.getBalance(player.getUniqueId())))
                .replace("{bank_balance_formatted}", Format.formatCurrencyUnits(bank.getBalance(player.getUniqueId())))
                .replace("{interest_rate}", Double.toString(interest.getDouble("interest.rate")))
                .replace("{interest_interval}", Integer.toString(interest.getInt("interest.interval")))
                .replace("{interest_time}", Format.formatTime(interestData.getLong("time-until-interest")));
    }

    public static String applyPlaceholders(@NotNull OfflinePlayer player, @NotNull long amount, @NotNull String text) {

        String newText = applyPlaceholders(player, text);

        return newText
                .replace("{amount}", Format.formatCurrency(amount))
                .replace("{amount_formatted}", Format.formatCurrencyUnits(amount));
    }
}
