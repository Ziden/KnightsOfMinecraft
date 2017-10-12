/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.titulos;

import java.util.HashMap;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author User
 */
public class CmdTitulo implements CommandExecutor {


    public static ChatColor getCorNivel(int nivel) {
        if (nivel <= 1) {
            return ChatColor.GRAY;
        }
        switch (nivel) {
            case 2:
                return ChatColor.WHITE;
            case 3:
                return ChatColor.YELLOW;
            case 4:
                return ChatColor.LIGHT_PURPLE;
            case 5:
                return ChatColor.GREEN;
            case 6:
                return ChatColor.BLUE;
            case 7:
                return ChatColor.GOLD;
            case 8:
                return ChatColor.RED;

        }
        return ChatColor.AQUA;
    }



    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] args) {
           Player p = (Player) cs;
        if (args.length == 0) {
            EscolheTitulo.open(p);
        }
       return true;
    }

}
