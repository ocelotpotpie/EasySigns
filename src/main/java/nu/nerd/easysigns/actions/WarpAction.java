package nu.nerd.easysigns.actions;

import nu.nerd.easysigns.SignData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;


public class WarpAction extends SignAction {


    private Location loc;
    private SignData sign;
    private boolean valid = true;


    /**
     * Construct new action from command arguments
     * @param sign the sign this action is being applied to
     * @param args raw command arguments from the CommandExecutor
     */
    public WarpAction(SignData sign, String[] args) {
        this.sign = sign;
        int x, y, z;
        World world;
        try {
            if (args.length == 4) {
                world = Bukkit.getWorld(args[0]);
                x = Integer.parseInt(args[1]);
                y = Integer.parseInt(args[2]);
                z = Integer.parseInt(args[3]);
                loc = new Location(world, x, y, z);
            } else {
                x = Integer.parseInt(args[0]);
                y = Integer.parseInt(args[1]);
                z = Integer.parseInt(args[2]);
                loc = new Location(sign.getBlock().getWorld(), x, y, z);
            }
        } catch (NumberFormatException|IndexOutOfBoundsException ex) {
            valid = false;
        }
    }


    public String getName() {
        return "warp";
    }


    public String getUsage() {
        return "[<world>] <x> <y> <z>";
    }


    public String getHelpText() {
        return "Makes a warp sign. Send the x, y, and z coords, and optionally the world name. " +
                "The user will warp there when they activate the sign.";
    }


    /**
     * Used to serialize the action into BlockStore
     * @return String in the form of "warp world 0 70 0"
     */
    public String toString() {
        return String.format("%s %s %d %d %d", getName(), loc.getWorld().getName(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
    }


    /**
     * The action was successfully constructed with valid arguments
     */
    public boolean isValid() {
        return valid;
    }


    /**
     * The action performed when the sign is clicked
     * @param player the player clicking the sign
     */
    public void action(Player player) {
        loc.setDirection(player.getLocation().getDirection()); //maintain same rotation
        player.teleport(loc);
    }


}
