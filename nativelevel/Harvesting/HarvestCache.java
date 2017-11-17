/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.Harvesting;

import nativelevel.Harvesting.Harvestable;
import java.util.ArrayList;
import java.util.Arrays;
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
public class HarvestCache {

    private static HashMap<Jobs.Classe, HashSet<Harvestable>> harvestablesBySkill = new HashMap<Jobs.Classe, HashSet<Harvestable>>();
    private static HashMap<Integer, HashSet<Harvestable>> harvestableByMaterial = new HashMap<Integer, HashSet<Harvestable>>();

    public static void reloadCache() {
        harvestablesBySkill = new HashMap<Jobs.Classe, HashSet<Harvestable>>();
        harvestableByMaterial = new HashMap<Integer, HashSet<Harvestable>>();
        HarvestConfig.Load();
    }

    public static void add(Harvestable h) {
        if (harvestablesBySkill.containsKey(h.classe)) {
            HashSet<Harvestable> harvestables = harvestablesBySkill.get(h.classe);
            harvestables.add(h);
        } else {
            HashSet<Harvestable> harvestables = new HashSet<Harvestable>();
            harvestables.add(h);
            harvestablesBySkill.put(h.classe, harvestables);
        }
        if (harvestableByMaterial.containsKey(h.m.getId())) {
            HashSet<Harvestable> harvestables = harvestableByMaterial.get(h.m.getId());
            harvestables.add(h);
        } else {
            HashSet<Harvestable> harvestables = new HashSet<Harvestable>();
            harvestables.add(h);
            harvestableByMaterial.put(h.m.getId(), harvestables);
        }
    }

    public static HashSet<Harvestable> getHarvestable(String m) {
        try {
            return harvestablesBySkill.get(Jobs.Classe.valueOf(m));
        } catch (Exception e) {
            return null;
        }
    }

    public static HashSet<Harvestable> getHarvestable(Material m) {
        HashSet<Harvestable> harvestables = new HashSet<Harvestable>();
        HashSet<Harvestable> byBlock = harvestableByMaterial.get(m.getId());
        if (byBlock != null) {
            for (Harvestable harv : byBlock) {
                harvestables.add(harv);
            }
        }
        return harvestables;
    }

    public static Harvestable getHarvestable(Material m, byte data) {
        if (m == null) {
            return null;
        }
        HashSet<Harvestable> harvs = harvestableByMaterial.get(m.getId());
        if (harvs == null) {
            return null;
        }
        for (Harvestable harv : harvs) {
            if (harv.data == data && harv.m == m) {
                return harv;
            }
        }
        return null;
    }

    public static HashMap<Material, Material> translations = new HashMap<Material, Material>();

    static {
        translations.put(Material.CROPS, Material.WHEAT);
        translations.put(Material.BEETROOT_BLOCK, Material.BEETROOT_SEEDS);    
    }
    
    public static List<Material> ignora = Arrays.asList(new Material[]{});

    public static Harvestable getHarvestable(Block b) {
        Material blockMat = b.getType();
        byte data = b.getData();

        if (blockMat == Material.GLOWING_REDSTONE_ORE) {
            blockMat = Material.REDSTONE_ORE;
        }

        HashSet<Harvestable> byBlock = harvestableByMaterial.get(blockMat.getId());
        if (byBlock != null) {
            for (Harvestable harv : byBlock) {
                if (harv.data == data || (harv.m == Material.CROPS || harv.m == Material.PUMPKIN || harv.m == Material.BEETROOT_BLOCK || harv.m == Material.MELON || harv.m == Material.NETHER_WART_BLOCK || harv.m == Material.NETHER_WARTS || harv.m==Material.NETHER_STALK || harv.m == Material.CARROT || harv.m == Material.POTATO || harv.m == Material.COCOA || harv.m == Material.SUGAR_CANE_BLOCK || harv.m == Material.SUGAR_CANE)) {
                    return harv;
                }
            }
        }

        if (translations.containsKey(blockMat)) {
            Material traduzido = translations.get(blockMat);
            byBlock = harvestableByMaterial.get(traduzido);
            if (byBlock != null) {
                for (Harvestable harv : byBlock) {
                    if (harv.data == data || (harv.m == Material.CROPS) || harv.m == Material.PUMPKIN || harv.m == Material.MELON || harv.m == Material.NETHER_WART_BLOCK || harv.m == Material.NETHER_WARTS || harv.m == Material.CARROT || harv.m == Material.POTATO || harv.m == Material.COCOA) {
                        return harv;
                    }
                }
            }
        }

        if (b.getDrops().size() > 0) {
            for (ItemStack ss : b.getDrops()) {
                byBlock = harvestableByMaterial.get(ss.getType().getId());
                if (byBlock != null) {
                    for (Harvestable harv : byBlock) {
                        if (harv.data == ss.getData().getData()) {
                            return harv;
                        }
                    }
                }
                break;
            }
        }

        return null;
    }
}
