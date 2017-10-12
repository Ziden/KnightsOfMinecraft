/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.ComandosNovos.list.KomSubs.CraftSubs;

import java.util.Arrays;
import java.util.HashSet;
import nativelevel.Crafting.CraftCache;
import nativelevel.Crafting.CraftConfig;
import nativelevel.Crafting.Craftable;
import nativelevel.Jobs;
import nativelevel.Lang.L;
import nativelevel.ComandosNovos.Comando.CommandType;
import nativelevel.ComandosNovos.SubCmd;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author vntgasl
 *
 */
public class CmdCraftCheck extends SubCmd {

    public CmdCraftCheck() {
        super("check", CommandType.OP);
    }

    public static void invClick(InventoryClickEvent ev) {
        Player p = (Player) ev.getWhoClicked();
        if (!p.isOp()) {
            return;
        }
        if (ev.getClickedInventory() != null && ev.getClickedInventory().getName() != null && ev.getClickedInventory().getName().equalsIgnoreCase("Craftaveis")) {
            ev.setCancelled(true);

            Craftable harv = CraftCache.getCraftable(ev.getCurrentItem());

            if (ev.getAction() == InventoryAction.DROP_ONE_SLOT) {
                ItemStack dropado = ev.getCurrentItem();
                if (harv != null) {
                    CraftConfig.remove(harv);
                    CraftCache.reloadCache();
                    p.sendMessage("Removido");
                    ev.setCancelled(true);
                    ev.getInventory().setItem(ev.getSlot(), new ItemStack(Material.AIR));
                    ev.getCurrentItem().setType(Material.AIR);
                    return;
                }
            }

            if (harv == null) {
                p.sendMessage("Deu alguma merda... nao achei o coletavel");
                return;
            }

            if (ev.isShiftClick()) {
                if (ev.isRightClick()) {
                    harv.expRatio += 0.1;
                } else {
                    harv.expRatio -= 0.1;
                }
            } else {
                if (ev.isRightClick()) {
                    harv.diff += 1;
                } else {
                    harv.diff -= 1;
                }
            }

            ev.getInventory().setItem(ev.getSlot(), harv.getIcon(true));

            p.sendMessage("Alterado");
            CraftCache.add(harv);
            CraftConfig.add(harv);
        }
    }

    public void display(Player p, HashSet<Craftable> harvestables, int pagina) {
        int tamanho = 6 * 9;
        int ct = 1;
        int inicioPagina = pagina * tamanho - (tamanho - 1);
        int fimPagina = pagina * tamanho;
        Inventory i = Bukkit.createInventory(p, tamanho, "Craftaveis");
        for (Craftable h : harvestables) {
            if (ct >= inicioPagina && ct < fimPagina) {
                i.addItem(h.getIcon(true));
            }
            ct++;
        }
        p.openInventory(i);
    }

    @Override
    public void execute(CommandSender cs, String[] args) {
        if (args.length < 3) {
            cs.sendMessage(L.m("Use /kom crafts check <classe> <pagina>"));
            cs.sendMessage(L.m("ou /kom crafts check <material> <pagina>"));
        } else {
            Jobs.Classe skill = null;
            try {
                skill = Jobs.Classe.valueOf(args[2]);
            } catch (Exception e) {

            }
            if (skill != null) {
                HashSet<Craftable> craftables = CraftCache.getCraftables(skill);
                if (craftables == null) {
                    cs.sendMessage(ChatColor.RED + L.m("Classe sem craftaveis !"));
                } else {

                    int pagina = 1;

                    if (args.length >= 4) {
                        pagina = Integer.valueOf(args[3]);
                    }

                    display((Player) cs, craftables, pagina);
                }

            } else {
                Material m = null;
                try {
                    m = Material.valueOf(args[2]);
                } catch (Exception e) {

                }
                if (m == null) {
                    m = Material.getMaterial(args[2]);
                }
                Craftable craftables = CraftCache.getCraftable(new ItemStack(m,1));
                if (craftables == null) {
                    cs.sendMessage(ChatColor.RED + L.m("Nao achei craftaveis para ") + args[2]);
                } else {
                    display((Player) cs, new HashSet<Craftable>(Arrays.asList(new Craftable[]{craftables})),1);
                }
            }
        }
    }

}
