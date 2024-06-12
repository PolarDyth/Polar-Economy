package me.polardyth.polareconomy.economy.balances.parents;

import me.polardyth.polareconomy.PolarSettings;
import me.polardyth.polareconomy.placeholders.Placeholder;
import me.polardyth.polareconomy.systems.transactionhistory.TransactionSource;
import me.polardyth.polareconomy.systems.transactionhistory.TransactionType;
import me.polardyth.polareconomy.utils.MessageUtil;
import me.polardyth.polareconomy.utils.files.interfaces.FileHandler;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public abstract class StoredMoneyManager extends BalanceManager {

    private static final FileConfiguration config = PolarSettings.getConfigFiles().getFile("banker").getConfig();
    private final TransactionSource source;

    public StoredMoneyManager(FileHandler dataFile, String target, TransactionSource source) {
        super(dataFile, target);
        this.source = source;
    }

    public void withdrawFunds(long amount, Player player, BalanceManager to) {
        // Withdraw funds from a player's bank account

        if (amount < 0) {
            player.sendRichMessage(MessageUtil.getNegativeNumberMessage());
            return;
        }

        if (amount == 0) {
            player.sendRichMessage("<red>You have no coins!");
            return;
        }

        transferFunds(amount, player, this, to, source);
        player.sendRichMessage(Placeholder.applyPlaceholders(player, amount, config.getString("messages.withdraw")));
    }

    public void depositFunds(long amount, Player player, BalanceManager from) {
        // Deposit funds into a player's bank account

        if (amount < 0) {
            player.sendRichMessage(MessageUtil.getNegativeNumberMessage());
            return;
        }

        if (amount == 0) {
            player.sendRichMessage("<red>You have no coins!");
            return;
        }

        transferFunds(amount, player, from, this, TransactionSource.PURSE);
        player.sendRichMessage(Placeholder.applyPlaceholders(player, amount, config.getString("messages.deposit")));
    }

    private void transferFunds(long amount, Player player, BalanceManager from, BalanceManager to, TransactionSource source) {
        // Transfer funds between two players

        from.removeBalance(player, amount, TransactionType.WITHDRAW, from.getType().toString(), to.getType().toString());
        to.addBalance(player.getUniqueId(), amount, TransactionType.DEPOSIT, to.getType().toString(), from.getType().toString());
    }
}
