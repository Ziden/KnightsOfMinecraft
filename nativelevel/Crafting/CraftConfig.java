/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.Crafting;

import java.util.HashSet;
import nativelevel.Custom.CustomItem;
import static nativelevel.Harvesting.HarvestConfig.ConfigFile;
import nativelevel.Harvesting.Harvestable;
import nativelevel.Jobs;
import nativelevel.KoM;
import nativelevel.config.ConfigManager;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

/**
 *
 * @author User
 *
 */
public class CraftConfig {

    public static ConfigManager ConfigFile;

    public void onEnable() {
        Load();
    }

    public static void Load() {
        try {
            KoM.log.info("Loadando craftings");
            ConfigFile = new ConfigManager(KoM._instance.getDataFolder() + "/Crafting.yml");
            HashSet<Craftable> harvestables = getAll();
            for (Craftable h : harvestables) {
                CraftCache.add(h);
                KoM.log.info("+Craftable " + h.m.name() + " " + h.classe.name());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            ex.printStackTrace();
        }
    }

    private static HashSet<Craftable> getAll() {
        HashSet<Craftable> craftables = new HashSet<Craftable>();
        for (Jobs.Classe skill : Jobs.Classe.values()) {
            ConfigurationSection section = ConfigFile.getConfig().getConfigurationSection(skill.name());
            if (section == null) {
                continue;
            }
            for (String key : section.getKeys(false)) {
                try {
                    String materialName = key.split("-")[0];
                    byte data = Byte.parseByte(key.split("-")[1]);
                    int minSkill = ConfigFile.getConfig().getInt(skill.name() + "." + key + ".Diff");
                    double expRatio = ConfigFile.getConfig().getDouble(skill.name() + "." + key + ".ExpRatio");
                    if (ConfigFile.getConfig().contains(skill.name() + "." + key + ".CustomItem")) {
                        String itemName = ConfigFile.getConfig().getString(skill.name() + "." + key + ".CustomItem");
                        CustomItem customItem = CustomItem.getByName(itemName);
                        if (customItem != null) {
                            Craftable craft = new Craftable(customItem, minSkill, skill, expRatio);
                            craftables.add(craft);
                        }
                    } else {
                        Craftable craft = new Craftable(Material.valueOf(materialName), data, minSkill, skill, expRatio);
                        craftables.add(craft);
                    }

                } catch (Exception e) {
                    KoM.log.info("ERRO AO CARREGAR CRAFTAVEL " + key);
                    e.printStackTrace();
                }
            }
        }
        return craftables;
    }

    public static void remove(Craftable h) {
        String node = h.classe + "." + h.m.name() + "-" + h.data;
        ConfigFile.getConfig().set(node, null);
        ConfigFile.SaveConfig();
    }

    public static void add(Craftable h) {
        String node = h.classe.name() + "." + h.m.name() + "-" + h.data;
        ConfigFile.getConfig().set(node + ".Diff", h.diff);
        ConfigFile.getConfig().set(node + ".ExpRatio", h.expRatio);
        if (h.customItem != null) {
            ConfigFile.getConfig().set(node + ".CustomItem", h.customItem.getName());
        }
        ConfigFile.SaveConfig();
        CraftCache.add(h);
    }

    public static void init(String node, Object value) {
        if (!ConfigFile.getConfig().contains(node)) {
            ConfigFile.getConfig().set(node, value);
        }
        ConfigFile.SaveConfig();
    }

}
