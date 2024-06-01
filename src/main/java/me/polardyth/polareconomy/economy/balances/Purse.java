package me.polardyth.polareconomy.economy.balances;

import me.polardyth.polareconomy.economy.balances.parents.BalanceManager;
import org.bukkit.configuration.file.FileConfiguration;

public class Purse extends BalanceManager {

    public Purse(FileConfiguration dataFile, String target) {
        super(dataFile, target);
    }

    @Override
    public String getType() {
        return "purse";
    }
}
