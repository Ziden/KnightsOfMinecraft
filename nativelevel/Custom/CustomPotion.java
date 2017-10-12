package nativelevel.Custom;

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
import org.bukkit.Color;
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
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

/**
 *
 * @author vntgasl
 *
 */
public abstract class CustomPotion implements HaveRecipe {

    public void consome(Player player) {
        if (player.getItemInHand().getAmount() > 1) {
            player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
        } else {
            player.setItemInHand(null);
        }
    }

    public PotionType m;
    public List<String> lore;
    public String name;
    public boolean isSplash = false;
    private boolean blockIteract = true;

    public CustomPotion(String nome, String descEffect, PotionType type, boolean isSplash) {
        this.m = type;
        this.isSplash = isSplash;
        this.lore = new ArrayList<String>();
        this.name = nome;
        this.lore.add(ChatColor.GRAY + "-" + descEffect);
        this.lore.add(ChatColor.BLACK + ":" + this.name);
        if (!isSplash) {
            this.blockIteract = false;
        }
    }

    public void specialThreat(ItemStack ss) {

    }

    public static void mostra(Player p) {
        Inventory inv = Bukkit.createInventory(p, 9 * 6, "Custom Potions");
        for (CustomPotion cu : PotionLoader.customItems.values()) {
            inv.addItem(cu.generateItem(1));
        }
        p.openInventory(inv);
    }

    public static void showRecipes(Player p) {
        Inventory inv = Bukkit.createInventory(p, 9 * 6, "Potions Recipes");
        for (CustomPotion cu : PotionLoader.customItems.values()) {
            inv.addItem(cu.generateRecipe().getItem());
        }
        p.openInventory(inv);
    }

    public static String getRecipeName(Class<? extends CustomPotion> type) {
        return CustomPotion.getItem(type).name;
    }

    public abstract void interage(PlayerInteractEvent ev);

    public abstract void splashEvent(PotionSplashEvent ev, Player quemJogou);

    public abstract ItemStack[] getRecipe();

    public abstract double getExpRatio();

    public abstract int getMinimumSkill();

    public abstract ItemStack brewWith();
    
    public abstract Color cor();

    public abstract void drink(PlayerItemConsumeEvent ev);

    public boolean blockInteract() {
        return blockIteract;
    }

    public RecipePage generateRecipe() {
        RecipePage page = new RecipePage();

        String text = ChatColor.RED + L.m("Ingredientes:\n\n");
        for (ItemStack ss : getRecipe()) {
            String name = LangMinecraft.get().get(ss);
            text += (ChatColor.DARK_BLUE + "- " + ss.getAmount() + "x " + name + "\n");
        }
        text += ChatColor.DARK_BLUE + L.m("Esquente depois com " + ChatColor.RED) + LangMinecraft.get().get(brewWith()) + "\n";
        text += ChatColor.BLUE + L.m("Dificuldade: ") + Jobs.getNomeDificuldade(getMinimumSkill());
        page.createPage(name, text, BookTypes.Alquimia);
        return page;
    }

    public static void displayRecipes() {
    }

    public static CustomPotion getCustomItem(ItemStack s) {
        if (s == null) {
            return null;
        }
        ItemMeta m = s.getItemMeta();
        if (m == null || m.getLore() == null || m.getLore().size() == 0) {
            return null;
        }
        String last = m.getLore().get(m.getLore().size() - 1);
        if (last.length() < 3) {
            return null;
        }
        String[] split = last.split(":");
        if (split.length != 2) {
            return null;
        }
        return getItem(split[1]);
    }

    public ItemStack generateItem(int potionStrength) {
        Potion p = new Potion(m);
        p.setLevel(potionStrength);
        p.setSplash(isSplash);
        ItemStack item = p.toItemStack(1);
        ItemMeta meta = item.getItemMeta();
        PotionMeta pot = (PotionMeta) meta;
        pot.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        pot.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);

        item.setItemMeta(pot);
       
        
        PotionMeta potmeta = (PotionMeta)item.getItemMeta();
        potmeta.clearCustomEffects();
        potmeta.setColor(this.cor());
        potmeta.setBasePotionData(new PotionData(PotionType.MUNDANE,false,false));
     
        item.setItemMeta(potmeta);
        setItemNameAndLore(item, ChatColor.GOLD + name, lore.toArray(new String[lore.size()]));
        specialThreat(item);
        return item;
    }

    public static CustomPotion getItem(Class<? extends CustomPotion> item) {
        if (PotionLoader.customItemsClass.containsKey(item)) {
            return PotionLoader.customItemsClass.get(item);
        }
        return null;
    }

    public static CustomPotion getItem(String nome) {
        if (PotionLoader.customItems.containsKey(nome)) {
            return PotionLoader.customItems.get(nome);
        }
        return null;
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
