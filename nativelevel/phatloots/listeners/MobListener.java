package nativelevel.phatloots.listeners;

import nativelevel.phatloots.PhatLoot;
import nativelevel.phatloots.PhatLoots;
import nativelevel.phatloots.regions.RegionHook;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.entity.Horse.Variant;
import org.bukkit.entity.*;
import org.bukkit.event.Listener;

/**
 * Listens for Mob events that trigger loots
 *
 * @author Codisimus
 */
public abstract class MobListener implements Listener {
    public static boolean mobTypes;
    public static boolean namedMobs;
    public static RegionHook regionHook;
    public boolean mobWorlds;
    public boolean mobRegions;

    /**
     * Returns a cleaned up string representation of the given Entity's type
     *
     * @param entity The given Entity
     * @return The name of the type of the Entity
     */
    abstract String getLootType();

    /**
     * Returns the PhatLoot for the given Entity if one exists.
     * A PhatLoot for a mob is searched for in the following order
     *     With a custom name
     *     Of a specific Type
     *         In a specific Region
     *         In a specific World
     *     Of any type
     *         In a specific Region
     *         In a specific World
     *     Of a specific Type
     *         Anywhere
     *     Of any type
     *         Anywhere
     *
     * @param entity The given Entity
     * @return The PhatLoot or null if there are none for the Entity
     */
    public PhatLoot getPhatLoot(LivingEntity entity) {
        //First check for a PhatLoot matching the mob's custom name
        if (namedMobs) {
            String name = entity instanceof HumanEntity
                          ? ((HumanEntity) entity).getName() //NPC or Player
                          : entity.getCustomName(); //Mob
            if (name != null) {
                name = name.replace(" ", "_");
                name = name.replace("§", "&");
                name += getLootType();
                PhatLoot phatLoot = PhatLoots.getPhatLoot(name);
                if (phatLoot != null) {
                    //A PhatLoot for a named mob trumps all others
                    return phatLoot;
                }
            }
        }

        //Retrieve the more specific type of the mob if there is one
        //ex. Wither Skeleton as opposed to normal Skeleton
        //    or a Priest rather than a normal Villager
        String type = (entity instanceof Player) ? "Player" : entity.getType().getName();
        String specificType = null;
        if (mobTypes) {
            switch (entity.getType()) {
            case PIG_ZOMBIE:
            case ZOMBIE: //'BabyVillager' | 'Baby' | 'Villager' | 'Normal'
                Zombie zombie = (Zombie) entity;
                if (zombie.isBaby()) {
                    specificType = zombie.isVillager() ? "BabyVillager" : "Baby";
                } else if (zombie.isVillager()) {
                    specificType = "Villager";
                } else {
                    specificType = "Normal";
                }
                break;
            case SKELETON: //'Wither' | 'Normal'
                specificType = toCamelCase(((Skeleton) entity).getSkeletonType());
                break;
            case VILLAGER: //Profession
                specificType = toCamelCase(((Villager) entity).getProfession());
                break;
            case CREEPER: //'Powered' | 'Normal'
                Creeper creeper = (Creeper) entity;
                specificType = creeper.isPowered() ? "Powered" : "Normal";
                break;
            case HORSE: //Color + Style (type is also determined by variant
                Horse horse = (Horse) entity;
                type = toCamelCase(horse.getVariant());
                if (horse.getVariant() == Variant.HORSE) {
                    specificType = toCamelCase(horse.getColor());
                    switch (horse.getStyle()) {
                    case WHITE_DOTS:
                        specificType += "Spotted";
                        break;
                    case BLACK_DOTS:
                        specificType += "Dark";
                        break;
                    case WHITE:
                        specificType += "Light";
                        break;
                    case WHITEFIELD:
                        specificType += "Milky";
                        break;
                    }
                }
                break;
            case SHEEP: //Color
                Sheep sheep = (Sheep) entity;
                specificType = toCamelCase(sheep.getColor());
                break;
            default:
                break;
            }
        }
        //Check if the entity is a baby
        if (entity instanceof Ageable && !((Ageable) entity).isAdult()) {
            //Amend 'Baby' to the beginning of the specific type
            specificType = specificType == null ? "Baby" : "Baby" + specificType;
        }

        //Check if the mob is within a region
        Location location = entity.getLocation();
        List<String> regionNames = new ArrayList<>();
        if (mobRegions && regionHook != null) {
            regionNames = regionHook.getRegionNames(location);
        }

        //Get the loot type and the name of the world for constructing the PhatLoot name
        type += getLootType();
        String worldName = mobWorlds ? '@' + location.getWorld().getName() : null;

        //The order of priority for finding the correct PhatLoot may be found in the documentation of this method
        PhatLoot phatLoot;
        if (mobTypes && specificType != null) {
            if (mobRegions) {
                for (String regionName : regionNames) {
                    phatLoot = PhatLoots.getPhatLoot(specificType + type + '@' + regionName);
                    if (phatLoot != null) {
                        return phatLoot;
                    }
                }
            }
            if (mobWorlds) {
                phatLoot = PhatLoots.getPhatLoot(specificType + type + worldName);
                if (phatLoot != null) {
                    return phatLoot;
                }
            }
        }

        if (mobRegions) {
            for (String regionName : regionNames) {
                phatLoot = PhatLoots.getPhatLoot(type + '@' + regionName);
                if (phatLoot != null) {
                    return phatLoot;
                }
            }
        }
        if (mobWorlds) {
            phatLoot = PhatLoots.getPhatLoot(type + worldName);
            if (phatLoot != null) {
                return phatLoot;
            }
        }

        if (mobTypes && specificType != null) {
            phatLoot = PhatLoots.getPhatLoot(specificType + type);
            if (phatLoot != null) {
                return phatLoot;
            }
        }

        return PhatLoots.getPhatLoot(type);
    }

    private static String toCamelCase(Enum type) {
        if (type == null) {
            return "Normal";
        }
        String s = type.name();
        String[] parts = s.split("_");
        String camelCaseString = "";
        for (String part : parts){
            camelCaseString = camelCaseString + toProperCase(part);
        }
        return camelCaseString;
    }

    private static String toProperCase(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }
}
