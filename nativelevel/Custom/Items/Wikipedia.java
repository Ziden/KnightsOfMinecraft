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
import nativelevel.Crafting.CraftCache;
import nativelevel.Crafting.CraftEvents;
import nativelevel.Crafting.Craftable;
import nativelevel.Harvesting.HarvestCache;
import nativelevel.Harvesting.Harvestable;
import nativelevel.Planting.PlantCache;
import nativelevel.Planting.Plantable;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Wikipedia extends CustomItem {

    public Wikipedia() {
        super(Material.BOOK, L.m("Wikipedia"), L.m("Aprenda sobre o mundo"), CustomItem.RARO);
    }

    @Override
    public boolean onItemInteract(Player player) {
        return false;
    }
}
