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

public class FastDig extends CustomPotion {

    public final PotionEffect efeito = new PotionEffect(PotionEffectType.FAST_DIGGING, 20 * 60 * 10, 0);

    public FastDig() {
        super(L.m("Poção de Escavação"), L.m("Permite cavar rapidamente"), PotionType.INSTANT_HEAL, false);
    }

    @Override
    public void interage(PlayerInteractEvent ev) {

    }
    
    public Color cor() {
        return Color.fromBGR(27, 110, 145);
    }
    
    

    @Override
    public void splashEvent(PotionSplashEvent ev, Player p) {

    }

    @Override
    public ItemStack[] getRecipe() {
        return new ItemStack[]{
            new ItemStack(Material.CLAY_BALL, 1),
            new ItemStack(Material.COOKIE, 1),
            new ItemStack(Material.RAW_FISH, 1)};
    }

    @Override
    public int getMinimumSkill() {
        return 50;
    }

    @Override
    public double getExpRatio() {
        return 1d;
    }

    @Override
    public ItemStack brewWith() {
        return new ItemStack(Material.NETHER_STALK, 1);
    }

    @Override
    public void drink(PlayerItemConsumeEvent ev) {
        ev.getPlayer().addPotionEffect(efeito);
        ev.getPlayer().sendMessage(ChatColor.GREEN + L.m("* gulp gulp *"));
        KoM.efeitoBlocos(ev.getPlayer(), Material.WOOL);

    }

}
