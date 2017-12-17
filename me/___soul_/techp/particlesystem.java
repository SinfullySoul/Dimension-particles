package me.___soul_.techp;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.List;

public class particlesystem extends BukkitRunnable implements Runnable{//player.spawnParticle
    private startup plugin = startup.getPlugin(startup.class);

    public FileConfiguration nodetypecfg;
    public File nodetypefile;

    public void run(){
        for(Player p : Bukkit.getServer().getOnlinePlayers()) {
            List<Entity> entitylist = p.getNearbyEntities(10, 5, 10);
            for(int t=0;t<entitylist.size();t++){
                int r = 0;
                if(entitylist.get(t).getType() == EntityType.ARMOR_STAND){
                    String ename = entitylist.get(t).getCustomName();
                    if(!entitylist.get(t).getName().equalsIgnoreCase("ARMOR STAND")) {
                        String name = entitylist.get(t).getCustomName();
                        nodetypefile = new File(plugin.getDataFolder(), "nodetypes.yml");
                        nodetypecfg = YamlConfiguration.loadConfiguration(nodetypefile);

                        if(nodetypecfg.isSet(name)) {
                            if (ename.contains(nodetypecfg.getString(ename + ".name"))) {
                                float xf = (float) nodetypecfg.getDouble(name + ".color.x");
                                float yf = (float) nodetypecfg.getDouble(name + ".color.y");
                                float zf = (float) nodetypecfg.getDouble(name + ".color.z");
                                float conf = (float) nodetypecfg.getDouble(name + ".color.con");

                                if (nodetypecfg.isSet(ename)) {
                                    Location eloc = entitylist.get(t).getLocation();
                                    double x = eloc.getX();
                                    double y = eloc.getY();
                                    double z = eloc.getZ();

//                                p.sendMessage(ename + " " + xf + " " + yf + " " + zf + " " + conf);
                                    particlesetup(entitylist, p, t, x, y, z, xf, yf, zf, conf);
                                    // createCircle(eloc);
                                    createlink(eloc);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    public void particlesetup(List<Entity> entitylist, Player p, int t,double x, double y, double z, float cx, float cy, float cz, float con){
        for(int v = 0; v < 3;) {
            //List<Entity> entitylist = p.getNearbyEntities(6, 2, 6);
            //entitylist.get(t).getWorld().spawnParticle(Particle.BARRIER, x, y + 1.3, z, 0, con, cx, cy, cz);

            entitylist.get(t).getWorld().spawnParticle(Particle.REDSTONE, x, y + 0.6, z, 0, con, cx, cy, cz);
            entitylist.get(t).getWorld().spawnParticle(Particle.REDSTONE, x, y + 0.6, z, 0, con, cx, cy, cz);
            entitylist.get(t).getWorld().spawnParticle(Particle.REDSTONE, x, y + 0.6, z, 0, con, cx, cy, cz);
            v++;
        }
    }

    public void createlink(Location loc) {
            Location particleLoc = new Location(loc.getWorld(), loc.getX(), loc.getY() + 0.7, loc.getZ());
            loc.getWorld().spigot().playEffect(particleLoc, Effect.WITCH_MAGIC, 0, 0, 0.0f, 0.0f, 0.0f, 0.0f, 2, 10);
    }
    /*
    public void createCircle(Location loc) {
        float radius = 0.7f;
        for (double a = 0; a < Math.PI * 2; a += Math.PI / 10) {
            double x = radius * Math.cos(a) - 0.5;
            double z = radius * Math.sin(a) - 0.5;
            Location particleLoc = new Location(loc.getWorld(), (loc.getX() + x) + 0.5, loc.getY() + 0.3, (loc.getZ() + z) + 0.5);
            loc.getWorld().spigot().playEffect(particleLoc, Effect.MAGIC_CRIT, 0, 0, 0.0f, 0.0f, 0.0f, 0.0f, 4, 20);
        }
    }
    */
}
