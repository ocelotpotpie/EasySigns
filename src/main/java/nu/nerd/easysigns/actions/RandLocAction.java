package nu.nerd.easysigns.actions;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import nu.nerd.easysigns.SignData;

public class RandLocAction extends SignAction {

    private final SignData sign;
    private int distance;
    private boolean valid = true;

    public RandLocAction(SignData sign, String[] args) {
        this.sign = sign;
        try {
            distance = Integer.parseInt(args[0]);
        } catch (NumberFormatException | IndexOutOfBoundsException ex) {
            valid = false;
        }
    }

    public RandLocAction(SignData sign, ConfigurationSection attributes) {
        this.sign = sign;
        this.distance = attributes.getInt("distance");
    }

    @Override
    public String getName() {
        return "randloc";
    }

    @Override
    public String getUsage() {
        return "<max_distance>";
    }

    @Override
    public String getHelpText() {
        return "Randomly spawns the player within <max_distance> away from 0,0.";
    }

    @Override
    public String toString() {
        return String.format("%s %d", getName(), distance);
    }

    @Override
    public boolean isValid() {
        return valid;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("distance", distance);
        return map;
    }

    @Override
    public void action(Player player) {
        double angle = 2 * Math.PI * Math.random();
        double range = distance * Math.random();
        int x = (int) (range * Math.cos(angle));
        int z = (int) (range * Math.sin(angle));
        int y = player.getWorld().getHighestBlockYAt(x, z);
        player.teleport(new Location(player.getWorld(), x, y, z));
    }

}
