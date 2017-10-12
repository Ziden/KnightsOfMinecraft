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
public class Power extends CustomPotion {


    public Color cor() {
        return Color.RED;
    }
    

    public Power() {
        super(L.m("Poção de Força"), L.m("Deixa os ataques mais fortes por pouco tempo"), PotionType.INSTANT_HEAL, false);
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
            new ItemStack(Material.GOLD_NUGGET, 1),
            new ItemStack(Material.GHAST_TEAR, 1),
            new ItemStack(Material.IRON_BLOCK, 1)};
    }

    @Override
    public int getMinimumSkill() {
        return 100;
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
        ev.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE,20*6, 0));
        ev.getPlayer().sendMessage(ChatColor.GREEN + L.m("Você sente seus musculos fortalecendo rapidamente !"));
        KoM.efeitoBlocos(ev.getPlayer(), Material.GLOWSTONE);
    }

}
