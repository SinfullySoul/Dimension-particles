package me.___soul_.techp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class startup extends JavaPlugin {
    private ConfigManager cfgm;

    public String it = getConfig().getString("name");
    public List<String> lore = (List)getConfig().getList("lore");
    public String skinurl = getConfig().getString("skin");

    public List Players = new ArrayList();

    public void onEnable() {
        loadConfigManager();
        registerEvents();
        getServer().getScheduler().runTaskTimer(this, new particlesystem(), 20, 7);
    }

    public void onDisable() {
        reloadConfig();
        saveConfig();
    }

    public void loadConfigManager(){
        cfgm = new ConfigManager();
        cfgm.createConfig();
        cfgm.setup();
        cfgm.savenodes();
    }

    public void registerEvents(){
        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(new event(), this);
    }

    //commands\\
    private String cmd1 = "techp";
    //=---------------=\\

    //sub-commands\\
    private String subcmd1 = "spawn";
    private String subcmd2 = "create";
    private String subcmd3 = "del";
    private String subcmd4 = "remove";
    private String subcmd5 = "reload";
    //=---------------=\\

    public void setrecipe(Recipe recipe){

        Bukkit.addRecipe(recipe);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(sender instanceof Player){
            if(cmd.getName().equalsIgnoreCase(cmd1)) {
                Player p = ((Player) sender).getPlayer();
                Location loc = p.getLocation();
                if (args.length != 0) {
                    if(args[0].equalsIgnoreCase(subcmd5)){
                        reloadConfig();
                        saveConfig();
                        onEnable();
                        p.sendMessage(ChatColor.GREEN + "reloaded ");
                        return true;
                    }else if(args[0].equalsIgnoreCase(subcmd4)){
                        if (!Players.contains(p.getName())) {
                            p.sendMessage(ChatColor.GREEN + "now enabling");
                            Players.add(p.getName());
                            return true;
                            //Players.contains(p.getName())
                        }else{
                            p.sendMessage(ChatColor.RED + "now disabling");
                            Players.remove(p.getName());
                            return true;
                        }
                    }else if(args[0].equalsIgnoreCase(subcmd3)){
                        if(args.length > 1) {
                            if (cfgm.getnodetype().isSet(args[1])) {

                                cfgm.remove(args[1], p);

                                p.sendMessage(ChatColor.GREEN + "now deleting node " + args[1]);
                                return true;
                            } else {
                                p.sendMessage(ChatColor.RED + "that node is not created?");
                                return true;
                            }
                        }else{
                            p.sendMessage(ChatColor.RED + "need more arguments");
                            return true;
                        }
                    }else if (args[0].equalsIgnoreCase(subcmd1)) {
                        if(args.length > 1) {
                            if (cfgm.getnodetype().isSet(args[1])) {

                                p.sendMessage(ChatColor.AQUA + "you have spawn node [" + args[1] + "]");
                                nodespawing.spawnnode(loc, args[1]);
                                return true;
                            } else {
                                p.sendMessage(ChatColor.RED + "that node wasn't created " + args[1]);
                                return true;
                            }
                        }else{
                            p.sendMessage("" + args.length);
                            p.sendMessage(ChatColor.RED + "need more arguments");
                            return true;
                        }
                    } else if (args[0].toString().equalsIgnoreCase(subcmd2)) {
                        if (args.length > 1 && !cfgm.getnodetype().isSet(args[1])) {
                            if (!args[2].toString().isEmpty() && !args[3].toString().isEmpty() && !args[4].toString().isEmpty() && !args[5].toString().isEmpty() && !args[6].toString().isEmpty()) {
                                String name = args[1].toString();

                                p.sendMessage("ok got it " + name);
                                double dx = Double.parseDouble(args[2]);
                                double dy = Double.parseDouble(args[3]);
                                double dz = Double.parseDouble(args[4]);
                                double cond = Double.parseDouble(args[5]);
                                String world = (args[6]);

                                cfgm.setstring(name + ".name", name);
                                cfgm.setDouble(name + ".color.x", dx);
                                cfgm.setDouble(name + ".color.y", dy);
                                cfgm.setDouble(name + ".color.z", dz);
                                cfgm.setDouble(name + ".color.con", cond);
                                cfgm.setstring(name + ".World", world);

                                cfgm.savenodes();
                                cfgm.reloadnodetype();
                                return true;
                            } else {
                                p.sendMessage(args[6]);
                                p.sendMessage(ChatColor.RED + "/techp create <name> <color-x> <color-y> <color-z> <contrast> <world-name>");
                                p.sendMessage(ChatColor.RED + "need to set <color-x> <color-y> <color-z> <contrast> <world-name>");
                                return true;
                            }
                        } else {
                            p.sendMessage(ChatColor.RED + "/techp create <name> <color-x> <color-y> <color-z> <contrast> <world-name>");
                            return true;
                        }
                    }else {
                        p.sendMessage(ChatColor.RED + "need more arguments");
                        return true;
                    }

                } else {
                    p.sendMessage(ChatColor.RED + "need more arguments");
                    return true;
                }
            }
        } else{
            sender.sendMessage(ChatColor.RED + "This command can only be use by a player.");
            return true;
        }
        return false;
    }

}
