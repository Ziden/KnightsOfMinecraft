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

import java.util.List;
import nativelevel.Custom.CustomItem;
import nativelevel.KoM;
import nativelevel.Lang.L;
import nativelevel.utils.LocUtils;
import nativelevel.utils.MetaUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BussolaMagica extends CustomItem {

    public BussolaMagica() {
        super(Material.COMPASS, L.m("Bussola Mágica"), L.m("Aponta para lugares"), CustomItem.INCOMUM);
    }

    @Override
    public boolean onItemInteract(Player player) {

        List<LocalBussola> locais = KoM.database.getLocais();

        Inventory i = Bukkit.createInventory(player, 9 * 4, "Locais");

        for (LocalBussola local : locais) {
            if (local.nivel <= player.getLevel() + 25 || player.hasPermission("kom.locais")) {
                ItemStack ss = new ItemStack(local.getIcone());
                if(ss==null || ss.getItemMeta()==null) {
                    ss = new ItemStack(Material.GRASS);
                }
                i.addItem(MetaUtils.setItemNameAndLore(ss, ChatColor.YELLOW + "Apontar para " + local.nome, new String[]{ChatColor.GREEN + "Nivel: " + local.nivel, ChatColor.GREEN + "Clique para apontar."}));
            }
        }

        player.openInventory(i);

        return false;
    }

    public static LocalBussola getLocal(String nome) {
        List<LocalBussola> locais = KoM.database.getLocais();
        for (LocalBussola local : locais) {
            if (local.nome.equalsIgnoreCase(nome)) {
                return local;
            }
        }
        return null;
    }

    public static void incClick(InventoryClickEvent ev) {
        if ((ev.getInventory().getName() != null && ev.getInventory().getName().equalsIgnoreCase("Locais")) || (ev.getInventory().getTitle() != null && ev.getInventory().getTitle().equalsIgnoreCase("Locais"))) {
            KoM.debug("LOCAIS");
            ev.setCancelled(true);
            Player p = (Player) ev.getWhoClicked();
            p.closeInventory();
            if (ev.getCurrentItem() != null) {
                ItemMeta meta = ev.getCurrentItem().getItemMeta();
                if (meta.getDisplayName() != null && meta.getDisplayName().contains("Apontar para")) {
                    String[] s = meta.getDisplayName().split(" ");
                    String nomeLocal = s[2];
                    LocalBussola loc = getLocal(nomeLocal);
                    if (loc != null) {
                        Location localFinal = LocUtils.str2loc(loc.local);
                      
                        p.setCompassTarget(localFinal);
                        p.sendMessage(ChatColor.GREEN + "Sua bussola agora está apontando para " + loc.nome);
                       
                    }
                }
            }
        }
    }

    public static class LocalBussola {

        public int nivel;
        public String nome;
        public String local;
        
        private Material icone = null;
        
        public Material getIcone() {
            if(icone == null) {
               Location l = LocUtils.str2loc(local);
               icone = l.getWorld().getHighestBlockAt(l).getType();
               if(icone == Material.AIR)
                   icone = l.getWorld().getHighestBlockAt(l).getRelative(BlockFace.DOWN).getType();
               KoM.debug("MAIS ALTO = "+icone);
            }
            return icone;
        }

    }

}
