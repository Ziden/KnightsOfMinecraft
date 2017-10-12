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
package nativelevel.Custom.Buildings;

import com.sk89q.worldedit.world.chunk.Chunk;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import nativelevel.sisteminhas.ClanLand;
import nativelevel.KoM;
import nativelevel.Lang.L;
import net.sacredlabyrinth.phaed.simpleclans.Clan;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

// tower \o/
public class Torre extends Construcao {

    public Torre() {
        super(new Vector(5, 15, 5), L.m("Torre"));
    }

    public void constroi(Location loc, Player quem, Clan clan) {

        Construcao.save(loc, tamanho);
        
        
        
        int iX = (int) loc.getBlockX();
        int iY = (int) loc.getBlockY();
        int iZ = (int) loc.getBlockZ();
        // base
        for (int x = (int) loc.getBlockX(); x < loc.getBlockX() + this.tamanho.getBlockX(); x++) {
            for (int z = (int) loc.getBlockZ(); z < loc.getBlockZ() + this.tamanho.getBlockZ(); z++) {
                Block aqui = Bukkit.getServer().getWorld(loc.getWorld().getName()).getBlockAt(x, (int) loc.getBlockY() + 0, z);
                Block emCima = Bukkit.getServer().getWorld(loc.getWorld().getName()).getBlockAt(x, (int) loc.getBlockY() + 10, z);
                Block noMeio = Bukkit.getServer().getWorld(loc.getWorld().getName()).getBlockAt(x, (int) loc.getBlockY() + 5, z);
                noMeio.setType(Material.WOOD);

                if (x == loc.getBlockX() + 5) {
                    continue;
                }
                if (z == loc.getBlockZ() + 5) {
                    continue;
                }
                if ((x == iX || x == iX + 4) && (z == iZ || z == iZ + 4)) {
                    Block f0 = Bukkit.getServer().getWorld(loc.getWorld().getName()).getBlockAt(x, (int) loc.getBlockY() + 11, z);
                    Block f1 = Bukkit.getServer().getWorld(loc.getWorld().getName()).getBlockAt(x, (int) loc.getBlockY() + 12, z);
                    Block f2 = Bukkit.getServer().getWorld(loc.getWorld().getName()).getBlockAt(x, (int) loc.getBlockY() + 13, z);
                   // blocks.add(f1);
                    // blocks.add(f2);
                    f1.setType(Material.FENCE);
                    f2.setType(Material.FENCE);
                    f0.setType(Material.FENCE);
                }

                Block teto = Bukkit.getServer().getWorld(loc.getWorld().getName()).getBlockAt(x, (int) loc.getBlockY() + 14, z);
                // blocks.add(teto);
                teto.setType(Material.WOOD);

                if (aqui.getType() == Material.AIR) {
                    //this.blocks.add(aqui);
                    aqui.setType(Material.WOOD);
                }
                // blocks.add(emCima);
                emCima.setType(Material.WOOD);
            }
        }

        // escada
        // colunas
        for (int y = (int) loc.getBlockY(); y < loc.getBlockY() + 10; y++) {
            Bukkit.getServer().getWorld(loc.getWorld().getName()).getBlockAt(iX + 0, y, iZ + 0).setType(Material.WOOD);
            Bukkit.getServer().getWorld(loc.getWorld().getName()).getBlockAt(iX + 4, y, iZ + 0).setType(Material.WOOD);
            Bukkit.getServer().getWorld(loc.getWorld().getName()).getBlockAt(iX + 0, y, iZ + 4).setType(Material.WOOD);
            Bukkit.getServer().getWorld(loc.getWorld().getName()).getBlockAt(iX + 4, y, iZ + 4).setType(Material.WOOD);
              //this.blocks.add(Bukkit.getServer().getWorld(loc.getWorld().getName()).getBlockAt(0, y, 0));
            //this.blocks.add(Bukkit.getServer().getWorld(loc.getWorld().getName()).getBlockAt(4, y, 0));
            //this.blocks.add(Bukkit.getServer().getWorld(loc.getWorld().getName()).getBlockAt(4, y, 4));
            //this.blocks.add(Bukkit.getServer().getWorld(loc.getWorld().getName()).getBlockAt(0, y, 4));
        }

        Location escada = loc.clone();
        escada.add(0, 0, 1);

        // escada
        for (int c = 0; c < 10; c++) {
            Bukkit.getServer().getWorld(loc.getWorld().getName()).getBlockAt(escada).setType(Material.WOOD);
            escada.add(0, 1, 0);
            if (c < 3) { // 0 1 2
                escada.add(1, 0, 0);
            } else if (c < 5) { // 3 4 
                escada.add(0, 0, 1);
            } else if (c < 7) { // 5 6
                escada.add(-1, 0, 0);
            } else { //7 8
                escada.add(0, 0, -1);
            }
            Block acima1 = Bukkit.getServer().getWorld(loc.getWorld().getName()).getBlockAt(escada).getRelative(BlockFace.UP);
            Block acima2 = acima1.getRelative(BlockFace.UP);
            Block acima3 = acima2.getRelative(BlockFace.UP);
            acima1.setType(Material.AIR);
            acima2.setType(Material.AIR);
            acima3.setType(Material.AIR);
        }

        // base
        Block obsidian = Bukkit.getServer().getWorld(loc.getWorld().getName()).getBlockAt(iX + 2, iY, iZ + 2);
        Block placa = Bukkit.getServer().getWorld(loc.getWorld().getName()).getBlockAt(iX + 2, iY + 1, iZ + 2);
        obsidian.setType(Material.WOOD);
        placa.setType(Material.SIGN_POST);
        Location spawnTorre = Bukkit.getServer().getWorld(loc.getWorld().getName()).getBlockAt(iX + 2, iY + 1, iZ + 2).getLocation();
        Sign s = (Sign) Bukkit.getServer().getWorld(loc.getWorld().getName()).getBlockAt(iX + 2, iY + 1, iZ + 2).getState();
        s.setLine(0, "[Server]");
        s.setLine(1, L.m("[Torre]"));
        s.setLine(2, clan.getTag());
       //if(Board.getFactionAt(new FLocation(quem.getLocation())).isWarZone())
        //    s.setLine(3,"10");
        //else
        s.setLine(3, L.m("Vida:100"));
        s.update();
        for(Entity e : quem.getNearbyEntities(200, 20, 200)) {
            if(e.getType()==EntityType.PLAYER) {
                ((Player)e).sendMessage(ChatColor.RED+L.m("[Perigo]")+ChatColor.GREEN+L.m("Voce escutou uma torre sendo construida nos arredores !"));
            }
        }
        KoM.database.setTower(clan.getTag(), spawnTorre);
    }
}
