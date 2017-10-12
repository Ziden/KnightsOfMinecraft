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

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
//import mania.net32.kombungeehomes.BungeeHomesAPI;
//import mania.net32.kombungeehomes.LocBungeeCord;
import nativelevel.KoM;
import nativelevel.utils.BungLocation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.messaging.PluginMessageRecipient;

/**
 *
 * @author usuario
 */
public class BungeeCordKom {

    public static void tp(Player p, String mundo, double x, double y, double z, float pitch, float yaw) {
        World w = Bukkit.getWorld(mundo);
        // tem esse mundo aki então é pro mesmo servidor
        if(w!=null) {
            Location l = new Location(w, x,y,z);
            l.setPitch(pitch);
            l.setYaw(yaw);
             if(x==0 & y==0 && z==0 && pitch == 0 && yaw==0) {
                 l=w.getSpawnLocation();
             }
            //p.teleport(l);
            p.teleport(l, PlayerTeleportEvent.TeleportCause.PLUGIN);
        } else {
            // faz tp do bungee entre servidores sei la como eu
           
            if(x==0 & y==0 && z==0 && pitch == 0 && yaw==0) {
                // teleporta pro spawn do mundo
                // BungeeHomesAPI.sendPlayerToWorld(p, new LocBungeeCord(mundo, mundo, x, y, z, yaw, pitch));
            } else {
                // bungeia o kra pras coords passadas
                // BungeeHomesAPI.sendPlayerToLocBungeeCord(p, new LocBungeeCord(mundo, mundo, x, y, z, yaw, pitch));
            }
                   
            //p.sendMessage("Chame um staff deu bug !");
        }
    }
    
     public static void tp(Player p, BungLocation l) {
        tp(p,l.mundo,l.x,l.y,l.z,l.pitch,l.yaw);
    }
    
    
        public static void TeleportarTPBG(String server, Player sender) {
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(b);
            try {
                out.writeUTF("Connect");
                out.writeUTF(server);
            } catch (IOException localIOException) {
            }
            ((PluginMessageRecipient) sender).sendPluginMessage(KoM._instance, "BungeeCord", b.toByteArray());
    }
    
}
