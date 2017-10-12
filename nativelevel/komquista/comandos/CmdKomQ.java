package nativelevel.komquista.comandos;

import java.util.List;
import nativelevel.komquista.DB;
import nativelevel.komquista.managers.ControleTempos;
import nativelevel.komquista.managers.Guilda;
import nativelevel.komquista.managers.KomQuistaManager;
import nativelevel.komquista.utils.ChatUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdKomQ
        implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
        if (cmd.getName().equalsIgnoreCase("komq")) {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("placar")) {
                    if (!KomQuistaManager.isAberto()) {
                        sender.sendMessage("§cO Evento está fechado!");
                        return false;
                    }

                    List<Guilda> guildas = ControleTempos.getGuildasByTime();
                    ChatColor last = ChatColor.GOLD;
                    ChatUtils.sendMessage(sender, "§7O evento acaba em §c" + KomQuistaManager.getTempoRestante() + " §7minutos!");
                    if (!ControleTempos.lasttag.equalsIgnoreCase("")) {
                        ChatUtils.sendMessage(sender, "§bAtual : " + ControleTempos.lasttag + " com " + (System.currentTimeMillis() - ControleTempos.lastdominio) / 1000L + " segundos!");
                    }
                    if (guildas.size() > 0) {
                        for (int x = 0; x < guildas.size(); x++) {
                            Guilda g = (Guilda) guildas.get(x);
                            ChatUtils.sendMessage(sender, last + "" + ChatUtils.getNumber(x + 1) + " - A guilda §l" + g.getTag() + ChatColor.RESET + "" + last + " está com §n" + g.getTotalTime() + ChatColor.RESET + "" + last + " segundos!");
                            last = ChatColor.GREEN;
                        }

                    } else {
                        ChatUtils.sendMessage(sender, "§cNinguém dominou o castelo ainda!");
                    }
                    return true;
                }
                if (!args[0].equalsIgnoreCase("holos")) {

                    if (args[0].equalsIgnoreCase("addholo")) {
                        if ((!(sender instanceof Player)) || (!sender.isOp())) {
                        }
                    } else {
                        if (args[0].equalsIgnoreCase("ultimo")) {
                            String last = DB.getLast();
                            if (last != null) {
                                sender.sendMessage("§cUltimo vencedor foi " + last + " !");
                            } else {
                                sender.sendMessage("§cNão teve ultimo ganhador do KomQuista!");
                            }

                            return true;
                        }
                        if ((args[0].equalsIgnoreCase("abrirtreino")) && (sender.isOp())) {
                            if ((KomQuistaManager.isAberto()) || (KomQuistaManager.isAbrindo())) {
                                sender.sendMessage("§cKomQuista já está sendo aberto ou já está!");
                                return true;
                            }
                            KomQuistaManager.abre(false);
                            return true;
                        }
                        if ((args[0].equalsIgnoreCase("abrir")) && (sender.isOp())) {
                            if ((KomQuistaManager.isAberto()) || (KomQuistaManager.isAbrindo())) {
                                sender.sendMessage("§cKomQuista já está sendo aberto ou já está!");
                                return true;
                            }
                            KomQuistaManager.abre(true);
                            return true;
                        }
                        if ((args[0].equalsIgnoreCase("fechar")) && (sender.isOp())) {
                            if (!KomQuistaManager.isAberto()) {
                                sender.sendMessage("§cKomQuista já está fechado!");
                                return true;
                            }
                            KomQuistaManager.fecha();
                            return true;
                        }
                    }
                }
            } else if (args.length == 2) {
                if (args[0].equalsIgnoreCase("ver")) {
                    sender.sendMessage("§cA Guilda " + args[1] + " ganhou " + DB.getWinsOfTag(args[1].toLowerCase()) + " KomQuistas!");
                    return true;
                }
                if ((args[0].equalsIgnoreCase("deleteholo")) && (sender.isOp())) {
                    int id;
                    try {
                        id = Integer.valueOf(args[1]).intValue();
                    } catch (NumberFormatException e) {
                        sender.sendMessage("§aIsso ae não é um numero");
                        return false;
                    }

                    boolean deleto = DB.removeLoc(id);
                    if (deleto) {
                        sender.sendMessage("§eHolograma " + id + " deletado com sucesso!");
                    } else {
                        sender.sendMessage("§eNão existe um holograma do komquista com esse id!");
                    }

                    return true;
                }
            }

            sender.sendMessage("§c-§a-§c-§a-§c-§a-§c-§a-§c-§a-§c-§a-§c-§a-§c-§a-§c-§a-§c-§a-§c-§a-§c-§a-");
            sender.sendMessage("§e-/komq placar");
            sender.sendMessage("§e-/komq ultimo");
            sender.sendMessage("§e-/komq ver TAG");
            if (sender.isOp()) {
                sender.sendMessage("§e-/komq abrirtreino");
                sender.sendMessage("§e-/komq abrir");
                sender.sendMessage("§e-/komq fechar");
                sender.sendMessage("§e-/komq addholo");
                sender.sendMessage("§e-/komq holos");
                sender.sendMessage("§e-/komq deleteholo ID");
            }
            sender.sendMessage("§c-§a-§c-§a-§c-§a-§c-§a-§c-§a-§c-§a-§c-§a-§c-§a-§c-§a-§c-§a-§c-§a-§c-§a-");
        }

        return false;
    }
}
