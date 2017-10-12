/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.utils;

import nativelevel.KoM;
import nativelevel.MetaShit;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.metadata.Metadatable;

/**
 *
 * @author User
 */
public class Cooldown {

    private long time;

    public Cooldown(int millis) {
        this.time = System.currentTimeMillis() + millis;
    }

    public boolean isOver() {
        return System.currentTimeMillis() > time;
    }

    public long millisRemaining() {
        return time - System.currentTimeMillis();
    }

    public static void setMetaCooldown(Metadatable e, String cdName, int millis) {
        MetaShit.setMetaObject("cooldown-" + cdName, e, new Cooldown(millis));
    }

    public static Cooldown get(Metadatable e, String cdName) {
        if (!e.hasMetadata("cooldown-" + cdName)) {
            return null;
        }
        return (Cooldown) MetaShit.getMetaObject("cooldown-" + cdName, e);
    }

    public static boolean isCooldown(Metadatable e, String cdName) {
        if (!e.hasMetadata("cooldown-" + cdName)) {
            return false;
        }
        boolean taCd = !((Cooldown) MetaShit.getMetaObject("cooldown-" + cdName, e)).isOver();
        if (!taCd) {
            e.removeMetadata("cooldown-" + cdName, KoM._instance);
        }
        return taCd;
    }

    public static void removeCooldown(Metadatable e, String cdname) {
        if (isCooldown(e, cdname)) {
            e.removeMetadata("cooldown-" + cdname, KoM._instance);
        }
    }

}
