/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.sisteminhas;

import net.minecraft.server.v1_12_R1.EntityIronGolem;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftEntity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 *
 * @author User
 */
public class IronGolem {

    public static void morre(EntityDeathEvent ev) {
        if (ev.getEntity().getType() == EntityType.IRON_GOLEM) {
            if (ev.getEntity().getVehicle() != null && ev.getEntity().getVehicle().getType() == EntityType.WOLF) {
                ev.getEntity().getVehicle().remove();
            }
        }
    }
    

    public static void spawn(Player p) {
        org.bukkit.entity.IronGolem golem = p.getWorld().spawn(p.getLocation(), org.bukkit.entity.IronGolem.class);
        Wolf wolf = p.getWorld().spawn(p.getLocation(), Wolf.class);
        wolf.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1));
        wolf.setPassenger(golem);
        wolf.setBaby();
        wolf.setBreed(true);
        wolf.setOwner(p);
    }

    public static void dano(EntityDamageByEntityEvent event) {
        if (event.getDamager().getType() == EntityType.WOLF) {
            if (event.getDamager().getPassenger() != null && event.getDamager().getPassenger().getType() == EntityType.IRON_GOLEM) {
                EntityIronGolem golem = (EntityIronGolem) ((CraftEntity) event.getDamager().getPassenger()).getHandle();
                golem.world.broadcastEntityEffect(golem, (byte) 4);
                //golem.world.makeSound(golem, "mob.irongolem.throw", 1.0F, 1.0F);
            } else {
                event.setDamage(event.getDamage() * 4);
            }
            if (event.getEntity() instanceof Monster) {
                event.setDamage(event.getDamage() * 3);
            }
        } else if (event.getEntity().getType() == EntityType.WOLF) {
            if (event.getEntity().getPassenger() != null && event.getEntity().getPassenger().getType() == EntityType.IRON_GOLEM) {
                event.setCancelled(true);
            }
        }
    }

}
