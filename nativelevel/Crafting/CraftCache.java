/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.Crafting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import nativelevel.Custom.CustomItem;
import nativelevel.Jobs;
import nativelevel.KoM;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author User
 *
 */
public class CraftCache {

    private static List<Craftable> craftaveis = new ArrayList<Craftable>();
    private static HashMap<Jobs.Classe, HashSet<Craftable>> craftBySkill = new HashMap<Jobs.Classe, HashSet<Craftable>>();
    private static HashMap<Integer, Craftable> harvestableByMaterial = new HashMap<Integer, Craftable>();
    private static HashMap<Class, Craftable> craftByCustom = new HashMap<Class, Craftable>();

     public static void reloadCache() {
        craftBySkill = new HashMap<Jobs.Classe, HashSet<Craftable>>();
        harvestableByMaterial = new HashMap<Integer, Craftable>();
        craftByCustom = new HashMap<Class, Craftable>();
        craftaveis = new ArrayList<Craftable>();
        CraftConfig.Load();
    }
    
    public static void add(Craftable h) {
        craftaveis.add(h);
        if (craftBySkill.containsKey(h.classe)) {
            HashSet<Craftable> harvestables = craftBySkill.get(h.classe);
            harvestables.add(h);
        } else {
            HashSet<Craftable> harvestables = new HashSet<Craftable>();
            harvestables.add(h);
            craftBySkill.put(h.classe, harvestables);
        }
        harvestableByMaterial.put(h.m.getId(), h);
        if (h.customItem != null) {
            craftByCustom.put(h.customItem.getClass(), h);
        }

    }

    public static HashSet<Craftable> getCraftables(Jobs.Classe m) {
        return craftBySkill.get(m);
    }

    public static Craftable getCraftable(ItemStack ss) {
        CustomItem custom = CustomItem.getItem(ss);
        KoM.debug("pegando craftable "+ss.getType().name());
        if (custom != null) {
             KoM.debug("eh um custom item");
            if (craftByCustom.containsKey(custom.getClass())) {
                return craftByCustom.get(custom.getClass());
            } else {
                 KoM.debug("nao achei a classe dele");
            }
        }
        for(Craftable c : craftaveis) {
            if(c.m==ss.getType() && (c.data==ss.getData().getData() || c.data == ss.getDurability()))
                return c;
        }
        return null;
    }

    
    public static Craftable getCraftable(Material m) {
        if (m == null) {
            return null;
        }
        if (harvestableByMaterial.containsKey(m.getId())) {
            return harvestableByMaterial.get(m.getId());
        }
        return null;
    }
    

}
