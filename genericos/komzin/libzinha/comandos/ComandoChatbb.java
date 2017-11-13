/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genericos.komzin.libzinha.comandos;

import genericos.komzin.libzinha.InstaMCLibKom;
import genericos.komzin.libzinha.PlayerInfo;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Gabriel
 */
public class ComandoChatbb implements CommandExecutor {

    public ComandoChatbb() {
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] strings) {
        if (cs instanceof Player) {
            Player p = (Player) cs;
            if (!cs.hasPermission("kom.staff")) {
                return true;
            }
            PlayerInfo meta = InstaMCLibKom.getinfo(p);
            if (meta.inChannel == null || !meta.inChannel.equalsIgnoreCase("staff")) {
                meta.inChannel = "staff";
                p.sendMessage(ChatColor.GOLD + "Voce esta falando dos bigboss (privado), pegue um whisky e sirva-se de um charuto");
            } else {
                meta.inChannel = null;
                p.sendMessage(ChatColor.GOLD + "Voce saiu do chat bigboss");
            }
        }
        return true;
    }

}
