package nu.nerd.easysigns.actions;

import org.bukkit.entity.Player;

/**
 * Abstract base class for sign actions.
 * Actions, such as WarpAction, inherit from here.
 * Actions are serialized by the toString() method returning the same
 * String passed to /easy-sign. e.g. WarpAction may return "warp world 0 70 0"
 * Constructors should take SignData sign, String[] args and throw
 * SignActionException if their arguments are malformed.
 */
public abstract class SignAction {

    /**
     * The canonical name of this action, for user commands and serialization
     */
    abstract public String getName();

    /**
     * The arguments this action takes
     */
    abstract public String getUsage();

    /**
     * The help text (excluding arguments) displayed by the command
     */
    abstract public String getHelpText();

    /**
     * The action performed when the sign is clicked
     * @param player the player clicking the sign
     */
    abstract public void action(Player player);

}
