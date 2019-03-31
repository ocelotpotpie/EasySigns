package nu.nerd.easysigns.actions;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Abstract base class for sign actions. Actions, such as WarpAction, inherit
 * from here. Constructors, when coming from a command, should take SignData
 * sign, String[] args and make isValid() return false if a valid action could
 * not be constructed. When loading an action from BlockStore, the constructor
 * with SignData sign, ConfigurationSection attributes will be used.
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
     * Was the action successfully constructed with valid arguments? This is
     * used instead of having the constructor throw an exception, because you
     * can't really get at the usage or help text without a class...
     * 
     * @return true if the action was successfully constructed
     */
    abstract public boolean isValid();

    /**
     * If this action may need to prevent further actions from running, this can
     * be overridden and selectively return true
     * 
     * @return false unless subsequent actions should be skipped
     */
    public boolean shouldExit(Player player) {
        return false;
    }

    /**
     * If the action class has any attributes to persist to BlockStore, it
     * should override this method and supply a key/value map of the objects.
     * When the signs are loaded, one of the two constructors will load them
     * with the Bukkit ConfigurationSection API.
     */
    public Map<String, Object> serialize() {
        return new HashMap<>();
    }

    /**
     * This should be overriden so /easy-sign-info will show the correct
     * representation of the action stored. For a simple action with no
     * arguments, this can be left as just the name.
     */
    @Override
    public String toString() {
        return getName();
    }

    /**
     * The action performed when the sign is clicked
     * 
     * @param player the player clicking the sign
     */
    abstract public void action(Player player);

    /**
     * Translate formatting codes beginning with '&' and replace doubled-up
     * ampersands with a single ampersand.
     * 
     * @param message the message string to be translated.
     * @return the translated message.
     */
    public static String translate(String message) {
        return ChatColor.translateAlternateColorCodes('&', message.replace("&&", "\f")).replace("\f", "&");
    }
}
