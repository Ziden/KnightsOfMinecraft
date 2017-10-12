/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.Classes.Mage;

import org.bukkit.ChatColor;

/**
 *
 * @author User
 */
public enum Elements {

    Fogo(ChatColor.RED + "☣"), Raio(ChatColor.DARK_AQUA + "☼"), Terra(ChatColor.DARK_GREEN + "☢");
    
    public String icon;
    
    private Elements(String icon) {
        this.icon = icon;
    }
    
}
