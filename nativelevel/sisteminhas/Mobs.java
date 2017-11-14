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
package nativelevel.sisteminhas;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import io.lumine.xikage.mythicmobs.mobs.ActiveMob;
import nativelevel.Listeners.GeneralListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import me.fromgate.playeffect.PlayEffect;
import me.fromgate.playeffect.VisualEffect;
import nativelevel.Classes.Lumberjack;
import nativelevel.Custom.CustomItem;
import nativelevel.Custom.Items.CapaInvisvel;
import nativelevel.Custom.Items.DoubleXP;
import nativelevel.Custom.Items.DropMob;
import nativelevel.Custom.Items.FolhaDeMana;
import nativelevel.Custom.Items.ItemIncomum;
import nativelevel.Dano;
import nativelevel.Jobs;
import nativelevel.KoM;
import nativelevel.Lang.L;
import nativelevel.MetaShit;
import nativelevel.MetaShit;
import nativelevel.Attributes.AttributeInfo;
import nativelevel.Jobs.TipoClasse;
import nativelevel.bencoes.TipoBless;
import nativelevel.gemas.Raridade;
import nativelevel.integration.WorldGuardKom;
import nativelevel.karma.Karma;
import nativelevel.sisteminhas.ClanLand;
import nativelevel.sisteminhas.Deuses;
import nativelevel.sisteminhas.KomSystem;
import nativelevel.sisteminhas.XP;
import nativelevel.rankings.Estatistica;
import nativelevel.rankings.RankDB;
import nativelevel.utils.LocUtils;
import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Horse.Variant;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Llama;
import org.bukkit.entity.Monster;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Tameable;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class Mobs extends KomSystem {

    // efeitos genéricos q kker um pode ter
    public enum EfeitoMobs {

        defesaExtra("Resistente", "▧", "Torna criaturas menos propensas a tomar grandes quantidades de dano"), // ok

        danoExtra("Assassino", "☣", "Tem garras, espinhos ou afins que causem maior dano"), // ok

        fantasma("Fantasma", "☠", "Não recebe nenhum tipo de dano físico"), // ta no Damage da Events

        bateFogo("Flamejante", "ﺴ", "Tem temperatura alta suficiente para fazer inimigos pegarem fogo."), // ok

        cega("Ofuscante", "❂", "Tem esporos ou pós que causam cegueira a pessoas."), // ok

        envenena("Cancerigeno", "❧", "Causa anormalias em nossas peles com algum tipo de veneno"), // ok

        slowa("Congelante", "๑", "Faz com que os músculos se contraiam e se movam mais devagar ao contato."), // ok

        velocidade("Ligeiro", "✰", "Tem músculos avançados e podem se movimentar rapidamente."), // ok

        empurra("Forte", "♣", " Tem força suprema e seus ataques podem te jogar para traz."), // ok

        borrachudo("Borrachudo", "☽", "Causa uma energia reversa ao ser atacado, empurrando o atacante."), // okk

        enfraquecedor("Enfraquecedor", "▼", "Faz com que inimigos se sintam mais fracos por conta de seu odor"), // ok

        fome("Pestilento", "☢", "Sua toxina faz com que pessoas sintam mais fome."), // ok

        regen("Imortal", "☥", "Evolução que faz com que os tecidos do monstro regenerem com facilidade."), // ok

        espinhudo("Espinhudo", "☤", "Ao atacar fisicamente, o monstro contra-ataca."),
        antiProjetil("Rochoso", "↙", "Possui tecido firme e sólido, afins que projeteis não conseguem causar dano"); // ta no Events

        public String desc;
        public String chara;
        public String oqFaz;

        private EfeitoMobs(String desc, String chara, String oqFaz) {
            this.desc = desc;
            this.chara = chara;
            this.oqFaz = oqFaz;
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void alvo(EntityTargetEvent ev) {
        Mobs.target(ev);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void spawn(final CreatureSpawnEvent ev) {

        // MULA DUPA ITEM
        if(ev.getEntity().getType()==EntityType.MULE) {
            ev.setCancelled(true);
            return;
        }
        
        if(ev.getEntity().getType()==EntityType.LLAMA) {
            ev.setCancelled(true);
            return;
        }
        
        if(ev.getEntity().getType()==EntityType.HORSE) {
            Horse h = (Horse)ev.getEntity();
            if(h.getVariant()==Variant.MULE || h.getVariant()==Variant.DONKEY) {
                ev.setCancelled(true);
                return;
            }
        }
        
        if (ev.getSpawnReason() == SpawnReason.NATURAL) {
            int numeroPerto = ev.getEntity().getNearbyEntities(25, 4, 25).size();
            if (numeroPerto > 4) {
                KoM.debug("cancel 8");
                ev.setCancelled(true);
            }
        }

        //KoM.debug("TENTANDO SPAWNAR MOB " + ev.getEntity().getType().name() + " REASON " + ev.getSpawnReason().name());

        if (ev.getEntityType() == EntityType.SQUID) {
            if (!ev.getLocation().getWorld().getName().equalsIgnoreCase("dungeon")) {
                ev.setCancelled(true);
                return;
            }
        }

        if (ev.getEntity() instanceof Horse && ev.getSpawnReason() != CreatureSpawnEvent.SpawnReason.SPAWNER_EGG && ev.getSpawnReason() != CreatureSpawnEvent.SpawnReason.CUSTOM) {

            Horse h = (Horse) ev.getEntity();

            if (h.getCustomName() != null && h.getCustomName().length() > 0) {
                return;
            }

            if (h.getColor() == Horse.Color.BLACK || h.getColor() == Horse.Color.WHITE) {
                if (Jobs.rnd.nextInt(8) != 1) {
                    h.setColor(Horse.Color.CREAMY);
                }
            }

            if (h.getColor() != Horse.Color.CREAMY) {
                if (Jobs.rnd.nextInt(7) != 1) {
                    h.setColor(Horse.Color.CREAMY);
                }
            }

            if (h.getStyle() != Horse.Style.NONE) {
                if (Jobs.rnd.nextInt(5) != 1) {
                    h.setStyle(Horse.Style.NONE);
                }
            }
        }

        if (ev.getEntityType() == EntityType.HORSE || ev.getEntity().getType() == EntityType.VILLAGER) {
            return;
        }

        final String tipo = ClanLand.getTypeAt(ev.getEntity().getLocation());
        if (ev.getSpawnReason() == CreatureSpawnEvent.SpawnReason.SPAWNER) {
            if (ev.getEntity().getWorld().getName().equalsIgnoreCase("vila")) {
                ev.setCancelled(true);
                KoM.debug("cancel 1");
                return;
            }
            if (!ev.getEntity().getWorld().getName().equalsIgnoreCase("vila")
                    && !ev.getEntity().getWorld().getName().equalsIgnoreCase("dungeon")) {

                if (tipo != null && !tipo.equalsIgnoreCase("WARZ")) {
                    ev.setCancelled(true);
                    return;
                } else if (tipo != null && tipo.equalsIgnoreCase("WARZ")) {
                    return;
                }
            }
            int q = 0;
            for (Entity e : ev.getEntity().getNearbyEntities(16, 50, 16)) {
                if (e.getType() == ev.getEntityType()) {
                    q++;
                }
            }
            if (q > 8) {
                KoM.debug("cancel 2");
                ev.setCancelled(true);
                return;
            }
        } else if (ev.getSpawnReason() == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG) {
            int q = 0;
            for (Entity e : ev.getEntity().getNearbyEntities(16, 20, 16)) {
                if (e.getType() == ev.getEntityType()) {
                    q++;
                }
            }
            if (q > 12) {
                KoM.debug("cancel 3");
                ev.setCancelled(true);
                return;
            }
        } else if (ev.getSpawnReason() == CreatureSpawnEvent.SpawnReason.BREEDING) {
            int q = 0;
            for (Entity e : ev.getEntity().getNearbyEntities(16, 20, 16)) {
                if (e.getType() == ev.getEntityType()) {
                    q++;
                }
            }
            if (q > 10) {
                KoM.debug("cancel 4");
                ev.setCancelled(true);
                return;
            }
        } else if (ev.getSpawnReason() != CreatureSpawnEvent.SpawnReason.CUSTOM) {
            if (!ev.getEntity().getWorld().getName().equalsIgnoreCase("dungeon") && ev.getEntity().getWorld().getEntities().size() >= 1200) {
                KoM.debug("cancel 5");
                ev.setCancelled(true);
                return;
            }
        }

        if (ev.isCancelled()) {
            return;
        }

        if (ev.getLocation().getWorld().getName().equalsIgnoreCase("dungeon")) {
            ApplicableRegionSet set = KoM.worldGuard.getRegionManager(Bukkit.getWorld("dungeon")).getApplicableRegions(ev.getLocation());
            if (set.size() == 0) {
                KoM.debug("cancel 6");
                ev.setCancelled(true);
                return;
            }
            if (ev.getEntity().getLocation().getY() <= 5 && ev.getEntity().getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.GRASS) {
                ev.setCancelled(true);
                return;
            }
        }
        if (ev.getEntity() instanceof Monster) {
            if (Deuses.paz && !ev.getEntity().getWorld().getName().equalsIgnoreCase("dungeon")) {
                ev.setCancelled(true);
                KoM.debug("cancel 7");
                Deuses.matou++;
                return;
            }
            if (WorldGuardKom.ehSafeZone(ev.getLocation()) || tipo.equalsIgnoreCase("SAFE")) {
                ev.setCancelled(true);
                return;
            }
            if (ev.getEntity().getLocation().getWorld().getName().equalsIgnoreCase("dungeon")) {
                if (ev.getSpawnReason() == CreatureSpawnEvent.SpawnReason.SPAWNER) {
                    int numeroPerto = ev.getEntity().getNearbyEntities(25, 4, 25).size();
                    if (numeroPerto > 4) {
                        KoM.debug("cancel 8");
                        ev.setCancelled(true);
                    }
                }
            }
        }
        if (ev.getSpawnReason() == CreatureSpawnEvent.SpawnReason.BREEDING) {
            if (Jobs.rnd.nextInt(3) != 1) {
                ev.setCancelled(true);
                return;
            }
        }

        Runnable r = new Runnable() {
            public void run() {
                if (ev.getEntity().hasMetadata("mobCustom") || KoM.mm.getMobManager().isActiveMob(ev.getEntity().getUniqueId()) || ev.getEntity().getCustomName() != null) {
                    if (KoM.debugMode) {
                        KoM.log.info("MOB JA TINHA COISAS");
                    }
                    return;
                } else {
                    if (ev.getEntity().getCustomName() == null || ev.getEntity().getCustomName().length() == 0) {
                        Mobs.spawn(ev, tipo);
                    }
                    if (KoM.debugMode) {
                        KoM.log.info("SETEI COISAS DO MOB");
                    }
                }
            }
        };
        Bukkit.getScheduler().scheduleSyncDelayedTask(KoM._instance, r, 20 * 2);
    }

    public static int firstDigit(int n) {
        while (n < -9 || 9 < n) {
            n /= 10;
        }
        return Math.abs(n);
    }

    public static EfeitoMobs getEfeito(String nome) {
        for (EfeitoMobs m : EfeitoMobs.values()) {
            if (m.desc.equalsIgnoreCase(nome)) {
                return m;
            }
            try {
                if (EfeitoMobs.valueOf(nome) != null) {
                    return EfeitoMobs.valueOf(nome);
                }
            } catch (Exception e) {
            }
        }
        return null;
    }

    public static Material[] dropsLevelAlto
            = {
                Material.NETHER_WARTS, Material.GLOWSTONE_DUST, Material.BLAZE_POWDER, Material.BOOK_AND_QUILL, Material.APPLE,
                Material.BLAZE_ROD, Material.GOLDEN_CARROT, Material.GHAST_TEAR, Material.GOLD_NUGGET, Material.MAGMA_CREAM, Material.DIRT, Material.COBBLESTONE, Material.REDSTONE, Material.POISONOUS_POTATO, Material.IRON_INGOT,
                Material.NETHERRACK, Material.NETHER_STAR, Material.EXP_BOTTLE, Material.MOSSY_COBBLESTONE, Material.PAINTING, Material.BEDROCK, Material.BAKED_POTATO, Material.DIAMOND, Material.IRON_BLOCK, Material.BLAZE_POWDER, Material.EXP_BOTTLE, Material.FIREWORK, Material.HUGE_MUSHROOM_1, Material.DRAGON_EGG,
                Material.SKULL, Material.DIAMOND, Material.IRON_SWORD, Material.IRON_AXE, Material.IRON_PICKAXE, Material.SULPHUR, Material.APPLE, Material.RED_MUSHROOM, Material.REDSTONE, Material.COMPASS, Material.EMPTY_MAP
            };

    public static void dropRandomItem(Location l) {
        Material random = dropsLevelAlto[Jobs.rnd.nextInt(dropsLevelAlto.length)];
        l.getWorld().dropItemNaturally(l, new ItemStack(random, 1));
    }

    public static double getDamageReduced(LivingEntity player) {
        EntityEquipment equips = player.getEquipment();
        ItemStack boots = equips.getBoots();
        ItemStack helmet = equips.getHelmet();
        ItemStack chest = equips.getChestplate();
        ItemStack pants = equips.getLeggings();
        double red = 0.0;
        if (helmet.getType() == Material.LEATHER_HELMET) {
            red = red + 0.04;
        } else if (helmet.getType() == Material.GOLD_HELMET) {
            red = red + 0.08;
        } else if (helmet.getType() == Material.CHAINMAIL_HELMET) {
            red = red + 0.08;
        } else if (helmet.getType() == Material.IRON_HELMET) {
            red = red + 0.08;
        } else if (helmet.getType() == Material.DIAMOND_HELMET) {
            red = red + 0.12;
        }
        //
        if (boots.getType() == Material.LEATHER_BOOTS) {
            red = red + 0.04;
        } else if (boots.getType() == Material.GOLD_BOOTS) {
            red = red + 0.04;
        } else if (boots.getType() == Material.CHAINMAIL_BOOTS) {
            red = red + 0.04;
        } else if (boots.getType() == Material.IRON_BOOTS) {
            red = red + 0.08;
        } else if (boots.getType() == Material.DIAMOND_BOOTS) {
            red = red + 0.12;
        }
        //
        if (pants.getType() == Material.LEATHER_LEGGINGS) {
            red = red + 0.08;
        } else if (pants.getType() == Material.GOLD_LEGGINGS) {
            red = red + 0.12;
        } else if (pants.getType() == Material.CHAINMAIL_LEGGINGS) {
            red = red + 0.16;
        } else if (pants.getType() == Material.IRON_LEGGINGS) {
            red = red + 0.20;
        } else if (pants.getType() == Material.DIAMOND_LEGGINGS) {
            red = red + 0.24;
        }
        //
        if (chest.getType() == Material.LEATHER_CHESTPLATE) {
            red = red + 0.12;
        } else if (chest.getType() == Material.GOLD_CHESTPLATE) {
            red = red + 0.20;
        } else if (chest.getType() == Material.CHAINMAIL_CHESTPLATE) {
            red = red + 0.20;
        } else if (chest.getType() == Material.IRON_CHESTPLATE) {
            red = red + 0.24;
        } else if (chest.getType() == Material.DIAMOND_CHESTPLATE) {
            red = red + 0.32;
        }
        return red/2;
    }

    public static void mobApanhaHigh(EntityDamageByEntityEvent ev) {

        Entity quemCausou = ev.getDamager();
        if (quemCausou instanceof Projectile) {
            if (((Projectile) quemCausou).getShooter() instanceof LivingEntity) {
                quemCausou = (LivingEntity) ((Projectile) quemCausou).getShooter();
            }
        }

        if (quemCausou != null) {

            if (quemCausou.getType() != EntityType.PLAYER) {
                return;
            }

            if (ev.getEntity().hasMetadata(quemCausou.getName() + "Tempo")) {
                long tempo = (Long) MetaShit.getMetaObject(quemCausou.getName() + "Tempo", ev.getEntity());
                long agora = System.currentTimeMillis() / 1000;
                if ((tempo + 1) > agora) {
                    ev.setCancelled(true);
                    return;
                }
            }
        }

        double danoAbsorvido = getDamageReduced((LivingEntity) ev.getEntity()) * ev.getDamage();
        double danoFinal = ev.getDamage() - danoAbsorvido;

        //// ALTERANDO DANO DE MOB
        //danoFinal /= 1.7;

        if (ev.getEntity().getType() == EntityType.CREEPER || ev.getEntity().getType() == EntityType.ZOMBIE) {
            ev.getEntity().getLocation().getWorld().playEffect(ev.getEntity().getLocation().add(0.0D, 1.0D, 0.0D), Effect.STEP_SOUND, Material.SLIME_BLOCK.getId());
        } else if (ev.getEntity().getType() == EntityType.GHAST || ev.getEntity().getType() == EntityType.SKELETON) {
            ev.getEntity().getLocation().getWorld().playEffect(ev.getEntity().getLocation().add(0.0D, 1.0D, 0.0D), Effect.STEP_SOUND, Material.WOOL.getId());
        } else if (ev.getEntity().getType() == EntityType.BLAZE) {
            ev.getEntity().getLocation().getWorld().playEffect(ev.getEntity().getLocation().add(0.0D, 1.0D, 0.0D), Effect.STEP_SOUND, Material.GOLD_BLOCK.getId());
        } else if (ev.getEntity().getType() == EntityType.PIG_ZOMBIE || ev.getEntity().getType() == EntityType.SPIDER || ev.getEntity().getType() == EntityType.CAVE_SPIDER) {
            ev.getEntity().getLocation().getWorld().playEffect(ev.getEntity().getLocation().add(0.0D, 1.0D, 0.0D), Effect.STEP_SOUND, Material.OBSIDIAN.getId());
        } else {
            ev.getEntity().getLocation().getWorld().playEffect(ev.getEntity().getLocation().add(0.0D, 1.0D, 0.0D), Effect.STEP_SOUND, 152);
        }

        // ((LivingEntity)ev.getEntity()).setHealth(vida);
        ev.getEntity().setLastDamageCause(ev);

        if (ev.getDamager().getType() == EntityType.PLAYER) {
            KoM.dano.mostraDano((Player) ev.getDamager(), danoFinal, Dano.BATI);
        }

        Mobs.simpleKick(0.2, 0.1, (Player) quemCausou, ev.getEntity());
        MetaShit.setMetaObject(quemCausou.getName() + "Tempo", ev.getEntity(), System.currentTimeMillis() / 1000);

        if (danoFinal > ((LivingEntity) ev.getEntity()).getHealth()) {
            ev.setDamage(danoFinal);
            KoM.debug("Batendo com danoFinal padrao do mine");
        } else {
            GeneralListener.ultimoDano.put(ev.getEntity().getUniqueId(), ev.getDamager().getUniqueId());
            ((LivingEntity) ev.getEntity()).damage(danoFinal);
            ((LivingEntity) ev.getEntity()).setNoDamageTicks(0);
            ev.setCancelled(true);
            KoM.debug("Batendo com .damage");
        }
    }

    public static void morreMob(EntityDeathEvent ev) {
        
        for(PotionEffect ef : ev.getEntity().getActivePotionEffects())
            ev.getEntity().removePotionEffect(ef.getType());
        
        if (KoM.mm.getMobManager().isActiveMob(ev.getEntity().getUniqueId())) {

            Optional<ActiveMob> omob = KoM.mm.getMobManager().getActiveMob(ev.getEntity().getUniqueId());
            ActiveMob mob = omob.get();
            if (mob.getLevel() > 1) {
                if (ev.getEntity().getKiller() != null) {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "[BOSS]" + ChatColor.BLUE + ev.getEntity().getKiller().getName() + L.m(" matou o BOSS ") + ev.getEntity().getCustomName());
                    }
                } else {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "[BOSS]" + ChatColor.BLUE + "O BOSS " + ev.getEntity().getCustomName() + L.m(" foi MORTO !"));
                    }
                }
            }
        }

        if (ev.getEntity().getType() == EntityType.GHAST) {
            ev.getEntity().getWorld().dropItemNaturally(ev.getEntity().getLocation(), new ItemStack(Material.FIREBALL, 2));
        }

        if (ev.getEntity() instanceof Monster && !(ev.getEntity() instanceof Tameable) || ev.getEntity().getType() == EntityType.MAGMA_CUBE || ev.getEntity().getType() == EntityType.SLIME) {

            KoM.debug("MORRENDO MOB");

            double xpBoss = 0;

            Object level = MetaShit.getMetaObject("nivel", ev.getEntity());
            int levelDaZona = ClanLand.getMobLevel(ev.getEntity().getLocation());
            int lvlDoMob = 0;
            if (level != null) {
                lvlDoMob = (int) level;
            }
            if (true) {

                int chanceDrop = 1;
                int chanceDropEsmeralda = Jobs.rnd.nextInt((levelDaZona + 1) * 4);

                if (levelDaZona > 16) {
                    chanceDropEsmeralda += 10;
                }

                if (levelDaZona > 8 && levelDaZona < 17) {
                    chanceDrop += 40;
                } else if (levelDaZona >= 17) {
                    chanceDrop += 150;
                }

                chanceDrop += (lvlDoMob - 1) * 400;

                if (Deuses.odio) {
                    chanceDrop += 80;
                    chanceDropEsmeralda += 50;
                }

                if (chanceDrop >= Jobs.rnd.nextInt(10000)) {
                    dropRandomItem(ev.getEntity().getLocation());
                    if (chanceDrop < 20) {
                        dropRandomItem(ev.getEntity().getLocation());
                    }
                }

                if (chanceDropEsmeralda > Jobs.rnd.nextInt(100)) {
                    ev.getEntity().getWorld().dropItemNaturally(ev.getEntity().getLocation(), new ItemStack(Material.EMERALD, 1));
                }

                int chanceDropMob = 1;
                switch (lvlDoMob) {
                    case 0:
                        chanceDropMob = 1;
                        break;
                    case 1:
                        chanceDropMob = 2;
                        break;
                    case 2:
                        chanceDropMob = 3;
                        break;
                    case 3:
                        chanceDropMob = 5;
                        break;
                    case 4:
                        chanceDropMob = 10;
                        break;
                    case 5:
                        chanceDropMob = 50;
                        break;
                }
                if (Deuses.odio) {
                    chanceDropMob += 30;
                }

                for (Entity e : ev.getEntity().getNearbyEntities(8, 3, 8)) {
                    if (e.getType() == EntityType.PLAYER) {
                        KoM.debug("PLAYER PERTO");
                        Player matou = (Player) e;
                        if (matou.getLevel() > 1 && matou.getLevel() <= 10) {
                            int rnd = Jobs.rnd.nextInt(10);
                            boolean jaPegou = matou.hasMetadata("monstropedia");
                            boolean jaTem = Mobs.temMonstropedia(matou);
                            KoM.debug(jaPegou + " - " + jaTem + " - " + rnd);
                            if (rnd == 1 && !jaPegou && !jaTem) {
                                KoM.debug("Dropando livro");
                                MetaShit.setMetaObject("monstropedia", matou, true);
                                ItemStack monstropedia = Mobs.getMonstropedia();
                                ev.getEntity().getWorld().dropItemNaturally(ev.getEntity().getLocation(), monstropedia);
                            }
                        }
                    }

                }

                if (chanceDropMob > Jobs.rnd.nextInt(100)) {
                    ev.getEntity().getWorld().dropItemNaturally(ev.getEntity().getLocation(), CustomItem.getItem(DropMob.class).generateItem());
                }

                int chanceIncomum = 1; // 1 em 100
                switch (lvlDoMob) {
                    case 0:
                        chanceIncomum = 0;
                        break;
                    case 1:
                        chanceIncomum = 1;
                        break;
                    case 2:
                        chanceIncomum = 2;
                        break;
                    case 3:
                        chanceIncomum = 10;
                        break;
                    case 4:
                        chanceIncomum = 30;
                        break;
                    case 5:
                        chanceIncomum = 80;
                        break;
                }
                if (Deuses.odio) {
                    chanceIncomum += 20;
                }
                Player matador = ev.getEntity().getKiller();
                if (matador != null) {
                    if (lvlDoMob >= 4) {
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            if (p != matador) {
                                p.sendMessage(ChatColor.GREEN + L.m("O jogador % acaba de matar um monstro maligno !", matador.getName()));
                            }
                        }
                    }
                }
                ItemStack drop = null;
                if (ev.getEntity().getType() == EntityType.ZOMBIE || ev.getEntity().getType() == EntityType.PIG_ZOMBIE) {
                    drop = new ItemStack(Material.ROTTEN_FLESH, 1);
                } else if (ev.getEntity().getType() == EntityType.SPIDER || ev.getEntity().getType() == EntityType.CAVE_SPIDER) {
                    drop = new ItemStack(Material.STRING);
                } else if (ev.getEntity().getType() == EntityType.SKELETON) {
                    drop = new ItemStack(Material.BONE, 1);
                }
                int qtdDrops = lvlDoMob + (levelDaZona / 5);
                if (qtdDrops > 0 && drop != null) {
                    drop.setAmount(qtdDrops);
                    ev.getEntity().getWorld().dropItemNaturally(ev.getEntity().getLocation(), drop);
                }
                if (ev.getEntity().getWorld().getName().equalsIgnoreCase("dungeon")) {
                    //ev.setDroppedExp((int) (exp * 1.5));
                    chanceIncomum *= 2;
                    if (KoM.debugMode) {
                        KoM.log.info("nivel daq morreu: " + lvlDoMob + " chanceDrop=" + chanceIncomum);
                    }
                } else {
                    // ev.setDroppedExp((int) exp);
                    chanceIncomum /= 1.2;
                }
                if (chanceIncomum > Jobs.rnd.nextInt(100)) {
                    ev.getDrops().add(CustomItem.getItem(ItemIncomum.class).generateItem());
                }
            }

            double xpTotal = XP.getExpPorAcao(levelDaZona * 5);
            
            xpTotal *= 1.2; // pokin melhor vai...
            
            if(ev.getEntity().getKiller()!=null) {
                if(Jobs.getJobLevel(Jobs.Classe.Mago, ev.getEntity().getKiller())==TipoClasse.PRIMARIA ||
                   Jobs.getJobLevel(Jobs.Classe.Paladino, ev.getEntity().getKiller())==TipoClasse.PRIMARIA ||
                   Jobs.getJobLevel(Jobs.Classe.Ladino, ev.getEntity().getKiller())==TipoClasse.PRIMARIA ) {
                    xpTotal *= 1.2;
                }
            }
            
            if (Deuses.odio) {
                xpTotal = xpTotal;
            }
            switch (lvlDoMob) {
                case 1:
                    xpTotal *= 2;
                    break;
                case 2:
                    xpTotal *= 10;
                    break;
                case 3:
                    xpTotal *= 20;
                    break;
                case 4:
                    xpTotal *= 30;
                    break;
                case 5:
                    xpTotal *= 45;
                    break;
            }

            int porradas = 0;
            if (ev.getEntity().hasMetadata("tomouPorradas")) {
                porradas = (Integer) MetaShit.getMetaObject("tomouPorradas", ev.getEntity());
                if (KoM.debugMode) {
                    System.out.println("TOMOU PORRADAS " + porradas);
                }
            }

            if (porradas > 5) {
                porradas = 5;
            }
            if (porradas <= 0) {
                porradas++;
            }
            xpTotal *= porradas * 0.2;

            ClanPlayer matou = null;
            if (ev.getEntity().getKiller() != null) {
                matou = ClanLand.manager.getClanPlayer(ev.getEntity().getKiller().getUniqueId());
            }
            HashSet<Player> beneficiados = new HashSet<Player>();
            Entity qconta = ev.getEntity();
            for (Entity e : ev.getEntity().getNearbyEntities(15, 3,15)) {
                if (e.getType() == EntityType.PLAYER && !beneficiados.contains(e)) {
                    Player beneficiado = (Player) e;
                    if (matou != null) {
                        if (!matou.isAlly(beneficiado) && !matou.getClan().isMember(beneficiado)) {
                            continue;
                        }
                    }
                    beneficiados.add((Player) e);
                }
            }
            if (beneficiados.size() == 0) {
                if (ev.getEntity().getKiller() != null) {
                    for (Entity e : ev.getEntity().getKiller().getNearbyEntities(15, 3, 15)) {
                        if (e.getType() == EntityType.PLAYER && !beneficiados.contains(e)) {
                            Player beneficiado = (Player) e;
                            if (matou != null) {
                                if (!matou.isAlly(beneficiado) && !matou.getClan().isMember(beneficiado)) {
                                    continue;
                                }
                            }
                            beneficiados.add((Player) e);
                        }
                    }
                    if (!beneficiados.contains(ev.getEntity().getKiller())) {
                        beneficiados.add(ev.getEntity().getKiller());
                    }
                }
            }
            if (ev.getEntity().getKiller() != null) {
                RankDB.addPontoCache(ev.getEntity().getKiller(), Estatistica.MOB_KILLS, (levelDaZona * 2) + (lvlDoMob * 5));
            }

            ev.setDroppedExp(0);

            if (xpBoss != 0) {
                xpTotal = xpBoss;
            }

            if (beneficiados.size() != 0) {
                int qt = beneficiados.size();
                if (qt == 0) {
                    qt = 1;
                }
                double qtdPorPlayer = (xpTotal);
                int levelZonaBruto = levelDaZona * 5;
                for (Player p : beneficiados) {
                    if (p == null) {
                        continue;
                    }
                    Karma.manoloMata(p, ev.getEntity());
                    double qtdEsse = qtdPorPlayer;
                    TipoBless ativo = TipoBless.save.getTipo(p);
                    if (ativo != null && ativo == TipoBless.Prosperidade) {
                        qtdEsse = qtdEsse * 3;
                    }
                    if (lvlDoMob < 5 && p.getLevel() < levelZonaBruto - 15) {
                        GeneralListener.givePlayerExperience(qtdEsse / 200, p);
                        p.sendMessage(ChatColor.RED + L.m("Voce nao ganhou muita exp pois o mob eh muito forte pro seu nivel"));
                    } else if (lvlDoMob < 5 && p.getLevel() > levelZonaBruto + 15) {
                        GeneralListener.givePlayerExperience(qtdEsse / 200, p);
                        p.sendMessage(ChatColor.RED + L.m("Voce nao ganhou muita exp pois o mob eh muito fraco pro seu nivel"));
                    } else {
                        GeneralListener.givePlayerExperience(qtdEsse, p);
                        //p.sendMessage(ChatColor.GREEN + L.m("Voce ganhou % exp", (DoubleXP.ativo ? qtdPorPlayer * 2 : qtdPorPlayer) + ""));
                    }
                }

            }
        }
    }
    public static EntityType[] hardMobs
            = {
                EntityType.GHAST, EntityType.BLAZE, EntityType.BLAZE, EntityType.BLAZE, EntityType.ENDERMAN, EntityType.MAGMA_CUBE, EntityType.MAGMA_CUBE, EntityType.WITCH
            };
    public static EntityType[] bosses
            = {
                EntityType.ENDER_DRAGON, EntityType.WITHER
            };
    public static List<EfeitoMobs> tmp = null;

    public static List<EntityType> bixinhos = Arrays.asList(new EntityType[]{EntityType.RABBIT, EntityType.COW, EntityType.CHICKEN, EntityType.PIG, EntityType.PARROT, EntityType.SHEEP, EntityType.EGG});
    
    public static void spawnaMob(CreatureSpawnEvent ev, String tipo) {
        if (ev.getEntity() instanceof Monster && !(ev.getEntity() instanceof Tameable) && (!bixinhos.contains(ev.getEntity().getType()))) {
            if (tipo == null) {
                return;
            }
            if (ev.getEntity().getType() == EntityType.ENDERMAN) {
                if (ev.getEntity().getLocation().getWorld().getName().equalsIgnoreCase("dungeon")) {
                    ev.setCancelled(true);
                    return;
                }
            }
            if (ev.getEntity().hasMetadata("mobCustom")) {
                if (KoM.debugMode) {
                    KoM.log.info("EH UM MOB CUSTOM !!");
                }
                return;
            }
            if (((LivingEntity) ev.getEntity()).getCustomName() != null && ((LivingEntity) ev.getEntity()).getCustomName().length() > 0) {
                return;
            }
            int levelDaZona = ClanLand.getMobLevel(ev.getLocation());
            int chanceSpawnMobForte = 0;
            if (levelDaZona > 18) {
                chanceSpawnMobForte = 18;
            } else if (levelDaZona > 16) {
                chanceSpawnMobForte = 12;
            } else if (levelDaZona > 13) {
                chanceSpawnMobForte = 5;
            } else if (levelDaZona > 10) {
                chanceSpawnMobForte = 2;
                //chanceSpawnBoss += 1;
            }
            if (Deuses.odio) {
                chanceSpawnMobForte += 10;
            }
            if (levelDaZona > 6 && ev.getEntity() instanceof Skeleton) {
                Spider s = (Spider) ev.getEntity().getWorld().spawnEntity(ev.getEntity().getLocation(), EntityType.SPIDER);
                s.setPassenger(ev.getEntity());
            } else if (levelDaZona > 10 && ev.getEntity().getType() == EntityType.ZOMBIE) {
                ev.getLocation().getWorld().spawnEntity(ev.getLocation(), EntityType.PIG_ZOMBIE);
                ev.setCancelled(true);
                return;
            }
            if (chanceSpawnMobForte > Jobs.rnd.nextInt(100)) {
                ev.getLocation().getWorld().spawnEntity(ev.getLocation(), hardMobs[Jobs.rnd.nextInt(hardMobs.length)]);
                if (Jobs.rnd.nextInt(2) == 1) {
                    ev.getEntity().remove();
                    return;
                }
            }
            int nivelDoMob = 0;
            int maxSorte = 100;
            if (levelDaZona < 3) {
                maxSorte = 75;
            } else if (levelDaZona < 7) {
                maxSorte = 87;
            } else if (levelDaZona < 12) {
                maxSorte = 94;
            }
            if (Deuses.odio) {
                maxSorte += 110;
            }
            // if (Jobs.rnd.nextInt(3) != 1) {
            int sorte = Jobs.rnd.nextInt(maxSorte);
            if (sorte > 50 && sorte <= 78) {
                nivelDoMob = 1;
            } else if (sorte >= 78 && sorte < 87) {
                nivelDoMob = 2;
            } else if (sorte >= 87 && sorte < 93) {
                nivelDoMob = 3;
            } else if (sorte >= 93 && sorte < 98) {
                nivelDoMob = 4;
            } else if (sorte > 98) {
                nivelDoMob = 5;
            }
            // }
            if (levelDaZona < 15 && nivelDoMob > 3) {
                if (Jobs.rnd.nextInt(3) != 1) {
                    nivelDoMob = 1;
                }
            }
            if (nivelDoMob > 0) {
                HashSet<EfeitoMobs> efeitosDoMob = getEfeitosAleatorios(nivelDoMob, ev.getEntity());
                MetaShit.setMetaObject("efeitos", ev.getEntity(), efeitosDoMob);
                MetaShit.setMetaObject("nivel", ev.getEntity(), nivelDoMob);
                ev.getEntity().setCustomNameVisible(true);
                if (efeitosDoMob.contains(EfeitoMobs.velocidade)) {
                    ev.getEntity().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 123123, 2));
                }
            } else {
                MetaShit.setMetaObject("nivel", ev.getEntity(), 0);
            }
            if (!ev.getEntity().hasMetadata("vidaCustom")) {
                double vidaMob = ev.getEntity().getMaxHealth();
                vidaMob += nivelDoMob * 15;
                if (levelDaZona > 5) {
                    vidaMob += levelDaZona * 1.2;
                }
                ev.getEntity().setMaxHealth(vidaMob);
                ev.getEntity().setHealth(ev.getEntity().getMaxHealth());
                if (KoM.debugMode) {
                    KoM.log.info("Setei vida de mob;");
                }
            }
        }
    }

    public static HashSet<EfeitoMobs> getEfeitosAleatorios(int qtd, LivingEntity mob) {
        if (mob.hasMetadata("mobCustom")) {
            return new HashSet<EfeitoMobs>();
        }
        List<EfeitoMobs> efeitos = new ArrayList<EfeitoMobs>(Arrays.asList(EfeitoMobs.values()));
        HashSet<EfeitoMobs> efeitosDoMob = new HashSet<EfeitoMobs>();
        String nomes = "";
        int qtdNomes = 0;
        int qtdTemp = qtd;
        while (qtdTemp > 0) {
            EfeitoMobs efeitoSorteado = efeitos.get(Jobs.rnd.nextInt(efeitos.size()));
            efeitosDoMob.add(efeitoSorteado);
            efeitos.remove(efeitoSorteado);
            nomes += " " + efeitoSorteado.chara;
            qtdNomes++;
            qtdTemp--;
        }
        mob.setCustomName(getCor(qtd) + nomes);
        return efeitosDoMob;
    }

    public static String getCor(int level) {
        switch (level) { // ②③④⑤➊ ➋ ➌ ➍ ➎
            case 1:
                return ChatColor.AQUA + "➊" + ChatColor.GREEN;
            case 2:
                return ChatColor.GREEN + "➋" + ChatColor.GREEN;
            case 3:
                return ChatColor.YELLOW + "➌" + ChatColor.GREEN;
            case 4:
                return ChatColor.GOLD + "➍" + ChatColor.GREEN;
            case 5:
                return ChatColor.RED + "➎" + ChatColor.GREEN;
        }
        return ChatColor.AQUA + "➊" + ChatColor.GREEN;
    }

    public static boolean temMonstropedia(Player p) {
        for (ItemStack ss : p.getInventory().getContents()) {
            if (ss == null || ss.getType() != Material.WRITTEN_BOOK) {
                continue;
            }
            BookMeta meta = (BookMeta) ss.getItemMeta();
            if (meta.getDisplayName() != null && meta.getDisplayName().contains("Monstropedia")) {
                return true;
            }
        }
        return false;
    }

    public static ItemStack getMonstropedia() {
        ItemStack livro = new ItemStack(Material.WRITTEN_BOOK, 1);
        BookMeta meta = (BookMeta) livro.getItemMeta();
        meta.setAuthor("Jabu");
        meta.setDisplayName(Raridade.Incomum.getIcone() + ChatColor.WHITE + " Monstropedia");
        List<String> paginas = new ArrayList<String>();
        paginas.add(ChatColor.RED + "Monstropédia\nVersão 1.0\n\n" + ChatColor.BLUE + "Anotações sobre peculiaridades evolutivas dos monstros que vivem em Aden.");
        paginas.add(ChatColor.RED + "Sumário\n\n" + ChatColor.BLUE + "Monstros a anos que residem em Aden, nossas terras. Assim como humanos, monstros possuem características, neste livro, tenrarei descrever algumas caracteristicas estudadas.");
        for (EfeitoMobs ef : EfeitoMobs.values()) {
            paginas.add(ChatColor.RED + ef.desc + "\n\n" + ChatColor.BLUE + "Simbologia: " + ChatColor.BLACK + ef.chara + "\n\n" + ChatColor.BLUE + ef.oqFaz);
        }
        meta.setPages(paginas);
        livro.setItemMeta(meta);
        return livro;
    }

    public static void playerBateEmMob(Player attacker, Monster mob, EntityDamageEvent ev) {
        // se ta batendo com machados

        if (attacker.hasPotionEffect(PotionEffectType.BLINDNESS)) {
            if (Jobs.rnd.nextInt(3) != 1) {
                attacker.sendMessage(ChatColor.RED + "Voce errou o ataque");
                ev.setCancelled(true);
                return;
            }
        }

        if (attacker.getItemInHand().getType() == Material.GOLD_AXE || attacker.getItemInHand().getType() == Material.WOOD_AXE || attacker.getItemInHand().getType() == Material.IRON_AXE || attacker.getItemInHand().getType() == Material.STONE_AXE || attacker.getItemInHand().getType() == Material.DIAMOND_AXE) {
            // verificando maxadada epica
            if (attacker.hasMetadata("epichax")) {
                MetaShit s;
                int task = (int) MetaShit.getMetaObject("epichax", attacker);
                Bukkit.getScheduler().cancelTask(task);
                attacker.removeMetadata("epichax", KoM._instance);
                if (attacker.hasMetadata("epichaxpronta")) {
                    attacker.removeMetadata("epichaxpronta", KoM._instance);
                    Lumberjack.tentaMachadadaEpicaEmMob(attacker, ev);
                    attacker.sendMessage(ChatColor.RED + L.m("Voce afobou a machadada epica !"));
                } else {
                    attacker.sendMessage(ChatColor.RED + L.m("Voce afobou a machadada epica !"));
                }
            }
        }

        if (ev.getCause() == DamageCause.ENTITY_ATTACK || ev.getCause() == DamageCause.CONTACT) {
            int hits = 0;
            if (mob.hasMetadata("tomouPorradas")) {
                hits = (Integer) MetaShit.getMetaObject("tomouPorradas", mob);
            }
            hits++;
            MetaShit.setMetaObject("tomouPorradas", mob, hits);
        }
        if (Jobs.getJobLevel("Ladino", attacker) == 1) {
            ev.setDamage(ev.getDamage() * 1.2);
        }
        if (attacker.getItemInHand().getType().name().contains("SPADE")) {
            if (Jobs.getJobLevel("Ferreiro", attacker) == 1) {
                if (attacker.getItemInHand().getType() == Material.GOLD_SPADE) {
                    ev.setDamage(ev.getDamage() * 4);
                } else {
                    ev.setDamage(ev.getDamage() * 2);
                }
            }
        } // se ta batendo com hoes
        else if (attacker.getItemInHand().getType() == Material.WOOD_HOE || attacker.getItemInHand().getType() == Material.GOLD_HOE || attacker.getItemInHand().getType() == Material.IRON_HOE || attacker.getItemInHand().getType() == Material.STONE_HOE || attacker.getItemInHand().getType() == Material.DIAMOND_HOE) {
            if (Jobs.getJobLevel("Fazendeiro", attacker) == 1) {
                if (attacker.getItemInHand().getType() == Material.GOLD_HOE) {
                    ev.setDamage(ev.getDamage() * 2);
                } else {
                    ev.setDamage(ev.getDamage() * 1.5);
                }
            }
            if (Jobs.getJobLevel("Alquimista", attacker) == 1) {
                if (attacker.getItemInHand().getType() == Material.GOLD_HOE) {
                    mob.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 20, 1));
                    mob.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20 * 20, 3));
                } else {
                    mob.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 15, 0));
                    mob.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20 * 20, 1));
                }
            }
        }
        Object objEf = MetaShit.getMetaObject("efeitos", mob);
        if (KoM.debugMode) {
            KoM.log.info("esse mob tem efeitos? :" + (objEf == null));
        }
        if (objEf != null) {
            HashSet<EfeitoMobs> efeitos = (HashSet) objEf;
            if (efeitos != null) {
                if (KoM.debugMode) {
                    KoM.log.info("EFEITOS:" + efeitos.toString());
                }
                if (efeitos.size() > 0) {
                    if (attacker.getLevel() <= 20 && !attacker.hasMetadata("msgmobs")) {
                        MetaShit.setMetaObject("msgmobs", attacker, true);
                        attacker.sendMessage("§e§l[Dica] §aAlguns mobs tem efeitos especiais. A Quantidade de efeitos que o mob tem fica em cima da cabeça dele quando voce olha para ele.");
                        attacker.sendMessage("§e§l[Dica] §aSimbolos marcam quem efeitos são.");
                    }
                }
                if (efeitos.contains(EfeitoMobs.borrachudo)) {
                    Vector v = attacker.getLocation().toVector().subtract(mob.getLocation().toVector()).normalize().multiply(1.8D);
                    v.setY(0.5d);
                    attacker.setVelocity(v);
                }
                if (efeitos.contains(EfeitoMobs.espinhudo) && ev.getCause() == DamageCause.ENTITY_ATTACK || ev.getCause() == DamageCause.CONTACT) {
                    Vector v = attacker.getLocation().toVector().subtract(mob.getLocation().toVector()).normalize().multiply(1.5D);
                    v.setY(0.2d);
                    attacker.setVelocity(v);
                    attacker.damage(ev.getDamage() / 4);
                }
                if (efeitos.contains(EfeitoMobs.regen)) {
                    mob.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 10, 1));
                }
                if (efeitos.contains(EfeitoMobs.defesaExtra)) {
                    ev.setDamage(ev.getDamage() / 2);
                }
            }
        }
        //ev.setDamage(ev.getDamage() / 1.6);
    }

    public static void enemyCauseDamage(Monster enemy, Player defender, EntityDamageEvent ev) {
        Integer lvlZona = ClanLand.getMobLevel(enemy.getLocation());
        if (lvlZona >= 21) {
            ev.setDamage(25D);
        }

        if (defender.hasPotionEffect(PotionEffectType.BLINDNESS)) {
            ev.setDamage(ev.getDamage() * 2);
        }

        double dano = ev.getDamage();
        dano += lvlZona / 10;
        ev.setDamage(dano);
        if (!enemy.hasMetadata("deuHit")) {
            MetaShit.setMetaObject("deuHit", enemy, "1");
        }
        if (enemy.hasMetadata("bonusDano")) {
            ev.setDamage(ev.getDamage() + (int) MetaShit.getMetaObject("bonusDano", enemy));
        }
        if (enemy.getType() == EntityType.SPIDER || enemy.getType() == EntityType.CAVE_SPIDER) {
            if (Jobs.rnd.nextInt(25) == 1) {
                //BukkitListener.wizard.prendeEnt(defender);
                defender.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20, 1));
                enemy.teleport(defender.getLocation());
            }
        }

        // aplicando efeitos
        Object objEf = MetaShit.getMetaObject("efeitos", enemy);
        if (objEf != null) {
            HashSet<EfeitoMobs> efeitos = (HashSet) objEf;
            ev.setDamage(ev.getDamage() + efeitos.size());
            if (KoM.debugMode) {
                KoM.log.info("EFEITOS:" + efeitos.toString());
            }
            if (efeitos != null) {
                ev.setDamage(ev.getDamage() + (efeitos.size() * 1.2));
                if (efeitos.contains(EfeitoMobs.danoExtra)) {
                    ev.setDamage(ev.getDamage() * 1.5);
                }
                if (efeitos.contains(EfeitoMobs.bateFogo)) {
                    defender.setFireTicks(20 * 10);
                }
                if (efeitos.contains(EfeitoMobs.empurra)) {
                    Vector v = enemy.getLocation().getDirection().normalize().multiply(2.5);
                    v.setY(v.getY() + 0.3D);
                    defender.setVelocity(v);
                }
                if (efeitos.contains(EfeitoMobs.enfraquecedor)) {
                    defender.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 20 * 20, 3));
                }
                if (efeitos.contains(EfeitoMobs.envenena)) {
                    defender.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20 * (Jobs.rnd.nextInt(5) + 5), 1));
                }
                if (efeitos.contains(EfeitoMobs.slowa)) {
                    defender.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * (Jobs.rnd.nextInt(5) + 5), 4));
                }
                if (efeitos.contains(EfeitoMobs.cega)) {
                    defender.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 20, 3));
                }
                if (efeitos.contains(EfeitoMobs.fome)) {
                    defender.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 20 * 10, 1));
                }
            }
        }
        if (enemy.getType() == EntityType.WITCH || enemy.getType() == EntityType.WITHER || enemy.getType() == EntityType.WITHER_SKULL) {
            defender.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20 * 5, 1));
            defender.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 5, 1));
            defender.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 5, 1));
            defender.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 20 * 5, 1));
            defender.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 20 * 5, 1));
        } else if (enemy.getType() == EntityType.PIG_ZOMBIE) {
            Vector v = enemy.getLocation().getDirection().normalize().multiply(1.5);
            v.setY(v.getY() + 0.3D);
            defender.setVelocity(v);
        } else if (enemy.getType() == EntityType.ENDERMAN) {
            defender.setVelocity(new Vector(0, 4, 0));
        } else if (enemy.getType() == EntityType.ENDER_DRAGON) {
            ev.setDamage(ev.getDamage() * 3);
        }
        if (!KoM.mm.getMobManager().isActiveMob(enemy.getUniqueId())) {
            Clan aqui = ClanLand.getClanAt(defender.getLocation());
            if (aqui != null) {
                if (!Deuses.odio) {
                    defender.sendMessage(ChatColor.GREEN + L.m("Jabu retirou o monstro de sua guilda !"));
                    enemy.remove();
                }
                return;
            }
        }
    }

    public static void simpleKick(double veloCima, double forcaChute, Player kicker, Entity item) {
        Vector v = item.getLocation().toVector().subtract(kicker.getLocation().toVector()).normalize().multiply(forcaChute);
        v.setY(veloCima);
        item.setVelocity(v);
    }

    public static void kick(double veloCima, double forcaChute, Player kicker, Entity item) {
        if (kicker.isSneaking()) {
            veloCima += 1D;
            forcaChute -= 0.2D;
        } else if (kicker.isSprinting()) {
            forcaChute += 3D;
        }
        Vector v = item.getLocation().toVector().subtract(kicker.getLocation().toVector()).normalize().multiply(forcaChute);
        v.setY(veloCima);
        item.setVelocity(v);
    }

    public static void doRandomKnock(LivingEntity e, float power) {
        double varX = 0D;
        double varY = 0D;
        int rnd = Jobs.rnd.nextInt(3);
        if (rnd == 1) {
            varX = 0.5D;
        } else if (rnd == 2) {
            varX = -0.5D;
        }
        rnd = Jobs.rnd.nextInt(3);
        if (rnd == 1) {
            varY = 0.5D;
        } else if (rnd == 2) {
            varY = -0.5D;
        }
        e.setVelocity(new Vector(varX, 0.6D, varY).multiply(power));
    }

    public static void spawn(CreatureSpawnEvent ev, String tipo) {
        if (ev.getEntity().getType() == EntityType.PIG_ZOMBIE) {
            ((PigZombie) ev.getEntity()).setAngry(true);
            ev.getEntity().setMaxHealth(ev.getEntity().getMaxHealth() * 2);
            ev.getEntity().setHealth(ev.getEntity().getMaxHealth());
        } else if (ev.getEntity().getType() == EntityType.ENDER_DRAGON) {
            ev.getEntity().setMaxHealth(ev.getEntity().getMaxHealth() * 4);
            ev.getEntity().setHealth(ev.getEntity().getMaxHealth());
        }
        spawnaMob(ev, tipo);
    }

    public static void redstone(BlockRedstoneEvent ev) {
        if (KoM.debugMode) {
            KoM.log.info(ev.getBlock().toString());
        }
        if (ev.getBlock().getState() instanceof CreatureSpawner) {
            CreatureSpawner spwn = (CreatureSpawner) ev.getBlock().getState();
            Block b = spwn.getWorld().getBlockAt(spwn.getLocation());
            spwn.getWorld().spawnEntity(spwn.getLocation(), spwn.getSpawnedType());
        }
        if (ev.getBlock().getType() == Material.REDSTONE_TORCH_ON && ev.getBlock().getRelative(BlockFace.DOWN).getType() == Material.IRON_BLOCK) {
            if (KoM.debugMode) {
                KoM.log.info("NAO FUNFA !");
            }
            ev.getBlock().setType(Material.AIR);
            final Block b = ev.getBlock();
            Bukkit.getScheduler().scheduleSyncDelayedTask(KoM._instance,
                    new Runnable() {
                        @Override
                        public void run() {
                            b.setType(Material.REDSTONE_TORCH_ON);
                        }
                    }, 20 * 6);
            ev.setNewCurrent(0);
        }
    }

    public static void target(EntityTargetEvent ev) {
        if (ev.getTarget() != null && ev.getTarget().getType() == EntityType.PLAYER) {
            if (CapaInvisvel.ativos.contains(ev.getTarget().getUniqueId())) {
                ev.setCancelled(true);
            }
        }
    }

}
