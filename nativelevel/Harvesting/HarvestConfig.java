/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.Harvesting;

import nativelevel.Harvesting.Harvestable;
import nativelevel.Harvesting.HarvestCache;
import java.util.HashSet;
import nativelevel.KoM;
import nativelevel.config.ConfigManager;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

/**
 *
 * @author vntgasl
 * 
 */

public class HarvestConfig {

    public static ConfigManager ConfigFile;
    public static ConfigManager MiningCfg;


    public void onEnable() {
        Load();
        //TreeType.fix();
    }
    
    public static void Load() {
        try {
            ConfigFile = new ConfigManager(KoM._instance.getDataFolder() + "/Harvesting.yml"); 
            HashSet<Harvestable> harvestables = getAll();
            for(Harvestable h : harvestables) {
                HarvestCache.add(h);
                KoM._instance.debug("ADDING Harvestable "+h.m.name()+" "+h.classe);
            }   
        } catch (Exception ex) {
             ex.printStackTrace();
        }
    }
    
    /*
    Mining:
        IRON_ORE-0:
            MinSkill: 30
            Cooldown: 10
            ExpRatio: 1
    Escavation:
        GRAVEL-0:
            MinSkill: 10
            Cooldown: 10
            ExpRatio: 1
    */
    
    static String [] classes = {"Fazendeiro", "Ferreiro", "Minerador", "Alquimista", "Engenheiro", "Lenhador", "Mago", "Paladino","Ladino"};
   
    private static HashSet<Harvestable> getAll() {
        HashSet<Harvestable> harvestables = new HashSet<Harvestable>();
        for(String skill : classes) {
            ConfigurationSection section = ConfigFile.getConfig().getConfigurationSection(skill);
            if(section==null) continue;
            for(String key : section.getKeys(false)) {
                String materialName = key.split("-")[0];
                byte data = Byte.parseByte(key.split("-")[1]);
                int minSkill = ConfigFile.getConfig().getInt(skill+"."+key+".MinSkill");
                int cooldown = ConfigFile.getConfig().getInt(skill+"."+key+".Cooldown");
                double expRatio = ConfigFile.getConfig().getDouble(skill+"."+key+".ExpRatio");
                Harvestable harvest = new Harvestable(Material.valueOf(materialName),data, skill, minSkill, expRatio);
                harvestables.add(harvest);
            }
        }
        
        return harvestables;
    }
    
    public static void remove(Harvestable h) {
       String node = h.classe+"."+h.m.name()+"-"+h.data;
       ConfigFile.getConfig().set(node, null);
       ConfigFile.SaveConfig();
    }
    
    public static void add(Harvestable h) {
        String node = h.classe+"."+h.m.name()+"-"+h.data;
        ConfigFile.getConfig().set(node+".MinSkill", h.difficulty);
        ConfigFile.getConfig().set(node+".ExpRatio", h.expRatio);
        ConfigFile.SaveConfig();
        HarvestCache.add(h);
    }

    public static void init(String node, Object value) {
        if (!ConfigFile.getConfig().contains(node)) {
            ConfigFile.getConfig().set(node, value);
        }
        ConfigFile.SaveConfig();
    }
    
}
