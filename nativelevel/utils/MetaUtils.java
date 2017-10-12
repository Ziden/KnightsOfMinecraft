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
package nativelevel.utils;

import java.util.Arrays;
import java.util.List;
import nativelevel.Jobs;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Llama;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

public class MetaUtils {

    // editei pra tirar aquele efeito italico q fica, e a cor na desc
    // vc ainda pode por sua cor e efeito, o reset soh afeta antes, e tirar esse comment tb
    public static ItemStack setItemNameAndLore(ItemStack item, String name, String... lore) {
        for (int i = 0; i < lore.length; i++) {
            lore[i] = ChatColor.RESET + lore[i];
        }
        if (item.getType() == Material.POTION) {
            PotionMeta im = (PotionMeta)item.getItemMeta();
            im.setDisplayName(ChatColor.RESET + name);
            im.setLore(Arrays.asList(lore));
            im.setColor(Color.fromRGB(Jobs.rnd.nextInt(255), Jobs.rnd.nextInt(255), Jobs.rnd.nextInt(255)));
            im.setBasePotionData(new PotionData(PotionType.WATER));
            item.setItemMeta(im);
        } else {
            ItemMeta im = item.getItemMeta();
            im.setDisplayName(ChatColor.RESET + name);
            im.setLore(Arrays.asList(lore));
            item.setItemMeta(im);
        }

        return item;
    }

    public static void appendLore(ItemStack item, String lore) {
        ItemMeta meta = item.getItemMeta();
        if (meta.getLore() == null) {
            List<String> l = Arrays.asList(new String[]{lore});
            meta.setLore(l);
        } else {
            List<String> loree = meta.getLore();
            loree.add(lore);
            meta.setLore(loree);
        }
        item.setItemMeta(meta);
    }

}
