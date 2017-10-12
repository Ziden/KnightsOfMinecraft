package nativelevel.phatloots.listeners;

import nativelevel.phatloots.PhatLoot;
import nativelevel.phatloots.PhatLoots;
import net.citizensnpcs.api.event.NPCDeathEvent;
import net.citizensnpcs.api.event.NPCSpawnEvent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Listens for NPC Spawns/Deaths and checks if there is a PhatLoot named after them
 *
 * @author Codisimus
 */
public class CitizensListener implements Listener {
    @EventHandler (ignoreCancelled = true)
    public void onNPCSpawn(NPCSpawnEvent event) {
        PhatLoot phatLoot = PhatLoots.getPhatLoot("NPC" + event.getNPC().getName() + "Spawn");
        if (phatLoot == null) {
            phatLoot = PhatLoots.getPhatLoot("NPCSpawn");
        }

        if (phatLoot != null) {
            Entity entity = event.getNPC().getEntity();
            if (entity instanceof LivingEntity) {
                phatLoot.rollForEquipment((LivingEntity) entity, 0);
            }
        }
    }

    @EventHandler (ignoreCancelled = true)
    public void onNPCDeath(NPCDeathEvent event) {
        PhatLoot phatLoot = PhatLoots.getPhatLoot("NPC" + event.getNPC().getName());
        if (phatLoot == null) {
            phatLoot = PhatLoots.getPhatLoot("NPC");
        }

        if (phatLoot != null) {
            Entity entity = event.getNPC().getEntity();
            if (entity instanceof LivingEntity) {
                event.setDroppedExp(phatLoot.rollForMobDrops(null, ((LivingEntity) entity).getKiller(), event.getDrops()));
            }
        }
    }
}
