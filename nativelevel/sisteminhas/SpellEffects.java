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
package nativelevel.sisteminhas;

import nativelevel.efeitos.SpellAnimation;
import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import nativelevel.Custom.Items.Armadilha;
import nativelevel.KoM;
import nativelevel.Lang.L;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.BlockIterator;

public class SpellEffects {

    public static HashMap<String, Runnable> paralized = new HashMap<String, Runnable>();
    public static List<Block> paralizeBlocks = new ArrayList<Block>();
    public static HashSet<UUID> paralizeCasts = new HashSet<UUID>();
    public static HashSet<UUID> bombCasts = new HashSet<UUID>();

    public void geyser(List<LivingEntity> targets, float power) {
        List<Player> playersNearby = new ArrayList<Player>();
        if (targets.size() > 0) {
            for (Entity e : targets.get(0).getNearbyEntities(50.0D, 50.0D, 50.0D)) {
                if ((e instanceof Player)) {
                    playersNearby.add((Player) e);
                }
            }
        }
        boolean sentPackets = false;
        for (LivingEntity target : targets) {
            target.setVelocity(new Vector(0.0D, 1.3f, 0.0D));
            target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 300, 3));
            new GeyserAnimation(target.getLocation(), playersNearby);
        }
    }

    public static void webUp(Player p) {
        final Block web = p.getWorld().getBlockAt(p.getLocation());
        if (web.getType() == Material.AIR) {
            p.sendMessage(ChatColor.AQUA + L.m("Voce foi preso em uma teia !"));
            web.setType(Material.WEB);
            Runnable webR = new Runnable() {

                @Override
                public void run() {
                    if (web.getType() == Material.WEB) {
                        web.setType(Material.AIR);
                    }
                }
            };
            Bukkit.getScheduler().scheduleSyncDelayedTask(KoM._instance, webR, 20 * 3);
        }
    }

    private static void teleport(Player player, Location location, HashSet<Location> smokeLocs) {
        player.teleport(location);
        for (Location l : smokeLocs) {
            l.getWorld().playEffect(l, Effect.SMOKE, 4);
        }
    }

    public static boolean isPathable(Block b) {
        Material material = b.getType();
        return (material == Material.AIR) || (material == Material.SAPLING) || (material == Material.WATER) || (material == Material.STATIONARY_WATER) || (material == Material.POWERED_RAIL) || (material == Material.DETECTOR_RAIL) || (material == Material.LONG_GRASS) || (material == Material.DEAD_BUSH) || (material == Material.YELLOW_FLOWER) || (material == Material.RED_ROSE) || (material == Material.BROWN_MUSHROOM) || (material == Material.RED_MUSHROOM) || (material == Material.TORCH) || (material == Material.FIRE) || (material == Material.REDSTONE_WIRE) || (material == Material.CROPS) || (material == Material.SIGN_POST) || (material == Material.LADDER) || (material == Material.RAILS) || (material == Material.WALL_SIGN) || (material == Material.LEVER) || (material == Material.STONE_PLATE) || (material == Material.WOOD_PLATE) || (material == Material.REDSTONE_TORCH_OFF) || (material == Material.REDSTONE_TORCH_ON) || (material == Material.STONE_BUTTON) || (material == Material.SNOW) || (material == Material.SUGAR_CANE_BLOCK) || (material == Material.VINE) || (material == Material.WATER_LILY) || (material == Material.NETHER_STALK);
    }
    public static HashSet<Byte> losTransparentBlocks = new HashSet<Byte>();

    public static HashSet<Byte> getTransparentBlocks() {
        if (losTransparentBlocks.size() == 0) {
            losTransparentBlocks.add((byte) Material.AIR.getId());
            losTransparentBlocks.add((byte) Material.TORCH.getId());
            losTransparentBlocks.add((byte) Material.REDSTONE_WIRE.getId());
            losTransparentBlocks.add((byte) Material.REDSTONE_TORCH_ON.getId());
            losTransparentBlocks.add((byte) Material.REDSTONE_TORCH_OFF.getId());
            losTransparentBlocks.add((byte) Material.YELLOW_FLOWER.getId());
            losTransparentBlocks.add((byte) Material.RED_ROSE.getId());
            losTransparentBlocks.add((byte) Material.BROWN_MUSHROOM.getId());
            losTransparentBlocks.add((byte) Material.RED_MUSHROOM.getId());
            losTransparentBlocks.add((byte) Material.LONG_GRASS.getId());
            losTransparentBlocks.add((byte) Material.DEAD_BUSH.getId());
            losTransparentBlocks.add((byte) Material.DIODE_BLOCK_ON.getId());
            losTransparentBlocks.add((byte) Material.DIODE_BLOCK_OFF.getId());
        }
        return losTransparentBlocks;
    }

    public static boolean inRange(Location loc1, Location loc2, int range) {
        return loc1.distanceSquared(loc2) < range * range;
    }

    public static boolean blink(Player player) {
        int range = Math.round(20);
        if (range <= 0) {
            range = 25;
        }
        if (range > 125) {
            range = 125;
        }
        BlockIterator iter;
        try {
            iter = new BlockIterator(player, range > 0 && range < 150 ? range : 150);
        } catch (IllegalStateException e) {
            iter = null;
        }
        HashSet<Location> smokes = null;
        smokes = new HashSet<Location>();
        Block prev = null;
        Block found = null;
        Block b;
        if (iter != null) {
            while (iter.hasNext()) {
                b = iter.next();
                if (getTransparentBlocks().contains((byte) b.getTypeId())) {
                    prev = b;
                    smokes.add(b.getLocation());
                } else {
                    found = b;
                    break;
                }
            }
        }

        if (found != null) {
            Location loc = null;
            if (range > 0 && !inRange(found.getLocation(), player.getLocation(), range)) {
            } else if (found.getRelative(0, -1, 0).equals(prev)) {
                // trying to move upward
                if (isPathable(prev) && isPathable(prev.getRelative(0, -1, 0))) {
                    loc = prev.getRelative(0, -1, 0).getLocation();
                }
            } else if (isPathable(found.getRelative(0, 1, 0)) && isPathable(found.getRelative(0, 2, 0))) {
                // try to stand on top
                loc = found.getLocation();
                loc.setY(loc.getY() + 1);
            } else if (prev != null && isPathable(prev) && isPathable(prev.getRelative(0, 1, 0))) {
                // no space on top, put adjacent instead
                loc = prev.getLocation();
            }
            if (loc != null) {
                loc.setX(loc.getX() + .5);
                loc.setZ(loc.getZ() + .5);
                loc.setPitch(player.getLocation().getPitch());
                loc.setYaw(player.getLocation().getYaw());
                teleport(player, loc, smokes);
                return true;
            } else {
                //player.sendMessage("* alvo invalido *");
            }
        } else {
            //player.sendMessage("* alvo invalido *");
        }
        return false;
    }

    public class GeyserAnimation extends SpellAnimation {

        private Location start;
        private List<Player> nearby;

        public GeyserAnimation(Location start, List<Player> nearby) {
            super(0, 2, true);
            this.start = start;
            this.nearby = nearby;
        }

        @Override
        protected void onTick(int tick) {
            if (tick > 4 * 2) {
                stop();
            } else if (tick < 4) {
                Block block = start.clone().add(0, tick, 0).getBlock();
                if (block.getType() == Material.AIR) {
                    for (Player p : nearby) {
                        p.sendBlockChange(block.getLocation(), Material.STATIONARY_WATER, (byte) 0);
                    }
                }
            } else {
                int n = 4 - (tick - 4) - 1; // top to bottom
                //int n = tick-height; // bottom to top
                Block block = start.clone().add(0, n, 0).getBlock();
                for (Player p : nearby) {
                    p.sendBlockChange(block.getLocation(), block.getType(), block.getData());
                }
            }
        }
    }

    public static void cleanMember(final HashSet<UUID> list, final UUID id) {
        Runnable r = new Runnable() {

            @Override
            public void run() {
                if (list.contains(id)) {
                    list.remove(id);
                }
            }
        };
        Bukkit.getScheduler().scheduleSyncDelayedTask(KoM._instance, r, 20 * 15);
    }

    public void fireRing(Entity l) {
        new FirenovaAnimation(l);
    }

    public class FirenovaAnimation implements Runnable {

        Entity player;
        int i;
        Block center;
        HashSet<Block> fireBlocks;
        int taskId;

        public FirenovaAnimation(Entity player) {
            this.player = player;
            player.getWorld().createExplosion(player.getLocation(), 0);
            this.i = 0;
            this.center = player.getLocation().getBlock();
            this.fireBlocks = new HashSet();

            this.taskId = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(KoM._instance, this, 0L, 5);
        }

        public void run() {
            for (Block block : this.fireBlocks) {
                if (block.getType() == Material.FIRE) {
                    byte b = 0;
                    block.setTypeIdAndData(0, b, false);
                }
            }
            this.fireBlocks.clear();

            this.i += 1;
            if (this.i <= 5) {
                byte byt = 15;
                int bx = this.center.getX();
                int y = this.center.getY();
                int bz = this.center.getZ();
                for (int x = bx - this.i; x <= bx + this.i; x++) {
                    for (int z = bz - this.i; z <= bz + this.i; z++) {
                        if ((Math.abs(x - bx) == this.i) || (Math.abs(z - bz) == this.i)) {
                            Block b = this.center.getWorld().getBlockAt(x, y, z);
                            if ((b.getType() == Material.AIR) || ((b.getType() == Material.LONG_GRASS))) {
                                Block under = b.getRelative(BlockFace.DOWN);
                                if ((under.getType() == Material.AIR) || ((under.getType() == Material.LONG_GRASS))) {
                                    b = under;
                                }

                                b.setTypeIdAndData(Material.FIRE.getId(), byt, false);
                                this.fireBlocks.add(b);
                            } else if ((b.getRelative(BlockFace.UP).getType() == Material.AIR) || ((b.getRelative(BlockFace.UP).getType() == Material.LONG_GRASS))) {
                                b = b.getRelative(BlockFace.UP);
                                b.setTypeIdAndData(Material.FIRE.getId(), byt, false);
                                this.fireBlocks.add(b);
                            }
                        }
                    }
                }
            } else if (this.i > 5 + 1) {
                Bukkit.getServer().getScheduler().cancelTask(this.taskId);
                // Mago.this.fireImmunity.remove(this.player);
            }
        }
    }

    public static void removeParalize(final ArrayList<Block> createdBlocks, final String paraName) {
        Runnable cleanParalize = new Runnable() {

            @Override
            public void run() {
                if (paraName.equalsIgnoreCase("MOB")) {
                    for (Block b : createdBlocks) {
                        b.setType(Material.AIR);
                    }
                } else if (paralized.containsKey(paraName)) {
                    paralized.remove(paraName);
                    Player t = Bukkit.getPlayer(paraName);
                    paralizeBlocks.removeAll(createdBlocks);
                    if (t != null) {
                        t.sendMessage(ChatColor.AQUA + L.m("* se livrou da paralizia *"));
                    }
                    for (Block b : createdBlocks) {
                        b.setType(Material.AIR);
                    }
                }
            }
        };
        if (!paralized.containsKey(paraName)) {
            paralized.put(paraName, cleanParalize);
            Bukkit.getScheduler().scheduleSyncDelayedTask(KoM._instance, cleanParalize, 20 * 10);
        }
    }

    public void paralize(Player caster, LivingEntity target) {

        final Location loc = new Location(target.getWorld(), target.getLocation().getBlockX(), target.getLocation().getBlockY(), target.getLocation().getBlockZ());
        final ArrayList<Block> createdBlocks = new ArrayList<Block>();
        Block b = caster.getWorld().getBlockAt(loc);
        Location tpLoc = loc.clone();
        tpLoc.add(new Vector(0.5f, 0, 0.5f));
        target.teleport(tpLoc);
        target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 4, 2));
        b.setType(Material.WEB);
        createdBlocks.add(b);

        if (target instanceof Player) {
            Block leaveW;
            Block leaveE;
            Block leaveN;
            Block leaveS;

            if (b.getRelative(BlockFace.EAST).getType() == Material.AIR) {
                leaveE = b.getRelative(BlockFace.EAST);
                leaveE.setType(Material.LEAVES);
                createdBlocks.add(leaveE);
            }
            if (b.getRelative(BlockFace.SOUTH).getType() == Material.AIR) {
                leaveS = b.getRelative(BlockFace.SOUTH);
                leaveS.setType(Material.LEAVES);
                createdBlocks.add(leaveS);
            }
            if (b.getRelative(BlockFace.NORTH).getType() == Material.AIR) {
                leaveN = b.getRelative(BlockFace.NORTH);
                leaveN.setType(Material.LEAVES);
                createdBlocks.add(leaveN);
            }
            if (b.getRelative(BlockFace.WEST).getType() == Material.AIR) {
                leaveW = b.getRelative(BlockFace.WEST);
                leaveW.setType(Material.LEAVES);
                createdBlocks.add(leaveW);
            }
            caster.sendMessage(ChatColor.AQUA + L.m("* paralizou %" , ((Player) target).getName()));
            final String paraName = ((Player) target).getName();
            paralizeBlocks.addAll(createdBlocks);
            removeParalize(createdBlocks, paraName);

        } else {
            removeParalize(createdBlocks, "MOB");
            caster.sendMessage(ChatColor.AQUA + L.m("paralizou %",target.toString()));
        }
    }
    public HashSet<Block> paredesCriadas = new HashSet<Block>();

    public void fazParede(final Player player) {
        Block target = player.getTargetBlock(Armadilha.m, 5);
        if ((target == null) || (target.getType() != Material.AIR)) {
            return;
        }
        ArrayList<Block> criados = new ArrayList<Block>();
        Location loc = target.getLocation();
        Vector dir = player.getLocation().getDirection();
        int wallWidth = Math.round(5);
        int wallHeight = Math.round(3);
        Block feet = target.getLocation().getBlock();
        Block temp = feet.getRelative(1, 0, 0);
        if (Math.abs(dir.getX()) > Math.abs(dir.getZ())) {
            for (int z = loc.getBlockZ() - wallWidth / 2; z <= loc.getBlockZ() + wallWidth / 2; z++) {
                for (int y = loc.getBlockY() - 1; y < loc.getBlockY() + wallHeight - 1; y++) {
                    if (player.getWorld().getBlockAt(target.getX(), y, z).getType() == Material.AIR) {
                        player.getWorld().getBlockAt(target.getX(), y, z).setType(Material.GLASS);
                        temp = player.getWorld().getBlockAt(target.getX(), y, z);
                        criados.add(temp);
                    }
                }
            }
        } else {
            for (int x = loc.getBlockX() - wallWidth / 2; x <= loc.getBlockX() + wallWidth / 2; x++) {
                for (int y = loc.getBlockY() - 1; y < loc.getBlockY() + wallHeight - 1; y++) {
                    if (player.getWorld().getBlockAt(x, y, target.getZ()).getType() == Material.AIR) {
                        player.getWorld().getBlockAt(x, y, target.getZ()).setType(Material.GLASS);
                        temp = player.getWorld().getBlockAt(x, y, target.getZ());
                        criados.add(temp);
                    }
                }
            }
        }
        paredesCriadas.addAll(criados);
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(KoM._instance, new LimpaMuro(criados, player), 20 * 6 - 10);
    }

    public boolean protegeBloco(Block b) {
        if (b.getType() == Material.GLASS) {
            if (paredesCriadas.contains(b)) {
                return true;
            }
        }

        if (b.getType() == Material.WEB || b.getType() == Material.LEAVES) {
            if (paralizeBlocks.contains(b)) {
                return true;
            }
        }
        return false;
    }

    public class LimpaMuro implements Runnable {

        ArrayList<Block> teias;
        Player p;

        public LimpaMuro(ArrayList<Block> teeeeia, Player p) {
            this.teias = teeeeia;
            this.p = p;
        }

        public void run() {
            if (p != null) {
                p.removeMetadata("wall", KoM._instance);
            }
            for (Block block : this.teias) {
                if (block.getType() == Material.GLASS) {
                    block.setType(Material.AIR);
                }
            }
            SpellEffects.this.paredesCriadas.removeAll(this.teias);
        }
    }
}
