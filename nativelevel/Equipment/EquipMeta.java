/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.Equipment;

import java.util.HashMap;
import java.util.Set;
import nativelevel.KoM;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
/**
 *
 * @author vntgasl
 */
public class EquipMeta {

    public EquipMeta(HashMap<Atributo, Double> a) {
        this.attributes = a;
    }
    
    public EquipMeta() {
        this.attributes = new HashMap<Atributo, Double>();
    }
    
    public Set<Atributo> getAtributos() {
        return this.attributes.keySet();
    }

    private HashMap<Atributo, Double> attributes = new HashMap();

    public void setAttribute(Atributo attr, double n) {
        attributes.put(attr, n);
    }
    
    public static int getNivelItem(ItemStack ss) {
        ItemMeta meta = ss.getItemMeta();
        if(meta!=null && meta.getLore()!=null) {
            for(String s : meta.getLore()) {
                if(s.contains("Nivel do Item")) {
                    return Integer.valueOf(ChatColor.stripColor(s.split(":")[1]).trim());
                }
            }
        }
        return -1;
    }
    
    public boolean isEqual(EquipMeta meta) {
        if(meta == null)
            return false;
        for(Atributo a : Atributo.values()) {
            if(meta.getAttribute(a) != this.getAttribute(a)) {
                KoM.debug("Meta nao bateu "+a.name()+" "+meta.getAttribute(a)+" "+this.getAttribute(a));
                return false;
            }
                
        }
        return true;
    }
    
    public double getAttribute(Atributo attr) {
        if(attributes.containsKey(attr))
            return attributes.get(attr);
        else
            return 0;
    }
    
    public static int getAttribute(Player p) {
        return 0;
    }

    public static void subMeta(EquipMeta meta, EquipMeta toSubtract) {
        for (Atributo attr : toSubtract.attributes.keySet()) {
            if (meta.attributes.containsKey(attr)) {
                double actual = meta.attributes.get(attr);
                actual -= toSubtract.attributes.get(attr);
                if (actual > 0) {
                    meta.attributes.put(attr, actual);
                } else {
                    meta.attributes.remove(attr);
                }
            } //else {
                //KoM.log.info("[ERROR] TRYNG TO REMOVE ATTRIBUTE FROM NON EXISTANT ATTRIBUTE !!! (Damage.Equipment.EquipMeta.java)");
            //}
        }
    }

    public static void addMeta(EquipMeta meta, EquipMeta toAdd) {
        for (Atributo attr : toAdd.attributes.keySet()) {
            if (meta.attributes.containsKey(attr)) {
                double actual = meta.attributes.get(attr);
                actual += toAdd.attributes.get(attr);
                meta.attributes.put(attr, actual);
            } else {
                meta.attributes.put(attr, toAdd.attributes.get(attr));
            }
        }
    }

}
