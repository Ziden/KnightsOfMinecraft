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
package nativelevel.Classes.Alchemy;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import me.fromgate.playeffect.PlayEffect;
import me.fromgate.playeffect.VisualEffect;
import nativelevel.CFG;
import nativelevel.sisteminhas.ClanLand;
import nativelevel.Custom.CustomItem;
import nativelevel.Jobs;
import nativelevel.KoM;
import nativelevel.Lang.L;
import nativelevel.Lang.PT;
import nativelevel.Listeners.GeneralListener;
import nativelevel.Menu.Menu;
import nativelevel.MetaShit;
import nativelevel.bencoes.TipoBless;
import nativelevel.config.ConfigKom;
import nativelevel.config.ItemJob;
import nativelevel.rankings.Estatistica;
import nativelevel.rankings.RankDB;
import nativelevel.spec.PlayerSpec;
import nativelevel.sisteminhas.XP;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.util.Vector;

public class Alchemist {

    public static boolean isReagent(int id) {
        if (id < 370 || id > 382) { // no é um reagente
            return false;
        }
        return true;
    }

    /*
    public static int getDifficulty(Material m) {
        if (m == Material.ENCHANTMENT_TABLE) {
            return 85;
        } else if (m == Material.TNT) {
            return 65;
        } else if (m == Material.EYE_OF_ENDER) {
            return 85;
        } else if (m == Material.BEACON) {
            return 95;
        }
        return 0;
    }
    */
    
    public static void tossTnt(PlayerInteractEvent ev) {
        if (CFG.dungeons.contains(ev.getPlayer().getWorld().getName())) {
            return;
        }
        String customItem = CustomItem.getCustomItem(ev.getPlayer().getItemInHand());
        if (customItem != null) {
            return;
        }
        int level = Jobs.getJobLevel(L.get("Classes.Alchemist"), ev.getPlayer());
        if (level == 0 || ev.getPlayer().getLevel() < 20) {
            ev.getPlayer().sendMessage(ChatColor.AQUA + Menu.getSimbolo(L.get("Classes.Alchemist")) + " " + ChatColor.RED + L.get("Classes.AlchemistClass.ThrowTnt"));
            return;
        }
        if (ev.getPlayer().getItemInHand().getType() == Material.TNT) {
            if (ev.getPlayer().getWorld().getName().equalsIgnoreCase("WoE")) {
                ev.setCancelled(true);
                return;
            }
            //  EntityTNTPrimed tnt  = (EntityTNTPrimed)ev.getPlayer().getWorld().spawnCreature(ev.getPlayer().getLocation(), EntityType.PRIMED_TNT);
            // tnt.//tnt.setVelocity(ev.getPlayer().getLocation().getDirection().normalize().multiply(2));
            TNTPrimed tnt = (TNTPrimed) ev.getPlayer().getWorld().spawn(ev.getPlayer().getLocation(), TNTPrimed.class);
            tnt.getLocation().add(0.5D, 0.5D, 0.5D);
            KoM.removeInventoryItems(ev.getPlayer().getInventory(), Material.TNT, 1);
            // LivingEntity bomba = ev.getPlayer().getWorld().spawnCreature(ev.getPlayer().getLocation(), EntityType.PRIMED_TNT);
            //Vector alvo = ev.getPlayer().getEyeLocation().toVector().add(ev.getPlayer().getLocation().getDirection().multiply(2));
            if (ev.getPlayer().isSprinting()) {
                tnt.setVelocity(ev.getPlayer().getLocation().getDirection().normalize().multiply(1.2));
            } else {
                tnt.setVelocity(ev.getPlayer().getLocation().getDirection().normalize().divide(new Vector(1.2, 1.2, 1.2)));
            }
        }
    }

