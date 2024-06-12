package me.polardyth.polareconomy.commands;

import me.polardyth.polareconomy.menus.banker.BankerMainPage;
import me.polardyth.polareconomy.economy.EconomyManager;
import me.polardyth.polareconomy.utils.MessageUtil;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BankCommand implements CommandExecutor {

    private final EconomyManager economyManager;

    public BankCommand(EconomyManager economyManager) {
        this.economyManager = economyManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendRichMessage(MessageUtil.getNotPlayerMessage());
            return true;
        }

        BankerMainPage menu = new BankerMainPage(economyManager, player);
        menu.open(player);

        return true;
    }
}
