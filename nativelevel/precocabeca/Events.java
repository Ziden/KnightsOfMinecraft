/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.precocabeca;

import nativelevel.sisteminhas.ClanLand;
import nativelevel.Lang.L;
import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

/**
 *
 * @author USER
 */
public class Events implements Listener {

    @EventHandler
    public void Morre(EntityDeathEvent e) {
        if (e.getEntity() instanceof Player && e.getEntity().getKiller() instanceof Player) {
            Player morreu = (Player) e.getEntity();
            Player matou = (Player) e.getEntity().getKiller();
            if (Principal.recom.isProcurado(morreu.getUniqueId())) {
                if (Principal.recom.getRecompensa(morreu.getUniqueId()) > 0) {
                    ClanPlayer cmorreu = ClanLand.manager.getClanPlayer(morreu);
                    ClanPlayer cmatou = ClanLand.manager.getClanPlayer(matou);
                    if (cmatou.isRival(morreu)) {

                        double recompensa = Principal.recom.getRecompensa(morreu.getUniqueId());
                        double re = Principal.recom.getRecompensa(morreu.getUniqueId());
                        ClanLand.econ.depositPlayer(matou.getName(), recompensa);
                        Principal.recom.removeRecompensa(morreu.getUniqueId());
                       // matou.sendMessage(ChatColor.AQUA + L.m("Você matou alguem que tinha uma recompensa na cabeça e acabou ganhando " + re + " esmeraldas !");
                        morreu.sendMessage(ChatColor.RED + L.m("A recompensa pela sua cabeça foi zerada!"));
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            if (p != morreu && p != matou) {
                              // p.sendMessage(ChatColor.RED + "" + matou.getName() + L.m(" acabou de matar " + morreu.getName() + " levando uma recompensa de " + re + " esmeraldas por sua cabeça !");
                            }
                        }

                    }
                }
            }
        }
    }
}
