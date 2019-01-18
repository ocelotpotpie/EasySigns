package nu.nerd.easysigns.actions;


import nu.nerd.easysigns.SignData;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;


public class SleepAction extends SignAction {


    private SignData sign;


    public SleepAction(SignData sign, String[] args) {
        this.sign = sign;
    }


    public SleepAction(SignData sign, ConfigurationSection attributes) {
        this.sign = sign;
    }


    public String getName() {
        return "sleep";
    }


    public String getUsage() {
        return "";
    }


    public String getHelpText() {
        return "Makes this a sleep sign. Anyone that activates the sign will have their bed respawn point set";
    }


    public String toString() {
        return getName();
    }


    public boolean isValid() {
        return true;
    }


    public void action(Player player) {
        player.setBedSpawnLocation(player.getLocation(), true);
        player.sendMessage(ChatColor.LIGHT_PURPLE + "Your bed has now been set!");
    }


}
