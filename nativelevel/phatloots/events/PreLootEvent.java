package nativelevel.phatloots.events;

/**
 * Called when a PhatLoot is about to be looted
 *
 * @author Codisimus
 */
public abstract class PreLootEvent extends PhatLootsEvent {
    protected double lootingBonus;

    /**
     * Returns the added bonus looting percentage
     *
     * @return The LootingBonus which may be positive of negative
     */
    public double getLootingBonus() {
        return lootingBonus;
    }

    /**
     * Sets the bonus amount of looting
     *
     * @param lootingBonus The LootingBonus which may be positive of negative
     */
    public void setLootingBonus(double lootingBonus) {
        this.lootingBonus = lootingBonus;
    }
}
