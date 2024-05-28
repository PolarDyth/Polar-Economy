package me.polardyth.polareconomy.utils;

import org.bukkit.entity.Player;

import java.util.logging.Level;
import java.util.logging.Logger;

public class BankBalanceEditor {

    private final Player player;
    private final EconomyManager economyManager;

    public BankBalanceEditor(EconomyManager economyManager, Player player) {
        this.player = player;
        this.economyManager = economyManager;
    }

    public void withdrawFunds(double amount) {
        // Withdraw funds from a player's bank account

        if (amount <= 0) {
            player.sendRichMessage(MessageUtil.getNegativeNumberMessage());
            return;
        }

        if (hasTooManyDecimals(amount)) {
            player.sendRichMessage(MessageUtil.getTooManyDecimalsMessage());
            return;
        }

        if (economyManager.removeBankBalance(player.getUniqueId(), amount)) {
            player.sendRichMessage("<green>Successfully withdrew " + amount);
        } else {
            player.sendRichMessage(MessageUtil.getInsufficientFundsMessage());
        }
    }

    public void depositFunds(double amount) {
        // Deposit funds into a player's bank account

        if (amount <= 0) {
            player.sendRichMessage(MessageUtil.getNegativeNumberMessage());
            return;
        }

        if (hasTooManyDecimals(amount)) {
            player.sendRichMessage(MessageUtil.getTooManyDecimalsMessage());
            return;
        }

        if (economyManager.removePurseBalance(player.getUniqueId(), amount)) {
            economyManager.addBankBalance(player.getUniqueId(), amount);
            player.sendRichMessage("<green>Successfully deposited " + amount);
        } else {
            player.sendRichMessage(MessageUtil.getInsufficientFundsMessage());
        }
    }

    private boolean hasTooManyDecimals(double amount) {
        String text = Double.toString(amount);
        int integerPlace = text.indexOf('.');
        int decimalPlace = text.length() - integerPlace - 1;

        return decimalPlace > 2;
    }
}
