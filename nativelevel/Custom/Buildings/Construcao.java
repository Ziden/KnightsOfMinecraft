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

import org.bukkit.Chunk;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

public abstract class Construcao {

    protected Vector tamanho;
    protected ArrayList<Block> blocks;
    protected String name;

    private static HashSet<Chunk> construido = new HashSet<Chunk>();

    public static boolean chunkConstruido(Chunk c) {
        return construido.contains(c);
    }
    
    public static void save(Location inicio, Vector tamanho) {
        Chunk l1 = inicio.getChunk();
        Chunk l2 = inicio.add(tamanho).getChunk();
        Chunk l3 = inicio.add(tamanho.getX(), 0, 0).getChunk();
        Chunk l4 = inicio.add(0, 0, tamanho.getZ()).getChunk();
        construido.add(l1);
        construido.add(l2);
        construido.add(l3);
        construido.add(l4);
    }

    public static void destroi(Location inicio, Vector tamanho) {
        Chunk l1 = inicio.getChunk();
        Chunk l2 = inicio.add(tamanho).getChunk();
        Chunk l3 = inicio.add(tamanho.getX(), 0, 0).getChunk();
        Chunk l4 = inicio.add(0, 0, tamanho.getZ()).getChunk();
        construido.remove(l1);
        construido.remove(l2);
        construido.remove(l3);
        construido.remove(l4);
    }

    public Construcao(Vector tam, String nome) {
        tamanho = tam;
        name = nome;
    }

    public void destroi() {
        for (Block b : blocks) {
            b.setType(Material.AIR);
            try {
                this.finalize();
            } catch (Throwable ex) {
                Logger.getLogger(Construcao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public boolean podeConstruir(Location loc) {
        for (int x = (int) loc.getX(); x < (int) loc.getX() + tamanho.getX(); x++) {
            for (int y = (int) loc.getY(); y < (int) loc.getY() + tamanho.getY(); y++) {
                for (int z = (int) loc.getZ(); z < (int) loc.getZ() + tamanho.getZ(); z++) {
                    if (Bukkit.getServer().getWorld(loc.getWorld().getName()).getBlockAt(x, y, z).getType() != Material.AIR) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

}
