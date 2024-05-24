package me.polardyth.polareconomy.menus;

import me.polardyth.polareconomy.systems.MenuMaker;
import me.polardyth.polareconomy.utils.EconomyManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class BankerMenu extends MenuMaker {
    private final MiniMessage miniMessage;
    private final EconomyManager economyManager;

    public BankerMenu(EconomyManager economyManager, Player player) {
        super(36, "Banker");
        this.economyManager = economyManager;
        miniMessage = MiniMessage.miniMessage();

        for (int i = 0; i < 36; i++) {
            setItem(i, blackPane());
        }

        setItem(11, depositButton(player));
    }

    private ItemStack blackPane() {
        ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(miniMessage.deserialize("<black>"));
        meta.lore();
        item.setItemMeta(meta);

        return item;
    }

    private ItemStack depositButton(Player player) {

        Style style = Style.style(TextDecoration.BOLD).decoration(TextDecoration.ITALIC, false);

        TextDecoration.State state = TextDecoration.State.TRUE;

        ItemStack item = new ItemStack(Material.CHEST);
        ItemMeta meta = item.getItemMeta();

        List<Component> loreList = new ArrayList<>();
        loreList.add(miniMessage.deserialize("<gray>Current Balance: <gold>" + economyManager.getBalance(player.getUniqueId())));
        loreList.add(miniMessage.deserialize(""));
        loreList.add(miniMessage.deserialize("<gray>Store coins in the bank to keep them"));
        loreList.add(miniMessage.deserialize("<gray>safe and earn interest on them!"));
        loreList.add(miniMessage.deserialize(""));
        loreList.add(miniMessage.deserialize("<gray>You will earn <aqua>2%<gray> interest every season"));
        loreList.add(miniMessage.deserialize("<gray>for your first <gold>10 million<gray> banked coins."));
        loreList.add(miniMessage.deserialize(""));
        loreList.add(miniMessage.deserialize("<gray>Until next interest: <aqua>1d 12h 30m"));
        loreList.add(miniMessage.deserialize(""));
        loreList.add(miniMessage.deserialize("<yellow>Click to make a deposit"));

        for (Component comp : loreList) {
            comp.decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE);
        }

        meta.displayName(miniMessage.deserialize("<green>Deposit Coins</green>").decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE));
        meta.lore(loreList);
        item.setItemMeta(meta);

        return item;
    }
}
