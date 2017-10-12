package nativelevel.phatloots.loot;

import nativelevel.phatloots.PhatLoot;
import nativelevel.phatloots.PhatLoots;
import nativelevel.phatloots.PhatLootsUtil;
import nativelevel.phatloots.gui.Button;
import nativelevel.phatloots.gui.InventoryListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * An Experience is a range of experience to be given to the looter
 *
 * @author Codisimus
 */
@SerializableAs("Experience")
public class Experience extends Loot {
    private int lowerAmount;
    private int upperAmount;

    /**
     * Adds Experience as Loot
     */
    private static class AddExperienceButton extends Button {
        private AddExperienceButton(ItemStack item) {
            super(item);
        }

        @Override
        public boolean onClick(ClickType click, Inventory inv, PhatLoot phatLoot, List<Loot> lootList) {
            //Add a new Experience loot
            lootList.add(new Experience(0, 0));
            return true;
        }
    }

    /**
     * Registers the AddExperienceButton in the PhatLoot GUI
     */
    public static void registerButton() {
        //Register the Add Experience Button
        ItemStack item = new ItemStack(Material.EXP_BOTTLE);
        ItemMeta info = Bukkit.getItemFactory().getItemMeta(item.getType());
        info.setDisplayName("§2Add experience...");
        item.setItemMeta(info);

        InventoryListener.registerButton(new Experience.AddExperienceButton(item));
    }

    /**
     * Constructs a new Experience loot for the given range
     *
     * @param lowerAmount The lower bound of the range
     * @param upperAmount The upper bound of the range
     */
    public Experience(int lowerAmount, int upperAmount) {
        this.lowerAmount = lowerAmount;
        this.upperAmount = upperAmount;
    }

    /**
     * Constructs a new Experience loot from a Configuration Serialized phase
     *
     * @param map The map of data values
     */
    public Experience(Map<String, Object> map) {
        String currentLine = null; //The value that is about to be loaded (used for debugging)
        try {
            probability = (Double) map.get(currentLine = "Probability");
            lowerAmount = (Integer) map.get(currentLine = "Lower");
            upperAmount = (Integer) map.get(currentLine = "Upper");
        } catch (Exception ex) {
            //Print debug messages
            PhatLoots.logger.severe("Failed to load Experience line: " + currentLine);
            PhatLoots.logger.severe("of PhatLoot: " + (PhatLoot.current == null ? "unknown" : PhatLoot.current));
            PhatLoots.logger.severe("Last successfull load was...");
            PhatLoots.logger.severe("PhatLoot: " + (PhatLoot.last == null ? "unknown" : PhatLoot.last));
            PhatLoots.logger.severe("Loot: " + (Loot.last == null ? "unknown" : Loot.last.toString()));
        }
    }

    /**
     * Adds this experience to the LootBundle
     *
     * @param lootBundle The loot that has been rolled for
     * @param lootingBonus The increased chance of getting rarer loots
     */
    @Override
    public void getLoot(LootBundle lootBundle, double lootingBonus) {
        lootBundle.addExp(PhatLootsUtil.rollForInt(lowerAmount, upperAmount));
    }

    /**
     * Returns the information of the Experience in the form of an ItemStack
     *
     * @return An ItemStack representation of the Loot
     */
    @Override
    public ItemStack getInfoStack() {
        //An Experience loot is represented by an Experience Bottle
        ItemStack infoStack = new ItemStack(Material.EXP_BOTTLE);

        //Set the display name of the item
        ItemMeta info = Bukkit.getItemFactory().getItemMeta(infoStack.getType());
        info.setDisplayName("§2Experience");

        //Add more specific details of the Experience loot
        List<String> details = new ArrayList();
        details.add("§4Probability: §6" + probability);
        details.add("§4Lower Bound: §6" + lowerAmount);
        details.add("§4Upper Bound: §6" + upperAmount);

        //Construct the ItemStack and return it
        info.setLore(details);
        infoStack.setItemMeta(info);
        return infoStack;
    }

    /**
     * @return false because this type of Loot has no toggleable settings
     */
    @Override
    public boolean onToggle(ClickType click) {
        return false;
    }

    /**
     * Modifies the amount of experience that is to be looted
     *
     * @param amount The amount to modify by (may be negative)
     * @param both Whether both the upper and lower bounds are being modified
     * @return true because the experience amount has changed
     */
    @Override
    public boolean modifyAmount(int amount, boolean both) {
        if (both) {
            lowerAmount += amount;
        }
        upperAmount += amount;
        if (amount < 0) {
            if (upperAmount < 0) {
                resetAmount();
            } else if (lowerAmount < 0) {
                lowerAmount = 0;
            }
        }
        return true;
    }

    /**
     * Sets the amount of experience to be looted to 0
     *
     * @return true because the experience amount has changed
     */
    @Override
    public boolean resetAmount() {
        lowerAmount = 0;
        upperAmount = 0;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(lowerAmount);
        if (lowerAmount != upperAmount) {
            sb.append("-");
            sb.append(upperAmount);
        }
        sb.append(" experience");
        sb.append(" @ ");
        //Only display the decimal values if the probability is not a whole number
        sb.append(String.valueOf(Math.floor(probability) == probability ? (int) probability : probability));
        sb.append("%");
        return sb.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Experience) {
            Experience loot = (Experience) object;
            return loot.lowerAmount == lowerAmount
                    && loot.upperAmount == upperAmount;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + this.lowerAmount;
        hash = 47 * hash + this.upperAmount;
        return hash;
    }

    @Override
    public Map<String, Object> serialize() {
        Map map = new TreeMap();
        map.put("Probability", probability);
        map.put("Lower", lowerAmount);
        map.put("Upper", upperAmount);
        return map;
    }
}
