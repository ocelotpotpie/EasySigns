package nu.nerd.easysigns.actions;

import nu.nerd.easysigns.EasySigns;
import nu.nerd.easysigns.SignData;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;


public class HealAction extends SignAction {


    private SignData sign;
    int gap = 0;
    boolean valid = true;


    public HealAction(SignData sign, String[] args) {
        this.sign = sign;
        if (args.length > 0) {
            try {
                gap = Integer.parseInt(args[0]);
            } catch (NumberFormatException ex) {
                valid = false;
            }
        }
    }


    public HealAction(SignData sign, ConfigurationSection attributes) {
        this.sign = sign;
        this.gap = attributes.getInt("gap");
    }


    public String getName() {
        return "heal";
    }


    public String getUsage() {
        return "[<gap>]";
    }


    public String getHelpText() {
        return "Refills a player's health. If gap is provided (it defaults to 0) then the player gets a half a " +
                "heart every gap seconds. 0 means fill it up instantly.";
    }


    public String toString() {
        return String.format("%s %d", getName(), gap);
    }


    public boolean isValid() {
        return valid;
    }


    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("gap", gap);
        return map;
    }


    public void action(Player player) {
        if (gap == 0) {
            player.setHealth(20);
        } else {
            new BukkitRunnable() {
                int i = (20 - (int)Math.floor(player.getHealth()));
                public void run() {
                    if (player.getHealth() < 20 && i > 0) {
                        player.setHealth(player.getHealth() + 1);
                    } else {
                        player.sendMessage("Done!");
                        this.cancel();
                    }
                    player.sendMessage("i: " + i + ", health: " + player.getHealth());
                    i--;
                }
            }.runTaskTimer(EasySigns.instance, 20L, 20L * gap);
        }
    }


}
