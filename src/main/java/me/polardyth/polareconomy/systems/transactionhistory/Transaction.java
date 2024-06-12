package me.polardyth.polareconomy.systems.transactionhistory;

import java.util.UUID;

public record Transaction(UUID playerUUID, String source, String destination, long amount, TransactionType type, long timestamp) {
}
