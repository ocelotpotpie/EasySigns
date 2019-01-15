package nu.nerd.easysigns;

import net.sothatsit.blockstore.BlockStoreApi;
import nu.nerd.easysigns.actions.SignAction;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;


public class SignListener implements Listener {


    private EasySigns plugin;


    public SignListener() {
        plugin = EasySigns.instance;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }


    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (!event.hasBlock() || !plugin.isSign(event.getClickedBlock())) return;
        if (plugin.isEasySign(event.getClickedBlock())) {
            SignData sign = SignData.load(event.getClickedBlock());
            for (SignAction action : sign.getActions()) {
                action.action(event.getPlayer());
            }
        }
    }


}
