package genericos.komzin.libzinha.reboot;

import genericos.komzin.libzinha.utils.Utils;
import java.util.logging.Level;
import java.util.logging.Logger;
import nativelevel.KoM;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ThreadReinicioRapido
        extends Thread {

    public static void broadcastMessage(String s) {
        try {
            for(Player p : Bukkit.getOnlinePlayers())
                p.sendMessage(s);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public void run() {
        broadcastMessage("§7§l[§6§lK§e§lo§6§lM§7§l] §d O inverno está chegando ! Jabu irá congelar o mundo em 2 minutos !");
        try {
            sleep(60000L);
        } catch (InterruptedException ex) {
            Logger.getLogger(ThreadReinicioRapido.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (!RebootUtils.EmReinicio) {
            return;
        }
        broadcastMessage("§7§l[§6§lK§e§lo§6§lM§7§l] §d Voce sente um calafrio. Falta 1 minuto para o congelamento do mundo de Jabu!");
        try {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "save-all");
        } catch (Exception localException) {
        }

        if (!RebootUtils.EmReinicio) {
            return;
        }
        try {
            sleep(30000L);
        } catch (InterruptedException ex) {
            Logger.getLogger(ThreadReinicioRapido.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (!RebootUtils.EmReinicio) {
            return;
        }
        broadcastMessage("§7§l[§6§lK§e§lo§6§lM§7§l] §d Seu corpo treme de frio, Jabu está congelando o mundo em 30 segundos!");
        try {
            sleep(20000L);
        } catch (InterruptedException ex) {
            Logger.getLogger(ThreadReinicioRapido.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (!RebootUtils.EmReinicio) {
            return;
        }
        broadcastMessage("§7§l[§6§lK§e§lo§6§lM§7§l] §d Seu corpo está quase totalmente congelado. Jabu está congelando o mundo em 10 segundos");
        try {
            
            sleep(5000L);
        } catch (InterruptedException ex) {
            Logger.getLogger(ThreadReinicioRapido.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (!RebootUtils.EmReinicio) {
            return;
        }
        broadcastMessage("§7§l[§6§lK§e§lo§6§lM§7§l] §d Seu corpo está quase totalmente congelado. Jabu está congelando o mundo em 5 segundos");

        try {
            sleep(5000L);
        } catch (InterruptedException ex) {
            Logger.getLogger(ThreadReinicioRapido.class.getName()).log(Level.SEVERE, null, ex);
        }

        Bukkit.getScheduler().scheduleSyncRepeatingTask(KoM._instance, new KikaNegada(), (20), (20));
        
        Runnable r = new Runnable() {
            public void run() {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "save-all");
                //Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop");
            }
        };
        Bukkit.getScheduler().scheduleSyncDelayedTask(KoM._instance, r, 1);
    }
}


/* Location:              C:\Users\User\Desktop\REPO\InstaMCLibKom.jar!\instamc\coders\libkom\reboot\ThreadReinicioRapido.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */
