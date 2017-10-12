package nativelevel.Comandos;

import nativelevel.sisteminhas.ClanLand;
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
public class CmdAnuncio implements CommandExecutor  {

    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] args) {
        
        if(!cs.hasPermission("kom.vip")) {
            cs.sendMessage(ChatColor.RED+"Voce precisa ser VIP para poder usar este comando");
            return true;
        }
        
        String a = "";
        for(int x = 0 ; x < args.length ; x++) {
            a += args[x]+" ";
        }
        
        Player p = (Player)cs;
        
        if(!ClanLand.econ.has(p.getName(), 50)) {
            p.sendMessage(ChatColor.RED+"Voce precisa de 50 esmeraldas para fazer um anuncio");
            return true;
        }
        
        ClanLand.econ.withdrawPlayer(p.getName(), 50);
        
        String frase = ChatColor.RED+""+ChatColor.BOLD+"[Berrante da Montanha] "+ChatColor.RESET+a.replaceAll("&", "§");
        
        for(Player pl : Bukkit.getOnlinePlayers()) {
            pl.sendMessage(frase);
        }
        
        return false;
    }

}
