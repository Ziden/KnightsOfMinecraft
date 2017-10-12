package nativelevel.ajuda;

import java.util.UUID;
import nativelevel.rankings.Estatistica;
import nativelevel.rankings.RankDB;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Ziden
 */
public class ComandoLike implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] strings) {
        Player p = (Player)cs;
        if(Cmds.possiveisLikes.containsKey(p.getUniqueId())) {
            UUID like = Cmds.possiveisLikes.get(p.getUniqueId());
            Cmds.possiveisLikes.remove(p.getUniqueId());
            Player likado = Bukkit.getPlayer(like);
            if(likado != null) {
                if(!Cmds.jaDeramLike.containsKey(likado.getUniqueId()) || !Cmds.jaDeramLike.get(likado.getUniqueId()).equals(p.getUniqueId())) {
                    RankDB.addPontoCache(likado, Estatistica.AJUDANTE, 1);
                }
                Cmds.jaDeramLike.put(likado.getUniqueId(),p.getUniqueId());
                likado.sendMessage(ChatColor.GREEN+" "+p.getName()+" deu "+ChatColor.YELLOW+" LIKE "+ChatColor.GREEN+" na sua resposta ! O KoM terá mais jogadores assim :)");
                p.sendMessage(ChatColor.GREEN+"Voce deu like na resposta");
            } else {
                p.sendMessage(ChatColor.RED+"Voce deu LIKE mas o jogador estava offline");
            }
        } else {
            p.sendMessage(ChatColor.RED+"Voce nao tem nada para dar LIKE.");
        }
        return true;
        
    }

}
