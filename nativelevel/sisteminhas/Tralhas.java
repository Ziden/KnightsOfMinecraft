/*

 ╭╮╭━╮╱╱╭━╮╭━╮
 ┃┃┃╭╯╱╱┃┃╰╯┃┃
 ┃╰╯╯╭━━┫╭╮╭╮┃
 ┃╭╮┃┃╭╮┃┃┃┃┃┃
 ┃┃┃╰┫╰╯┃┃┃┃┃┃
 ╰╯╰━┻━━┻╯╰╯╰╯

 Desenvolvedor: ZidenVentania
 Colaboradores: NeT32, Gabripj, Feldmann
 Patrocionio: InstaMC

 */
package nativelevel.sisteminhas;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.StateFlag.State;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import java.util.ArrayList;
import java.util.List;
import nativelevel.Jobs;
import nativelevel.KoM;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

public class Tralhas {

    
    
   
    
    
    
    /**
     * Gets entities inside a cone.
     *
     * @see Utilities#getPlayersInCone(List, Location, int, int, int)
     *
     * @param entities - {@code List<Entity>}, list of nearby entities
     * @param startpoint - {@code Location}, center point
     * @param radius - {@code int}, radius of the circle
     * @param degrees - {@code int}, angle of the cone
     * @param direction - {@code int}, direction of the cone
     * @return {@code List<Entity>} - entities in the cone
     */
    public static List<Entity> getEntitiesInCone(List<Entity> entities, Location startpoint, int radius, int degrees, int direction) {
        List<Entity> newEntities = new ArrayList<Entity>();

        int[] startPos = new int[]{(int) startpoint.getX(), (int) startpoint.getZ()};

        int[] endA = new int[]{(int) (radius * Math.cos(direction - (degrees / 2))), (int) (radius * Math.sin(direction - (degrees / 2)))};

        for (Entity e : entities) {
            Location l = e.getLocation();
            int[] entityVector = getVectorForPoints(startPos[0], startPos[1], l.getBlockX(), l.getBlockY());

            double angle = getAngleBetweenVectors(endA, entityVector);
            if (Math.toDegrees(angle) < degrees && Math.toDegrees(angle) > 0) {
                newEntities.add(e);
            }
        }
        return newEntities;
    }

    public static double getAngle(Vector vec1, Vector vec2) {
       // vec1 = vec1.normalize();
       // vec2 = vec2.normalize();
        vec1.setY(1);
        vec2.setY(1);
        return vec1.angle(vec2) * 180.0F / 3.141592653589793D;
    }

    /**
     * Created an integer vector in 2d between two points
     *
     * @param x1 - {@code int}, X pos 1
     * @param y1 - {@code int}, Y pos 1
     * @param x2 - {@code int}, X pos 2
     * @param y2 - {@code int}, Y pos 2
     * @return {@code int[]} - vector
     */
    public static int[] getVectorForPoints(int x1, int y1, int x2, int y2) {
        return new int[]{x2 - x1, y2 - y1};
    }

    /**
     * Get the angle between two vectors.
     *
     * @param vector1 - {@code int[]}, vector 1
     * @param vector2 - {@code int[]}, vector 2
     * @return {@code double} - angle
     */
    public static double getAngleBetweenVectors(int[] vector1, int[] vector2) {
        return Math.atan2(vector2[1], vector2[0]) - Math.atan2(vector1[1], vector1[0]);
    }

    public static void setDurabilityPC(ItemStack item, int percent) {
        if (percent > 100) {
            percent = 100;
        }
        item.setDurability((short) (item.getType().getMaxDurability() - (percent * item.getType().getMaxDurability()) / 100));
    }

    // double d, double d1, double d2, float f, List list, Vec3D vec3d
    public static void explosionEffect(LivingEntity player) {
        Location loc = player.getLocation();
        player.getWorld().createExplosion(loc, 0.0F, false);
    }

    public static void doRandomKnock(LivingEntity e, float power) {
        if(e.getType()==EntityType.PLAYER) {
            if(((Player)e).hasPermission("kom.semkb")) {
                return;
            }
        }
        double varX = 0D;
        double varY = 0D;

        int rnd = Jobs.rnd.nextInt(3);
        if (rnd == 1) {
            varX = 0.5D;
        } else if (rnd == 2) {
            varX = -0.5D;
        }

        rnd = Jobs.rnd.nextInt(3);
        if (rnd == 1) {
            varY = 0.5D;
        } else if (rnd == 2) {
            varY = -0.5D;
        }
        e.setVelocity(new Vector(varX, 0.6D, varY).multiply(power));
    }

