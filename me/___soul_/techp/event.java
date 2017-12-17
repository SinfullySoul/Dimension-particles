package me.___soul_.techp;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import java.io.File;
import java.util.List;

public class event implements Listener {
    public void setupitem(){}
    private startup plugin = startup.getPlugin(startup.class);
    private List Players = plugin.Players;

    private FileConfiguration nodetypecfg;
    private File nodetypefile;

    private String it = plugin.it;
    private List<String> lore = plugin.lore;
    private String skinurl = plugin.skinurl;

    public ItemStack skull = createitem.setSkull(skinurl, lore, it);
    public ShapedRecipe recipe = createitem.recipesetup(skull);

    @EventHandler
    public void onPlayerUse(PlayerInteractAtEntityEvent e) {

        Player p = e.getPlayer();
        if (e.getRightClicked().getType() == EntityType.ARMOR_STAND) {
            ArmorStand et = ((ArmorStand) e.getRightClicked());

            nodetypefile = new File(plugin.getDataFolder(), "nodetypes.yml");
            nodetypecfg = YamlConfiguration.loadConfiguration(nodetypefile);
            if (!(et.getName().toString().contains("Armor Stand"))) {
                if(p.getItemInHand().getType() == Material.SKULL_ITEM) {

                    String name = et.getCustomName().toString();

                    if (!(p.getItemInHand().getItemMeta().getDisplayName() == null)) {
                        if (p.getItemInHand().getItemMeta().getDisplayName().toString().contains(ChatColor.translateAlternateColorCodes('&', it))) {

                            e.setCancelled(true);
                            if (p.getItemInHand().hasItemMeta()) {
                                if (nodetypecfg.isSet(name)) {
                                    String w = nodetypecfg.getString(name + ".World");

                                    if (plugin.getServer().getWorld(w) != null) {

                                        Location loc = new Location(plugin.getServer().getWorld(w), plugin.getServer().getWorld(w).getSpawnLocation().getX(), plugin.getServer().getWorld(w).getSpawnLocation().getY(), plugin.getServer().getWorld(w).getSpawnLocation().getZ());
                                        p.teleport(loc);
                                    }
                                }
                            }
                        }else{
                            e.setCancelled(true);
                        }
                    }else{
                        if (nodetypecfg.isSet(name)) {
                            e.setCancelled(true);
                        }
                    }
                }else if((p.getItemInHand().getType() == Material.AIR)) {
                    if (Players.contains(p.getName())) {

                        //  if (!(et.getName().toString().contains("Armor Stand"))) {
                        String name = et.getCustomName().toString();

                        if (nodetypecfg.isSet(name)) {
                            et.remove();
                        }
                    }
                }
            }
        }
    }
    @EventHandler
    public void onPlayerUse(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
            if(p.getItemInHand().getType() == Material.SKULL_ITEM) {
                if (p.getItemInHand().hasItemMeta()) {
                    if (p.getItemInHand().getItemMeta().getDisplayName() != null) {
                        if (p.getItemInHand().getItemMeta().getDisplayName().toString().contains(ChatColor.translateAlternateColorCodes('&', it))) {
                            e.setCancelled(true);
                        }
                    }
                }
            }
        }
    }
}
