package me.polardyth.polareconomy.menus.banker;

import me.polardyth.polareconomy.PolarSettings;
import me.polardyth.polareconomy.economy.balances.BalanceType;
import me.polardyth.polareconomy.economy.EconomyManager;
import me.polardyth.polareconomy.economy.balances.parents.StoredMoneyManager;
import me.polardyth.polareconomy.menus.banker.parents.BankerDefault;
import me.polardyth.polareconomy.placeholders.Format;
import me.polardyth.polareconomy.placeholders.Placeholder;
import me.polardyth.polareconomy.systems.transactionhistory.Transaction;
import me.polardyth.polareconomy.utils.MiniColor;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BankerMainPage extends BankerDefault {
    private final FileConfiguration config;
    private final EconomyManager economyManager;

    public BankerMainPage(EconomyManager economyManager, Player player) {
        super(economyManager.getStoredMoneyManager(BalanceType.BANK), player);

        config = PolarSettings.getConfigFiles().getFile("banker").getConfig();
        this.economyManager = economyManager;
        StoredMoneyManager bank = economyManager.getStoredMoneyManager(BalanceType.BANK);

        setItem(11, depositButton(player), depositAction());
        setItem(13, withdrawButton(player), withdrawAction());
        setItem(15, recentTransactions(player));
        setItem(31, closeButton(), closeAction());
        setItem(32, informationButton());
    }

    private ItemStack depositButton(Player player) {

        ItemStack item = new ItemStack(Material.CHEST);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(MiniColor.TEXT.deserialize(config.getString("menu.main-page.deposit-button.title")));
        List<Component> loreList = new ArrayList<>();

        for (String lore : config.getStringList("menu.main-page.withdraw-button.lore")) {
            loreList.add(MiniColor.TEXT.deserialize(Placeholder.applyPlaceholders(player, lore)));
        }

        meta.lore(loreList);
        item.setItemMeta(meta);

        return item;
    }

    private ItemStack withdrawButton(Player player) {

        ItemStack item = new ItemStack(Material.DISPENSER);
        ItemMeta meta = item.getItemMeta();

        List<Component> loreList = new ArrayList<>();

        meta.displayName(MiniColor.TEXT.deserialize(config.getString("menu.main-page.withdraw-button.title")));

        for (String lore : config.getStringList("menu.main-page.withdraw-button.lore")) {
            loreList.add(MiniColor.TEXT.deserialize(Placeholder.applyPlaceholders(player, lore)));
        }

        meta.lore(loreList);
        item.setItemMeta(meta);

        return item;
    }

    private ItemStack recentTransactions(Player player) {

        ItemStack item = new ItemStack(Material.FILLED_MAP);
        ItemMeta meta = item.getItemMeta();

        List<Component> loreList = new ArrayList<>();

        meta.displayName(MiniColor.TEXT.deserialize(config.getString("menu.main-page.recent-transactions.title")));

        List<Transaction> transactions = PolarSettings.getTransactionHandler().getTransactionsForPlayer(player.getUniqueId());
        transactions = transactions.stream()
                .filter(transaction -> transaction.source().equals(BalanceType.BANK.toString()))
                .collect(Collectors.toList());
        transactions.sort(Comparator.comparing(Transaction::timestamp).reversed());

        for (int i = 0; i < Math.min(transactions.size(), 7); i++) {
            Transaction transaction = transactions.get(i);
            String timeAgo = Format.formatTime(System.currentTimeMillis() - transaction.timestamp());
            long amount = transaction.amount();
            StringBuilder text = new StringBuilder("<gold>" + amount + "<gray>, <yellow>" + timeAgo);
            if (transaction.source().equals(BalanceType.BANK.toString())) {
                switch (transaction.type()) {
                    case DEPOSIT -> {
                        loreList.add(MiniColor.TEXT.deserialize(Placeholder.applyPlaceholders(player, amount, text.insert(0, "<green>+ ").toString())));
                    }
                    case WITHDRAW -> {
                        loreList.add(MiniColor.TEXT.deserialize(Placeholder.applyPlaceholders(player, amount, text.insert(0, "<red>- ").toString())));
                    }
                    default -> {
                        loreList.add(MiniColor.TEXT.deserialize(Placeholder.applyPlaceholders(player, amount, "An error has occurred. Please contact an administrator.")));
                    }
                }
            }
        }

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
