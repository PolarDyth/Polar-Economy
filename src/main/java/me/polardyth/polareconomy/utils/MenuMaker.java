package me.polardyth.polareconomy.utils;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class MenuMaker {

    private final Inventory menuInventory;
    private final Map<Integer, MenuAction> actions;
    private final UUID uuid;

    private static Map<UUID, MenuMaker> inventoriesByUUID;
    private static Map<UUID, UUID> openInventories;

    public MenuMaker(int invSize, @NotNull String invName) {
        uuid = UUID.randomUUID();
        MiniMessage miniMessage = MiniMessage.miniMessage();
        this.menuInventory = Bukkit.createInventory(null, invSize, miniMessage.deserialize(invName));

        actions = new HashMap<>();
        inventoriesByUUID = new HashMap<>();
        openInventories = new HashMap<>();

        inventoriesByUUID.put(getUuid(), this);
    }

    public void delete() {

        for (Player player : Bukkit.getOnlinePlayers()) {
            UUID uuid = getOpenInventories().get(player.getUniqueId());
            if (uuid.equals(getUuid())) {
                player.closeInventory();
            }
        }

        getInventoriesByUUID().remove(getUuid());
    }

    public void setItem(int slot, ItemStack item, MenuAction action) {
        menuInventory.setItem(slot, item);
        if (action != null) {
            actions.put(slot, action);
        }
    }

    public void setItem(int slot, ItemStack item) {
        setItem(slot, item, null);
    }

    public void open (Player player) {
        player.openInventory(menuInventory);
        openInventories.put(player.getUniqueId(), getUuid());
    }

    public Inventory getMenuInventory() {
        return menuInventory;
    }

    public UUID getUuid() {
        return uuid;
    }

    public static Map<UUID, MenuMaker> getInventoriesByUUID() {
        return inventoriesByUUID;
    }

    public static Map<UUID, UUID> getOpenInventories() {
        return openInventories;
    }

    public Map<Integer, MenuAction> getActions() {
        return actions;
    }

    public interface MenuAction {
        void click(Player player);
    }
}
