package nativelevel.CustomEvents;

import nativelevel.Classes.Mage.MageSpell;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 *
 * @author Ziden
 */
public class SpellHitEvent extends Event {

    public static HandlerList handlers = new HandlerList();
    
    private LivingEntity hit;
    private Location loc;
    private Projectile proj;
    private MageSpell spell;
    
    public SpellHitEvent(LivingEntity hit, Location loc, Projectile proj, MageSpell spell) {
        this.hit = hit;
        this.loc = loc;
        this.proj = proj;
        this.spell = spell;
    }

    public LivingEntity getHit() {
        return hit;
    }

    public void setHit(LivingEntity hit) {
        this.hit = hit;
    }

    public Location getLoc() {
        return loc;
    }

    public void setLoc(Location loc) {
        this.loc = loc;
    }

    public Projectile getProj() {
        return proj;
    }

    public void setProj(Projectile proj) {
        this.proj = proj;
    }

    @Override
    public HandlerList getHandlers() {
        return this.getHandlers();
    }
    
    
    
}
