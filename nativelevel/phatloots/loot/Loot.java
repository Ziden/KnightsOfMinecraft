package nativelevel.phatloots.loot;

import nativelevel.phatloots.PhatLootsUtil;
import nativelevel.phatloots.gui.Tool;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

/**
 * A Loot has a probability of looting
 *
 * @author Codisimus
 */
public abstract class Loot implements Comparable, ConfigurationSerializable {
    static Loot last; //The last successfully loaded Loot (used for debugging)
    double probability = 100;

    /**
     * Adds the loot to the item list
     *
     * @param lootBundle The loot that has been rolled for
     * @param lootingBonus The increased chance of getting rarer loots
     */
    public abstract void getLoot(LootBundle lootBundle, double lootingBonus);

    /**
     * Returns the information of the loot in the form of an ItemStack
     *
     * @return An ItemStack representation of the Loot
     */
    public abstract ItemStack getInfoStack();

    /**
     * Toggles a Loot setting depending on the type of Click
     *
     * @param click The type of Click (Only SHIFT_LEFT, SHIFT_RIGHT, and MIDDLE are used)
     * @return true if the Loot InfoStack should be refreshed
     */
    public abstract boolean onToggle(ClickType click);

    /**
     * Modifies the amount associated with the Loot
     *
     * @param amount The amount to modify by (may be negative)
     * @param both true if both lower and upper ranges should be modified, false for only the upper range
     * @return true if the Loot InfoStack should be refreshed
     */
    public abstract boolean modifyAmount(int amount, boolean both);

    /**
     * Resets the amount of Loot to 1
     *
     * @return true if the Loot InfoStack should be refreshed
     */
    public abstract boolean resetAmount();

    /**
     * Manages clicking on Loot with a custom Tool
     *
     * @param tool The Tool that was used to click
     * @param click The type of Click (Only LEFT, RIGHT, MIDDLE, SHIFT_LEFT, SHIFT_RIGHT, and DOUBLE_CLICK are used)
     * @return true if the Loot InfoStack should be refreshed
     */
    public boolean onToolClick(Tool tool, ClickType click) {
        return false;
    }

    /**
     * Returns the chance of looting
     *
     * @return The chance of looting
     */
    public double getProbability() {
        return probability;
    }

    /**
     * Sets the Probability of looting
     *
     * @param probability The Probability to be set
     */
    public void setProbability(double probability) {
        this.probability = probability;
    }

    /**
     * Rolls for the loot and returns true if it was a successful roll
     *
     * @param lootingBonus The increased chance of getting rarer loots
     * @return true if the loot should be looted
     */
    public boolean rollForLoot(double lootingBonus) {
        return roll() < (probability + lootingBonus);
    }

    /**
     * Returns a double between 0 (inclusive) and 100 (exclusive)
     *
     * @return The number that was rolled
     */
    public static double roll() {
        return PhatLootsUtil.rollForDouble(100);
    }

    /**
     * Compares Loot objects to order them by their probability (lowest first)
     *
     * @param object An object which may not be an instance of Loot
     * @return 0 if the object is equal to this, 1 if the object's probability is lower, and -1 otherwise
     */
    @Override
    public int compareTo(Object object) {
        return object instanceof Loot
               ? Double.compare(probability, ((Loot) object).probability)
               : -1;
    }
}
