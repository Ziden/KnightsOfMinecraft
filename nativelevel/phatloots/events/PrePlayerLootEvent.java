package nativelevel.phatloots.events;

import nativelevel.phatloots.PhatLoot;
import nativelevel.phatloots.PhatLootChest;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

/**
 * Called before a Player loots a PhatLoot
 *
 * @author Codisimus
 */
public class PrePlayerLootEvent extends PreLootEvent {
    private static final HandlerList handlers = new HandlerList();
    private final Player looter;
    private final PhatLoot phatLoot;
    private final PhatLootChest chest;

    /**
     * Creates a new event with the given data
     *
     * @param looter The Player who is looting
     * @param phatLoot The PhatLoot that the Player looted
     * @param chest The PhatLootChest being looted or null if no chest was involved
     * @param lootingBonus The bonus amount of looting probability
     */
    public PrePlayerLootEvent(Player looter, PhatLoot phatLoot, PhatLootChest chest, double lootingBonus) {
        this.looter = looter;
        this.phatLoot = phatLoot;
        this.chest = chest;
        this.lootingBonus = lootingBonus;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    /**
     * Returns the Player who is looting
     *
     * @return The Player who is looting
     */
    public Player getLooter() {
        return looter;
    }

    /**
     * Returns the PhatLoot that provided the loot
     *
     * @return The PhatLoot that provided the loot
     */
    public PhatLoot getPhatLoot() {
        return phatLoot;
    }

    /**
     * Returns the chest that is being looted
     *
     * @return The PhatLootChest being looted or null if no chest was involved
     */
    public PhatLootChest getChest() {
        return chest;
    }
}
