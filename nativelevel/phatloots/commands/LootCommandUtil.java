package nativelevel.phatloots.commands;

import nativelevel.phatloots.PhatLoot;
import nativelevel.phatloots.PhatLoots;
import nativelevel.phatloots.PhatLootsUtil;
import nativelevel.phatloots.loot.Loot;
import nativelevel.phatloots.loot.LootCollection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

/**
 * Utility methods commonly used in the LootCommand class
 *
 * @author Codisimus
 */
public class LootCommandUtil {

    /**
     * Adds/Removes a Loot to the specified PhatLoot
     *
     * @param sender The CommandSender modifying the PhatLoot
     * @param phatLootName The name of the PhatLoot to be modified or null to indicate all linked PhatLoots
     * @param add True the Loot will be added, false if it will be removed
     * @param collName The name of the collection if any
     * @param loot The Loot that will be added/removed
     */
    public static void setLoot(CommandSender sender, String phatLootName, boolean add, String collName, Loot loot) {
        String lootDescription = loot.toString();

        for (PhatLoot phatLoot : getPhatLoots(sender, phatLootName)) {
            //Check if a LootCollection was specified
            LootCollection coll = null;
            if (collName != null) {
                coll = phatLoot.findCollection(collName);
                if (coll == null) {
                    sender.sendMessage("§4Collection §6" + collName + "§4 does not exist");
                    return;
                }
            }

            if (coll == null) {
                if (add) { //Add to PhatLoot
                    if (phatLoot.addLoot(loot)) { //Successful
                        sender.sendMessage("§6" + lootDescription
                                + "§5 added as Loot for PhatLoot §6"
                                + phatLoot.name);
                        phatLoot.save();
                    } else { //Unsuccessful
                        sender.sendMessage("§6" + lootDescription
                                + "§4 is already Loot for PhatLoot §6"
                                + phatLoot.name);
                    }
                } else { //Remove from PhatLoot
                    if (phatLoot.removeLoot(loot)) { //Successful
                        sender.sendMessage("§6" + lootDescription
                                + "§5 removed as Loot for PhatLoot §6"
                                + phatLoot.name);
                        phatLoot.save();
                    } else { //Unsuccessful
                        sender.sendMessage("§6" + lootDescription
                                + "§4 is not Loot for PhatLoot §6"
                                + phatLoot.name);
                    }
                }
            } else {
                if (add) { //Add to LootCollection
                    if (coll.addLoot(loot)) { //Successful
                        sender.sendMessage("§6" + lootDescription
                                + "§5 added as Loot for Collection §6"
                                + coll.name);
                        phatLoot.save();
                    } else { //Unsuccessful
                        sender.sendMessage("§6" + lootDescription
                                + "§4 is already Loot for Collection §6"
                                + coll.name);
                    }
                } else { //Remove from LootCollection
                    if (coll.removeLoot(loot)) { //Successful
                        sender.sendMessage("§6" + lootDescription
                                + "§5 removed as Loot for Collection §6"
                                + coll.name);
                        phatLoot.save();
                    } else { //Unsuccessful
                        sender.sendMessage("§6" + lootDescription
                                + "§4 is not Loot for Collection §6"
                                + coll.name);
                    }
                }
            }
        }
    }

    /**
     * Returns the a LinkedList of PhatLoots.
     * If a name is provided then only the PhatLoot with the given name will be in the List.
     * If no name is provided then each PhatLoot that is linked to the target Block will be in the List
     *
     * @param sender The CommandSender targeting a Block
     * @param name The name of the PhatLoot to be found
     * @return The a LinkedList of PhatLoots
     */
    public static LinkedList<PhatLoot> getPhatLoots(CommandSender sender, String name) {
        LinkedList<PhatLoot> phatLoots = new LinkedList<>();

        if (name != null) {
            //Find the PhatLoot using the given name
            PhatLoot phatLoot = PhatLoots.getPhatLoot(name);

            //Inform the sender if the PhatLoot does not exist
            if (phatLoot != null ) {
                phatLoots.add(phatLoot);
            } else {
                sender.sendMessage("§4PhatLoot §6" + name + "§4 does not exist.");
            }
        } else {
            //Cancel is the sender is console
            if (sender instanceof Player) {
                phatLoots = PhatLootsUtil.getPhatLoots((Player) sender);
            } else {
                sender.sendMessage("§4You cannot do this from the console!");
            }
        }

        return phatLoots;
    }

    /**
     * Retrieves the first int value from the given string
     *
     * @param string The String that contains the amount
     * @return The int value of -1 if no int was found
     */
    public static int getLowerBound(String string) {
        if (string.contains("-")) {
            string = string.substring(0, string.indexOf('-'));
        }

        try {
            return Integer.parseInt(string);
        } catch (Exception notInt) {
            return -1;
        }
    }

    /**
     * Retrieves the last int value from the given string
     *
     * @param string The String that contains the amount
     * @return The int value of -1 if no int was found
     */
    public static int getUpperBound(String string) {
        if (string.contains("-")) {
            string = string.substring(string.indexOf('-') + 1);
        }

        try {
            return Integer.parseInt(string);
        } catch (Exception notInt) {
            return -1;
        }
    }

    /**
     * Retrieves Enchantments from the given string
     *
     * @param string The String that contains the item
     * @return The Enchantments of the item
     */
    public static Map<Enchantment, Integer> getEnchantments(String string) {
        Map<Enchantment, Integer> enchantments = new HashMap<>();
        try {
            for (String split: string.split("&")) {
                Enchantment enchantment = null;
                int level = -1;

                if (split.contains("(")) {
                    int index = split.indexOf('(');
                    level = Integer.parseInt(split.substring(index + 1, split.length() - 1));
                    split = split.substring(0, index);
                }

                for (Enchantment enchant: Enchantment.values()) {
                    if (enchant.getName().equalsIgnoreCase(split)) {
                        enchantment = enchant;
                    }
                }

                if (enchantment == null) {
                    continue;
                }

                if (level < enchantment.getStartLevel()) {
                    level = enchantment.getStartLevel();
                }

                enchantments.put(enchantment, level);
            }
        } catch (Exception notEnchantment) {
            return null;
        }
        return enchantments;
    }

    /**
     * Retrieves a short value from the given string
     *
     * @param string The String that contains the number
     * @return The short or -1 if the String was not a short
     */
    public static short getData(String string) {
        short data;
        try {
            data = Short.parseShort(string);
        } catch (Exception notShort) {
            return -1;
        }
        return data;
    }

    /**
     * Retrieves a double value from the given string that ends with %
     *
     * @param sender The CommandSender that will receive error messages
     * @param string The String that contains the percent
     * @return The double value or -1 if the percent was invalid
     */
    public static double getPercent(CommandSender sender, String string) {
        double percent;
        try {
            percent = Double.parseDouble(string);
            if (percent < 0) {
                if (sender != null) {
                    sender.sendMessage("§4The percent cannot be below 0");
                }
            }
            if (percent > 100) {
                if (sender != null) {
                    sender.sendMessage("§4The percent cannot be above 100");
                }
            } else {
                return percent;
            }
        } catch (Exception notDouble) {
            if (sender != null) {
                sender.sendMessage("§6" + string + "§4 is not a valid number");
            }
        }
        return -1;
    }
}
