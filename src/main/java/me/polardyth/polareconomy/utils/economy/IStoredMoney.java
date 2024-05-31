package me.polardyth.polareconomy.utils.economy;

import org.bukkit.entity.Player;

public interface IStoredMoney extends IBalanceManager {

    public void withdrawFunds(int amount, Player player, IBalanceManager to);

    public void depositFunds(int amount, Player player, IBalanceManager from);
}
