package me.polardyth.polareconomy.utils.economy;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EconomyManager {

    private final Map<String, IBalanceManager> balanceManagers = new HashMap<>();

    public EconomyManager(IBalanceManager... balanceManager) {

        for (IBalanceManager manager : balanceManager) {
            this.balanceManagers.put(manager.getType(), manager);
        }
    }

    public IBalanceManager getBalanceManager(String type) {
        return this.balanceManagers.get(type);
    }

    public IStoredMoney getStoredMoneyManager(String type) {
        return (IStoredMoney) this.balanceManagers.get(type);
    }
}
