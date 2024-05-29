package me.polardyth.polareconomy.utils.economy;

import java.util.UUID;

public class PurseManager implements BalanceManager{
    @Override
    public double getBalance(UUID playerUUID) {
        return 0;
    }

    @Override
    public void setBalance(UUID playerUUID, int amount) {

    }

    @Override
    public void addBalance(UUID playerUUID, int amount) {

    }

    @Override
    public boolean removeBalance(UUID playerUUID, int amount) {
        return false;
    }

    @Override
    public String getType() {
        return "purse";
    }
}
