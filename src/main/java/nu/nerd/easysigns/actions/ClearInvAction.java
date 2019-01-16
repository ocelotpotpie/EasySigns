package nu.nerd.easysigns.actions;


import nu.nerd.easysigns.SignData;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ClearInvAction extends SignAction {


    private SignData sign;


    public ClearInvAction(SignData sign, String[] args) {
        this.sign = sign;
    }


    public String getName() {
        return "ci";
    }


    public String getUsage() {
        return "";
    }


    public String getHelpText() {
        return "Clears the player's inventory";
    }


    public String toString() {
        return getName();
    }


    public boolean isValid() {
        return true;
    }


    public void action(Player player) {
        player.getInventory().clear();
    }


}
