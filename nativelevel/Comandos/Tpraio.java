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
public class Tpraio implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] args) {
        //tpraio mundo x y z raio mundo x y z
        if(args.length==9) {
            String mundo = args[0]; //
            int x = Integer.valueOf(args[1]);
            int y = Integer.valueOf(args[2]);
            int z = Integer.valueOf(args[3]);
            
            int raio = Integer.valueOf(args[4]);
            
            String mundo2 = args[5]; //
            int x2 = Integer.valueOf(args[6]);
            int y2 = Integer.valueOf(args[7]);
            int z2 = Integer.valueOf(args[8]);
            
            Block alvo = Bukkit.getWorld(mundo).getBlockAt(x,y,z);
            Location destino = new Location(Bukkit.getWorld(mundo2),x2,y2,z2);
            destino.setX(destino.getX()+0.5);
            destino.setZ(destino.getZ()+0.5);
            for(Player p : Bukkit.getOnlinePlayers()) {
                if(p.getWorld().getName().equalsIgnoreCase(alvo.getWorld().getName()))
                if(p.getLocation().distance(alvo.getLocation()) <= raio) {
                    p.teleport(destino);
                }
            }
            
        }
        
        return true;
    }
    
}