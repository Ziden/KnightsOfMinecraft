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

import nativelevel.rankings.Estatistica;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CmdTop implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] args) {
      
        if(args.length == 0) {
            
            cs.sendMessage(ChatColor.GREEN+"____ Topzeiros do KoM ____");
            
            for(Estatistica s : Estatistica.values()) {
                cs.sendMessage(ChatColor.GREEN+"| "+ChatColor.YELLOW+"/top "+s.titulo);
            }
            
        }
        return true;
    }

}
