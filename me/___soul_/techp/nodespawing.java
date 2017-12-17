package me.___soul_.techp;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;

public class nodespawing {

    public static void spawnnode(Location loc, String n) {

        ArmorStand stand = loc.getWorld().spawn(loc, ArmorStand.class);

        stand.setCustomName(n);
        //stand.setCustomNameVisible(false);
        stand.setGravity(false);
        stand.setBasePlate(false);
        stand.setSmall(true);
        stand.setVisible(false);
        //stand.setInvulnerable(true);

        return;
    }
}
