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

import java.util.Arrays;
import java.util.List;
import nativelevel.Custom.CustomItem;
import nativelevel.KoM;
import nativelevel.sisteminhas.ClanLand;
import nativelevel.Lang.L;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SeguroDeItems extends CustomItem {

    public SeguroDeItems() {
        super(Material.PAPER, L.m("Seguro de Itens"), L.m("Nao dropa items ao morrer"), CustomItem.EPICO);
    }

    public static ItemStack geraSeguro(int cargas) {

        ItemStack papel = CustomItem.getItem(SeguroDeItems.class).generateItem();

        ItemMeta meta = papel.getItemMeta();
        meta.setLore(Arrays.asList(new String[]{
            ChatColor.AQUA + "Cargas : " + cargas,
            ChatColor.BLACK + ":Seguro de Itens",}
        ));
        papel.setItemMeta(meta);
        return papel;
    }

    public static boolean isItemSeguro(ItemStack ss) {
        if (ss == null) {
            return false;
        }
        if (ss.getType() == Material.MONSTER_EGG || ss.getType() == Material.MONSTER_EGGS || ss.getType() == Material.WRITTEN_BOOK) {
            return true;
        }
        return false;
    }

    public static boolean segurou(Player p) {
        for (ItemStack ss : p.getInventory().getContents()) {
            if (ss == null) {
                continue;
            }
            String c = CustomItem.getCustomItem(ss);
            if (c != null) {
                if (c.equalsIgnoreCase("Seguro de Itens")) {
                    return gastaSeguro(p, ss);
                }
            }
        }
        return false;
    }

    public static boolean gastaSeguro(Player p, ItemStack seguro) {
        ItemMeta meta = seguro.getItemMeta();
        List<String> lore = meta.getLore();
        try {
            int qtd = Integer.valueOf(lore.get(0).split(":")[1].trim());
            qtd--;
            if (qtd <= 0) {
                meta.setDisplayName("Seguro vencido");
                lore.clear();
            } else {
                lore.remove(0);
                lore.add(0, ChatColor.AQUA + "Cargas : " + qtd);
            }
            meta.setLore(lore);
            seguro.setItemMeta(meta);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean onItemInteract(Player p) {
        //Terrenos.permission.
        return true;
    }

}
