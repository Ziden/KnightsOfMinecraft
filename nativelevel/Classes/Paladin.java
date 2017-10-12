/*

 ╭╮╭━╮╱╱╭━╮╭━╮
 ┃┃┃╭╯╱╱┃┃╰╯┃┃
 ┃╰╯╯╭━━┫╭╮╭╮┃
 ┃╭╮┃┃╭╮┃┃┃┃┃┃
 ┃┃┃╰┫╰╯┃┃┃┃┃┃
 ╰╯╰━┻━━┻╯╰╯╰╯

 Desenvolvedor: ZidenVentania
 Colaboradores: NeT32, Gabripj, Feldmann
 Patrocionio: InstaMC

 */
package nativelevel.Classes;

import me.fromgate.playeffect.PlayEffect;
import me.fromgate.playeffect.VisualEffect;
import nativelevel.Custom.CustomItem;
import nativelevel.Custom.Items.Ank;
import nativelevel.Custom.Items.FolhaDeMana;
import nativelevel.Jobs;
import nativelevel.Menu.Menu;
import nativelevel.KoM;
import nativelevel.MetaShit;
import nativelevel.Attributes.Mana;
import nativelevel.spec.PlayerSpec;
import nativelevel.sisteminhas.Tralhas;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class Paladin {

    private static final Material[] lootsExtras = {Material.GOLDEN_APPLE, Material.CAKE, Material.EMERALD, Material.GLOWSTONE_DUST, Material.COOKIE, Material.COOKIE, Material.COOKIE, Material.COOKIE, Material.COOKIE, Material.COOKIE};

    public static boolean ehEspada(Material m) {
        if (m == Material.WOOD_SWORD || m == Material.STONE_SWORD || m == Material.IRON_SWORD || m == Material.GOLD_SWORD || m == Material.DIAMOND_SWORD) {
            return true;
        }
        return false;
    }
    //  ლ(ಠ益ಠლ) paladinos...odeia....magos... !!!

    public static void swordHit(EntityDamageByEntityEvent event, Player bateu) {
     
    }

    public static void usaAnk(EntityDamageEvent ev, Player p) {
        // dano matador

        if (KoM.debugMode) {
            KoM.log.info(p.getName() + " ativando ressurrenct");
        }
        if (ev.getDamage() > p.getHealth()) {
            if (Ank.protegidos.contains(p.getName())) {
                if (KoM.debugMode) {
                    KoM.log.info(p.getName() + " removendo hashmap");
                }
                Ank.protegidos.remove(p.getName());
                p.sendMessage(ChatColor.GREEN + "Ativando luz divina !");
                if (KoM.debugMode) {
                    KoM.log.info(p.getName() + " criando item");
                }
                ItemStack ank = CustomItem.getItem(Ank.class).generateItem(1);
                KoM.gastaCustomItem(p, ank);
                if (KoM.debugMode) {
                    KoM.log.info(p.getName() + " verificando se tem custom item");
                }
                if (KoM.temCustomItem(p, ank)) {
                    p.sendMessage(ChatColor.AQUA + Menu.getSimbolo("Paladino") + " " + ChatColor.GOLD + "A outra Luz que voce tem evita que voce volte a vida !");
                } else {
                    PlayEffect.play(VisualEffect.FIREWORKS_EXPLODE, p.getLocation(), "type:star color:white");
                    p.setHealth(p.getMaxHealth());
                    p.getWorld().createExplosion(p.getLocation(), 0);
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 300, 1));
                    ev.setDamage(1D);
                    ev.setCancelled(true);
                    return;
                }

            }
        }
    }

    public static void pegaDropsExtrasDeMobs(Entity e, Player p) {
        if (!ehEspada(p.getItemInHand().getType())) {
            return;
        }
        if (e instanceof Creature) {
            int level = Jobs.getJobLevel("Paladino", p);
            int rnd = 0;
            if (level == 1) // primaria 
            {
                rnd = 30;
            }
            if (level == 2) {
                rnd = 80;
            }
            if (rnd != 0) {
                if (Jobs.rnd.nextInt(rnd) == 1) {
                    p.sendMessage(ChatColor.AQUA + Menu.getSimbolo("Paladino") + " " + ChatColor.YELLOW + "Voce foi abencoado pelo deus Jabu !");
                    // dropando coisa boa
                    int item = Jobs.rnd.nextInt(lootsExtras.length);
                    p.getWorld().dropItem(e.getLocation(), new ItemStack(lootsExtras[item], 1));
                    // dropando reagentes de alquimia
                    item = 370 + Jobs.rnd.nextInt(382 - 370);
                    p.getWorld().dropItem(e.getLocation(), new ItemStack(item, 1));
                }
            }
        }
    }

    public static void tomaMenosDano(EntityDamageEvent ev, Player p) {

        int level = Jobs.getJobLevel("Paladino", p);
        int rnd = 0;
        // chance de evitar dano
        if (level == 1) // primaria
        {
            rnd = 10;
       
            if (p.getItemInHand().getType() == Material.IRON_DOOR && p.getLocation().getBlock().getType() == Material.WEB) {
                p.sendMessage(ChatColor.RED + "Voce nao conseguiu bloquear o ataque por estar preso em teias");
            } else {
                if (p.getItemInHand().getType() == Material.IRON_DOOR && (ev.getCause() == DamageCause.ENTITY_ATTACK || ev.getCause() == DamageCause.CONTACT || ev.getCause() == DamageCause.PROJECTILE)) {
                    Entity causador = null;
                    if (ev instanceof EntityDamageByEntityEvent) {
                        causador = ((EntityDamageByEntityEvent) ev).getDamager();
                        Vector chave = causador.getLocation().getDirection();
                        if (ev.getCause() == DamageCause.PROJECTILE) {
                            chave = causador.getVelocity();
                            chave.setY(1);
                            chave = chave.normalize();
                        }
                        double angle = Tralhas.getAngle(p.getLocation().getDirection(), chave);
                        if (KoM.debugMode) {
                            KoM.log.info("Angle: " + angle);
                        }
                        if (angle < 50) {
                            p.sendMessage(ChatColor.RED + "Voce tem que estar de frente para poder bloquear o ataque !");
                            return;
                        }
                    }
                    int mana = 30;
                    if (PlayerSpec.temSpec(p, PlayerSpec.Guardiao)) {
                        mana = 10;
                    } else if (PlayerSpec.temSpec(p, PlayerSpec.Crusador)) {
                        mana = 60;
                    }
                    if (Mana.spendMana(p, mana)) {
                        p.sendMessage(ChatColor.GOLD + "Voce bloqueou o ataque");
                        PlayEffect.play(VisualEffect.FIREWORKS_SPARK, p.getLocation(), "num:10");
                        PlayEffect.play(VisualEffect.SOUND, p.getLocation(), "type:zombie_metal");
                        ev.setDamage(0f);
                        ev.setCancelled(true);
                        p.setNoDamageTicks(20);
                        if(ev instanceof EntityDamageByEntityEvent) {
                            EntityDamageByEntityEvent ev2 = (EntityDamageByEntityEvent)ev;
                            if(ev2.getDamager().getType()==EntityType.PLAYER) {
                                Player bateu = (Player) ev2.getDamager();
                                if(Thief.taInvisivel(bateu))
                                    Thief.revela(bateu);
                            }
                        }
                    }
                    return;
                }
            }
                

        }
        if (level == 2) {
            rnd = 50;
        }
        if (rnd != 0) {
            if (Jobs.rnd.nextInt(rnd) == 1) {
                ev.setDamage(0);
                p.playEffect(p.getLocation(), Effect.MOBSPAWNER_FLAMES, 20);
                p.playEffect(p.getLocation(), Effect.GHAST_SHOOT, 20);
                p.sendMessage(ChatColor.AQUA + Menu.getSimbolo("Paladino") + " " + ChatColor.GOLD + "Deus Jabu te protege do mal !");
                return;
            }
        } else {
            return;
        }
        // se tomar, reduz o dano
        double reducao = 0;
        if (level == 1) // primaria
        {
            reducao = 1;
        }
        if (level == 2) {
            reducao = 0;
        }

        if (ev.getDamage() <= reducao) {
            ev.setDamage(1D);
        } else {
            ev.setDamage(ev.getDamage() - reducao);
        }
    }
}
