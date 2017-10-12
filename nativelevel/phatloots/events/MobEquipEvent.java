package nativelevel.phatloots.events;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.meta.PotionMeta;

/**
 * Called when a mob is spawned and given a weapon/armor/potion effect
 *
 * @author Cody
 */
public class MobEquipEvent extends PhatLootsEvent {
    private static final HandlerList handlers = new HandlerList();
    private final LivingEntity mob;
    private PotionMeta potion;

    /**
     * Creates a new event with the given data
     *
     * @param mob The mob whose equipment was modified
     * @param potion The PotionMeta that is to be applied to the mob
     */
    public MobEquipEvent(LivingEntity mob, PotionMeta potion) {
        this.mob = mob;
        this.potion = potion;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    /**
     * Returns the mob that was spawned
     *
     * @return The mob that was spawned
     */
    public LivingEntity getMob() {
        return mob;
    }

    /**
     * Returns the Equipment that the mob spawned with
     *
     * @return The EntityEquipment of the mob
     */
    public EntityEquipment getEquipment() {
        return mob.getEquipment();
    }

    /**
     * Returns the custom name of the mob
     *
     * @return The name of the mob
     */
    public String getCustomName() {
        return mob.getCustomName();
    }

    /**
     * Set the custom name of the mob
     *
     * @param name The new name for the mob
     */
    public void setCustomName(String name) {
        mob.setCustomName(name);
        mob.setCustomNameVisible(name != null);
    }

    /**
     * Returns the Potion effects that will be applied to the mob
     *
     * @return The PotionMeta or null if there is none
     */
    public PotionMeta getPotionMeta() {
        return potion;
    }

    /**
     * Set the PotionMeta to be applied to the mob
     *
     * @param potion The new PotionMeta for the mob
     */
    public void setPotionMeta(PotionMeta potion) {
        this.potion = potion;
    }
}
