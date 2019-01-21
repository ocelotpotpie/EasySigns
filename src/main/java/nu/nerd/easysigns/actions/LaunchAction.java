package nu.nerd.easysigns.actions;


import nu.nerd.easysigns.SignData;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;


public class LaunchAction extends SignAction {


    private SignData sign;
    private Vector vector;
    private boolean valid = true;


    public LaunchAction(SignData sign, String[] args) {
        this.sign = sign;
        try {
            double x = Double.parseDouble(args[0]);
            double y = Double.parseDouble(args[1]);
            double z = Double.parseDouble(args[2]);
            if (Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2)) >= 10) {
                valid = false;
            } else {
                vector = new Vector(x, y, z);
            }
        } catch (NumberFormatException|IndexOutOfBoundsException ex) {
            valid = false;
        }
    }


    public LaunchAction(SignData sign, ConfigurationSection attributes) {
        this.sign = sign;
        this.vector = attributes.getVector("vector");
    }


    public String getName() {
        return "launch";
    }


    public String getUsage() {
        return "<x> <y> <z>";
    }


    public String getHelpText() {
        return "Launches a player with the specified velocity vector. The magnitude of the vector cannot " +
                "exceed 10. That is, sqrt(x^2 + y^2 + z^2) must be < 10.";
    }


    public String toString() {
        return String.format("%s %.2f %.2f %.2f", getName(), vector.getX(), vector.getY(), vector.getZ());
    }


    public boolean isValid() {
        return valid;
    }


    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("vector", vector);
        return map;
    }


    public void action(Player player) {
        player.setVelocity(vector);
    }


}
