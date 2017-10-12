/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nativelevel.precocabeca;

import java.util.UUID;
import nativelevel.KoM;
import nativelevel.sisteminhas.ClanLand;
import nativelevel.Lang.L;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author USER
 * 
 */

public class CmdRecompensa implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (cmd.getName().equalsIgnoreCase("recompensa")) {
            if (sender instanceof Player) {
                if (args.length == 2) {
                    try {
                        Player arvo = Bukkit.getPlayerExact(args[0]);
                        Player p = (Player) sender;
                        OfflinePlayer off = Bukkit.getOfflinePlayer(args[0]);
                        double valor = Integer.valueOf(args[1]);
                        UUID alvo = null;
                        if (arvo != null) {
                            alvo = arvo.getUniqueId();
                        } else if (off.hasPlayedBefore()) {
                            alvo = off.getUniqueId();
                        }
                        if (alvo != null) {
                            ClanPlayer palvo = ClanLand.manager.getClanPlayer(alvo);
                            if (valor >= 30) {
                                double valort = valor * 0.9;
                                if (ClanLand.econ.has(p.getName(), valor)) {
                                    double taxa = valor - valort;
                                    ClanLand.econ.withdrawPlayer(p.getName(), valor);
                                    p.sendMessage(ChatColor.AQUA + "Você botou " + valort + " na cabeça de " + args[0] + ", foi cobrada uma taxa de 10%(" + taxa + ")!");
                                    Principal.recom.botaRecompensa(alvo, valort);
                                    for (Player pl : Bukkit.getOnlinePlayers()) {
                                        ClanPlayer cp = ClanLand.manager.getClanPlayer(p);
                                        if (palvo.isRival(pl)) {
                                            if (false) {
                                                pl.sendMessage(L.m("§6Nova recompensa no jogador %", palvo.getName()));
                                            }
                                        }
                                    }
                                } else {
                                    p.sendMessage(ChatColor.GOLD + L.m("Você não tem esse valor!"));
                                }
                            } else {
                                p.sendMessage(ChatColor.GOLD +L.m( "O valor minimo de recompensa é 30!"));
                            }

                        } else {
                            p.sendMessage(ChatColor.GOLD +L.m( "Este jogador não existe!"));
                        }
                    } catch (NumberFormatException er) {
                        sender.sendMessage(ChatColor.GOLD +L.m( "Use /recompensa Jogador Quantidade ou /recompensa listar"));
                    }

                } else if (args.length == 1) {
                    //if (sender.isOp()) {
                        if(args[0].equalsIgnoreCase("listar")) {
                            //KnightsOfMania.database.verCabecasMaisCaras((Player)sender);
                            return true;
                        }
                        Player on = Bukkit.getPlayerExact(args[0]);
                        UUID uuid;
                        if (on != null) {
                            uuid = on.getUniqueId();

                        } else {
                            OfflinePlayer off = Bukkit.getOfflinePlayer(args[0]);
                            uuid = off.getUniqueId();
                        }

                        if (uuid != null) {
                            double qt = Principal.recom.getRecompensa(uuid);
                            sender.sendMessage(ChatColor.LIGHT_PURPLE +L.m( "Este jogador tem uma cabeça no valor de " + qt + " esmeraldas !"));
                        } else {
                            sender.sendMessage(ChatColor.LIGHT_PURPLE + L.m("Nenhum registro deste jogador!"));
                        }
                    } else {
                        sender.sendMessage(ChatColor.GOLD +L.m( "Use /recompensa Jogador Quantidade ou /recompensa listar!"));
                    }

            }

        }

        return false;
    }

}
