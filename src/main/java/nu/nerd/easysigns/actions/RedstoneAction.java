package nu.nerd.easysigns.actions;

import nu.nerd.easysigns.EasySigns;
import nu.nerd.easysigns.SignData;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.material.Sign;
import org.bukkit.block.data.Powerable;
import org.bukkit.block.data.type.RedstoneWire;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;


public class RedstoneAction extends SignAction {


    private SignData sign;


    private static final BlockFace[] faces = {
            BlockFace.DOWN,
            BlockFace.UP,
            BlockFace.NORTH,
            BlockFace.EAST,
            BlockFace.SOUTH,
            BlockFace.WEST
    };


    public RedstoneAction(SignData sign, String[] args) {
        this.sign = sign;
    }


    public RedstoneAction(SignData sign, ConfigurationSection attributes) {
        this.sign = sign;
    }


    public String getName() {
        return "redstone";
    }


    public String getUsage() {
        return "";
    }


    public String getHelpText() {
        return "Causes the sign to emit a redstone signal.";
    }


    public String toString() {
        return getName();
    }


    public boolean isValid() {
        return true;
    }


    private void setRedstone(Block block, boolean powered) {
        if (block.getBlockData() instanceof RedstoneWire) {
            RedstoneWire wire = (RedstoneWire) block.getBlockData();
            int val = (powered) ? 15 : 0;
            wire.setPower(val);
            block.setBlockData(wire);
        }
        else if (block.getBlockData() instanceof Powerable) {
            Powerable powerable = (Powerable) block.getBlockData();
            powerable.setPowered(powered);
            block.setBlockData(powerable);
        }
        if (powered) {
            EasySigns.instance.getTickingRedstone().add(block);
        } else {
            EasySigns.instance.getTickingRedstone().remove(block);
        }
    }


    public void action(Player player) {
        Block start = sign.getBlock();
        if (sign.getBlock().getType().equals(Material.WALL_SIGN)) {
            //If this is a wall sign, power from the block behind it.
            Sign s = (Sign) start.getState().getData();
            start = start.getRelative(s.getAttachedFace());
        }
        for (BlockFace face : faces) {
            Block block = start.getRelative(face);
            if (block.getBlockData() instanceof RedstoneWire || block.getBlockData() instanceof Powerable) {
                setRedstone(block, true);
                new BukkitRunnable() {
                    public void run() {
                        setRedstone(block, false);
                    }
                }.runTaskLater(EasySigns.instance, 20L);
            }
        }
    }


}
