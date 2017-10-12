package nativelevel.RecipeBooks;

import java.util.List;
import nativelevel.Custom.CustomItem;
import nativelevel.Custom.Items.RecipeBook;
import nativelevel.Custom.Items.RecipeForBook;
import nativelevel.KoM;
import nativelevel.Lang.L;
import nativelevel.sisteminhas.KomSystem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

/**
 *
 * @author User
 *
 */
public class RecipeBooksEvents extends KomSystem {

    public static boolean ehDono(Player p, ItemStack livro) {
        BookMeta meta = (BookMeta) livro.getItemMeta();
        return meta.getAuthor() != null && meta.getAuthor().equalsIgnoreCase(p.getName());
    }

    @EventHandler
    public void click(InventoryClickEvent ev) {
        if (ev.getWhoClicked().getType() == EntityType.PLAYER && ev.getCurrentItem() != null && ev.getCurrentItem().getType() == Material.WRITTEN_BOOK && ev.getCursor() != null && ev.getCursor().getType() == Material.PAPER) {

            CustomItem item = CustomItem.getItem(ev.getCurrentItem());
            if (item != null && item instanceof RecipeBook) {
                RecipeBook book = (RecipeBook) item;
                BookTypes bookType = book.getBookType(ev.getCurrentItem());

                BookMeta meta = (BookMeta) ev.getCurrentItem().getItemMeta();
                if (meta.getAuthor() == null || meta.getAuthor().equalsIgnoreCase("ninguem...")) {
                    meta.setAuthor(ev.getWhoClicked().getName());
                    ev.getCurrentItem().setItemMeta(meta);
                }

                CustomItem recipe = CustomItem.getItem(ev.getCursor());
                if (recipe != null && recipe instanceof RecipeForBook) {
                    RecipeForBook recipePage = (RecipeForBook) recipe;
                    BookTypes recipeType = recipePage.getBookType(ev.getCursor());
                    Player p = (Player) ev.getWhoClicked();
                    if (!recipeType.name().equalsIgnoreCase(bookType.name())) {
                        p.sendMessage(ChatColor.RED + L.m("Este livro só aceita " + BookTypes.getRecipeTitle(bookType) + " de ") + bookType.name());
                        ev.setCancelled(true);
                        return;
                    }

                    List<String> recipes = book.getRecipes(ev.getCurrentItem());

                    RecipePage page = RecipePage.getPage(ev.getCursor());
                    if (page == null) {
                        KoM.debug("ERRORRRR: PAGE IS NULL !!");
                    }
                    if (recipes != null && recipes.contains(page.name)) {
                        p.sendMessage(ChatColor.RED + L.m("Este livro ja possui essa " + BookTypes.getRecipeTitle(bookType)));
                        ev.setCancelled(true);
                        ev.setCursor(page.getItem());
                        return;
                    }
                    book.addToBook(ev.getCurrentItem(), page);
                    ev.setResult(Event.Result.DENY);
                    ev.setCursor(new ItemStack(Material.AIR));
                    p.sendMessage(ChatColor.GREEN + L.m("Você adicionou a página ao seu livro de ") + bookType.name());
                }
            }

        }
    }

}
