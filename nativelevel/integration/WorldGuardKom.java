/*

 ╭╮╭━╮╱╱╭━╮╭━╮
 ┃┃┃╭╯╱╱┃┃╰╯┃┃
 ┃╰╯╯╭━━┫╭╮╭╮┃
 ┃╭╮┃┃╭╮┃┃┃┃┃┃
 ┃┃┃╰┫╰╯┃┃┃┃┃┃
 ╰╯╰━┻━━┻╯╰╯╰╯

 Desenvolvedor: ZidenVentania
 Colaboradores: NeT32, Gabripj, Feldmann
 Patrocionio: InstaMC

 */
package nativelevel.integration;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.StateFlag.State;
import nativelevel.KoM;
import nativelevel.sisteminhas.ClanLand;
import org.bukkit.Location;

/**
 *
 * @author usuario
 */
public class WorldGuardKom {

    public static boolean ehSafeZone(Location l) {
        if (l.getWorld().getName().equalsIgnoreCase("vila")) {
            return true;
        }
        
        String tipo = ClanLand.getTypeAt(l);
        if(tipo.equalsIgnoreCase("SAFE"))
            return true;
        
        ApplicableRegionSet set = KoM.worldGuard.getRegionManager(l.getWorld()).getApplicableRegions(l);
         if(set.queryState(null, DefaultFlag.PVP) == StateFlag.State.DENY) {
            return true;
        }
        return false;
    }
}
