/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.Equipment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import nativelevel.Equipment.EquipMeta;
import nativelevel.KoM;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author vntgasl
 *
 */
public class ItemAttributes {

    public static void subAttribute(ItemStack ss, Atributo attribute, int qtd) {
        ItemMeta meta = ss.getItemMeta();
        List<String> lore = meta.getLore();
        if (lore == null) {
            lore = new ArrayList<String>();
        }
        // if already have this attribute, we sum
        for (String loreIn : lore) {
            if (loreIn.startsWith("§9+ ") && loreIn.endsWith(attribute.name().replace("_", " "))) {
                int hasAttribute = Integer.valueOf(loreIn.split(" ")[1]);
                hasAttribute -= qtd;
                if (hasAttribute < 0) {
                    hasAttribute = 0;
                }
                lore.remove(loreIn);
                String pct = attribute.pct ? "%" : "";
                lore.add(0, "§9+ " + hasAttribute + pct+ " " + attribute.name().replace("_", " "));
                meta.setLore(lore);
                ss.setItemMeta(meta);
                return;
            }
        }
        return;
    }

    public static EquipMeta getAttributes(ItemStack ss) {
        HashMap<Atributo, Double> attrs = new HashMap();
        ItemMeta meta = ss.getItemMeta();
        if (meta == null) {
            return new EquipMeta(attrs);
        }
        List<String> lore = meta.getLore();
        if (lore == null) {
            lore = new ArrayList<String>();
        }
        // if already have this attribute, we sum
        for (String loreIn : lore) {
            if (loreIn.startsWith("§9+")) {
                String attrName = "";
                //+9 Physical Damage
                for (int x = 1; x < loreIn.split(" ").length; x++) {
                    attrName += loreIn.split(" ")[x];
                    if (x != loreIn.split(" ").length - 1) {
                        attrName += "_";
                    }
                }
                try {
                Atributo attr = Atributo.valueOf(attrName);
                double value = Integer.valueOf(loreIn.split("\\+")[1].split(" ")[0].replace("%", ""));

                attrs.put(attr, value);
                } catch(Exception e){
                    
                }
            }
        }
        return new EquipMeta(attrs);
    }
    //is: AA0069KI74

    public static int getAttribute(ItemStack ss, Atributo attribute) {
        ItemMeta meta = ss.getItemMeta();
        List<String> lore = meta.getLore();
        if (lore == null) {
            lore = new ArrayList<String>();
        }
        // if already have this attribute, we sum
        for (String loreIn : lore) {
            if (loreIn.startsWith("§9+") && loreIn.endsWith(attribute.name())) {
                return Integer.valueOf(loreIn.split("\\+")[1].split(" ")[0].replace("%", ""));
            }
        }
        return 0;
    }

    public static void addAttribute(ItemStack ss, Atributo attribute, int qtd) {
        ItemMeta meta = ss.getItemMeta();
        KoM.debug("addando " + qtd + " " + attribute.name());
        List<String> lore = meta.getLore();
        if (lore == null) {
            lore = new ArrayList<String>();
        }
        // if already have this attribute, we sum
        for (String loreIn : lore) {
            if (loreIn == null) {
                continue;
            }
            KoM.debug("Ja tinha lore: "+lore);
            if (loreIn.startsWith("§9+") && loreIn.endsWith(attribute.name().replace("_", " "))) {
                int hasAttribute = Integer.valueOf(ChatColor.stripColor(loreIn.split(" ")[0].replace("%", "")));
                hasAttribute += qtd;
                lore.remove(loreIn);
                String pct = attribute.pct ? "%" : "";
                lore.add(0, "§9+" + hasAttribute + (pct) + " " + attribute.name().replace("_", " "));
                meta.setLore(lore);
                ss.setItemMeta(meta);
                return;
            }
        }
        String pct = attribute.pct ? "%" : "";
        lore.add(0, "§9+" + qtd + pct + " " + attribute.name().replace("_", " "));
        meta.setLore(lore);
        ss.setItemMeta(meta);
        return;
    }

}
