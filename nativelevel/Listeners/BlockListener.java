package nativelevel.Listeners;

import java.util.Arrays;
import java.util.List;
import nativelevel.Classes.Engineer;
import nativelevel.Classes.Farmer;
import nativelevel.Classes.Lumberjack;
import nativelevel.Custom.CustomItem;
import nativelevel.Custom.Items.Ponte;
import nativelevel.Custom.Items.SuperBomba;
import nativelevel.CustomEvents.BlockHarvestEvent;
import nativelevel.ExecutaSkill;
import nativelevel.Harvesting.HarvestEvents;
import nativelevel.Jobs;
import nativelevel.KoM;
import nativelevel.Lang.L;
import static nativelevel.Listeners.GeneralListener.pistaoNaoEmpurra;
import static nativelevel.Listeners.GeneralListener.wizard;
import nativelevel.Planting.PlantEvents;
import nativelevel.integration.SimpleClanKom;
import nativelevel.rankings.Estatistica;
import nativelevel.rankings.RankDB;
import nativelevel.sisteminhas.Dungeon;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BrewingStand;
import org.bukkit.block.Furnace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffectType;

/**
 *
 * @author Ziden
 * 
 */

public class BlockListener implements Listener {

    public static List<Material> passaveis = Arrays.asList(new Material[]{Material.GRASS, Material.DOUBLE_PLANT, Material.RED_ROSE, Material.YELLOW_FLOWER, Material.AIR, Material.WATER});

    @EventHandler
    public void harvest(BlockHarvestEvent ev) {
        if(ev.getHarvestable().classe==Jobs.Classe.Lenhador) {
            Lumberjack.corta(ev);
        } else  if(ev.getHarvestable().classe==Jobs.Classe.Fazendeiro) {
            Farmer.blockHarvest(ev);
        }
    }
    
