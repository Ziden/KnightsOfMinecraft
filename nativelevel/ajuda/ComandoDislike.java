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
public class ComandoDislike implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] strings) {
        Player p = (Player)cs;
        if(Cmds.possiveisLikes.containsKey(p.getUniqueId())) {
            UUID like = Cmds.possiveisLikes.get(p.getUniqueId());
            Cmds.possiveisLikes.remove(p.getUniqueId());
            Player likado = Bukkit.getPlayer(like);
            if(likado != null) {
                likado.sendMessage(ChatColor.RED+" "+p.getName()+" deu "+ChatColor.YELLOW+" DISLIKE "+ChatColor.GREEN+" na sua resposta !");
                p.sendMessage(ChatColor.GREEN+"Voce deu dislike na resposta");
            } else {
                p.sendMessage(ChatColor.RED+"Voce deu dislike mas o jogador estava offline");
            }
        } else {
            p.sendMessage(ChatColor.RED+"Voce nao tem nada para dar LIKE.");
        }
        return true;
        
    }

}
