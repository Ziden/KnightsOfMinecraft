package nativelevel.phatloots.events;

import nativelevel.phatloots.PhatLoot;
import nativelevel.phatloots.PhatLootChest;
import nativelevel.phatloots.loot.LootBundle;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

/**
 * Called when a Player loots a PhatLoot
 *
 * @author Codisimus
 */
public class PlayerLootEvent extends LootEvent {
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
     * @param lootBundle The Loot that the Player received
     */
    public PlayerLootEvent(Player looter, PhatLoot phatLoot, PhatLootChest chest, LootBundle lootBundle) {
        this.looter = looter;
        this.phatLoot = phatLoot;
        this.chest = chest;
        this.lootBundle = lootBundle;
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