    /*
    public static boolean craftPotion(final InventoryClickEvent event, int exp) {
        Player p = ((Player) event.getWhoClicked());
        int dificuldade = Alchemist.getDifficulty(event.getCurrentItem().getType());

        TipoBless ativo = TipoBless.save.getTipo((Player) event.getWhoClicked());
        if (ativo != null && ativo == TipoBless.Alquimia) {
            dificuldade -= 100;
            if (dificuldade < 0) {
                dificuldade = 0;
            }
            exp = exp * 3;
        }

        int sucesso = Jobs.hasSuccess(dificuldade, L.get("Classes.Alchemist"), p);
        if (sucesso == Jobs.success) {
            p.sendMessage(ChatColor.GOLD + PT.craft_sucess);
            ItemMeta meta = event.getCurrentItem().getItemMeta();
            List<String> lore = meta.getLore();
            if (lore == null) {
                lore = new ArrayList<String>();
            }
            meta.setLore(lore);
            event.getCurrentItem().setItemMeta(meta);
            RankDB.addPontoCache(p, Estatistica.ALQUIMISTA, exp / 10);
        } else {
            exp = 1;
            event.setCurrentItem(new ItemStack(Material.SULPHUR, 1));
            p.sendMessage(ChatColor.RED + PT.craft_fail);
        }
        GeneralListener.givePlayerExperience(exp, p);
        return true;
    }
    */

    /*
    public static int getExp(Potion p) {
        int diff = getDifficulty(p);
        int exp = XP.getExpPorAcao(diff);
        return exp;
        /*
        int xp = 0;
        
        if(xp==0) {
            if (p.getType() == PotionType.FIRE_RESISTANCE) {
                xp += 33;
            } else if (p.getType() == PotionType.INSTANT_DAMAGE) {
                xp += 38;
            } else if (p.getType() == PotionType.INSTANT_HEAL) {
                xp += 38;
            } else if (p.getType() == PotionType.SPEED) {
                xp += 26;
            } else if (p.getType() == PotionType.WATER_BREATHING) {
                xp += 0;
            } else if (p.getType() == PotionType.INVISIBILITY) {
                xp += 68;
            } else if (p.getType() == PotionType.SLOWNESS) {
                xp += 38;
            } else if (p.getType() == PotionType.POISON) {
                xp += 38;
            }
        }
       
        if (p.isSplash()) {
            xp += 10;
        }
        if (p.hasExtendedDuration()) {
            xp += 10;
        }
        if (p.getLevel() > 1) {
            xp += 10;
        }
        return xp;
       }
        */
 
  

