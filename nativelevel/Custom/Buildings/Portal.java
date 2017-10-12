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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import nativelevel.KoM;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class Portal extends Construcao {

    public Portal() {
        super(new Vector(1, 2, 1), "Portal");
    }
    public static HashMap<Block, Block> portais = new HashMap<Block, Block>();

    public List<Block> constroi(final Location inicio, final Location fim) {

        Construcao.save(inicio, tamanho);
        Construcao.save(fim, tamanho);
        
        if(!inicio.getWorld().getName().equalsIgnoreCase(fim.getWorld().getName())) {
            return null;
        }
        
        BlockState state = inicio.getWorld().getBlockAt(inicio).getState();
        state.setType(Material.PORTAL);
        state.update(true, false);

        BlockState state3 = inicio.getWorld().getBlockAt(inicio.getBlock().getRelative(BlockFace.UP).getLocation()).getState();
        state3.setType(Material.PORTAL);
        state3.update(true, false);
        
        BlockState state2 = inicio.getWorld().getBlockAt(fim).getState();
        state2.setType(Material.PORTAL);
        state2.update(true, false);

        BlockState state4 = inicio.getWorld().getBlockAt(fim.getBlock().getRelative(BlockFace.UP).getLocation()).getState();
        state4.setType(Material.PORTAL);
        state4.update(true, false);

        portais.put(inicio.getBlock(), fim.getBlock());
        portais.put(fim.getBlock(), inicio.getBlock());
        KoM.rewind.put(inicio.getBlock(), Material.AIR);
        KoM.rewind.put(inicio.getBlock().getRelative(BlockFace.UP), Material.AIR);
        KoM.rewind.put(fim.getBlock(), Material.AIR);
        KoM.rewind.put(fim.getBlock().getRelative(BlockFace.UP), Material.AIR);

        Runnable r = new Runnable() {
            public void run() {
                inicio.getBlock().setType(Material.AIR);
                inicio.getBlock().getRelative(BlockFace.UP).setType(Material.AIR);
                fim.getBlock().setType(Material.AIR);
                fim.getBlock().getRelative(BlockFace.UP).setType(Material.AIR);
                KoM.rewind.remove(inicio.getBlock());
                KoM.rewind.remove(inicio.getBlock().getRelative(BlockFace.UP));
                KoM.rewind.remove(fim.getBlock());
                KoM.rewind.remove(fim.getBlock().getRelative(BlockFace.UP));
                portais.remove(inicio.getBlock());
                portais.remove(fim.getBlock());
            }
        };
        
        for(Player p : Bukkit.getOnlinePlayers()) {
            if(p==null)
                continue;
            if(p.getLocation().distance(inicio) < 20 || p.getLocation().distance(fim) < 20) {
                p.sendMessage(ChatColor.GREEN+"Voce ouviu um portal se abrindo...");
                p.playSound(p.getLocation(), Sound.BLOCK_PORTAL_TRAVEL, 1,1);
            }
        }
        
        Bukkit.getScheduler().scheduleSyncDelayedTask(KoM._instance, r, 20*60);
           
        
        return null;

    }

    public destroiPortal getDestruidor(List<Block> blocos, Location l) {
        return new destroiPortal(blocos, l);
    }

    public class destroiPortal implements Runnable {

        List<Block> blocos;
        Location l;

        public destroiPortal(List<Block> blocos, Location la) {
            this.blocos = blocos;
            l = la;
        }

        @Override
        public void run() {

            for (Block b : blocos) {
                b.setType(Material.AIR);
            }
            Portal.portais.remove(l);
            Construcao.destroi(l, tamanho);
        }
    }
}
