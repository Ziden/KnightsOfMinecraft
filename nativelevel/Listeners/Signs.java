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
package nativelevel.Listeners;

import nativelevel.sisteminhas.Boat;
import nativelevel.Custom.Buildings.AutoDispenser;
import nativelevel.Custom.Buildings.Construcao;
import nativelevel.KoM;
import nativelevel.Lang.L;
import nativelevel.Menu.netMenu;
import nativelevel.MetaShit;
import org.bukkit.util.Vector;
import nativelevel.Attributes.Mana;
import nativelevel.Attributes.Stamina;
import nativelevel.integration.BungeeCordKom;
import nativelevel.integration.SimpleClanKom;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public class Signs {

    public static void signClick(PlayerInteractEvent ev, final Sign s) {
        if(ChatColor.stripColor(s.getLine(1)).equalsIgnoreCase("[Fonte]")) {
            final Player p = ev.getPlayer();
            if(p.hasMetadata("bebendo")) {
                p.sendMessage(ChatColor.RED+"Aguarde...");
                return;
            }
            MetaShit.setMetaObject("bebendo", p, "1");
            p.sendMessage(ChatColor.GREEN+"Voce comeca a beber agua da fonte");
            Runnable r = new Runnable() {
                public void run() {
                    if(p==null)
                        return;
                     p.removeMetadata("bebendo", KoM._instance);
                     if(p.getLocation().distance(s.getLocation())>5) {
                         p.sendMessage(ChatColor.RED+"Voce ficou muito longe da fonte para beber !");
                     } else {
                         Mana.enxe(p);
                         Stamina.enxe(p);
                         p.sendMessage(ChatColor.GREEN+"Voce tomou agua da fonte !");
                         
                     }
                     
                }
            };
            Bukkit.getScheduler().scheduleSyncDelayedTask(KoM._instance, r, 20*5);
           
            return;
        }
        else if (s.getLine(1).equalsIgnoreCase(L.m("[Torre]"))) {
            int vida = Integer.valueOf(s.getLine(3).split(":")[1]);
            String tag = s.getLine(2);
            vida = vida - 1;
            Location inicio = s.getLocation();
            inicio.setX(inicio.getX()-2);
            inicio.setZ(inicio.getZ()-2);
            Construcao.destroi(inicio, new Vector(5, 15, 5));
            if (vida <= 0) {
                for (int x = s.getX() - 2; x < s.getX() + 3; x++) {
                    for (int y = s.getY() - 1; y < s.getY() + 15; y++) {
                        for (int z = s.getZ() - 2; z < s.getZ() + 3; z++) {
                            Bukkit.getWorld(s.getWorld().getName()).getBlockAt(x, y, z).setType(Material.AIR);
                        }
                    }
                }
                KoM.database.remTower(tag);
                for(Entity e: ev.getPlayer().getNearbyEntities(100, 20, 100)) {
                    if(e.getType()==EntityType.PLAYER) {
                        ((Player)e).sendMessage(ChatColor.RED+L.m("Voce ouve uma torre desmoronando"));
                    }
                }
            } else {
                s.setLine(3, L.m("Vida:") + vida);
                s.update();
            }
        } else if (s.getLine(1).equalsIgnoreCase("[Turret]")) {
            /*
            int vida = Integer.valueOf(s.getLine(2).split(":")[1]);
            int id = Integer.valueOf(s.getLine(3).split(":")[1]);
            vida = vida - 1;
            int y = s.getY();
            int z = s.getZ();
            if (vida <= 0) {
                AutoDispenser.removeDispenser(id);
            } else {
                s.setLine(2, L.m("Vida:") + vida);
                s.update();
            }
            */
        } else if (s.getLine(1).equalsIgnoreCase("[MinhasClasses]")) {
            ev.getPlayer().sendMessage(ChatColor.GREEN + "Vendo suas classes !");
            //IconMenu.mostraClasses(ev.getPlayer());
            netMenu.mostraClasses(ev.getPlayer());
        } else if (s.getLine(1).equalsIgnoreCase("[Achar Guilda]")) {
            SimpleClanKom.setupFactionForBeginner(ev.getPlayer());
        } else if (s.getLine(1).equalsIgnoreCase(L.m("[Tempo]"))) {
            Boat.printCooldown(ev.getPlayer());
        }
    }
}
