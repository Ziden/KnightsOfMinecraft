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

import nativelevel.Custom.Buildings.AutoDispenser;
import nativelevel.Custom.CustomItem;
import nativelevel.Jobs;
import nativelevel.sisteminhas.ClanLand;
import nativelevel.Lang.L;
import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class DeedAutoDispenser extends CustomItem {

    public DeedAutoDispenser() {
        super(Material.PAPER, L.m("Projeto de AutoDispenser"), L.m("Constroi um dispenser automatico"), CustomItem.INCOMUM);
    }

    @Override
    public boolean onItemInteract(Player player) {
        return false;
        /*
        if (Jobs.getJobLevel("Engenheiro", player) != 1) {
            player.sendMessage(ChatColor.RED + L.m("Apenas engenheiros sabem usar isto !"));
            return true;
        }

        Location onde = player.getLocation();
        String cla = null;
        Clan c = ClanLand.manager.getClanByPlayerUniqueId(player.getUniqueId());
        ClanPlayer cp = ClanLand.manager.getAnyClanPlayer(player.getUniqueId());
        if (cp != null) {
            cla = cp.getTag();
        }
        
        if(player.getWorld().getName().equalsIgnoreCase("dungeon")) {
            player.sendMessage(ChatColor.RED+"Voce nao pode usar isto aqui");
            return false;
        }
        
        AutoDispenser disp = new AutoDispenser(player.getName(), onde, cla);

        if (!disp.podeConstruir(onde)) {
            player.sendMessage(ChatColor.RED + L.m("Voce nao pode construir isto aqui !"));
            return false;
        }

        disp.constroi(onde);
        player.teleport(player.getLocation().add(0, 3, 0));
        player.playSound(player.getLocation(), Sound.ANVIL_LAND, 10, 0);
        if (player.getItemInHand().getAmount() == 1) {
            player.setItemInHand(null);
        } else {
            player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
        }
        player.sendMessage(ChatColor.GOLD + L.m("Voce construiu o dispenser automatico !"));
        return false;
         */
    }
}
