package me.polardyth.polareconomy.menus.banker;

import me.polardyth.polareconomy.PolarSettings;
import me.polardyth.polareconomy.economy.balances.BalanceType;
import me.polardyth.polareconomy.economy.balances.interfaces.IEconomyManager;
import me.polardyth.polareconomy.economy.balances.interfaces.IStoredMoney;
import me.polardyth.polareconomy.menus.banker.parents.BankerDefault;
import me.polardyth.polareconomy.utils.MiniColor;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class BankerMainPage extends BankerDefault {
    private final FileConfiguration config;
    private final IEconomyManager economyManager;
    private final IStoredMoney bank;

    public BankerMainPage(IEconomyManager economyManager, Player player) {
        super(economyManager.getStoredMoneyManager(BalanceType.BANK), player);

        config = PolarSettings.getConfigFiles().getConfig("banker");
        this.economyManager = economyManager;
        bank = economyManager.getStoredMoneyManager(BalanceType.BANK);

        setItem(11, depositButton(player), depositAction());
        setItem(13, withdrawButton(player), withdrawAction());
        setItem(15, recentTransactions());
        setItem(31, closeButton(), closeAction());
        setItem(32, informationButton());
    }

    private ItemStack depositButton(Player player) {

        ItemStack item = new ItemStack(Material.CHEST);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(MiniColor.TEXT.deserialize(config.getString("menu.main-page.deposit-button.title")));

        meta.lore(replaceText(config.getStringList("menu.main-page.deposit-button.lore")));
        item.setItemMeta(meta);

        return item;
    }

    private ItemStack withdrawButton(Player player) {

        ItemStack item = new ItemStack(Material.DISPENSER);
        ItemMeta meta = item.getItemMeta();

        List<Component> loreList = new ArrayList<>();

        meta.displayName(MiniColor.TEXT.deserialize(config.getString("menu.main-page.withdraw-button.title")));

        for (String lore : config.getStringList("menu.main-page.withdraw-button.lore")) {
            loreList.add(MiniColor.TEXT.deserialize(lore.replace("{balance}", Integer.toString(bank.getBalance(player.getUniqueId())))));
        }

        meta.lore(loreList);
        item.setItemMeta(meta);

        return item;
    }

    private ItemStack recentTransactions() {

        ItemStack item = new ItemStack(Material.FILLED_MAP);
        ItemMeta meta = item.getItemMeta();

        List<Component> loreList = new ArrayList<>();

        meta.displayName(MiniColor.TEXT.deserialize(config.getString("menu.main-page.recent-transactions.title")));

        loreList.add(MiniColor.TEXT.deserialize("<red>! Work in progress !</red>"));

        meta.lore(loreList);
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack closeButton() {

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

        meta.displayName(MiniColor.TEXT.deserialize(config.getString("menu.main-page.information-button.title")));

        for (String lore : config.getStringList("menu.main-page.information-button.lore")) {
            loreList.add(MiniColor.TEXT.deserialize(lore));
        }

        meta.lore(loreList);
        item.setItemMeta(meta);

        return item;
    }

    private MenuAction closeAction() {
        return HumanEntity::closeInventory;
    }

    private MenuAction depositAction() {
        return player -> {
            BankerDepositPage depositPage = new BankerDepositPage(economyManager, player);
            depositPage.open(player);
        };
    }

    private MenuAction withdrawAction() {
        return player -> {
            BankerWithdrawPage withdrawPage = new BankerWithdrawPage(economyManager, player);
            withdrawPage.open(player);
        };
    }
}