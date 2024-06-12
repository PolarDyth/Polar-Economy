package me.polardyth.polareconomy.economy.balances;

import me.polardyth.polareconomy.economy.balances.parents.BalanceManager;
import me.polardyth.polareconomy.utils.files.interfaces.FileHandler;

public class Purse extends BalanceManager  {

    public Purse(FileHandler dataFile, String target) {
        super(dataFile, target);
    }

    @Override
    public BalanceType getType() {
        return BalanceType.PURSE;
    }
}
