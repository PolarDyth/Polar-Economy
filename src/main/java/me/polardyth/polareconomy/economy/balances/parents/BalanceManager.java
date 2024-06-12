package me.polardyth.polareconomy.economy.balances.parents;

import me.polardyth.polareconomy.PolarSettings;
import me.polardyth.polareconomy.economy.balances.interfaces.MoneyType;
import me.polardyth.polareconomy.systems.transactionhistory.Transaction;
import me.polardyth.polareconomy.systems.transactionhistory.TransactionSource;
import me.polardyth.polareconomy.systems.transactionhistory.TransactionType;
import me.polardyth.polareconomy.utils.files.interfaces.FileHandler;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.UUID;

public abstract class BalanceManager implements MoneyType {

    private final FileHandler dataFile;
    private final FileConfiguration dataConfig;
    private final String target;

    public BalanceManager(FileHandler dataFile, String target) {
        this.dataFile = dataFile;
        dataConfig = dataFile.getConfig();
        this.target = "." + target;
    }

    public long getBalance(UUID playerUUID) {
        return dataConfig.getLong(playerUUID.toString() + target);
    }

    public void setBalance(UUID playerUUID, long amount) {
        dataConfig.set(playerUUID.toString() + target, amount);
        dataFile.saveFile();
    }


    public void addBalance(UUID playerUUID, long amount, TransactionType type, String source, String destination) {
        updateBalance(playerUUID, amount, type, source, destination, true);
    }

    public void removeBalance(Player player, long amount, TransactionType type, String source, String destination) {
        if (getBalance(player.getUniqueId()) < amount) {
            player.sendRichMessage(dataConfig.getString("pay.error.insufficient-funds"));
            return;
        }
        updateBalance(player.getUniqueId(), amount, type, source, destination, false);
    }

    private void updateBalance(UUID playerUUID, long amount, TransactionType type, String source, String destination, boolean isAdding) {
        long newBalance = isAdding ? getBalance(playerUUID) + amount : getBalance(playerUUID) - amount;
        dataConfig.set(playerUUID.toString() + target, newBalance);
        dataFile.saveFile();

        Transaction transaction = new Transaction(playerUUID, source, destination, amount, type, System.currentTimeMillis());
        PolarSettings.getTransactionHandler().createTransaction(transaction);
    }
}
