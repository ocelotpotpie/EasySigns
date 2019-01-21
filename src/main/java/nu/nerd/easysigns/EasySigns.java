package nu.nerd.easysigns;

import net.sothatsit.blockstore.BlockStoreApi;
import nu.nerd.easysigns.actions.*;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;


public class EasySigns extends JavaPlugin {


    public static EasySigns instance;
    public static String key = "actions";
    private Map<String, Class> actionAtlas;


    public static final Set<Material> signMaterials = new HashSet<>(Arrays.asList(
            Material.WALL_SIGN,
            Material.SIGN
    ));


    @Override
    public void onEnable() {
        EasySigns.instance = this;
        createActionAtlas();
        new CommandHandler();
        new SignListener();
    }


    /**
     * Register SignAction subclasses for use
     */
    private void createActionAtlas() {
        actionAtlas = new HashMap<>();
        actionAtlas.put("sleep", SleepAction.class);
        actionAtlas.put("warp", WarpAction.class);
        actionAtlas.put("check-empty-inventory", CheckEmptyInvAction.class);
        actionAtlas.put("ci", ClearInvAction.class);
        actionAtlas.put("cmd", CmdAction.class);
        actionAtlas.put("give", GiveAction.class);
        actionAtlas.put("hunger", HungerAction.class);
        actionAtlas.put("announce", AnnounceAction.class);
        actionAtlas.put("heal", HealAction.class);
        actionAtlas.put("max", MaxAction.class);
        actionAtlas.put("msg", MsgAction.class);
        actionAtlas.put("take", TakeAction.class);
        actionAtlas.put("lore", LoreAction.class);
        actionAtlas.put("potion", PotionAction.class);
        actionAtlas.put("clearpotions", ClearPotionsAction.class);
        actionAtlas.put("leather", LeatherAction.class);
        actionAtlas.put("sound", SoundAction.class);
    }


    /**
     * Look up a class by its name
     * @param name class name to look up
     * @return the matching Class object or null
     */
    public Class getActionClassByName(String name) {
        return actionAtlas.get(name);
    }


    /**
     * Returns a Set of valid action names
     */
    public Set<String> getValidActions() {
        return actionAtlas.keySet();
    }


    /**
     * All of the action classes
     */
    public Collection<Class> getActionClasses() {
        return actionAtlas.values();
    }


    /**
     * Check if a block is a sign
     * @param block the block to check
     * @return true if the block's material is that of a sign of any type
     */
    public boolean isSign(Block block) {
        return signMaterials.contains(block.getType());
    }


    /**
     * Consult BlockStore to see if this block is registered as an EasySign
     * @param block the block to check
     * @return true if EasySign
     */
    public boolean isEasySign(Block block) {
        if (!isSign(block)) return false;
        Object object = BlockStoreApi.getBlockMeta(block, this, key);
        return object != null;
    }


}
