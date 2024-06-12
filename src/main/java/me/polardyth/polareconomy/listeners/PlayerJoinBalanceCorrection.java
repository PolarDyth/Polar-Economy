package me.polardyth.polareconomy.listeners;

import me.polardyth.polareconomy.economy.balances.BalanceType;
import me.polardyth.polareconomy.economy.EconomyManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.logging.Logger;

public class PlayerJoinBalanceCorrection implements Listener {

    private final FileConfiguration config;
    private final EconomyManager economyManager;

    public PlayerJoinBalanceCorrection(FileConfiguration config, EconomyManager economyManager) {
        this.config = config;
        this.economyManager = economyManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!config.contains(player.getUniqueId().toString())) {
            Logger.getLogger("Minecraft").info("Debug: Player " + player.getName() + " does not have a balance in the config file.");
            economyManager.getBalanceManager(BalanceType.PURSE).setBalance(player.getUniqueId(), 0);
            economyManager.getBalanceManager(BalanceType.BANK).setBalance(player.getUniqueId(), 0);
        }
    }
}
