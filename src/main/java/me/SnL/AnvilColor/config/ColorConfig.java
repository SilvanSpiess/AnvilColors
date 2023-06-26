package me.SnL.AnvilColor.config;
import me.SnL.AnvilColor.AnvilColor;
import me.SnL.AnvilColor.config.ColorConfig;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class ColorConfig {    
    private final AnvilColor plugin;
    private final Logger logger;
    List<String> defaultParams;
    List<String> customParams;

    public ColorConfig(AnvilColor plugin){
        this.plugin = plugin;
        logger = plugin.getLogger();
        try {
            matchConfigParams();
        }
        catch (IOException | InvalidConfigurationException e) {
            logger.severe(e.getMessage());
        }
        if (new File(plugin.getDataFolder(), "config.yml").exists()) {loadDefaultConfig();loadCustomConfig();}
        else loadDefaultConfig();
    }

    private void loadCustomConfig() {
        logger.info("Loading custom config");
        final FileConfiguration configFile = new YamlConfiguration();
        final FileConfiguration defaultConfig = new YamlConfiguration();
        try {
            defaultConfig.load(new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("config.yml"))));
            configFile.load(new File(plugin.getDataFolder(), "config.yml"));

            customParams = new ArrayList<>(configFile.getValues(false).keySet());
            for(String s : customParams){
                colorDict.put(s, configFile.getString(s));
            }
            for (Map.Entry<String, String> entry : colorDict.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                //logger.info(key + " : " + value);
            }
            
        } //catch (IOException | InvalidConfigurationException e) {
            catch (IllegalArgumentException | IOException | InvalidConfigurationException ignored){
            //if it fails it reverst to the default config
            logger.severe("Failed to parse custom config");
            logger.warning("Reverting to default config");
            loadDefaultConfig();
        }
    }

    private void loadDefaultConfig() {
        logger.info("Loading default config");
        final FileConfiguration defaultConfig = new YamlConfiguration();
        try{
            defaultConfig.load(new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("config.yml"))));
            
            defaultParams = new ArrayList<>(defaultConfig.getValues(false).keySet());
            for(String s : defaultParams){
                colorDict.put(s, defaultConfig.getString(s));
            }
        } catch (IOException | InvalidConfigurationException e) {
            logger.severe("Failed to parse custom config");
            logger.warning("Reverting to default config");
            loadDefaultConfig();
        }
    }

    private void matchConfigParams() throws IOException, InvalidConfigurationException {
        /* function that checks whether the config.yml has all the correct parameters. 
        If it is missing any, it will try to update the config.yml file to contain them. */
        //opens file
        File configFile = new File(plugin.getDataFolder(),"config.yml");
        if(!configFile.exists()) {
            plugin.saveDefaultConfig();
            return;
        }

        //gets the custom and default config as a YamlConfiguration.
        final FileConfiguration customConfig = new YamlConfiguration();
        customConfig.load(configFile);
        final FileConfiguration defaultConfig = new YamlConfiguration();
        defaultConfig.load(new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("config.yml"))));

        // gets the values inside the custom config
        Map<String,Object> customValues = customConfig.getValues(true);

        //gets a list of all the parameters
        customParams = new ArrayList<>(customValues.keySet());
        defaultParams = new ArrayList<>(defaultConfig.getValues(true).keySet());
        
        Collections.sort(customParams);
        Collections.sort(defaultParams);

        //and compares them
        if(customParams.equals(defaultParams)) {
            logger.info("Custom config is up-to-date");
        }
        else { //if they don't match, it throws a warning and tries to update the custom config.yml to contain the missing parameters.
            logger.warning("Custom config is missing some parameters\nTrying to reconstruct config.yml keeping current config values");
            BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("config.yml")));
            List<String> lines = new ArrayList<>();
            br.lines().forEach(line -> { //checks for each parameter in the default config, to find the corresponding one in the custom config
                boolean found = false;
                for(String k : customParams) {
                    if(line.matches("\\s*" + k + ".*")) {
                        found = true;
                        break;
                    } 
                } //if it can't find a config that should be there, it adds it to the list 'lines', which will later add it to the real file.
                if(!found)
                    lines.add(line);
            });
            br.close();
            customConfig.load(new File(plugin.getDataFolder(), "config.yml"));
            String contents = customConfig.saveToString();
            BufferedReader br2 = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(contents.getBytes(StandardCharsets.UTF_8))));
            br2.lines().forEach(line2 -> { //checks for each parameter in the default config, to find the corresponding one in the custom config
                if(!lines.contains(line2)){
                    lines.add(line2);
                }
            });
            br2.close();

            //it adds the missing parameters to the custom config
            FileWriter fw = new FileWriter(configFile);
            BufferedWriter bw = new BufferedWriter(fw);
            for(String line : lines) {
                bw.write(line);
                bw.write("\n");
            }
            bw.flush();
            bw.close();
        }
        /*else { //if they don't match, it throws a warning and tries to update the custom config.yml to contain the missing parameters.
            logger.warning("Custom config is missing some parameters\nTrying to reconstruct config.yml keeping current config values");
            InputStreamReader isr = new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("config.yml"));
            BufferedReader br = new BufferedReader(isr);
            List<String> lines = new ArrayList<>();
            br.lines().forEach(line -> { //checks for each parameter in the default config, to find the corresponding one in the custom config
                boolean found = false;
                for(String k : customParams) {
                    if(line.matches("\\s*" + k + ".*")) {
                        found = true;
                        lines.add(line.substring(0,line.indexOf(k) + k.length() + 1) + " " + customValues.get(k).toString());
                        break;
                    }
                } //if it can't find a config that should be there, it adds it to the list 'lines', which will later add it to the real file.
                if(!found)
                    lines.add(line);

            });
            isr.close();
            br.close();

            //it adds the missing parameters to the custom config
            FileWriter fw = new FileWriter(configFile);
            BufferedWriter bw = new BufferedWriter(fw);
            for(String line : lines) {
                bw.write(line);
                bw.write("\n");
            }
            bw.flush();
            bw.close();
        }*/
    }

    public void reloadConfig() {
        try {
            matchConfigParams();
        }
        catch (IOException | InvalidConfigurationException e) {
            logger.severe(e.getMessage());
        }
        loadCustomConfig();
    }
    
    private HashMap<String, String> colorDict = new HashMap<String, String>();
    public HashMap<String, String> getColorDict() {
        return colorDict;
    }

    public Boolean isDefaultColor(String colorTag) {
        return colorDict.containsKey(colorTag.substring(1, colorTag.length()-1));  
    }
    
    public Boolean isCustomColor(String colorTag) {
        //logger.info("isCustomColor is called");
        if (new File(plugin.getDataFolder(), "config.yml").exists()){
            //logger.info("custom exists");  
            if(customParams.contains(colorTag.substring(1, colorTag.length()-1)) && !defaultParams.contains(colorTag.substring(1, colorTag.length()-1))){
                //logger.info("is custom color is true");
                return colorDict.containsKey(colorTag.substring(1, colorTag.length()-1));
            }
            return false;
        }
        return false;
    }
    
    public String getColor(String colorTag) {
        return "x" + colorDict.get(colorTag.substring(1, colorTag.length()-1));
    }
    public Boolean isFont(String colorTag) {
        if(colorTag.equals("<bold>") || colorTag.equals("<italic>")  || colorTag.equals("<underlined>") || colorTag.equals("<strikethrough>") || colorTag.equals("<reset>") || colorTag.equals("<obf>") || 
        colorTag.equals("<l>") || colorTag.equals("<o>") || colorTag.equals("<n>") || colorTag.equals("<m>")  || colorTag.equals("<r>") || colorTag.equals("<k>")){
            return colorDict.containsKey(colorTag.substring(1, colorTag.length()-1));
        }
        return false;
    }
    public String getFont(String colorTag) {
        //logger.info(colorDict.get(colorTag.substring(1, colorTag.length()-1)));
        return colorDict.get(colorTag.substring(1, colorTag.length()-1));
    }    
}