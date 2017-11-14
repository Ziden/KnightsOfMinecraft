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

import nativelevel.sisteminhas.ClanLand;
import nativelevel.Custom.Buildings.Torre;
import nativelevel.Custom.CustomItem;
import nativelevel.Jobs;
import nativelevel.KoM;
import nativelevel.Lang.L;
import net.sacredlabyrinth.phaed.simpleclans.Clan;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class DeedTorre extends CustomItem {

    public DeedTorre() {
        super(Material.PAPER, L.m("Projeto de Torre"), L.m("Constroi uma torre instantanea"), CustomItem.INCOMUM);
    }

    @Override
    public boolean onItemInteract(Player player) {
        if (Jobs.getJobLevel("Lenhador", player) != 1) {
            player.sendMessage(ChatColor.RED +L.m( "Apenas lenhadores sabem construir a torre !"));
            return true;
        }

        if(player.getWorld().getName().equalsIgnoreCase("dungeon")) {
            player.sendMessage(ChatColor.RED+L.m("Voce nao pode colocar a torre aqui !"));
            return true;
        }
        
        Torre torre = new Torre();
        Location onde = player.getLocation();
        
        onde.setX(onde.getX()-2);
        onde.setZ(onde.getZ()-2);
        
        KoM.debug("Construindo torre em "+onde.toString());
        
        if (!torre.podeConstruir(onde)) {
            player.sendMessage(ChatColor.RED +L.m( "Voce nao pode construir isto aqui !"));
            return false;
        }
         if(player.hasPotionEffect(PotionEffectType.SLOW))
            return false;
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20*5, 1));
        Clan clan = ClanLand.manager.getClanByPlayerUniqueId(player.getUniqueId());
        if(clan==null) {
            player.sendMessage(ChatColor.RED+L.m("Voce nao pode construir a torre por nao ter uma guilda !"));
            return false;
        }
        if(KoM.database.getTower(clan.getTag())!=null) {
            player.sendMessage(ChatColor.RED+L.m("Sua guilda ja tem uma torre, digite /guilda torre para ir para ela !"));
            return false;
        }
        torre.constroi(onde, player, clan);
        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 10, 0);
        if (player.getItemInHand().getAmount() == 1) {
            player.setItemInHand(null);
        } else {
            player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
        }
        player.sendMessage(ChatColor.GOLD + L.m("Voce construiu a torre !"));
        return false;
    }
}
