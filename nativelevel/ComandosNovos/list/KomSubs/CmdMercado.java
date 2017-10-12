/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.ComandosNovos.list.KomSubs;


import java.util.ArrayList;
import java.util.List;
import nativelevel.ComandosNovos.Comando.CommandType;
import nativelevel.ComandosNovos.SubCmd;
import nativelevel.ComandosNovos.list.KomSubs.CraftSubs.CmdCraftAdd;
import nativelevel.ComandosNovos.list.KomSubs.CraftSubs.CmdCraftCheck;
import nativelevel.mercadinho.MarketManager;
import nativelevel.mercadinho.MenuMercado;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author vntgasl
 * 
 */

public class CmdMercado extends SubCmd {

    public CmdMercado() {
        super("mercadoteste", CommandType.OP); // só op
    }

    @Override
    public void execute(CommandSender cs, String[] args) {
        if(cs instanceof Player) {
            cs.sendMessage(ChatColor.GREEN+"Enquanto nao tem NPC o mercado abre por comando mermo =P");
            MenuMercado.abre((Player)cs);
        }
        else
            cs.sendMessage("Abrir o mercado do console é dificil neh mano...");
    }

}
