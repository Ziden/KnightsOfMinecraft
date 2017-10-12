/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.Classes.Mage;

import nativelevel.Jobs;
import nativelevel.RecipeBooks.BookTypes;
import nativelevel.RecipeBooks.HaveRecipe;
import nativelevel.RecipeBooks.RecipePage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

/**
 *
 * @author User
 * 
 */
public abstract class MageSpell implements HaveRecipe {

    public MageSpell(String name) {
        this.name = name;
    }
    
    public String name;
    
    public abstract void cast(Player caster);
    public abstract double getManaCost();
    public abstract double getExpRatio();
    public abstract int getMinSkill();
    public abstract Elements [] getElements();
    public abstract int getCooldownInSeconds();
    
    public void spellHit(LivingEntity hit, Location l, Projectile p) {
        
    }
    
    public static void showRecipes(Player p) {
        Inventory inv = Bukkit.createInventory(p, InventoryType.CHEST, "Mage Scrolls");
        for (MageSpell cu : SpellLoader.spells.values()) {
            inv.addItem(cu.generateRecipe().getItem());
        }
        p.openInventory(inv);
    }
    
    @Override
    public RecipePage generateRecipe() {
         RecipePage page = new RecipePage();

        String text = ChatColor.GREEN + "Elementos:\n\n";
        for (Elements ss : getElements()) {
            text += (ChatColor.BLACK + "- " +ss.icon + " "+ss.name() + "\n");
        }
        text+= ChatColor.DARK_BLUE+"Custo de Mana:"+ChatColor.DARK_GREEN+getManaCost()+"\n";
        text+= ChatColor.DARK_BLUE+"Dificuldade: "+Jobs.getNomeDificuldade(getMinSkill())+"\n";
        text+= ChatColor.DARK_BLUE+"Cooldown: "+ChatColor.DARK_GREEN+getCooldownInSeconds();
        page.createPage(name, text, BookTypes.Magia);
        return page;
    }
    
}
