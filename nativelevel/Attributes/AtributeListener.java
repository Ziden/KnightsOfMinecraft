package nativelevel.Attributes;


import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.mobs.ActiveMob;
import java.util.Arrays;
import java.util.List;
import nativelevel.Dano;
import nativelevel.Jobs;
import nativelevel.KoM;
import nativelevel.MetaShit;
import nativelevel.Equipment.EquipManager;
import nativelevel.Equipment.EquipMeta;
import nativelevel.Equipment.Atributo;
import nativelevel.Classes.Mage.SpellParticleEffects;
import nativelevel.Classes.Mage.spelllist.Paralyze;
import nativelevel.sisteminhas.KomSystem;
import nativelevel.utils.LocUtils;
import net.minecraft.server.v1_12_R1.ItemShield;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.EntityEffect;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.SmallFireball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;

/**
 *
 * @author User
 *
 */
/*

 Nessa classe vai conter as formulas base de dano, q nao precisam ser implementadas nas skills
 por exemplo, dano físico, armadura etc

 */
public class AtributeListener extends KomSystem {

    /*
    
     CALCULANDO DANO ARCO
    
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void dealDamage(EntityDamageByEntityEvent ev) {
        if (ev.getDamager() instanceof Projectile) {
            Projectile pj = (Projectile) ev.getDamager();
            if (pj.getShooter() != null && pj.getShooter() instanceof Player) {
                Player atirador = (Player) pj.getShooter();
                // somando dano de flehas
                EquipMeta equips = EquipManager.getPlayerEquipmentMeta(atirador);
                ev.setDamage(ev.getDamage() + (ev.getDamage() * (equips.getAttribute(Atributo.Dano_Distancia) / 100)));
            }
        }
    }

    /*
    
     Stun
    
     */
    public void stun(Player batendo, final LivingEntity tomando, EquipMeta metaBatendo) {

        double chanceStun = metaBatendo.getAttribute(Atributo.Chance_Stun);
        if (Jobs.rnd.nextInt(100) < chanceStun) {
            double tempoStun = metaBatendo.getAttribute(Atributo.Tempo_Stun);
            Paralyze.paraliza(tomando);
            batendo.sendMessage(ChatColor.GREEN + "Paralizou o alvo");
            Runnable de = new Runnable() {
                public void run() {
                    if (tomando == null) {
                        return;
                    }

                    if (Paralyze.isParalizado(tomando)) {
                        Paralyze.removeParalize(tomando);
                    }
                }
            };
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, de, (int) tempoStun);
        }

    }

    /*
    
     Critico
    
     */
    public void critico(Player batendo, EntityDamageByEntityEvent ev, EquipMeta meta) {
        double chanceAcertoCritico = meta.getAttribute(Atributo.Chance_Critico);
        if (chanceAcertoCritico > 0) {
            KoM.debug("Chance Critico " + chanceAcertoCritico);
            if (Jobs.rnd.nextInt(100) < chanceAcertoCritico) {
                batendo.sendMessage("§c§lVoce acertou um crítico !");
                batendo.playEffect(ev.getEntity().getLocation(), Effect.CRIT, 1);
                double bonusCrit = 0.1;
                bonusCrit += (meta.getAttribute(Atributo.Dano_Critico) + 1) / 500d;
                if (bonusCrit < 0) {
                    bonusCrit = 0;
                }
                KoM.debug("Bonus Crit: " + bonusCrit);
                ev.setDamage(ev.getDamage() + (ev.getDamage() * bonusCrit));
            }
        }
    }

    /*
    
     REGEN VIDA
     */
    @EventHandler
    public static void regen(EntityRegainHealthEvent ev) {
        if (ev.getRegainReason() == RegainReason.SATIATED) {
            if (Jobs.rnd.nextInt(5) != 1) {
                ev.setCancelled(true);
            }
        }
        /*
         if (ev.getEntity().getType() == EntityType.PLAYER) {
         double qtd = ev.getAmount();
         Player p = (Player) ev.getEntity();
         EquipMeta meta = EquipManager.getPlayerEquipmentMeta(p);
         if (ev.getRegainReason() == RegainReason.REGEN || ev.getRegainReason() == RegainReason.SATIATED) {
         double bonus = meta.getAttribute(Atributo.Regeneração_Vida);
         if (bonus > 0) {
         ev.setAmount(ev.getAmount() + bonus);
         }
         }
         }
         */
    }


    /*
     Calculando dano físico e acerto crítico
     */
    @EventHandler(priority = EventPriority.HIGH)
    public void entityDamage(EntityDamageByEntityEvent ev) {

        KoM.debug(" entityDamage do atributo " + ev.getDamage() + " ev " + ev.getEntity().getName());

        // player hits a player
        if (ev.getDamager().getType() == EntityType.PLAYER) {
            Player batendo = (Player) ev.getDamager();
            EquipMeta meta = EquipManager.getPlayerEquipmentMeta(batendo);
            // + Dano Físico ,contando apenas para porrada mesmo
            if (ev.getCause() == DamageCause.ENTITY_ATTACK) {
                double neutralDamage = meta.getAttribute(Atributo.Dano_Fisico);
                if (neutralDamage == 0) {
                    neutralDamage = 1;
                }
                neutralDamage = neutralDamage / 100d;
                ev.setDamage(ev.getDamage() + (ev.getDamage() * neutralDamage));
            }
            ////// CRITICO ////////
            critico(batendo, ev, meta);
            if (ev.getEntity() instanceof LivingEntity) {
                stun(batendo, (LivingEntity) ev.getEntity(), meta);
            }
            ////////////////////////
            //roubarVida(batendo, ev, meta);
            //// PROJECTILE DAMAGE /////
        } else if (ev.getDamager() instanceof Projectile && ((Projectile) ev.getDamager()).getShooter() instanceof Player) {
            Player shooter = (Player) ((Projectile) ev.getDamager()).getShooter();
            EquipMeta meta = EquipManager.getPlayerEquipmentMeta(shooter);
            if (ev.getDamager() instanceof Arrow) {
                double neutralDamage = meta.getAttribute(Atributo.Dano_Distancia);
                if (neutralDamage == 0) {
                    neutralDamage = 1;
                }
                neutralDamage = neutralDamage / 100d;
                ev.setDamage(ev.getDamage() + (ev.getDamage() * neutralDamage));
            }
            critico(shooter, ev, meta);
        }
    }

    /////// ARMOR WILL PROTECT DAMAGE //////
    public static List<DamageCause> armorProtects = Arrays.asList(new DamageCause[]{
        DamageCause.CONTACT,
        DamageCause.FALLING_BLOCK,
        DamageCause.ENTITY_ATTACK,
        DamageCause.ENTITY_EXPLOSION,
        DamageCause.PROJECTILE,
        DamageCause.THORNS
    });

    @EventHandler(priority = EventPriority.MONITOR)
    public void takeDamage(EntityDamageEvent ev) {

        KoM.debug("Chegando dano " + ev.getDamage() + " em " + ev.getEntity().getName()+" na loc "+LocUtils.loc2str(ev.getEntity().getLocation()));

        if (ev.getEntity() instanceof LivingEntity) {
            LivingEntity e = (LivingEntity) ev.getEntity();

            if (ev.getDamage() == 0) {
                ev.setCancelled(true);
                return;
            }

            double damage = ev.getDamage();
            double finalDamage = damage;

            if (ev.getEntity().getType() == EntityType.PLAYER) {
                Player tomou = (Player) ev.getEntity();
                EquipMeta equipTomou = EquipManager.getPlayerEquipmentMeta(tomou);
                double armor = equipTomou.getAttribute(Atributo.Armadura);
                /*
                 if (ev.getCause() == DamageCause.FIRE || ev.getCause() == DamageCause.LAVA || ev.getCause() == DamageCause.FIRE_TICK) {
                 double mult = equipTomou.getAttribute(Atributo.Resistencia_Fogo);
                 if (mult > 0) {
                 mult = mult / 100;
                 ev.setDamage(ev.getDamage() - (ev.getDamage() * mult));
                 }
                 } else if (ev.getCause() == DamageCause.ENTITY_EXPLOSION) {
                 double mult = equipTomou.getAttribute(Atributo.Resistencia_Explosao);
                 if (mult > 0) {
                 mult = mult / 100;
                 ev.setDamage(ev.getDamage() - (ev.getDamage() * mult));
                 }
                 }
                 */
                if (ev.getCause() == DamageCause.ENTITY_ATTACK || ev.getCause() == DamageCause.ENTITY_SWEEP_ATTACK) {
                    double esquiva = equipTomou.getAttribute(Atributo.Chance_Esquiva);
                    if (Jobs.rnd.nextInt(100) < esquiva) {
                        tomou.sendMessage(ChatColor.GREEN + "Voce se esquivou do ataque");
                        SpellParticleEffects.colorida(tomou.getLocation(), 0, 255, 255);
                        ev.setCancelled(true);
                        ev.setDamage(0);
                        return;
                    }
                }
                /*else if (ev.getCause() == DamageCause.WITHER) {
                 double mult = equipTomou.getAttribute(Atributo.Resistencia_Wither);
                 if (mult > 0) {
                 mult = mult / 100;
                 ev.setDamage(ev.getDamage() - (ev.getDamage() * mult));
                 }
                 } else if (ev.getCause() == DamageCause.POISON) {
                 double mult = equipTomou.getAttribute(Atributo.Resistencia_Veneno);
                 if (mult > 0) {
                 mult = mult / 100;
                 ev.setDamage(ev.getDamage() - (ev.getDamage() * mult));
                 }
                 }
                 */
                KoM.debug("Depois das resists: " + ev.getDamage());

                //////////////////////////
                /// CALCULANDO ARMADURA //
                //////////////////////////
                if (armorProtects.contains(ev.getCause())) {

                    double armorPenetration = 0;

                    if (ev instanceof EntityDamageByEntityEvent) {

                        EntityDamageByEntityEvent ev2 = (EntityDamageByEntityEvent) ev;
                        if (ev2.getDamager().getType() == EntityType.PLAYER) {
                            Player batendo = (Player) ev2.getDamager();
                            EquipMeta meta = EquipManager.getPlayerEquipmentMeta(batendo);
                            armorPenetration = meta.getAttribute(Atributo.Penetr_Armadura);
                        } else if (ev2.getDamager().getType() == EntityType.ARROW) {
                            Arrow a = (Arrow) ev2.getDamager();
                            if (a.getShooter() instanceof Player) {
                                Player atirador = (Player) a.getShooter();
                                EquipMeta meta = EquipManager.getPlayerEquipmentMeta(atirador);
                                armorPenetration = meta.getAttribute(Atributo.Penetr_Armadura);
                            }
                        }
                    }

                    // resisting damage from ARMOR
                    armor -= armorPenetration;
                    if (armor < 0) {
                        armor = 0;
                    }

                    armor *= 2;

                    KoM.debug("Armor: " + armor);

                    double formulaArmor = 50 / ((double) armor + 50d);

                    KoM.debug("FORMULA: " + formulaArmor);

                    KoM.debug("Dano: " + ev.getDamage());

                    double reduzido = ev.getDamage() - (ev.getDamage() * formulaArmor);

                    ev.setDamage(ev.getDamage() * formulaArmor);

                    KoM.debug("Depois dos calculos: " + ev.getDamage());

                }
                KoM.dano.mostraDano((Player) ev.getEntity(), ev.getDamage(), Dano.TOMEI);
            }

            /*
             if (finalDamage > 0 && !ev.isCancelled()) {

             double life = e.getHealth() - finalDamage;
             if (life < 0) {
             life = 0;
             }
             e.setLastDamageCause(ev);
             e.setLastDamage(finalDamage);
             e.setHealth(life);
             e.playEffect(EntityEffect.HURT);
             ev.setCancelled(true);
             KoM.debug("DANO "+finalDamage);
             } else {

             }
             */
        }
    }

}
