/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import nativelevel.KoM;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;

/**
 *
 * @author vntgasl
 */
public class Config {

    public static ConfigManager cfg;
    public static ConfigManager Lang;

    public Config() {
        try {
            cfg = new ConfigManager(KoM._instance.getDataFolder() + "/configDoZiden.yml");
            if (!cfg.getConfig().contains("Doacoes")) {
                cfg.getConfig().set("Doacoes", 0);
            }
            cfg.SaveConfig();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void setDoacao(int n) {
        cfg.getConfig().set("Doacoes", n);
        cfg.SaveConfig();
    }
    
    public static int getDoacoes() {
        return cfg.getConfig().getInt("Doacoes");
    }
}
