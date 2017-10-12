/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.ComandosNovos;

import java.util.ArrayList;
import java.util.List;
import nativelevel.ComandosNovos.Comando.CommandType;
import nativelevel.KoM;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author User
 * 
 */

public abstract class SubCmd {

    public String cmd;
    public CommandType type;
    protected List<SubCmd> subs = new ArrayList<SubCmd>();

    public SubCmd(String cmd, CommandType type) {
        this.cmd = cmd;
        this.type = type;
    }

    public void showSubCommands(CommandSender cs, String mainCmd) {
        cs.sendMessage(ChatColor.YELLOW + "|________________oO " + cmd + " Oo_____________");
        for (SubCmd cmd : subs) {
            if (cmd.type != CommandType.OP) {
                cs.sendMessage(ChatColor.YELLOW + "|" + ChatColor.GREEN + " - /" + mainCmd + " " + cmd.cmd);
            } else if (cs.isOp()) {
                cs.sendMessage(ChatColor.YELLOW + "|" + ChatColor.BLUE + " - /" + mainCmd + " " + cmd.cmd + " (Op)");
            }
        }
        cs.sendMessage(ChatColor.YELLOW + "|_______________________________________");
    }

    public abstract void execute(CommandSender cs, String[] args);

    public void runSync(Runnable r) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(KoM._instance, r);
    }

    public void runAsync(Runnable r) {
        new Thread(r).start();
    }
}
