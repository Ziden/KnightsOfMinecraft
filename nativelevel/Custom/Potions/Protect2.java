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
 * 
 */

public class Protect2 extends CustomPotion {

    public final PotionEffect efeito = new PotionEffect(PotionEffectType.ABSORPTION, 20 * 60 * 5, 1);

    public Protect2() {
        super(L.m("Poção de Proteção Media"), L.m("Te protege"), PotionType.INSTANT_HEAL, false);
    }
    
        public Color cor() {
        return Color.AQUA;
    }


    @Override
    public void interage(PlayerInteractEvent ev) {

    }

    @Override
    public void splashEvent(PotionSplashEvent ev, Player p) {

    }

    @Override
    public ItemStack[] getRecipe() {
        return new ItemStack[]{
            new ItemStack(Material.PUMPKIN, 1),
            new ItemStack(Material.EMERALD, 1),
            new ItemStack(Material.MILK_BUCKET, 1)};
    }

    @Override
    public int getMinimumSkill() {
        return 60;
    }

    @Override
    public double getExpRatio() {
        return 1.2;
    }

    @Override
    public ItemStack brewWith() {
        return new ItemStack(Material.REDSTONE, 1);
    }

    @Override
    public void drink(PlayerItemConsumeEvent ev) {
        ev.getPlayer().addPotionEffect(efeito);
        ev.getPlayer().sendMessage(ChatColor.GREEN + L.m("* gulp gulp *"));
        KoM.efeitoBlocos(ev.getPlayer(), Material.WOOL);

    }

}
