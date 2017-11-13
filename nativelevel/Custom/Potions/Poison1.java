/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.Custom.Potions;

import genericos.komzin.libzinha.listeners.GeralListener;
import me.fromgate.playeffect.PlayEffect;
import me.fromgate.playeffect.VisualEffect;
import nativelevel.Lang.L;
import nativelevel.Custom.CustomPotion;
import nativelevel.KoM;
import nativelevel.Listeners.GeneralListener;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.util.Vector;

/**
 *
 * @author User
 *
 */
public class Poison1 extends CustomPotion {

    private PotionEffect efeito = new PotionEffect(PotionEffectType.POISON, 20*10,0);
    
    public Color cor() {
        return Color.GREEN;
    }
    
    private DamageCause damageType = DamageCause.ENTITY_EXPLOSION;

    public Poison1() {
        super(L.m("Poção de Veneno Fraco"), L.m("Causa Veneno a todos"), PotionType.POISON, true);
    }

    @Override
    public void interage(PlayerInteractEvent ev) {
        ThrownPotion thrownPotion = ev.getPlayer().launchProjectile(ThrownPotion.class);
        thrownPotion.setItem(new ItemStack(ev.getPlayer().getItemInHand()));
        thrownPotion.setShooter(ev.getPlayer());
        this.consome(ev.getPlayer());
    }

    @Override
    public void splashEvent(PotionSplashEvent ev, Player p) {
        for (Entity e : ev.getAffectedEntities()) {
            if (e instanceof LivingEntity) {
                  if(ev.getIntensity((LivingEntity)e)==0)
                continue;
                if ((e.getType() == EntityType.PLAYER || e instanceof Monster) && !e.hasMetadata("NPC")) {
                    GeneralListener.ultimoDano.put(e.getUniqueId(), p.getUniqueId());
                    ((LivingEntity)e).addPotionEffect(efeito);
                    KoM.efeitoBlocos(e, Material.EMERALD_BLOCK);
                }
            }
        }
    }

    @Override
    public ItemStack[] getRecipe() {
        return new ItemStack[]{
            new ItemStack(Material.LEAVES, 1),
            new ItemStack(Material.POTATO_ITEM, 1),
            new ItemStack(Material.ROTTEN_FLESH, 1)};
    }

    @Override
    public int getMinimumSkill() {
        return 35;
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

    }

}
