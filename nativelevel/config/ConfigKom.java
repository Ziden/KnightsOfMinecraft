/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.config;

import nativelevel.lojaagricola.*;
import nativelevel.config.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import nativelevel.Classes.*;
import nativelevel.KoM;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

/**
 *
 * @author vntgasl
 */
public class ConfigKom {

    /*
    public static ConfigManager cfg;

    public static boolean temDados = false;
    
    public static ItemJob getPocao(Potion p) {
        PotionType tipo = p.getType();
        if(cfg.getConfig().contains("pocao."+p.getType())) {
            return getItem("pocao."+p.getType());
        }
        return null;
    }
    
    public ConfigKom() {
        try {
            cfg = new ConfigManager(KnightsOfMania._instance.getDataFolder() + "/materiais.yml");
            if(!cfg.getConfig().contains("kom")) {
                cfg.getConfig().set("kom", "ehfodao");
                
                // pocoes
                for(PotionType t : PotionType.values()) {
                    Potion pot = new Potion(t);
                    int xp = Alchemist.getExp(pot);
                    if(xp != 0) {
                        int diff = Alchemist.getDifficulty(pot);
                        setItem("pocao."+t.name(), diff, xp, "Alquimista");
                    }
                }
                
                setItem("pocao.comsplash", 10, 15, "Alquimista");
                setItem("pocao.comduracao", 10, 15, "Alquimista");
                setItem("pocao.comnivel", 10, 15, "Alquimista");
                
                for(Material m : Material.values()) {
                    String nome = m.name();
                    int exp = Blacksmith.pegaExpDe(m);
                    String job = null;
                    if(exp != 0) {
                        job = "Ferreiro";
                        int diff = Blacksmith.getDifficulty(m);
                        ConfigKom.setItem(m, diff, exp, job);
                        continue;
                    } 
                    exp = Farmer.pegaExpDe(m, null);
                    if(exp != 0) {
                        job = "Fazendeiro";
                        int diff = Farmer.pegaDificuldadeDe(m);
                        ConfigKom.setItem(m, diff, exp, job);
                        continue;
                    }
                    exp = Minerador.pegaExpDe(m);
                    if(exp != 0) {
                        job = "Minerador";
                        int diff = Farmer.pegaDificuldadeDe(m);
                        ConfigKom.setItem(m, diff, exp, job);
                        continue;
                    }
                    exp = Lumberjack.pegaExpDe(m);
                    if(exp != 0) {
                        job = "Lenhador";
                        int diff = Lumberjack.pegaDificuldadeDe(m);
                        ConfigKom.setItem(m, diff, exp, job);
                        continue;
                    }
                    exp = Engineer.getExpFrom(m);
                    if(exp != 0) {
                        job = "Engenheiro";
                        int diff = Engineer.getDifficultyFrom(m);
                        ConfigKom.setItem(m, diff, exp, job);
                        continue;
                    }
                }
            }
            cfg.SaveConfig();
            temDados = true;
            KnightsOfMania.log.info("CONFIG DE ITEMS LOADED");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

     public static void setItem(String mat, int diff, int xp, String job) {
        cfg.getConfig().set(mat+".diff", diff);
        cfg.getConfig().set(mat+".job", job.toLowerCase());
        cfg.getConfig().set(mat+".xp", xp);
        cfg.SaveConfig();
        ItemJob itemJob = new ItemJob();
        itemJob.dificuldade = diff;
        itemJob.xp = xp;
        itemJob.job = job;
        try {
            Material m = Material.valueOf(mat);
            itemJob.item = m;
        }catch(Exception e) {   
            e.printStackTrace();
            itemJob.item = Material.AIR;
        }
        items.put(mat, itemJob);
    }
    
    public static void setItem(Material mat, int diff, int xp, String job) {
       setItem(mat.name(), diff, xp, job);
    }
    
    private static HashMap<String, ItemJob> items = new HashMap<String, ItemJob>();
    
    public static ItemJob getItem(Material mat) {
        return getItem(mat.name());
    }
    
    public static ItemJob getItem(String mat) {
        if(items.containsKey(mat))
            return items.get(mat);
        if(!cfg.getConfig().contains(mat))
            return null;
        int diff = cfg.getConfig().getInt(mat+".diff");
        int xp = cfg.getConfig().getInt(mat+".xp");
        String job = cfg.getConfig().getString(mat+".job");
        ItemJob itemJob = new ItemJob();
        itemJob.dificuldade = diff;
        itemJob.xp = xp;
        itemJob.job = job;
        try {
            itemJob.item = Material.valueOf(mat);
        } catch(Exception e) {
            itemJob.item = Material.AIR;
        }
        return itemJob;
    }
    */
}
