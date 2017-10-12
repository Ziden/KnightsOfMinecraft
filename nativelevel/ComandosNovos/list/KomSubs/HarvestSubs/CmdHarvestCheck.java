/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.ComandosNovos.list.KomSubs.HarvestSubs;

import java.util.HashSet;
import nativelevel.Custom.CustomItem;
import nativelevel.Harvesting.HarvestCache;
import nativelevel.Harvesting.HarvestConfig;
import nativelevel.Harvesting.Harvestable;
import nativelevel.KoM;
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
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author vntgasl
 */
public class CmdHarvestCheck extends SubCmd {

    public CmdHarvestCheck() {
        super("check", CommandType.OP);
    }

    public static void invClick(InventoryClickEvent ev) {
        Player p = (Player) ev.getWhoClicked();
        if (!p.isOp()) {
            return;
        }
        if (ev.getClickedInventory() != null && ev.getClickedInventory().getName() != null && ev.getClickedInventory().getName().equalsIgnoreCase("Coletaveis")) {
            ev.setCancelled(true);

            Harvestable harv = HarvestCache.getHarvestable(ev.getCurrentItem().getType(), ev.getCurrentItem().getData().getData());

            KoM.debug(ev.getAction().name());

            if (ev.getAction() == InventoryAction.DROP_ONE_SLOT) {
                ItemStack dropado = ev.getCurrentItem();
                if (harv != null) {
                    HarvestConfig.remove(harv);
                    HarvestCache.reloadCache();
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
                }
                else {
                    harv.expRatio -= 0.1;
                }
            } else {
                if (ev.isRightClick()) {
                    harv.difficulty += 1;
                }
                else {
                    harv.difficulty -= 1;
                }
            }

            ev.getInventory().setItem(ev.getSlot(), harv.getIcon(true));

            p.sendMessage("Alterado");
            HarvestCache.add(harv);
            HarvestConfig.add(harv);
        }
    }

    public void display(Player p, HashSet<Harvestable> harvestables, int pagina) {
        int tamanho = 6 * 9;
        int ct = 1;
        int inicioPagina = pagina * tamanho - (tamanho - 1);
        int fimPagina = pagina * tamanho;
        Inventory i = Bukkit.createInventory(p, tamanho, "Coletaveis");
        for (Harvestable h : harvestables) {
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
            cs.sendMessage(L.m("Use /kom coletavel check <classe> <pagina>"));
            cs.sendMessage(L.m("Or /kom coletavel check <material> <pagina>"));
        } else {
            String skill = null;
            try {
                skill = args[2];
            } catch (Exception e) {

            }

            int pagina = 1;

            if (args.length >= 4) {
                pagina = Integer.valueOf(args[3]);
            }

            if (skill != null) {
                HashSet<Harvestable> harvestables = HarvestCache.getHarvestable(skill);
                if (harvestables == null) {
                    cs.sendMessage(ChatColor.RED + L.m("Nao achei coletaveis para esta classe !"));
                } else {
                    display((Player) cs, harvestables, pagina);
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
                HashSet<Harvestable> harvestables = HarvestCache.getHarvestable(m);
                if (harvestables == null || harvestables.size() == 0) {
                    cs.sendMessage(ChatColor.RED + L.m("Nao achei coletaveis ") + args[2]);
                } else {
                    display((Player) cs, harvestables, pagina);
                }
            }
        }
    }

}
