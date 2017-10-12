/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.Custom.Potions;

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

public class Mana3 extends CustomPotion {

    public Mana3() {
        super(L.m("Poção de Mana Forte"), L.m("Regenera bastante mana"), PotionType.WATER_BREATHING, false);
    }

    public Color cor() {
        return Color.BLUE;
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
            CustomItem.getItem(FolhaDeMana.class).generateItem(),
            new ItemStack(Material.CAKE, 1),
            new ItemStack(Material.GHAST_TEAR, 1)};
    }

    @Override
    public int getMinimumSkill() {
        return 90;
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
        Mana.changeMana(ev.getPlayer(), 200);
        KoM.efeitoBlocos(ev.getPlayer(), Material.LAPIS_BLOCK);
        ev.getPlayer().sendMessage(ChatColor.GREEN+"Voce recuperou um pouco de seu mana");
    }

}
