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
import nativelevel.CFG;
import nativelevel.Custom.CustomItem;
import nativelevel.Jobs;
import nativelevel.KoM;
import nativelevel.sisteminhas.ClanLand;
import nativelevel.Lang.L;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Runa extends CustomItem {

    public Runa() {
        super(Material.FIREWORK_CHARGE, L.m("Runa"), L.m("Usada por magos"), CustomItem.INCOMUM);
    }

    public static Location getLocal(ItemStack runa) {
        ItemMeta m = runa.getItemMeta();
        List<String> lore = m.getLore();
        if (lore.size() == 0) {
            return null;
        }
        String[] split = ChatColor.stripColor(lore.get(0)).split(":");
        if (split.length > 1) {
            String s = split[1];
            String[] coords = s.split("@");
            double x = Integer.valueOf(coords[0]);
            double y = Integer.valueOf(coords[1]);
            double z = Integer.valueOf(coords[2]);
            Location l = new Location(Bukkit.getWorld(CFG.mundoGuilda), x, y, z);
            return l;
        }
        return null;
    }

    public static void marca(ItemStack ss, Location local, String nomePlay) {
        String loc = local.getBlockX() + "@" + local.getBlockY() + "@" + local.getBlockZ();
        ItemMeta meta = ss.getItemMeta();
        meta.setLore(Arrays.asList(new String[]{
            ChatColor.BLACK + ":" + loc,
            ChatColor.GOLD + "- Uma runa marcada por "+nomePlay,
            ChatColor.BLACK + ":Runa"}
        ));
        ss.setItemMeta(meta);
    }

    @Override
    public boolean onItemInteract(Player p) {
        if (Jobs.getJobLevel("Mago", p) != 1) {
            p.sendMessage(ChatColor.RED + "Voce olha para a pedra magica sem entender oq ela faz.");
        } else {
            p.sendMessage(ChatColor.RED + "Voce pode marcar esta runa usando a magia de marcar runas !");
        }
        return true;
    }

}
