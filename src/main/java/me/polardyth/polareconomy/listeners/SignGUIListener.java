package me.polardyth.polareconomy.listeners;

import me.polardyth.polareconomy.menus.bankermenu.BankerMainPage;
import me.polardyth.polareconomy.systems.MiniColor;
import me.polardyth.polareconomy.utils.economy.BankManager;
import me.polardyth.polareconomy.utils.economy.EconomyManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import java.util.logging.Logger;

public class SignGUIListener implements Listener {

    private final EconomyManager economyManager;
    private final Material blockSave;
    private final Location location;
    private final boolean deposit;

    public SignGUIListener(EconomyManager economyManager, Location location, Material blockSave, boolean deposit) {
        this.economyManager = economyManager;
        this.location = location;
        this.blockSave = blockSave;
        this.deposit = deposit;
    }

    @EventHandler
    public void onSignChange(SignChangeEvent event) {

        BankManager bankBalanceEditor = new BankManager(economyManager, event.getPlayer());

        // Handle sign change event
        Logger.getLogger("Minecraft").info("Debug: Block is currently " + event.getBlock().getLocation() + " and the location is " + location);
        if (event.getBlock().getLocation().equals(location)) {
            // Handle sign change event for the temporary sign
            Logger.getLogger("Minecraft").info("Debug: Location correct");
            Player player = event.getPlayer();
            Component lines = event.line(0);
            try {
                // Handle the amount
                assert lines != null;
                double amount = Double.parseDouble(MiniColor.TEXT.serialise(lines));
                if (deposit) {
                    bankBalanceEditor.depositFunds(amount);
                } else {
                    bankBalanceEditor.withdrawFunds(amount);
                }
                BankerMainPage mainPaige = new BankerMainPage(economyManager, player);
                mainPaige.open(player);

            } catch (NumberFormatException e) {
                player.sendMessage(MiniColor.TEXT.deserialize("<red>Invalid amount!"));
            }
        }
        event.getBlock().setType(blockSave);
        HandlerList.unregisterAll(SignGUIListener.this);
    }
}
