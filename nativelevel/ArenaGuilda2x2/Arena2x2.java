/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.ArenaGuilda2x2;

import java.util.logging.Logger;
import nativelevel.KoM;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

/**
 *
 * @author vntgasl
 *
 */
public class Arena2x2 {

    public static String nomeMundoArena = "Arena";
    public static int tamanhoArena = 20;

    public static boolean debugMode = false;
    public static Logger log = Logger.getLogger("Minecraft");
    public static ArenaDB sql;
    public static Plugin instancia = null;
    public static MatchMaking match = new MatchMaking();

    public void onEnable() {
        instancia = KoM._instance;
        sql = new ArenaDB();
        Bukkit.getServer().getPluginManager().registerEvents(new Eventos(), instancia);

        if (!KoM.serverTestes) {
            
            sql.inicializa();
            match.start();
            sql.limpaTudo();

        }

        log.info("Arena 2x2 LOADED");
    }

    public static void teleportSincrono(final Player p, final Location l) {
        Runnable r = new Runnable() {
            public void run() {
                p.teleport(l, PlayerTeleportEvent.TeleportCause.PLUGIN);
            }
        };
        Bukkit.getScheduler().scheduleSyncDelayedTask(instancia, r, 1);
    }

    public void onDisable() {
        match.interrupt();
    }

}
