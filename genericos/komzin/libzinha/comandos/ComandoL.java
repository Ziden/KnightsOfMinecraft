/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genericos.komzin.libzinha.comandos;

import genericos.komzin.libzinha.InstaMCLibKom;
import genericos.komzin.libzinha.PlayerInfo;
import nativelevel.KoM;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Gabriel
 */
public class ComandoL implements CommandExecutor {

    public ComandoL() {
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] args) {
        if (cs instanceof Player) {
            Player p = (Player) cs;
            PlayerInfo meta = InstaMCLibKom.getinfo(p);
            if (meta.talkingTo == null) {
                p.sendMessage(ChatColor.YELLOW + "Voce ja está falando normalmente !");
                p.removeMetadata("fixglobal", KoM._instance);
            } else {
                meta.talkingTo = null;
                p.sendMessage(ChatColor.GREEN + "Agora você está falando no local !");
                p.removeMetadata("fixglobal", KoM._instance);
            }
        }
        return true;
    }
}