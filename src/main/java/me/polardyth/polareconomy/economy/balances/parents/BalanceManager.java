package me.polardyth.polareconomy.economy.balances.parents;

import me.polardyth.polareconomy.economy.balances.interfaces.IBalanceManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.UUID;

public abstract class BalanceManager implements IBalanceManager {

    private final FileConfiguration dataFile;
    private final String target;

    public BalanceManager(FileConfiguration dataFile, String target) {
        this.dataFile = dataFile;
        this.target = "." + target;
    }

    public int getBalance(UUID playerUUID) {
        return dataFile.getInt(playerUUID.toString() + target);
    }

    public void setBalance(UUID playerUUID, int amount) {
        dataFile.set(playerUUID.toString() + target, amount);
    }


    public void addBalance(UUID playerUUID, int amount) {
        dataFile.set(playerUUID.toString() + target, getBalance(playerUUID) + amount);
    }

    public void removeBalance(Player player, int amount) {
        UUID uuid = player.getUniqueId();
        if (getBalance(uuid) < amount) {
            player.sendRichMessage(dataFile.getString("pay.error.insufficient-funds"));
            return;
        }

        dataFile.set(uuid + target, getBalance(player.getUniqueId()) - amount);
    }
}
