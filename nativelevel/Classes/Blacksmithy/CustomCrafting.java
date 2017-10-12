package nativelevel.Classes.Blacksmithy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nativelevel.Jobs;
import nativelevel.Lang.L;
import nativelevel.Lang.LangMinecraft;
import nativelevel.RecipeBooks.BookTypes;
import nativelevel.RecipeBooks.HaveRecipe;
import nativelevel.RecipeBooks.RecipePage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

/**
 *
 * @author vntgasl
 *
 */
public abstract class CustomCrafting implements HaveRecipe {

    public void consome(Player player) {
        if (player.getItemInHand().getAmount() > 1) {
            player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
        } else {
            player.setItemInHand(null);
        }
    }

    public List<String> lore;
    public String name;

    public CustomCrafting(String nome, String descEffect) {
        this.lore = new ArrayList<String>();
        this.name = nome;
        this.lore.add(ChatColor.GRAY + "-" + descEffect);
    }

    public static CustomCrafting getItem(String nome) {
        if (RecipeLoader.customItems.containsKey(nome)) {
            return RecipeLoader.customItems.get(nome);
        }
        return null;
    }

    public static void showRecipes(Player p) {
        Inventory inv = Bukkit.createInventory(p, 9 * 6, "Crafting Recipes");
        for (CustomCrafting cu : RecipeLoader.customItems.values()) {
            inv.addItem(cu.generateRecipe().getItem());
        }
        p.openInventory(inv);
    }

    public abstract ItemStack[] getRecipe();

    public abstract double getExpRatio();

    public abstract int getMinimumSkill();

    public abstract int getHammerHits();

    public abstract ItemStack getItemPrincipal();

    public abstract ItemStack aplica(ItemStack ss);

    public abstract ItemStack aplicaNoCraftNormal(ItemStack ss);

    public RecipePage generateRecipe() {
        RecipePage page = new RecipePage();

        String text = ChatColor.DARK_BLUE + L.m("Item Na Bigorna: " + ChatColor.RED) + LangMinecraft.get().get(this.getItemPrincipal()) + ChatColor.DARK_BLUE + "\n";
        text += ChatColor.DARK_BLUE + L.m("Materiais:\n");
        for (ItemStack ss : getRecipe()) {
            String name = LangMinecraft.get().get(ss);
            text += (ChatColor.RED + "- " + ss.getAmount() + "x " + name + "\n");
        }
        text += ChatColor.DARK_BLUE + L.m("Bata com uma pá " + ChatColor.RED) + getHammerHits() + ChatColor.DARK_BLUE + " vezes.\n";
        text += ChatColor.BLUE + L.m("Dificuldade: ") + ChatColor.DARK_RED + Jobs.getNomeDificuldade(getMinimumSkill());
        page.createPage(name, text, BookTypes.Ferraria);
        return page;
    }

    public static void displayRecipes() {

    }

    public static ItemStack setItemNameAndLore(ItemStack item, String name, String... lore) {
        for (int i = 0; i < lore.length; i++) {
            lore[i] = ChatColor.RESET + lore[i];
        }
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(ChatColor.RESET + name);
        im.setLore(Arrays.asList(lore));
        item.setItemMeta(im);
        return item;
    }

}
