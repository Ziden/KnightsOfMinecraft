/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.ArenaGuilda2x2;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import nativelevel.sisteminhas.ClanLand;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

/**
 * @author usuario
 */
public class MatchMaking extends Thread {

    /////////// CONTEUDO DA THREAD//////////////////
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000 * 10); // cada 10 sec
                // verificando se existem guildas aguardando
                List<String> guildasRanked = Arena2x2.sql.obtemGuildasEsperando(true);
                List<String> guildasTreino = Arena2x2.sql.obtemGuildasEsperando(false);
                if (Arena2x2.debugMode) {
                    Arena2x2.log.info("Guildas ranked :" + guildasRanked.toString());
                    Arena2x2.log.info("Guildas treino :" + guildasTreino.toString());
                }
                // se tem guilda perando o ranked
                if (guildasRanked.size() == 2) {
                    // verificando se tem arena livre
                    int idArenaLivre = Arena2x2.sql.getArenaLivre();
                    if (idArenaLivre != -1) {
                        Arena2x2.sql.comecaJogo(idArenaLivre, guildasRanked, true);
                    } else {
                        if (Arena2x2.debugMode) {
                            Arena2x2.log.info("Nao encontrei arena pra jogar ranked !");
                        }
                    }
                } else {
                    if (Arena2x2.debugMode) {
                        Arena2x2.log.info("Nao tem 2 guildas ranked pra jogar !");
                    }
                }
                // se tem guilda esperando o modo treino
                if (guildasTreino.size() == 2) {
                    // verificando se tem arena livre
                    int idArenaLivre = Arena2x2.sql.getArenaLivre();
                    if (idArenaLivre != -1) {
                        Arena2x2.sql.comecaJogo(idArenaLivre, guildasTreino, false);
                    } else {
                        if (Arena2x2.debugMode) {
                            Arena2x2.log.info("Nao encontrei arena pra jogar treino !");
                        }
                    }
                } else {
                    if (Arena2x2.debugMode) {
                        Arena2x2.log.info("Nao tem 2 guildas treino pra jogar !");
                    }
                }
            } catch (Exception ex) {
                Arena2x2.log.info(ex.getMessage());
                ex.printStackTrace();
            }
        }
    }
    ////////// FIM CONTEUDO THREAD, AKI SÒ STATIC //////////

    public static int tamanhoGrupo = 2;

    public static void botaMatchMaking(Player p, boolean ranked) {
        ClanPlayer cp = ClanLand.manager.getClanPlayer(p);
        if (cp == null || cp.getClan() == null) {
            p.sendMessage(ChatColor.RED + "Voce precisa de uma guilda !");
            return;
        }

       // if (!cp.getClan().getFundador().equals(p.getUniqueId())) {
        //     p.sendMessage("Apenas líderes podem chamar jogadores para arena 2x2 !");
        //    return;
        // }
        if (Arena2x2.sql.jaTaNoMatcher(cp.getClan().getTag())) {
            p.sendMessage("Sua guilda já está aguardando na arena !");
            return;
        }

        if (Arena2x2.sql.taEmJogo(p) || Arena2x2.sql.taEmJogo(cp.getClan().getTag())) {
            p.sendMessage(ChatColor.RED + "Voce ou sua guilda ja está em jogo !");
            return;
        }

        List<Player> jogadores = new ArrayList<Player>();
        jogadores.add(p);

        for (Entity e : p.getNearbyEntities(5, 3, 5)) {
            if (e.getType() == EntityType.PLAYER) {
                ClanPlayer cpa = ClanLand.manager.getClanPlayer(((Player) e).getUniqueId());
                if (cpa != null && cpa.getClan() != null && (cpa.getClan().getTag().equalsIgnoreCase(cp.getClan().getTag()))) {
                    jogadores.add(cpa.toPlayer());
                }
                if (jogadores.size() >= tamanhoGrupo) {
                    break;
                }
            }
        }

        if (jogadores.size() < tamanhoGrupo) {
            p.sendMessage(ChatColor.RED + "Para entrar na arena " + tamanhoGrupo + "x" + tamanhoGrupo + " fique perto de " + (tamanhoGrupo - 1) + " membros de sua guilda !");
            return;
        }

        Arena2x2.sql.botaMatchMaking(cp, jogadores, ranked);
    }
}
