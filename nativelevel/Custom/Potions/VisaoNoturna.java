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

public class VisaoNoturna extends CustomPotion {

    public final PotionEffect efeito = new PotionEffect(PotionEffectType.NIGHT_VISION, 20 * 60 * 5, 0);

    public VisaoNoturna() {
        super(L.m("Poção de Visão Noturna"), L.m("Permite ver no escuro"), PotionType.INSTANT_HEAL, false);
    }

    @Override
    public void interage(PlayerInteractEvent ev) {

    }
    
    public Color cor() {
        return Color.SILVER;
    }

    @Override
    public void splashEvent(PotionSplashEvent ev, Player p) {

    }

    @Override
    public ItemStack[] getRecipe() {
        return new ItemStack[]{
            new ItemStack(Material.HAY_BLOCK, 1),
            new ItemStack(Material.CAKE, 1),
            new ItemStack(Material.MUSHROOM_SOUP, 1)};
    }

    @Override
    public int getMinimumSkill() {
        return 70;
    }

    @Override
    public double getExpRatio() {
        return 2;
    }

    @Override
    public ItemStack brewWith() {
        return new ItemStack(Material.NETHER_STALK, 1);
    }

    @Override
    public void drink(PlayerItemConsumeEvent ev) {
        ev.getPlayer().addPotionEffect(efeito);
        ev.getPlayer().sendMessage(ChatColor.GREEN + L.m("Voce está vendo no escuro"));
        KoM.efeitoBlocos(ev.getPlayer(), Material.WOOL);

    }

}
