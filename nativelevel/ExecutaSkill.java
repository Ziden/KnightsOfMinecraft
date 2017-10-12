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
package nativelevel;

import me.fromgate.playeffect.PlayEffect;
import me.fromgate.playeffect.VisualEffect;
import nativelevel.Listeners.GeneralListener;
import nativelevel.Classes.Alchemy.Alchemist;
import nativelevel.Classes.Engineer;
import nativelevel.Classes.Farmer;
import nativelevel.Classes.Blacksmithy.Blacksmith;
import nativelevel.Classes.Thief;
import nativelevel.Classes.Lumberjack;
import nativelevel.Classes.Mage.Wizard;
import nativelevel.Classes.Minerador;
import nativelevel.Classes.Paladin;
import nativelevel.Custom.CustomItem;
import nativelevel.Custom.Items.CajadoElemental;
import nativelevel.Lang.L;
import nativelevel.Attributes.AttributeInfo;
import nativelevel.Attributes.Mana;
import nativelevel.Attributes.Stamina;
import nativelevel.integration.SimpleClanKom;
import nativelevel.karma.Criminoso;
import nativelevel.sisteminhas.ClanLand;
import nativelevel.spec.PlayerSpec;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.EntityEffect;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Creature;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ExecutaSkill {

    public static void playerToma(final EntityDamageEvent ev) {

        KoM.debug("Dano no começo do playertoma " + ev.getDamage());
        if (ev.getEntity() instanceof Player) {
            Player p = (Player) ev.getEntity();

            if(Thief.taInvisivel(p))
                Thief.revela(p);
            
            if (p.isBlocking()) { //if (p.getItemInHand().getType() == Material.SHIELD || p.getInventory().getItemInOffHand().getType() == Material.SHIELD) {

                if (Jobs.getJobLevel(Jobs.Classe.Paladino, p) != Jobs.TipoClasse.PRIMARIA || !Mana.spendMana(p, 15)) {

                    int firstEmpty = p.getInventory().firstEmpty();

                    p.sendMessage(ChatColor.RED + "Voce nao consegue defender direito e quase perdeu seu escudo");

                    if (firstEmpty != -1) {

                        if (p.getItemInHand().getType() == Material.SHIELD) {

                            p.getInventory().addItem(p.getItemInHand().clone());
                            p.setItemInHand(new ItemStack(Material.AIR, 1));

                        } else if (p.getInventory().getItemInOffHand().getType() == Material.SHIELD) {

                            p.getInventory().addItem(p.getInventory().getItemInOffHand().clone());
                            p.getInventory().setItemInOffHand(new ItemStack(Material.AIR, 1));
                        }

                    } else {

                        if (p.getItemInHand().getType() == Material.SHIELD) {

                            p.getInventory().addItem(p.getItemInHand().clone());
                            p.getWorld().dropItem(p.getLocation(), p.getItemInHand());

                        } else if (p.getItemInHand().getType() == Material.SHIELD) {

                            p.getWorld().dropItem(p.getLocation(), p.getInventory().getItemInOffHand());
                            p.getInventory().addItem(p.getInventory().getItemInOffHand().clone());
                        }

                    }
                    ev.setCancelled(true);
                    double vida = p.getHealth() - ev.getDamage();
                    if (vida < 0) {
                        vida = 0;
                    }
                    p.setHealth(vida);
                } else {
                    PlayEffect.play(VisualEffect.FIREWORKS_SPARK, p.getLocation(), "num:10");
                    PlayEffect.play(VisualEffect.SOUND, p.getLocation(), "type:zombie_metal");
                }

            }

            if (p.getVehicle() != null) {
                p.eject();
            }

            Paladin.usaAnk(ev, p);

            if (ev.getCause() == DamageCause.FIRE || ev.getCause() == DamageCause.FIRE_TICK || ev.getCause() == DamageCause.LAVA) {
                Blacksmith.evitaFogo(ev, (Player) ev.getEntity());
            }
            String cajadoElemental = null;
            if (p.getItemInHand() != null) {
                String ci = CustomItem.getCustomItem(p.getItemInHand());
                if (ci != null && ci.equalsIgnoreCase("Cajado Elemental") && Jobs.getJobLevel("Mago", p) == 1) {
                    cajadoElemental = CajadoElemental.getElemento(p.getItemInHand());
                    if (cajadoElemental == null) {
                        cajadoElemental = "Nulo";
                    }
                }
            }
            if (ev.getCause() == DamageCause.POISON || ev.getCause() == DamageCause.WITHER) {
                if (cajadoElemental != null) {
                    if (cajadoElemental.equalsIgnoreCase("Nulo")) {
                        CajadoElemental.botaElemento(p.getItemInHand(), "Veneno");
                        p.sendMessage(ChatColor.GREEN + "Seu cajado absorveu o elemento Veneno");
                    } else if (cajadoElemental.equalsIgnoreCase("Veneno")) {
                        ev.setDamage(ev.getDamage() * 0.8);
                    }
                }

                int amp = 0;
                if (Jobs.getJobLevel("Ladino", p) == 1) {
                    amp++;
                }

                if (ev.getCause() == DamageCause.POISON && Jobs.getJobLevel("Alquimista", p) == 1 && p.getLevel() > 5) {
                    ev.setCancelled(true);
                    return;
                }

                if (p.hasMetadata("dot")) {
                    long dot = (Long) MetaShit.getMetaObject("dot", p);
                    long agora = System.currentTimeMillis() / 1000;

                    if (agora <= dot) {
                        ev.setCancelled(true);
                        return;
                    }
                }
                MetaShit.setMetaObject("dot", p, System.currentTimeMillis() / 1000);
                ev.setCancelled(true);
                double dano = ev.getDamage();
                double vida = p.getHealth();
                for (PotionEffect ef : p.getActivePotionEffects()) {
                    if (ef.getType() == PotionEffectType.POISON || ef.getType() == PotionEffectType.WITHER) {
                        amp += (ef.getAmplifier() - 1);
                    }
                }
                dano += amp;
                if (dano <= vida) {
                    vida -= dano;
                    p.setHealth(vida);
                    p.playEffect(EntityEffect.HURT);
                } else {
                    p.setHealth(0);
                }
            } else if (ev.getCause() == DamageCause.BLOCK_EXPLOSION || ev.getCause() == DamageCause.ENTITY_EXPLOSION) {

            } else if (ev.getCause() == DamageCause.FIRE || ev.getCause() == DamageCause.FIRE_TICK || ev.getCause() == DamageCause.LAVA) {

                if (Jobs.getJobLevel("Lenhador", p) == 1) {
                    ev.setDamage(ev.getDamage() * 1.6);
                }
                if (PlayerSpec.temSpec(p, PlayerSpec.Ceifador)) {
                    ev.setDamage(ev.getDamage() * 2);
                } else if (PlayerSpec.temSpec(p, PlayerSpec.Pescador)) {
                    ev.setDamage(ev.getDamage() * 0.6);
                }
                if (cajadoElemental != null) {
                    if (cajadoElemental.equalsIgnoreCase("Nulo")) {
                        CajadoElemental.botaElemento(p.getItemInHand(), "Fogo");
                        p.sendMessage(ChatColor.GREEN + "Seu cajado absorveu o elemento Fogo");
                    } else if (cajadoElemental.equalsIgnoreCase("Fogo")) {
                        ev.setDamage(ev.getDamage() * 0.8);
                    }
                }
            } else if (ev.getCause() == DamageCause.LIGHTNING) {

                if (Jobs.getJobLevel("Paladino", p) == 1) {
                    ev.setDamage(ev.getDamage() * 1.2);
                }

                if (cajadoElemental != null) {
                    if (cajadoElemental.equalsIgnoreCase("Nulo")) {
                        CajadoElemental.botaElemento(p.getItemInHand(), "Raio");
                        p.sendMessage(ChatColor.GREEN + "Seu cajado absorveu o elemento Raio");
                    } else if (cajadoElemental.equalsIgnoreCase("Raio")) {
                        ev.setDamage(ev.getDamage() * 0.8);
                    }
                }
            } else if (ev.getCause() == DamageCause.MAGIC) {

                if (Jobs.getJobLevel("Ferreiro", p) == 1 || Jobs.getJobLevel("Engenheiro", p) == 1) {
                    ev.setDamage(ev.getDamage() * 1.6);
                }

                if (cajadoElemental != null) {
                    if (cajadoElemental.equalsIgnoreCase("Nulo")) {
                        CajadoElemental.botaElemento(p.getItemInHand(), "Agua");
                        p.sendMessage(ChatColor.GREEN + "Seu cajado absorveu o elemento Agua");
                    } else if (cajadoElemental.equalsIgnoreCase("Agua")) {
                        ev.setDamage(ev.getDamage() * 0.8);
                    }
                }
            }

            Paladin.tomaMenosDano(ev, p);
            if (p.getLocation().getBlock().getLightLevel() > 3) {
                Thief.revela(p);
            }
        }
    }

    public static void playerBate(EntityDamageByEntityEvent event) {

        KoM.debug("Dano no começo do playerBate " + event.getDamage());

        if (event.getDamager() instanceof Player) {
            Player attacker = (Player) event.getDamager();

            Paladin.swordHit(event, attacker);
            if (event.isCancelled()) {
                return;
            }
            
            double ratioNivel = 1 + (attacker.getLevel() / 100d) / 2d;
            event.setDamage(event.getDamage() * ratioNivel);

            if (!CustomItem.podeUsar(attacker, attacker.getItemInHand())) {
                event.setCancelled(true);
                return;
            }

            if (!Stamina.spendStamina(attacker, 1)) {
                attacker.sendMessage(ChatColor.RED + L.m("Voce esta sem stamina !"));
                event.setCancelled(true);
                return;
            }

            //if (event.getEntity() instanceof LivingEntity) {
            //SpecialEquipment.applyEffects(attacker, (LivingEntity) event.getEntity());
            // }
            if (attacker.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) {
                attacker.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
            }
            if (attacker.hasMetadata("tempoHit")) {
                long tempoHit = (long) MetaShit.getMetaObject("tempoHit", attacker);
                long tempoAgora = System.currentTimeMillis();
                if (tempoHit + 200 > tempoAgora) {
                    event.setCancelled(true);
                    return;
                }
            }
            if (Jobs.getJobLevel("Paladino", attacker) == 1) {
                MetaShit.setMetaObject("tempoHit", attacker, System.currentTimeMillis() + 200);

            } else {
                MetaShit.setMetaObject("tempoHit", attacker, System.currentTimeMillis());
            }

            if (event.getEntity() instanceof Creature && attacker.getItemInHand() != null && attacker.getItemInHand().getType() == Material.EGG) {
                Farmer.transformaEmOvO(attacker, event.getEntity());
                return;
            }
            // player bate em player
            if (event.getEntity() instanceof Player) {

                Player defender = (Player) event.getEntity();

                if (!SimpleClanKom.canPvp(attacker, defender)) {
                    event.setCancelled(true);
                    return;
                }

                String customItem = CustomItem.getCustomItem(attacker.getItemInHand());
                if (customItem != null) {
                    CustomItem.hitWithCustomItem(customItem, event, attacker, defender);
                }
                if (event.isCancelled()) {
                    return;
                }

                if (Jobs.getJobLevel("Ferreiro", defender) == 1 && event.getCause() != DamageCause.PROJECTILE && defender.getLevel() >= 6) {
                    event.setDamage(event.getDamage() * 0.80);
                }
                if (Jobs.getJobLevel("Paladino", defender) == 1 && event.getCause() != DamageCause.PROJECTILE) {
                    event.setDamage(event.getDamage() * 0.90);
                }
                if (Jobs.getJobLevel("Ferreiro", attacker) == 1) {
                    event.setDamage(event.getDamage() * 0.85);
                    if (PlayerSpec.temSpec(attacker, PlayerSpec.Forjador)) {
                        event.setDamage(event.getDamage() * 0.90);
                    }
                }
                if (Jobs.getJobLevel("Ladino", defender) == 1) {
                    if (PlayerSpec.temSpec(attacker, PlayerSpec.Assassino)) {
                        if (customItem != null && customItem.equalsIgnoreCase("Adaga da Ponta Diamantada")) {
                            event.setDamage(event.getDamage() * 1.6);
                        }
                    }
                    if (defender.getLocation().getBlock().getLightLevel() < 3) {
                        event.setDamage(event.getDamage() * 1.2);
                    }
                }

                if ((event.getCause() == DamageCause.ENTITY_ATTACK || event.getCause() == DamageCause.CONTACT)) {
                    if (PlayerSpec.temSpec(defender, PlayerSpec.Guardiao)) {
                        event.setDamage(event.getDamage() * 0.9);
                    }
                    if (PlayerSpec.temSpec(attacker, PlayerSpec.Guardiao)) {
                        event.setDamage(event.getDamage() * 0.85);
                    } else if (PlayerSpec.temSpec(attacker, PlayerSpec.Crusador)) {
                        if (customItem == null && attacker.getItemInHand() != null && attacker.getItemInHand().getType().name().contains("SWORD")) {
                            event.setDamage(event.getDamage() * 1.3);
                        }
                    }
                }

                if (defender.hasMetadata("shield")) {
                    defender.sendMessage(ChatColor.GREEN + L.m("Voce refletiu o ataque e o escudo se dissipou"));
                    defender.removeMetadata("shield", KoM._instance);
                    attacker.damage(event.getDamage(), defender);
                    event.setDamage(0);
                    return;
                }

                /*
                 double chanceEsquiva = Attributes.calcDodgeChance(infoTomou.attributes.get(Attr.agility), infoBateu.attributes.get(Attr.dexterity));
                 int ladino = Jobs.getJobLevel("Ladino", defender);
                 if (ladino == 1) {
                 chanceEsquiva += 15;
                 } else if (ladino == 2) {
                 chanceEsquiva += 5;
                 }
               
                 if (chanceEsquiva > 0 && Jobs.rnd.nextInt(100) < chanceEsquiva) {
                 attacker.sendMessage(ChatColor.RED + defender.getName() + L.m(" se esquivou do ataque !"));
                 defender.sendMessage(ChatColor.GREEN + L.m("Voce se esquivou do ataque !"));
                 PlayEffect.play(VisualEffect.SPELL, defender.getLocation(), "num:1");
                 defender.setNoDamageTicks(20);
                 event.setCancelled(true);

                 if (attacker.getItemInHand().getType() == Material.WOOD_AXE || attacker.getItemInHand().getType() == Material.GOLD_AXE || attacker.getItemInHand().getType() == Material.IRON_AXE || attacker.getItemInHand().getType() == Material.STONE_AXE || attacker.getItemInHand().getType() == Material.DIAMOND_AXE) {
                 // verificando maxadada epica
                 if (attacker.hasMetadata("epichax")) {
                 int task = (int) MetaShit.getMetaObject("epichax", attacker);
                 Bukkit.getScheduler().cancelTask(task);
                 attacker.removeMetadata("epichax", KnightsOfMania._instance);
                 if (attacker.hasMetadata("epichaxpronta")) {

                 } else {
                 attacker.sendMessage(ChatColor.RED + L.m("Voce afobou a machadada epica !"));
                 }
                 }
                 //event.setDamage(event.getDamage()*1.1);
                 }

                 return;
                 }
                 */
                if (attacker.isSneaking()) {
                    Blacksmith.tentaDerrubarArmadura(event);
                }

                // blacksmith blaze rod set on fire
                if (attacker.getItemInHand() != null && attacker.getItemInHand().getType() == Material.BLAZE_ROD && attacker.getLevel() >= 35 && Jobs.getJobLevel("Ferreiro", attacker) == 1) {
                    attacker.sendMessage(ChatColor.GREEN + L.m("Voce usou a blaze rod para tocar fogo no inimigo !"));
                    defender.setFireTicks(20 * 40);
                    defender.getWorld().playEffect(defender.getLocation(), Effect.EXPLOSION_HUGE, 2);
                    if (attacker.getItemInHand().getAmount() == 1) {
                        attacker.setItemInHand(null);
                    } else {
                        attacker.getItemInHand().setAmount(attacker.getItemInHand().getAmount() - 1);
                    }
                }

                if (!defender.getWorld().getName().equalsIgnoreCase("Woe")) {
                    if (event.getDamage() > 0 && !event.isCancelled()) {
                        //MetaShit.setMetaObject("logout", defender, System.currentTimeMillis() / 1000);
                    }
                }

                // 0 damage no soco
                if (attacker.getItemInHand() == null || attacker.getItemInHand().getType() == Material.AIR) {
                    event.setDamage(0D);
                    event.setCancelled(true);
                    return;
                }

                if (Jobs.getJobLevel("Ladino", attacker) == 1) {
                    event.setDamage(event.getDamage() * 1.1);
                    // if (PlayerSpec.temSpec(attacker, PlayerSpec.Assassino)) {
                    //     event.setDamage(event.getDamage() * 1.40);
                    if (PlayerSpec.temSpec(attacker, PlayerSpec.Ranger)) {
                        event.setDamage(event.getDamage() * 0.8);
                    }
                }
                if (attacker.getItemInHand().getType().name().contains("SPADE")) {
                    if (Jobs.getJobLevel("Ferreiro", attacker) == 1 && attacker.getLevel() >= 4) {
                        if (attacker.getItemInHand().getType() == Material.GOLD_SPADE) {
                            event.setDamage(event.getDamage() * 1.65);
                        } else {
                            event.setDamage(event.getDamage() * 1.4);
                        }
                        if (PlayerSpec.temSpec(attacker, PlayerSpec.Macador)) {
                            event.setDamage(event.getDamage() + 8D);
                        }
                    }
                } // se ta batendo com MAXADOS
                else if (attacker.getItemInHand().getType() == Material.WOOD_AXE || attacker.getItemInHand().getType() == Material.GOLD_AXE || attacker.getItemInHand().getType() == Material.IRON_AXE || attacker.getItemInHand().getType() == Material.STONE_AXE || attacker.getItemInHand().getType() == Material.DIAMOND_AXE) {
                    // verificando maxadada epica
                    if (attacker.hasMetadata("epichax")) {
                        int task = (int) MetaShit.getMetaObject("epichax", attacker);
                        Bukkit.getScheduler().cancelTask(task);
                        attacker.removeMetadata("epichax", KoM._instance);
                        if (attacker.hasMetadata("epichaxpronta")) {
                            attacker.removeMetadata("epichaxpronta", KoM._instance);
                            Lumberjack.tentaMachadadaEpica(defender, attacker, event);
                        } else {
                            attacker.sendMessage(ChatColor.RED + L.m("Voce afobou a machadada epica !"));
                        }
                    }
                    //event.setDamage(event.getDamage()*1.1);
                } // se ta batendo com hoes
                else if (attacker.getItemInHand().getType() == Material.WOOD_HOE || attacker.getItemInHand().getType() == Material.GOLD_HOE || attacker.getItemInHand().getType() == Material.IRON_HOE || attacker.getItemInHand().getType() == Material.STONE_HOE || attacker.getItemInHand().getType() == Material.DIAMOND_HOE) {
                    if (!attacker.getItemInHand().getEnchantments().containsKey(Enchantment.DURABILITY)) {
                        attacker.getItemInHand().setDurability((short) (attacker.getItemInHand().getDurability() + 1));
                    }

                    if (attacker.getItemInHand().getDurability() == attacker.getItemInHand().getType().getMaxDurability()) {
                        attacker.setItemInHand(null);
                        event.setCancelled(true);
                        return;
                    }

                    if (Jobs.getJobLevel("Fazendeiro", attacker) == 1) {
                        if (attacker.getItemInHand().getType().name().contains("HOE")) {
                            event.setDamage((event.getDamage() * 1.2));
                            if (PlayerSpec.temSpec(attacker, PlayerSpec.Ceifador)) {
                                event.setDamage(event.getDamage() * 2);
                            }
                        } else {
                            event.setDamage((event.getDamage() * 0.8));
                        }

                    }
                    if (Jobs.getJobLevel("Alquimista", attacker) == 1) {

                        /*
                         if (attacker.getItemInHand().getType() == Material.GOLD_HOE) {
                         if (PlayerSpec.temSpec(attacker, PlayerSpec.Cientista)) {
                         defender.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20 * 20, 1));
                         } else {
                         defender.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20 * 20, 1));
                         }
                         if (PlayerSpec.temSpec(attacker, PlayerSpec.Envenenador)) {
                         defender.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 20 * 20, 2));
                         }
                         defender.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 20, 0));
                         } else {
                         if (PlayerSpec.temSpec(attacker, PlayerSpec.Cientista)) {
                         defender.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20 * 20, 0));
                         } else {
                         defender.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20 * 20, 0));
                         }
                         if (PlayerSpec.temSpec(attacker, PlayerSpec.Envenenador)) {
                         defender.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 20 * 20, 0));
                         }
                         defender.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 15, 0));
                         }
                         */
                    }

                }

                if (attacker.getItemInHand().getType() == Material.WOOD_PICKAXE || attacker.getItemInHand().getType() == Material.IRON_PICKAXE || attacker.getItemInHand().getType() == Material.STONE_PICKAXE || attacker.getItemInHand().getType() == Material.DIAMOND_PICKAXE) {
                    GeneralListener.miner.tentaDerrubarArma(event);
                }

                if (!event.isCancelled()) {
                    // se eu nao for inimigo, eu viro criminoso
                    ClanPlayer clanAtacante = ClanLand.manager.getClanPlayer(attacker);
                    if (!event.isCancelled() && (clanAtacante == null || !clanAtacante.isRival(defender))) {
                        if (!Criminoso.isCriminoso(defender)) {
                            Criminoso.setCriminoso(attacker);
                        }
                    }
                }
            }
            Thief.revela(attacker);
            //////////// Bonus da força  /////////

            if (attacker.getItemInHand() != null) {
                String ci = CustomItem.getCustomItem(attacker.getItemInHand());
                if (ci != null && ci.equalsIgnoreCase("Cajado Elemental")) {
                    // nada 
                } else {

                    // BONUS DE DANO DO NIVEL


                    KoM.debug("Dano passando com ratio " + ratioNivel + " ficando " + event.getDamage());

                    // bonus de força no dano
                    //event.setDamage(Attributes.calcDamage(event.getDamage(), infoBateu.attributes.get(Attr.strength)));
                    /////////// bonus da dex /////////////
                    //event.setDamage(event.getDamage() + Attributes.calcBonusPhysicalDamage(event.getDamage(), infoBateu.attributes.get(Attr.dexterity)));
                }
            }

            if (attacker.getItemInHand() != null && (attacker.getItemInHand().getType() == Material.IRON_DOOR || attacker.getItemInHand().getType() == Material.EGG)) {
                if (attacker.getItemInHand().getType() == Material.IRON_DOOR && Jobs.getJobLevel("Paladino", attacker) == 1) {
                    int stam = 65;
                    if (PlayerSpec.temSpec(attacker, PlayerSpec.Guardiao)) {
                        stam = 30;
                    }
                    if (Stamina.spendStamina(attacker, stam)) {
                        ((LivingEntity) event.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 3, 2));
                        ((LivingEntity) event.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * 3, 2));
                        //PlayEffect.play(VisualEffect.CRIT_MAGIC, event.getEntity().getLocation(), "num:10");
                        event.getEntity().getWorld().playEffect(event.getEntity().getLocation(), Effect.CRIT, 1);
                        attacker.sendMessage(ChatColor.GREEN + "Voce deu uma escudada no alvo !");
                    }
                }
                event.setDamage(0l);
                event.setCancelled(true);
                return;
            }
        }

        KoM.debug("Dano no final do playerBate " + event.getDamage());
    }

    /*
     public static boolean verificaExpBlocoQuebrado(final BlockBreakEvent event) {
     if (event.isCancelled()) {
     return false;
     }
        
     int exp = Minerador.pegaExpDe(event.getBlock().getType());
     if (exp != 0) {
     if (exp > 2 && !Stamina.spendStamina(event.getPlayer(), 5)) {
     event.getPlayer().sendMessage(ChatColor.RED + L.m("Voce esta sem stamina !"));
     event.setCancelled(true);
     return true;
     }
     Minerador.minera(event.getPlayer(), event.getBlock(), exp);
     return true;
     }

     if (event.getBlock().getType() == Material.LEAVES) {
     Farmer.cortaFolha(event.getPlayer(), event.getBlock());
     return true;
     }

     exp = Farmer.pegaExpDe(event.getBlock().getType(), event.getBlock());
     if (exp != 0) {
     Farmer.colhe(event.getPlayer(), event.getBlock(), exp);
     }

     exp = Lumberjack.pegaExpDe(event.getBlock().getType(),event.getPlayer());
     if (exp != 0) {
     Lumberjack.cortaLenha(event.getPlayer(), event.getBlock(), exp);
     return true;
     }
     return false;
     }
     */

    /*
     public static void verificaBlocoColoado(final BlockPlaceEvent ev) {

     if (ev.getBlock().getType() == Material.SPONGE || ev.getBlock().getType() == Material.DIAMOND_BLOCK) {
     ev.setCancelled(true);
     }
     int dificuldade = Farmer.pegaDificuldadeDe(ev.getBlock().getType());
     int exp = Farmer.pegaExpDe(ev.getBlock().getType(), ev.getBlock());
     if (dificuldade != 0) {
     Farmer.planta(ev.getPlayer(), ev.getBlock(), exp);
     }

     exp = Engineer.getExpFrom(ev.getBlock().getType());
     dificuldade = Engineer.getDifficultyFrom(ev.getBlock().getType());

     if (dificuldade != 0) {
     Engineer.colocaBloco(ev);
     }
     }
     */
    /*
     public static boolean verificaItemCraftado(final InventoryClickEvent event) {

     if (event.getWhoClicked().getInventory().firstEmpty() == -1 || event.getAction() == InventoryAction.HOTBAR_SWAP || Thief.taInvisivel((Player) event.getWhoClicked())) {
     ((Player) event.getWhoClicked()).sendMessage(ChatColor.RED + L.m("Voce nao pode fazer isto !"));
     event.setCancelled(true);
     return false;
     }

     int exp = Blacksmith.pegaExpDe(event.getCurrentItem().getType());
     // eh um item de forjar que eh alterado pela exp
     if (exp != 0) {
     Blacksmith.craftItem(event, exp);
     return true;
     } else if (Alchemist.getDifficulty(event.getCurrentItem().getType()) != 0) {
     Alchemist.craftPotion(event, 10);
     return true;
     }

     exp = Minerador.pegaExpDe(event.getCurrentItem().getType());
     // eh um item de forjar que eh alterado pela exp
     if (exp != 0) {
     Minerador.craftaItem(event, exp);
     return true;
     } else if (Alchemist.getDifficulty(event.getCurrentItem().getType()) != 0) {
     Alchemist.craftPotion(event, 0);
     return true;
     }

     exp = Farmer.pegaExpDe(event.getCurrentItem().getType(), null);
     if (exp != 0) {
     Farmer.craftaItem(event, exp);
     return true;
     }

     exp = Engineer.getExpFrom(event.getCurrentItem().getType());
     if (exp != 0) {
     Engineer.craftaItem(event, exp);
     return true;
     }

     exp = Lumberjack.pegaExpDe(event.getCurrentItem().getType(), (Player)event.getWhoClicked());
     if (exp != 0) {
     Lumberjack.transformaTabuas(event, exp);
     return true;
     } else {
     String customItem = CustomItem.getCustomItem(event.getCurrentItem());
     if (customItem != null) {
     if (customItem.equalsIgnoreCase(L.m("Lockpick"))) {
     Engineer.fazCustomItem(event, 60);
     } else if (customItem.equalsIgnoreCase(L.m("Logout Trap"))) {
     Engineer.fazCustomItem(event, 55);
     } else if (customItem.equalsIgnoreCase(L.m("Pe de Cabra"))) {
     Engineer.fazCustomItem(event, 80);
     } else if (customItem.equalsIgnoreCase(L.m("Bomba de Farinha"))) {
     Alchemist.craftCustomItem(event, 80);
     } else if (customItem.equalsIgnoreCase(L.m("Capacete da Visao"))) {
     Engineer.fazCustomItem(event, 80);
     } else if (customItem.equalsIgnoreCase(L.m("Para Raio"))) {
     Engineer.fazCustomItem(event, 80);
     } else if (customItem.equalsIgnoreCase(L.m("Projeto de Torre"))) {
     Engineer.fazCustomItem(event, 80);
     }

     }
     }
     return false;
     }
     */
}
