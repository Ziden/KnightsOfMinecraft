/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nativelevel.ArenaGuilda2x2;

import java.util.ArrayList;
import java.util.List;
import nativelevel.ArenaGuilda2x2.Arena2x2;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

/**
 *
 * @author usuario
 * 
 */

public class ArenaManager {

    public static void criaNovaArena(Location l, String nome) {
         l=  new Location(l.getWorld(), l.getX(), l.getY()-1, l.getZ());
         Location spawn1 = new Location(l.getWorld(), l.getX()+Arena2x2.tamanhoArena, l.getY(), l.getZ());
         Location spawn2 = new Location(l.getWorld(), l.getX()-Arena2x2.tamanhoArena, l.getY(), l.getZ());
         spawn1.getBlock().setType(Material.STAINED_CLAY);
         spawn1.getBlock().setData((byte)14);
         spawn2.getBlock().setType(Material.STAINED_GLASS);
         spawn2.getBlock().setData((byte)14);
         l.getBlock().setType(Material.GLOWSTONE);
         Arena2x2.sql.criaArena(l.getBlockX(), l.getBlockY()+1, l.getBlockZ(), nome);
    }
    
    public static void saiDaFila(Player p) {
         Arena2x2.sql.saiDaFila(p.getUniqueId());
    }
}
