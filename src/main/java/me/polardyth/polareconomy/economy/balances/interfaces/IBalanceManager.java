package me.polardyth.polareconomy.economy.balances.interfaces;

import me.polardyth.polareconomy.economy.balances.BalanceType;
import org.bukkit.entity.Player;

import java.util.UUID;

public interface IBalanceManager {
    long getBalance(UUID playerUUID);
    void setBalance(UUID playerUUID, long amount);
    void addBalance(UUID playerUUID, long amount);
    void removeBalance(Player player, long amount);
    BalanceType getType();
}
