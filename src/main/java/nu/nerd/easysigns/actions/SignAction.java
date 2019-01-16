package nu.nerd.easysigns.actions;

import org.bukkit.entity.Player;

/**
 * Abstract base class for sign actions.
 * Actions, such as WarpAction, inherit from here.
 * Actions are serialized by the toString() method returning the same
 * String passed to /easy-sign. e.g. WarpAction may return "warp world 0 70 0"
 * Constructors should take SignData sign, String[] args and make isValid()
 * return false if a valid action could not be constructed.
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
     * Was the action successfully constructed with valid arguments?
     * This is used instead of having the constructor throw an exception,
     * because you can't really get at the usage or help text without a class...
     * @return true if the action was successfully constructed
     */
    abstract public boolean isValid();

    /**
     * If this action may need to prevent further actions from running, this can
     * be overridden and selectively return true
     * @return false unless subsequent actions should be skipped
     */
    public boolean shouldExit(Player player) {
        return false;
    }

    /**
     * The action performed when the sign is clicked
     * @param player the player clicking the sign
     */
    abstract public void action(Player player);

}
