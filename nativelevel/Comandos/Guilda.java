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
package nativelevel.Comandos;

import nativelevel.CFG;
import nativelevel.sisteminhas.ClanLand;
import nativelevel.KoM;
import nativelevel.Lang.L;
import nativelevel.MetaShit;
import net.sacredlabyrinth.phaed.simpleclans.Clan;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Guilda implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] args) {
        if (cs instanceof Player) {
            Player p = (Player) cs;
            try {
                if (args.length == 0) {
                    p.sendMessage(ChatColor.GREEN + L.m("oOo________________Guildas do KoM _________________oOo"));
                    p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.YELLOW + "/guilda criar <tag> <desc>" + ChatColor.WHITE + " - cria uma guilda nova")); // ok
                    p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.YELLOW + "/guilda convidar <jogador>" + ChatColor.WHITE + " - convida um jogador")); // ok
                    p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.YELLOW + "/guilda info <guilda>" + ChatColor.WHITE + " - ve info da guilda"));   //ok                             
                    p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.YELLOW + "/guilda infojogador <jogador>" + ChatColor.WHITE + " - ve info do jogador"));   //ok
                    p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.YELLOW + "/guilda confiar/desconfiar <jogador>" + ChatColor.WHITE + " - pode mecher em terra publica"));
                    p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.YELLOW + "/guilda <home/sethome>" + ChatColor.WHITE + " - seta ou vai para home"));     // ok         
                    p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.YELLOW + "/guilda ally <add/remove> <tag>" + ChatColor.WHITE + " - faz/desfaz alianças ")); // ok
                    p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.YELLOW + "/guilda rival <add/remove> <tag>" + ChatColor.WHITE + " - faz/desfaz rivalidades"));  // ok
                    p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.YELLOW + "/guilda conquistar/desconquistar" + ChatColor.WHITE + " - conquista territorio p/ construir")); // ok
                    p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.YELLOW + "/guilda sair" + ChatColor.WHITE + " - sai da guilda"));  // ok   
                    p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.YELLOW + "/guilda pilhagem <tag>" + ChatColor.WHITE + " - ve pontos de pilhagem")); // ok  
                    p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.YELLOW + "/guilda poder" + ChatColor.WHITE + " - ve seu poder"));    // ok
                    p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.YELLOW + "/guilda ranking" + ChatColor.WHITE + " - ve ranking geral")); // ok
                    p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.YELLOW + "/guilda dono <nick>" + ChatColor.WHITE + " - da terreno a um membro")); // ok
                    p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.YELLOW + "/guilda torre" + ChatColor.WHITE + " - vai para torre da guilda")); // ok
                    p.sendMessage(ChatColor.GREEN + L.m("| " + ChatColor.YELLOW + "/guilda setrank" + ChatColor.WHITE + " - altera o rank de um membro")); // ok
                    //p.sendMessage(ChatColor.GREEN + L.m("_______________Mais comandos em /gld_______________"));
                } else {
                    Clan clan = ClanLand.manager.getClanByPlayerName(p.getName());
                    if (args.length == 1) {
                        if (args[0].equalsIgnoreCase("info")) {
                            p.chat("/gld profile");
                            return true;
                        } else if (args[0].equalsIgnoreCase("ranking")) {
                            p.chat("/gld ranking");
                            return true;
                        } else if (args[0].equalsIgnoreCase("home")) {
                            p.chat("/gld casa");
                            return true;
                        }  else if (args[0].equalsIgnoreCase("setrank")) {
                            p.chat("/gld setrank");
                            return true;
                        }else if (args[0].equalsIgnoreCase("conquistar")) {
                            p.chat("/terreno conquistar");
                            return true;
                        } else if (args[0].equalsIgnoreCase("desconquistar")) {
                            p.chat("/terreno deixar");
                            return true;
                        } else if (args[0].equalsIgnoreCase("sethome")) {
                            p.chat("/kom sethome");
                            return true;
                        } else if (args[0].equalsIgnoreCase("sair")) {
                            p.chat("/gld deixar");
                            return true;
                        } else if (args[0].equalsIgnoreCase("torre")) {
                            if (clan == null) {
                                p.sendMessage(ChatColor.RED + L.m("Voce precisa de uma guilda..."));
                                return true;
                            }
                            Location localTorre = KoM.database.getTower(clan.getTag());
                            if (localTorre == null) {
                                p.sendMessage(ChatColor.RED + L.m("Sua guilda nao tem torre, peca a um engenheiro para fazer uma !"));
                                return true;
                            }

                            if (p.hasMetadata("tempoDano")) {
                                long tempo = (long) MetaShit.getMetaObject("tempoDano", p);
                                if (tempo + 10 > System.currentTimeMillis() / 1000) {
                                    p.sendMessage(ChatColor.RED + L.m("Aguarde % segundos para usar este comando !", ((tempo + 10) - (System.currentTimeMillis() / 1000))));
                                    return true;
                                }
                            }

                            int secs = 90;
                            if (p.hasMetadata("tpTorre")) {
                                long tempo = (long) MetaShit.getMetaObject("tpTorre", p);
                                if (tempo + secs > System.currentTimeMillis() / 1000) {
                                    p.sendMessage(ChatColor.GREEN + L.m("Aguarde % segundos para ir a sua torre !", ((tempo + secs) - (System.currentTimeMillis() / 1000))));
                                    return true;
                                }
                            }
                            MetaShit.setMetaObject("tpTorre", p, System.currentTimeMillis() / 1000);
                            p.teleport(localTorre);
                            p.sendMessage(ChatColor.GREEN + L.m("Voce foi a sua torre !"));
                            return true;
                        } else if (args[0].equalsIgnoreCase("poder")) {
                            if (clan == null) {
                                p.sendMessage(ChatColor.RED + L.m("Voce nao pertence a uma guilda !"));
                                return true;
                            }
                            int poder = ClanLand.getPoder(clan.getTag());
                            int qtdTerrenos = ClanLand.getQtdTerrenos(clan.getTag());
                            p.sendMessage(ChatColor.GREEN + L.m("Guilda  : %", clan.getTagLabel(false) + " " + clan.getName()));
                            p.sendMessage(ChatColor.GREEN + L.m("Poder   : %", (poder + CFG.landMax)));
                            p.sendMessage(ChatColor.GREEN + L.m("Terrenos: %", qtdTerrenos));
                            return true;
                        } else if (args[0].equalsIgnoreCase("conquistar")) {
                            p.chat("terreno conquistar");
                            return true;
                        }
                    } else if (args.length == 2) {
                        if (args[0].equalsIgnoreCase("info")) {
                            p.chat("/gld perfil " + args[1]);
                            return true;
                        } else if (args[0].equalsIgnoreCase("convidar")) {
                            p.chat("/gld convidar " + args[1]);
                            return true;
                        } else if (args[0].equalsIgnoreCase("confiar")) {
                            p.chat("/gld confiavel " + args[1]);
                            return true;
                        } else if (args[0].equalsIgnoreCase("desconfiar")) {
                            p.chat("/gld inconfiavel " + args[1]);
                            return true;
                        } else if (args[0].equalsIgnoreCase("infojogador")) {
                            p.chat("/gld espiar " + args[1]);
                            return true;
                        } else if (args[0].equalsIgnoreCase("dono")) {
                            p.chat("/terreno dono " + args[1]);
                            return true;
                        } else if (args[0].equalsIgnoreCase("pilhagem")) {
                            String tag = args[1];
                            Clan alvo = ClanLand.manager.getClan(tag);
                            if (clan == null) {
                                p.sendMessage(ChatColor.RED + L.m("Voce nao esta em uma guilda !"));
                                return true;
                            }
                            if (alvo == null) {
                                p.sendMessage(ChatColor.RED + L.m("Guilda nao encontrada !"));
                                return true;
                            }
                            int pontos = ClanLand.getPtosPilagem(clan.getTag(), tag);
                            if (pontos < 0) {
                                pontos = 0;
                            }
                            p.sendMessage(ChatColor.GREEN + L.m("Sua guilda tem % pontos de pilhagem sobre esta guilda ", pontos));
                            return true;
                        }
                    } else if (args.length == 3) {
                        if (args[0].equalsIgnoreCase("criar")) {
                            String tag = args[1];
                            String nome = args[2];
                            p.chat("/gld criar " + tag + " " + nome);
                            return true;
                        } else if (args[0].equalsIgnoreCase("ally")) {
                            String acao = args[1];
                            String tag = args[2];
                            p.chat("/gld pacto " + acao + " " + tag);
                            return true;
                        } else if (args[0].equalsIgnoreCase("rival")) {
                            String acao = args[1];
                            String tag = args[2];
                            p.chat("/gld rival " + acao + " " + tag);
                            return true;
                        }
                    }
                }
                if (args.length > 0) {
                    String cmd = "";
                    for (String arg : args) {
                        cmd += arg + " ";
                    }
                    p.chat("/gld " + cmd);
                }

            } catch (Throwable t) {
                KoM.log.info(t.getMessage());
                t.printStackTrace();
            }
        }
        return false;
    }
}
