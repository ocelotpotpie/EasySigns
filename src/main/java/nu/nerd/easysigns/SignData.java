package nu.nerd.easysigns;

import nu.nerd.easysigns.actions.SignAction;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;


/**
 * Representation of an EasySign
 */
public class SignData {


    private Block block;
    private List<SignAction> actions;


    public SignData(Block block) {
        this.block = block;
        this.actions = new ArrayList<>();
    }


    public Block getBlock() {
        return block;
    }


    public List<SignAction> getActions() {
        return actions;
    }


}
