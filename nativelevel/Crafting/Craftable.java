/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.Crafting;

import java.util.ArrayList;
import java.util.List;
import nativelevel.Custom.CustomItem;
import nativelevel.Jobs;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author User
 */
public class Craftable {

    public Jobs.Classe classe;
    public int diff;
    public Material m;
    public byte data;
    public double expRatio;
    public CustomItem customItem = null;

    public Craftable(CustomItem customItem, int minSkill, Jobs.Classe skillsNeeded, double expRatio) {
        this.customItem = customItem;
        this.diff = minSkill;
        this.classe = skillsNeeded;
        this.expRatio = expRatio;
        ItemStack custom = customItem.generateItem();
        this.m = custom.getType();
        this.data = custom.getData().getData();
    }

    public Craftable(Material m, byte data, int minSkill, Jobs.Classe skillsNeeded, double expRatio) {
        this.m = m;
        this.data = data;
        this.diff = minSkill;
        this.classe = skillsNeeded;
        this.expRatio = expRatio;
    }

    public ItemStack getIcon(boolean op) {
        if (customItem == null) {
            ItemStack ss = new ItemStack(m, 1, data);
            ItemMeta meta = ss.getItemMeta();
            List<String> lore = new ArrayList<String>();
            lore.add(ChatColor.YELLOW + classe.name());
            lore.add(ChatColor.GREEN + "Dificuldade: " + diff);
            lore.add(ChatColor.GREEN + "XP Ratio: " + expRatio);
            if (op) {
                lore.add(ChatColor.AQUA + "Click Direito: " + ChatColor.YELLOW + "+1 Dificuldade");
                lore.add(ChatColor.AQUA + "Click Esquerdo: " + ChatColor.YELLOW + "-1 Dificuldade");
                lore.add(ChatColor.AQUA + "Shift Click Direito: " + ChatColor.YELLOW + "+0.1 XP");
                lore.add(ChatColor.AQUA + "Shift Click Esquerdo: " + ChatColor.YELLOW + "-0.1 XP");
                lore.add(ChatColor.AQUA + "Mouse + Q: " + ChatColor.YELLOW + "Deletar");
            }
            meta.setLore(lore);
            ss.setItemMeta(meta);
            return ss;
        } else {
            return customItem.generateItem();
        }
    }

}
