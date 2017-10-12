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
 */
public class Msgraio implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] args) {
        //tpraio mundo x y z raio msg
        if(args.length==6) {
            String mundo = args[0]; //
            int x = Integer.valueOf(args[1]);
            int y = Integer.valueOf(args[2]);
            int z = Integer.valueOf(args[3]);
            
            int raio = Integer.valueOf(args[4]);
            
            String msg = args[5]; //
            msg = msg.replaceAll("\\_", " ");
            msg = ChatColor.translateAlternateColorCodes('&', msg);
            
            Block alvo = Bukkit.getWorld(mundo).getBlockAt(x,y,z);
            for(Player p : Bukkit.getOnlinePlayers()) {
                 if(p.getWorld().getName().equalsIgnoreCase(alvo.getWorld().getName()))
                if(p.getLocation().distance(alvo.getLocation()) <= raio) {
                    p.sendMessage(msg);
                }
            }
            
        }
        
        return true;
    }
    
}