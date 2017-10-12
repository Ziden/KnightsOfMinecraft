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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;
import me.fromgate.playeffect.PlayEffect;
import me.fromgate.playeffect.VisualEffect;
import nativelevel.Custom.CustomItem;
import nativelevel.Jobs;
import nativelevel.KoM;
import nativelevel.sisteminhas.ClanLand;
import nativelevel.Lang.L;
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

public class SuperBomba extends CustomItem {

    public SuperBomba() {
        super(Material.TNT, L.m("Mega Bomba C4"), L.m("Faz um santo estrago "), CustomItem.EPICO);
    }

    public static List<Material> podemExplodir = Arrays.asList(new Material[]{
        Material.BRICK, Material.CLAY_BRICK, Material.FENCE, Material.SANDSTONE_STAIRS, Material.COBBLESTONE_STAIRS, Material.COBBLE_WALL, Material.BRICK_STAIRS, Material.MOSSY_COBBLESTONE, Material.TNT, Material.DIRT, Material.SANDSTONE, Material.SAND, Material.GRAVEL, Material.GRASS, Material.COBBLESTONE, Material.STONE, Material.FENCE, Material.BRICK, Material.WOOD, Material.LOG, Material.GLASS
    });

    private static HashMap<String, Long> cooldownDasGuildas = new HashMap<String,Long>();
    
    public static List<Block> getNearbyBlocks(Location location, int radius) {
        List<Block> blocks = new ArrayList<Block>();
        for(int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
            for(int y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++) {
                for(int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
                   blocks.add(location.getWorld().getBlockAt(x, y, z));
                }
            }
        }
        return blocks;
    }
    
    @Override
    public boolean onItemInteract(Player p) {
       
        Block colocando = p.getTargetBlock(Armadilha.m, 10);
        if(colocando == null) {
            p.sendMessage(ChatColor.RED+L.m("Selecione um bloco válido para colocar a bomba !"));
            return true;
        }
        
        Clan inimigo = null;
        if (!podemExplodir.contains(colocando.getType())) {
            if(KoM.debugMode) p.sendMessage("tentei em "+colocando.getType().toString());
            p.sendMessage(ChatColor.RED +L.m( "Voce apenas pode colocar a super bomba em terras/pedras/vidros/madeiras"));
            return true;
        }
        
        if(colocando.getRelative(BlockFace.UP).getType()==Material.AIR) {
            colocando = colocando.getRelative(BlockFace.UP);
        } else {
            p.sendMessage(ChatColor.RED+L.m("O bloco em cima do bloco selecionado precisa estar vazio !"));
            return true;
        }
        
        ClanPlayer cp = ClanLand.manager.getClanPlayer(p);
        if (cp == null) {
            p.sendMessage(ChatColor.RED + L.m("Voce precisa de uma guilda !"));
            return true;
        }
        
        long tempo = TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis());//(int) ((System.currentTimeMillis() / (1000*60)) % 60);
        if(cooldownDasGuildas.containsKey(cp.getTag())) {
            long cd = cooldownDasGuildas.get(cp.getTag());
            if(cd+10 > tempo) {
                p.sendMessage(L.m("Sua guilda precisa aguardar 10 minutos para usar mais bombas !"));
            }
        }
        
        if (Jobs.getJobLevel("Engenheiro", p) != 1) {
            p.sendMessage(L.m("Apenas bons engenheiros podem usar isto !"));
            return true;
        }
        String type = ClanLand.getTypeAt(colocando.getLocation());
       
        if (type.equalsIgnoreCase("WILD")) {

            Location loc = colocando.getLocation().add(1, 0, 0);
            if (ClanLand.getTypeAt(loc).equalsIgnoreCase("CLAN")) {
                Clan c = ClanLand.getClanAt(loc);
                if (!c.isRival(cp.getTag())) {
                    loc = colocando.getLocation().add(-1, 0, 0);
                    if (ClanLand.getTypeAt(loc).equalsIgnoreCase("CLAN")) {
                        c = ClanLand.getClanAt(loc);
                        if (!c.isRival(cp.getTag())) {
                            loc = colocando.getLocation().add(0, 0, 1);
                            if (ClanLand.getTypeAt(loc).equalsIgnoreCase("CLAN")) {
                                c = ClanLand.getClanAt(loc);
                                if (!c.isRival(cp.getTag())) {
                                    loc = colocando.getLocation().add(0, 0, -1);
                                    if (ClanLand.getTypeAt(loc).equalsIgnoreCase("CLAN")) {
                                        c = ClanLand.getClanAt(loc);
                                        if (!c.isRival(cp.getTag())) {
                                            p.sendMessage(ChatColor.RED + L.m("Voce so pode por isto perto de inimigos !"));
                                            return true;
                                        } else {
                                            inimigo = c;
                                        }
                                    }
                                } else {
                                    inimigo = c;
                                }
                            }

                        } else {
                            inimigo = c;
                        }
                    }
                } else {
                    inimigo = c;
                }
            }

        } else if (type.equalsIgnoreCase("CLAN")) {
            inimigo = ClanLand.getClanAt(colocando.getLocation());
            if (!inimigo.isRival(cp.getTag())) {
                p.sendMessage(ChatColor.RED + L.m("Voce apenas pode colocar isto em inimigos !"));
                return true;
            }
        } else {
            p.sendMessage(ChatColor.RED + L.m("Voce apenas pode colocar isto colado em base de inimigos ou dentro delas !"));
            return true;
        }
        
