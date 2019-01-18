package nu.nerd.easysigns.actions;

import nu.nerd.easysigns.SignData;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class GiveAction extends SignAction {


    private SignData sign;
    private ItemStack item;
    int slot = -1;
    boolean valid = true;


    public GiveAction(SignData sign, String[] args) {
        this.sign = sign;
        try {
            if (sign.getEditingPlayer() != null) {
                //Action is being created by command
                String itemName = args[0];
                if (itemName.equalsIgnoreCase("held")) {
                    item = sign.getEditingPlayer().getInventory().getItemInMainHand();
                    if (args.length > 1) {
                        slot = Integer.parseInt(args[1]);
                    }
                } else {
                    item = new ItemStack(Material.valueOf(itemName.toUpperCase()), Integer.parseInt(args[1]));
                    if (args.length > 2) {
                        slot = Integer.parseInt(args[2]);
                    }
                }
            } else {
                //Action is being loaded from BlockStore
                slot = Integer.parseInt(args[0]);
                item = deserializeItemStack(String.join(" ", Arrays.copyOfRange(args, 1, args.length)));
            }
        } catch (IndexOutOfBoundsException|IllegalArgumentException ex) {
            valid = false;
        }
    }


    public GiveAction(SignData sign, ConfigurationSection attributes) {
        this.sign = sign;
        this.item = (ItemStack) attributes.get("item");
        this.slot = attributes.getInt("slot");
    }


    public String getName() {
        return "give";
    }


    public String getUsage() {
        return "<item> <qty> [<slot>] or held [<slot>]";
    }


    public String getHelpText() {
        return "Gives the player an item. Place it in the specified slot number, or a free slot if not specified. " +
                "If 'held' is used in place of an item name, the item in your main hand will be used.";
    }


    public String toString() {
        return String.format("%s %d %s", getName(), slot, serializeItemStack());
    }


    public String displayInfo() {
        if (slot > -1) {
            return String.format("%s %s %d", getName(), item.toString(), slot);
        } else {
            return String.format("%s %s", getName(), item.toString());
        }
    }


    public boolean isValid() {
        return valid;
    }


    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("item", item);
        map.put("slot", slot);
        return map;
    }


    private String serializeItemStack() {
        FileConfiguration yaml = new YamlConfiguration();
        yaml.set("ItemStack", item);
        return yaml.saveToString();
    }


    private ItemStack deserializeItemStack(String str) {
        try {
            FileConfiguration yaml = new YamlConfiguration();
            yaml.loadFromString(str);
            return (ItemStack) yaml.get("ItemStack");
        } catch (InvalidConfigurationException ex) {
            return null;
        }
    }


    public void action(Player player) {
        if (slot > -1) {
            player.getInventory().setItem(slot, item);
        } else {
            player.getInventory().addItem(item);
        }
        //todo: give item and account for possibility of only having room for x/y items
    }


}