    @EventHandler
    public void pistao(BlockPistonExtendEvent ev) {
        for (Block b : ev.getBlocks()) {
            if (pistaoNaoEmpurra.contains(b.getType())) {
                ev.setCancelled(true);
            }
            if (b.getType() == Material.MELON_BLOCK || b.getType() == Material.MELON_SEEDS) {
                if (Jobs.rnd.nextInt(100) <= 60) {
                    b.setType(Material.AIR);
                }
            }
            if (!ev.getBlock().getWorld().getName().equalsIgnoreCase("dungeon")) {
                if (b.getType() == Material.SLIME_BLOCK) {
                    ev.setCancelled(true);
                }
            }
        }
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void quebraBloco(final BlockBreakEvent event) {
        if (event.isCancelled()) {
            return;
        }
        
        KoM.debug("Block Break event highest");

        if (event.getPlayer().getGameMode() == GameMode.CREATIVE && event.getPlayer().hasPermission("kom.build")) {
            return;
        }

        if (Ponte.bloqueios.contains(event.getBlock())) {
            if (Jobs.rnd.nextInt(10) != 1) {
                event.setCancelled(true);
                event.getPlayer().sendMessage(ChatColor.RED + "Voce nao conseguiu remover o bloco estranho, tente novamente.");

                return;
            } else {
                event.setCancelled(true);
                event.getBlock().setType(Material.AIR);
                Ponte.bloqueios.remove(event.getBlock());
                return;
            }
        }

        event.setExpToDrop(0);
        if (!SimpleClanKom.canBuild(event.getPlayer(), event.getBlock().getLocation(), event.getBlock(), false)) {
            event.setCancelled(true);
            return;
        }
        
        if(event.getBlock().getType()==Material.LEAVES || event.getBlock().getType()==Material.LEAVES_2) {
            Farmer.cortaFolha(event.getPlayer(), event.getBlock());
        }

        if (event.getBlock().getWorld().getName().equalsIgnoreCase("dungeon")) {
            Dungeon.blockBreak(event);
            return;
        }

        if (event.getBlock().getRelative(BlockFace.DOWN).getType() == Material.DOUBLE_PLANT && event.getBlock().getType() == Material.DOUBLE_PLANT) {
            event.getPlayer().sendMessage(ChatColor.RED + L.m("Colha a flor pela raiz !"));
            event.setCancelled(true);
            return;
        }

        if (event.getBlock().getRelative(BlockFace.UP).getType() == Material.SUGAR_CANE_BLOCK || event.getBlock().getRelative(BlockFace.UP).getType() == Material.CACTUS || event.getBlock().getRelative(BlockFace.UP).getType() == Material.SUGAR_CANE || event.getBlock().getRelative(BlockFace.UP).getType() == Material.NETHER_WARTS) {
            event.getPlayer().sendMessage(ChatColor.RED + L.m("Comece colhendo pelas pontas !"));
            event.setCancelled(true);
            return;
        }

        if (event.getBlock().getType() == Material.TNT && SuperBomba.explosivos.containsKey(event.getBlock())) {
            event.setCancelled(true);
        }

        if (event.getBlock().getType() == Material.SIGN || event.getBlock().getType() == Material.SIGN_POST) {
            Sign s = (Sign) event.getBlock().getState();
            if (!event.getPlayer().isOp() && s.getLine(0).equalsIgnoreCase("[Server]") || s.getLine(0).equalsIgnoreCase("§l§r[Torres]")) {
                event.getPlayer().sendMessage(ChatColor.RED + L.m("Esta placa esta presa por um poder maior."));
                event.setCancelled(true);
                return;
        }
        }
        if (event.getBlock().getRelative(BlockFace.UP).getType() == Material.SIGN || event.getBlock().getRelative(BlockFace.UP).getType() == Material.SIGN_POST) {
            Sign s = (Sign) event.getBlock().getRelative(BlockFace.UP).getState();
            if (!event.getPlayer().isOp() && s.getLine(0).equalsIgnoreCase("[Server]")) {
                event.setCancelled(true);
                return;
            }
        }
        // corrigir bug de fazer item sem chegar skil kebrando
        byte limpo = 0;
        if (event.getBlock().getType() == Material.FURNACE || event.getBlock().getType() == Material.BURNING_FURNACE) {
            ((Furnace) event.getBlock().getState()).getInventory().clear();
            event.getBlock().setType(Material.AIR);
            event.getBlock().setData(limpo);
            event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.FURNACE, 1));
        } else if (event.getBlock().getType() == Material.BREWING_STAND) {
            ((BrewingStand) event.getBlock().getState()).getInventory().clear();
            event.getBlock().setType(Material.AIR);
            event.getBlock().setData(limpo);
            event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.BREWING_STAND_ITEM, 1));
        }

        if (event.getBlock().getType() == Material.WEB || event.getBlock().getType() == Material.GLOWSTONE) {
            if (wizard.protegeTeia(event.getBlock())) {
                event.setCancelled(true);
                return;
            }
        }
        KoM.generator.breakBlock(event);
        
        HarvestEvents.quebraBlock(event);
        
        KoM.debug("Fim block break highest");

    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void poeBloco(BlockPlaceEvent ev) {
        if (ev.isCancelled()) {
            return;
        }
        if (ev.getPlayer().isOp()) {
            return;
        }

        if (!ev.getPlayer().isOp()) {
            if (ev.getBlock().getType() == Material.BEACON) {
                ev.setCancelled(true);
            }
        }

        if (ev.getPlayer().getWorld().getName().equalsIgnoreCase("dungeon")) {
            if (ev.getBlock().getType() != Material.TORCH) {
                ev.getPlayer().damage(7);
            } else {
                Arrow a = Bukkit.getWorld("dungeon").spawn(ev.getBlock().getLocation(), Arrow.class);
                for (Entity e : a.getNearbyEntities(8, 8, 8)) {
                    if (e.getType() == EntityType.PLAYER) {
                        Player nego = (Player) e;
                        if (nego.hasPotionEffect(PotionEffectType.BLINDNESS)) {
                            nego.removePotionEffect(PotionEffectType.BLINDNESS);
                            nego.sendMessage(ChatColor.GREEN + "Voce pode ver novamente");
                        }
                    }
                }
            }
        }
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void colocaBloco(final BlockPlaceEvent ev) {

        if (ev.isCancelled()) {
            return;
        }

        if (ev.getPlayer().hasPermission("kom.build")) {
            return;
        }

        if (ev.getBlock().getType() == Material.COMMAND) {
            //KnightsOfMinecraft.komLog.escreve(ev.getPlayer().getName() +L.m( " colocou command block no mundo " + ev.getBlock().getLocation().getWorld().getName() + " na loc " + ev.getBlock().getLocation().getBlockX() + "," + ev.getBlock().getLocation().getBlockY() + "," + ev.getBlock().getLocation().getBlockZ());
        }
        
        // anti bug de criar redstone_block
        if (ev.getBlock().getType() == Material.STONE_BUTTON) {
            if (ev.getBlockAgainst().getType().getId() == 98 && ev.getBlockAgainst().getData() == 3) {
                if (!ev.getPlayer().isOp()) {
                    ev.setCancelled(true);
                    return;
                }
            }
        }

        String customItem = CustomItem.getCustomItem(ev.getPlayer().getItemInHand());
        if (customItem != null) {
            ev.setCancelled(true);
            return;
        }

        if (ev.getBlock().getWorld().getName().equalsIgnoreCase("dungeon")) {
            Dungeon.blockPlace(ev);
        }

        if (!SimpleClanKom.canBuild(ev.getPlayer(), ev.getBlock().getLocation(), ev.getBlock(), true)) {
            ev.setCancelled(true);
            return;
        }
        
      

        if (ev.getBlock().getType() == Material.BED || ev.getBlock().getType() == Material.HOPPER || ev.getBlock().getType() == Material.HOPPER_MINECART || ev.getBlock().getType() == Material.IRON_ORE || ev.getBlock().getType() == Material.GOLD_ORE || ev.getBlock().getType() == Material.MELON || ev.getBlock().getType() == Material.PUMPKIN || ev.getBlock().getType() == Material.EMERALD_ORE) {
            if (!ev.getPlayer().isOp()) {
                ev.getPlayer().sendMessage(ChatColor.RED + L.m("Este item esta bloqueado temporariamente !"));
                ev.setCancelled(true);
            }
        }
        
          KoM.debug("Planting");
        PlantEvents.plantaBlock(ev);
        
        if (!ev.isCancelled()) {
            ev.getBlock().setMetadata("playerpois", new FixedMetadataValue(KoM._instance, true));
        }

        if (!ev.isCancelled()) {
            RankDB.addPontoCache(ev.getPlayer(), Estatistica.BUILDER, 1);
        }
    }

}