    /*
    public static int getDifficulty(Potion p) {
        int dificuldade = 0;
        
        if(dificuldade == 0) {
            if (p.getType() == PotionType.FIRE_RESISTANCE) {
                dificuldade += 80;
            } else if (p.getType() == PotionType.INSTANT_DAMAGE) {
                dificuldade += 65;
            } else if (p.getType() == PotionType.SPEED) {
                dificuldade += 89;
            } else if (p.getType() == PotionType.INSTANT_HEAL) {
                dificuldade += 50;
            } else if (p.getType() == PotionType.WATER_BREATHING) {
                dificuldade += 200;
            } else if (p.getType() == PotionType.INVISIBILITY) {
                dificuldade += 80;
            } else if (p.getType() == PotionType.SLOWNESS) {
                dificuldade += 80;
            } else if (p.getType() == PotionType.POISON) {
                dificuldade += 30;
            } else if (p.getType() == PotionType.STRENGTH) {
                dificuldade += 80;
            } else if (p.getType() == PotionType.REGEN) {
                dificuldade += 82;
            } else {
                dificuldade += 70;
            }
        }
        

        if (p.isSplash()) {
            dificuldade += 10;
        }
        if (p.hasExtendedDuration()) {
            dificuldade += 10;
        }
        if (p.getLevel() > 1) {
            dificuldade += 10;
        }
        if (KnightsOfMania.debugMode) {
            KnightsOfMania.log.info("nivel da pocao: " + p.getLevel());
        }
        return dificuldade;
    }
    */

 
    /*
    public static boolean createPotionAttempt(InventoryClickEvent ev) {
        if (ev.getSlot() != 0 && ev.getSlot() != 1 && ev.getSlot() != 2) {
            return true;
        }
        int xp = 0;
        Player p = ((Player) ev.getWhoClicked());
        ItemMeta m = ev.getCurrentItem().getItemMeta();
        // reagent slot
        if (ev.getSlot() == 3) {
            if (ev.getInventory().getItem(0).getType() != Material.AIR
                    || ev.getInventory().getItem(1).getType() != Material.AIR
                    || ev.getInventory().getItem(2).getType() != Material.AIR) {
                p.sendMessage(ChatColor.AQUA + Menu.getSimbolo(L.get("Classes.Alchemist")) + " " + ChatColor.GOLD + L.m("Voce apenas pode mover o reagente depois de mover a pocao !"));
                ev.setCancelled(true);
                return false;
            }
            // potion slot
        } else {

            if (ev.getCursor() != null && ev.getCursor().getType() == Material.POTION) {
                ItemMeta meta = ev.getCursor().getItemMeta();
                if (meta.getLore() != null && meta.getLore().size() > 0 && meta.getLore().get(meta.getLore().size() - 1).contains(ChatColor.YELLOW + L.m("Criada por"))) {
                    ev.setCancelled(true);
                }
            }

            if (ev.getCurrentItem() == null || ev.getCurrentItem().getType() == Material.AIR || ev.getCurrentItem().getType() == Material.GLASS_BOTTLE
                    || (ev.getCurrentItem().getType() == Material.POTION && ev.getCurrentItem().getDurability() == 0)) {

                return true;
            } else {

            }

            Potion pot = Potion.fromItemStack(ev.getCurrentItem());
            if (pot == null) {
                return true;
            }
            int potionLevel = pot.getLevel();
            if (p.isOp()) {
                p.sendMessage("pot lvl : " + potionLevel);
            }

            xp = getExp(pot);
            int job = Jobs.getJobLevel("Alquimista", p);
            if(job==0)
                xp = xp / 5;
            else if(job==1) {
                xp = xp / 2;
            }
            
            int dificuldade = getDifficulty(pot);

            TipoBless ativo = TipoBless.save.getTipo(p);
            if (ativo != null && ativo == TipoBless.Alquimia) {
                dificuldade -= 20;
                if (dificuldade < 0) {
                    dificuldade = 0;
                }
                xp = xp * 3;
            }

            if (PlayerSpec.temSpec(p, PlayerSpec.Cientista)) {
                dificuldade -= 10;
            }
            int sucesso = Jobs.hasSuccess(dificuldade, L.get("Classes.Alchemist"), p);
            if (sucesso == Jobs.fail || sucesso == Jobs.badQuality) {
                if (Jobs.rnd.nextInt(100) == 1) {
                    p.sendMessage(ChatColor.RED + L.m("Voce acabou explodindo a poção sem querer !"));
                    PlayEffect.play(VisualEffect.EXPLOSION_HUGE, p.getLocation(), "num:1");
                    p.damage(15D);
                }

                p.sendMessage(ChatColor.GOLD + PT.craft_fail);
                ev.getInventory().setItem(ev.getSlot(), new ItemStack(374, 1));
                return true;
            }
        }

        PlayEffect.play(VisualEffect.SOUND, p.getLocation(), "type:DRINK");
        p.sendMessage(ChatColor.GOLD + PT.craft_sucess);
        // if its a crafted potion,continue
        // if (m.getLore() != null && m.getLore().size() > 0) {
        //    if (m.getLore().get(m.getLore().size() - 1).contains(ChatColor.YELLOW + L.m("Criada por"))) {
        //       xp = 0;
        //  }
        // }
        if (m != null) {
            m.setLore(Arrays.asList(new String[]{ChatColor.YELLOW + L.m("Criada por ") + ChatColor.GREEN + p.getName()}));
        }
        ev.getCurrentItem().setItemMeta(m);
        GeneralListener.givePlayerExperience(xp, p);
        return true;
    }
    */

