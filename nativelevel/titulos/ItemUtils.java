package nativelevel.titulos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemUtils {

    public static ItemStack getHead(String name)
    {
        ItemStack is = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
        SkullMeta meta = (SkullMeta) is.getItemMeta();
        meta.setOwner(name);
        is.setItemMeta(meta);
        return is;
    }

    public static ItemStack SetItemName(ItemStack i, String name)
    {
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(name);
        i.setItemMeta(im);
        return i;
    }

    public static String GetItemName(ItemStack i)
    {
        if (i == null)
        {
            return null;
        }
        String itemname = "";
        ItemMeta im = i.getItemMeta();
        if (im == null)
        {
            return null;
        }
        if (!i.hasItemMeta())
        {
            itemname = i.getType().name();
        }
        else if (im.getDisplayName() != null)
        {
            itemname = im.getDisplayName();
        }
        else
        {
            itemname = i.getType().name();
        }
        return itemname;
    }

    public static ItemStack AddLore(ItemStack i, String lore)
    {
        ItemMeta im = i.getItemMeta();
        List<String> aux = new ArrayList();
        if (im.getLore() == null || im.getLore().size() == 0)
        {
            aux.add(lore);
            im.setLore(aux);
            i.setItemMeta(im);
            return i;
        }
        else
        {
            aux = im.getLore();
            aux.add(lore);
            im.setLore(aux);
            i.setItemMeta(im);
            return i;
        }
    }

    public static List<String> GetLore(ItemStack i)
    {
        ItemMeta im = i.getItemMeta();
        return im.getLore();
    }

    public static ItemStack SetLore(ItemStack i, List<String> l)
    {
        ItemMeta im = i.getItemMeta();
        im.setLore(l);
        i.setItemMeta(im);
        return i;
    }

    public static ItemStack ClearLore(ItemStack i)
    {
        List<String> aux = new ArrayList();
        ItemMeta im = i.getItemMeta();
        im.setLore(aux);
        i.setItemMeta(im);
        return i;
    }

    public static ItemStack addColor(ItemStack item, Color cor)
    {
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
        meta.setColor(cor);
        item.setItemMeta(meta);
        return item;
    }

    public static String getItemName(ItemStack is)
    {
        if (is == null)
        {
            return null;
        }
        if (is.getItemMeta() == null)
        {
            return null;
        }
        if (is.getItemMeta().getDisplayName() == null)
        {
            return null;
        }
        return is.getItemMeta().getDisplayName();
    }

    /*
     MineP    
     */
    public static org.bukkit.inventory.ItemStack CreateStack(Material type)
    {
        return CreateStack(type.getId(), (byte) 0, 1, (short) 0, null, new String[0], null);
    }

    public static org.bukkit.inventory.ItemStack CreateStack(int id)
    {
        return CreateStack(id, (byte) 0, 1, (short) 0, null, new String[0], null);
    }

    public static org.bukkit.inventory.ItemStack CreateStack(Material type, int amount)
    {
        return CreateStack(type.getId(), (byte) 0, amount, (short) 0, null, new String[0], null);
    }

    public static org.bukkit.inventory.ItemStack CreateStack(int id, int amount)
    {
        return CreateStack(id, (byte) 0, amount, (short) 0, null, new String[0], null);
    }

    public static org.bukkit.inventory.ItemStack CreateStack(Material type, byte data)
    {
        return CreateStack(type.getId(), data, 1, (short) 0, null, new String[0], null);
    }

    public static org.bukkit.inventory.ItemStack CreateStack(int id, byte data)
    {
        return CreateStack(id, data, 1, (short) 0, null, new String[0], null);
    }

    public static org.bukkit.inventory.ItemStack CreateStack(Material type, byte data, int amount)
    {
        return CreateStack(type.getId(), data, amount, (short) 0, null, new String[0], null);
    }

    public static org.bukkit.inventory.ItemStack CreateStack(int id, byte data, int amount)
    {
        return CreateStack(id, data, amount, (short) 0, null, new String[0], null);
    }

    public static org.bukkit.inventory.ItemStack CreateStack(Material type, byte data, int amount, String name)
    {
        return CreateStack(type.getId(), data, amount, (short) 0, name, new String[0], null);
    }

    public static org.bukkit.inventory.ItemStack CreateStack(int id, byte data, int amount, String name)
    {
        return CreateStack(id, data, amount, (short) 0, name, new String[0], null);
    }

    public static org.bukkit.inventory.ItemStack CreateStack(Material type, byte data, int amount, String name, List<String> lore)
    {
        return CreateStack(type.getId(), data, amount, (short) 0, name, lore, null);
    }

    public static org.bukkit.inventory.ItemStack CreateStack(int id, byte data, int amount, String name, List<String> lore)
    {
        return CreateStack(id, data, amount, (short) 0, name, lore, null);
    }

    public static org.bukkit.inventory.ItemStack CreateStack(Material type, byte data, int amount, String name, String[] lore)
    {
        return CreateStack(type.getId(), data, amount, (short) 0, name, ArrayToList(lore), null);
    }

    public static org.bukkit.inventory.ItemStack CreateStack(int id, byte data, int amount, String name, String[] lore)
    {
        return CreateStack(id, data, amount, (short) 0, name, ArrayToList(lore), null);
    }

    public static org.bukkit.inventory.ItemStack CreateStack(Material type, byte data, int amount, short damage, String name, String[] lore)
    {
        return CreateStack(type.getId(), data, amount, damage, name, ArrayToList(lore), null);
    }

    public static org.bukkit.inventory.ItemStack CreateStack(int id, byte data, int amount, short damage, String name, String[] lore)
    {
        return CreateStack(id, data, amount, damage, name, ArrayToList(lore), null);
    }

    public static org.bukkit.inventory.ItemStack CreateStack(Material type, byte data, int amount, short damage, String name, List<String> lore)
    {
        return CreateStack(type.getId(), data, amount, damage, name, lore, null);
    }

    public static org.bukkit.inventory.ItemStack CreateStack(Material type, byte data, int amount, String name, List<String> lore, String owner)
    {
        return CreateStack(type.getId(), data, amount, (short) 0, name, lore, owner);
    }

    public static org.bukkit.inventory.ItemStack CreateStack(int id, byte data, int amount, String name, List<String> lore, String owner)
    {
        return CreateStack(id, data, amount, (short) 0, name, lore, owner);
    }

    public static org.bukkit.inventory.ItemStack CreateStack(Material type, byte data, int amount, String name, String[] lore, String owner)
    {
        return CreateStack(type.getId(), data, amount, (short) 0, name, ArrayToList(lore), owner);
    }

    public static org.bukkit.inventory.ItemStack CreateStack(int id, byte data, int amount, String name, String[] lore, String owner)
    {
        return CreateStack(id, data, amount, (short) 0, name, ArrayToList(lore), owner);
    }

    public static org.bukkit.inventory.ItemStack CreateStack(Material type, byte data, int amount, short damage, String name, String[] lore, String owner)
    {
        return CreateStack(type.getId(), data, amount, damage, name, ArrayToList(lore), owner);
    }

    public static org.bukkit.inventory.ItemStack CreateStack(int id, byte data, int amount, short damage, String name, String[] lore, String owner)
    {
        return CreateStack(id, data, amount, damage, name, ArrayToList(lore), owner);
    }

    public static org.bukkit.inventory.ItemStack CreateStack(Material type, byte data, int amount, short damage, String name, List<String> lore, String owner)
    {
        return CreateStack(type.getId(), data, amount, damage, name, lore, owner);
    }

    public static org.bukkit.inventory.ItemStack CreateStack(int id, byte data, int amount, short damage, String name, List<String> lore, String owner)
    {
        org.bukkit.inventory.ItemStack stack;
        if (data == 0)
        {
            stack = new org.bukkit.inventory.ItemStack(id, amount, damage);
        }
        else
        {
            stack = new org.bukkit.inventory.ItemStack(id, amount, damage, Byte.valueOf(data));
        }
        ItemMeta itemMeta = stack.getItemMeta();
        if (itemMeta == null)
        {
            return null;
        }
        boolean setMeta = false;
        if (name != null)
        {
            itemMeta.setDisplayName(name);
            setMeta = true;
        }
        if (lore != null)
        {
            if (itemMeta.getLore() != null)
            {
                itemMeta.setLore(CombineLore(itemMeta.getLore(), lore));
            }
            else
            {
                itemMeta.setLore(lore);
            }
            setMeta = true;
        }
        if (setMeta)
        {
            stack.setItemMeta(itemMeta);
        }
        return stack;
    }

    private static List<String> CombineLore(List<String> A, List<String> B)
    {
        for (String b : B)
        {
            A.add(b);
        }
        return A;
    }

    public static List<String> ArrayToList(String[] array)
    {
        if (array.length == 0)
        {
            return null;
        }
        List list = new ArrayList();
        for (String cur : array)
        {
            list.add(cur);
        }
        return list;
    }

    public static String GetLoreVar(org.bukkit.inventory.ItemStack stack, String var)
    {
        if (stack == null)
        {
            return null;
        }
        ItemMeta meta = stack.getItemMeta();
        if (meta == null)
        {
            return null;
        }
        if (meta.getLore() == null)
        {
            return null;
        }
        for (String cur : meta.getLore())
        {
            if (cur.contains(var))
            {
                int index = var.split(" ").length;
                String[] tokens = cur.split(" ");
                String out = "";
                for (int i = index; i < tokens.length; i++)
                {
                    out = out + tokens[i] + " ";
                }
                if (out.length() > 0)
                {
                    out = out.substring(0, out.length() - 1);
                }
                return out;
            }
        }
        return null;
    }

    public static int GetLoreVar(org.bukkit.inventory.ItemStack stack, String var, int empty)
    {
        if (stack == null)
        {
            return empty;
        }
        ItemMeta meta = stack.getItemMeta();
        if (meta == null)
        {
            return 0;
        }
        if (meta.getLore() == null)
        {
            return 0;
        }
        for (String cur : meta.getLore())
        {
            if (cur.contains(var))
            {
                String[] tokens = cur.split(" ");
                try
                {
                    return Integer.parseInt(tokens[(tokens.length - 1)]);
                }
                catch (Exception e)
                {
                    return empty;
                }
            }
        }
        return 0;
    }

    public static List<String> formatLore(String text)
    {
        List<String> lore = new ArrayList<String>();
        lore.addAll(Arrays.asList(text.split("@")));
        return lore;
    }
}
