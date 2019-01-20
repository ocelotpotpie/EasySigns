package nu.nerd.easysigns.actions;


import net.sothatsit.blockstore.BlockStoreApi;
import nu.nerd.easysigns.EasySigns;
import nu.nerd.easysigns.SignData;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class MaxAction extends SignAction {


    private SignData sign;
    private int uses;
    boolean valid = true;


    public MaxAction(SignData sign, String[] args) {
        this.sign = sign;
        try {
            uses = Integer.parseInt(args[0]);
        } catch (IndexOutOfBoundsException|IllegalArgumentException ex) {
            valid = false;
        }
    }


    public MaxAction(SignData sign, ConfigurationSection attributes) {
        this.sign = sign;
        this.uses = attributes.getInt("uses");
    }


    public String getName() {
        return "max";
    }


    public String getUsage() {
        return "<uses>";
    }


    public String getHelpText() {
        return "Allows the sign to be used <uses> times and no more. No other commands will be run once limit is reached.";
    }


    public String toString() {
        return String.format("%s %d", getName(), uses);
    }


    public boolean isValid() {
        return valid;
    }


    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("uses", uses);
        return map;
    }


    private int timesUsed(Player player) {
        FileConfiguration yaml = new YamlConfiguration();
        try {
            String str = (String) BlockStoreApi.getBlockMeta(sign.getBlock(), EasySigns.instance, "max");
            if (str == null) return 0;
            yaml.loadFromString(str);
        } catch (InvalidConfigurationException ex) {
            ex.printStackTrace();
        }
        return yaml.getInt(player.getUniqueId().toString(), 0);
    }


    private void incrementUsed(Player player) {
        FileConfiguration yaml = new YamlConfiguration();
        try {
            String str = (String) BlockStoreApi.getBlockMeta(sign.getBlock(), EasySigns.instance, "max");
            if (str != null) {
                yaml.loadFromString(str);
            }
            int times = yaml.getInt(player.getUniqueId().toString(), 0);
            yaml.set(player.getUniqueId().toString(), times + 1);
            BlockStoreApi.setBlockMeta(sign.getBlock(), EasySigns.instance, "max", yaml.saveToString());
        } catch (InvalidConfigurationException ex) {
            ex.printStackTrace();
        }
    }


    public boolean shouldExit(Player player) {
        //don't process further actions if the player has used this sign already
        return timesUsed(player) > uses;
    }


    public void action(Player player) {
        int pTimes = timesUsed(player);
        if (pTimes <= uses) {
            incrementUsed(player);
            pTimes++;
        }
        if (pTimes > uses) {
            player.sendMessage(String.format("%sMaximum uses reached! (%d)", ChatColor.GREEN, uses));
        }
    }


}
