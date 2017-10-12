/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.sisteminhas;

import nativelevel.phatloots.PhatLootsAPI;
import java.util.ArrayList;
import java.util.List;
import nativelevel.CFG;
import nativelevel.Jobs;
import nativelevel.Lang.L;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Player;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

/**
 *
 * @author vntgasl
 */

public class Tesouros implements Listener {

    @EventHandler
    public void interage(PlayerInteractEvent ev) {
        if (ev.getPlayer().getItemInHand() != null && ev.getPlayer().getItemInHand().getType() == Material.WRITTEN_BOOK) {
            usaLivro(ev.getPlayer());
        }
    }

    @EventHandler
    public void minera(BlockBreakEvent ev) {
        int chance = 2000;
        if (ev.getBlock().getLocation().getY() < 40) {
            if (ev.getBlock().getType() == Material.STONE) {
                if (Jobs.rnd.nextInt(chance) == 1) {
                    ev.getPlayer().sendMessage(ChatColor.GREEN + L.m("Voce encontrou um velho livro enterrado"));
                    geraTesouro(ev.getPlayer());
                }
            }
        }
    }

    @EventHandler
    public void pescaBaiacu(PlayerFishEvent ev) {
        // CALCULANDO CHANCE DE PEGAR TESOURO
        if (ev.getCaught() != null) {
            if (Jobs.rnd.nextInt(900) == 0) {
                ev.getPlayer().sendMessage(ChatColor.GREEN + L.m("Voce pescou um velho livro"));
                geraTesouro(ev.getPlayer());
            }
        }
    }

    public ItemStack geraTesouro(Player p) {
        ItemStack livro = new ItemStack(Material.WRITTEN_BOOK, 1);
        BookMeta meta = (BookMeta) livro.getItemMeta();
        meta.setTitle(ChatColor.AQUA + "[Quest] Tesouro de Jabu");
        meta.setAuthor("Jabu Tesoureiro");
        List<String> pages = new ArrayList<String>();
        pages.add(0, "Isto e um tesouro de Jabu. Vá em sorathes e encontre estas coordenadas e use este livro para obter o tesouro !");
        pages.add(1, "Mas lembre-se dos mobs que podem lhe atacar !!");
        pages.add(2, "X:" + (2500 - (Jobs.rnd.nextInt(5000))));
        pages.add(3, "Z:" + (2500 - (Jobs.rnd.nextInt(5000))));
        meta.setPages(pages);
        livro.setItemMeta(meta);
        p.getInventory().addItem(livro);
        p.updateInventory();
        return livro;
    }

    // TO-DO, podemos fazer via PHAT-LOOTS depois
    public ItemStack[] sorteiaItems() {
        List<ItemStack> itemsl = PhatLootsAPI.rollForLoot("Tesouro").getItemList();
        return itemsl.toArray(new ItemStack[itemsl.size()]);
    }

    public void usaLivro(Player p) {
        ItemStack livro = p.getItemInHand();
        if (livro != null && livro.getType() == Material.WRITTEN_BOOK) {
            BookMeta meta = (BookMeta) livro.getItemMeta();
            if (meta.hasAuthor()) {
                if (meta.getAuthor().equalsIgnoreCase("Jabu Tesoureiro")) {
                    if (p.getWorld().getName().equalsIgnoreCase(CFG.mundoGuilda)) {

                        int x = Integer.parseInt(meta.getPage(3).split(":")[1]);

                        int z = Integer.parseInt(meta.getPage(4).split(":")[1]);

                        int px = p.getLocation().getBlockX();
                        int pz = p.getLocation().getBlockZ();
                        if (px < 0) {
                            px++;
                        }
                        if (pz < 0) {
                            pz++;
                        }

                        if (x == px && z == pz) {
                            int y = p.getLocation().getBlockY();

                            Block bau = p.getWorld().getBlockAt(x, y, z);

                            p.sendMessage(ChatColor.GREEN + "Voce encontrou o tesouro !");

                            bau.setType(Material.CHEST);
                            /*
                             bau.getRelative(0, 1, 0).setType(Material.WEB);

                             bau.getRelative(0, -1, 0).setType(Material.COAL_BLOCK);
                             bau.getRelative(1, -1, 0).setType(Material.WEB);
                             bau.getRelative(-2, -1, -1).setType(Material.WEB);
                             bau.getRelative(0, -1, 1).setType(Material.WEB);
                             bau.getRelative(0, -1, -2).setType(Material.WEB);
                             bau.getRelative(0, -3, 0).setType(Material.DIAMOND_ORE);
                             */
                            Chest c = (Chest) bau.getState();

                            c.getInventory().setContents(sorteiaItems());

                            c.getWorld().spawn(bau.getRelative(0, 0, -1).getLocation(), Spider.class);
                            c.getWorld().spawn(bau.getRelative(0, 0, 1).getLocation(), Zombie.class);
                            c.getWorld().spawn(bau.getRelative(-1, 0, 0).getLocation(), Blaze.class);
                            c.getWorld().spawn(bau.getRelative(1, 0, 0).getLocation(), Creeper.class);

                            p.setItemInHand(null);
                        } else {
                            p.sendMessage(ChatColor.RED + "Voce nao esta no local correto !");
                        }
                    } else {
                        p.sendMessage(ChatColor.GOLD + "Neste mundo não tem tesouros vá em Sorathes !");
                    }
                }
            }
        }
    }
}
