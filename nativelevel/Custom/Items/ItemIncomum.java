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
import nativelevel.Equipment.Generator.EquipGenerator;
import nativelevel.Custom.CustomItem;
import nativelevel.Jobs;
import nativelevel.KoM;
import nativelevel.sisteminhas.ClanLand;
import nativelevel.Lang.L;
import nativelevel.gemas.Raridade;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemIncomum extends CustomItem {

    public ItemIncomum() {
        super(Material.CHEST, L.m("Item Incomum Sortido"), L.m("Um item legal aleatorio"), CustomItem.INCOMUM);
    } 
  
    @Override
    public boolean onItemInteract(Player p) {  
        p.getInventory().setItemInMainHand(EquipGenerator.gera(Raridade.Incomum, p.getLevel()));
        return true;
    }

}
