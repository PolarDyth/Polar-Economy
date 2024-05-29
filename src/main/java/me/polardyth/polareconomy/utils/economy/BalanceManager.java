package me.polardyth.polareconomy.utils.economy;

import java.util.UUID;

public interface BalanceManager {
    double getBalance(UUID playerUUID);
    void setBalance(UUID playerUUID, int amount);
    void addBalance(UUID playerUUID, int amount);
    boolean removeBalance(UUID playerUUID, int amount);
    String getType();
}
