/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.Comandos;

import nativelevel.Custom.Items.ItemConsole;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Carlos
 */
public class ConsoleItem implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] strings) {
        if (!cs.isOp()) {
            return false;
        }
        if (!(cs instanceof Player)) {
            return false;
        }
        Player p = (Player) cs;
        if (strings.length <= 1) {
            p.sendMessage("§c/consoleitem IDDOITEM Comando!");
            return false;
        }

        String cmd = "";
        for (int x = 1; x < strings.length; x++) {
            cmd += strings[x] + " ";
        }
        cmd = cmd.trim();
        p.getInventory().addItem(ItemConsole.create(cmd));
        p.sendMessage("§a Ta ae o item com o cmd §c/" + cmd);
        return false;
    }

}
