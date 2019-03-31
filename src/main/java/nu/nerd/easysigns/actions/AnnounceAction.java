package nu.nerd.easysigns.actions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import net.sothatsit.blockstore.BlockStoreApi;
import nu.nerd.easysigns.EasySigns;
import nu.nerd.easysigns.SignData;

public class AnnounceAction extends SignAction {

    private final SignData sign;
    private String message;
    boolean valid = true;

    public AnnounceAction(SignData sign, String[] args) {
        this.sign = sign;
        if (args.length > 0) {
            message = String.join(" ", args);
            message = translate(message);
        } else {
            valid = false;
        }
    }

    public AnnounceAction(SignData sign, ConfigurationSection attributes) {
        this.sign = sign;
        this.message = attributes.getString("message");
    }

    @Override
    public String getName() {
        return "announce";
    }

    @Override
    public String getUsage() {
        return "<message>";
    }

    @Override
    public String getHelpText() {
        return "Sets up an announcement sign. The message is broadcast (only once) when a player " +
               "clicks the sign. The message supports colors with the same notation as /signtext, " +
               "and %s is replaced with the player's name.";
    }

    @Override
    public String toString() {
        return String.format("%s %s", getName(), message);
    }

    @Override
    public boolean isValid() {
        return valid;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        return map;
    }

    private boolean hasUsed(Player player) {
        String[] used = (String[]) BlockStoreApi.getBlockMeta(sign.getBlock(), EasySigns.instance, "announce");
        if (used != null) {
            for (String id : used) {
                if (id.equalsIgnoreCase(player.getUniqueId().toString())) {
                    return true;
                }
            }
        }
        return false;
    }

    private void setUsed(Player player) {
        String[] newUsed;
        String[] oldUsed = (String[]) BlockStoreApi.getBlockMeta(sign.getBlock(), EasySigns.instance, "announce");
        if (oldUsed != null && oldUsed.length > 0) {
            newUsed = Arrays.copyOf(oldUsed, oldUsed.length + 1);
            newUsed[newUsed.length - 1] = player.getUniqueId().toString();
        } else {
            newUsed = new String[] { player.getUniqueId().toString() };
        }
        BlockStoreApi.setBlockMeta(sign.getBlock(), EasySigns.instance, "announce", newUsed);
    }

    @Override
    public void action(Player player) {
        if (!hasUsed(player)) {
            player.getServer().broadcastMessage(message.replace("%s", player.getName()));
            setUsed(player);
        } else {
            player.sendMessage(ChatColor.GREEN + "[SIGN] " + ChatColor.WHITE + "You can only announce here once!");
        }
    }

}
