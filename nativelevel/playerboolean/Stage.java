/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.playerboolean;

import java.util.HashSet;
import org.bukkit.entity.Player;

/**
 *
 * @author vntgasl
 */
public class Stage {
    
    public Stage() {
        stagesCompleted = new HashSet<String>();
    }
    
    public HashSet<String> stagesCompleted = null;
   
    public static boolean completouStage(Player p, PredefinedStages s) {
        return StageDB.getPlayerStage(p).stagesCompleted.contains(s.name());
    }
    
    public static void completa(Player p, PredefinedStages s) {
        StageDB.addCompletedStage(p, s.name());
    }
    
    public static enum PredefinedStages {
        ESCOLHEUSEXO;
    }
    
}
