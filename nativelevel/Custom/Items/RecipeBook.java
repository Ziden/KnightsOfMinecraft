/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.Custom.Items;

import java.util.ArrayList;
import java.util.List;
import nativelevel.Custom.CustomItem;
import nativelevel.KoM;
import nativelevel.Lang.L;
import nativelevel.gemas.Raridade;
import nativelevel.RecipeBooks.BookTypes;
import nativelevel.RecipeBooks.RecipePage;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

/**
 *
 * @author User
 */
public class RecipeBook extends CustomItem {

    public RecipeBook() {
        super(Material.WRITTEN_BOOK, L.m("Livro de Receitas"), L.m("Coloque Paginas Aqui"), CustomItem.INCOMUM);
    }

    public static ItemStack createBook(BookTypes type) {
        ItemStack ss = CustomItem.getItem(RecipeBook.class).generateItem(1);
        ItemMeta meta = ss.getItemMeta();
        String nome = BookTypes.getRecipeTitle(type);
        List<String> lore = meta.getLore();
        lore.add(0, ChatColor.YELLOW + L.m(nome+"s de ") + type.name());
        meta.setDisplayName(Raridade.Comum.getIcone()+ChatColor.YELLOW + L.m(" Livro de ") + type.name());
        meta.setLore(lore);
        ss.setItemMeta(meta);
        BookMeta bookMeta = (BookMeta) ss.getItemMeta();
        bookMeta.setAuthor("Ninguem...");
        bookMeta.addPage(BookTypes.getFirstPage(type));
        ss.setItemMeta(bookMeta);
        return ss;
    }

    public void addToBook(ItemStack book, RecipePage recipe) {
        BookMeta meta = (BookMeta) book.getItemMeta();
        if (meta.getPages() == null) {
            meta.setPages(new ArrayList<String>());
        }
        String nome = BookTypes.getRecipeTitle(recipe.bookType);
        List<String> pages = new ArrayList<String>(meta.getPages());
        String fullPage = ChatColor.BLUE+nome + " de: "+ChatColor.RED + recipe.name + " \n\n" + recipe.text;
        pages.add(fullPage);
        meta.setPages(pages);
        book.setItemMeta(meta);
    }

    public List<String> getRecipes(ItemStack book) {
        BookMeta meta = (BookMeta) book.getItemMeta();
        List<String> recipes = new ArrayList<String>();
        if (meta.getPages() == null || meta.getPages().size() == 0) {
            return recipes;
        }
        for (int x = 1; x < meta.getPages().size(); x++) {
            String page = meta.getPages().get(x);
            String primeira = page.split("\n")[0];
            KoM.debug("PRIMEIRA: "+primeira);
            String recipeName = ChatColor.stripColor(primeira.split(":")[1]).trim();
            KoM.debug("RECEITA: "+recipeName);
            recipes.add(recipeName);
        }
        return recipes;
    }

    public BookTypes getBookType(ItemStack book) {
        ItemMeta meta = book.getItemMeta();
        String firstLine = meta.getLore().get(0);
        try {
            return BookTypes.valueOf(firstLine.split(" ")[2]);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void posCriacao(ItemStack ss) {
        BookMeta meta = (BookMeta) ss.getItemMeta();
        meta.setAuthor("Ninguem...");
        ss.setItemMeta(meta);
    }

    @Override
    public boolean onItemInteract(Player p) {
        return true;
    }

}
