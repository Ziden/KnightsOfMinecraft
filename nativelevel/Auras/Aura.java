/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.Auras;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import nativelevel.Auras.Lista.*;
import nativelevel.KoM;
import nativelevel.MetaShit;
import nativelevel.gemas.Raridade;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.ChatColor;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 *
 * @author Gabriel
 *
 */
public abstract class Aura {

    Raridade r;
    public static HashSet<Aura> auras = new HashSet<Aura>();

    public static HashMap<UUID, Aura> ativas = new HashMap<UUID, Aura>();

    private static HashSet<UUID> remover = new HashSet<UUID>();

    static {
        auras.add(new NyanCat());
        auras.add(new Lava());
        auras.add(new Esmeralda());
        auras.add(new Dark());
        auras.add(new Tontura());
        auras.add(new AsasDeAnjo());
        auras.add(new LordGelado());
        auras.add(new Natal());
        auras.add(new Musical());
        auras.add(new Fumaca());
        auras.add(new Amor());
    }
    
    public static void onEnable() {
        Runnable r = new Runnable() {
            public void run() {
                boolean newTick = true;
                for (UUID u : ativas.keySet()) {
                    Player p = Bukkit.getPlayer(u);
                    if (p == null) {
                        remover.add(u);
                    } else {
                        ativas.get(u).tick(p, newTick);
                    }
                    newTick = false;
                }
                if (remover.size() > 0) {
                    for (UUID uuid : remover) {
                        ativas.remove(uuid);
                    }
                }
                remover.clear();
            }
        };
        Bukkit.getScheduler().scheduleSyncRepeatingTask(KoM._instance, r, 3, 3);
    }

    public static ItemStack icone(String nome, Material icone) {
        ItemStack ss = new ItemStack(icone, 1);
        ItemMeta meta = ss.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.UNDERLINE + nome);
        ss.setItemMeta(meta);
        return ss;
    }

    public static void clica(InventoryClickEvent ev) {
        if (ev.getClickedInventory() != null && ((ev.getClickedInventory().getTitle() != null && ev.getClickedInventory().getTitle().equalsIgnoreCase("Minhas Auras")) || (ev.getClickedInventory().getName() != null && ev.getClickedInventory().getName().equalsIgnoreCase("Minhas Auras")))) {
            ev.setCancelled(true);
            Player p = (Player) ev.getWhoClicked();
            if(ev.getCurrentItem()==null || ev.getCurrentItem().getType()==Material.AIR)
                return;
            String clicado = ChatColor.stripColor(ev.getCurrentItem().getItemMeta().getDisplayName());
            p.closeInventory();
            if (clicado.equalsIgnoreCase("Remover Aura")) {
                if (ativas.containsKey(p.getUniqueId())) {
                    ativas.remove(p.getUniqueId());
                    p.sendMessage(ChatColor.GREEN + "Aura Removida");
                } else {
                    p.sendMessage(ChatColor.RED + "Nenhuma aura ativa");
                }
            } else {
                
                if(!p.hasPermission("kom.vip")) {
                    p.sendMessage(ChatColor.RED+"Apenas VIPs podem usar Auras !");
                    return;
                }
                
                
                Aura a = Aura.getAura(clicado);
                if (a != null) {
                    ativas.put(p.getUniqueId(), a);
                    p.sendMessage(ChatColor.GREEN + "Aura ativada");
                    for (Entity e : p.getNearbyEntities(15, 15, 15)) {
                        if (e.getType() == EntityType.PLAYER) {
                            ((Player) e).sendMessage(ChatColor.AQUA + "* " + a.frase().replace("@", p.getName()) + " *");
                        }
                    }
                }
            }
        }
    }

    public static void abreAuras(Player p) {
        Inventory i = Bukkit.createInventory(p, 9 * 3, "Minhas Auras");
        HashSet<Aura> auras = KoM.database.getAuras(p.getUniqueId());
        i.setItem(4, icone("Remover Aura", Material.BARRIER));
        int x = 9;
        for (Aura a : auras) {
            i.setItem(x, icone(a.getNome(), a.getIcone()));
            x++;
        }
        p.openInventory(i);
    }

    public static void interage(PlayerInteractEvent ev) {
        if (ev.getPlayer().getItemInHand() != null && ev.getPlayer().getItemInHand().getType() == Material.CHEST) {
            Aura aura = getAura(ev.getPlayer().getItemInHand());
            if (aura != null) {
                ev.setCancelled(true);
                HashSet<Aura> auras = KoM.database.getAuras(ev.getPlayer().getUniqueId());
                if (auras.contains(aura)) {
                    ev.getPlayer().sendMessage(ChatColor.RED + "Voce ja tem essa aura !");
                    return;
                } else {
                    KoM.database.aprendeAura(ev.getPlayer().getUniqueId(), aura);
                    ev.getPlayer().setItemInHand(null);
                    ev.getPlayer().sendMessage(ChatColor.GREEN + "Voce aprendeu a aura !");
                    ev.getPlayer().sendMessage(ChatColor.GREEN + "Use " + ChatColor.YELLOW + "/kom aura" + ChatColor.GREEN + " para ver suas auras !");
                }
            }
        }
    }

    public static Aura getAura(ItemStack ss) {
        if (ss.getType() == Material.CHEST) {
            ItemMeta meta = ss.getItemMeta();
            if (meta.getDisplayName() != null && meta.getDisplayName().contains("§aAura")) {
                List<String> lore = meta.getLore();
                if (lore != null && lore.size() > 1) {
                    String aura = ChatColor.stripColor(lore.get(1));
                    Aura ar = Aura.getAura(aura);
                    if (ar != null) {
                        return ar;
                    }
                }
            }
        }
        return null;
    }

    public static ItemStack criaItem(String aura) {
        Aura a = getAura(aura);
        if (a == null) {
            return null;
        }
        ItemStack ss = new ItemStack(Material.CHEST, 1);
        ItemMeta meta = ss.getItemMeta();
        meta.setDisplayName("§9♦ §aAura " + a.getNome());
        List<String> lore = new ArrayList<String>();
        lore.add("§9Use para aprender a aura");
        lore.add("§a" + a.getNome());
        meta.setLore(lore);
        ss.setItemMeta(meta);
        return ss;
    }

    public static Aura getAura(String nome) {
        for (Aura a : auras) {
            if (a.getNome().equalsIgnoreCase(nome)) {
                return a;
            }
        }
        return null;
    }

    public abstract String getNome();

    public abstract Material getIcone();

    public abstract String frase();

    public abstract void tick(Player p, boolean newTick);

}
