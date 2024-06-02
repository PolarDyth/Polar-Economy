package me.polardyth.polareconomy.economy.balances;

import me.polardyth.polareconomy.economy.balances.parents.StoredMoneyManager;
import me.polardyth.polareconomy.utils.config.interfaces.FileHandler;
import org.bukkit.configuration.file.FileConfiguration;

public class Bank extends StoredMoneyManager {

    public Bank(FileHandler dataFile, String target) {
        super(dataFile, target);
    }

    @Override
    public BalanceType getType() {
        return BalanceType.BANK;
    }
}
