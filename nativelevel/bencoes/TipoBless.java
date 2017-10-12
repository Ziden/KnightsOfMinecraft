/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.bencoes;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import nativelevel.KoM;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author User
 *
 */
public enum TipoBless {

    Forjar("+5% em forjar items e 3x XP"), // 
    Reparar("+15% chance de reparar"), //   
    Alquimia("+5% acerto e 2x mais XP com poções"),//
    Encantamento("Maior chance de encantar items e XP"),
    Prosperidade("XP de Mobs"),
    Mineracao("Maior chance de achar minerios e XP"),
    Serralheria("Mais lenha de arvores e mais XP"),
    Pescaria("Mais XP e bonus na pesca");
    //Colheita("Mais XP Colhendo plantas")

    String nome;

    public static File dat;

    public static SaveMaroto save = new SaveMaroto();

    public static void interage(PlayerInteractEvent ev) {
        if (ev.getPlayer().getItemInHand() != null) {
            if (ev.getPlayer().getItemInHand().getType() == Material.NETHER_STAR) {
                ItemMeta meta = ev.getPlayer().getItemInHand().getItemMeta();
                if (meta.getLore().get(2).contains("Benção")) {
                    String nome = meta.getDisplayName().split(" ")[2];
                    KoM.log.info(nome);
                    TipoBless tipo = TipoBless.valueOf(nome);
                    TipoBless ativo = save.getTipo(ev.getPlayer());
                    if (ativo != null) {
                        ev.getPlayer().sendMessage(ChatColor.RED + "Voce ja tem uma benção ativa: " + ativo.name());
                        return;
                    }
                    save.add(ev.getPlayer(), tipo);
                    Player p = ev.getPlayer();
                    int tempo = save.tem(ev.getPlayer());
                    if (p.getItemInHand().getAmount() == 1) {
                        p.setItemInHand(null);
                    } else {
                        p.getItemInHand().setAmount(p.getItemInHand().getAmount() - 1);
                    }
                    ev.getPlayer().sendMessage(ChatColor.GREEN + "Voce ativou a benção " + tipo.name() + " por " + tempo + " segundos");
                    ev.getPlayer().sendMessage(ChatColor.GREEN + "Digite /kom bencao para saber quanto tempo falta!");

                }
            }
        }
    }

    public static void init() {
        dat = new File("SaveBuffs.dat");
        if (!dat.exists()) {
            try {
                dat.createNewFile();
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
        SaveMaroto psave = save.Load();
        if (psave != null) {
            save = psave;
        }
    }

    public static void mostraTodos(Player p) {
        Inventory i = Bukkit.createInventory(p, 18, "Benções");
        for (TipoBless tipo : TipoBless.values()) {
            i.addItem(cria(tipo));
        }
        p.openInventory(i);
    }

    public static ItemStack cria(TipoBless tipo) {
        ItemStack item = new ItemStack(Material.NETHER_STAR, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Benção de " + tipo.name());
        List<String> lore = new ArrayList<String>();
        lore.add(ChatColor.YELLOW + tipo.nome);
        lore.add(ChatColor.GREEN + "Dura 60 Minutos");
        lore.add(ChatColor.GREEN + "Benção de Bonus");
        lore.add(ChatColor.GREEN + "Esta benção é poderosa. Use com sabedoria.");
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    private TipoBless(String nome) {
        this.nome = nome;
    }

}
