package nativelevel.phatloots.events;

import nativelevel.phatloots.PhatLootChest;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

/**
 * Called when a PhatLootChest breaks
 *
 * @author Codisimus
 */
public class ChestBreakEvent extends PhatLootChestEvent {
    private static final HandlerList handlers = new HandlerList();
    private final Player lastLooter;
    private long respawnTime;

    /**
     * Creates a new event with the given data
     *
     * @param lastLooter The Player who was last seen looting the chest
     * @param chest The PhatLootChest being looted or null if no chest was involved
     * @param respawnTime The amount of time (in ticks) until the chest respawns or -1 if it never respawns
     */
    public ChestBreakEvent(PhatLootChest chest, Player lastLooter, long respawnTime) {
        this.chest = chest;
        this.lastLooter = lastLooter;
        this.respawnTime = respawnTime;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    /**
     * Returns the Player who was last seen looting the chest
     *
     * @return The Player who was last seen looting the chest
     */
    public Player getLastLooter() {
        return lastLooter;
    }

    /**
     * Returns the amount of time (in ticks) until the chest respawns
     *
     * @return The amount of time until the chest respawns or -1 if it never respawns
     */
    public long getRespawnTime() {
        return respawnTime;
    }

    /**
     * Sets how long until the chest respawns
     *
     * @param time The new time in ticks
     */
    public void setRespawnTime(long time) {
        respawnTime = time;
    }
}
