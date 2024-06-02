package me.polardyth.polareconomy.economy.balances.parents;

import me.polardyth.polareconomy.PolarSettings;
import me.polardyth.polareconomy.economy.balances.interfaces.IBalanceManager;
import me.polardyth.polareconomy.economy.balances.interfaces.IStoredMoney;
import me.polardyth.polareconomy.placeholders.Placeholder;
import me.polardyth.polareconomy.utils.MessageUtil;
import me.polardyth.polareconomy.utils.config.interfaces.FileHandler;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public abstract class StoredMoneyManager extends BalanceManager implements IStoredMoney {

    private static final FileConfiguration config = PolarSettings.getConfigFiles().getFile("banker").getConfig();

    public StoredMoneyManager(FileHandler dataFile, String target) {
        super(dataFile, target);
    }

    public void withdrawFunds(long amount, Player player, IBalanceManager to) {
        // Withdraw funds from a player's bank account

        if (amount < 0) {
            player.sendRichMessage(MessageUtil.getNegativeNumberMessage());
            return;
        }

        if (amount == 0) {
            player.sendRichMessage("<red>You have no coins!");
            return;
        }

        transferFunds(amount, player, this, to);
        player.sendRichMessage(Placeholder.applyPlaceholders(player, amount, config.getString("messages.withdraw")));
    }

    public void depositFunds(long amount, Player player, IBalanceManager from) {
        // Deposit funds into a player's bank account

        if (amount < 0) {
            player.sendRichMessage(MessageUtil.getNegativeNumberMessage());
            return;
        }

        if (amount == 0) {
            player.sendRichMessage("<red>You have no coins!");
            return;
        }

        transferFunds(amount, player, from, this);
        player.sendRichMessage(Placeholder.applyPlaceholders(player, amount, config.getString("messages.deposit")));
    }

    private void transferFunds(long amount, Player player, IBalanceManager from, IBalanceManager to) {
        // Transfer funds between two players

        from.removeBalance(player, amount);
        to.addBalance(player.getUniqueId(), amount);
    }
}
