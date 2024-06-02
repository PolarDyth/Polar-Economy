package me.polardyth.polareconomy.listeners;

import me.polardyth.polareconomy.economy.balances.BalanceType;
import me.polardyth.polareconomy.economy.balances.interfaces.IBalanceManager;
import me.polardyth.polareconomy.economy.balances.interfaces.IEconomyManager;
import me.polardyth.polareconomy.economy.balances.interfaces.IStoredMoney;
import me.polardyth.polareconomy.menus.banker.BankerMainPage;
import me.polardyth.polareconomy.utils.MiniColor;
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

    private final IEconomyManager economyManager;
    private final Material blockSave;
    private final Location location;
    private final boolean deposit;

    public SignGUIListener(IEconomyManager economyManager, Location location, Material blockSave, boolean deposit) {
        this.economyManager = economyManager;
        this.location = location;
        this.blockSave = blockSave;
        this.deposit = deposit;
    }

    @EventHandler
    public void onSignChange(SignChangeEvent event) {

        IBalanceManager purse = economyManager.getBalanceManager(BalanceType.PURSE);
        IStoredMoney bank = economyManager.getStoredMoneyManager(BalanceType.BANK);

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
                int amount = Integer.parseInt(MiniColor.TEXT.serialise(lines));
                if (deposit) {
                    bank.depositFunds(amount, player, purse);
                } else {
                    bank.withdrawFunds(amount, player, purse);
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
