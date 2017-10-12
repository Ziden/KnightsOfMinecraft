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

package nativelevel.sisteminhas;

import org.bukkit.Location;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.LocationFlag;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import java.util.Iterator;
import nativelevel.CFG;
import nativelevel.KoM;
import nativelevel.Lang.L;
import nativelevel.integration.BungeeCordKom;
import nativelevel.MetaShit;
import nativelevel.MetaShit;
import nativelevel.utils.LocUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

/**
 * Used to travel between worlds.
 */
public class Boat {

    public static int timeInSeconds = 20;
    public static long lastBoat = 0;

    public static void printCooldown(Player p) {

        long tempoFalta = Boat.timeInSeconds - ((System.currentTimeMillis() / 1000) - lastBoat);
        p.sendMessage(ChatColor.GREEN + L.m("Faltam % segundos para o transporte chegar !", tempoFalta+""));
    }

    public static void timerStart() {

        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    if (KoM.debugMode) {
                        KoM.log.info("Starting Boat Timer..");
                    }
                    Boat.lastBoat = System.currentTimeMillis() / 1000;
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (p.getGameMode() == GameMode.CREATIVE)
                            continue;
                        if (!p.hasMetadata("Transporte")) { // Barco = Boat
                            
                            ApplicableRegionSet set = KoM.worldGuard.getRegionManager(p.getWorld()).getApplicableRegions(p.getLocation());
                            if (set.size() > 0) {

                                Iterator<ProtectedRegion> i = set.iterator();
                                while (i.hasNext()) {
                                    if (KoM.debugMode) {
                                        KoM.log.info("Checking Region..");
                                    }
                                    ProtectedRegion region = i.next();
                                    if(region.getId().contains("transporte-")) {
                                        p.teleport(CFG.spawnTree.toLocation());
                                        return;
                                    }
                                    if (region.getId().contains("barco-") || region.getId().contains("balao-")) {
                                        // ta entrando em um barco
                                        if (p.hasMetadata("Transporte")) {
                                            p.removeMetadata("Transporte", KoM._instance);
                                        }
                                        
                                        com.sk89q.worldedit.Location locWE  = region.getFlag(DefaultFlag.SPAWN_LOC);
                                        Location loc = new Location(Bukkit.getWorld(locWE.getWorld().getName()), locWE.getPosition().getBlockX(),locWE.getPosition().getBlockY(),locWE.getPosition().getBlockZ());        
                                        
                                        MetaShit.setMetaObject("Transporte", p, loc);
                                        // joining the boat
                                        if(region.getId().contains("balao-")) {
                                            BungeeCordKom.tp(p, "Arena", 5000, 75, -5000, 0, 0);
                                        } else {
                                            BungeeCordKom.tp(p, "Arena", -5000, 75, 5000, 0, 0);
                                        }
                                        
                                        p.sendMessage(ChatColor.GREEN + L.m("Você entrou no transporte ! Chegando no destino em % segundos.", Boat.timeInSeconds+""));
                                        break;
                                    }
                                }
                            }
                        } else {
                            // ja ta no barco
                            if (p.hasMetadata("Transporte")) {
                                Location localBoat = (Location)MetaShit.getMetaObject("Transporte", p);
                                p.sendMessage(ChatColor.GREEN + L.m("Você chegou no seu destino "));
                                p.teleport(localBoat);
                                p.removeMetadata("Transporte", KoM._instance);
                                
                            } else {
                                p.teleport(CFG.spawnTree.toLocation());
                            }
                        }
                    }
                } catch (Exception e) {
                    KoM.log.info("Problem in Boat: ");
                    KoM.log.info(e.getMessage());
                    e.printStackTrace();

                }
            }
        };
        Bukkit.getScheduler().scheduleSyncRepeatingTask(KoM._instance, r, 20 * Boat.timeInSeconds, 20 * Boat.timeInSeconds);

    }
    
}
