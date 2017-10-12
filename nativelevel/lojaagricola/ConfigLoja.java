/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.lojaagricola;

import nativelevel.config.*;
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
public class ConfigLoja {

    public static ConfigManager cfg;

    public ConfigLoja() {
        try {
            cfg = new ConfigManager(KoM._instance.getDataFolder() + "/lojaAgricola.yml");
            for(Vendavel v : LojaAgricola.vendaveis) {
                if(!cfg.getConfig().contains(v.getNomeTecnico())) {
                    cfg.getConfig().set(v.getNomeTecnico(), v.precoPack);
                    setQtdAlta(v.getNomeTecnico(), 666);
                    setQtdBaixo(v.getNomeTecnico(), 666);
                    setQtdNormal(v.getNomeTecnico(), 666);
                    setPreco(v.getNomeTecnico(), 666);
                }
            }
            cfg.SaveConfig();
            KoM.log.info("LOJA AGRICOLA CONFIGURADA");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static int getQtdAlta(String nome) {
         return cfg.getConfig().getInt(nome+"-alto");
    }
    
       public static int getQtdNormal(String nome) {
         return cfg.getConfig().getInt(nome+"-normal");
    }
    
    
    public static int qtdQtdBaixa(String nome) {
         return cfg.getConfig().getInt(nome+"-baixo");
    }
    
    public static void setQtdBaixo(String nome, int preco) {
        cfg.getConfig().set(nome+"-baixo", preco);
        cfg.SaveConfig();
    }
    
    public static void setQtdAlta(String nome, int preco) {
        cfg.getConfig().set(nome+"-alto", preco);
        cfg.SaveConfig();
    }
    
     public static void setQtdNormal(String nome, int preco) {
        cfg.getConfig().set(nome+"-normal", preco);
        cfg.SaveConfig();
    }
    
    public static int getPreco(String nome) {
        return cfg.getConfig().getInt(nome);
    }
    
    public static void setPreco(String nome, int preco) {
        cfg.getConfig().set(nome, preco);
        cfg.SaveConfig();
    }
}
