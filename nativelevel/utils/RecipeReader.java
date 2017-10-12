package nativelevel.utils;

import java.util.List;
import java.util.Map;
import nativelevel.Lang.LangMinecraft;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

public class RecipeReader {

    private Inventory inv;

    public RecipeReader(Recipe recipe, Player p) {
        ItemStack [] matrix = null;
        if ((recipe instanceof ShapelessRecipe)) {
            matrix = getMatrix((ShapelessRecipe)recipe); 
        } else if ((recipe instanceof ShapedRecipe)) {
             matrix = getMatrix((ShapedRecipe)recipe);  
        }
       
        TableGenerator tabela = new TableGenerator(TableGenerator.Alignment.CENTER, TableGenerator.Alignment.CENTER, TableGenerator.Alignment.CENTER);

        String msg = "Para se criar "+LangMinecraft.get().get(recipe.getResult())+" Precisa-se: ";
        for(int x = 0 ; x < matrix.length ; x+=3) {
            tabela.addRow(matrix[x]==null ? "Nada" : LangMinecraft.get().get(matrix[x]),
                    matrix[x+1]==null ? "Nada" : LangMinecraft.get().get(matrix[x+1]),
                    matrix[x+2]==null ? "Nada" : LangMinecraft.get().get(matrix[x+2]));
        }
        p.sendMessage(ChatColor.YELLOW+msg);
        for(String s : tabela.generate(TableGenerator.Receiver.CLIENT, true, true))
            p.sendMessage(ChatColor.GREEN+s);
        /*
        if ((recipe instanceof ShapelessRecipe)) {
            this.inv = Bukkit.createInventory(null, InventoryType.WORKBENCH, title);
            ShapelessRecipe shapeless = (ShapelessRecipe) recipe;
            ItemStack[] matrix = getMatrix(shapeless);
            this.inv.setItem(0, recipe.getResult());
            for (int i = 0; i < matrix.length; i++) {
                if (matrix[i] != null) {
                    this.inv.setItem(i + 1, matrix[i]);
                }
            }
        } else if ((recipe instanceof FurnaceRecipe)) {
            this.inv = Bukkit.createInventory(null, InventoryType.FURNACE, title);
            FurnaceRecipe furnace = (FurnaceRecipe) recipe;
            ItemStack src = furnace.getInput();
            if (src.getDurability() == Short.MAX_VALUE) {
                src.setDurability((short) 0);
            }
            this.inv.setItem(0, src);
            this.inv.setItem(1, new ItemStack(Material.COAL));
            this.inv.setItem(2, furnace.getResult());
        } else if ((recipe instanceof ShapedRecipe)) {
            this.inv = Bukkit.createInventory(null, InventoryType.WORKBENCH, title);
            ShapedRecipe shaped = (ShapedRecipe) recipe;
            ItemStack[] matrix = getMatrix(shaped);
            this.inv.setItem(0, recipe.getResult());
            for (int i = 0; i < matrix.length; i++) {
                if (matrix[i] != null) {
                    this.inv.setItem(i + 1, matrix[i]);
                }
            }
        }
         */
    }

    private ItemStack[] getMatrix(ShapelessRecipe recipe) {
        ItemStack[] items = new ItemStack[9];
        List<ItemStack> list = recipe.getIngredientList();
        for (int i = 0; i < list.size(); i++) {
            items[i] = ((ItemStack) list.get(i));
            if ((items[i] != null)
                    && (items[i].getDurability() == Short.MAX_VALUE)) {
                items[i].setDurability((short) 0);
            }
        }
        return items;
    }

    private String nineChars(String[] shape) {
        String f = "";
        if (shape.length == 1) {
            f = f + "      ";
        } else if (shape.length == 2) {
            f = f + "   ";
        }
        String[] arrayOfString;
        int j = (arrayOfString = shape).length;
        for (int i = 0; i < j; i++) {
            String row = arrayOfString[i];
            if (row.length() == 1) {
                f = f + "  ";
            } else if (row.length() == 2) {
                f = f + " ";
            }
            f = f + row;
        }
        return f;
    }

    private ItemStack[] getMatrix(ShapedRecipe recipe) {
        ItemStack[] items = new ItemStack[9];
        String chars = nineChars(recipe.getShape());
        for (int i = 0; i < 9; i++) {
            items[i] = ((ItemStack) recipe.getIngredientMap().get(Character.valueOf(chars.charAt(i))));
            if ((items[i] != null)
                    && (items[i].getDurability() == Short.MAX_VALUE)) {
                items[i].setDurability((short) 0);
            }
        }
        return items;
    }

    public Inventory display() {
        return this.inv;
    }
}
