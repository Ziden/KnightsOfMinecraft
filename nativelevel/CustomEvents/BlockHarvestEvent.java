/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.CustomEvents;

import nativelevel.Harvesting.Harvestable;
import nativelevel.Jobs;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 *
 * @author User
 */
public class BlockHarvestEvent extends Event implements Cancellable {

    public static HandlerList handlers = new HandlerList();

    public BlockHarvestEvent(Player p, Block b, Harvestable h, Jobs.Sucesso rs) {
        this.block = b;
        this.harvestable = h;
        this.player = p;
    }

    private boolean shouldDefaultHarvest = true;
    private boolean canceled = false;
    private Block block;
    private Harvestable harvestable;
    private Player player;
    public Jobs.Sucesso result;

    public Jobs.Sucesso getResult() {
        return result;
    }

    public Block getBlock() {
        return block;
    }

    public void setDefaultHarvest(boolean b) {
        this.shouldDefaultHarvest = b;
    }

    public boolean isDefaultHarvest() {
        return shouldDefaultHarvest;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public Harvestable getHarvestable() {
        return harvestable;
    }

    public void setHarvestable(Harvestable harvestable) {
        this.harvestable = harvestable;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return this.canceled;
    }

    @Override
    public void setCancelled(boolean bln) {
        this.canceled = bln;
    }

}
