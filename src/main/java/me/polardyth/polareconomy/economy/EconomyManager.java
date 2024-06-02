package me.polardyth.polareconomy.economy;

import me.polardyth.polareconomy.economy.balances.BalanceType;
import me.polardyth.polareconomy.economy.balances.interfaces.IBalanceManager;
import me.polardyth.polareconomy.economy.balances.interfaces.IEconomyManager;
import me.polardyth.polareconomy.economy.balances.interfaces.IStoredMoney;

import java.util.HashMap;
import java.util.Map;

public class EconomyManager implements IEconomyManager {

    private final Map<BalanceType, IBalanceManager> balanceManagers = new HashMap<>();

    public EconomyManager(IBalanceManager... balanceManager) {

        for (IBalanceManager manager : balanceManager) {
            this.balanceManagers.put(manager.getType(), manager);
        }
    }

    public IBalanceManager getBalanceManager(BalanceType type) {
        return this.balanceManagers.get(type);
    }

    public IStoredMoney getStoredMoneyManager(BalanceType type) {
        return (IStoredMoney) this.balanceManagers.get(type);
    }
}
