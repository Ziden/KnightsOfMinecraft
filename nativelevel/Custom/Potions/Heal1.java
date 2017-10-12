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
public class Heal1 extends CustomPotion {

    public final double CURA = 4;

    public Heal1() {
        super(L.m("Poção de Vida Fraca"), L.m("Cura um pouco de vida"), PotionType.INSTANT_HEAL, false);
    }

    @Override
    public void interage(PlayerInteractEvent ev) {

    }
    
     public Color cor() {
        return Color.fromBGR(243, 247, 0);
    }

    @Override
    public void splashEvent(PotionSplashEvent ev, Player p) {

    }

    @Override
    public ItemStack[] getRecipe() {
        return new ItemStack[]{
            new ItemStack(Material.APPLE, 1),
            new ItemStack(Material.WHEAT, 1),
            new ItemStack(Material.LEAVES, 1)};
    }

    @Override
    public int getMinimumSkill() {
        return 25;
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
        if (ev.getPlayer().getHealth() == ev.getPlayer().getMaxHealth()) {
            ev.getPlayer().sendMessage(ChatColor.RED + L.m("Você já está com a vida cheia !"));
            ev.setCancelled(true);
            return;
        }
        double vida = ev.getPlayer().getHealth() + CURA;
        if (vida > ev.getPlayer().getMaxHealth()) {
            ev.getPlayer().setHealth(ev.getPlayer().getMaxHealth());
        } else {
            ev.getPlayer().setHealth(vida);
        }
        ev.getPlayer().sendMessage(ChatColor.GREEN + L.m("Você sente seus ferimentos cicatrizarem"));
        KoM.efeitoBlocos(ev.getPlayer(), Material.WOOL);

    }

}
