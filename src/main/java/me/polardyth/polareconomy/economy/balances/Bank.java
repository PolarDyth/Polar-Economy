package me.polardyth.polareconomy.economy.balances;

import me.polardyth.polareconomy.economy.balances.parents.StoredMoneyManager;
import org.bukkit.configuration.file.FileConfiguration;

public class Bank extends StoredMoneyManager {

    public Bank(FileConfiguration dataFile, String target) {
        super(dataFile, target);
    }

    @Override
    public String getType() {
        return "bank";
    }
}
