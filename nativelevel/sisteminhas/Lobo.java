/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.sisteminhas;

import java.util.ArrayList;
import java.util.List;
import me.fromgate.playeffect.PlayEffect;
import me.fromgate.playeffect.VisualEffect;
import nativelevel.Jobs;
import nativelevel.KoM;
import nativelevel.MetaShit;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author User
 */


public class Lobo implements CommandExecutor {
    
    public static void interactOsso(PlayerInteractEvent ev) {
        if (ev.getPlayer().getItemInHand() != null && ev.getPlayer().getItemInHand().getType() == Material.BONE) {
            ItemMeta meta = ev.getPlayer().getItemInHand().getItemMeta();
            if (meta.getLore() == null || meta.getLore().size() == 0) {
                if (ev.getPlayer().hasMetadata("Lobo")) {
                    Wolf w = (Wolf) MetaShit.getMetaObject("Lobo", ev.getPlayer());
                    InfoLobo il = getLobo(ev.getPlayer());
                    if(il==null) {
                        ev.getPlayer().sendMessage(ChatColor.RED+"Nao encontrei seu osso magico");
                        return;
                    }
                    if (w.isDead() || !w.isValid()) {
                        criaLobo(ev.getPlayer(), getLobo(ev.getPlayer()));
                        ev.getPlayer().playSound(ev.getPlayer().getLocation(), Sound.ENTITY_WOLF_PANT, 1, 1);
                        PlayEffect.play(VisualEffect.HEART, ev.getPlayer().getLocation(), "num:5");
                    } else {
                        w.teleport(ev.getPlayer().getLocation());
                        w.setAngry(false);
                        w.setSitting(false);
                        ev.getPlayer().playSound(ev.getPlayer().getLocation(), Sound.ENTITY_WOLF_PANT, 1, 1);
                        PlayEffect.play(VisualEffect.HEART, ev.getPlayer().getLocation(), "num:5");
                        
                    }
                } else {
                    InfoLobo info = getLobo(ev.getPlayer());
                    if(info==null)
                        return;
                    if (info.dono.equalsIgnoreCase(ev.getPlayer().getName())) {
                        criaLobo(ev.getPlayer(), info);
                        ev.getPlayer().playSound(ev.getPlayer().getLocation(), Sound.ENTITY_WOLF_PANT, 1, 1);
                        PlayEffect.play(VisualEffect.HEART, ev.getPlayer().getLocation(), "num:5");
                    }
                }
            }
            if (ev.getPlayer().getItemInHand().getAmount() > 1) {
                ev.getPlayer().getItemInHand().setAmount(ev.getPlayer().getItemInHand().getAmount() - 1);
            } else {
                ev.getPlayer().setItemInHand(null);
            }
        }
    }
    
    public static Wolf criaLobo(Player p, InfoLobo i) {
        if (p.hasMetadata("Lobo")) {
            Wolf w = (Wolf) MetaShit.getMetaObject("Lobo", p);
            w.remove();
        }
        Wolf w = p.getLocation().getWorld().spawn(p.getLocation(), Wolf.class);
        w.setOwner(p);
        w.setCollarColor(i.cor);
        w.setCustomName(i.nome);
        w.setCustomNameVisible(true);
        MetaShit.setMetaObject("Lobo", p, w);
        return w;
    }
    
