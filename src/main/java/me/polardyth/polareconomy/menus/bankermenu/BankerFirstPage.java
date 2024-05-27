package me.polardyth.polareconomy.menus.bankermenu;

import me.polardyth.polareconomy.systems.MenuMaker;
import me.polardyth.polareconomy.systems.MiniColor;
import me.polardyth.polareconomy.utils.EconomyManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class BankerFirstPage extends MenuMaker {
    private final FileConfiguration bankerConfig;
    private final EconomyManager economyManager;

    public BankerFirstPage(EconomyManager economyManager, Player player) {


        super(36, "banker");
        bankerConfig = economyManager.getConfigs().getConfig("banker");
        this.economyManager = economyManager;

        for (int i = 0; i < 36; i++) {
            setItem(i, blackPane());
        }

        setItem(11, depositButton(player));
        setItem(13, withdrawButton(player));
        setItem(15, recentTransactions(player));
        setItem(31, closeButton(player));
        setItem(32, informationButton());
    }

    private ItemStack blackPane() {
        ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(MiniColor.TEXT.deserialize("<black>"));
        meta.lore();
        item.setItemMeta(meta);

        return item;
    }

    private ItemStack depositButton(Player player) {

        ItemStack item = new ItemStack(Material.CHEST);
        ItemMeta meta = item.getItemMeta();

        List<Component> loreList = new ArrayList<>();

        meta.displayName(MiniColor.TEXT.deserialize(bankerConfig.getString("menu.deposit-button.title")));

        for (String lore : bankerConfig.getStringList("menu.deposit-button.lore")) {
            loreList.add(MiniColor.TEXT.deserialize(lore.replace("{balance}", Double.toString(economyManager.getBalance(player.getUniqueId())))));
        }

        meta.lore(loreList);
        item.setItemMeta(meta);

        return item;
    }

    private ItemStack withdrawButton(Player player) {

        ItemStack item = new ItemStack(Material.DISPENSER);
        ItemMeta meta = item.getItemMeta();

        List<Component> loreList = new ArrayList<>();

        meta.displayName(MiniColor.TEXT.deserialize(bankerConfig.getString("menu.withdraw-button.title")));

        for (String lore : bankerConfig.getStringList("menu.withdraw-button.lore")) {
            loreList.add(MiniColor.TEXT.deserialize(lore.replace("{balance}", Double.toString(economyManager.getBalance(player.getUniqueId())))));
        }

        meta.lore(loreList);
        item.setItemMeta(meta);

        return item;
    }

    private ItemStack recentTransactions(Player player) {

        ItemStack item = new ItemStack(Material.FILLED_MAP);
        ItemMeta meta = item.getItemMeta();

        List<Component> loreList = new ArrayList<>();

        meta.displayName(MiniColor.TEXT.deserialize(bankerConfig.getString("menu.recent-transactions.title")));

        loreList.add(MiniColor.TEXT.deserialize("<red>! Work in progress !</red>"));

        meta.lore(loreList);
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack closeButton(Player player) {

            ItemStack item = new ItemStack(Material.BARRIER);
            ItemMeta meta = item.getItemMeta();

            List<Component> loreList = new ArrayList<>();

            meta.displayName(MiniColor.TEXT.deserialize("<red>Close</red>"));

            meta.lore();
            item.setItemMeta(meta);

            return item;
    }

    private ItemStack informationButton() {

        ItemStack item = new ItemStack(Material.REDSTONE_TORCH);
        ItemMeta meta = item.getItemMeta();

        List<Component> loreList = new ArrayList<>();

        meta.displayName(MiniColor.TEXT.deserialize(bankerConfig.getString("menu.information-button.title")));

        for (String lore : bankerConfig.getStringList("menu.information-button.lore")) {
            loreList.add(MiniColor.TEXT.deserialize(lore));
        }

        meta.lore(loreList);
        item.setItemMeta(meta);

        return item;
    }
}