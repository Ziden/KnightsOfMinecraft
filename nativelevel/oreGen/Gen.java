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
package nativelevel.oreGen;

import java.util.HashSet;
import java.util.Random;
import java.util.logging.Logger;
import nativelevel.Classes.Minerador;
import nativelevel.MetaShit;
import nativelevel.KoM;
import nativelevel.bencoes.TipoBless;
import nativelevel.spec.PlayerSpec;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.world.ChunkLoadEvent;

public class Gen {

    // TO CLEAN CHUNKS (Forevah)
    public static boolean cleanChunk = false;

    public static final Logger log = Logger.getLogger("Minecraft");
    public static HashSet<Material> ores = new HashSet<Material>();
    public static Gen instance = null;

    public void onEnable() {
        instance = this;
        ores.add(Material.GRAVEL);
        ores.add(Material.COAL_ORE);
        ores.add(Material.DIAMOND_ORE);
        ores.add(Material.REDSTONE_ORE);
        ores.add(Material.IRON_ORE);
        ores.add(Material.GOLD_ORE);
        ores.add(Material.LAPIS_ORE);
        ores.add(Material.EMERALD_ORE);
        ores.add(Material.GLOWSTONE);
        ores.add(Material.SOUL_SAND);
        ores.add(Material.QUARTZ_ORE);
        Gen.editBukkitMineralGeneration();
    }

    public boolean isHidden(Block b) {
        if (b.getY() == 0) {
            return false;
        }
        if (b.getType() != Material.STONE && b.getType() != Material.DIRT && b.getType() != Material.SAND && b.getType() != Material.SANDSTONE && b.getType() != Material.GRAVEL && b.getType() != Material.AIR) {
            return false;
        }
        if (b.getRelative(BlockFace.UP).getType() == Material.AIR) {
            return false;
        }
        if (b.getRelative(BlockFace.NORTH).getType() == Material.AIR) {
            return false;
        }
        if (b.getRelative(BlockFace.SOUTH).getType() == Material.AIR) {
            return false;
        }
        if (b.getRelative(BlockFace.WEST).getType() == Material.AIR) {
            return false;
        }
        if (b.getRelative(BlockFace.EAST).getType() == Material.AIR) {
            return false;
        }
        if (b.getRelative(BlockFace.DOWN).getType() == Material.AIR) {
            return false;
        }
        return true;
    }
    BlockFace[] faces = {BlockFace.UP, BlockFace.DOWN, BlockFace.EAST, BlockFace.WEST, BlockFace.NORTH, BlockFace.SOUTH};
    Random rnd = new Random();

    public int getOreSize(Material ore) {
        int s = 0;
        if (ore == Material.COAL_ORE) {
            return rnd.nextInt(6) + 2;
        }
        if (ore == Material.IRON_ORE) {
            return rnd.nextInt(7) + 3;
        }
        if (ore == Material.GOLD_ORE) {
            return rnd.nextInt(4) + 3;
        }
        if (ore == Material.LAPIS_ORE) {
            return rnd.nextInt(6) + 1;
        }
        if (ore == Material.DIAMOND_ORE) {
            return rnd.nextInt(4) + 1;
        }
        if (ore == Material.REDSTONE_ORE) {
            return rnd.nextInt(4) + 1;
        }
        if (ore == Material.EMERALD_ORE) {
            return rnd.nextInt(7) + 1;
        }
        if (ore == Material.GLOWSTONE) {
            return rnd.nextInt(2) + 1;
        }
        if (ore == Material.SOUL_SAND) {
            return rnd.nextInt(2) + 1;
        }
        if (ore == Material.QUARTZ_ORE) {
            return rnd.nextInt(3) + 1;
        }
        if (ore == Material.GRAVEL) {
            return rnd.nextInt(3) + 3;
        }
        return s;
    }