    /*
    public static boolean caldeiraoDePocoes(PlayerInteractEvent ev) {
        if (ev.getAction() == Action.RIGHT_CLICK_BLOCK && !ev.getPlayer().isSneaking()) {
            if (ev.getClickedBlock().getType() == Material.CAULDRON) {
                if (Jobs.getJobLevel("Alquimista", ev.getPlayer()) != 1 || ev.getPlayer().getLevel() < 30) {
                    ev.getPlayer().sendMessage(ChatColor.RED + L.m("Apenas alquimistas experientes sabem usar o caldeirao..."));
                    ev.setCancelled(true);
                    return true;
                }
                int level = ev.getClickedBlock().getData();
                ItemStack namao = ev.getPlayer().getItemInHand();

                if (namao.getType() == Material.POTION) {
                    if (namao.getDurability() == 0 && namao.getData().getData() == 0) {
                        ev.getPlayer().sendMessage(ChatColor.RED + L.m("Apenas agua nao fará uma poção..."));
                        ev.setCancelled(true);
                        return true;
                    }
                }

                Block caldeirao = ev.getClickedBlock();
                List<PotionEffect> efeitosCaldeirao;
                if (level >= 3 && namao != null && namao.getType() == Material.GLASS_BOTTLE) {
                    caldeirao.setData((byte) 0);
                    int sucesso = Jobs.hasSuccess(67, "Alquimista", ev.getPlayer());
                    if (sucesso == Jobs.fail || sucesso == Jobs.badQuality) {
                        ev.getPlayer().sendMessage(ChatColor.RED + L.m("Sua mistura parece que nao ficou boa..."));
                        ev.getPlayer().setItemInHand(new ItemStack(Material.POTION, 1));
                        caldeirao.removeMetadata("efeitosCald", KnightsOfMania._instance);
                        GeneralListener.givePlayerExperience(10, ev.getPlayer());
                        return true;
                    }
                    ev.getPlayer().sendMessage(ChatColor.GREEN + L.m("Voce esvaziou o caldeirao e enxeu uma mistura de pocao !"));
                    ItemStack pocaoCriada = new ItemStack(Material.POTION, 1);
                    PotionMeta meta = (PotionMeta) pocaoCriada.getItemMeta();
                    efeitosCaldeirao = (List<PotionEffect>) MetaShit.getMetaObject("efeitosCald", caldeirao);
                    ev.getPlayer().sendMessage(ChatColor.GREEN + L.m("Efeitos misturados: ") + efeitosCaldeirao.size());
                    List<String> lore = new ArrayList<String>();
                    for (PotionEffect e : efeitosCaldeirao) {
                        meta.addCustomEffect(e, true);
                        lore.add(ChatColor.YELLOW + e.getType().getName() + " " + e.getAmplifier() + " - " + e.getDuration());
                    }
                    lore.add(ChatColor.YELLOW + L.m("Misturado por ") + ChatColor.GREEN + ev.getPlayer().getName());
                    meta.setLore(lore);
                    meta.setDisplayName(ChatColor.GREEN + L.m("Mistura de Pocoes ") + Jobs.rnd.nextInt(900));
                    pocaoCriada.setItemMeta(meta);
                    ev.getPlayer().setItemInHand(pocaoCriada);
                    int xp = 200;
                    TipoBless ativo = TipoBless.save.getTipo(ev.getPlayer());
                    if (ativo != null && ativo == TipoBless.Alquimia) {
                        xp = 400;
                    }
                    GeneralListener.givePlayerExperience(xp, ev.getPlayer());
                    caldeirao.removeMetadata("efeitosCald", KnightsOfMania._instance);
                    return true;
                }
                if (namao == null || namao.getType() != Material.POTION) {
                    ev.getPlayer().sendMessage(ChatColor.RED + L.m("Voce apenas pode colocar pocoes ou ingredientes secretos no caldeirao ou retirar com um frasco"));
                    ev.setCancelled(true);
                    return true;
                }

                if (level >= 3) {
                    ev.getPlayer().sendMessage(ChatColor.RED + L.m("Sua pocao esta pronta, use um pote vazio para retirar ela !"));
                    ev.setCancelled(true);
                    return true;
                }
                PotionMeta pocao = (PotionMeta) namao.getItemMeta();
                Potion pot = Potion.fromItemStack(namao);
                if (pot == null) {
                    return true;
                }
                if (caldeirao.hasMetadata("efeitosCald")) {
                    efeitosCaldeirao = (List<PotionEffect>) MetaShit.getMetaObject("efeitosCald", caldeirao);
                    efeitosCaldeirao.addAll(pot.getEffects());
                    caldeirao.setData((byte) (level + 1));
                    MetaShit.setMetaObject("efeitosCald", caldeirao, efeitosCaldeirao);
                    int xp = 10;
                    TipoBless ativo = TipoBless.save.getTipo(ev.getPlayer());
                    if (ativo != null && ativo == TipoBless.Alquimia) {
                        xp = 30;
                    }
                    GeneralListener.givePlayerExperience(xp, ev.getPlayer());
                    ev.getPlayer().sendMessage(ChatColor.GREEN + L.m("Voce adicionou uma pitadinha de pocao no caldeirao.."));
                    ev.getPlayer().sendMessage(ChatColor.GREEN + L.m("Efeitos misturados: ") + efeitosCaldeirao.size());
                    ev.getPlayer().setItemInHand(null);
                    return true;
                } else {
                    // esvazia
                    caldeirao.setData((byte) 1);
                    efeitosCaldeirao = new ArrayList<PotionEffect>();
                    efeitosCaldeirao.addAll(pot.getEffects());
                    MetaShit.setMetaObject("efeitosCald", caldeirao, efeitosCaldeirao);
                    int xp = 10;
                    TipoBless ativo = TipoBless.save.getTipo(ev.getPlayer());
                    if (ativo != null && ativo == TipoBless.Alquimia) {
                        xp = 50;
                    }
                    GeneralListener.givePlayerExperience(xp, ev.getPlayer());
                    ev.getPlayer().sendMessage(ChatColor.GREEN + L.m("Voce comecou a enxer o caldeirao.."));
                    ev.getPlayer().sendMessage(ChatColor.GREEN + L.m("Efeitos misturados: ") + efeitosCaldeirao.size());
                    ev.getPlayer().setItemInHand(null);
                    return true;
                }
            }
        }
        return false;
    }
    */

