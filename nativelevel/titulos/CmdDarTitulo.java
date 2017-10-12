/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.titulos;

import java.util.HashMap;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author User
 * 
 */

public class CmdDarTitulo implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] args) {
       // Player p = (Player) cs;
        if(!cs.isOp())
            return true;
        if (args.length != 3) {
            cs.sendMessage("/dartitulo <nick> <cor> <titulo>");
            return true;
        } else {
            String nick = args[0];
            String titulo = args[2];
            ChatColor cor = ChatColor.valueOf(args[1]);
            if (cor == null || cor.isFormat()) {
                cs.sendMessage("Cor invalida!");
            }
            Player pMano = Bukkit.getPlayer(nick);
            if (pMano == null) {
                cs.sendMessage("nao achei o mano " + nick);
                return true;
            }
            TituloDB.addTitulo(pMano.getUniqueId(), titulo, cor, true);
            cs.sendMessage("Adicionei o titulo " + cor + titulo + "§f pro mano " + nick);
        }
        return true;
    }

}
