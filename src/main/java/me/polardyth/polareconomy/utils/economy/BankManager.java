package me.polardyth.polareconomy.utils.economy;

import me.polardyth.polareconomy.utils.MessageUtil;
import org.bukkit.entity.Player;

import java.util.UUID;

public class BankManager implements StoredMoney, BalanceManager {

    private final Player player;
    private final EconomyManager economyManager;

    public BankManager(EconomyManager economyManager, Player player) {
        this.player = player;
        this.economyManager = economyManager;
    }

    @Override
    public double getBalance(UUID playerUUID) {
        return 0;
    }

    @Override
    public void setBalance(UUID playerUUID, int amount) {

    }

    @Override
    public void addBalance(UUID playerUUID, int amount) {

    }

    @Override
    public boolean removeBalance(UUID playerUUID, int amount) {
        return false;
    }

    @Override
    public String getType() {
        return "bank";
    }

    public void withdrawFunds(int amount) {
        // Withdraw funds from a player's bank account

        if (amount <= 0) {
            player.sendRichMessage(MessageUtil.getNegativeNumberMessage());
            return;
        }

        if (economyManager.removeBalance(player.getUniqueId(), "bank", amount)) {
            economyManager.addBalance(player.getUniqueId(), "purse", amount);
            player.sendRichMessage("<green>Successfully withdrew " + amount);
        } else {
            player.sendRichMessage(MessageUtil.getInsufficientFundsMessage());
        }
    }

    public void depositFunds(int amount) {
        // Deposit funds into a player's bank account

        if (amount <= 0) {
            player.sendRichMessage(MessageUtil.getNegativeNumberMessage());
            return;
        }

        if (economyManager.removeBalance(player.getUniqueId(), "purse", amount)) {
            economyManager.addBalance(player.getUniqueId(), "bank", amount);
            player.sendRichMessage("<green>Successfully deposited " + amount);
        } else {
            player.sendRichMessage(MessageUtil.getInsufficientFundsMessage());
        }
    }



//    private boolean hasTooManyDecimals(double amount) {
//        String text = Double.toString(amount);
//        int integerPlace = text.indexOf('.');
//        int decimalPlace = text.length() - integerPlace - 1;
//
//        return decimalPlace > 2;
//    }
}
