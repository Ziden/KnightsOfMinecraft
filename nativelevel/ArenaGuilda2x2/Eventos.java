/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.ArenaGuilda2x2;

import nativelevel.rankings.Estatistica;
import nativelevel.rankings.RankDB;
import nativelevel.sisteminhas.ClanLand;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class Eventos implements Listener {
    
    @EventHandler
    public void negoMorre(PlayerDeathEvent ev) {
        if (!ev.getEntity().getWorld().getName().equalsIgnoreCase("Arena")) {
            return;
        }
        if (ev.getEntity().getKiller() != null && Arena2x2.sql.taVivoEmJogo(ev.getEntity())) {
            // +1 kill
            Arena2x2.sql.atualizaStats(ev.getEntity().getKiller().getUniqueId(), 1, 0, 0, 0);
            RankDB.addPontoCache(ev.getEntity().getKiller(), Estatistica.ARENA, 1);
        }
        Arena2x2.sql.jogadorMorre(ev.getEntity());
    }
    
    @EventHandler
    public void negoKita(PlayerQuitEvent ev) {
        if (!ev.getPlayer().getWorld().getName().equalsIgnoreCase("Arena")) {
            ClanPlayer cp = ClanLand.manager.getClanPlayer(ev.getPlayer());
            if (cp == null || cp.getTag() == null) {
                return;
            }
            Arena2x2.sql.saiDaFila(ev.getPlayer().getUniqueId());
            return;
        }
        if (Arena2x2.sql.taVivoEmJogo(ev.getPlayer())) {
            Arena2x2.sql.jogadorMorre(ev.getPlayer());
        } else {
            ClanPlayer cp = ClanLand.manager.getClanPlayer(ev.getPlayer());
            if (cp != null && cp.getClan() != null) {
                if (Arena2x2.sql.jaTaNoMatcher(cp.getClan().getTag())) {
                    Arena2x2.sql.saiDaFila(ev.getPlayer().getUniqueId());
                }
            }
        }
    }
    
    @EventHandler
    public void negoFoge(PlayerTeleportEvent ev) {
        if (!ev.getPlayer().getWorld().getName().equalsIgnoreCase("Arena")) {
            return;
        }
        if (Arena2x2.sql.taVivoEmJogo(ev.getPlayer())) {
            ev.setCancelled(true);
        }
    }
    
}
