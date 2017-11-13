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

public class BonusLife extends CustomPotion {

    public final PotionEffect efeito = new PotionEffect(PotionEffectType.HEALTH_BOOST, 20 * 60 * 10, 3);

    public BonusLife() {
        super(L.m("Poção da Imortalidade"), L.m("Te deixa com mais vida"), PotionType.INSTANT_HEAL, false);
    }

    @Override
    public void splashEvent(PotionSplashEvent ev, Player p) {

    }
    
    public Color cor() {
        return Color.fromRGB(244, 228, 7);
    }

    @Override
    public ItemStack[] getRecipe() {
        return new ItemStack[]{
            new ItemStack(Material.GOLDEN_APPLE, 1),
            new ItemStack(Material.GOLDEN_CARROT, 1),
            new ItemStack(Material.GOLD_INGOT, 1)};
    }

    @Override
    public int getMinimumSkill() {
        return 120;
    }

    @Override
    public double getExpRatio() {
        return 1d;
    }

    @Override
    public ItemStack brewWith() {
        return new ItemStack(Material.FERMENTED_SPIDER_EYE, 1);
    }

    @Override
    public void drink(PlayerItemConsumeEvent ev) {
        ev.getPlayer().addPotionEffect(efeito);
        ev.getPlayer().sendMessage(ChatColor.GREEN + L.m("* gulp gulp *"));
        KoM.efeitoBlocos(ev.getPlayer(), Material.WOOL);
    }

    @Override
    public void interage(PlayerInteractEvent ev) {
        
    }

}
