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

import java.awt.List;
import java.util.ArrayList;
import me.fromgate.playeffect.PlayEffect;
import me.fromgate.playeffect.VisualEffect;
import nativelevel.CustomEvents.BlockHarvestEvent;
import nativelevel.Listeners.GeneralListener;
import nativelevel.Jobs;
import nativelevel.Lang.PT;
import nativelevel.Menu.Menu;
import nativelevel.MetaShit;
import nativelevel.KoM;
import nativelevel.Lang.L;
import nativelevel.Attributes.AttributeInfo;
import nativelevel.Attributes.Mana;
import nativelevel.Attributes.Stamina;
import nativelevel.bencoes.TipoBless;
import nativelevel.config.ConfigKom;
import nativelevel.config.ItemJob;
import nativelevel.rankings.Estatistica;
import nativelevel.rankings.RankDB;
import nativelevel.spec.PlayerSpec;
import nativelevel.sisteminhas.XP;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class Lumberjack {

    public static final Material[] dropsBonus = {Material.SULPHUR, Material.GOLD_INGOT, Material.BROWN_MUSHROOM, Material.RED_MUSHROOM, Material.GOLDEN_APPLE, Material.GLOWSTONE_DUST};

    /* 
     public static int pegaDificuldadeDe(Material m) {

     if (m == Material.LOG) {
     return 25;
     } else if (m == Material.WOOD) {
     return 38;
     } else if (m == Material.LOG_2) {
     return 70;
     }
     return 0;
     }
     */

    /*
     public static int pegaExpDe(Material m, Player p) {
     int diff = Lumberjack.pegaDificuldadeDe(m);
     if (m == Material.LOG) {
     return XP.getExpPorAcao(p.getLevel());
     } else if (m == Material.WOOD) {
     return XP.getExpPorAcao(p.getLevel());
     } else if (m == Material.LOG_2) {
     return XP.getExpPorAcao(p.getLevel());
     }
     return 0;
     }
     */
    static final int[] monstros = {90, 91, 92, 93};

    public static void cortaFolha(Player p) {

    }

    public static void preparaMachadadaEpica(final Player p) {

        if (p.hasMetadata("epichax")) {
            return;
        }
        if (Jobs.getJobLevel("Lenhador", p) != 1) {
            return;
        }

        int custo = 35;
        int modTempo = 0;
        boolean cansa = false;
        if (PlayerSpec.temSpec(p, PlayerSpec.Barbaro)) {
            modTempo = -20;
            custo = 45;
        }
        final int finalMod = modTempo;

        if (!Stamina.spendStamina(p, custo)) {
            return;
        }
        
        ItemStack off = p.getInventory().getItemInOffHand();
        if(off != null && off.getType()!=Material.AIR) {
            p.sendMessage(ChatColor.RED+"Voce precisa das 2 mãos para conseguir usar a Machadada Épica");
            return;
        }

        if (Thief.taInvisivel(p)) {
            Thief.revela(p);
        }

        p.sendMessage(ChatColor.GREEN + L.m("Voce segurou firmemente o machado e o colocou de lado"));

        if (!p.hasPotionEffect(PotionEffectType.SPEED)) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 5 + finalMod, 0));
        }

        final Runnable r3 = new Runnable() {
            public void run() {
                if (p.hasMetadata("epichax")) {
                    p.sendMessage(ChatColor.RED + L.m("O machado ja se abaixou, voce passou do ponto..."));
                }
                p.removeMetadata("epichaxpronta", KoM._instance);
                p.removeMetadata("epichax", KoM._instance);
                if (p.hasPotionEffect(PotionEffectType.SPEED)) {
                    p.removePotionEffect(PotionEffectType.SPEED);
                }
            }
        };

        final Runnable r2 = new Runnable() {
            public void run() {
                if (p.getLocation().getBlock().getType() == Material.WEB) {
                    if (p.hasPotionEffect(PotionEffectType.SPEED)) {
                        p.removePotionEffect(PotionEffectType.SPEED);
                    }
                    p.sendMessage(ChatColor.RED + "Voce se enrosca nas teias e nao consegue preparar a machadada");
                    p.removeMetadata("epichaxpronta", KoM._instance);
                    p.removeMetadata("epichax", KoM._instance);
                    return;
                }
                p.sendMessage(ChatColor.GREEN + "Voce coloca força no machado e joga seu corpo de lado ! A machada epica esta pronta !");
                int id = Bukkit.getScheduler().scheduleSyncDelayedTask(KoM._instance, r3, 35 + finalMod);
                MetaShit.setMetaObject("epichax", p, id);
                MetaShit.setMetaObject("epichaxpronta", p, 0);
            }
        };

        final Runnable r = new Runnable() {
            public void run() {
                if (p.getLocation().getBlock().getType() == Material.WEB) {
                    if (p.hasPotionEffect(PotionEffectType.SPEED)) {
                        p.removePotionEffect(PotionEffectType.SPEED);
                    }
                    p.sendMessage(ChatColor.RED + "Voce se enrosca nas teias e nao consegue preparar a machadada");
                    p.removeMetadata("epichaxpronta", KoM._instance);
                    p.removeMetadata("epichax", KoM._instance);
                    return;
                }
                p.sendMessage(ChatColor.GREEN + "Voce levanta o machado e se prepara para a machadada epica !");
                for (Entity e : p.getNearbyEntities(8, 2, 8)) {
                    if (e.getType() == EntityType.PLAYER) {
                        ((Player) e).sendMessage(ChatColor.GOLD + p.getName() + " esta levantando o machado lentamente !");
                    }
                }
                int id = Bukkit.getScheduler().scheduleSyncDelayedTask(KoM._instance, r2, 40 + finalMod);
                MetaShit.setMetaObject("epichax", p, id);
            }
        };
        if (p.getLocation().getBlock().getType() == Material.WEB) {
            if (p.hasPotionEffect(PotionEffectType.SPEED)) {
                p.removePotionEffect(PotionEffectType.SPEED);
            }
            p.sendMessage(ChatColor.RED + "Voce se enrosca nas teias e nao consegue preparar a machadada");
            p.removeMetadata("epichaxpronta", KoM._instance);
            p.removeMetadata("epichax", KoM._instance);
            return;
        }
        int sched = Bukkit.getScheduler().scheduleSyncDelayedTask(KoM._instance, r, 40 + finalMod);
        MetaShit.setMetaObject("epichax", p, sched);
    }

    public static void salta(PlayerInteractEvent ev) {
        if (ev.getPlayer().getLocation().getBlock().getType() == Material.STATIONARY_WATER || ev.getPlayer().isSneaking() || ev.getPlayer().getWorld().getName().equalsIgnoreCase("dungeon")) {
            return;
        }

        if (ev.getPlayer().getItemInHand().getType() == Material.IRON_AXE || ev.getPlayer().getItemInHand().getType() == Material.GOLD_AXE || ev.getPlayer().getItemInHand().getType() == Material.DIAMOND_AXE || ev.getPlayer().getItemInHand().getType() == Material.WOOD_AXE || ev.getPlayer().getItemInHand().getType() == Material.STONE_AXE) {

            if (!Stamina.spendStamina(ev.getPlayer(), 25)) {
                return;
            }

            PlayEffect.play(VisualEffect.SMOKE_LARGE, ev.getPlayer().getLocation(), "num:1");
            Vector jumpDir = ev.getPlayer().getLocation().getDirection().normalize().multiply(3);
            jumpDir.divide(new Vector(1, 5, 1));
            jumpDir.setY(0.6D);

            ev.getPlayer().setVelocity(jumpDir);
        }
    }
    
    public static void corta(BlockHarvestEvent ev) {
        ev.getPlayer().playSound(ev.getBlock().getLocation(), Sound.ENTITY_IRONGOLEM_ATTACK, 1, 1);
        
        TipoBless ativo = TipoBless.save.getTipo(ev.getPlayer());
        if (ativo != null && ativo == TipoBless.Serralheria) {
            int exp = XP.getExpPorAcao(ev.getHarvestable().difficulty);
            exp = exp * 10;
            XP.changeExp(ev.getPlayer(), exp, 1);
        }

        if (Jobs.rnd.nextInt(10000) == 1) {
            ev.getPlayer().sendMessage(ChatColor.GREEN + "Ao dar a cortada final, farpas da madeira saltaram e pegaram no seu olho");
            ev.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 60, 2));
        }
    }
    

    public static boolean cortaLenha(Player p, Block b, int exp) {

        if (b.hasMetadata("playerpois")) {
            exp = 0;
        }
        //int dificuldade = Lumberjack.pegaDificuldadeDe(b.getType());

      
        
        /*
         if (sucess == Jobs.fail) {
         b.setType(Material.AIR);
         p.sendMessage(ChatColor.AQUA + Menu.getSimbolo("Lenhador") + " " + ChatColor.RED + "Voce nao conseguiu cortar a madeira corretamente.");
         return false;
         } else {
         RankDB.addPontoCache(p, Estatistica.LENHADOR, exp);
         if (!b.hasMetadata("playerpois") && sucess == Jobs.BONUS && Jobs.rnd.nextInt(10) == 1 || (ativo != null && ativo == TipoBless.Serralheria)) {
         ItemStack drop = new ItemStack(dropsBonus[Jobs.rnd.nextInt(dropsBonus.length)], 1);
         p.getWorld().dropItem(b.getLocation(), drop);
         ItemStack madeira = new ItemStack(Material.WOOD, 1);
         //madeira.setData();
         p.getWorld().dropItem(b.getLocation(), madeira);
         p.sendMessage(ChatColor.AQUA + Menu.getSimbolo("Lenhador") + " " + ChatColor.GOLD + "Voce extraiu alguns recursos extras da arvore !");
         }
         }
         if (sucess != Jobs.OVERLEVEL && sucess != Jobs.BONUS) {
         GeneralListener.givePlayerExperience(exp + (p.getLevel() / 15), p);
         }
         return true;
         */
        
        return true;
    }

    /*
    public static void fazCustomItem(InventoryClickEvent event, ItemStack queFiz) {
        Player p = ((Player) event.getWhoClicked());
        if (event.getCursor() != null && event.getCursor().getAmount() > 63) {
            return;
        }
        int dificuldade = 0;
        //if(queFiz instanceof Tabua)
        //    dificuldade = 55;
        //if(queFiz instanceof DeedTorre)
        //    dificuldade = 75;
        int suc = Jobs.hasSuccess(dificuldade, "Lenhador", p);
        if (suc == Jobs.fail) {
            p.sendMessage(ChatColor.RED + PT.craft_fail);
            event.setCurrentItem(new ItemStack(Material.STICK, 1));
            return;
        }
        p.sendMessage(ChatColor.GOLD + PT.craft_sucess);
    }
    */

    /*
    public static void transformaTabuas(InventoryClickEvent event, double exp) {
        Player p = ((Player) event.getWhoClicked());
        if (event.getCursor() != null && event.getCursor().getAmount() > 63) {
            return;
        }
        int dificuldade = 2;

        TipoBless ativo = TipoBless.save.getTipo(p);
        if (ativo != null && ativo == TipoBless.Serralheria) {
            exp = exp * 5;
            dificuldade = 0;
        }

        int qtd = 1;
        int sucesses = 0;
        int jobLvl = Jobs.getJobLevel("Lenhador", p);
        if (jobLvl == 1) {
            qtd = 4;
        } else if (jobLvl == 2) {
            qtd = 3;
            exp = exp * 0.75;
        } else {
            exp = exp * 0.25;
        }

        for (int x = 0; x < qtd; x++) {
            int suc = Jobs.hasSuccess(dificuldade, "Lenhador", p);
            if (suc != Jobs.fail) {
                sucesses += 1;
            }
        }

        GeneralListener.givePlayerExperience(exp, p);
        if (sucesses == 0) {
            p.sendMessage(ChatColor.AQUA + Menu.getSimbolo("Lenhador") + " " + ChatColor.RED + "Voce falhou ao cortar as madeiras !");
            event.setCurrentItem(new ItemStack(Material.STICK, 1));
            return;
        }

        String plural = sucesses > 1 ? "s" : "";

        if (ativo != null && ativo == TipoBless.Serralheria) {
            sucesses += 1;
        }

        event.getCurrentItem().setAmount(sucesses);
        p.sendMessage(ChatColor.AQUA + Menu.getSimbolo("Lenhador") + " " + ChatColor.GREEN + "Voce conseguiu cortar " + ChatColor.GOLD + sucesses + ChatColor.GREEN + " madeira" + plural + " corretamente !");
    }
    */

    public static void tentaMachadadaEpica(Player tomou, Player bateu, EntityDamageEvent ev) {
        int multiplicador = 8;
        if (Thief.taInvisivel(bateu)) {
            multiplicador = 4;
        }
        if (ev.getDamage() == 0) {
            return;
        }
        ev.setDamage(ev.getDamage() * multiplicador);
        if (ev.getEntity() instanceof Player) {
            PlayEffect.play(VisualEffect.FIREWORKS_SPARK, ev.getEntity().getLocation(), "type:ball color:yellow");
            tomou.sendMessage(ChatColor.RED + bateu.getName() + " te acertou uma machadada epica !");
            if (bateu.hasPotionEffect(PotionEffectType.SPEED)) {
                bateu.removePotionEffect(PotionEffectType.SPEED);
            }
            /*
             AttributeInfo info = KnightsOfMania.database.getAtributos(bateu);
             int agi = info.attributes.get(Attributes.Attr.agility);
             if (agi >= 130) {
             bateu.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 9999, 2));
             } else if (agi >= 100) {
             bateu.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 9999, 1));
             } else if (agi >= 70) {
             bateu.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 9999, 0));
             }
             */
            bateu.sendMessage(ChatColor.AQUA + Menu.getSimbolo("Lenhador") + " " + ChatColor.GOLD + "Voce deu uma machadada epica !");
            if (PlayerSpec.temSpec(bateu, PlayerSpec.Carrasco)) {
                double vida = bateu.getHealth() + bateu.getMaxHealth() / 2;
                if (vida > bateu.getMaxHealth()) {
                    vida = bateu.getMaxHealth();
                }
                bateu.setHealth(vida);
            }
            //if (!bateu.hasPotionEffect(PotionEffectType.POISON)) {
            //    bateu.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 20, 1));
            //}
            if (PlayerSpec.temSpec(bateu, PlayerSpec.Barbaro)) {
                bateu.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 20 * 6, 1));
            }
        }
    }

    public static void tentaMachadadaEpicaEmMob(Player bateu, EntityDamageEvent ev) {
        if (ev.getDamage() == 0) {
            return;
        }
        int bonus = 10 + Jobs.rnd.nextInt(20);
        if (bateu.getItemInHand() != null && bateu.getItemInHand().getType() == Material.GOLD_AXE) {
            bonus += 10;
        }
        ev.setDamage(ev.getDamage() + bonus);
        PlayEffect.play(VisualEffect.FIREWORKS_SPARK, ev.getEntity().getLocation(), "type:ball color:yellow");
        bateu.sendMessage(ChatColor.AQUA + Menu.getSimbolo("Lenhador") + " " + ChatColor.GOLD + "Voce deu uma machadada epica !");
    }
}