    public static void interage(PlayerInteractEntityEvent ev) {
        if (ev.getRightClicked() != null && ev.getRightClicked().getType() == EntityType.WOLF) {
            Wolf w = (Wolf) ev.getRightClicked();
            if (w.getOwner() != null) {
                return;
            }
            if (ev.getPlayer().getItemInHand() == null || ev.getPlayer().getItemInHand().getType() != Material.BONE) {
                return;
            }
            ev.setCancelled(true);
            if (getLobo(ev.getPlayer()) != null) {
                ev.getPlayer().sendMessage(ChatColor.RED + "Guarde isto na sua Enderchest e use um osso para chamar seu lobo !");
                return;
            }
            ItemStack osso = ev.getPlayer().getItemInHand();
            if (Jobs.getJobLevel("Fazendeiro", ev.getPlayer()) == 1 && ev.getPlayer().getLevel() >= 13) {
                
                ItemMeta meta = osso.getItemMeta();
                if (meta.getDisplayName() != null && meta.getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "Osso Magico")) {
                    List<String> lore = meta.getLore();
                    if (lore != null && lore.size() > 0 && lore.get(0).equalsIgnoreCase("Um Osso Magico")) {
                        ev.getPlayer().setItemInHand(null);
                        ev.getPlayer().sendMessage(ChatColor.GREEN + "Voce domesticou o lobo e ganhou o osso magico dele!");
                        ev.getPlayer().sendMessage(ChatColor.GREEN + "Use um Osso para chamar seu !");
                        ev.getPlayer().sendMessage(ChatColor.GREEN + "Guarde seu osso magico na sua Enderchest !");
                        ev.getPlayer().sendMessage(ChatColor.GREEN + "Altere ele pelo /lobo !");
                        w.setCollarColor(DyeColor.YELLOW);
                        w.setOwner(ev.getPlayer());
                        w.setCustomName("Lobo");
                        ev.getPlayer().playSound(ev.getPlayer().getLocation(), Sound.ENTITY_WOLF_PANT, 1, 1);
                        PlayEffect.play(VisualEffect.HEART, ev.getPlayer().getLocation(), "num:5");
                        w.setCustomNameVisible(true);
                        MetaShit.setMetaObject("Lobo", ev.getPlayer(), w);
                        ev.getPlayer().setItemInHand(criaItemLobo(ev.getPlayer()));
                    }
                }
            } else {
                ev.getPlayer().sendMessage(ChatColor.RED + "O lobo te olha com uma cara de besta");
            }
        }
    }
    
    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] args) {
        if (cs instanceof Player) {
            Player p = (Player) cs;
            if (Jobs.getJobLevel("Fazendeiro", p) != 1) {
                p.sendMessage(ChatColor.RED + "Apenas fazendeiros sabem usar lobos");
                return true;
            }
            InfoLobo info = getLobo(p);
            if (info == null && !p.isOp()) {
                p.sendMessage(ChatColor.RED + "Voce nao tem um lobo ! Domestique um !");
                return true;
            }
            if (args.length == 0) {
                cs.sendMessage(ChatColor.GREEN + "____________LOBOS____________");
                cs.sendMessage(ChatColor.GREEN + "|" + ChatColor.YELLOW + " /lobo nome <nome>");
                cs.sendMessage(ChatColor.GREEN + "|" + ChatColor.YELLOW + " /lobo cor <cor>");
                cs.sendMessage(ChatColor.GREEN + "|" + ChatColor.YELLOW + " /lobo cores");
                if (p.isOp()) {
                    cs.sendMessage(ChatColor.GREEN + "|" + ChatColor.YELLOW + " /lobo item");
                }
                cs.sendMessage(ChatColor.GREEN + "____________________________");
            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("cores")) {
                    String s = "";
                    for (DyeColor cor : DyeColor.values()) {
                        s += cor.name() + " | ";
                    }
                    cs.sendMessage(ChatColor.GREEN + s);
                } else if (args[0].equalsIgnoreCase("item") && p.isOp()) {
                    p.getInventory().addItem(Lobo.criaItem());
                }
            } else if (args.length == 2) {
                if (args[0].equalsIgnoreCase("nome")) {
                    String nome = args[1];
                    updateLobo(p, nome, info.cor);
                    info.nome = nome;
                    Wolf w = criaLobo(p, info);
                } else if (args[0].equalsIgnoreCase("cor")) {
                    try {
                        DyeColor cor = DyeColor.valueOf(args[1]);
                        updateLobo(p, info.nome, cor);
                        info.cor = cor;
                        Wolf w = criaLobo(p, info);
                    } catch (Exception e) {
                        cs.sendMessage(ChatColor.GREEN + "| " + ChatColor.YELLOW + "Veja  /lobo cores");
                    }
                }
            }
        }
        
        return true;
    }
    
    private static class InfoLobo {
        
        String nome;
        DyeColor cor;
        String dono;
    }
    
    public static void updateLobo(Player p, String nome, DyeColor cor) {
        ItemStack ss = getItemLobo(p);
        ItemMeta meta = ss.getItemMeta();
        List<String> lore = new ArrayList<String>();
        lore.add("Um Osso de Lobo");
        lore.add("Nome:" + nome);
        lore.add("Cor:" + cor.name());
        lore.add("Dono:" + p.getName());
        meta.setLore(lore);
        ss.setItemMeta(meta);
    }
    
    public static ItemStack getItemLobo(Player p) {

        for (ItemStack ss : p.getEnderChest().getContents()) {
            if (ss == null) {
                continue;
            }
            if (ss.getType() == Material.BONE) {
                ItemMeta meta = ss.getItemMeta();
                if (meta.getDisplayName() != null && meta.getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "Osso de Lobo")) {
                    List<String> lore = meta.getLore();
                    if (lore != null && lore.size() > 0 && lore.get(0).equalsIgnoreCase("Um Osso de Lobo")) {
                        p.getEnderChest().setItem(0, ss);
                        return ss;
                    }
                }
            }
        }
        
        for (ItemStack ss : p.getInventory().getContents()) {
            if (ss == null) {
                continue;
            }
            if (ss.getType() == Material.BONE) {
                ItemMeta meta = ss.getItemMeta();
                if (meta.getDisplayName() != null && meta.getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "Osso de Lobo")) {
                    List<String> lore = meta.getLore();
                    if (lore != null && lore.size() > 0 && lore.get(0).equalsIgnoreCase("Um Osso de Lobo")) {
                        return ss;
                    }
                }
            }
        }
        return null;
    }
    
    public static InfoLobo getLobo(Player p) {
        for (ItemStack ss : p.getEnderChest().getContents()) {
            if (ss == null) {
                continue;
            }
            if (ss.getType() == Material.BONE) {
                ItemMeta meta = ss.getItemMeta();
                if (meta.getDisplayName() != null && meta.getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "Osso de Lobo")) {
                    List<String> lore = meta.getLore();
                    if (lore != null && lore.size() > 0 && lore.get(0).equalsIgnoreCase("Um Osso de Lobo")) {
                        String nome = lore.get(1).split(":")[1];
                        String cor = lore.get(2).split(":")[1];
                        String dono = lore.get(3).split(":")[1];
                        DyeColor corDye = DyeColor.valueOf(cor);
                        InfoLobo info = new InfoLobo();
                        info.cor = corDye;
                        info.nome = nome;
                        info.dono = dono;
                        return info;
                    }
                }
            }
        }
        for (ItemStack ss : p.getInventory().getContents()) {
            if (ss == null) {
                continue;
            }
            if (ss.getType() == Material.BONE) {
                ItemMeta meta = ss.getItemMeta();
                if (meta.getDisplayName() != null && meta.getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "Osso de Lobo")) {
                    List<String> lore = meta.getLore();
                    if (lore != null && lore.size() > 0 && lore.get(0).equalsIgnoreCase("Um Osso de Lobo")) {
                        String nome = lore.get(1).split(":")[1];
                        String cor = lore.get(2).split(":")[1];
                        String dono = lore.get(3).split(":")[1];
                        DyeColor corDye = DyeColor.valueOf(cor);
                        InfoLobo info = new InfoLobo();
                        info.cor = corDye;
                        info.nome = nome;
                        info.dono = dono;
                        return info;
                    }
                }
            }
        }
        return null;
    }
    
    public static ItemStack criaItemLobo(Player p) {
        ItemStack ss = new ItemStack(Material.BONE);
        ItemMeta meta = ss.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Osso de Lobo");
        List<String> lore = new ArrayList<String>();
        lore.add("Um Osso de Lobo");
        lore.add("Nome:Lobo");
        lore.add("Cor:" + DyeColor.YELLOW.name());
        lore.add("Dono:" + p.getName());
        meta.setLore(lore);
        ss.setItemMeta(meta);
        return ss;
    }
    
    public static ItemStack criaItem() {
        ItemStack ss = new ItemStack(Material.BONE);
        ItemMeta meta = ss.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Osso Magico");
        List<String> lore = new ArrayList<String>();
        lore.add("Um Osso Magico");
        lore.add("Use para Domesticar um Lobo");
        meta.setLore(lore);
        ss.setItemMeta(meta);
        return ss;
    }
}
