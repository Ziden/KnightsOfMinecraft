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

import java.util.HashSet;
import nativelevel.Custom.CustomItem;
import nativelevel.Jobs;
import nativelevel.KoM;
import nativelevel.Lang.L;
import nativelevel.Attributes.Mana;
import nativelevel.gemas.Gema;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class Encaixe extends CustomItem {

    public static HashSet<String> protegidos = new HashSet<String>();

    public Encaixe() {
        super(Material.MINECART, L.m("Encaixe"), L.m("Coloque sobre um item (Pode Quebrar)"), CustomItem.RARO);
    }

    public static void encaixa(InventoryClickEvent ev) {
        if (ev.isCancelled()) {
            return;
        }
        if (ev.getCursor() == null) {
            return;
        }
        String ci = CustomItem.getCustomItem(ev.getCursor());
        if (ci == null) {
            return;
        }
        if (ev.getCurrentItem() == null) {
            return;
        }
        if (ci.equalsIgnoreCase("Encaixe")) {
            if(ev.getCurrentItem()==null || ev.getCurrentItem().getType()==Material.AIR)
                return;
            if (!podeTerEncaixe(ev.getCurrentItem())) {
                ((Player) ev.getWhoClicked()).sendMessage(ChatColor.RED + "Apenas equipamentos podem ter encaixes.");
                return;
            }
            if (Gema.temSockets(ev.getCurrentItem())) {
                ((Player) ev.getWhoClicked()).sendMessage(ChatColor.RED + "Item item ja tem um encaixe.");
                return;
            }
            int jobLevel = Jobs.getJobLevel("Ferreiro", ((Player) ev.getWhoClicked()));
            if (jobLevel != 1) {
                ((Player) ev.getWhoClicked()).sendMessage(ChatColor.RED + "Apenas bons ferreiros sabem fazer isto.");
                return;
            }
            ev.setCancelled(true);
            int SUCESSO = Jobs.hasSuccess(50, "Ferreiro", ((Player) ev.getWhoClicked()));
            if (SUCESSO == Jobs.success) {
                Gema.addSocket(ev.getCurrentItem(), Gema.values()[Jobs.rnd.nextInt(Gema.values().length)]);
                ev.setCursor(new ItemStack(Material.COAL));
                ((Player) ev.getWhoClicked()).sendMessage(ChatColor.RED + "Voce adicionou um encaixe ao item.");
            } else {
                ev.setCursor(null);
                ev.setCurrentItem(new ItemStack(Material.COAL));
                ((Player) ev.getWhoClicked()).sendMessage(ChatColor.RED + "Voce falhou em adicionar um encaixe ao item e o quebrou.");
            }
        }
    }

    private static boolean podeTerEncaixe(ItemStack ss) {
        String name = ss.getType().name();
        if (name.contains("BOW") || name.contains("_SPADE") || name.contains("_SWORD") || name.contains("_AXE") || name.contains("_HOE") || name.contains("_PICKAXE") || name.contains("_LEGGINGS") || name.contains("CHESTPLATE") || name.contains("BOOTS") || name.contains("HELMET")) {
            return true;
        }
        return false;
    }

    @Override
    public boolean onItemInteract(Player p) {
        return false;
    }

}
