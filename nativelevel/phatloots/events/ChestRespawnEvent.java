package nativelevel.phatloots.events;

import nativelevel.phatloots.PhatLootChest;
import org.bukkit.block.Block;
import org.bukkit.event.HandlerList;

/**
 * Called when a PhatLootChest respawns
 *
 * @author Codisimus
 */
public class ChestRespawnEvent extends PhatLootChestEvent {
    private static final HandlerList handlers = new HandlerList();
    public static enum RespawnReason { INITIAL, DELAYED, PLUGIN_DISABLED, OTHER }
    private long delay;
    private final RespawnReason reason;

    /**
     * Creates a new event with the given data
     *
     * @param chest The PhatLootChest that respawned
     * @param delay The amount of time to delay the respawn process
     * @param reason The reason that the chest is respawning
     */
    public ChestRespawnEvent(PhatLootChest chest, long delay, RespawnReason reason) {
        this.chest = chest;
        this.delay = delay;
        this.reason = reason;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    /**
     * Returns the amount of time (in ticks) until the chest respawns
     *
     * @return The amount of time to delay the respawn process
     */
    public long getRespawnTime() {
        return delay;
    }

    /**
     * Sets how long to delay the chest respawning
     *
     * @param time The new time in ticks
     */
    public void setRespawnTime(long time) {
        delay = time;
    }

    /**
     * Sets where the chest should respawn
     *
     * @param target The Block location
     */
    public void setRespawnLocation(Block target) {
        chest.moveTo(target);
    }

    /**
     * Returns the reason that the chest has respawned
     *
     * @return The reason for the respawning of the PhatLootChest
     */
    public RespawnReason getReason() {
        return reason;
    }
}
