/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.ComandosNovos.list.KomSubs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import nativelevel.KoM;
import nativelevel.MetaShit;
import nativelevel.ComandosNovos.Comando.CommandType;
import nativelevel.ComandosNovos.SubCmd;
import nativelevel.ComandosNovos.list.KomSubs.CraftSubs.CmdCraftAdd;
import nativelevel.ComandosNovos.list.KomSubs.CraftSubs.CmdCraftCheck;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author vntgasl
 */
public class CmdOE extends SubCmd {

    public CmdOE() {
        super("echest", CommandType.OP);
    }
    
   // TODO evitar acessar banco qnd xeretar

    public static HashSet<UUID> xeretando = new HashSet<UUID>();
    
    @Override
    public void execute(CommandSender cs, String[] args) {
        if (args.length < 2) {
            cs.sendMessage(ChatColor.RED + "Digite /kom echest <player>");
            return;
        } else {
            String player = args[1];
            OfflinePlayer objetoPlayer = Bukkit.getPlayer(player);
            if (objetoPlayer == null) {
                objetoPlayer = Bukkit.getOfflinePlayer(player);
            } else {
                //((Player)objetoPlayer).sendMessage(ChatColor.RED+"Sua EnderChest está sendo investigada !");
                Player on = (Player)objetoPlayer;
                if(on.getOpenInventory()!=null) {
                    on.closeInventory();
                }
            }

            KoM.log.info("Abrindo inv de "+objetoPlayer.getName()+" uid "+objetoPlayer.getUniqueId());
            
            ItemStack[] items = KoM.database.getBanco(objetoPlayer.getUniqueId());
            int slots = KoM.database.getSlotsBanco(objetoPlayer.getUniqueId().toString());
            int linhas = slots + 1;
            if (linhas > 5) {
                linhas = 5;
            }

            Inventory i = Bukkit.createInventory((Player) cs, linhas * 9, "Xeretando Banco");
            for (ItemStack item : items) {
                if (item != null && item.getType() != Material.AIR) {
                    i.addItem(item);
                }
            }
            ((Player)cs).openInventory(i);
            MetaShit.setMetaObject("vendoenderchest", ((Player)cs), objetoPlayer.getUniqueId());
            xeretando.add(objetoPlayer.getUniqueId());
        }
    }
    
   
    
    public static void invClick(InventoryCloseEvent ev) {
       if(ev.getPlayer().hasMetadata("vendoenderchest")) {
           UUID u = (UUID)MetaShit.getMetaObject("vendoenderchest", ev.getPlayer());
           KoM.database.setBanco(u, ev.getInventory().getContents());
           ev.getPlayer().sendMessage(ChatColor.GREEN+"EnderChest Salva");
           ev.getPlayer().removeMetadata("vendoenderchest", KoM._instance);
           xeretando.remove(u);
       }
    }

}
