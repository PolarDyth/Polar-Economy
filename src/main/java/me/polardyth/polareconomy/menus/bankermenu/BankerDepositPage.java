package me.polardyth.polareconomy.menus.bankermenu;

import me.polardyth.polareconomy.listeners.SignGUIListener;
import me.polardyth.polareconomy.systems.MenuMaker;
import me.polardyth.polareconomy.systems.MiniColor;
import me.polardyth.polareconomy.utils.EconomyManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

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

        setItem(15, specificAmountButton(), specificAmountAction());
    }

    private ItemStack blackPane() {
        ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(MiniColor.TEXT.deserialize("<black>"));
        meta.lore();
        item.setItemMeta(meta);

        return item;
    }

    private ItemStack specificAmountButton() {
        ItemStack item = new ItemStack(Material.OAK_SIGN);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(MiniColor.TEXT.deserialize(bankerConfig.getString("menu.deposit-page.specific-amount-button.title")));

        List<Component> loreList = new ArrayList<>();

        for (String lore : bankerConfig.getStringList("menu.deposit-page.specific-amount-button.lore")) {
            loreList.add(MiniColor.TEXT.deserialize(lore.replace("{amount}", "0")));
        }

        meta.lore(loreList);
        item.setItemMeta(meta);

        return item;
    }

    private MenuAction specificAmountAction() {
        return player -> {
            SignGUIListener signGUIListener = new SignGUIListener(economyManager);
            signGUIListener.openSignGUI(player);
        };
    }
}
