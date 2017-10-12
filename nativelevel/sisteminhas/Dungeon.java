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

import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;
import nativelevel.KoM;
import nativelevel.Lang.L;
import nativelevel.MetaShit;
import nativelevel.utils.BookUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class Dungeon extends KomSystem {

    public static int LUZ_DO_ESCURO = 2;
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onAsyncChat(AsyncPlayerChatEvent event) {
        Dungeon.textoAtivaRedstone(event);
    }

    public static void blockBreak(BlockBreakEvent event) {
        if (!event.getPlayer().isOp()) {

            Location l = event.getBlock().getLocation();
            l.setY(0);
            if (l.getBlock().getType() == Material.DIAMOND_BLOCK) {
                return;
            }

            event.setCancelled(true);
        }
    }

    public static void textoAtivaRedstone(final AsyncPlayerChatEvent ev) {
        if (ev.getPlayer().getWorld().getName().equalsIgnoreCase("dungeon")) {
            if (ev.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.DIAMOND_BLOCK) {
                if (ev.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getType() == Material.CHEST) {
                    KoM.log.info("MSG :" + ev.getMessage());
                    ItemStack book = BookUtil.getBookAt(ev.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN));
                    if (book != null) {
                        BookMeta meta = (BookMeta) book.getItemMeta();
                        if (meta.getPages().size() > 0) {
                            KoM.log.info("MSG LIVRO :" + meta.getPages().get(0));
                            if (ev.getMessage().equalsIgnoreCase(meta.getPages().get(0))) {
                                Runnable r = new Runnable() {
                                    public void run() {
                                        final Block toxa = ev.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN);
                                        if (!toxa.hasMetadata("redstone") && toxa.getType() == Material.REDSTONE_BLOCK) {
                                            toxa.setType(Material.AIR);
                                            ev.setCancelled(true);
                                            return;
                                        }
                                        if (toxa.hasMetadata("redstone")) {
                                            ev.setCancelled(true);
                                            return;
                                        }
                                        KoM.rewind.put(toxa, Material.AIR);
                                        toxa.setType(Material.REDSTONE_BLOCK);

                                        MetaShit.setMetaString("redstone", toxa, "1");
                                        int task = Bukkit.getScheduler().scheduleSyncDelayedTask(KoM._instance,
                                                new Runnable() {

                                                    @Override
                                                    public void run() {
                                                        toxa.setType(Material.AIR);
                                                        toxa.removeMetadata("redstone", KoM._instance);
                                                    }
                                                }, 20 * 10);
                                    }
                                };
                                Bukkit.getScheduler().scheduleSyncDelayedTask(KoM._instance, r, 5);
                            }
                        }
                    }
                }
            }
        }
    }

    public static void itemAtivaRedstone(PlayerInteractEvent ev) {
       // if (ev.getPlayer().getWorld().getName().equalsIgnoreCase("dungeon")) {
            if (ev.getAction() == Action.RIGHT_CLICK_BLOCK) {
                Block clicado = ev.getClickedBlock();
                if (clicado != null && ev.getPlayer().getItemInHand() != null && ev.getPlayer().getItemInHand().getType() != Material.AIR) {
                    Block emBaixo = clicado.getRelative(BlockFace.DOWN);
                    for (int x = 0; x < 3; x++) {
                        emBaixo = emBaixo.getRelative(BlockFace.DOWN);
                        if (emBaixo.getType() == Material.SPONGE) {
                            if (emBaixo.getRelative(BlockFace.DOWN).getType() == Material.CHEST) {
                                Chest c = (Chest) emBaixo.getRelative(BlockFace.DOWN).getState();
                                for (ItemStack ss : c.getBlockInventory().getContents()) {
                                    if (ss == null) {
                                        continue;
                                    }
                                    if (ss.getType() == ev.getPlayer().getItemInHand().getType()) {
                                        ItemMeta m1 = ss.getItemMeta();
                                        ItemMeta m2 = ev.getPlayer().getItemInHand().getItemMeta();
                                        if (((m1.getLore() == null && m2.getLore() == null)
                                                || m1.getLore().size() == m2.getLore().size() && m1.getLore().equals(m2.getLore())) && (m1.getDisplayName() != null && m2.getDisplayName() != null && m1.getDisplayName().equalsIgnoreCase(m2.getDisplayName()))) {

                                            if(!m2.getDisplayName().contains("[Quest]")) {
                                                int qtd = ev.getPlayer().getItemInHand().getAmount();
                                                if(qtd==1)
                                                    ev.getPlayer().setItemInHand(null);
                                                else
                                                    ev.getPlayer().getItemInHand().setAmount((qtd-1));
                                            }
                                            final Block bbb = emBaixo;
                                            //PlayEffect.play(VisualEffect.SMOKE, clicado.getLocation(), "num:3");
                                            clicado.getWorld().playEffect(clicado.getLocation(), Effect.SMOKE, 3);
                                            emBaixo.setType(Material.REDSTONE_BLOCK);
                                            Runnable r = new Runnable() {
                                                @Override
                                                public void run() {
                                                    Block bloco = bbb.getLocation().getBlock();
                                                    if (bloco.getType() == Material.REDSTONE_BLOCK) {
                                                        bloco.setType(Material.SPONGE);
                                                    }
                                                }
                                            };
                                            Bukkit.getScheduler().scheduleSyncDelayedTask(KoM._instance, r, 20 * 20);
                                            KoM.rewind.put(emBaixo, Material.SPONGE);
                                        }
                                    }
                                }
                                break;
                            }
                        }
                    }
                }
            }
       // }
    }

    public static void empurraBloco(PlayerInteractEvent ev) {
        if (ev.getPlayer().getWorld().getName().equalsIgnoreCase("dungeon")) {
            if (ev.getAction() == Action.RIGHT_CLICK_BLOCK) {
                Block clicked = ev.getClickedBlock();
                if (clicked != null) {

                    if (clicked.getType().getId() == 97) {
                        ev.getPlayer().sendMessage(ChatColor.RED + "Esta pedra e muito pesada para ser empurrada !");
                        return;
                    }

                    if (clicked.getRelative(BlockFace.DOWN).getType() == Material.DIAMOND_BLOCK) {

                        if (ev.getBlockFace() == BlockFace.DOWN || ev.getBlockFace() == BlockFace.UP) {
                            ev.getPlayer().sendMessage(ChatColor.RED + L.m("Voce nao pode empurrar o bloco para la !"));
                            return;
                        }

                        String frase = L.m("Voce empurrou o bloco !");
                        Block novo = clicked.getRelative(ev.getBlockFace().getOppositeFace());
                        if (ev.getPlayer().isSneaking()) {
                            frase = L.m("Voce puxou o bloco !");
                            novo = clicked.getRelative(ev.getBlockFace());
                        }
                        if ((novo.getType() != Material.TORCH && novo.getType() != Material.AIR) || novo.getRelative(BlockFace.DOWN).getType() != Material.DIAMOND_BLOCK) {
                            ev.getPlayer().sendMessage(ChatColor.RED + L.m("Voce nao pode empurrar o bloco para la !"));
                            return;
                        }
                        novo.setType(clicked.getType());
                        novo.getState().setData(clicked.getState().getData());
                        clicked.setType(Material.AIR);
                        clicked.getLocation().getWorld().playEffect(clicked.getLocation(), Effect.SMOKE, 1);
                        clicked.getLocation().getWorld().playEffect(novo.getLocation(), Effect.SMOKE, 1);
                        //PlayEffect.play(VisualEffect.SMOKE, novo.getLocation(), "num:3");
                        ev.getPlayer().sendMessage(ChatColor.GREEN + frase);
                    } else if (clicked.getRelative(BlockFace.DOWN).getType() == Material.ICE) {
                        if (ev.getBlockFace() == BlockFace.DOWN || ev.getBlockFace() == BlockFace.UP) {
                            ev.getPlayer().sendMessage(ChatColor.RED + L.m("Voce nao pode empurrar o bloco para la !"));
                            return;
                        }
                        BlockFace direcao = ev.getBlockFace().getOppositeFace();
                        Block abaixo = ev.getClickedBlock().getRelative(direcao).getRelative(BlockFace.DOWN);
                        Block afrente = ev.getClickedBlock().getRelative(direcao);
                        if (afrente.getType() == Material.AIR && abaixo.getType() == Material.ICE) {
                            int max = 10;
                            List<Block> passar = new ArrayList<Block>();
                            Block ultimo = null;
                            while (max > 0) {
                                abaixo = abaixo.getRelative(direcao);
                                afrente = afrente.getRelative(direcao);
                                if (afrente.getType() == Material.AIR && abaixo.getType() == Material.ICE) {
                                    passar.add(afrente);
                                    ultimo = afrente;
                                } else {
                                    break;
                                }
                                max--;
                            }
                            if (ultimo != null) {
                                ev.getPlayer().sendMessage(ChatColor.GREEN + L.m("Voce empurrou o bloco !"));
                                ultimo.setType(clicked.getType());
                                ultimo.getState().setData(clicked.getState().getData());
                                clicked.setType(Material.AIR);
                                for (Block b : passar) {
                                     b.getLocation().getWorld().playEffect(b.getLocation(), Effect.SMOKE, 1);
                                     //clicked.getLocation().getWorld().playEffect(b.getLocation(), Effect.SMOKE, 1);
                                    //PlayEffect.play(VisualEffect.SMOKE, b.getLocation(), "num:3");
                                    //PlayEffect.play(VisualEffect.SMOKE, clicked.getLocation(), "num:3");
                                }
                            }

                        } else {
                            ev.getPlayer().sendMessage(ChatColor.RED + L.m("Voce nao pode empurrar o bloco para la !"));
                            return;
                        }
                    }
                }
            }
        }
    }

    public static void itemActivatesRedstone(PlayerInteractEvent ev) {
            if (ev.getAction() == Action.RIGHT_CLICK_BLOCK) {
                Block clicado = ev.getClickedBlock();
                if (clicado != null && ev.getPlayer().getItemInHand() != null && ev.getPlayer().getItemInHand().getType() != Material.AIR) {
                    Block emBaixo = clicado.getRelative(BlockFace.DOWN);
                    for (int x = 0; x < 3; x++) {
                        emBaixo = emBaixo.getRelative(BlockFace.DOWN);
                        if (emBaixo.getType() == Material.GOLD_BLOCK) {
                            if (emBaixo.getRelative(BlockFace.DOWN).getType() == Material.CHEST) {
                                Chest c = (Chest) emBaixo.getRelative(BlockFace.DOWN).getState();
                                for (ItemStack ss : c.getBlockInventory().getContents()) {
                                    if (ss == null) {
                                        continue;
                                    }
                                    if (ss.getType() == ev.getPlayer().getItemInHand().getType()) {
                                        ItemMeta m1 = ss.getItemMeta();
                                        ItemMeta m2 = ev.getPlayer().getItemInHand().getItemMeta();
                                        if (((m1.getLore() == null && m2.getLore() == null)
                                                || m1.getLore().size() == m2.getLore().size() && m1.getLore().equals(m2.getLore())) && (m1.getDisplayName() != null && m2.getDisplayName() != null && m1.getDisplayName().equalsIgnoreCase(m2.getDisplayName()))) {

                                            if(!m2.getDisplayName().contains("[Quest]")) {
                                                int qtd = ev.getPlayer().getItemInHand().getAmount();
                                                if(qtd==1)
                                                    ev.getPlayer().setItemInHand(null);
                                                else
                                                    ev.getPlayer().getItemInHand().setAmount((qtd-1));
                                            }
                                            final Block bbb = emBaixo;
                                           // PlayEffect.play(VisualEffect.SMOKE, clicado.getLocation(), "num:3");
                                            emBaixo.setType(Material.REDSTONE_BLOCK);
                                            Runnable r = new Runnable() {
                                                @Override
                                                public void run() {
                                                    Block bloco = bbb.getLocation().getBlock();
                                                    if (bloco.getType() == Material.REDSTONE_BLOCK) {
                                                        bloco.setType(Material.GOLD_BLOCK);
                                                    }
                                                }
                                            };
                                            Bukkit.getScheduler().scheduleSyncDelayedTask(KoM._instance, r, 20 * 20);
                                            KoM.rewind.put(emBaixo, Material.GOLD_BLOCK);
                                            //Rewind.add(emBaixo, Material.GOLD_BLOCK);
                                        }
                                    }
                                }
                                break;
                            }
                        }
                    }
                }
            }
    }


    public static void interact(PlayerInteractEvent ev) {
        if (ev.getPlayer().isOp()) {
            return;
        }
        if (ev.getPlayer().getItemInHand() != null && ev.getPlayer().getItemInHand().getType() == Material.WATER_BUCKET) {
            ev.setCancelled(true);
        }
    }

  
    
    public static void blockPlace(BlockPlaceEvent event) {
        if (event.getPlayer().isOp()) {
            return;
        }

        Location l = event.getBlock().getLocation();
        l.setY(0); // 1910 4 998
        if (l.getBlock().getType() == Material.DIAMOND_BLOCK) {
            return;
        }

        if (event.getBlockReplacedState().getType() == Material.VINE || event.getBlockReplacedState().getType() == Material.STATIONARY_WATER || event.getBlockReplacedState().getType() == Material.WATER || event.getBlockReplacedState().getType() == Material.LAVA) {
            event.setCancelled(true);
            return;
        }
        if (event.getBlock().getType() == Material.TORCH) {
            event.getPlayer().sendMessage(ChatColor.GREEN + L.m("Você colocou uma tocha temporária."));
            final Block b = event.getBlock();
            KoM.rewind.put(b, Material.AIR);
            Runnable r = new Runnable() {

                @Override
                public void run() {
                    Block bloco = b.getLocation().getBlock();
                    if (bloco.getType() == Material.TORCH) {
                        bloco.setType(Material.AIR);
                        KoM.rewind.remove(bloco);
                    }
                }
            };
            Bukkit.getScheduler().scheduleSyncDelayedTask(KoM._instance, r, 20 * 120);
            // NativeLevel.rewind.put(event.getBlock(), Material.AIR);
        } else {
            // foi pro SimpleClanKom.java
            // event.setCancelled(true);
            // event.getPlayer().sendMessage(ChatColor.RED + "Nada de tentar colocar blocos aqui !");
            // event.getPlayer().damage(5D);
        }
    }
}
