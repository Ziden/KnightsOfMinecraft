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
public class Cure2 extends CustomPotion {

    public Cure2() {
        super(L.m("Antibiotico"), L.m("Cura venenos"), PotionType.INSTANT_HEAL, false);
    }
    
     public Color cor() {
        return Color.fromRGB(244, 168, 6);
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
            new ItemStack(Material.MELON, 1),
            new ItemStack(Material.BOWL, 1),
            new ItemStack(Material.PAPER, 1)};
    }

    @Override
    public int getMinimumSkill() {
        return 65;
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
        if (ev.getPlayer().hasPotionEffect(PotionEffectType.POISON)) {
            for (PotionEffect effect : ev.getPlayer().getActivePotionEffects()) {
                if (effect.getType().getName() == PotionEffectType.POISON.getName()) {
                    ev.getPlayer().removePotionEffect(PotionEffectType.POISON);
                    ev.getPlayer().sendMessage(ChatColor.GREEN + L.m("Você se curou do veneno !"));
                    KoM.efeitoBlocos(ev.getPlayer(), Material.PUMPKIN);
                }
            }
        } else {
            ev.setCancelled(true);
            ev.getPlayer().sendMessage(ChatColor.RED + L.m("Você não está envenenado para beber isto !"));

        }
    }

}
