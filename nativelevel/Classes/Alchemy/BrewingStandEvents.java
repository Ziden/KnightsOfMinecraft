/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.Classes.Alchemy;

import nativelevel.Custom.CustomItem;
import nativelevel.Custom.Items.PotionExtract;
import nativelevel.Lang.L;
import nativelevel.Custom.CustomPotion;
import nativelevel.sisteminhas.KomSystem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author User
 */
public class BrewingStandEvents extends KomSystem {

    @EventHandler
    public void brew(BrewEvent ev) {
        ev.setCancelled(true);

        BrewerInventory inv = ev.getContents();
        ItemStack ingredient = inv.getIngredient();

        for (int x = 0; x < inv.getContents().length; x++) {
            ItemStack brewing = inv.getContents()[x];
            if (brewing == null || brewing.getType() == Material.AIR || brewing.getType() == ev.getContents().getIngredient().getType()) {
                continue;
            }

            CustomItem custom = CustomItem.getItem(brewing);
            if(custom==null || !(custom instanceof PotionExtract)) {
                inv.setItem(x, new ItemStack(Material.GLASS_BOTTLE, 1));
                return;
            }
            
            PotionExtract extract = (PotionExtract) CustomItem.getItem(brewing);
            CustomPotion result = extract.getPotionFromExtract(brewing);
            ItemStack neededToBrew = result.brewWith();
            if (neededToBrew.getType() != ingredient.getType() || neededToBrew.getData().getData() != ingredient.getData().getData()) {
                inv.setItem(x, new ItemStack(Material.GLASS_BOTTLE, 1));
            } else {
                inv.setItem(x, result.generateItem(1));
            }

        }
        if (ingredient.getAmount() > 1) {
            ingredient.setAmount(ingredient.getAmount() - 1);
        } else {
            ev.getContents().setIngredient(null);
        }
    }

    @EventHandler
    public void inventoryClick(InventoryClickEvent ev) {
        if (ev.getInventory().getType() == InventoryType.BREWING && ev.getWhoClicked().getType() == EntityType.PLAYER) {

            if (ev.isShiftClick()) {
                ev.setCancelled(true);
            }

            Player p = (Player) ev.getWhoClicked();
            BrewerInventory inv = (BrewerInventory) ev.getInventory();
            ItemStack placing = ev.getCursor();
            if (ev.isShiftClick()) {
                placing = ev.getCurrentItem();
            }
            // im placing something in the potion area
            if (placing != null && placing.getType() != Material.AIR && (ev.getRawSlot() == 0 || ev.getRawSlot() == 1 || ev.getRawSlot() == 2)) {
                // will check if its an extract
                CustomItem ci = CustomItem.getItem(placing);
                if (ci == null || !(ci instanceof PotionExtract)) {
                    ev.setCancelled(true);
                    p.sendMessage(ChatColor.RED + L.m("Você apenas pode colocar extratos de poções aqui. Crie seus extratos em um caldeirão. !"));
                    return;
                }
            }
        }
    }

}
