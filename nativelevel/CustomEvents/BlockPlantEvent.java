
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nativelevel.CustomEvents;

import nativelevel.Jobs;
import nativelevel.Planting.Plantable;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 *
 * @author User
 * 
 */

public class BlockPlantEvent extends Event implements Cancellable {

    public static HandlerList handlers = new HandlerList();

    public BlockPlantEvent(Player p, Block b, Plantable h, Jobs.Sucesso rs) {
        this.block = b;
        this.harvestable = h;
        this.player = p;
    }
    
    private boolean canceled = false;
    private Block block;
    private Plantable harvestable;
    private Player player;
    public Jobs.Sucesso result;

    public Jobs.Sucesso getResult() {
        return result;
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public Plantable getPlantable() {
        return harvestable;
    }

    public void setPlantable(Plantable harvestable) {
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