    // pra limpar mapas se nao precisar só comentar o conteúdo
    // pode deixar se quiser, editei pra nao interferir com o komklaim
    public void load(ChunkLoadEvent ev) {
        /*
         if (!ev.isNewChunk()) {
         if (ev.getChunk().getBlock(1, 0, 0).getType() != Material.DIAMOND_BLOCK) {
         if(NativeLevel.debugMode)
         NativeLevel.log.info("cleaning chunk "+ev.getChunk().toString());
         ev.getChunk().getBlock(1, 0, 0).setType(Material.DIAMOND_BLOCK);
         ev.getChunk().getBlock(1, 1, 0).setType(Material.BEDROCK);
         for (int x = 0; x < 16; x++) {
         for (int y = 0; y < 150; y++) {
         for (int z = 0; z < 16; z++) {
         if (ores.contains(ev.getChunk().getBlock(x, y, z).getType())) {
         ev.getChunk().getBlock(x, y, z).setType(Material.STONE);
         }
         }
         }
         }
         }
         }  
         * 
         */
    }

    public static void editBukkitMineralGeneration() {
        /*
         try {
         BiomeBase [] biomesList = (BiomeBase[])Reflector.getStaticValue("net.minecraft.server.v1_6_R3.BiomeBase", "biomes");  
         for(BiomeBase b : biomesList) {
         if(b==null) continue;
         NativeLevel.log.info("refletindo "+b.toString());
         BiomeDecorator deco = b.I;
         try {
         Reflector.setInstanceValue(deco, "k", new WorldGenMinable(net.minecraft.server.v1_7_R3.Block..id, 16));
         Reflector.setInstanceValue(deco, "l", new WorldGenMinable(net.minecraft.server.v1_7_R3.Block.STONE.id, 16));
         Reflector.setInstanceValue(deco, "m", new WorldGenMinable(net.minecraft.server.v1_7_R3.Block.STONE.id, 16));
         Reflector.setInstanceValue(deco, "n", new WorldGenMinable(net.minecraft.server.v1_7_R3.Block.STONE.id, 16));
         Reflector.setInstanceValue(deco, "o", new WorldGenMinable(net.minecraft.server.v1_7_R3.Block.STONE.id, 16));
         Reflector.setInstanceValue(deco, "p", new WorldGenMinable(net.minecraft.server.v1_7_R3.Block.STONE.id, 16));
         } catch(Exception e) {
         NativeLevel.log.info("biome "+b.toString()+" naum tomou patch !");
         }
         }
         } catch(Exception e) {
         NativeLevel.log.info("Error reflecting BIOME DATA !!!!");
         NativeLevel.log.info(e.getMessage());
         e.printStackTrace();
         NativeLevel.log.info("Error reflecting BIOME DATA !!!!");
            
         }
         */
    }

    public Material getOre(int y, Player p) {

        int chance = rnd.nextInt(10100);

        TipoBless ativo = TipoBless.save.getTipo(p);
        if (ativo != null && ativo == TipoBless.Mineracao) {
            chance = rnd.nextInt(8000);
        }

        if (KoM.debugMode && p.isOp()) {
            p.sendMessage("Mining Luck: " + chance);
        }
        if (y > 50 && y < 150) {
            if (chance > 0 && chance < 450) {
                return Material.COAL_ORE;
            } else if (chance > 360 && chance < 400) {
                return Material.IRON_ORE;
            } else if (chance > 500 && chance < 880) {
                return Material.GRAVEL;
            }
        } else if (y < 50 && y > 20) {
            if (chance > 0 && chance < 150) {
                return Material.COAL_ORE;
            } else if (chance > 300 && chance < 365) {
                return Material.IRON_ORE;
            } else if (chance > 600 && chance < 620) {
                return Material.GOLD_ORE;
            } else if (chance > 700 && chance < 720) {
                return Material.REDSTONE_ORE;
            }  else if (chance > 800 && chance < 1200) {
                return Material.GRAVEL;
            }
        } else if (y < 20) {
            if (chance > 000 && chance < 100) {
                return Material.COAL_ORE;
            } else if (chance > 300 && chance < 345) {
                return Material.IRON_ORE;
            } else if (chance > 600 && chance < 629) {
                return Material.GOLD_ORE;
            } else if (chance > 700 && chance < 705) {
                return Material.REDSTONE_ORE;
            } else if (chance > 1000 && chance < 1005) {
                return Material.LAPIS_ORE;
            } else if (chance > 800 && chance < 802) {
                return Material.DIAMOND_ORE;
            } else if (chance > 1100 && chance < 1130) {
                return Material.EMERALD_ORE;
            } else if (chance > 1300 && chance < 1301) {
                return Material.GLOWSTONE;
            } else if (chance > 1300 && chance < 1315) {
                return Material.SOUL_SAND;
            } else if (chance > 1400 && chance < 1415) {
                return Material.QUARTZ_ORE;
            } 
        }
        return null;
    }

