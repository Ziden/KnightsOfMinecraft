/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.CustomEvents;

import nativelevel.Crafting.Craftable;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author User
 * 
 */
public class FinishCraftEvent extends Event {

    public static HandlerList handlers = new HandlerList();

    public boolean failed = false;
    public boolean canceled = false;
    private Player player;
    private Inventory craftingInventory;
    private ItemStack result;
    private ItemStack cursor;
    private Craftable craftable;
    
    public FinishCraftEvent(Player p, Inventory inv, ItemStack result, ItemStack cursor, Craftable craftable) {
        this.player = p;
        this.craftingInventory = inv;
        this.result = result;
        this.cursor = cursor;
        this.craftable = craftable;
    }
    
    private boolean isDefaultCraft = true;

    public boolean isIsDefaultCraft() {
        return isDefaultCraft;
    }

    public Craftable getCraftable() {
        return craftable;
    }

    public void setCraftable(Craftable craftable) {
        this.craftable = craftable;
    }

    
    public void setIsDefaultCraft(boolean isDefaultCraft) {
        this.isDefaultCraft = isDefaultCraft;
    }
    
    

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Inventory getCraftingInventory() {
        return craftingInventory;
    }

    public void setCraftingInventory(Inventory craftingInventory) {
        this.craftingInventory = craftingInventory;
    }

    public ItemStack getResult() {
        return result;
    }

    public void setResult(ItemStack result) {
        this.result = result;
    }

    public ItemStack getCursor() {
        return cursor;
    }

    public void setCursor(ItemStack cursor) {
        this.cursor = cursor;
    }
    
    
    
    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

}
