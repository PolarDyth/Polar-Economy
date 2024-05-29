package me.polardyth.polareconomy.menus.bankermenu;

import me.polardyth.polareconomy.PolarSettings;
import me.polardyth.polareconomy.listeners.SignGUIListener;
import me.polardyth.polareconomy.systems.MenuMaker;
import me.polardyth.polareconomy.systems.MiniColor;
import me.polardyth.polareconomy.utils.economy.EconomyManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class BankerDepositPage extends MenuMaker {

    private final FileConfiguration bankerConfig;
    private final EconomyManager economyManager;

    public BankerDepositPage(EconomyManager economyManager, Player player) {
        super(36, "banker");

        this.economyManager = economyManager;
        bankerConfig = economyManager.getConfigs().getConfig("banker");

        for (int i = 0; i < 36; i++) {
            setItem(i, blackPane());
        }

        setItem(11, depositAll(player), depositAllAction());
        setItem(13, depositHalf(player), depositHalfAction());
        setItem(15, specificAmountButton(player), specificAmountAction());
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

    private ItemStack depositAll(Player player) {
        ItemStack item = new ItemStack(Material.CHEST);
        item.setAmount(64);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(MiniColor.TEXT.deserialize(bankerConfig.getString("menu.deposit-page.deposit-all.title")));

        List<Component> loreList = new ArrayList<>();

        for (String lore : bankerConfig.getStringList("menu.deposit-page.deposit-all.lore")) {
            loreList.add(MiniColor.TEXT.deserialize(lore
                    .replace("{bank_balance}", Double.toString(economyManager.getBankBalance(player.getUniqueId())))
                    .replace("{amount}", Double.toString(economyManager.getPurseBalance(player.getUniqueId())))));
        }

        meta.lore(loreList);
        item.setItemMeta(meta);

        return item;
    }

    private ItemStack depositHalf(Player player) {
        ItemStack item = new ItemStack(Material.CHEST);
        item.setAmount(32);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(MiniColor.TEXT.deserialize(bankerConfig.getString("menu.deposit-page.deposit-half.title")));

        List<Component> loreList = new ArrayList<>();

        final DecimalFormat df = new DecimalFormat("0.00");
        double amount = Double.parseDouble(df.format(economyManager.getPurseBalance(player.getUniqueId()) / 2));

        for (String lore : bankerConfig.getStringList("menu.deposit-page.deposit-half.lore")) {
            loreList.add(MiniColor.TEXT.deserialize(lore
                    .replace("{bank_balance}", Double.toString(economyManager.getBankBalance(player.getUniqueId())))
                    .replace("{amount}", Double.toString(amount))));
        }

        meta.lore(loreList);
        item.setItemMeta(meta);

        return item;
    }

    private ItemStack specificAmountButton(Player player) {
        ItemStack item = new ItemStack(Material.OAK_SIGN);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(MiniColor.TEXT.deserialize(bankerConfig.getString("menu.deposit-page.specific-amount-button.title")));

        List<Component> loreList = new ArrayList<>();

        for (String lore : bankerConfig.getStringList("menu.deposit-page.specific-amount-button.lore")) {
            loreList.add(MiniColor.TEXT.deserialize(lore.replace("{bank_balance}", Double.toString(economyManager.getBankBalance(player.getUniqueId())))));
        }

        meta.lore(loreList);
        item.setItemMeta(meta);

        return item;
    }

    private ItemStack backButton() {
        ItemStack item = new ItemStack(Material.ARROW);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(MiniColor.TEXT.deserialize(bankerConfig.getString("menu.deposit-page.back-button.title")));

        List<Component> loreList = new ArrayList<>();

        for (String lore : bankerConfig.getStringList("menu.deposit-page.back-button.lore")) {
            loreList.add(MiniColor.TEXT.deserialize(lore));
        }

        meta.lore(loreList);
        item.setItemMeta(meta);

        return item;
    }

    private MenuAction specificAmountAction() {
        return player -> {
            SignGUI signGUI = new SignGUI(player);
            PolarSettings.getPlugin().getServer().getPluginManager().registerEvents(new SignGUIListener(economyManager, signGUI.getLocation(), signGUI.getBlockSave(), true), PolarSettings.getPlugin());
        };
    }

    private MenuAction goBackAction() {
        return player -> {
            BankerMainPage mainPage = new BankerMainPage(economyManager, player);
            mainPage.open(player);
        };
    }

    private MenuAction depositAllAction() {
        return player -> {
            double amount = economyManager.getData().getConfig("balances").getDouble(player.getUniqueId() + ".purse");
            economyManager.addBankBalance(player.getUniqueId(), amount);
            economyManager.setPurseBalance(player.getUniqueId(), 0);

            BankerMainPage mainPage = new BankerMainPage(economyManager, player);
            mainPage.open(player);
        };
    }

    private MenuAction depositHalfAction() {
        return player -> {
            final DecimalFormat df = new DecimalFormat("0.00");
            double amount = Double.parseDouble(df.format(economyManager.getData().getConfig("balances").getDouble(player.getUniqueId() + ".purse") / 2));
            economyManager.addBankBalance(player.getUniqueId(), amount);
            economyManager.setPurseBalance(player.getUniqueId(), economyManager.getData().getConfig("balances").getDouble(player.getUniqueId() + ".purse") - amount);

            BankerMainPage mainPage = new BankerMainPage(economyManager, player);
            mainPage.open(player);
        };
    }
}
