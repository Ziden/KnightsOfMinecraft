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
 * Event for a player un-equipping an item
 */
public class PlayerUnequipEvent extends Event
{

    private static final HandlerList handlers = new HandlerList();

    private final Player player;
    private final ItemStack item;
    private final EquipMeta itemMeta;
    
    /**
     * Constructor
     *
     * @param player player un-equipping the item
     * @param item   item that was un-equipped
     */
    public PlayerUnequipEvent(Player player, ItemStack item)
    {
        this.player = player;
        this.item = item;
        this.itemMeta = null;
    }

    public EquipMeta getItemMeta() {
        return itemMeta;
    }
    
    
    
    public PlayerUnequipEvent(Player player, EquipMeta meta) {
        this.player = player;
        this.itemMeta = meta;
        this.item = null;
    }

    /**
     * @return player un-equipping the item
     */
    public Player getPlayer()
    {
        return player;
    }

    /**
     * @return item that was un-equipped
     */
    public ItemStack getItem()
    {
        return item;
    }

    /**
     * @return handlers for this event
     */
    @Override
    public HandlerList getHandlers()
    {
        return handlers;
    }

    /**
     * @return handlers for this event
     */
    public static HandlerList getHandlerList()
    {
        return handlers;
    }
}