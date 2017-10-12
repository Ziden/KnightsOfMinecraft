/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.utils;

import java.lang.reflect.Field;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 *
 * @author User
 */
public class ScreenTitle {

    public static void show(Player p, String title, String sub) {
        if (sub != null) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + p.getName() + " subtitle " + sub);
        }
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "title " + p.getName() + " title " + title);
    }

}
