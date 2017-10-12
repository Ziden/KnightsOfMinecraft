/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.precocabeca;

import java.util.logging.Logger;
import nativelevel.KoM;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author lko
 */
public class Principal {
  

    public static Recompensas recom = new Recompensas();

    public Principal() {
        onEnable();
    }
  
    public void onEnable() {
       // Bukkit.getPluginCommand("recompensa").setExecutor(new CmdRecompensa());
        //Bukkit.getPluginManager().registerEvents(new Events(), NativeLevel.instanciaDoPlugin);
    }

}
