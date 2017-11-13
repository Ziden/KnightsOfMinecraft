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
import nativelevel.sisteminhas.ClanLand;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import org.bukkit.ChatColor;
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

public class PowerAlly extends CustomPotion {

    private int CURA = 8;

    public PowerAlly() {
        super(L.m("Poção de Força Pegajosa"), L.m("Da Força apenas a Aliados"), PotionType.INSTANT_DAMAGE, true);
    }
    
    public Color cor() {
        return Color.RED;
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
        ClanPlayer eu = ClanLand.manager.getClanPlayer(p.getUniqueId());
        KoM.act(ev.getPotion(), p.getName() + " jogou uma " + this.name);
        for (Entity e : ev.getAffectedEntities()) {
            KoM.debug("Tentando pot força em "+e.getName());
              if(ev.getIntensity((LivingEntity)e)==0)
                continue;
              KoM.debug("Tem intensity");
            if (e instanceof LivingEntity) {
                LivingEntity le = (LivingEntity) e;
                if ((e.getType() == EntityType.PLAYER || e instanceof Monster) && !e.hasMetadata("NPC")) {
                    KoM.debug("valido");
                    ClanPlayer cp = ClanLand.manager.getClanPlayer(e.getUniqueId());
                    //// NAO AFETA INIMIGOS
                   
                    if (eu != null) {
                        if (cp != null) {
                            if (!eu.getTag().equalsIgnoreCase(cp.getTag()) && !eu.getClan().isAlly(cp.getTag())) {
                                continue;
                            }
                        }
                    }
                    le.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 6, 0));
                    KoM.efeitoBlocos(le, Material.GLOWSTONE);
                }
            }
        }
    }

    @Override
    public ItemStack[] getRecipe() {
        return new ItemStack[]{
            new ItemStack(Material.CAKE, 1),
            new ItemStack(Material.SPECKLED_MELON, 1),
            new ItemStack(Material.GHAST_TEAR, 1)};
    }

    @Override
    public int getMinimumSkill() {
        return 115;
    }

    @Override
    public double getExpRatio() {
        return 2;
    }

    @Override
    public ItemStack brewWith() {
        return new ItemStack(Material.FERMENTED_SPIDER_EYE, 1);
    }

    @Override
    public void drink(PlayerItemConsumeEvent ev) {

    }

}
