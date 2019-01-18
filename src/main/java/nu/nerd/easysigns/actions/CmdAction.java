package nu.nerd.easysigns.actions;


import nu.nerd.easysigns.SignData;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class CmdAction extends SignAction {


    private SignData sign;
    private String command;
    boolean valid = true;


    public CmdAction(SignData sign, String[] args) {
        this.sign = sign;
        if (args.length > 0) {
            command = String.join(" ", args);
        } else {
            valid = false;
        }
    }


    public CmdAction(SignData sign, ConfigurationSection attributes) {
        this.sign = sign;
        this.command = attributes.getString("command");
    }


    public String getName() {
        return "cmd";
    }


    public String getUsage() {
        return "<command>";
    }


    public String getHelpText() {
        return "Runs a command as the user. Omit the leading slash.";
    }


    public String toString() {
        return String.format("%s %s", getName(), command);
    }


    public boolean isValid() {
        return true;
    }


    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("command", command);
        return map;
    }


    public void action(Player player) {
        player.performCommand(command);
    }


}
