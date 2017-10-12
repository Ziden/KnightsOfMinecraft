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
public class Msgzinha implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] args) {
        if (args.length > 2) {
            String jogador = args[0];
            Player p = Bukkit.getPlayer(jogador);
            if (p == null) {
                return false;
            }
            String msg = "";
            for (int x = 1; x < args.length; x++) {
                msg += args[x];
            }
            p.sendMessage(msg.replaceAll("\\&", "§"));
        }
        return true;
    }

}
