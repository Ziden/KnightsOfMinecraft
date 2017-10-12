/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.Planting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import nativelevel.Jobs;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author vntgasl
 */
public class PlantCache {

    private static HashMap<Jobs.Classe, HashSet<Plantable>> harvestablesBySkill = new HashMap<Jobs.Classe, HashSet<Plantable>>();
    private static HashMap<Integer, HashSet<Plantable>> harvestableByMaterial = new HashMap<Integer, HashSet<Plantable>>();

    public static void add(Plantable h) {
        if (harvestablesBySkill.containsKey(h.classe)) {
            HashSet<Plantable> harvestables = harvestablesBySkill.get(h.classe);
            harvestables.add(h);
        } else {
            HashSet<Plantable> harvestables = new HashSet<Plantable>();
            harvestables.add(h);
            harvestablesBySkill.put(h.classe, harvestables);
        }
        if (harvestableByMaterial.containsKey(h.m.getId())) {
            HashSet<Plantable> harvestables = harvestableByMaterial.get(h.m.getId());
            harvestables.add(h);
        } else {
            HashSet<Plantable> harvestables = new HashSet<Plantable>();
            harvestables.add(h);
            harvestableByMaterial.put(h.m.getId(), harvestables);
        }
    }

    public static HashSet<Plantable> getPlantable(String m) {
        try {
            return harvestablesBySkill.get(Jobs.Classe.valueOf(m));
        } catch (Exception e) {
            return null;
        }
    }

    public static HashSet<Plantable> getPlantable(Material m) {
        HashSet<Plantable> harvestables = new HashSet<Plantable>();
        HashSet<Plantable> byBlock = harvestableByMaterial.get(m.getId());
        if (byBlock != null) {
            for (Plantable harv : byBlock) {
                harvestables.add(harv);
            }
        }
        return harvestables;
    }

    public static Plantable getPlantable(Block b) {
        Material blockMat = b.getType();
        byte data = b.getData();
        HashSet<Plantable> byBlock = harvestableByMaterial.get(blockMat.getId());
        if (byBlock != null) {
            for (Plantable harv : byBlock) {
                //  if (harv.data == data) {
                return harv;
                // }
            }
        }

        for (ItemStack ss : b.getDrops()) {
            byBlock = harvestableByMaterial.get(ss.getType());
            if (byBlock != null) {
                for (Plantable p : byBlock) {
                    return p;
                }
            }
        }

        return null;
    }
}
