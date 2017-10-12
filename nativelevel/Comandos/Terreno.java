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

import java.util.List;
import java.util.UUID;
import nativelevel.CFG;
import nativelevel.KoM;
import nativelevel.sisteminhas.ClanLand;
import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import java.util.HashMap;
import nativelevel.Lang.L;
import nativelevel.rankings.Estatistica;
import nativelevel.rankings.RankDB;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Terreno implements CommandExecutor {

    public static HashMap<Location, String> rewindChunks = new HashMap<Location, String>();

    private static void sendHelp(CommandSender cs, boolean leader) {
        ClanLand.msg(cs, L.m("Use: /terreno amigo add (nome)"));
        ClanLand.msg(cs, L.m("Use: /terreno amigo remover (nome)"));
        ClanLand.msg(cs, L.m("Use: /terreno amigo limpar"));
        ClanLand.msg(cs, L.m("Use: /terreno info"));
        if (leader) {
            ClanLand.msg(cs, ChatColor.YELLOW + L.m("|____________Lider____________|"));
            ClanLand.msg(cs, ChatColor.YELLOW + L.m("Use: /terreno conquistar"));
            ClanLand.msg(cs, ChatColor.YELLOW + L.m("Use: /terreno deixar"));
            ClanLand.msg(cs, ChatColor.YELLOW + L.m("Use: /terreno publico"));
            ClanLand.msg(cs, ChatColor.YELLOW + L.m("Use: /terreno dono (nome)"));
        }
    }

    public static boolean temGuildaPerto(Player p, ClanPlayer c, Location l) {
        String minhaTag = "asdasdasdasd";
        if(c!=null)
            minhaTag = c.getTag();
        int x = p.getLocation().getChunk().getBlock(1, 0, 1).getLocation().getBlockX();
        int y = p.getLocation().getChunk().getBlock(1, 0, 1).getLocation().getBlockZ();
        Location up = new Location(p.getWorld(), x + 16, 0, y);
        Clan cUp = ClanLand.getClanAt(up);
        // nao tem clan em cima ou não é meu clan
        if (cUp == null || !cUp.getTag().equalsIgnoreCase(minhaTag)) {
            if (cUp != null && !cUp.getTag().equalsIgnoreCase(minhaTag)) {
                return true;
            }
            Location down = new Location(p.getWorld(), x - 16, 0, y);
            Clan cDown = ClanLand.getClanAt(down);
            if (cDown == null || !cDown.getTag().equalsIgnoreCase(minhaTag)) {
                if (cDown != null && !cDown.getTag().equalsIgnoreCase(minhaTag)) {
                    return true;
                }
                Location left = new Location(p.getWorld(), x, 0, y + 16);
                Clan cLeft = ClanLand.getClanAt(left);
                if (cLeft == null || !cLeft.getTag().equalsIgnoreCase(minhaTag)) {
                    if (cLeft != null && !cLeft.getTag().equalsIgnoreCase(minhaTag)) {
                        return true;
                    }
                    Location right = new Location(p.getWorld(), x, 0, y - 16);
                    Clan cRight = ClanLand.getClanAt(right);
                    if (cRight == null || !cRight.getTag().equalsIgnoreCase(minhaTag)) {
                        if (cRight != null && !cRight.getTag().equalsIgnoreCase(minhaTag)) {
                            return true;
                        }
                        right = new Location(p.getWorld(), x - 16, 0, y - 16);
                        cRight = ClanLand.getClanAt(right);
                        if (cRight == null || !cRight.getTag().equalsIgnoreCase(minhaTag)) {
                            if (cRight != null && !cRight.getTag().equalsIgnoreCase(minhaTag)) {
                                return true;
                            }
                            right = new Location(p.getWorld(), x + 16, 0, y - 16);
                            cRight = ClanLand.getClanAt(right);
                            if (cRight == null || !cRight.getTag().equalsIgnoreCase(minhaTag)) {
                                if (cRight != null && !cRight.getTag().equalsIgnoreCase(minhaTag)) {
                                    return true;
                                }
                                right = new Location(p.getWorld(), x - 16, 0, y + 16);
                                cRight = ClanLand.getClanAt(right);
                                if (cRight == null || !cRight.getTag().equalsIgnoreCase(minhaTag)) {
                                    if (cRight != null && !cRight.getTag().equalsIgnoreCase(minhaTag)) {
                                        return true;
                                    }
                                    right = new Location(p.getWorld(), x + 16, 0, y + 16);
                                    cRight = ClanLand.getClanAt(right);
                                    if (cRight == null || !cRight.getTag().equalsIgnoreCase(minhaTag)) {
                                        if (cRight != null && !cRight.getTag().equalsIgnoreCase(minhaTag)) {
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
        if (cs instanceof ConsoleCommandSender) {
            ClanLand.msg(cs, L.m("Admin, favor entrar no jogo."));
            return true;
        }
        boolean leader = ClanLand.manager.getClanPlayer((Player) cs) == null ? false : ClanLand.manager.getClanPlayer((Player) cs).isLeader();
        if (args.length == 0 || args.length > 3) {
            sendHelp(cs, leader);
            return true;
        }
        Player p = (Player) cs;
        if (!p.isOp() && p.getWorld().getName().equalsIgnoreCase("woe") || p.getWorld().getName().equalsIgnoreCase("vila") || p.getWorld().getName().equalsIgnoreCase("dungeon")) {
            p.sendMessage(ChatColor.RED + L.m("Este comando nao funciona aqui !"));
            return true;
        }
        Clan c = ClanLand.getClanAt(p.getLocation());
        ClanPlayer cp = ClanLand.manager.getClanPlayer(p);
        if (ClanLand.isSafeZone(p.getLocation())) {
            if (!p.isOp()) {
                ClanLand.msg(p, L.m("Voce nao tem poder aqui, Voce esta numa Cidade."));
                return true;
            }
        }
        if (ClanLand.isWarZone(p.getLocation())) {
            if (!p.isOp()) {
                ClanLand.msg(p, L.m("Voce nao tem poder aqui, Voce esta numa WarZone."));
                return true;
            }
        }
        if (args.length == 3) {
            if (!args[0].equals("mem") && !args[0].equals("amigo")) {
                sendHelp(cs, leader);
                return true;
            }
            if (c == null || cp == null) {
                ClanLand.msg(p, L.m("Voce nem tem um clan."));
                return true;
            }
            if (!c.getTag().equals(cp.getClan().getTag())) {
                ClanLand.msg(p, L.m("Este terreno nao eh do seu clan."));
                return true;
            }
            if (ClanLand.getOwnerAt(p.getLocation()).equals("none")) {
                ClanLand.msg(p, L.m("Esse terreno eh publico do clan."));
                return true;
            }
            if (!ClanLand.getOwnerAt(p.getLocation()).equals(p.getUniqueId().toString()) && !leader) {
                ClanLand.msg(p, L.m("Voce nao eh dono desse terreno."));
                return true;
            }
            String player = args[2];
            Player p2 = Bukkit.getPlayer(player);
            if (p2 != null) {
                String uuid = p2.getUniqueId().toString();
                if (!c.getAllMembers().contains(ClanLand.manager.getClanPlayer(player))) {
                    ClanLand.msg(p, L.m("Este jogador nao eh do seu clan."));
                    return true;
                }
                if (args[1].equals("add")) {
                    if (ClanLand.isMemberAt(p.getLocation(), uuid)) {
                        ClanLand.msg(p, L.m("Este jogador ja eh membro deste terreno."));
                        return true;
                    }
                    ClanLand.addMemberAt(p.getLocation(), uuid);
                    ClanLand.msg(p, L.m("O jogador %"  + ChatColor.GREEN + " agora eh membro deste terreno.",player));
                    return true;
                } else if (args[1].equals("rem") || args[1].equals("remover")) {
                    if (!ClanLand.isMemberAt(p.getLocation(), uuid)) {
                        ClanLand.msg(p, L.m("Este jogador nao eh membro deste terreno."));
                        return true;
                    }
                    ClanLand.removeMemberAt(p.getLocation(), uuid);
                    ClanLand.msg(p, L.m("O jogador %" + ChatColor.GREEN + " agora nao eh mais membro deste terreno.",player));
                    return true;
                } else {
                    sendHelp(cs, leader);
                    return true;
                }
            } else {
                ClanLand.msg(p, L.m("O jogador %"+ ChatColor.GREEN + " está offline.",player));
                return true;
            }
        } else if (args.length == 2) {
            if (args[0].equals("membros") || args[0].equals("mem")) {
                if (ClanLand.getOwnerAt(p.getLocation()).equals("none")) {
                    ClanLand.msg(p, L.m("Esse terreno eh publico do clan."));
                    return true;
                }
                if (args[1].equals("limpar")) {
                    if (cp == null) {
                        ClanLand.msg(p, L.m("Voce nem tem um clan."));
                        return true;
                    }
                    if (!c.getTag().equals(cp.getClan().getTag())) {
                        ClanLand.msg(p, L.m("Este terreno nao eh do seu clan."));
                        return true;
                    }
                    if (!ClanLand.getOwnerAt(p.getLocation()).equals(p.getUniqueId().toString()) && !leader) {
                        ClanLand.msg(p, L.m("Voce nao eh dono desse terreno."));
                        return true;
                    }
                    ClanLand.clearMembersAt(p.getLocation());
                    ClanLand.msg(p, L.m("Seu terreno nao tem mais membros."));
                    return true;
                } else {
                    sendHelp(cs, leader);
                    return true;
                }
            } else if (args[0].equals("dono")) {
                if (cp == null) {
                    ClanLand.msg(p, L.m("Voce nem tem um clan."));
                    return true;
                } else if (!leader) {
                    ClanLand.msg(p, L.m("Voce nao eh lider do seu clan."));
                    return true;
                }
                if (c == null || !c.getTag().equals(cp.getClan().getTag())) {
                    ClanLand.msg(p, L.m("Este terreno nem eh do seu clan."));
                    return true;
                }
                Player alvo = Bukkit.getPlayer((args[1]));
                if (c != null && c.getAllAllyMembers() != null && !c.getAllMembers().contains(ClanLand.manager.getClanPlayer(alvo))) {
                    ClanLand.msg(p, L.m("Este jogador nao esta no seu clan."));
                    return true;
                }
                String owner = ClanLand.getOwnerAt(p.getLocation());
                if (owner != null && owner.equals(alvo.getUniqueId().toString())) {
                    ClanLand.msg(p, L.m("Esse terreno ja eh do jogador %",args[1]));
                    return true;
                }
                ClanLand.setOwnerAt(p.getLocation(), alvo.getUniqueId().toString());
                ClanLand.msg(p, L.m("Esse terreno agora eh do jogador %"+ ChatColor.GREEN + ", do seu clan.",args[1]));
                ClanLand.update(p, p.getLocation());
                return true;
            } else {
                sendHelp(cs, leader);
                return true;
            }
        } else if (args[0].equals("publico") || args[0].equals("pub")) {
            if (cp == null) {
                ClanLand.msg(p, L.m("Voce nem tem um clan."));
                return true;
            } else if (!leader) {
                ClanLand.msg(p, L.m("Voce nao eh lider do seu clan."));
                return true;
            }
            if (c == null || !c.getTag().equals(cp.getClan().getTag())) {
                ClanLand.msg(p, L.m("Este terreno nem eh do seu clan."));
                return true;
            }
            if (ClanLand.getOwnerAt(p.getLocation()).equals("none")) {
                ClanLand.msg(p, L.m("Este terreno ja eh publico."));
                return true;
            }
            ClanLand.msg(p, L.m("Este terreno agora eh publico."));
            ClanLand.setOwnerAt(p.getLocation(), null);
            ClanLand.update(p, p.getLocation());
            return true;
        } else if (args[0].equals("deixar")) {
            if (cp == null) {
                ClanLand.msg(p, L.m("Voce nem tem um clan."));
                return true;
            } else if (!leader) {
                ClanLand.msg(p, L.m("Voce nao eh lider do seu clan."));
                return true;
            }
            if (c == null) {
                ClanLand.msg(p, L.m("Este terreno nem tem dono."));
                return true;
            } else if (!c.getTag().equals(cp.getTag())) {
                ClanLand.msg(p, L.m("Voce nao pode deixar terreno do clan %" + ChatColor.GREEN + ".",c.getColorTag()));
                return true;
            }
            ClanLand.removeClanAt(p.getLocation());
            ClanLand.msg(p, L.m("Este terreno nao eh mais do seu clan."));
            ClanLand.update(p, p.getLocation());
            return true;
        } else if (args[0].equals("conquistar") || args[0].equals("conq")) {
            if (p.getWorld().getName().equalsIgnoreCase("vila")) {
                p.sendMessage(ChatColor.RED + L.m("Nao se pode conquistar Rhodes... pelo menos não sem um bom exercito !"));
                return true;
            }
            if (ClanLand.isSafeZone(p.getLocation()) || ClanLand.isWarZone(p.getLocation())) {
                p.sendMessage(ChatColor.RED + L.m("Voce nao pode conquistar aqui !"));
                return true;
            }
            if (cp == null || cp.getClan() == null) {
                ClanLand.msg(p, L.m("Voce nao tem um clan."));
                return true;
            } else if (!leader) {
                ClanLand.msg(p, L.m("Voce nao eh lider do seu clan."));
                return true;
            }
            ////// SE JA TEM UM CLAN AQUI
            if (c != null) {
                if (c.getTag().equals(cp.getTag())) {
                    ClanLand.msg(p, L.m("Seu clan ja eh dono desse terreno."));
                    return true;
                }
                if (KoM.debugMode) {
                    KoM.log.info("TEM UM CLA INIMIGO AKI");
                }
                ClanLand.msg(p, L.m("O clan %" + ChatColor.GREEN + " ja eh dono desse terreno.",c.getColorTag()));
                if (c.isRival(cp.getTag())) {
                    int ptosPilhagem = ClanLand.getPtosPilagem(cp.getTag(), c.getTag());
                    if (ptosPilhagem < CFG.custoAbaixarPower) {
                        ClanLand.msg(p, L.m("Voce precisa de % pontos de pilhagem para dominar esta area",CFG.custoAbaixarPower));
                        return true;
                    }
                    ptosPilhagem -= CFG.custoAbaixarPower;
                    String tagInimiga = c.getTag();
                    int x = p.getLocation().getChunk().getBlock(1, 0, 1).getLocation().getBlockX();
                    int y = p.getLocation().getChunk().getBlock(1, 0, 1).getLocation().getBlockZ();
                    Location up = new Location(p.getWorld(), x + 16, 0, y);
                    Clan cUp = ClanLand.getClanAt(up);
                    // se naõ tem clan em cima
                    if (cUp != null && cUp.getTag().equalsIgnoreCase(tagInimiga)) {
                        Location down = new Location(p.getWorld(), x - 16, 0, y);
                        Clan cDown = ClanLand.getClanAt(down);
                        // se nao tem clan em baixo
                        if (cDown != null && cDown.getTag().equalsIgnoreCase(tagInimiga)) {
                            Location left = new Location(p.getWorld(), x, 0, y + 16);
                            Clan cLeft = ClanLand.getClanAt(left);
                            if (cLeft != null && cLeft.getTag().equalsIgnoreCase(tagInimiga)) {
                                Location right = new Location(p.getWorld(), x, 0, y - 16);
                                Clan cRight = ClanLand.getClanAt(right);
                                if (cRight != null && cRight.getTag().equalsIgnoreCase(tagInimiga)) {
                                    ClanLand.msg(p, L.m("Voce tem que dominar inimigos pelas bordas !"));
                                    return true;
                                }
                            }
                        }
                    }
                    if (!ClanLand.econ.has(p.getName(), 50)) {
                        p.sendMessage(ChatColor.RED + L.m("Voce precisa de 50 Esmeraldas para dominar o terreno !"));
                        return true;
                    }
                    
                    ClanLand.removeClanAt(p.getLocation());
                    ClanLand.setClanAt(p.getLocation(), cp.getTag());
                    ClanLand.msg(p, ChatColor.GREEN + L.m("Voce dominou o terreno da guilda por 1 hora !"));
                    ClanLand.setPtosPilhagem(cp.getTag(), c.getTag(), ptosPilhagem);
                    RankDB.addPontoCache(p, Estatistica.DOMINADOR, 1);
                    for (ClanPlayer p2 : c.getOnlineMembers()) {
                        p2.toPlayer().sendMessage(ChatColor.RED + L.m("Um territorio de sua guilda foi dominado pela guilda % por uma hora !!",cp.getTag()));
                    }
                    ClanLand.update(p, p.getLocation());
                    final Location loc = p.getLocation();
                    final Clan c2 = c;

                    Runnable r;
                    r = new Runnable() {
                        public void run() {
                            ClanLand.removeClanAt(loc);
                            ClanLand.setClanAt(loc, c2.getTag());
                            for (ClanPlayer p : c2.getOnlineMembers()) {
                                if (p != null && p.toPlayer() != null) {
                                    p.toPlayer().sendMessage(ChatColor.GREEN + L.m("A terra conquistada voltou a sua guilda !"));
                                }
                            }
                        }
                    };
                    rewindChunks.put(p.getLocation(), cp.getTag());
                    Bukkit.getScheduler().scheduleSyncDelayedTask(KoM._instance, r, 20 * 60 * 60);
                }
                return true;
            }
            c = cp.getClan();
            int qtdTerrenos = ClanLand.getQtdTerrenos(c.getTag());
            int poder = ClanLand.getPoder(c.getTag());
            if (KoM.debugMode) {
                KoM.log.info("pegando terrenos e poder de |" + c.getTag() + "| de tamanho de tag =" + c.getTag().length());
                KoM.log.info("INT qtdTerrenos: " + qtdTerrenos);
                KoM.log.info("INT poder: " + poder);
            }
            int preco = qtdTerrenos * CFG.landPrice;
            if (!ClanLand.econ.has(p.getName(), preco)) {
                ClanLand.msg(p, L.m("Voce precisa de % esmeraldas para dominar uma nova terra !",preco));
                return true;
            }
            if (KoM.debugMode) {
                KoM.log.info("TEM GRANA PRA COMPRAR");
            }
            int numeroMembrosGuilda = c.getSize();
            if (qtdTerrenos >= CFG.landMax + poder) {
                ClanLand.msg(p, L.m("Sua guilda pode apenas ter % terrenos !",((int) CFG.landMax + (int) poder)));
                ClanLand.msg(p, L.m("Para ter mais terrenos, consiga mais poder recrutando membros novos ou com a pedra do poder !"));
                return true;
            }
            // if (qtdTerrenos > 0) {
            String minhaTag = c.getTag();
            int x = p.getLocation().getChunk().getBlock(1, 0, 1).getLocation().getBlockX();
            int y = p.getLocation().getChunk().getBlock(1, 0, 1).getLocation().getBlockZ();
            Location up = new Location(p.getWorld(), x + 16, 0, y);
            Clan cUp = ClanLand.getClanAt(up);
            // nao tem clan em cima ou não é meu clan
            if (cUp == null || !cUp.getTag().equalsIgnoreCase(minhaTag)) {
                if (cUp != null && !cUp.getTag().equalsIgnoreCase(minhaTag)) {
                    p.sendMessage(ChatColor.RED + L.m("Voce nao pode conquistar colado em outras guildas !"));
                    return true;
                }
                Location down = new Location(p.getWorld(), x - 16, 0, y);
                Clan cDown = ClanLand.getClanAt(down);
                if (cDown == null || !cDown.getTag().equalsIgnoreCase(minhaTag)) {
                    if (cDown != null && !cDown.getTag().equalsIgnoreCase(minhaTag)) {
                        p.sendMessage(ChatColor.RED + L.m("Voce nao pode conquistar colado em outras guildas !"));
                        return true;
                    }
                    Location left = new Location(p.getWorld(), x, 0, y + 16);
                    Clan cLeft = ClanLand.getClanAt(left);
                    if (cLeft == null || !cLeft.getTag().equalsIgnoreCase(minhaTag)) {
                        if (cLeft != null && !cLeft.getTag().equalsIgnoreCase(minhaTag)) {
                            p.sendMessage(ChatColor.RED + L.m("Voce nao pode conquistar colado em outras guildas !"));
                            return true;
                        }
                        Location right = new Location(p.getWorld(), x, 0, y - 16);
                        Clan cRight = ClanLand.getClanAt(right);
                        if (cRight == null || !cRight.getTag().equalsIgnoreCase(minhaTag)) {
                            if (cRight != null && !cRight.getTag().equalsIgnoreCase(minhaTag)) {
                                p.sendMessage(ChatColor.RED + L.m("Voce nao pode conquistar colado em outras guildas !"));
                                return true;
                            }
                            right = new Location(p.getWorld(), x - 16, 0, y - 16);
                            cRight = ClanLand.getClanAt(right);
                            if (cRight == null || !cRight.getTag().equalsIgnoreCase(minhaTag)) {
                                if (cRight != null && !cRight.getTag().equalsIgnoreCase(minhaTag)) {
                                    p.sendMessage(ChatColor.RED + L.m("Voce nao pode conquistar colado em outras guildas !"));
                                    return true;
                                }
                                right = new Location(p.getWorld(), x + 16, 0, y - 16);
                                cRight = ClanLand.getClanAt(right);
                                if (cRight == null || !cRight.getTag().equalsIgnoreCase(minhaTag)) {
                                    if (cRight != null && !cRight.getTag().equalsIgnoreCase(minhaTag)) {
                                        p.sendMessage(ChatColor.RED + L.m("Voce nao pode conquistar colado em outras guildas !"));
                                        return true;
                                    }
                                    right = new Location(p.getWorld(), x - 16, 0, y + 16);
                                    cRight = ClanLand.getClanAt(right);
                                    if (cRight == null || !cRight.getTag().equalsIgnoreCase(minhaTag)) {
                                        if (cRight != null && !cRight.getTag().equalsIgnoreCase(minhaTag)) {
                                            p.sendMessage(ChatColor.RED + L.m("Voce nao pode conquistar colado em outras guildas !"));
                                            return true;
                                        }
                                        right = new Location(p.getWorld(), x + 16, 0, y + 16);
                                        cRight = ClanLand.getClanAt(right);
                                        if (cRight == null || !cRight.getTag().equalsIgnoreCase(minhaTag)) {
                                            if (cRight != null && !cRight.getTag().equalsIgnoreCase(minhaTag)) {
                                                p.sendMessage(ChatColor.RED + L.m("Voce nao pode conquistar colado em outras guildas !"));
                                                return true;
                                            }
                                            if (qtdTerrenos > 0) {
                                                ClanLand.msg(p, L.m("Voce so pode conquistar terrenos do lado do seu ou longe de inimigos !"));
                                                return true;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            // }
            ClanLand.econ.withdrawPlayer(p.getName(), preco);
            p.playSound(p.getLocation(), Sound.BLOCK_GRASS_BREAK, 10, 0);
            ClanLand.setClanAt(p.getLocation(), cp.getClan().getTag());
            ClanLand.msg(p, L.m("Este terreno agora eh da sua guilda."));
            ClanLand.update(p, p.getLocation());
            return true;
        } else if (args[0].equals("info") || args[0].equals("i")) {
            if (c == null) {
                ClanLand.msg(p, L.m("Esse terreno nao tem dono."));
                return true;
            }
            if (cp != null && c.getTag().equals(cp.getClan().getTag())) {
                String owner = ClanLand.getOwnerAt(p.getLocation());
                if (owner.equals("none")) {
                    ClanLand.msg(p, L.m("Esse terreno eh publico, do seu clan."));
                    return true;
                } else {
                    List<String> membros = ClanLand.getMembersAt(p.getLocation());
                    ClanLand.msg(p, "Esse terreno eh do jogador " + owner + ChatColor.GREEN + ", do seu clan.");
                    if (membros.size() == 1 && membros.get(0).isEmpty()) {
                        ClanLand.msg(p, L.m("Este terreno nao tem membros."));
                    } else {
                        ClanLand.msg(p, L.m("Membros do terreno:"));
                        for (String s : membros) {
                            UUID id = UUID.fromString(s);
                            OfflinePlayer mem = Bukkit.getPlayer(id);
                            if (mem == null) {
                                mem = Bukkit.getOfflinePlayer(id);
                            }
                            if (mem != null) {
                                ClanLand.msg(p, "- " + mem.getName());
                            }
                        }
                    }
                    return true;
                }
            }
            ClanLand.msg(p, L.m("Este terreno eh do clan ", c.getColorTag()));
            return true;
        } else if (args[0].equals("safe")) {
            if (p.isOp()) {
                if (ClanLand.isSafeZone(p.getLocation())) {
                    ClanLand.setClanAt(p.getLocation(), "WILD");
                    ClanLand.msg(p, L.m("Nao eh mais safezone"));
                } else {
                    ClanLand.setClanAt(p.getLocation(), "SAFE");
                    ClanLand.msg(p, L.m("Agora eh safezone"));
                }
                ClanLand.update(p, p.getLocation());
                return true;
            } else {
                sendHelp(cs, leader);
                return true;
            }
        } else if (args[0].equals("war")) {
            if (p.isOp()) {
                if (ClanLand.isWarZone(p.getLocation())) {
                    ClanLand.setClanAt(p.getLocation(), "WILD");
                    ClanLand.msg(p, L.m("Nao eh mais warzone"));
                } else {
                    ClanLand.setClanAt(p.getLocation(), "WARZ");
                    ClanLand.msg(p, L.m("Agora eh warzone"));
                }
                ClanLand.update(p, p.getLocation());
                return true;
            } else {
                sendHelp(cs, leader);
                return true;
            }
        } else {
            sendHelp(cs, leader);
            return true;
        }
    }
}
