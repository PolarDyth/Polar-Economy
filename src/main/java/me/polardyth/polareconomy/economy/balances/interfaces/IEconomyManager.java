package me.polardyth.polareconomy.economy.balances.interfaces;

import me.polardyth.polareconomy.economy.balances.BalanceType;

public interface IEconomyManager {

    public IBalanceManager getBalanceManager(BalanceType type);
    public IStoredMoney getStoredMoneyManager(BalanceType type);
}
