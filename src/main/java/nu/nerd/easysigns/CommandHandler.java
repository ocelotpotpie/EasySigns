package nu.nerd.easysigns;

import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;


public class CommandHandler implements TabExecutor {


    private EasySigns plugin;


    public CommandHandler() {
        plugin = EasySigns.instance;
        plugin.getCommand("easy-sign").setExecutor(this);
        plugin.getCommand("easy-sign-delete").setExecutor(this);
        plugin.getCommand("easy-sign-info").setExecutor(this);
        plugin.getCommand("easy-sign-remove").setExecutor(this);
        plugin.getCommand("easy-sign-reorder").setExecutor(this);
    }


    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You must be a player to use EasySigns.");
            return true;
        }
        switch (cmd.getName().toLowerCase()) {
            case "easy-sign": addAction(sender, args);
                break;
            case "easy-sign-delete": deleteSign(sender);
                break;
            case "easy-sign-info": signInfo(sender);
                break;
            case "easy-sign-remove": removeAction(sender, args);
                break;
            case "easy-sign-reorder": reorderActions(sender, args);
                break;
        }
        return true;
    }


    /**
     * Suppress tab completion, except for the /easy-sign command, which should
     * suggest valid actions.
     */
    public List<String> onTabComplete(CommandSender commandSender, Command cmd, String s, String[] strings) {
        if (cmd.getName().equalsIgnoreCase("easy-sign")) {
            //todo: tab complete valid sign actions
            return new ArrayList<>();
        } else {
            return new ArrayList<>();
        }
    }


    /**
     * Add an action to a sign, registering it if it's not yet a valid Easysign
     */
    private void addAction(CommandSender sender, String[] args) {
        return;
    }


    /**
     * Remove all actions from a sign and unregister it as an EasySign
     */
    private void deleteSign(CommandSender sender) {
        return;
    }


    /**
     * Determine if this is a valid EasySign and list actions on it
     */
    private void signInfo(CommandSender sender) {
        return;
    }


    /**
     * Remove the specified action from the sign
     */
    private void removeAction(CommandSender sender, String[] args) {
        return;
    }


    /**
     * Reorder an action on a sign
     */
    private void reorderActions(CommandSender sender, String[] args) {
        return;
    }


}
