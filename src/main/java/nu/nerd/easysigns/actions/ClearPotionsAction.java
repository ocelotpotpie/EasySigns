package nu.nerd.easysigns.actions;

import nu.nerd.easysigns.SignData;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;


public class ClearPotionsAction extends SignAction {


    private SignData sign;


    public ClearPotionsAction(SignData sign, String[] args) {
        this.sign = sign;
    }


    public ClearPotionsAction(SignData sign, ConfigurationSection attributes) {
        this.sign = sign;
    }


    public String getName() {
        return "clearpotions";
    }


    public String getUsage() {
        return "";
    }


    public String getHelpText() {
        return "Clears all potion effects.";
    }


    public String toString() {
        return getName();
    }


    public boolean isValid() {
        return true;
    }


    public void action(Player player) {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
    }


}
