package nu.nerd.easysigns;

import nu.nerd.easysigns.actions.SignAction;
import nu.nerd.easysigns.actions.SignActionException;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
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
            //todo: recreate command help from CH version
            return;
        }

        //Exit early if the player is not looking at a sign
        if (!plugin.isSign(looking)) {
            sender.sendMessage(ChatColor.RED + "That's not a sign. Look at a sign to run this command.");
            return;
        }

        //Create a new sign, or load an existing one
        if (plugin.isEasySign(looking)) {
            sign = new SignData(looking);
            player.sendMessage("Already a sign. Todo: load it");
        } else {
            sign = new SignData(looking);
        }

        String actionName = args[0];
        String[] arguments = Arrays.copyOfRange(args, 1, args.length);
        Class c = plugin.getActionClassByName(actionName.toLowerCase());

        //Instantiate the correct class and save it to the sign
        try {
            SignAction action = (SignAction) c.getConstructor(SignData.class, String[].class).newInstance(sign, arguments);
            sign.getActions().add(action);
            sign.save();
            sender.sendMessage(ChatColor.LIGHT_PURPLE + "Easy sign action added!");
        }
        catch (InvocationTargetException ex) {
            if (ex.getTargetException() instanceof SignActionException) {
                //todo: print command usage
            }
        }
        catch (NoSuchMethodException|InstantiationException|IllegalAccessException ex) {
            sender.sendMessage(ChatColor.RED + "Invalid action: " + actionName + ". Run /easy-sign for usage.");
        }

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
