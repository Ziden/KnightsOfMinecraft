/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.Equipment;

import java.util.Arrays;
import java.util.List;
import nativelevel.Attributes.Health;
import nativelevel.CustomEvents.PlayerEquipEvent;
import nativelevel.CustomEvents.PlayerUnequipEvent;
import nativelevel.KoM;
import nativelevel.MetaShit;
import nativelevel.scores.SBCore;
import nativelevel.sisteminhas.KomSystem;
import net.minecraft.server.v1_12_R1.ItemArmor;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import net.minecraft.server.v1_12_R1.NBTTagList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author vntgasl
 */
public class EquipmentEvents extends KomSystem {

    @EventHandler
    public void swapa(PlayerSwapHandItemsEvent ev) {
        EquipManager.checkEquips(ev.getPlayer());
    }

    @EventHandler
    public void pegaCriativo(InventoryCreativeEvent ev) {
        ev.setCurrentItem(WeaponDamage.checkForMods(ev.getCurrentItem()));
        ev.setCursor(WeaponDamage.checkForMods(ev.getCursor()));
    }

    @EventHandler
    public void invClick(InventoryClickEvent ev) {

        ItemStack ss = ev.getCurrentItem();
        if (ss == null) {
            return;
        }
        net.minecraft.server.v1_12_R1.ItemStack itemNMS = CraftItemStack.asNMSCopy(ss);
        NBTTagCompound tag = itemNMS.getTag();

        if (tag != null) {
            NBTTagList lista = tag.getList("AttributeModifiers", 10);
            if (lista != null) {
                return;
            }
        }

        ev.setCurrentItem(WeaponDamage.checkForMods(ev.getCurrentItem()));
    }

    @EventHandler
    public void itemDrop(PlayerDropItemEvent ev) {
        EquipManager.checkEquips(ev.getPlayer());
    }

