/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.Comandos;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.data.DataException;
import java.io.File;
import java.io.IOException;
import nativelevel.KoM;
import nativelevel.Listeners.GeneralListener;
import nativelevel.sisteminhas.QuestsIntegracao;
import nativelevel.sisteminhas.XP;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author vntgasl
 * 
 */

public class DarExp implements CommandExecutor {

    
    
    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] args) {
        
      if(args.length==2) {
          
          String player = args[0];
          int level = Integer.valueOf(args[1]);
          
          int exp = (int)(XP.getExpPorAcao(level) * QuestsIntegracao.XP_MOD);
          
          Player p = Bukkit.getPlayer(player);
          if(p!=null) {
              p.sendMessage(ChatColor.GREEN+"Voce ganhou "+exp+" EXP por fazer a quest");
              GeneralListener.givePlayerExperience(exp,p);
              
          }
          
          
      }
        
      return true;
    }
    
  
}
