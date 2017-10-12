/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.Planting;

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

public class PlantConfig {

    public static ConfigManager ConfigFile;
    public static ConfigManager MiningCfg;


    public void onEnable() {
        Load();
        //TreeType.fix();
    }
    
    public static void Load() {
        try {
            ConfigFile = new ConfigManager(KoM._instance.getDataFolder() + "/Planting.yml"); 
            HashSet<Plantable> harvestables = getAll();
            for(Plantable h : harvestables) {
                PlantCache.add(h);
                KoM._instance.debug("ADDING Plantable "+h.m.name()+" "+h.classe);
            }   
        } catch (Exception ex) {
             ex.printStackTrace();
        }
    }
    
    static String [] classes = {"Fazendeiro", "Ferreiro", "Minerador", "Alquimista", "Engenheiro", "Lenhador", "Mago", "Paladino","Ladino"};
   
    private static HashSet<Plantable> getAll() {
        HashSet<Plantable> harvestables = new HashSet<Plantable>();
        for(String skill : classes) {
            ConfigurationSection section = ConfigFile.getConfig().getConfigurationSection(skill);
            if(section==null) continue;
            for(String key : section.getKeys(false)) {
                String materialName = key.split("-")[0];
                byte data = Byte.parseByte(key.split("-")[1]);
                int minSkill = ConfigFile.getConfig().getInt(skill+"."+key+".MinSkill");
                int cooldown = ConfigFile.getConfig().getInt(skill+"."+key+".Cooldown");
                double expRatio = ConfigFile.getConfig().getDouble(skill+"."+key+".ExpRatio");
                Plantable harvest = new Plantable(Material.valueOf(materialName),data, skill, minSkill, expRatio);
                harvestables.add(harvest);
            }
        }
        
        return harvestables;
    }
    
    public static void add(Plantable h) {
        String node = h.classe+"."+h.m.name()+"-"+h.data;
        ConfigFile.getConfig().set(node+".MinSkill", h.difficulty);
        ConfigFile.getConfig().set(node+".ExpRatio", h.expRatio);
        ConfigFile.SaveConfig();
        PlantCache.add(h);
    }

    public static void init(String node, Object value) {
        if (!ConfigFile.getConfig().contains(node)) {
            ConfigFile.getConfig().set(node, value);
        }
        ConfigFile.SaveConfig();
    }
    
}
