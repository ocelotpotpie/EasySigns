package nu.nerd.easysigns.actions;

import nu.nerd.easysigns.SignData;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;


public class TeleportBedAction extends SignAction {


    private SignData sign;


    public TeleportBedAction(SignData sign, String[] args) {
        this.sign = sign;
    }


    public TeleportBedAction(SignData sign, ConfigurationSection attributes) {
        this.sign = sign;
    }


    public String getName() {
        return "tpbed";
    }


    public String getUsage() {
        return "";
    }


    public String getHelpText() {
        return "Teleports the player back to their bed.";
    }


    public String toString() {
        return getName();
    }


    public boolean isValid() {
        return true;
    }


    public void action(Player player) {
        if (player.getBedSpawnLocation() != null) {
            player.sendMessage("Returning to your bed");
            Location loc = player.getBedSpawnLocation();
            loc.setDirection(player.getLocation().getDirection());
            player.teleport(loc);
        } else {
            player.sendMessage("You don't have a bed set!");
        }
    }


}
