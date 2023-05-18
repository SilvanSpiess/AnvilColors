package me.SnL.AnvilColor;

import me.SnL.AnvilColor.config.ColorConfig;
import me.SnL.AnvilColor.listener.AnvilListener;
import me.SnL.AnvilColor.listener.CommandListener;
import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

public class AnvilColor extends JavaPlugin {
    AnvilListener anvilListener;
    ColorConfig config;
    private Logger logger;
    
    @Override
    public void onEnable() {
        logger = getLogger();
        config = new ColorConfig(this);
        anvilListener = new AnvilListener(this, config); 
        getServer().getPluginManager().registerEvents(anvilListener,this); 
        getCommand("ColorCodes").setExecutor(new CommandListener(this, config));
    }
}
