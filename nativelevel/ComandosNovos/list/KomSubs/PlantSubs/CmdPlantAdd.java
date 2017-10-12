/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.ComandosNovos.list.KomSubs.PlantSubs;

import java.util.HashSet;
import nativelevel.Custom.CustomItem;
import nativelevel.Lang.L;
import nativelevel.Menu.Menu;
import nativelevel.Planting.PlantConfig;
import nativelevel.Planting.Plantable;
import nativelevel.ComandosNovos.Comando.CommandType;
import nativelevel.ComandosNovos.SubCmd;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author vntgasl
 */
public class CmdPlantAdd extends SubCmd {

    public CmdPlantAdd() {
        super("add", CommandType.OP);
    }

    public void display(Player p, HashSet<Plantable> harvestables) {
        Inventory i = Bukkit.createInventory(p, InventoryType.CHEST, L.m("Plantables"));
        for (Plantable h : harvestables) {
            i.addItem(h.getIcon());
        }
        p.openInventory(i);
    }

    @Override
    public void execute(CommandSender cs, String[] args) {
        if (args.length < 6) {
            cs.sendMessage(ChatColor.GREEN + "Use /kom colocavel add <classe> <minSkill> <material> <data>");
        } else {
            String skill = args[2];
            Material mat = null;
            int data = Integer.valueOf(args[5]);
            try {
                mat = Material.valueOf(args[4]);
            } catch(Exception e) {
                cs.sendMessage(ChatColor.RED+"Nao achei o material "+args[4]+" tente bater com uma maça no bloco plantado pra pegar..");
                return;
            }
            int id = Menu.getId(skill);
            if (skill != null && id != -1) {
                Player p = (Player) cs;
                int minSkill = Integer.valueOf(args[3]);
                Plantable harvestable = new Plantable(mat, (byte)data, skill, minSkill, 1);
                PlantConfig.add(harvestable);
                p.sendMessage(ChatColor.GREEN + L.m("Adicionado bloco colocavel !"));
            } else {
                cs.sendMessage(ChatColor.RED + L.m("Esse nao eh uma classe valida!"));
            }
        }
    }

}
