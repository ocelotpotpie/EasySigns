package nu.nerd.easysigns.actions;


import nu.nerd.easysigns.SignData;
import org.bukkit.entity.Player;

public class CmdAction extends SignAction {


    private SignData sign;
    private String command;
    boolean valid = true;


    public CmdAction(SignData sign, String[] args) {
        this.sign = sign;
        if (args.length > 0) {
            command = String.join(" ", args);
        } else {
            valid = false;
        }
    }


    public String getName() {
        return "cmd";
    }


    public String getUsage() {
        return "<command>";
    }


    public String getHelpText() {
        return "Runs a command as the user. Omit the leading slash.";
    }


    public String toString() {
        return String.format("%s %s", getName(), command);
    }


    public boolean isValid() {
        return true;
    }


    public void action(Player player) {
        player.performCommand(command);
    }


}
