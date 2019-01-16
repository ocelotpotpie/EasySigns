package nu.nerd.easysigns.actions;


import nu.nerd.easysigns.SignData;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CheckEmptyInvAction extends SignAction {


    private SignData sign;
    private String message;


    public CheckEmptyInvAction(SignData sign, String[] args) {
        this.sign = sign;
        if (args.length > 0) {
            message = String.join(" ", args);
        }
    }


    public String getName() {
        return "check-empty-inventory";
    }


    public String getUsage() {
        return "[<message>]";
    }


    public String getHelpText() {
        return "If the player's inventory is not empty, do not execute any subsequent sign actions and show <message>, " +
                "if specified, or a default message.";
    }


    public String toString() {
        if (message != null && message.length() > 0) {
            return String.format("%s %s", getName(), message);
        } else {
            return getName();
        }
    }


    public boolean isValid() {
        return true;
    }


    public boolean shouldExit(Player player) {
        //don't process further actions if inventory contents > 0
        return hasEmptyInv(player);
    }


    public void action(Player player) {
        if (hasEmptyInv(player)) {
            player.sendMessage(ChatColor.RED + message);
        }
    }


    private boolean hasEmptyInv(Player player) {
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null) return true;
        }
        return false;
    }


}
