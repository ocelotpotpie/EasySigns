package nu.nerd.easysigns.actions;

import nu.nerd.easysigns.SignData;
import org.bukkit.entity.Player;

/**
 * Abstract base class for sign actions.
 * Actions, such as WarpAction, inherit from here.
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
     * @param sign the sign clicked
     */
    abstract public void action(Player player, SignData sign);

    //todo: some way to serialize/deserialize from BlockStore

}
