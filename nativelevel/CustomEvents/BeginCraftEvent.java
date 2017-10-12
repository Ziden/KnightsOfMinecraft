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
 */
public class BeginCraftEvent extends Event {

    public static HandlerList handlers = new HandlerList();

    public boolean failed = false;
    public boolean canceled = false;
    private int chanceBonus = 0;
    private int multiplyExp = 1;
    private Player player;
    private Inventory craftingInventory;
    private ItemStack result;
    private ItemStack cursor;
    private Craftable craftable;

    public BeginCraftEvent(Player p, Inventory inv, ItemStack result, ItemStack cursor, Craftable craftable) {
        this.player = p;
        this.craftingInventory = inv;
        this.result = result;
        this.cursor = cursor;
        this.craftable = craftable;
    }

    public int getMultiplyExp() {
        return multiplyExp;
    }

    public void setMultiplyExp(int multiplyExp) {
        this.multiplyExp = multiplyExp;
    }

    private boolean isDefaultCraft = true;

    public boolean isIsDefaultCraft() {
        return isDefaultCraft;
    }

    public int getChanceBonus() {
        return chanceBonus;
    }

    public void setChanceBonus(int chanceBonus) {
        this.chanceBonus = chanceBonus;
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
