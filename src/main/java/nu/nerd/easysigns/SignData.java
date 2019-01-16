package nu.nerd.easysigns;

import net.sothatsit.blockstore.BlockStoreApi;
import nu.nerd.easysigns.actions.SignAction;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


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


    /**
     * Persist the sign to BlockStore
     */
    public void save() {
        List<String> list = actions.stream().map(SignAction::toString).collect(Collectors.toList());
        String[] pack = list.toArray(new String[list.size()]);
        BlockStoreApi.setBlockMeta(block, EasySigns.instance, EasySigns.key, pack);
    }


    /**
     * Load the sign at the specififed block from BlockStore
     */
    @SuppressWarnings("unchecked")
    public static SignData load(Block block) {
        SignData sign = new SignData(block);
        String[] pack = (String[]) BlockStoreApi.getBlockMeta(block, EasySigns.instance, EasySigns.key);
        for (String s : pack) {
            String[] terms = s.split(" ");
            String name = terms[0];
            String[] args = Arrays.copyOfRange(terms, 1, terms.length);
            Class c = EasySigns.instance.getActionClassByName(name.toLowerCase());
            try {
                SignAction action = (SignAction) c.getConstructor(SignData.class, String[].class).newInstance(sign, args);
                sign.actions.add(action);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return sign;
    }


    /**
     * Remove the sign at the specified block from BlockStore
     */
    public static void delete(Block block) {
        BlockStoreApi.removeBlockMeta(block, EasySigns.instance, EasySigns.key);
    }


}
