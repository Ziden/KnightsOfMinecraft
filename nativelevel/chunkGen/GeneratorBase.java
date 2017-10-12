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
package nativelevel.chunkGen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

public class GeneratorBase extends ChunkGenerator {

    void setBlock(int x, int y, int z, byte[][] chunk, Material material) {
        if (y < 256 && y >= 0 && x <= 16 && x >= 0 && z <= 16 && z >= 0) {
            if (chunk[y >> 4] == null) {
                chunk[y >> 4] = new byte[16 * 16 * 16];
            }
            chunk[y >> 4][((y & 0xF) << 8) | (z << 4) | x] = (byte) material.getId();
        }
    }

    @Override
    public byte[][] generateBlockSections(World world, Random rand, int ChunkX,
            int ChunkZ, BiomeGrid biome) {
        byte[][] chunk = new byte[world.getMaxHeight() / 16][];
        for (int x = 0; x < 16; x++) { //loop through all of the blocks in the chunk that are lower than maxHeight
            for (int z = 0; z < 16; z++) {
                int maxHeight = 64; //how thick we want out flat terrain to be
                for (int y = 0; y < maxHeight; y++) {
                    setBlock(x, y, z, chunk, Material.STONE); //set the current block to stone
                }
            }
        }
        return chunk;
    }

    @Override
    public List<BlockPopulator> getDefaultPopulators(World world) {
        ArrayList<BlockPopulator> pops = new ArrayList<BlockPopulator>();
        //Add Block populators here
        return pops;
    }
}
