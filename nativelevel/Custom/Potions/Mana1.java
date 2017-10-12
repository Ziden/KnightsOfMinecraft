/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.Custom.Potions;

import me.fromgate.playeffect.PlayEffect;
import me.fromgate.playeffect.VisualEffect;
import nativelevel.Custom.CustomItem;
import nativelevel.Lang.L;
import nativelevel.Custom.CustomPotion;
import nativelevel.Custom.Items.FolhaDeMana;
import nativelevel.KoM;
import nativelevel.Attributes.Mana;
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

public class Mana1 extends CustomPotion {

    public Mana1() {
        super(L.m("Poção de Mana Fraca"), L.m("Recupera um pouco de mana"), PotionType.WATER_BREATHING, false);
    }

    @Override
    public void interage(PlayerInteractEvent ev) {

    }
    
    public Color cor() {
        return Color.BLUE;
    }

    @Override
    public void splashEvent(PotionSplashEvent ev, Player p) {

    }

    @Override
    public ItemStack[] getRecipe() {
        return new ItemStack[]{
            CustomItem.getItem(FolhaDeMana.class).generateItem(),
            new ItemStack(Material.GLASS, 1),
            new ItemStack(Material.WHEAT, 1)};
    }

    @Override
    public int getMinimumSkill() {
        return 25;
    }

    @Override
    public double getExpRatio() {
        return 1.5;
    }

    @Override
    public ItemStack brewWith() {
        return new ItemStack(Material.SPIDER_EYE, 1);
    }

    @Override
    public void drink(PlayerItemConsumeEvent ev) {
        Mana.changeMana(ev.getPlayer(), 20);
        KoM.efeitoBlocos(ev.getPlayer(), Material.LAPIS_BLOCK);
        //PlayEffect.play(VisualEffect.SPELL_MOB, ev.getPlayer().getLocation(), "num:2");
        ev.getPlayer().sendMessage(ChatColor.GREEN+"Voce recuperou um pouco de seu mana");
    }

}
