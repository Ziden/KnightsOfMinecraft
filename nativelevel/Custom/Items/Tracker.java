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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import nativelevel.Custom.CustomItem;
import nativelevel.Jobs;
import nativelevel.Lang.L;
import nativelevel.KoM;
import nativelevel.sisteminhas.ClanLand;
import nativelevel.Attributes.Mana;
import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;

public class Tracker extends CustomItem {

    public Tracker() {
        super(Material.COMPASS, L.m("Tracker"),L.m( "Localiza guilda mais proxima "), CustomItem.EPICO);
    }


    @Override
    public boolean onItemInteract(Player p) {
        if(Jobs.getJobLevel(L.get("Classes.Thief"),p)!=1) {
            p.sendMessage(ChatColor.RED+L.get("Classes.Thief.Only"));
            return true;
        }
        ItemMeta m = p.getItemInHand().getItemMeta();
        if(!m.getDisplayName().equalsIgnoreCase("Tracker")) {
            p.sendMessage(ChatColor.RED+L.get("TrackerUsed"));
            return true;
        }
        Clan nearest = null;
        double dist = 0;
        for(Clan c : ClanLand.manager.getClans()) {
            double distance = c.getHomeLocation().distance(p.getLocation());
            if(nearest==null || distance<dist) {
                nearest = c;
                dist = distance;
            }
        }
        if(nearest != null) {
            p.sendMessage(ChatColor.GREEN+L.get("TrackerOn"));
            
            
        }
        return true;
    }
    
}
