/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.ComandosNovos.list.KomSubs;


import nativelevel.ComandosNovos.Comando.CommandType;
import nativelevel.ComandosNovos.SubCmd;
import nativelevel.Custom.CustomPotion;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 *
 * @author User
 * 
 */

public class CmdPotions extends SubCmd {

    public CmdPotions() {
        super("potions", CommandType.OP);
    }

    @Override
    public void execute(CommandSender cs, String[] args) {
        Player p = (Player) cs;
        CustomPotion.mostra(p);
    }

}
