package me.polardyth.polareconomy.utils.economy;

import org.bukkit.entity.Player;

import java.util.UUID;

public interface IBalanceManager {
    double getBalance(UUID playerUUID);
    void setBalance(UUID playerUUID, int amount);
    void addBalance(UUID playerUUID, int amount);
    void removeBalance(Player player, int amount);
    String getType();
}
