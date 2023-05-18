package me.SnL.AnvilColor.listener;
import me.SnL.AnvilColor.AnvilColor;
import me.SnL.AnvilColor.config.ColorConfig;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


public class CommandListener implements CommandExecutor {
    ColorConfig config;
    final AnvilColor plugin;

    public CommandListener(AnvilColor plugin, ColorConfig config){
        this.config = config;
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args){
        if (label.equalsIgnoreCase("colorcodes")){
            if(commandSender instanceof Player){
                Audience player = (Audience) commandSender;
                commandSender.sendMessage("§bThe following§r §5c§9o§bl§ao§eu§6r§cs §bare available:§r");
                player.sendMessage(getDisplayText1());
                player.sendMessage(getDisplayText2());
                player.sendMessage(getDisplayText3());
                player.sendMessage(getDisplayText4());
                player.sendMessage(getDisplayText5());
                player.sendMessage(getDisplayText6());
                commandSender.sendMessage("§bCustom colors are:");
                player.sendMessage(getDisplayText7());
                commandSender.sendMessage("§bAlternativley use hex-colors in the format §d<#ff00ff>");
                return true;
            }
            else {
                commandSender.sendMessage("You must be a player!");
                return true;
            }
        }
        return false;
    }
    public Component getDisplayText1() {        
        var mm = MiniMessage.miniMessage();
        Component parsed = mm.deserialize("<#"+config.getColorDict().get("yellow")+">"+"yellow"+"</#"+config.getColorDict().get("yellow")+"> " + "<#"+config.getColorDict().get("orange")+">"+"orange"+"</#"+config.getColorDict().get("orange")+"> " + "<#"+config.getColorDict().get("red")+">"+"red"+"</#"+config.getColorDict().get("red")+">");
        return parsed;        
    }
    public Component getDisplayText2() {        
        var mm = MiniMessage.miniMessage();
        Component parsed = mm.deserialize("<#"+config.getColorDict().get("purple")+">"+"purple"+"</#"+config.getColorDict().get("purple")+"> " + "<#"+config.getColorDict().get("magenta")+">"+"magenta"+"</#"+config.getColorDict().get("magenta")+"> " + "<#"+config.getColorDict().get("pink")+">"+"pink"+"</#"+config.getColorDict().get("pink")+">");
        return parsed;        
    }
    public Component getDisplayText3() {        
        var mm = MiniMessage.miniMessage();
        Component parsed = mm.deserialize("<#"+config.getColorDict().get("blue")+">"+"blue"+"</#"+config.getColorDict().get("blue")+"> " + "<#"+config.getColorDict().get("cyan")+">"+"cyan"+"</#"+config.getColorDict().get("cyan")+"> " + "<#"+config.getColorDict().get("light_blue")+">"+"light_blue"+"</#"+config.getColorDict().get("light_blue")+">");
        return parsed;        
    }
    public Component getDisplayText4() {        
        var mm = MiniMessage.miniMessage();
        Component parsed = mm.deserialize("<#"+config.getColorDict().get("green")+">"+"green"+"</#"+config.getColorDict().get("green")+"> " + "<#"+config.getColorDict().get("lime")+">"+"lime"+"</#"+config.getColorDict().get("lime")+"> " + "<#"+config.getColorDict().get("brown")+">"+"brown"+"</#"+config.getColorDict().get("brown")+">");
        return parsed;        
    }
    public Component getDisplayText5() {        
        var mm = MiniMessage.miniMessage();
        Component parsed = mm.deserialize("<#"+config.getColorDict().get("black")+">"+"black"+"</#"+config.getColorDict().get("black")+"> " + "<#"+config.getColorDict().get("gray")+">"+"gray"+"</#"+config.getColorDict().get("gray")+"> " + "<#"+config.getColorDict().get("light_gray")+">"+"light_gray"+"</#"+config.getColorDict().get("light_gray")+"> " + "<#"+config.getColorDict().get("white")+">"+"white"+"</#"+config.getColorDict().get("white")+">");
        return parsed;        
    }
    public Component getDisplayText6() {        
        var mm = MiniMessage.miniMessage();
        //Component parsed = mm.deserialize("<r><b>"+"bold"+"</b> " + "<i>"+"italic"+"</i> " + "<u>"+"underlined"+"</u> " + "<st>"+"strikethrough"+"</st>" + "<r>");
        Component parsed = mm.deserialize("<reset><b>bold</b> " + "<i>italic</i> " + "<u>underlined</u> " + "<st>strikethrough</st> " + "<reset><i>obf-></i> <obf>obf</obf>"); 
        
        return parsed;        
    }
    public Component getDisplayText7() {        
        var mm = MiniMessage.miniMessage();
        Component parsed = mm.deserialize("<#"+config.getColorDict().get("gold")+">"+"gold"+"</#"+config.getColorDict().get("gold")+"> " + "<#"+config.getColorDict().get("silver")+">"+"silver"+"</#"+config.getColorDict().get("silver")+"> " + "<#"+config.getColorDict().get("aqua")+">"+"aqua"+"</#"+config.getColorDict().get("aqua")+">");
        return parsed;        
    }
    
    public Component getDisplayText(){
        var mm = MiniMessage.miniMessage();
        Component parsed = mm.deserialize(" <#f8c627>yellow</#f8c627>  <#f07613>orange</#f07613>  <#a12722>red</#a12722> \n <#792aac>purple</#792aac>  <#bd44b3>magenta</#bd44b3>  <#ed8dac>pink</#ed8dac> \n <#35399d>blue</#35399d>  <#158991>cyan</#158991>  <#3aafd9>light_blue</#3aafd9> \n <#546d1b>green</#546d1b>  <#70b919>lime</#70b919>  <#724728>brown</#724728> \n <#141519>black</#141519>  <#3e4447>gray</#3e4447>  <#8e8e86>light_gray</#8e8e86>  <#e9ecec>white</#e9ecec> \n <reset><b>bold</b>  <i>italic</i>  <u>underlined</u>  <st>strikethrough</st>\n");
        return parsed;        
    }
}
