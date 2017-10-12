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

public class DropMob extends CustomItem {

    public DropMob() {
        super(Material.CHEST, L.m("Bau Envelhecido"), L.m("Um velho bau empoeirado"), CustomItem.RARO);
    } 
   
    @Override
    public boolean onItemInteract(Player p) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "loot give "+p.getName()+" DropMob");
        if (p.getItemInHand().getAmount() == 1) {
            p.setItemInHand(null);
        } else {
            p.getItemInHand().setAmount(p.getItemInHand().getAmount() - 1);
        }
        p.updateInventory();
        return true;
    }

}
