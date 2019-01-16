package nu.nerd.easysigns;

import nu.nerd.easysigns.actions.SignAction;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
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
    public List<String> onTabComplete(CommandSender commandSender, Command cmd, String alias, String[] args) {
        if (cmd.getName().equalsIgnoreCase("easy-sign") && args.length == 1) {
            List<String> completions = new ArrayList<>();
            String action = args[0];
            if (action.equals("")) {
                completions.addAll(plugin.getValidActions());
            } else {
                plugin.getValidActions().stream().filter(s -> s.startsWith(args[0])).forEach(completions::add);
            }
            return completions;
        } else {
            return new ArrayList<>();
        }
    }


    /**
     * Add an action to a sign, registering it if it's not yet a valid Easysign
     */
    @SuppressWarnings("unchecked")
    private void addAction(CommandSender sender, String[] args) {

        Player player = (Player) sender;
        Block looking = player.getTargetBlock(null, 5);
        SignData sign;

        //Print command help if no action is specified
        if (args.length < 1) {
            addActionCommandHelpText(sender);
            return;
        }

        //Exit early if the player is not looking at a sign
        if (!plugin.isSign(looking)) {
            sender.sendMessage(ChatColor.RED + "That's not a sign. Look at a sign to run this command.");
            return;
        }

        //Create a new sign, or load an existing one
        if (plugin.isEasySign(looking)) {
            sign = SignData.load(looking);
        } else {
            sign = new SignData(looking);
        }

        String actionName = args[0];
        String[] arguments = Arrays.copyOfRange(args, 1, args.length);
        Class c = plugin.getActionClassByName(actionName.toLowerCase());

        if (c == null) {
            sender.sendMessage(ChatColor.RED + "Invalid action: " + actionName + ". Run /easy-sign for usage.");
            return;
        }

        //Instantiate the correct class and save it to the sign
        try {
            SignAction action = (SignAction) c.getConstructor(SignData.class, String[].class).newInstance(sign, arguments);
            if (action.isValid()) {
                sign.getActions().add(action);
                sign.save();
                sender.sendMessage(ChatColor.LIGHT_PURPLE + "Easy sign action added!");
            } else {
                sender.sendMessage(String.format("%sUsage: /easy-sign %s %s", ChatColor.RED, action.getName(), action.getUsage()));
                sender.sendMessage(action.getHelpText());
            }
        }
        catch (Exception ex) {
            sender.sendMessage(ChatColor.RED + "Error running command.");
            ex.printStackTrace();
        }

    }


    /**
     * Remove all actions from a sign and unregister it as an EasySign
     */
    private void deleteSign(CommandSender sender) {
        Player player = (Player) sender;
        Block looking = player.getTargetBlock(null, 5);

        if (!plugin.isSign(looking) || !plugin.isEasySign(looking)) {
            sender.sendMessage(ChatColor.RED + "That doesn't appear to be an easy sign.");
            return;
        }

        SignData.delete(looking);
        sender.sendMessage(ChatColor.RED + "Sign removed");
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


    /**
     * Print the big help text block when /easy-sign is run with no arguments
     */
    @SuppressWarnings("unchecked")
    private void addActionCommandHelpText(CommandSender sender) {
        Player player = (Player) sender;
        SignData sign = new SignData(player.getLocation().getBlock());
        String actionFmt = ChatColor.BLUE + "%s %s" + ChatColor.WHITE + " - %s";
        String cmdFmt = ChatColor.YELLOW + "%s " + ChatColor.GRAY + "- %s";

        //List available actions and their usage
        sender.sendMessage(ChatColor.RED + "Usage: /easy-sign <type> [<args>]");
        for (Class c : plugin.getActionClasses()) {
            try {
                SignAction action = (SignAction) c.getConstructor(SignData.class, String[].class).newInstance(sign, new String[0]);
                sender.sendMessage(String.format(actionFmt, action.getName(), action.getUsage(), action.getHelpText()));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        //More commands
        sender.sendMessage(ChatColor.GOLD + "Multiple tasks can be added to each sign, and are run in order.");
        sender.sendMessage(ChatColor.GOLD + "Related commands:");
        sender.sendMessage(String.format(cmdFmt, "/easy-sign-info", "List all actions on a sign."));
        sender.sendMessage(String.format(cmdFmt, "/easy-sign-remove", "Remove a single action from a sign."));
        sender.sendMessage(String.format(cmdFmt, "/easy-sign-reorder", "Move an action from one position to another."));
        sender.sendMessage(String.format(cmdFmt, "/easy-sign-delete", "Remove all actions from a sign."));

        //Colors
        sender.sendMessage(ChatColor.translateAlternateColorCodes(
                '&', "Color reference: &00 &11 &22 &33 &44 &55 &66 &77 &88 &99 &AA &BB &CC &DD &EE &FF"
        ));

        //Tips
        sender.sendMessage(ChatColor.GOLD + "Remember, you also have access to /signtext");
        sender.sendMessage("" + ChatColor.BLUE + ChatColor.UNDERLINE + "http://wiki.nerd.nu/wiki/EasySign");
    }


}
