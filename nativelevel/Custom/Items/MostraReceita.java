/*

 ╭╮╭━╮╱╱╭━╮╭━╮
 ┃┃┃╭╯╱╱┃┃╰╯┃┃
 ┃╰╯╯╭━━┫╭╮╭╮┃
 ┃╭╮┃┃╭╮┃┃┃┃┃┃
 ┃┃┃╰┫╰╯┃┃┃┃┃┃
 ╰╯╰━┻━━┻╯╰╯╰╯

 Desenvolvedor: ZidenVentania
 Colaboradores: NeT32, Gabripj, Feldmann
 Patrocionio: InstaMC

 */
package nativelevel.Custom.Items;

import java.util.ArrayList;
import java.util.List;
import nativelevel.Custom.CustomItem;
import nativelevel.KoM;
import nativelevel.Lang.L;
import nativelevel.utils.RecipeReader;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.ItemMeta;

public class MostraReceita extends CustomItem {

    public MostraReceita() {
        super(Material.BOOK, L.m("Aprenda a Criar"), L.m("Saiba como criar o item"), CustomItem.RARO);
    }

    public ItemStack fazReceita(Recipe r) {
        ItemStack ss = this.generateItem();
        CustomItem ci = CustomItem.getItem(r.getResult());
        if (ci != null) {
            ItemMeta meta = ss.getItemMeta();
            List<String> lore = (meta.getLore() == null ? new ArrayList<String>() : new ArrayList<String>(meta.getLore()));
            lore.add(0,ChatColor.GREEN+ci.getName());
            meta.setLore(lore);
            ss.setItemMeta(meta);
        }
        return ss;
    }

    @Override
    public boolean onItemInteract(Player p) {
        String receita = ChatColor.stripColor(p.getItemInHand().getItemMeta().getLore().get(0));
        KoM.debug("RECEITA = "+receita);
        CustomItem ci = CustomItem.getByName(receita);
        if( ci != null ) {
            for(Recipe r : KoM.receitasCustom) {
                CustomItem ci2 = CustomItem.getItem(r.getResult());
                if(ci2 != null) {
                    if(ci2.getName().equalsIgnoreCase(ci.getName())) {
                        RecipeReader reader = new RecipeReader(r, p);
                        //Inventory i = reader.display();
                        //p.openInventory(i);
                        //p.sendMessage(ChatColor.GREEN+"Voce está vendo como craftar o item");
                        break;
                        
                    }
                    
                }
                
            }
            
        }
        
        return true;
    }
}
