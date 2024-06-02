package me.polardyth.polareconomy.menus;

import me.polardyth.polareconomy.PolarSettings;
import me.polardyth.polareconomy.utils.MiniColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.block.sign.Side;
import org.bukkit.block.sign.SignSide;
import org.bukkit.entity.Player;

public class SignGUI {

    private Material blockSave;
    private final Location location;

    public SignGUI(Player player) {

        location = player.getLocation().getBlock().getLocation();
        location.setY(location.getY() - 4);
        Block block = location.getBlock();
        blockSave = block.getType();
        block.setType(Material.OAK_SIGN);
        Sign sign = (Sign) block.getState();
        SignSide side =  sign.getSide(Side.FRONT);
        side.line(1, MiniColor.TEXT.deserialize("^^^^^^^^"));
        side.line(2, MiniColor.TEXT.deserialize("Enter amount"));
        side.line(3, MiniColor.TEXT.deserialize("to withdraw"));
        sign.update();

        PolarSettings.getPlugin().getServer().getScheduler().scheduleSyncDelayedTask(PolarSettings.getPlugin(), () -> {
            player.openSign(sign, Side.FRONT);
        }, 2);
    }

    public Location getLocation() {
        return location;
    }

    public Material getBlockSave() {
        return blockSave;
    }
}
