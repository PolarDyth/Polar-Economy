package me.polardyth.polareconomy.economy.balances.interfaces;

import org.bukkit.entity.Player;

import java.util.UUID;

public interface IBalanceManager {
    int getBalance(UUID playerUUID);
    void setBalance(UUID playerUUID, int amount);
    void addBalance(UUID playerUUID, int amount);
    void removeBalance(Player player, int amount);
    String getType();
}
