package nativelevel.phatloots.events;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

/**
 * Called when a mob is killed and drops custom loot
 *
 * @author Codisimus
 */
public class PreMobDropLootEvent extends PreLootEvent {
    private static final HandlerList handlers = new HandlerList();
    private final LivingEntity mob;
    private final Player killer;

    /**
     * Creates a new event with the given data
     *
     * @param mob The mob that was killed
     * @param killer The Player who killed the mob or null if the mob died of natural causes
     * @param lootingBonus The bonus amount of looting probability
     */
    public PreMobDropLootEvent(LivingEntity mob, Player killer, double lootingBonus) {
        this.mob = mob;
        this.killer = killer;
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
     * Returns the mob that was killed
     *
     * @return The mob that was killed
     */
    public LivingEntity getMob() {
        return mob;
    }

    /**
     * Returns the Player who killed the mob
     *
     * @return The Player who killed the mob or null if the mob died of natural causes
     */
    public Player getKiller() {
        return killer;
    }
}
