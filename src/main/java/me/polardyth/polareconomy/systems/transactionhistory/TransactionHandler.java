package me.polardyth.polareconomy.systems.transactionhistory;

import me.polardyth.polareconomy.utils.files.interfaces.FileHandler;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;

public class TransactionHandler {

    private final Map<UUID, List<Transaction>> transactions = new HashMap<>();
    private final FileHandler dataFile;

    public TransactionHandler(FileHandler dataFile) {
        this.dataFile = dataFile;
        loadTransactions();
    }

    public void createTransaction(Transaction transaction) {
        transactions.computeIfAbsent(transaction.playerUUID(), k -> new ArrayList<>()).add(transaction);
        saveTransactions(transaction);
    }

    public List<Transaction> getTransactionsForPlayer(UUID playerId) {
        return transactions.getOrDefault(playerId, new ArrayList<>());
    }

    public void loadTransactions() {
        FileConfiguration config = dataFile.getConfig();
        if (config.isConfigurationSection("transactions")) {
            config.getConfigurationSection("transactions").getKeys(false).forEach(key -> {
                UUID playerUUID = UUID.fromString(key);
                config.getConfigurationSection("transactions." + key).getKeys(false).forEach(transactionKey -> {
                    String source = config.getString("transactions." + key + "." + transactionKey + ".source");
                    String destination = config.getString("transactions." + key + "." + transactionKey + ".destination");
                    long amount = config.getLong("transactions." + key + "." + transactionKey + ".amount");
                    TransactionType type = TransactionType.valueOf(config.getString("transactions." + key + "." + transactionKey + ".type"));
                    long timestamp = config.getLong("transactions." + key + "." + transactionKey + ".timestamp");
                    transactions.computeIfAbsent(playerUUID, k -> new ArrayList<>()).add(new Transaction(playerUUID, source, destination, amount, type, timestamp));
                });
            });
        }
    }

    public void saveTransactions(Transaction transaction) {
        String path = "transactions." + transaction.playerUUID() + "." + UUID.randomUUID();
        FileConfiguration config = dataFile.getConfig();
        config.set(path + ".source", transaction.source());
        config.set(path + ".destination", transaction.destination());
        config.set(path + ".amount", transaction.amount());
        config.set(path + ".type", transaction.type().toString());
        config.set(path + ".timestamp", transaction.timestamp());
        dataFile.saveFile();
    }
}
