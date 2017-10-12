/*

 ╭╮╭━╮╱╱╭━╮╭━╮
 ┃┃┃╭╯╱╱┃┃╰╯┃┃
 ┃╰╯╯╭━━┫╭╮╭╮┃
 ┃╭╮┃┃╭╮┃┃┃┃┃┃
 ┃┃┃╰┫╰╯┃┃┃┃┃┃
 ╰╯╰━┻━━┻╯╰╯╰╯

 Desenvolvedor: ZidenVentania
 Colaboradores: NeT32, Gabripj, Feldmann
 Patrocionio: InstaMC

 */
package nativelevel.Custom.Items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import nativelevel.Custom.CustomItem;
import nativelevel.Jobs;
import nativelevel.KoM;
import nativelevel.sisteminhas.ClanLand;
import nativelevel.Lang.L;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemsLord extends CustomItem {

    public ItemsLord() {
        super(Material.CHEST, L.m("Items de Lord"), L.m("Precisa ser lord"), CustomItem.EPICO);
    } 
   
    @Override
    public boolean onItemInteract(Player p) {
        if(!p.hasPermission("kom.lord")) {
            p.sendMessage(ChatColor.RED+"Apenas Lords podem usar isto...");
            return true;
        }
        
        int qtos = 0;
        for(ItemStack ss : p.getInventory().getContents()) {
            if(ss!= null && ss.getType()!=Material.AIR) {
                qtos++;
            }
        }
        
        if(qtos>2) {
            p.sendMessage(ChatColor.RED+"Esvazie seu inventario...");
            return true;
        }
        
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "loot give "+p.getName()+" lord");
        if (p.getItemInHand().getAmount() == 1) {
            p.setItemInHand(null);
        } else {
            p.getItemInHand().setAmount(p.getItemInHand().getAmount() - 1);
        }
        p.updateInventory();
        return true;
    }

}
