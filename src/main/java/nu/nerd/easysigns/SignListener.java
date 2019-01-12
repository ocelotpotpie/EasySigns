package nu.nerd.easysigns;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


public class SignListener implements Listener {


    private EasySigns plugin;


    private static final Set<Material> validMaterials = new HashSet<>(Arrays.asList(
            Material.WALL_SIGN,
            Material.SIGN
    ));


    public SignListener() {
        plugin = EasySigns.instance;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }


    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (!event.hasBlock() || !validMaterials.contains(event.getClickedBlock().getType())) return;
        event.getPlayer().sendMessage("Interaction event!");
    }


}
