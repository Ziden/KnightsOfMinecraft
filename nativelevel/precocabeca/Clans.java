/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nativelevel.precocabeca;

import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;
import net.sacredlabyrinth.phaed.simpleclans.managers.ClanManager;
import org.bukkit.Bukkit;

/**
 *
 * @author USER
 */
public class Clans {
    public static ClanManager manager; 

public void onEnable(){
           manager = ((SimpleClans) Bukkit.getServer().getPluginManager()
                .getPlugin("SimpleClans")).getClanManager();
}

}