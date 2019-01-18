package nu.nerd.easysigns.actions;


import nu.nerd.easysigns.SignData;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class HungerAction extends SignAction {


    private SignData sign;


    public HungerAction(SignData sign, String[] args) {
        this.sign = sign;
    }


    public HungerAction(SignData sign, ConfigurationSection attributes) {
        this.sign = sign;
    }


    public String getName() {
        return "hunger";
    }


    public String getUsage() {
        return "";
    }


    public String getHelpText() {
        return "Refills a player's hunger bar";
    }


    public boolean isValid() {
        return true;
    }


    public void action(Player player) {
        player.setFoodLevel(20);
        player.setSaturation(20);
    }


}
