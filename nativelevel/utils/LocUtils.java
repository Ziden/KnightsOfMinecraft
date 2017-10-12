/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.utils;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

/**
 *
 * @author Gabriel
 */
public class LocUtils {

    //Convert String to Location
    public static Location str2loc(String str) {
        String str2loc[] = str.split("\\:");
        Location loc = new Location(Bukkit.getServer().getWorld(str2loc[0]), 0, 0, 0);
        loc.setX(Double.parseDouble(str2loc[1]));
        loc.setY(Double.parseDouble(str2loc[2]));
        loc.setZ(Double.parseDouble(str2loc[3]));
        return loc;
    }

  
    //Convert Location To String
    public static String loc2str(Location loc) {
        return loc.getWorld().getName() + ":" + loc.getBlockX() + ":" + loc.getBlockY() + ":" + loc.getBlockZ();
    }

    public static boolean inArea(Location targetLocation, Location inAreaLocation1, Location inAreaLocation2) {
        if (inAreaLocation1.getWorld().getName() == inAreaLocation2.getWorld().getName()) { // Check for worldName location1, location2
            if (targetLocation.getWorld().getName() == inAreaLocation1.getWorld().getName()) { // Check for worldName targetLocation, location1
                if ((targetLocation.getBlockX() >= inAreaLocation1.getBlockX() && targetLocation.getBlockX() <= inAreaLocation2.getBlockX()) || (targetLocation.getBlockX() <= inAreaLocation1.getBlockX() && targetLocation.getBlockX() >= inAreaLocation2.getBlockX())) { // Check X value
                    if ((targetLocation.getBlockZ() >= inAreaLocation1.getBlockZ() && targetLocation.getBlockZ() <= inAreaLocation2.getBlockZ()) || (targetLocation.getBlockZ() <= inAreaLocation1.getBlockZ() && targetLocation.getBlockZ() >= inAreaLocation2.getBlockZ())) { // Check Z value
                        if ((targetLocation.getBlockY() >= inAreaLocation1.getBlockY() && targetLocation.getBlockY() <= inAreaLocation2.getBlockY()) || (targetLocation.getBlockY() <= inAreaLocation1.getBlockY() && targetLocation.getBlockY() >= inAreaLocation2.getBlockY())) { // Check Y value
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static boolean inAreaIgnoreY(Location targetLocation, Location inAreaLocation1, Location inAreaLocation2) {
        if (inAreaLocation1.getWorld().getName() == inAreaLocation2.getWorld().getName()) { // Check for worldName location1, location2
            if (targetLocation.getWorld().getName() == inAreaLocation1.getWorld().getName()) { // Check for worldName targetLocation, location1
                if ((targetLocation.getBlockX() >= inAreaLocation1.getBlockX() && targetLocation.getBlockX() <= inAreaLocation2.getBlockX()) || (targetLocation.getBlockX() <= inAreaLocation1.getBlockX() && targetLocation.getBlockX() >= inAreaLocation2.getBlockX())) { // Check X value
                    if ((targetLocation.getBlockZ() >= inAreaLocation1.getBlockZ() && targetLocation.getBlockZ() <= inAreaLocation2.getBlockZ()) || (targetLocation.getBlockZ() <= inAreaLocation1.getBlockZ() && targetLocation.getBlockZ() >= inAreaLocation2.getBlockZ())) { // Check Z value
                        return true;

                    }
                }
            }
        }
        return false;
    }

    public static boolean isSameLocation(Location l1, Location l2) {
        if (l1.getBlockX() == l2.getBlockX()) {
            if (l1.getBlockZ() == l2.getBlockZ()) {
                return true;
            }
        }
        return false;
    }

    public static int[] getChunkLocation(Location l) {
        l = l.getChunk().getBlock(0, 0, 0).getLocation();
        return new int[]{(int) l.getX() / 16, (int) l.getZ() / 16};
    }

    public static Location locOfChunk(String w, int x, int z) {
        return Bukkit.getWorld(w).getChunkAt(x * 16, z * 16).getBlock(0, 0, 0).getLocation();
    }

    public static final BlockFace[] axis = {BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST};
    public static final BlockFace[] radial = {BlockFace.NORTH, BlockFace.NORTH_EAST, BlockFace.EAST, BlockFace.SOUTH_EAST, BlockFace.SOUTH, BlockFace.SOUTH_WEST, BlockFace.WEST, BlockFace.NORTH_WEST};

    /**
     * Gets the horizontal Block Face from a given yaw angle<br>
     * This includes the NORTH_WEST faces
     *
     * @param yaw angle
     * @return The Block Face of the angle
     */
    public static BlockFace yawToFace(float yaw) {
        return yawToFace(yaw, true);
    }

    /**
     * Gets the horizontal Block Face from a given yaw angle
     *
     * @param yaw angle
     * @param useSubCardinalDirections setting, True to allow NORTH_WEST to be
     * returned
     * @return The Block Face of the angle
     */
    public static BlockFace yawToFace(float yaw, boolean useSubCardinalDirections) {
        if (useSubCardinalDirections) {
            return radial[Math.round(yaw / 45f) & 0x7];
        } else {
            return axis[Math.round(yaw / 90f) & 0x3];
        }
    }

    public static Vector getTrajectory(Entity from, Entity to) {
        return getTrajectory(from.getLocation().toVector(), to.getLocation().toVector());
    }

    public static Vector getTrajectory(Location from, Location to) {
        return getTrajectory(from.toVector(), to.toVector());
    }

    public static Vector getTrajectory(Vector from, Vector to) {
        return to.subtract(from).normalize();
    }

    public static void knock(Entity damager, Entity damaged, double knockback) {
        Vector trajectory = getTrajectory2d(damager, damaged);
        trajectory.multiply(0.6D * knockback);
        trajectory.setY(Math.abs(trajectory.getY()));

        velocity(damaged, trajectory, 0.2D + trajectory.length() * 0.8D, false, 0.0D, Math.abs(0.2D * knockback), 0.4D + 0.04D * knockback);

    }

    public static Vector getTrajectory2d(Entity from, Entity to) {
        return getTrajectory2d(from.getLocation().toVector(), to.getLocation().toVector());
    }

    public static Vector getTrajectory2d(Location from, Location to) {
        return getTrajectory2d(from.toVector(), to.toVector());
    }

    public static Vector getTrajectory2d(Vector from, Vector to) {
        return to.subtract(from).setY(0).normalize();
    }

    public static void velocity(Entity ent, Vector vec, double str, boolean ySet, double yBase, double yAdd, double yMax) {
        if ((Double.isNaN(vec.getX())) || (Double.isNaN(vec.getY())) || (Double.isNaN(vec.getZ())) || (vec.length() == 0.0D)) {
            return;
        }
        if (ySet) {
            vec.setY(yBase);
        }
        vec.normalize();
        vec.multiply(str);

        vec.setY(vec.getY() + yAdd);
        if (vec.getY() > yMax) {
            vec.setY(yMax);
        }
     
        ent.setFallDistance(0.0F);
        ent.setVelocity(vec);
    }


    public static List<Location> trace(Location one, Location two, int n) {
        n++;
        Location temp = null;
        List<Location> locs = new ArrayList<Location>();
        Location base = two.clone().subtract(one);
        Vector unit = new Vector(base.getX() / n, base.getY() / n, base.getZ() / n);
        temp = one;
        for (int i = 1; i < n; i++) {
            locs.add(locs.size(), temp.add(unit).clone());

        }
        return locs;
    }
}
