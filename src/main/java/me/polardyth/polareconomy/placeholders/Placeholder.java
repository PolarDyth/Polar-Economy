package me.polardyth.polareconomy.placeholders;

import me.polardyth.polareconomy.PolarSettings;
import me.polardyth.polareconomy.economy.balances.BalanceType;
import me.polardyth.polareconomy.economy.balances.interfaces.IBalanceManager;
import me.polardyth.polareconomy.economy.balances.interfaces.IStoredMoney;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

public class Placeholder {

    private static final IBalanceManager purse = PolarSettings.getEconomyManager().getBalanceManager(BalanceType.PURSE);
    private static final IStoredMoney bank = PolarSettings.getEconomyManager().getStoredMoneyManager(BalanceType.BANK);
    private static final FileConfiguration interest = PolarSettings.getConfigFiles().getFile("interest").getConfig();


    public static String applyPlaceholders(@NotNull final OfflinePlayer player, @NotNull final String text) {

        return text
                .replace("{player}", player.getName())
                .replace("{player_uuid}", player.getUniqueId().toString())
                .replace("{purse_balance}", Format.formatCurrency(purse.getBalance(player.getUniqueId())))
                .replace("{purse_balance_formatted}", Format.formatCurrencyUnits(purse.getBalance(player.getUniqueId())))
                .replace("{bank_balance}", Format.formatCurrency(bank.getBalance(player.getUniqueId())))
                .replace("{bank_balance_formatted}", Format.formatCurrencyUnits(bank.getBalance(player.getUniqueId())))
                .replace("{interest_rate}", Double.toString(interest.getDouble("interest.rate")))
                .replace("{interest_interval}", Integer.toString(interest.getInt("interest.interval")));
    }

    public static String applyPlaceholders(@NotNull OfflinePlayer player, @NotNull long amount, @NotNull String text) {

        String newText = applyPlaceholders(player, text);

        return newText
                .replace("{amount}", Format.formatCurrency(amount))
                .replace("{amount_formatted}", Format.formatCurrencyUnits(amount));
    }

    private void dynamicInterest(String text) {

        if (text.contains("{interest_time}")) {
            PolarSettings.getPlugin().getServer().getScheduler().scheduleSyncRepeatingTask(PolarSettings.getPlugin(), () -> {
                long timeUntilInterest = interest.getLong("time-until-interest");
                text.replace("{interest_time}", Format.formatTime(timeUntilInterest));
            }, 0L, 20L);
        }
    }
}
