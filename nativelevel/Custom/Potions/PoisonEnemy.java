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

public class PoisonEnemy extends CustomPotion {

    private PotionEffect efeito = new PotionEffect(PotionEffectType.POISON, 20 * 20, 0);

    public PoisonEnemy() {
        super(L.m("Poção de Veneno Pegajosa"), L.m("Nao envenena aliados"), PotionType.POISON, true);
    }
    
    public Color cor() {
        return Color.GREEN;
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
        for (Entity e : ev.getAffectedEntities()) {
              if(ev.getIntensity((LivingEntity)e)==0)
                continue;
            if (e instanceof LivingEntity) {
                LivingEntity le = (LivingEntity) e;
                if ((e.getType() == EntityType.PLAYER || e instanceof Monster) && !e.hasMetadata("NPC")) {
                    ClanPlayer cp = ClanLand.manager.getClanPlayer(e.getUniqueId());
                    // SÒ PEGA INIMIGOS
                    if (eu != null) {
                        if (cp != null) {
                            if (eu.getTag().equalsIgnoreCase(cp.getTag()) || eu.getClan().isAlly(cp.getTag())) {
                                continue;
                            }
                        }
                    }
                    ((LivingEntity) e).addPotionEffect(efeito);
                    KoM.efeitoBlocos(e, Material.EMERALD_BLOCK);
                }
            }
        }
    }

    @Override
    public ItemStack[] getRecipe() {
        return new ItemStack[]{
            new ItemStack(Material.GOLD_NUGGET, 1),
            new ItemStack(Material.POISONOUS_POTATO, 1),
            new ItemStack(Material.SLIME_BALL, 1)};
    }

    @Override
    public int getMinimumSkill() {
        return 75;
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

    }

}
