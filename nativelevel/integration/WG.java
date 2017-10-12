/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.integration;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.StateFlag.State;
import nativelevel.KoM;
import org.bukkit.Location;

/**
 *
 * @author usuario
 */
public class WG {
    
    public static boolean ehSafeZone(Location l) {
        if(l.getWorld().getName().equalsIgnoreCase("vila")) return true;
        ApplicableRegionSet set = KoM.worldGuard.getRegionManager(l.getWorld()).getApplicableRegions(l);
        if(set.queryState(null, DefaultFlag.PVP) == StateFlag.State.DENY) {
            return true;
        }
       // if(set.getFlag(DefaultFlag.PVP)==State.DENY) {
       //     return true;
       // }
        return false;
    }
}
