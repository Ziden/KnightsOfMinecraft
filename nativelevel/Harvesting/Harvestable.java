/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.Harvesting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import nativelevel.Jobs;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author vntgasl
 */
public class Harvestable {

    public Harvestable(Material m, byte data, String skill, int minSkill, double expRatio) {
        this.m = m;
        this.data = data;
        this.classe = Jobs.Classe.valueOf(skill);
        this.difficulty = minSkill;
        this.expRatio = expRatio;
    }

    public ItemStack getIcon(boolean op) {
        ItemStack ss = new ItemStack(m, 1, data);
        ItemMeta meta = ss.getItemMeta();
        List<String> lore = new ArrayList<String>();
        lore.add(ChatColor.YELLOW + classe.name());
        lore.add(ChatColor.GREEN + "Dificuldade: " + ChatColor.YELLOW + difficulty);
        lore.add(ChatColor.GREEN + "Bonus XP: " + ChatColor.YELLOW + expRatio);
        if(op) {
           lore.add(ChatColor.AQUA + "Click Direito: " + ChatColor.YELLOW + "+1 Dificuldade");
           lore.add(ChatColor.AQUA + "Click Esquerdo: " + ChatColor.YELLOW + "-1 Dificuldade");
           lore.add(ChatColor.AQUA + "Shift Click Direito: " + ChatColor.YELLOW + "+0.1 XP");
           lore.add(ChatColor.AQUA + "Shift Click Esquerdo: " + ChatColor.YELLOW + "-0.1 XP");
           lore.add(ChatColor.AQUA + "Mouse + Q: " + ChatColor.YELLOW + "Deletar");
        }
        meta.setLore(lore);
        ss.setItemMeta(meta);
        return ss;
    }

    public double expRatio;
    public Material m;
    public byte data = 0;
    public Jobs.Classe classe; // MEDIUM will be calculated
    public int difficulty;

}
