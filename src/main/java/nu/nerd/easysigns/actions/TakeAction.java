package nu.nerd.easysigns.actions;


import nu.nerd.easysigns.EasySigns;
import nu.nerd.easysigns.SignData;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TakeAction extends SignAction {


    private SignData sign;
    private ItemStack item;
    private String failMessage;
    boolean valid = true;


    public TakeAction(SignData sign, String[] args) {
        this.sign = sign;
        try {
            String itemName = args[0];
            item = new ItemStack(Material.valueOf(itemName.toUpperCase()), Integer.parseInt(args[1]));
            if (args.length > 2) {
                failMessage = String.join(" ", Arrays.copyOfRange(args, 2, args.length));
            } else {
                valid = false;
            }
        } catch (IndexOutOfBoundsException|IllegalArgumentException ex) {
            valid = false;
        }
    }


    public TakeAction(SignData sign, ConfigurationSection attributes) {
        this.sign = sign;
        this.item = (ItemStack) attributes.get("item");
        this.failMessage = attributes.getString("failmessage");
    }


    public String getName() {
        return "take";
    }


    public String getUsage() {
        return "<item> <qty> <failmsg>";
    }


    public String getHelpText() {
        return "Takes an item from a player. If they don't have enough, the failmsg is shown, " +
                "and no other commands will be run.";
    }


    public String toString() {
        return String.format("%s %s %s", getName(), item.toString(), ChatColor.translateAlternateColorCodes('&', failMessage));
    }


    public boolean isValid() {
        return valid;
    }


    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("item", item);
        map.put("failmessage", failMessage);
        return map;
    }


    public boolean shouldExit(Player player) {
        if (player.hasMetadata("easysigns.take")) {
            //just took the item from the player, don't exit this time
            player.removeMetadata("easysigns.take", EasySigns.instance);
            return false;
        }
        return !player.getInventory().contains(item.getType(), item.getAmount());
    }


    public void action(Player player) {
        if (player.getInventory().contains(item.getType(), item.getAmount())) {
            player.getInventory().removeItem(item);
            player.setMetadata("easysigns.take", new FixedMetadataValue(EasySigns.instance, true));
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', failMessage));
        }
    }


}
