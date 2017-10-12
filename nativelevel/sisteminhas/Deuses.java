/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.sisteminhas;

import nativelevel.CFG;
import nativelevel.Custom.CustomItem;
import nativelevel.Jobs;
import nativelevel.KoM;
import nativelevel.Lang.L;
import nativelevel.Listeners.GeneralListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Deuses {

    public static int matou = 0;
    public static boolean day;
    public static boolean odio = false;
    public static boolean paz = false;

    public static void msg(String msg) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getWorld().getName().equalsIgnoreCase(CFG.mundoGuilda) || p.getWorld().getName().equalsIgnoreCase("vila")) {
                p.sendMessage("* " + msg + " *");
            }
        }
    }

    public static void testa() {
        if(KoM.reiniciando)
            return;
        boolean noite = day();
        if (noite != day) {
            day = noite;
            int rnd = Jobs.rnd.nextInt(5);
            if (noite) {
                switch (rnd) {
                    case 1:
                        msg(ChatColor.RED + L.m("Ubaj espalha a fome pelo povo de Aden"));
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            if (p.getWorld().getName().equalsIgnoreCase(CFG.mundoGuilda) || p.getWorld().getName().equalsIgnoreCase("vila")) {
                                int food = p.getFoodLevel() - 6;
                                if (food < 0) {
                                    food = 0;
                                }
                                p.setFoodLevel(0);
                            }
                        }
                    case 2:
                        msg(ChatColor.RED + L.m("Ubaj espalha a peste pelo povo de Aden"));
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            if (p.getWorld().getName().equalsIgnoreCase(CFG.mundoGuilda) || p.getWorld().getName().equalsIgnoreCase("vila")) {
                                p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20 * 10, 0));
                            }
                        }
                    case 3:
                        msg(ChatColor.RED + L.m("Ubaj começa a espalhar o ódio pelo povo de Aden"));
                        odio = true;
                        Runnable r = new Runnable() {
                            public void run() {
                                odio = false;
                                msg(ChatColor.RED + L.m("O ódio de Ubaj matou % pessoas !", matou));
                                matou = 0;
                            }
                        };
                        Bukkit.getScheduler().scheduleSyncDelayedTask(KoM._instance, r, 20 * 60 * 2);

                    case 4:
                        msg(ChatColor.RED + L.m("Ubaj espalha a escuridão pelo povo de Aden"));
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            if (p.getWorld().getName().equalsIgnoreCase(CFG.mundoGuilda) || p.getWorld().getName().equalsIgnoreCase("vila")) {
                                p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 30, 0));
                            }
                        }
                    case 5:
                        msg(ChatColor.RED + L.m("Ubaj espalha a escuridão pelo povo de Aden"));
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            if (p.getWorld().getName().equalsIgnoreCase(CFG.mundoGuilda) || p.getWorld().getName().equalsIgnoreCase("vila")) {
                                p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 30, 0));
                            }
                        }
                }
            } else {
                switch (rnd) {
                    case 1:
                        msg(ChatColor.RED + L.m("Jabu espalha a comida pelo povo de Aden"));
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            if (p.getWorld().getName().equalsIgnoreCase(CFG.mundoGuilda) || p.getWorld().getName().equalsIgnoreCase("vila")) {
                                p.setFoodLevel(20);
                            }
                        }
                    case 2:
                        msg(ChatColor.RED + L.m("Jabu espalha a sabedoria pelo povo de Aden"));
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            if (p.getWorld().getName().equalsIgnoreCase(CFG.mundoGuilda) || p.getWorld().getName().equalsIgnoreCase("vila")) {
                                GeneralListener.givePlayerExperience(100, p);
                            }
                        }
                    case 3:
                        msg(ChatColor.RED + L.m("Jabu começa a espalhar o a paz pelo povo de Aden"));
                        paz = true;
                        Runnable r = new Runnable() {
                            public void run() {
                                paz = false;
                                msg(ChatColor.RED + L.m("A paz de jabu evitou % monstros !", matou));
                                matou = 0;
                            }
                        };
                        Bukkit.getScheduler().scheduleSyncDelayedTask(KoM._instance, r, 20 * 60 * 2);

                    case 4:
                        msg(ChatColor.RED + L.m("Jabu espalha a vida pelo povo de Aden"));
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            if (p.getWorld().getName().equalsIgnoreCase(CFG.mundoGuilda) || p.getWorld().getName().equalsIgnoreCase("vila")) {
                                p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 20, 0));
                            }
                        }
                    case 5:
                        msg(ChatColor.RED + L.m("Jabu espalha a prosperidade pelo povo de Aden"));
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            if (p.getWorld().getName().equalsIgnoreCase(CFG.mundoGuilda) || p.getWorld().getName().equalsIgnoreCase("vila")) {
                                p.getInventory().addItem(new ItemStack(Material.EMERALD, 30));
                            }
                        }
                }
            }
        }
    }

    public static boolean day() {

        long time = Bukkit.getServer().getWorld(CFG.mundoGuilda).getTime();

        return time < 12300 || time > 23850;
    }

}
