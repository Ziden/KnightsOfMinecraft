/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.sisteminhas;

import nativelevel.CFG;
import org.bukkit.event.world.ChunkLoadEvent;

/**
 *
 * @author User
 * 
 */
public class MiniMissoes {
    
    public void chunkLoad(ChunkLoadEvent ev) {
        if(ev.getChunk().getWorld().getName().equalsIgnoreCase(CFG.mundoGuilda)) {
            
        }
    }
    
}
