package nativelevel.biblioteca;

import java.util.ArrayList;
import java.util.List;
import nativelevel.Custom.CustomItem;
import nativelevel.Custom.ItemLoader;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

/**
 *
 * @author Ziden
 *
 */
public class Bibliotecario {

    
    // TERMINAR DEPOIS...aff
    public static void pesquisa(String texto) {

        List<String> paginas = new ArrayList<String>();

        Material m = null;
        ItemStack icone = null;
        
        try {

            m = Material.valueOf(texto);
            icone = new ItemStack(m, 1);

        } catch (Exception e) {

        }

        if (icone != null) {
            List<Recipe> receitas = Bukkit.getRecipesFor(icone);
        }

        // ta foda, pensando...
    }

}
