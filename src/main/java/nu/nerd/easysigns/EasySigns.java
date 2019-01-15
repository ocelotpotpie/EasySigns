package nu.nerd.easysigns;

import net.sothatsit.blockstore.BlockStoreApi;
import nu.nerd.easysigns.actions.*;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;


public class EasySigns extends JavaPlugin {


    public static EasySigns instance;
    public static String prefix = "easysigns";
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


    private void createActionAtlas() {
        actionAtlas = new HashMap<>();
        actionAtlas.put("warp", WarpAction.class);
    }


    public Class getActionClassByName(String name) {
        return actionAtlas.get(name);
    }


    public boolean isSign(Block block) {
        return signMaterials.contains(block.getType());
    }


    public boolean isEasySign(Block block) {
        Object object = BlockStoreApi.getBlockMeta(block, this, prefix);
        return object != null;
    }


}
