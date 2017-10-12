/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.RecipeBooks;

import java.util.ArrayList;
import java.util.List;
import nativelevel.Custom.CustomItem;
import nativelevel.Custom.Items.RecipeForBook;
import nativelevel.gemas.Raridade;
import nativelevel.playerboolean.Cache;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author User
 * 
 */

public class RecipePage {
    
    public static Cache<RecipePage> recipes = new Cache<RecipePage>();
    
    public void createPage(String name, String text, BookTypes bookType) {
        this.name = name;
        this.text = text;
        this.bookType = bookType;
        ItemStack ss = CustomItem.getItem(RecipeForBook.class).generateItem(1);
        ItemMeta meta = ss.getItemMeta();
        List<String> lore = meta.getLore();
        String itemName = BookTypes.getRecipeTitle(bookType);
        lore.add(0,ChatColor.GREEN+itemName+" de "+bookType.name());
        lore.add(1,ChatColor.GREEN+bookType.name()+": "+name);
        meta.setDisplayName(Raridade.Raro.getIcone()+ChatColor.YELLOW+" "+name);
        meta.setLore(lore);
        ss.setItemMeta(meta);
        this.item = ss;
    }
    
    public ItemStack getItem() {
        return item;
    }
    
    public static RecipePage getPage(ItemStack page) {
        ItemMeta meta = page.getItemMeta();
        String recipeName = meta.getLore().get(1).split(":")[1].trim();
        return recipes.getCached(recipeName);
    }
    
    public String name;
    public String text;
    public BookTypes bookType;
    public ItemStack item;
}
