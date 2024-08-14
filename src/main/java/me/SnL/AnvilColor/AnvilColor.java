package me.SnL.AnvilColor;

import me.SnL.AnvilColor.config.ColorConfig;
import me.SnL.AnvilColor.listener.AnvilListener;
import me.SnL.AnvilColor.listener.CommandListener;
import org.bukkit.plugin.java.JavaPlugin;

public class AnvilColor extends JavaPlugin {
    AnvilListener anvilListener;
    ColorConfig config;
    
    @Override
    public void onEnable() {
        config = new ColorConfig(this);
        anvilListener = new AnvilListener(config); 
        getServer().getPluginManager().registerEvents(anvilListener,this); 
        getCommand("anvilcolors").setExecutor(new CommandListener(this, config));
    }
}
