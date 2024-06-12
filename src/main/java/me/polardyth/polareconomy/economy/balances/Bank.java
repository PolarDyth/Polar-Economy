package me.polardyth.polareconomy.economy.balances;

import me.polardyth.polareconomy.economy.balances.interfaces.MoneyType;
import me.polardyth.polareconomy.economy.balances.parents.StoredMoneyManager;
import me.polardyth.polareconomy.systems.transactionhistory.TransactionSource;
import me.polardyth.polareconomy.utils.files.interfaces.FileHandler;

public class Bank extends StoredMoneyManager implements MoneyType {

    public Bank(FileHandler dataFile, String target) {
        super(dataFile, target, TransactionSource.BANK);
    }

    @Override
    public BalanceType getType() {
        return BalanceType.BANK;
    }
}
