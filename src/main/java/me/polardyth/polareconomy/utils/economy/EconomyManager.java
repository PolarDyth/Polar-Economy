package me.polardyth.polareconomy.utils.economy;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EconomyManager {

    private final Map<String, BalanceManager> balanceManagers = new HashMap<>();

    public EconomyManager(BalanceManager... balanceManager) {

        for (BalanceManager manager : balanceManager) {
            this.balanceManagers.put(manager.getType(), manager);
        }
    }


    public double getBalance(UUID playerUUID, String type) {
        return balanceManagers.get(type).getBalance(playerUUID);
    }

    public void setBalance(UUID playerUUID, String type, int amount) {
        balanceManagers.get(type).setBalance(playerUUID, amount);
    }

    public void addBalance(UUID playerUUID, String type, int amount) {
        balanceManagers.get(type).addBalance(playerUUID, amount);
    }

    public boolean removeBalance(UUID playerUUID, String type, int amount) {
        return balanceManagers.get(type).removeBalance(playerUUID, amount);
    }
}
