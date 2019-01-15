package nu.nerd.easysigns;

import net.sothatsit.blockstore.BlockStoreApi;
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
        // --- testing ---
        event.getPlayer().sendMessage("Interaction event!");
        if (plugin.isEasySign(event.getClickedBlock())) {
            event.getPlayer().sendMessage("Is EasySign");
            for (String val : ((String[])BlockStoreApi.getBlockMeta(event.getClickedBlock(), plugin, EasySigns.prefix))) {
                event.getPlayer().sendMessage("- " + val);
            }
        }
    }


}
