/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.sisteminhas;

import nativelevel.CFG;
import nativelevel.KoM;
import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.util.Vector;

/**
 *
 * @author usuario
 */
public class Reseter {

    public static boolean resetMode = false;
    
    public static void limpaGuildas() {
        resetMode = true;
        if(KoM.debugMode) KoM.log.info("------- Rodando Cmd");
        Thread t = new Thread() {
            @Override
            public void run() {
                if(KoM.debugMode) KoM.log.info("------- Rodando Thread");
                for (Clan c : ClanLand.manager.getClans()) {
                    if(KoM.debugMode) KoM.log.info("------- LENDO GUILDA "+c.getTag());
                    if (c.getInactiveDays() > 14) {
                        ClanLand.limpaGuildaEDesfaz(c.getTag(), true);
                        for (ClanPlayer cp : c.getAllMembers()) {
                            ClanLand.manager.deleteClanPlayer(cp);
                            ClanLand.storage.deleteClanPlayer(cp);
                        }
                        c.disband();
                        ClanLand.storage.deleteClan(c);
                        ClanLand.manager.removeClan(c.getTag());

                    }
                }
            }
        };
        t.start();

    }

    private static class limpaChunk implements Runnable {

        Location posInicial = new Location(Bukkit.getWorld(CFG.mundoGuilda), -2000, 0, -2000);

        public void run() {
            Chunk c = Bukkit.getWorld(CFG.mundoGuilda).getChunkAt(posInicial);
            String tipo = ClanLand.getTypeAt(posInicial);
            if (tipo.equalsIgnoreCase("WILD") || tipo.equalsIgnoreCase("CLAN")) {
                String tagAli = ClanLand.getGuildaAli(c);
                if(tagAli==null) {
                    c.load(true);
                    Bukkit.getWorld(CFG.mundoGuilda).regenerateChunk(c.getX(), c.getZ());
                    KoM.log.info("Regenerei um chunk ! " + c.getX() + " " + c.getZ());
                    c.unload();
                }  
            }
            if (posInicial.getX() > 2000) {
                posInicial.setX(-2000);
                posInicial.setZ(posInicial.getZ() + 16);
            } else {
                posInicial.setX(posInicial.getX() + 16);
            }
        }
    }

    public static void regenMundo() {
        Location posInicial = new Location(Bukkit.getWorld(CFG.mundoGuilda), -2000, 0, -2000);
        limpaChunk limpador = new limpaChunk();
        limpador.posInicial = posInicial;
        Bukkit.getScheduler().scheduleSyncRepeatingTask(KoM._instance, limpador, 1, 1);
    }

    public static void wipeGeral() {

        for (Clan c : ClanLand.manager.getClans()) {
            // if(c.getInactiveDays() > 7) {
            ClanLand.limpaGuildaEDesfaz(c.getTag(), true);
            for (ClanPlayer cp : c.getAllMembers()) {
                ClanLand.manager.deleteClanPlayer(cp);
                ClanLand.storage.deleteClanPlayer(cp);
            }
            ClanLand.storage.deleteClan(c);
            ClanLand.manager.removeClan(c.getTag());
            // }
        }
    }

}
