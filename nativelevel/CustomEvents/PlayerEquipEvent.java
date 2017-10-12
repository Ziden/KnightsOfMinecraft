/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.CustomEvents;

import nativelevel.Equipment.EquipMeta;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

/**
 * Event for when a player equips an item
 */
public class PlayerEquipEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final Player player;
    private final ItemStack item;
    private final EquipMeta itemMeta;

    /**
     * Constructor
     *
     * @param player player equipping an item
     * @param item item that was equipped
     */
    public PlayerEquipEvent(Player player, ItemStack item) {
        this.player = player;
        this.item = item;
        this.itemMeta = null;
    }

    public PlayerEquipEvent(Player player, EquipMeta meta) {
        this.player = player;
        this.itemMeta = meta;
        this.item = null;
    }

    public EquipMeta getItemMeta() {
        return itemMeta;
    }

    /**
     * @return plyer that equipped the item
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * @return item that was equipped
     */
    public ItemStack getItem() {
        return item;
    }

    /**
     * @return handlers for this event
     */
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    /**
     * @return handlers for this event
     */
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
