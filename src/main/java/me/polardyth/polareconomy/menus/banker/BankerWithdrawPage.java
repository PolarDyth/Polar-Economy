package me.polardyth.polareconomy.menus.banker;

import me.polardyth.polareconomy.PolarSettings;
import me.polardyth.polareconomy.economy.balances.BalanceType;
import me.polardyth.polareconomy.economy.balances.interfaces.IBalanceManager;
import me.polardyth.polareconomy.economy.balances.interfaces.IEconomyManager;
import me.polardyth.polareconomy.economy.balances.interfaces.IStoredMoney;
import me.polardyth.polareconomy.listeners.SignGUIListener;
import me.polardyth.polareconomy.menus.SignGUI;
import me.polardyth.polareconomy.menus.banker.parents.BankerDefault;
import me.polardyth.polareconomy.utils.MiniColor;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class BankerWithdrawPage extends BankerDefault {


    private final FileConfiguration config;
    private final IEconomyManager economyManager;
    private final IStoredMoney bank;
    private final IBalanceManager purse;

    public BankerWithdrawPage(IEconomyManager economyManager, Player player) {
        super(economyManager.getStoredMoneyManager(BalanceType.BANK), player);

        this.economyManager = economyManager;
        config = PolarSettings.getConfigFiles().getConfig("banker");
        bank = economyManager.getStoredMoneyManager(BalanceType.BANK);
        purse = economyManager.getBalanceManager(BalanceType.PURSE);

        for (int i = 0; i < 36; i++) {
            setItem(i, blackPane());
        }

        setItem(10, withdrawAll(player), withdrawAllAction());
        setItem(12, withdrawHalf(player), withdrawHalfAction());
        setItem(14, withdrawTwentyPercent(player), withdrawTwentyPercentAction());
        setItem(16, specificAmountButton(player), specificAmountAction());
        setItem(31, backButton(), goBackAction());
    }

    private ItemStack blackPane() {
        ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(MiniColor.TEXT.deserialize("<black>"));
        meta.lore();
        item.setItemMeta(meta);

        return item;
    }

    private ItemStack withdrawAll(Player player) {
        ItemStack item = new ItemStack(Material.DISPENSER);
        item.setAmount(64);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(MiniColor.TEXT.deserialize(config.getString("menu.withdraw-page.withdraw-all.title")));

        meta.lore(replaceText(config.getStringList("menu.withdraw-page.withdraw-all.lore"), bank.getBalance(player.getUniqueId())));
        item.setItemMeta(meta);

        return item;
    }

    private ItemStack withdrawHalf(Player player) {
        ItemStack item = new ItemStack(Material.DISPENSER);
        item.setAmount(32);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(MiniColor.TEXT.deserialize(config.getString("menu.withdraw-page.withdraw-half.title")));

        int amount = bank.getBalance(player.getUniqueId()) / 2;

        meta.lore(replaceText(config.getStringList("menu.withdraw-page.withdraw-half.lore"), amount));
        item.setItemMeta(meta);

        return item;
    }

    private ItemStack withdrawTwentyPercent(Player player) {
        ItemStack item = new ItemStack(Material.DISPENSER);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(MiniColor.TEXT.deserialize(config.getString("menu.withdraw-page.withdraw-20.title")));

        meta.lore(replaceText(config.getStringList("menu.withdraw-page.withdraw-20.lore"), bank.getBalance(player.getUniqueId())));
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack specificAmountButton(Player player) {
        ItemStack item = new ItemStack(Material.OAK_SIGN);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(MiniColor.TEXT.deserialize(config.getString("menu.deposit-page.specific-amount-button.title")));

        meta.lore(replaceText(config.getStringList("menu.deposit-page.specific-amount-button.lore"), bank.getBalance(player.getUniqueId())));
        item.setItemMeta(meta);

        return item;
    }

    private ItemStack backButton() {
        ItemStack item = new ItemStack(Material.ARROW);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(MiniColor.TEXT.deserialize(config.getString("menu.deposit-page.back-button.title")));

        List<Component> loreList = new ArrayList<>();

        for (String lore : config.getStringList("menu.deposit-page.back-button.lore")) {
            loreList.add(MiniColor.TEXT.deserialize(lore));
        }

        meta.lore(loreList);
        item.setItemMeta(meta);

        return item;
    }

    private MenuAction withdrawAllAction() {
        return (player) -> {
            bank.withdrawFunds(bank.getBalance(player.getUniqueId()), player, purse);

            BankerMainPage mainPage = new BankerMainPage(economyManager, player);
            mainPage.open(player);
        };
    }

    private MenuAction withdrawHalfAction() {
        return player -> {
            bank.withdrawFunds(bank.getBalance(player.getUniqueId()) / 2, player, purse);

            BankerMainPage mainPage = new BankerMainPage(economyManager, player);
            mainPage.open(player);
        };
    }

    private MenuAction withdrawTwentyPercentAction() {
        return player -> {
            bank.withdrawFunds((int) (bank.getBalance(player.getUniqueId()) * 0.2), player, purse);
            BankerMainPage mainPage = new BankerMainPage(economyManager, player);
            mainPage.open(player);
        };
    }

    private MenuAction specificAmountAction() {
        return player -> {
            SignGUI signGUI = new SignGUI(player);
            PolarSettings.getPlugin().getServer().getPluginManager().registerEvents(new SignGUIListener(economyManager, signGUI.getLocation(), signGUI.getBlockSave(), false), PolarSettings.getPlugin());
        };
    }

    private MenuAction goBackAction() {
        return player -> {
            BankerMainPage mainPage = new BankerMainPage(economyManager, player);
            mainPage.open(player);
        };
    }
}
