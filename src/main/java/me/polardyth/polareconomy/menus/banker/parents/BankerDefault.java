package me.polardyth.polareconomy.menus.banker.parents;

import me.polardyth.polareconomy.economy.balances.parents.StoredMoneyManager;
import me.polardyth.polareconomy.placeholders.Placeholder;
import me.polardyth.polareconomy.utils.MenuMaker;
import me.polardyth.polareconomy.utils.MiniColor;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public abstract class BankerDefault extends MenuMaker {

    private final StoredMoneyManager bank;
    private final Player player;

    public BankerDefault(StoredMoneyManager bank, Player player) {
        super(36, "Banker");

        this.bank = bank;
        this.player = player;

        for (int i = 0; i < 36; i++) {
            setItem(i, blackPane());
        }
    }

    private ItemStack blackPane() {
        ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(MiniColor.TEXT.deserialize("<black>"));
        meta.lore();
        item.setItemMeta(meta);

        return item;
    }

    public List<Component> replaceText(List<String> path, long amount) {
        List<Component> loreList = new ArrayList<>();
        for (String lore: path) {
            loreList.add(MiniColor.TEXT.deserialize(Placeholder.applyPlaceholders(player, amount, lore)));
        }
        return loreList;
    }

    public List<Component> replaceText(List<String> path) {
        return replaceText(path, 0);
    }
}
