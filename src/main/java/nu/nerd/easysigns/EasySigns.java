package nu.nerd.easysigns;

import org.bukkit.plugin.java.JavaPlugin;


public class EasySigns extends JavaPlugin {


    public static EasySigns instance;


    @Override
    public void onEnable() {
        EasySigns.instance = this;
        new CommandHandler();
        new SignListener();
    }


}