    public static void lightningEffect(Location location) {
        int x = location.getBlockX();
        double y = location.getBlockY();
        int z = location.getBlockZ();
        Location toStrike = new Location(location.getWorld(), x, y, z);
        location.getWorld().strikeLightningEffect(toStrike);
    }

    public static void smokeScreenEffect(Location location) {
        for (int i = 0; i < 10; i++) {
            location.getWorld().playEffect(location, Effect.SMOKE, Jobs.rnd.nextInt(9));
        }
    }

    public static LivingEntity getTargetedEntity(Player player, int range, boolean checkLos) {
        boolean targetNonPlayers = true;
        boolean targetPlayers = true;
        List<Entity> ne = player.getNearbyEntities(range, range, range);
        ArrayList<LivingEntity> entities = new ArrayList<LivingEntity>();
        for (Entity e : ne) {
            if (((e instanceof LivingEntity))
                    && ((targetPlayers) || (!(e instanceof Player))) && ((targetNonPlayers) || ((e instanceof Player)))) {
                entities.add((LivingEntity) e);
            }

        }

        LivingEntity target = null;
        BlockIterator bi;
        try {
            bi = new BlockIterator(player, range);
        } catch (IllegalStateException e) {
            return null;
        }
        int bx;
        int by;
        int bz;
        while (bi.hasNext()) {
            Block b = bi.next();
            bx = b.getX();
            by = b.getY();
            bz = b.getZ();
            if ((checkLos) && b.getType() != Material.AIR) //&& (!MagicSpells.getTransparentBlocks().contains(Byte.valueOf((byte)b.getTypeId()))))
            {
                break;
            }

            for (LivingEntity e : entities) {
                Location l = e.getLocation();
                double ex = l.getX();
                double ey = l.getY();
                double ez = l.getZ();
                if ((bx - 0.75D <= ex) && (ex <= bx + 1.75D) && (bz - 0.75D <= ez) && (ez <= bz + 1.75D) && (by - 1 <= ey) && (ey <= by + 2.5D)) {
                    target = e;

                    //  if ((target != null) && (MagicSpells.getNoMagicZoneManager() != null) && (MagicSpells.getNoMagicZoneManager().willFizzle(target.getLocation(), this))) {
                    //   target = null;
                    //   continue;
                    //  }
                    return target;
                }
            }

        }

        return null;
    }

    public static Material getBaseMaterial(Material m) {
        if (m == Material.STONE_SWORD || m == Material.STONE_AXE || m == Material.STONE_PICKAXE || m == Material.STONE_HOE) {
            return Material.STONE;
        } else if (m == Material.IRON_SWORD || m == Material.IRON_AXE || m == Material.IRON_PICKAXE || m == Material.IRON_HOE) {
            return Material.IRON_INGOT;
        } else if (m == Material.GOLD_AXE || m == Material.GOLD_PICKAXE || m == Material.GOLD_HOE) {
            return Material.GOLD_INGOT;
        } else if (m == Material.DIAMOND_SWORD || m == Material.DIAMOND_AXE || m == Material.DIAMOND_PICKAXE || m == Material.DIAMOND_HOE) {
            return Material.DIAMOND;
        } else if (m == Material.IRON_BOOTS || m == Material.IRON_CHESTPLATE || m == Material.IRON_HELMET || m == Material.IRON_LEGGINGS) {
            return Material.IRON_INGOT;
        } else if (m == Material.GOLD_BOOTS || m == Material.GOLD_CHESTPLATE || m == Material.GOLD_HELMET || m == Material.GOLD_LEGGINGS) {
            return Material.GOLD_INGOT;
        } else if (m == Material.DIAMOND_BOOTS || m == Material.DIAMOND_CHESTPLATE || m == Material.DIAMOND_HELMET || m == Material.DIAMOND_LEGGINGS) {
            return Material.DIAMOND;
        } else if (m == Material.CHAINMAIL_BOOTS || m == Material.CHAINMAIL_CHESTPLATE || m == Material.CHAINMAIL_HELMET || m == Material.CHAINMAIL_LEGGINGS) {
            return Material.IRON_INGOT;
        }
        return null;
    }
}