    public HashSet<Block> getNearBlocks(Location l) {

        Block b = l.getWorld().getBlockAt(l);
        HashSet<Integer> used = new HashSet<Integer>();
        HashSet<Block> near = new HashSet<Block>();

        for (int x = 0; x < faces.length; x++) {
            int random = rnd.nextInt(faces.length);
            if (!used.contains(random)) {
                if (!ores.contains(b.getRelative(faces[random]).getType())) {
                    near.add(b.getRelative(faces[random]));
                }
                used.add(random);
            } else {
                while (true) {
                    random++;
                    if (random >= faces.length) {
                        random = 0;
                    }
                    if (!used.contains(random)) {
                        if (!ores.contains(b.getRelative(faces[random]).getType())) {
                            near.add(b.getRelative(faces[random]));
                        }
                        used.add(random);
                        break;
                    }
                }
            }
        }
        return near;
    }

    public void loga(PlayerJoinEvent ev) {
        if (ev.getPlayer().hasMetadata("minerou")) {
            ev.getPlayer().removeMetadata("minerou", KoM._instance);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void breakBlock(BlockBreakEvent ev) {
        if (ev.getBlock().getType() == Material.STONE) {

            if (ev.getBlock().hasMetadata("playerpois")) {
                return;
            }

            Minerador.axaItems(ev);
            String minedIn = MetaShit.getMetaString("MinedIn", ev.getPlayer());
            if (minedIn != null) {
                long mined = Long.valueOf(minedIn);
                if (System.currentTimeMillis() < mined + 500) {
                    if (KoM.debugMode) {
                        KoM.log.info("CANT MINE YET");
                    }
                    return;
                }
            }
            Material thisOre = this.getOre(ev.getPlayer().getLocation().getBlockY(), ev.getPlayer());
            if (thisOre == null && PlayerSpec.temSpec(ev.getPlayer(), PlayerSpec.Explorador)) {
                thisOre = this.getOre(ev.getPlayer().getLocation().getBlockY(), ev.getPlayer());
            }
            if (thisOre != null) {
                HashSet<Block> near = getNearBlocks(ev.getBlock().getLocation());
                for (Block b : near) {
                    if (isHidden(b)) {
                        b.setType(thisOre);
                        if (KoM.debugMode && ev.getPlayer().isOp()) {
                            ev.getPlayer().sendMessage("Generated Ore: " + thisOre.name());
                        }
                        MetaShit.setMetaString("MinedIn", ev.getPlayer(), String.valueOf(System.currentTimeMillis()));
                        geraVeioDeMinerio(b, thisOre, this.getOreSize(thisOre) - 1, ev.getPlayer());
                        break;
                    }
                }
            } else {
                if (ev.getPlayer().isSneaking() && ev.getPlayer().getEyeLocation().getPitch() == -90 && ev.getPlayer().getItemInHand() != null && ev.getPlayer().getItemInHand().getType() == Material.APPLE) {
                    checaChunk(ev.getPlayer());
                }
            }
        }
    }

    public void checaChunk(Entity p) {
        for (Chunk c : p.getWorld().getLoadedChunks()) {
            for (Entity e : c.getEntities()) {
                checaChunk(e);
            }
        }
    }

    public void geraVeioDeMinerio(Block b, Material ore, int count, Player p) {
        HashSet<Block> near = getNearBlocks(b.getLocation());
        for (Block block : near) {
            if (isHidden(block)) {
                block.setType(ore);
                count--;
                if (count > 0) {
                    geraVeioDeMinerio(block, ore, count, p);
                }
                break;
            }
        }
    }
}