        if(inimigo==null) {
            p.sendMessage(ChatColor.RED + L.m("Voce apenas pode colocar isto colado em base de inimigos ou dentro delas !"));
            return true;
        }
        
        if(inimigo.getOnlineMembers().size()<=2) {
            p.sendMessage(L.m("A guilda inimiga tem de ter pelo menos 3 jogadores online para explodi-la !"));
            return true;
        }
       
        
        if(!Mana.spendMana(p, 100)) {
            return true;
        }
        
        cooldownDasGuildas.put(cp.getTag(), tempo);

        for(ClanPlayer cpl : inimigo.getOnlineMembers()) {
            cpl.toPlayer().sendMessage(ChatColor.RED+L.m("[PERIGO]"+ChatColor.YELLOW+"Uma bomba C4 foi posicionada na base de sua guilda !!!"));
            cpl.toPlayer().sendMessage(ChatColor.RED+L.m("[PERIGO]"+ChatColor.YELLOW+"A bomba explodirá em 3 minutos se nenhum engenheiro a desarmar !"));
            cpl.toPlayer().sendMessage(ChatColor.RED+L.m("[PERIGO]"+ChatColor.YELLOW+"Para desarmar um engenheiro deve encontra-la e dar alguns clicks !"));
        }
        
        colocando.setType(Material.TNT);
        p.sendMessage(ChatColor.GREEN+L.m("Voce colocou o explosivo !"));
        p.sendMessage(ChatColor.GREEN+L.m("Ativando em 3 minutos ! Proteja a bomba até ela se ativar !"));
        
        final Block tnt = colocando;
        final Clan enemy = inimigo;
        

        
        Runnable r = new Runnable() {
            public void run() {
                
                //tnt.getLocation().getWorld().createExplosion(null, RARO, true) .createExplosion(tnt.getLocation(), 9);
                PlayEffect.play(VisualEffect.EXPLOSION_HUGE, tnt.getLocation(), "num:1");
                List<Block> blocos = SuperBomba.getNearbyBlocks(tnt.getLocation(), 5);
                for(Block b : blocos) {
                    if(SuperBomba.podemExplodir.contains(b.getType())) {
                        b.breakNaturally();
                    }
                }
                explosivos.remove(tnt);
                for(ClanPlayer cpl : enemy.getOnlineMembers()) {
                  cpl.toPlayer().sendMessage(ChatColor.RED+L.m("[PERIGO]"+ChatColor.YELLOW+"Uma bomba explodiu em sua base !!!"));
                }
            }
        };
        int idTask = Bukkit.getScheduler().scheduleSyncDelayedTask(KoM._instance, r, 20*60*3);
        Runnable r2 = new Runnable() {
            public void run() {
                if(explosivos.containsKey((tnt))) {
                    for(ClanPlayer cpl : enemy.getOnlineMembers()) {
                        cpl.toPlayer().sendMessage(ChatColor.RED+L.m("[PERIGO]"+ChatColor.YELLOW+"Faltam 1 minuto para a bomba explodir !!!"));
                      }
                }   
            }
        };
        Bukkit.getScheduler().scheduleSyncDelayedTask(KoM._instance, r2, 20*60*2);
        
        Runnable r3 = new Runnable() {
            public void run() {
                if(explosivos.containsKey((tnt))) {
                    for(ClanPlayer cpl : enemy.getOnlineMembers()) {
                        cpl.toPlayer().sendMessage(ChatColor.RED+L.m("[PERIGO]"+ChatColor.YELLOW+"Faltam 2 minuto para a bomba explodir !!!"));
                      }
                }   
            }
        };
        Bukkit.getScheduler().scheduleSyncDelayedTask(KoM._instance, r3, 20*60*1);
        
        explosivos.put(colocando,idTask);
        
        if (p.getItemInHand().getAmount() > 1) {
                p.getItemInHand().setAmount(p.getItemInHand().getAmount() - 1);
        } else {
                p.setItemInHand(null);
        }
        return true;
    }
    
    public static HashMap<Block, Integer> explosivos = new HashMap<Block, Integer>();

}
