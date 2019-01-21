package nu.nerd.easysigns.actions;

import nu.nerd.easysigns.SignData;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class RandLocAction extends SignAction {


    private SignData sign;
    private int distance;
    private boolean valid = true;


    public RandLocAction(SignData sign, String[] args) {
        this.sign = sign;
        try {
            distance = Integer.parseInt(args[0]);
        } catch (NumberFormatException|IndexOutOfBoundsException ex) {
            valid = false;
        }
    }


    public RandLocAction(SignData sign, ConfigurationSection attributes) {
        this.sign = sign;
        this.distance = attributes.getInt("distance");
    }


    public String getName() {
        return "randloc";
    }


    public String getUsage() {
        return "<max_distance>";
    }


    public String getHelpText() {
        return "Randomly spawns the player within <max_distance> away from 0,0.";
    }


    public String toString() {
        return String.format("%s %d", getName(), distance);
    }


    public boolean isValid() {
        return valid;
    }


    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("distance", distance);
        return map;
    }


    public void action(Player player) {
        Random random = new Random();
        int x = -distance + random.nextInt(distance*2);
        int z = -distance + random.nextInt(distance*2);
        int y = player.getWorld().getHighestBlockYAt(x, z);
        player.teleport(new Location(player.getWorld(), x, y, z));
    }


}
