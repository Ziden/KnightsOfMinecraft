package nativelevel.Classes.Mage;

import java.util.HashMap;
import java.util.HashSet;
import nativelevel.CustomEvents.SpellHitEvent;
import nativelevel.KoM;
import static nativelevel.Classes.Mage.SpellParticleEffects.ParticleType.BLUE_REDSTONE;
import nativelevel.MetaShit;
import nativelevel.sisteminhas.KomSystem;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileHitEvent;

/**
 *
 * @author Ziden
 */
public class SpellParticleEffects extends KomSystem {

    public static HashMap<Entity, ParticleType> ativos = new HashMap<Entity, ParticleType>();

    public enum ParticleType {

        BLUE_REDSTONE
    }

    @EventHandler
    public void projectileHit(ProjectileHitEvent ev) {
        if (ativos.containsKey(ev.getEntity())) {
            ativos.remove(ev.getEntity());
        }

        if (ev.getEntity().hasMetadata("spell")) {
            MageSpell spell = (MageSpell) MetaShit.getMetaObject("spell", ev.getEntity());
            spell.spellHit(null, ev.getEntity().getLocation(), ev.getEntity());
        }
    }

    public void onEnable() {
        Runnable r = new Runnable() {
            public void run() {
                tick();
            }
        };
        Bukkit.getScheduler().scheduleSyncRepeatingTask(KoM._instance, r, 1, 1);
    }

    public static void tick() {
        for (Entity p : ativos.keySet()) {
            if (p == null) {
                ativos.remove(p);
                continue;
            } else if (!p.isValid()) {
                p.remove();
                ativos.remove(p);
                continue;
            }
            ParticleType tipo = ativos.get(p);
            if (tipo == BLUE_REDSTONE) {
                colorida(p.getLocation(), 53, 153, 255);
                colorida(p.getLocation().add(0, 0.5, 0), 53, 153, 255);
            }
        }
    }
    
    public static void colorida(Location l, int r, int g, int b) {
        l.getWorld().spigot().playEffect(l, Effect.COLOURED_DUST, 0, (byte) 0, (r/255)+0.01f, g/255f, b/255f, 1, 0, 30);

    }

}
