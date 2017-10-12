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

/**
 *
 * @author vntgasl
 */
public class Botaschem implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] args) {
        //kompaste world x y z shem
        if(args.length==5) {
            //dungeon 1892 4 1023 
            // 
            
            String mundo = args[0]; //
            int x = Integer.valueOf(args[1]);
            int y = Integer.valueOf(args[2]);
            int z = Integer.valueOf(args[3]);
            String shem = args[4];
            World w = Bukkit.getWorld(mundo);
            if(w==null)
                return true;
            Location l = new Location(w,x,y,z);
            generateStructureBuild(l, shem);
        }
        
        return true;
    }
    
    public void generateStructureBuild(Location loc, String schematic) {
	EditSession es = new EditSession(new BukkitWorld(loc.getWorld()), 999999999);
	CuboidClipboard cc = null;
	try {
	    cc = CuboidClipboard.loadSchematic(new File(KoM.worldEdit.getDataFolder() + File.separator + "schematics", schematic + ".schematic"));
	} catch (Exception ex) {
	   ex.printStackTrace();
	} 
	if (cc != null) {
	    try {
		Vector v = Vector.toBlockPoint(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
		cc.setOrigin(new Vector(0, 0, 0));
		cc.paste(es, v, false);     
	    } catch (MaxChangedBlocksException ex) {
		ex.printStackTrace();
	    }
	}
    }
    
}