    public static void splashPotion(PotionSplashEvent event) {
        /*
        if (event.getPotion().getShooter() != null && ((LivingEntity) event.getPotion().getShooter()).getType() != EntityType.PLAYER) {
            return;
        }
        Player p = ((Player) event.getPotion().getShooter());
        if (event.getPotion().getShooter() instanceof Player) {
            if (Thief.taInvisivel(p)) {
                Thief.revela(p);
            }
            int lvl = Jobs.getJobLevel("Alquimista", p);
            boolean jogaTeia = event.getEntity().hasMetadata("teia");
            for (PotionEffect ef : event.getPotion().getEffects()) {

                HashSet<LivingEntity> removidas = new HashSet<LivingEntity>();
                if (ef.getType() == PotionEffectType.HEAL || ef.getType() == PotionEffectType.REGENERATION || ef.getType() == PotionEffectType.SPEED || ef.getType() == PotionEffectType.FIRE_RESISTANCE || ef.getType() == PotionEffectType.SPEED) {
                    ClanPlayer cp = ClanLand.manager.getClanPlayer(p);
                    for (LivingEntity e : event.getAffectedEntities()) {
                        if (e instanceof Player) {
                            ClanPlayer cp2 = ClanLand.manager.getClanPlayer((Player) e);
                            if (cp == null || cp.isRival((Player) e) || (!cp.isAlly((Player) e) && !cp.getTag().equalsIgnoreCase(cp2.getTag()))) {
                                removidas.add(e);
                            } else {
                                // vai pegar efeito
                                if (lvl == 1) {
                                    if (ef.getType() == PotionEffectType.HEAL) {
                                        double v = e.getHealth();
                                        v += 8;
                                        if (v > e.getMaxHealth()) {
                                            v = e.getMaxHealth();
                                        }
                                        e.setHealth(v);
                                    }
                                }
                            }
                        }
                    }
                    event.getAffectedEntities().removeAll(removidas);
                }
                if (ef.getType() == PotionEffectType.HARM || ef.getType() == PotionEffectType.POISON || ef.getType() == PotionEffectType.SLOW || ef.getType() == PotionEffectType.WEAKNESS) {

                    ClanPlayer cp = ClanLand.manager.getClanPlayer(p);
                    for (LivingEntity e : event.getAffectedEntities()) {
                        if (e instanceof Player) {
                            ClanPlayer cp2 = ClanLand.manager.getClanPlayer((Player) e);
                            if (cp.isAlly((Player) e) || cp.getTag().equalsIgnoreCase(cp2.getTag())) {
                                removidas.add(e);
                            } else {
                                // vai pegar
                                // vai pegar efeito
                                if (lvl == 1) {
                                    if (ef.getType() == PotionEffectType.HARM) {
                                        e.damage(6, p);
                                    }
                                }
                            }
                        }
                    }
                    event.getAffectedEntities().removeAll(removidas);
                }
                break;
            }
            if (jogaTeia) {
                for (Entity e : event.getAffectedEntities()) {
                    GeneralListener.wizard.prendeEnt(e);
                }
            }
            GeneralListener.givePlayerExperience(3, p);
        }
                */
    }

}
