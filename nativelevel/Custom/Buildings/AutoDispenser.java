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
import java.util.HashSet;
import nativelevel.MetaShit;
import nativelevel.KoM;
import nativelevel.sisteminhas.ClanLand;
import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class AutoDispenser {

    /*
    private final static double radius2 = 7.0 * 7.0;

    private String quemColocou;
    private Location location;
    private Entity alvo = null;
    private String tagDoClan = null;
    private int id = 0;

    private static int idCount = 0;
    public static HashSet<AutoDispenser> dispensersAtivos = new HashSet<AutoDispenser>();

    public static HashSet<AutoDispenser> paraAdicionar = new HashSet<AutoDispenser>();

    public AutoDispenser(String quemColocou, Location location, String tagDoClan) {
        super(new Vector(1, 3, 1), "AutoDispenser");
        this.location = location;
        this.tagDoClan = tagDoClan;
        this.id = idCount;
        idCount++;
        this.quemColocou = quemColocou;

    }

    public static void finaliza() {
        for (AutoDispenser disp : dispensersAtivos) {
            disp.destroi();
        }
    }

    public static void iniciaTask() {
        Runnable task = new Runnable() {
            public void run() {

                dispensersAtivos.addAll(paraAdicionar);
                paraAdicionar.clear();

                for (AutoDispenser disp : dispensersAtivos) {
                    disp.tick();
                }
            }

        };
        Bukkit.getScheduler().scheduleSyncRepeatingTask(KnightsOfMania._instance, task, 20 * 3, 20 * 3);
    }

    public static void removeDispenser(int id) {
        AutoDispenser aRemover = null;
        for (AutoDispenser disp : dispensersAtivos) {
            if (disp.id == id) {
                aRemover = disp;
                break;
            }

        }
        if (aRemover != null) {
            dispensersAtivos.remove(aRemover);
            aRemover.destroi();
        }
    }

    public void constroi(Location loc) {
        paraAdicionar.add(this);
        int x = loc.getBlockX();
        int z = loc.getBlockZ();
        Block up = loc.getBlock().getRelative(BlockFace.UP);
        loc.getBlock().setType(Material.IRON_BLOCK);
        up.setType(Material.DISPENSER);
        Block lamp = up.getRelative(BlockFace.UP);
        lamp.setType(Material.REDSTONE_BLOCK);
        Block placa = lamp.getRelative(BlockFace.UP);
        placa.setType(Material.SIGN_POST);
        Sign s = (Sign) placa.getState();
        blocks = new ArrayList<Block>();
        blocks.add(up);
        blocks.add(lamp);
        blocks.add(placa);
        blocks.add(loc.getBlock());
        s.setLine(0, "[Server]");
        s.setLine(1, "[Turret]");
        s.setLine(2, "Vida:50");
        s.setLine(3, "Id:" + this.id);
        s.update();
        this.location = placa.getLocation();
    }

    public void tick() {
        if (KnightsOfMania.debugMode) {
            KnightsOfMania.log.info("BUSCANDO ALVO PARA DISPENSER " + this.id);
        }

        if (alvo == null || !alvo.getWorld().getName().equalsIgnoreCase(location.getWorld().getName()) || alvo.getLocation().distanceSquared(location) >= radius2) {
            buscaNovoAlvo();
            if (KnightsOfMania.debugMode && alvo != null) {
                KnightsOfMania.log.info("ACHEI ALVO " + this.alvo.toString());
            }
        }
        if (alvo != null) {
            Location vemDaki = new Location(location.getWorld(), location.getBlockX(), location.getBlockY() + 2, location.getBlockZ());
            Arrow a = atiraFlechaEm(vemDaki, alvo, "autoDispenser");
            KnightsOfMania.log.info("ATIREI FLEXA " + this.alvo.toString());
        }
    }

    public static Arrow atiraFlechaEm(Location from, Entity to, String meta) {
        Location f = to.getLocation().add(0, 1, 0);
        Vector direction = f.toVector().subtract(from.toVector()).normalize();
        Arrow arrow = (Arrow) to.getWorld().spawnEntity(from.add(direction), EntityType.ARROW);
        arrow.setVelocity(direction.multiply(3f));
        arrow.setFireTicks(600);
        MetaShit.setMetaString("tipoTiro", arrow, meta);
        return arrow;
    }

    private void buscaNovoAlvo() {
        Player alvo = null;
        for (Player player : KnightsOfMania._instance.getServer().getOnlinePlayers()) {
            if (!player.getWorld().getName().equalsIgnoreCase(location.getWorld().getName())) {
                continue;
            }
            if (player.getLocation().distanceSquared(location) <= radius2) {
                if (tagDoClan != null) {
                    ClanPlayer cp = ClanLand.manager.getClanPlayer(player.getUniqueId());
                    if (cp != null) {
                        // n atira em manolo do mesmo clan
                        if (cp.getClan().isAlly(tagDoClan) || cp.getClan().getTag().equalsIgnoreCase(tagDoClan)) {
                            continue;
                        }
                        alvo = player;
                        break;
                    }
                }
            }
        }
        if (alvo != null) {
            this.alvo = alvo;
        }
    }
    */
}
