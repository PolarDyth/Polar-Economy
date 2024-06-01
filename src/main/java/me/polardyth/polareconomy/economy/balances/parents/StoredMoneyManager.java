package me.polardyth.polareconomy.economy.balances.parents;

import me.polardyth.polareconomy.economy.balances.interfaces.IBalanceManager;
import me.polardyth.polareconomy.economy.balances.interfaces.IStoredMoney;
import me.polardyth.polareconomy.utils.MessageUtil;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public abstract class StoredMoneyManager extends BalanceManager implements IStoredMoney {

    public StoredMoneyManager(FileConfiguration dataFile, String target) {
        super(dataFile, target);
    }

    public void withdrawFunds(int amount, Player player, IBalanceManager to) {
        // Withdraw funds from a player's bank account

        transferFunds(amount, player, this, to);
        player.sendRichMessage("<green>Successfully withdrew " + amount);
    }

    public void depositFunds(int amount, Player player, IBalanceManager from) {
        // Deposit funds into a player's bank account

        transferFunds(amount, player, from, this);
        player.sendRichMessage("<green>Successfully deposited " + amount);
    }

    private void transferFunds(int amount, Player player, IBalanceManager from, IBalanceManager to) {
        // Transfer funds between two players

        if (amount <= 0) {
            player.sendRichMessage(MessageUtil.getNegativeNumberMessage());
            return;
        }

        from.removeBalance(player, amount);
        to.addBalance(player.getUniqueId(), amount);
    }
}
