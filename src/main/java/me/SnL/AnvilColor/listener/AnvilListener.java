package me.SnL.AnvilColor.listener;
import me.SnL.AnvilColor.AnvilColor;
import me.SnL.AnvilColor.config.ColorConfig;

import java.util.logging.Logger;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;

public class AnvilListener implements Listener {
    ColorConfig config;
    AnvilColor plugin;
    private final Logger logger;
    public AnvilListener(AnvilColor plugin, ColorConfig config){
        this.config = config;
        this.plugin = plugin;
        logger = plugin.getLogger();
    }

    @EventHandler
    public void onPrepareAnvil(PrepareAnvilEvent event) {
        var result = event.getResult();
        if (result == null) return;
        var resultMeta = result.getItemMeta();
        if (resultMeta == null) return;
        String rawName = resultMeta.getDisplayName();
        String modifiedName = "";
        int closingTag = 0;
        int openingTag = 0;
        for (int i=0; i<rawName.length(); i++) {
            if (rawName.charAt(i) == '<') {
                //find closing tag
                boolean closingTagFound = false;
                openingTag = i;
                for (int j=i; j<rawName.length(); j++) {
                    if (rawName.charAt(j) == '>') {
                        closingTagFound = true;
                        closingTag = j;
                        break;
                    }
                }
                if (!closingTagFound) {
                    modifiedName += '<';
                    continue;
                }
                String colorTag = rawName.substring(openingTag,closingTag+1);
                if (isRGB(colorTag)) {
                    modifiedName += convertColor(getRGB(colorTag));
                    i = closingTag;
                } else if (config.isFont(colorTag)) {
                    modifiedName += convertColor(config.getFont(colorTag));
                    i = closingTag;
                } else if (config.isCustomColor(colorTag)) {
                    //logger.info("custom color recognised");
                    modifiedName += convertColor(config.getColor(colorTag));
                    i = closingTag;
                } else if (config.isDefaultColor(colorTag)) {
                    modifiedName += convertColor(config.getColor(colorTag));
                    i = closingTag;
                } else {
                    modifiedName += '<';
                }
            } else {
                modifiedName += rawName.charAt(i);
            }   
        }
        resultMeta.setDisplayName(modifiedName); //ff7700
        result.setItemMeta(resultMeta);
    }
    /*
     * gets string of type <#ff00ff>
     */
    boolean isRGB(String tagToCheck){
        if(tagToCheck.charAt(0) == '<' && tagToCheck.charAt(1) == '#' && tagToCheck.charAt(8) == '>'){
            String colorCode = "0123456789abcdef";
            for(int i = 2; i<8; i++){
                if(!(colorCode.contains(String.valueOf(tagToCheck.charAt(i))))){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /*
     * returns xff00ff
     */
    public String getRGB(String tagToConvert){
        return ("x" + tagToConvert.substring(2,8));
    }

    /*
     * gets String of form xff00ff
     * returns §x§f§f§0§0§f§f
     */
    public static String convertColor(String hexToConvert){
        String result = "";
        for(int i = 0; i<hexToConvert.length(); i++){
            result += "§" + hexToConvert.charAt(i);
        }
        return result;
    }
}
