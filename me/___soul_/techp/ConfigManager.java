package me.___soul_.techp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    private startup plugin = startup.getPlugin(startup.class);

    public FileConfiguration nodetypecfg;//ARMOR STAND
    public File nodetypefile;

    public void createConfig() {
        try {
            if (!plugin.getDataFolder().exists()) {
                plugin.getDataFolder().mkdirs();
            }
            File file = new File(plugin.getDataFolder(), "config.yml");
            if (!file.exists()) {
                plugin.getLogger().info("Config.yml not found, creating!");
                plugin.saveDefaultConfig();
            } else {
                plugin.getLogger().info("Config.yml found, loading!");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public void setup(){

        if(!plugin.getDataFolder().exists()){
            plugin.getDataFolder().mkdir();
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "now creating folder");
        }
        nodetypefile = new File(plugin.getDataFolder(), "nodetypes.yml");

        if(!nodetypefile.exists()){
            try{
                nodetypefile.createNewFile();
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "nodetypes.yml is now created.");
            } catch (IOException e){
                Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Could not create the nodetypes.yml");
            }
        }
        nodetypecfg = YamlConfiguration.loadConfiguration(nodetypefile);
    }
    public FileConfiguration getnodetype(){

        return nodetypecfg;
    }

    public void setstring(String s, String o){
        nodetypecfg.set(s, o);
    }
    public void setDouble(String s, double o){
        nodetypecfg.set(s,o);
    }

    public void remove(String s, Player p){
        for(String key : nodetypecfg.getConfigurationSection(s).getKeys(false)){
            nodetypecfg.set(s, null);
            nodetypecfg.set(s + "." + key, null);
            p.sendMessage(s + "." + key);

        }
        savenodes();
    }

    public void savenodes(){
        try{
            nodetypecfg.save(nodetypefile);
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "server have save the nodetypes.yml");
        } catch (IOException e){
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "Could not save the nodetypes.yml");
        }
    }
    public void reloadnodetype(){

        nodetypecfg = YamlConfiguration.loadConfiguration(nodetypefile);
    }
}
