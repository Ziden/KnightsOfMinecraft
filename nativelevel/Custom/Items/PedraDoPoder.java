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
import nativelevel.sisteminhas.ClanLand;
import nativelevel.Lang.L;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PedraDoPoder extends CustomItem {

    public PedraDoPoder() {
        super(Material.CLAY_BALL, L.m("Pedra do Poder"), L.m("Aumenta o poder da guilda"), CustomItem.EPICO);
    }

    @Override
    public boolean onItemInteract(Player p) {
        
        ClanPlayer cp = ClanLand.manager.getClanPlayer(p);
        if (cp == null || !cp.isLeader()) {
            p.sendMessage(ChatColor.RED + L.m("Apenas lideres de guildas tem interesse neste item raro !"));
            return true;
        }
        if (cp.getClan() != null) {
            int poder = ClanLand.getPoder(cp.getTag());
            ClanLand.setPoder(cp.getClan().getTag(), (poder + 1));
            p.sendMessage(ChatColor.GREEN + L.m("Agora sua guilda tem % de poder !!",(poder + 1) ));
            if (p.getItemInHand().getAmount() > 1) {
                p.getItemInHand().setAmount(p.getItemInHand().getAmount() - 1);
            } else {
                p.setItemInHand(null);
                //p.getInventory().removeItem(new ItemStack(Material.LAPIS_BLOCK, 1));
            }
        }
        return true;
    }
}
