package nativelevel.phatloots.events;

import nativelevel.phatloots.loot.LootBundle;
import java.util.List;
import org.bukkit.inventory.ItemStack;

/**
 * Called when a PhatLoot is lootedThe LootingBonus which may be positive of negative
 *
 * @author Codisimus
 */
public abstract class LootEvent extends PhatLootsEvent {
    protected LootBundle lootBundle;

    /**
     * Returns the loot that will be looted
     *
     * @return the bundle of loot that will be looted
     */
    public LootBundle getLootBundle() {
        return lootBundle;
    }

    /**
     * Returns the items that will be looted
     *
     * @return A list of items that will be looted
     */
    public List<ItemStack> getItemList() {
        return lootBundle.getItemList();
    }

    /**
     * Returns the amount of money that the Player will loot
     *
     * @return The amount of money that the Player will loot
     */
    public double getMoney() {
        return lootBundle.getMoney();
    }

    /**
     * Sets the amount of money to be looted by the player
     *
     * @param money The amount of money to be looted
     */
    public void setMoney(double money) {
        lootBundle.setMoney(money);
    }

    /**
     * Returns the amount of experience that will be looted
     *
     * @return The amount of experience looted
     */
    public int getExp() {
        return lootBundle.getExp();
    }

    /**
     * Sets the amount of experience to be looted
     *
     * @param exp The amount of experience to be looted
     */
    public void setExp(int exp) {
        lootBundle.setExp(exp);
    }
}
