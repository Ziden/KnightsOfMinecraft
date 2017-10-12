package nativelevel.ArenaGuilda2x2;

import java.util.List;
import nativelevel.sisteminhas.ClanLand;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdArena implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] args) {
        if (cs instanceof Player) {
            Player p = (Player) cs;
            if (args.length == 0) {
                p.sendMessage(ChatColor.GOLD + "oOo_____________Arena do KoM __________oOo");
                p.sendMessage(ChatColor.GOLD + "/arena jogar 2x2 normal");
                p.sendMessage(ChatColor.RED + "/arena jogar 1x1 torneio");
                p.sendMessage(ChatColor.RED + "/arena jogar 2x2 ranked");
                p.sendMessage(ChatColor.GOLD + "/arena sairfila");
                p.sendMessage(ChatColor.GOLD + "/arena sair");
                p.sendMessage(ChatColor.GOLD + "/arena verstats");
                if (p.isOp()) {
                    p.sendMessage(ChatColor.GREEN + "oOo___________OP__________oOo");
                    p.sendMessage(ChatColor.GREEN + "/arena criar <nome>");
                    p.sendMessage(ChatColor.GREEN + "/arena listar");
                    p.sendMessage(ChatColor.GREEN + "/arena deletar <nome>");
                    p.sendMessage(ChatColor.GREEN + "/arena tp <nome>");
                    p.sendMessage(ChatColor.GREEN + "/arena setspawn1 <nome>");
                    p.sendMessage(ChatColor.GREEN + "/arena setspawn2 <nome>");
                }
            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("listar") && p.isOp()) {
                    List<String> arenas = Arena2x2.sql.listaArenas();
                    String a = "";
                    for (String s : arenas) {
                        a += s + " ";
                    }
                    p.sendMessage(a);
                } else if (args[0].equalsIgnoreCase("debugmode") && p.isOp()) {
                    Arena2x2.debugMode = !Arena2x2.debugMode;
                    p.sendMessage("Debug da arena " + (Arena2x2.debugMode ? "Ligado" : "Desligado"));
                } else if (args[0].equalsIgnoreCase("sairfila")) {
                    Arena2x2.sql.saiDaFila(p.getUniqueId());
                } else if (args[0].equalsIgnoreCase("sair")) {
                    p.chat("/pvpm leave");
                } else if (args[0].equalsIgnoreCase("verstats")) {
                    ClanPlayer cp = ClanLand.manager.getClanPlayer(p);
                    if (cp == null) {
                        p.sendMessage(ChatColor.RED + "Voce precisa de uma guilda !");
                        return true;
                    }
                    Arena2x2.sql.viewStats(p, cp);
                }
            } else if (args.length == 2) {
                if (args[0].equalsIgnoreCase("criar") && p.isOp()) {
                    ArenaManager.criaNovaArena(p.getLocation(), args[1]);
                    p.sendMessage("Arena " + args[1] + " criada");
                } else if (args[0].equalsIgnoreCase("deletar") && p.isOp()) {
                    Arena2x2.sql.deletaArena(args[1]);
                    p.sendMessage("Arena " + args[1] + " deletada");
                } else if (args[0].equalsIgnoreCase("setspawn1") && p.isOp()) {
                  String arena = args[1];
                  Arena2x2.sql.setSpawn1(arena, p.getLocation());
                } else if (args[0].equalsIgnoreCase("setspawn2") && p.isOp()) {
                  String arena = args[1];
                  Arena2x2.sql.setSpawn2(arena, p.getLocation());
                } else if (args[0].equalsIgnoreCase("tp") && p.isOp()) {
                    Location l = Arena2x2.sql.getArena(args[1]);
                    if (l != null) {
                        p.teleport(l);
                    } else {
                        p.sendMessage("Nao encontrei arena..");
                    }
                }
            } else if (args.length == 3) {
                if (args[0].equalsIgnoreCase("jogar") && args[1].equalsIgnoreCase("2x2")) {
                    boolean ranked = args[2].equalsIgnoreCase("ranked");
                    if (ranked) {
                        p.sendMessage(ChatColor.RED + "Engenheiros trabalhando no ranked...");
                        return true;
                    }
                    MatchMaking.botaMatchMaking(p, ranked);
                } else if (args[0].equalsIgnoreCase("jogar") && args[1].equalsIgnoreCase("1x1")) {
                    p.chat("/pvpm join");
                }
            }
        }

        return true;
    }

}
