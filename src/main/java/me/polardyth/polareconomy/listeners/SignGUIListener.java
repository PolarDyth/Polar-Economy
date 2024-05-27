package me.polardyth.polareconomy.listeners;

import me.polardyth.polareconomy.menus.bankermenu.BankerMainPage;
import me.polardyth.polareconomy.systems.MiniColor;
import me.polardyth.polareconomy.utils.EconomyManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.block.sign.Side;
import org.bukkit.block.sign.SignSide;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignGUIListener implements Listener {

    private final EconomyManager economyManager;
    private final Location tempSignLocation = new Location(Bukkit.getWorlds().get(0), 0, 0, 0);;

    public SignGUIListener(EconomyManager economyManager) {
        this.economyManager = economyManager;
    }

    public void openSignGUI(Player player) {
        // Open a sign GUI
        Block block = tempSignLocation.getBlock();
        block.setType(Material.OAK_SIGN);

        Sign sign = (Sign) block.getState();
        SignSide side = sign.getSide(Side.FRONT);
        side.line(1, MiniColor.TEXT.deserialize("^^^^^^^^"));
        side.line(2, MiniColor.TEXT.deserialize("Enter the amount"));
        side.line(3, MiniColor.TEXT.deserialize("to withdraw"));
        sign.update(true);
        player.openSign(sign, Side.FRONT);
    }

    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        // Handle sign change event
        double amount = 0;
        if (event.getBlock().getLocation().equals(tempSignLocation)) {
            // Handle sign change event for the temporary sign
            Player player = event.getPlayer();
            Component lines = event.line(0);
            try {
                // Handle the amount
                assert lines != null;
                amount = Double.parseDouble(MiniColor.TEXT.serialise(lines));
                player.sendRichMessage("<green>Successfully withdrew " + Double.toString(amount));
                BankerMainPage mainPaige = new BankerMainPage(economyManager, player);
                mainPaige.open(player);

            } catch (NumberFormatException e) {
                player.sendMessage(MiniColor.TEXT.deserialize("<red>Invalid amount!"));
            }

            event.getBlock().setType(Material.AIR);
        }
    }
}
