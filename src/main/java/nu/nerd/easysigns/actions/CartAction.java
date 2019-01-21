package nu.nerd.easysigns.actions;

import nu.nerd.easysigns.SignData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;


public class CartAction extends SignAction {


    private SignData sign;
    private Location loc;
    private boolean valid = true;


    public CartAction(SignData sign, String[] args) {
        this.sign = sign;
        int x, y, z;
        World world;
        try {
            if (args.length == 4) {
                world = Bukkit.getWorld(args[0]);
                x = Integer.parseInt(args[1]);
                y = Integer.parseInt(args[2]);
                z = Integer.parseInt(args[3]);
                loc = new Location(world, x, y, z);
            } else {
                x = Integer.parseInt(args[0]);
                y = Integer.parseInt(args[1]);
                z = Integer.parseInt(args[2]);
                loc = new Location(sign.getBlock().getWorld(), x, y, z);
            }
        } catch (NumberFormatException|IndexOutOfBoundsException ex) {
            valid = false;
        }
    }


    public CartAction(SignData sign, ConfigurationSection attributes) {
        this.sign = sign;
        this.loc = (Location) attributes.get("loc");
    }


    public String getName() {
        return "cart";
    }


    public String getUsage() {
        return "[<world>] <x> <y> <z>";
    }


    public String getHelpText() {
        return "Spawns a minecart at the specified location.";
    }


    public String toString() {
        return String.format("%s %s %d %d %d", getName(), loc.getWorld().getName(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
    }


    public boolean isValid() {
        return valid;
    }


    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("loc", loc);
        return map;
    }


    public void action(Player player) {
        loc.getWorld().spawnEntity(loc, EntityType.MINECART);
    }


}
