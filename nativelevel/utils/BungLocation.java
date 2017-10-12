/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nativelevel.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

/**
 *
 * @author usuario
 * 
 */

public class BungLocation {
    
    public String mundo;
    public double x;
    public double y;
    public double z;
    public float pitch;
    public float yaw;
    
    public Location toLocation() {
        World w = Bukkit.getWorld(mundo);
        if(w==null) return null;
        Location l = new Location(Bukkit.getWorld(mundo), x,y,z);
        l.setPitch(pitch);
        l.setYaw(yaw);
        return l;
    }
    
    public BungLocation(String mundo, double x, double y, double z, float pitch, float yaw) {
        this.mundo = mundo;
        this.x = x;
        this.y = y;
        this.z = z;
        this.pitch = pitch;
        this.yaw = yaw;
    }
    
    public BungLocation(Location l) {
        this.mundo = l.getWorld().getName();
        this.x = l.getX();
        this.y = l.getY();
        this.z = l.getZ();
        this.pitch = l.getPitch();
        this.yaw = l.getYaw();
    }
    
}
