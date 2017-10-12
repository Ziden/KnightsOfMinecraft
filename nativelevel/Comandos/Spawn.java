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

import me.fromgate.playeffect.PlayEffect;
import me.fromgate.playeffect.VisualEffect;
import nativelevel.sisteminhas.ClanLand;
import nativelevel.KoM;
import nativelevel.Lang.L;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Spawn implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] args) {
        if (cs instanceof Player) {
            Player p = (Player) cs;
            if (!KoM.database.hasRegisteredClass(p.getUniqueId().toString())) {
                p.sendMessage(ChatColor.RED + L.m("Complete o tutorial, mero mortal ! Não tenha preguiça !"));
                return true;
            }
            if (args.length == 1 && args[0].equalsIgnoreCase("set") && p.isOp()) {
                Location l = p.getLocation();
                p.getWorld().setSpawnLocation(l.getBlockX(), l.getBlockY(), l.getBlockZ());
            } else {
                if (p.getWorld().getName().equalsIgnoreCase("arena") || p.getWorld().getName().equalsIgnoreCase("vila") || p.getWorld().getName().equalsIgnoreCase("dungeon") || p.isOp() || p.getWorld().getName().equalsIgnoreCase("eventos")) {
                    p.teleport(p.getWorld().getSpawnLocation());
                    PlayEffect.play(VisualEffect.FIREWORKS_SPARK, p.getLocation(), "color:purple type:burst");
                } else {
                    String type = ClanLand.getTypeAt(p.getLocation());
                    if(!type.equalsIgnoreCase("safe"))
                        p.sendMessage(ChatColor.RED + L.m("Voce pode apenas fazer isto em aventuras !"));
                    else
                        p.teleport(p.getWorld().getSpawnLocation());
                }
            }

        }
        return false;
    }
}
