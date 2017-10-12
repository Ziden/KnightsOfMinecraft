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

import nativelevel.Custom.CustomItem;
import nativelevel.Lang.L;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CajadoElemental extends CustomItem {

    public CajadoElemental() {
        super(Material.IRON_SPADE, L.m("Cajado Elemental"), L.m("Pode carregar elementos"), CustomItem.RARO);
    }
    
    public static void botaElemento(ItemStack cajado, String elemento) {
        ItemMeta meta = cajado.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN+"Cajado elemental: "+elemento);
        cajado.setItemMeta(meta);
    }
    
    public static String getElemento(ItemStack cajado) {
        ItemMeta meta = cajado.getItemMeta();
        if(meta.getDisplayName().contains(":")) {
            String elemento = meta.getDisplayName().trim().split(":")[1];
            return elemento;
        }
        return null;
    }

    @Override
    public boolean onItemInteract(Player p) {
        return true;
    }
}
