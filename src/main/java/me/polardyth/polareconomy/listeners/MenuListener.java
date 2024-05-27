package me.polardyth.polareconomy.listeners;

import me.polardyth.polareconomy.systems.MenuMaker;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import java.util.UUID;

public class MenuListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }
        UUID playerUuid = player.getUniqueId();

        UUID inventoryUuid = MenuMaker.getOpenInventories().get(playerUuid);
        if (inventoryUuid != null) {
            event.setCancelled(true);
            MenuMaker menu = MenuMaker.getInventoriesByUUID().get(inventoryUuid);
            MenuMaker.MenuAction action = menu.getActions().get(event.getSlot());

            if (action != null) {
                action.click(player);
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player player)) {
            return;
        }
        UUID playerUuid = player.getUniqueId();

        UUID inventoryUuid = MenuMaker.getOpenInventories().get(playerUuid);
        if (inventoryUuid != null) {
            MenuMaker.getInventoriesByUUID().remove(inventoryUuid);
            MenuMaker.getOpenInventories().remove(playerUuid);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UUID playerUuid = player.getUniqueId();

        UUID inventoryUuid = MenuMaker.getOpenInventories().get(playerUuid);
        if (inventoryUuid != null) {
            MenuMaker.getInventoriesByUUID().remove(inventoryUuid);
            MenuMaker.getOpenInventories().remove(playerUuid);
        }
    }


}
