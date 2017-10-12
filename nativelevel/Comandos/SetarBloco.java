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


import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


public class SetarBloco implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] args) {
        if(cs.isOp()) {
            String mundo = args[0];
            int x = Integer.valueOf(args[1]);
            int y = Integer.valueOf(args[2]);
            int z = Integer.valueOf(args[3]);
            String tipo = args[4];
            int data = Integer.valueOf(args[5]);
            Bukkit.getWorld(mundo).getBlockAt(x, y, z).setType(Material.valueOf(tipo));
            Bukkit.getWorld(mundo).getBlockAt(x, y, z).setData((byte)data);
        }
        return true;
    }

}
