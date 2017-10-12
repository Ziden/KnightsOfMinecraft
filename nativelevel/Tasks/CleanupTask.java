package nativelevel.Tasks;

import nativelevel.KoM;
import nativelevel.Language.MSG;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.World;
import org.bukkit.ChatColor;

/**
 *
 * @author Nosliw
 */
public class CleanupTask implements Runnable {

    public static final int DELAY = 15; // How often task cycle?
    public static final int CYCLES = 4 * 15; // Cycles before cleanup?
    private int counter = 0;

    @Override
    public void run() {
        counter++;

        for (Player p : Bukkit.getOnlinePlayers()) {

            if (counter >= CYCLES) {
                p.sendMessage(ChatColor.LIGHT_PURPLE + "Jabu irá purificar Items e Mobs !");
            } else if (counter >= CYCLES - 1) {
                p.sendMessage(ChatColor.LIGHT_PURPLE + "Jabu irá purificar Items e Mobs em 15 segundos.");
            } else if (counter >= CYCLES - 4) {
                p.sendMessage(ChatColor.LIGHT_PURPLE + "Jabu irá purificar Items e Mobs em 60 segundos.");
            }
        }

        if (counter >= CYCLES) {
            for (World world : Bukkit.getWorlds()) {
                for (Entity e : world.getEntities()) {
                    
                    if(e.hasMetadata("NPC"))
                        continue;
                    
                    if (e.getType() == EntityType.DROPPED_ITEM && e.getType() != EntityType.ARMOR_STAND) {
                        e.remove();
                    } else if (e instanceof Monster) {
                        LivingEntity monstro = (LivingEntity) e;
                        if (!KoM.mm.getMobManager().isActiveMob(e.getUniqueId()) && monstro.getHealth() == monstro.getMaxHealth()) {
                            e.remove();
                        }
                    }
                }
            }
            counter = 0;
        }
    }
}
