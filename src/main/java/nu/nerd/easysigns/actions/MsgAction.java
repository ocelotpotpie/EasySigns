package nu.nerd.easysigns.actions;

import nu.nerd.easysigns.SignData;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;


public class MsgAction extends SignAction {


    private SignData sign;
    private String message;
    boolean valid = true;


    public MsgAction(SignData sign, String[] args) {
        this.sign = sign;
        if (args.length > 0) {
            message = String.join(" ", args);
        } else {
            valid = false;
        }
    }


    public MsgAction(SignData sign, ConfigurationSection attributes) {
        this.sign = sign;
        this.message = attributes.getString("message");
    }


    public String getName() {
        return "msg";
    }


    public String getUsage() {
        return "<message...>";
    }


    public String getHelpText() {
        return "Sends the player a message. Color codes with & are supported.";
    }


    public String toString() {
        return String.format("%s %s", getName(), ChatColor.translateAlternateColorCodes('&', message));
    }


    public boolean isValid() {
        return valid;
    }


    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        return map;
    }


    public void action(Player player) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }


}
