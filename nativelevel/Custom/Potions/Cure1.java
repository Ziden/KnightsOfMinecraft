/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.Custom.Potions;

import nativelevel.Lang.L;
import nativelevel.Custom.CustomPotion;
import nativelevel.KoM;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

/**
 *
 * @author User
 */
public class Cure1 extends CustomPotion {

    public Cure1() {
        super(L.m("Antibiotico Fraco"), L.m("Cura pequenos venenos"), PotionType.INSTANT_HEAL, false);
    }

    @Override
    public void interage(PlayerInteractEvent ev) {

    }

    @Override
    public void splashEvent(PotionSplashEvent ev, Player p) {

    }
    
    public Color cor() {
        return Color.fromRGB(244, 168, 6);
    }

    @Override
    public ItemStack[] getRecipe() {
        return new ItemStack[]{
            new ItemStack(Material.LEAVES, 1),
            new ItemStack(Material.WHEAT, 1),
            new ItemStack(Material.SAND, 1)};
    }

    @Override
    public int getMinimumSkill() {
        return 1;
    }

    @Override
    public double getExpRatio() {
        return 1d;
    }

    @Override
    public ItemStack brewWith() {
        return new ItemStack(Material.SPIDER_EYE, 1);
    }

    @Override
    public void drink(PlayerItemConsumeEvent ev) {
        if (ev.getPlayer().hasPotionEffect(PotionEffectType.POISON)) {
            for (PotionEffect effect : ev.getPlayer().getActivePotionEffects()) {
                if (effect.getType().getName() == PotionEffectType.POISON.getName()) {
                    if (effect.getAmplifier() <= 1) {
                        ev.getPlayer().removePotionEffect(PotionEffectType.POISON);
                        ev.getPlayer().sendMessage(ChatColor.GREEN + L.m("Você se curou do veneno !"));
                        KoM.efeitoBlocos(ev.getPlayer(), Material.PUMPKIN);
                    } else {
                        ev.setCancelled(true);
                        ev.getPlayer().sendMessage(ChatColor.RED + L.m("Este veneno é muito forte para ser curado com esta poção !"));
                        
                    }
                }
            }
        } else {
            ev.setCancelled(true);
            ev.getPlayer().sendMessage(ChatColor.RED + L.m("Você não está envenenado para beber isto !"));
            
        }
    }

}
