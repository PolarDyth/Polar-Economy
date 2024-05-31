package me.polardyth.polareconomy.utils.economy;

import me.polardyth.polareconomy.utils.MessageUtil;
import me.polardyth.polareconomy.utils.config.FileHandler;
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

    public double getBalance(UUID playerUUID) {
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
        }

        if (getBalance(uuid) >= amount) {
            dataFile.set(uuid + target, getBalance(player.getUniqueId()) - amount);
        }
    }
}
