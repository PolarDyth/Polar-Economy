package me.polardyth.polareconomy.economy;

import me.polardyth.polareconomy.economy.balances.BalanceType;
import me.polardyth.polareconomy.economy.balances.parents.BalanceManager;
import me.polardyth.polareconomy.economy.balances.parents.StoredMoneyManager;

import java.util.HashMap;
import java.util.Map;

public class EconomyManager {

    private final Map<BalanceType, BalanceManager> balanceManagers = new HashMap<>();

    public EconomyManager(BalanceManager... balanceManager) {

        for (BalanceManager manager : balanceManager) {
            this.balanceManagers.put(manager.getType(), manager);
        }
    }

    public BalanceManager getBalanceManager(BalanceType type) {
        return this.balanceManagers.get(type);
    }

    public StoredMoneyManager getStoredMoneyManager(BalanceType type) {
        return (StoredMoneyManager) this.balanceManagers.get(type);
    }
}