    @EventHandler
    public void pickItem(PlayerPickupItemEvent ev) {
        if (ev.getPlayer().getInventory().firstEmpty() == ev.getPlayer().getInventory().getHeldItemSlot()) {
            EquipManager.checkEquips(ev.getPlayer());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void itemHeld(PlayerItemHeldEvent ev) {
        
        if(ev.isCancelled()){
            return;
        }
        
        ItemStack old = ev.getPlayer().getInventory().getItem(ev.getPreviousSlot());
        ItemStack niw = ev.getPlayer().getInventory().getItem(ev.getNewSlot());

        if (niw != null) {
            if (niw.getType() == Material.SHIELD) {
                ev.setCancelled(true);
            }
        }

        if (niw != null && isWeapon(niw)) {

            Bukkit.getServer().getPluginManager().callEvent(new PlayerEquipEvent(ev.getPlayer(), niw));
        }

        if (old != null && isWeapon(old)) {
            Bukkit.getServer().getPluginManager().callEvent(new PlayerUnequipEvent(ev.getPlayer(), old));
        }

        EquipManager.updateHand(ev.getPlayer(), niw);

    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onClick(InventoryClickEvent event) {

        if (event.getInventory() == null || event.getClickedInventory() == null) {
            return;
        }

        KoM.debug("Clicando inv type " + event.getInventory().getType().name() + " CLICKED " + event.getClickedInventory().getType().name());

        if (event.getInventory().getType() == InventoryType.CRAFTING || event.getInventory().getType() == InventoryType.PLAYER) {

            Player player = (Player) event.getWhoClicked();

            if (event.getSlotType() == InventoryType.SlotType.ARMOR || event.isShiftClick() || event.getSlot() == 40) {

                EquipManager.checkEquips(player);

            }

            KoM.debug("SLOT EV " + event.getSlot() + " SLOT HELD " + player.getInventory().getHeldItemSlot());
            // if he clicks the selected item on hotbar
            if (event.getSlot() == player.getInventory().getHeldItemSlot()) {

                EquipManager.checkEquips(player);
            }
        }
    }

    @EventHandler
    public void inventoryClose(InventoryCloseEvent ev) {
        if (ev.getPlayer().getType() == EntityType.PLAYER) {
            EquipManager.checkEquips((Player) ev.getPlayer());
        }
    }

    @EventHandler
    public void join(PlayerJoinEvent ev) {
        EquipManager.checkEquips(ev.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onInteract(PlayerInteractEvent event) {
        if (event.getHand() == EquipmentSlot.OFF_HAND) {
            return;
        }
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            String name = event.getPlayer().getItemInHand().getType().name();
            if (name.contains("_CHESTPLATE") || name.contains("_LEGGINGS") || name.contains("_BOOTS") || name.contains("_HELMET")) {
                EquipManager.checkEquips(event.getPlayer());
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onDeath(PlayerDeathEvent event) {
        EquipManager.checkEquips(event.getEntity());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onDeath(InventoryCloseEvent event) {
        if (event.getPlayer().getType() == EntityType.PLAYER) {
            EquipManager.checkEquips((Player) event.getPlayer());
        }
    }

    @EventHandler
    public void equip(PlayerEquipEvent ev) {
        if (ev.getItem() == null && ev.getItemMeta() == null) {
            return;
        }

        EquipMeta itemMeta = null;
        if (ev.getItem() != null) {
            itemMeta = ItemAttributes.getAttributes(ev.getItem());
        } else {
            itemMeta = ev.getItemMeta();
        }
        EquipMeta playerEquipMeta = EquipManager.getPlayerEquipmentMeta(ev.getPlayer());

        /*
         String s = "";
         for (Atributo a : itemMeta.getAtributos()) {
         double valor = itemMeta.getAttribute(a);
         s += ChatColor.GREEN + a.getName() + ChatColor.YELLOW + " +" + valor + "  ";
         }
         ev.getPlayer().sendMessage(ChatColor.GREEN+"Equipando");
       
         ev.getPlayer().sendMessage(s);
         */
        EquipMeta.addMeta(playerEquipMeta, itemMeta);
        EquipManager.setPlayerEquipmentMeta(ev.getPlayer(), playerEquipMeta);
        ev.getPlayer().setMaxHealth(Health.getMaxHealth(ev.getPlayer(), ev.getPlayer().getLevel()));

        if (itemMeta.getAtributos().contains(Atributo.Mana) || itemMeta.getAtributos().contains(Atributo.Stamina)) {
            SBCore.AtualizaObjetivos(ev.getPlayer());
        }
    }

    @EventHandler
    public void unequip(PlayerUnequipEvent ev) {
        if (ev.getItem() == null && ev.getItemMeta() == null) {
            return;
        }
        EquipMeta itemMeta = null;
        if (ev.getItem() != null) {
            itemMeta = ItemAttributes.getAttributes(ev.getItem());
        } else {
            itemMeta = ev.getItemMeta();
        }

        String s = "";

        /*
         for (Atributo a : itemMeta.getAtributos()) {
         double valor = itemMeta.getAttribute(a);
         s += ChatColor.GREEN + a.getName() + ChatColor.YELLOW + " -" + valor + "  ";
         }
         ev.getPlayer().sendMessage(ChatColor.GREEN+"Desequipando");
         ev.getPlayer().sendMessage(s);
         */
        EquipMeta playerEquipMeta = EquipManager.getPlayerEquipmentMeta(ev.getPlayer());
        EquipMeta.subMeta(playerEquipMeta, itemMeta);
        EquipManager.setPlayerEquipmentMeta(ev.getPlayer(), playerEquipMeta);
        ev.getPlayer().setMaxHealth(Health.getMaxHealth(ev.getPlayer(), ev.getPlayer().getLevel()));

        if (itemMeta.getAtributos().contains(Atributo.Mana) || itemMeta.getAtributos().contains(Atributo.Stamina)) {
            SBCore.AtualizaObjetivos(ev.getPlayer());
        }

    }

    public static boolean isArmor(ItemStack ss) {
        return (CraftItemStack.asNMSCopy(ss).getItem() instanceof ItemArmor);
    }

    public static boolean isWeapon(ItemStack ss) {
        return isWeapon(ss.getType());
    }

    public static boolean isWeapon(Material m) {
        return m != null && !m.name().contains("WOOD") && (m.name().contains("SWORD") || m.name().contains("AXE") || m.name().contains("SPADE") || m.name().contains("HOE") || m == Material.BOW);
    }

    public static List<Material> offHands = Arrays.asList(new Material[]{Material.STICK, Material.SHIELD, Material.WOOD_SWORD});

    public static boolean isOffHand(Material m, List<String> lore) {
        if(lore != null) {
            for(String s : lore) {
                if(s.contains("Mão Esquerda") || s.contains("Mão Secundária")) {
                    return true;
                }
            }
        }
        return m != null && (m == Material.STICK || m == Material.SHIELD || m.name().contains("WOOD_"));
    }

}
