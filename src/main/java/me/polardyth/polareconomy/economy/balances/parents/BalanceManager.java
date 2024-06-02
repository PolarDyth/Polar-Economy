package me.polardyth.polareconomy.economy.balances.parents;

import me.polardyth.polareconomy.economy.balances.interfaces.IBalanceManager;
import me.polardyth.polareconomy.utils.config.interfaces.FileHandler;
import me.polardyth.polareconomy.utils.config.interfaces.IDataFiles;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.UUID;

public abstract class BalanceManager implements IBalanceManager {

    private final FileHandler dataFile;
    private final FileConfiguration dataConfig;
    private final String target;

    public BalanceManager(FileHandler dataFile, String target) {
        this.dataFile = dataFile;
        dataConfig = dataFile.getConfig();
        this.target = "." + target;
    }

    public long getBalance(UUID playerUUID) {
        return dataConfig.getLong(playerUUID.toString() + target);
    }

    public void setBalance(UUID playerUUID, long amount) {
        dataConfig.set(playerUUID.toString() + target, amount);
        dataFile.saveFile();
    }


    public void addBalance(UUID playerUUID, long amount) {
        dataConfig.set(playerUUID.toString() + target, getBalance(playerUUID) + amount);
        dataFile.saveFile();
    }

    public void removeBalance(Player player, long amount) {
        UUID uuid = player.getUniqueId();
        if (getBalance(uuid) < amount) {
            player.sendRichMessage(dataConfig.getString("pay.error.insufficient-funds"));
            return;
        }

        dataConfig.set(uuid + target, getBalance(player.getUniqueId()) - amount);
        dataFile.saveFile();
    }
}
