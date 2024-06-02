package me.polardyth.polareconomy.economy.balances.interfaces;

import org.bukkit.entity.Player;

public interface IStoredMoney extends IBalanceManager {

    public void withdrawFunds(long amount, Player player, IBalanceManager to);

    public void depositFunds(long amount, Player player, IBalanceManager from);
}
