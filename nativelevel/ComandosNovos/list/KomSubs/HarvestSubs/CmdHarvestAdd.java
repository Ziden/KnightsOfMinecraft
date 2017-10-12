/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.ComandosNovos.list.KomSubs.HarvestSubs;

import java.util.HashSet;
import nativelevel.Custom.CustomItem;
import nativelevel.Harvesting.HarvestConfig;
import nativelevel.Harvesting.Harvestable;
import nativelevel.Lang.L;
import nativelevel.Menu.Menu;
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
import org.bukkit.util.BlockIterator;

/**
 *
 * @author vntgasl
 */
public class CmdHarvestAdd extends SubCmd {

    public CmdHarvestAdd() {
        super("add", CommandType.OP);
    }

    public static Block getTargetBlock(Player player, int range) {
        BlockIterator iter = new BlockIterator(player, range);
        Block lastBlock = iter.next();
        while (iter.hasNext()) {
            lastBlock = iter.next();
            if (lastBlock.getType() == Material.AIR) {
                continue;
            }
            break;
        }
        return lastBlock;
    }
    
    @Override
    public void execute(CommandSender cs, String[] args) {
        if (args.length < 4) {
            cs.sendMessage(ChatColor.GREEN + "Use /kom coletavel add <classe> <minSkill>");
        } else {
            String skill = args[2];
            int id = Menu.getId(skill);
            if (skill != null && id != -1) {
                Player p = (Player) cs;
                ItemStack item = p.getItemInHand();
                int minSkill = Integer.valueOf(args[3]);
                if (item == null || item.getType()==Material.AIR) {
                    
                    Block b = getTargetBlock(p, 5);
                    if(b==null)
                        return;
                    item = new ItemStack(b.getType().getId(), 1, (short)0, (byte)0);
                    if(item == null)
                        return;
                    p.sendMessage("Adicionado bloco q vc tava olhando "+b.getType().name());
                }
                Material mat = item.getType();
                byte data = item.getData().getData();
                Harvestable harvestable = new Harvestable(mat, data, skill, minSkill, 1);
                HarvestConfig.add(harvestable);
                p.sendMessage(ChatColor.GREEN + L.m("Adicionado bloco coletavel !"));
            } else {
                cs.sendMessage(ChatColor.RED + L.m("Esse nao eh uma classe valida!"));
            }
        }
    }

}
