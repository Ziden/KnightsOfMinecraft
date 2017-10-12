/*

 ╭╮╭━╮╱╱╭━╮╭━╮
 ┃┃┃╭╯╱╱┃┃╰╯┃┃
 ┃╰╯╯╭━━┫╭╮╭╮┃
 ┃╭╮┃┃╭╮┃┃┃┃┃┃
 ┃┃┃╰┫╰╯┃┃┃┃┃┃
 ╰╯╰━┻━━┻╯╰╯╰╯

 Desenvolvedor: ZidenVentania
 Colaboradores: NeT32, GabLootEventripj, Feldmann
 Patrocionio: InstaMC
 */
package nativelevel.Listeners;

import nativelevel.phatloots.PhatLoots;
import nativelevel.phatloots.events.MobDropLootEvent;
import nativelevel.phatloots.events.PlayerLootEvent;
import nativelevel.phatloots.events.PhatLootChestEvent;
import nativelevel.phatloots.events.PreMobDropLootEvent;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.StateFlag.State;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import nativelevel.utils.TitleAPI;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import me.asofold.bpl.simplyvanish.SimplyVanish;
import me.asofold.bpl.simplyvanish.config.VanishConfig;
import me.fromgate.playeffect.PlayEffect;
import me.fromgate.playeffect.VisualEffect;
import nativelevel.Auras.Aura;
import nativelevel.CFG;
import nativelevel.sisteminhas.ClanLand;
import nativelevel.Classes.Alchemy.Alchemist;
import nativelevel.Classes.Blacksmithy.Blacksmith;
import nativelevel.Classes.Engineer;
import nativelevel.Classes.Farmer;
import nativelevel.Classes.Lumberjack;
import nativelevel.Classes.Minerador;
import nativelevel.Classes.Paladin;
import nativelevel.Classes.Thief;
import nativelevel.Classes.Mage.Wizard;
import nativelevel.Comandos.Terreno;
import nativelevel.Custom.Buildings.Portal;
import nativelevel.Custom.CustomItem;
import nativelevel.Custom.ItemListener;
import nativelevel.Custom.Items.Armadilha;
import nativelevel.Custom.Items.Atadura;
import nativelevel.Custom.Items.CapaInvisvel;
import nativelevel.Custom.Items.Ponte;
import nativelevel.Custom.Items.Detonador;
import nativelevel.Custom.Items.Encaixe;
import nativelevel.Custom.Items.FolhaDeMana;
import nativelevel.Custom.Items.ItemRaroAleatorio;
import nativelevel.Custom.Items.LogoutTrap;
import nativelevel.Custom.Items.PedraDoPoder;
import nativelevel.Custom.Items.Runa;
import nativelevel.Custom.Items.SeguroDeItems;
import nativelevel.Custom.Items.SuperBomba;
import nativelevel.Dano;
import nativelevel.sisteminhas.Deuses;
import nativelevel.sisteminhas.Dungeon;
import static nativelevel.sisteminhas.Dungeon.LUZ_DO_ESCURO;
import nativelevel.ExecutaSkill;
import nativelevel.sisteminhas.Mobs;
import nativelevel.sisteminhas.Mobs.EfeitoMobs;
import nativelevel.Jobs;
import nativelevel.KoM;
import nativelevel.Lang.L;
import nativelevel.Language.LNG;
import nativelevel.Language.MSG;
import nativelevel.Language.TRL;
import nativelevel.Menu.Menu;
import nativelevel.Menu.netMenu;
import nativelevel.MetaShit;
import nativelevel.Attributes.AttributeInfo;
import nativelevel.Equipment.Generator.EquipGenerator;
import nativelevel.bencoes.TipoBless;
import nativelevel.gemas.Raridade;
import nativelevel.integration.BungeeCordKom;
import nativelevel.integration.SimpleClanKom;
import nativelevel.integration.WorldGuardKom;
import nativelevel.karma.Karma;
import nativelevel.rankings.Estatistica;
import nativelevel.rankings.RankDB;
import nativelevel.spec.PlayerSpec;
import nativelevel.sisteminhas.BookPortal;
import nativelevel.utils.BookUtil;
import nativelevel.utils.BungLocation;
import nativelevel.sisteminhas.IronGolem;
import nativelevel.sisteminhas.Lobo;
import nativelevel.lojaagricola.LojaAgricola;
import nativelevel.skills.Skill;
import nativelevel.skills.SkillMaster;
import nativelevel.sisteminhas.Horses;
import nativelevel.sisteminhas.XP;
import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import net.sacredlabyrinth.phaed.simpleclans.events.DisbandClanEvent;
import org.bukkit.Achievement;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BrewingStand;
import org.bukkit.block.Chest;
import org.bukkit.block.CommandBlock;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.block.Dropper;
import org.bukkit.block.Furnace;
import org.bukkit.block.Sign;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.craftbukkit.v1_12_R1.projectiles.CraftBlockProjectileSource;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Creature;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ThrownExpBottle;
import org.bukkit.entity.Fish;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Item;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.SmallFireball;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.Tameable;
import org.bukkit.entity.Vehicle;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Wolf;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockExpEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockIgniteEvent.IgniteCause;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.entity.ExpBottleEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityChangeBlockEvent;

import org.bukkit.event.entity.EntityCreatePortalEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.EntityTameEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.HorseJumpEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.PlayerLeashEntityEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SpawnEggMeta;
import org.bukkit.material.Button;
import org.bukkit.material.Cake;
import org.bukkit.material.Dispenser;
import org.bukkit.material.Door;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Openable;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerFishEvent;

public class GeneralListener implements Listener {

    public static HashMap<UUID, Projectile> pearls = new HashMap<UUID, Projectile>();

    @EventHandler
    public void adv(PlayerAdvancementDoneEvent ev) {
        KoM.log.info(ev.getAdvancement().toString());
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onEntityCreatePortalEvent(EntityCreatePortalEvent event) {
        if (event.getEntity() instanceof EnderDragon) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void blockFromToEvent(BlockFromToEvent event) {
        if (((event.getBlock().getType() == Material.WATER) || (event.getBlock().getType() == Material.STATIONARY_WATER))) {
            if (((event.getToBlock().getType() == Material.RED_MUSHROOM) || (event.getToBlock().getType() == Material.BROWN_MUSHROOM))) {
                event.setCancelled(true);
                event.getToBlock().setType(Material.AIR);
            }
            if (((event.getToBlock().getType() == Material.NETHER_WARTS) || (event.getToBlock().getType() == Material.PUMPKIN_STEM) || (event.getToBlock().getType() == Material.MELON_STEM) || (event.getToBlock().getType() == Material.CROPS) || (event.getToBlock().getType() == Material.CARROT) || (event.getToBlock().getType() == Material.POTATO))) {
                event.setCancelled(true);
                event.getToBlock().setType(Material.AIR);
            }
        }
        if (((event.getToBlock().getType() == Material.WATER) || (event.getToBlock().getType() == Material.STATIONARY_WATER))) {
            if (((event.getBlock().getType() == Material.RED_MUSHROOM) || (event.getBlock().getType() == Material.BROWN_MUSHROOM))) {
                event.setCancelled(true);
                event.getToBlock().setType(Material.AIR);
            }
            if (((event.getBlock().getType() == Material.NETHER_WARTS) || (event.getBlock().getType() == Material.PUMPKIN_STEM) || (event.getBlock().getType() == Material.MELON_STEM) || (event.getBlock().getType() == Material.CROPS) || (event.getBlock().getType() == Material.CARROT) || (event.getBlock().getType() == Material.POTATO))) {
                event.setCancelled(true);
                event.getToBlock().setType(Material.AIR);
            }
        }
    }

    @EventHandler
    public void projetil(ProjectileLaunchEvent ev) {
        if (ev.getEntity() instanceof EnderPearl) {
            pearls.put(((Entity) ev.getEntity().getShooter()).getUniqueId(), ev.getEntity());
        }
    }

    public void projectilBate(ProjectileHitEvent ev) {
        if (pearls.containsKey(((Entity) ev.getEntity().getShooter()).getUniqueId())) {
            pearls.remove(((Entity) ev.getEntity().getShooter()).getUniqueId());
        }
    }

    @EventHandler
    public void CommandBlock(ServerCommandEvent e) {
        if (e.getSender() instanceof CommandBlock) {
            CommandBlock bloco = (CommandBlock) e;
            KoM.log.info("CMDBLOCK: " + bloco.getCommand());
        }
    }

    public static String [] comeu = new String[]{"Voce comeu e ficou com o panduio cheio", "Eita comida boa", "Você se alimentou", "Você está satisfeito", "Tava uma delícia...", "A comida lhe alimentou bem", "Voce comeu a comida e se sujou todo", 
    "Voce comeu tudo com uma bocada só", "Você sente sua pança cheia", "Seu estomago faz um barulho estranho", "Voce se sente satisfeito", "A comida lhe satisfaz", "Voce da um tapinha na barriga cheia", "Que comida delicia", "Ooo delicia de comida", "O rango tava ótimo !", "Voce comeu tudo até o finalzinho", 
    "Ahh como comer é bom", "Eita pança cheia, delícia", "Voce comeu igual um porco", "Voce comeu com classe", "Voce comeu muito rapido", "Voce comeu devagar saboreando cada pedaço"};
    
    public static String [] barriga = new String[] {"Sua barriga deu uma roncada", "Voce ouve barulhos de sua barriga", "Sua barriga parece um barco velho contorcendo", "Voce sente muita fome", "Voce sente muito desejo de comer", "Parece que tem muito ar na sua barriga", "Voce precisa comer alguma coisa", "A fome está pegando", "Voce sente uma larica danada", 
        "Tudo q passa em sua mente agora é comida", "Voce gostaria muito de uma refeição", "Seu estomago ronca", "Seu estomago faz um barulho bizarro", "Seu estomago está vazio", "Voce precisa comer algo", "Sua fome está lhe deixando fraco"};
    
    @EventHandler
    public void onFoodChange(FoodLevelChangeEvent ev) {
        
        Block checkBlock = ev.getEntity().getWorld().getBlockAt(ev.getEntity().getLocation().getBlockX(), 0, ev.getEntity().getLocation().getBlockZ());
        if (checkBlock != null && checkBlock.getType() == Material.DIAMOND_BLOCK) {
            ev.setCancelled(true);
            return;
        }
        
        
        if (ev.getEntity() instanceof Player) {
            Player p = (Player)ev.getEntity();
            if (p.getLevel() <= 10) {
                ev.setFoodLevel(20);
                p.setFoodLevel(20);
                ev.setCancelled(true);
                return;
            }
            // se ta subindo a comida
            if(ev.getFoodLevel() > p.getFoodLevel()) {
                int diferenca = ev.getFoodLevel() - p.getFoodLevel();
                diferenca *= 2;
                int fim = p.getFoodLevel()+diferenca;
                if(fim > 20)
                    fim = 20;
                if(fim==20 && Jobs.rnd.nextInt(3)==1) {
                    p.sendMessage(ChatColor.GREEN+comeu[Jobs.rnd.nextInt(comeu.length)]);
                }
                ev.setFoodLevel(fim);
            } else {
                // se ta ficando com fome
                if(Jobs.rnd.nextBoolean()) {
                    ev.setCancelled(true);
                    return;
                }
                 
                if(ev.getFoodLevel() < 10) {
                     if(Jobs.rnd.nextInt(10)==1)
                        p.sendMessage(ChatColor.GREEN+barriga[Jobs.rnd.nextInt(barriga.length)]);
                }
            }
        }

   
      
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onProjectileHit(ProjectileHitEvent ev) {

        if (ev.getEntity().getType() == EntityType.FIREBALL) {
            double dano = 10;
            ClanPlayer cp = null;
            if (ev.getEntity().getShooter() != null && ((Entity) ev.getEntity().getShooter()).getType() == EntityType.PLAYER) {
                if (ev.getEntity().hasMetadata("modDano")) {
                    double mod = (double) MetaShit.getMetaObject("modDano", ev.getEntity());;
                    if (KoM.debugMode) {
                        KoM.log.info("Mod dano projectileHit firebola: " + mod);
                    }
                    dano *= mod;
                    //cp = Terrenos.manager.getClanPlayer(ev.getEntity().getUniqueId());
                }
            }
            PlayEffect.play(VisualEffect.EXPLOSION_HUGE, ev.getEntity().getLocation(), "num:1");

            if (!ev.getEntity().hasMetadata("NPC")) {
                for (Entity e : ev.getEntity().getNearbyEntities(5, 5, 5)) {
                    if (e instanceof LivingEntity) {
                        if (e.getType() == EntityType.PLAYER && ev.getEntity().getWorld().getName().equalsIgnoreCase("dungeon")) {
                            continue;
                        }
                        double danoBase = dano;
                        LivingEntity le = (LivingEntity) e;

                        if (e.getType() == EntityType.PLAYER) {
                            ClanPlayer t = ClanLand.manager.getAnyClanPlayer(((Player) e).getUniqueId());
                            if (t != null && cp != null && (t.getTag().equalsIgnoreCase(cp.getTag()) || t.isAlly(cp.toPlayer()))) {
                                continue;
                            }
                            //AttributeInfo info = KnightsOfMania.database.getAtributos((Player) le);
                            // danoBase *= 1 - Attributes.calcDamageAbsorbtion(info.attributes.get(Attr.tuffness));
                        }
                        le.damage(danoBase, (LivingEntity) ev.getEntity().getShooter());
                        le.setFireTicks(10);
                        if (le instanceof Player && (le.getLocation().getWorld().getName().equalsIgnoreCase("dungeon") || ClanLand.getTypeAt(le.getLocation()).equalsIgnoreCase("SAFE"))) {
                        } else {
                            Vector v = e.getLocation().toVector().subtract(ev.getEntity().getLocation().toVector()).normalize().multiply(1.8D);
                            v.setY(0.75d);
                            e.setVelocity(v);
                        }
                    }
                }
            }

        } else if (ev.getEntity().getType() == EntityType.ENDER_PEARL) {
        }
        ev.getEntity().remove();

    }

    @EventHandler
    public void onDispense(BlockDispenseEvent ev) {
        ItemStack stack = ev.getItem();

        if (stack == null || stack.getType() == Material.AIR) {
            return;
        }

        if (stack.getType() == Material.LAVA || stack.getType() == Material.LAVA_BUCKET) {
            ev.setCancelled(true);
        }

        if (ev.getBlock().getWorld().getName().equalsIgnoreCase("dungeon")) {

            Dispenser dispenser = (Dispenser) ev.getBlock().getState().getData(); //.getState() ?
            Block alvo = ev.getBlock().getRelative(dispenser.getFacing());
            Block timer = ev.getBlock().getRelative(dispenser.getFacing().getOppositeFace());

            org.bukkit.block.Dispenser dispBlock = (org.bukkit.block.Dispenser) ev.getBlock().getState();
            long lastSpawn = 0;
            long spawnTimer = 1000 * 60; // 1 min
            if (timer.getType() == Material.IRON_BLOCK) {
                if (KoM.debugMode) {
                    KoM.log.info("timer 10 min");
                }
                spawnTimer = spawnTimer *= 10; // 10 min
            }
            if (timer.getType() == Material.SPONGE) {
                if (KoM.debugMode) {
                    KoM.log.info("timer instant");
                }
                spawnTimer = 1; // instant
            }
            if (timer.getType() == Material.GOLD_BLOCK) {
                spawnTimer = spawnTimer *= (60 * 24); // 1 Day
                if (KoM.debugMode) {
                    KoM.log.info("timer 1 day");
                }
            }
            if (timer.getType() == Material.DIAMOND_BLOCK) {
                spawnTimer = spawnTimer *= (60 * 24 * 7); // 7 Days
                if (KoM.debugMode) {
                    KoM.log.info("timer 7 days");
                }
            }

            long current = System.currentTimeMillis();
            Inventory inventory = dispBlock.getInventory(); // You might have to use BlockInventory instead.
            if (KoM.debugMode) {
                KoM.log.info("items " + inventory.getContents().toString());
            }

            if (inventory.contains(Material.WRITTEN_BOOK)) {
                if (KoM.debugMode) {
                    KoM.log.info("has book");
                }
                for (ItemStack i : inventory.getContents()) {
                    if (i == null) {
                        continue;
                    }
                    if (i.getType() == Material.WRITTEN_BOOK) {
                        BookMeta m = (BookMeta) i.getItemMeta();
                        if (KoM.debugMode) {
                            KoM.log.info("found a book");
                        }
                        if (m.getPageCount() >= 1) {
                            lastSpawn = Long.valueOf(m.getPages().get(0));
                            if (KoM.debugMode) {
                                KoM.log.info("found a timer book");
                            }
                            if (lastSpawn + spawnTimer > current) {
                                if (KoM.debugMode) {
                                    KoM.log.info("spawning on cooldown");
                                }
                                ev.setCancelled(true);
                                return;
                            } else {
                                m.setPages(new String[]{current + ""});
                                i.setItemMeta(m);
                                if (KoM.debugMode) {
                                    KoM.log.info("updated spawn timer");
                                }
                            }
                        }
                        break;
                    }
                }
                ev.setCancelled(true);
                for (ItemStack i : inventory.getContents()) {
                    if (i != null) {
                        if (i.getType() == Material.MONSTER_EGG || i.getType() == Material.MONSTER_EGGS) {
                            if (KoM.debugMode) {
                                KoM.log.info("found 1 mob");
                            }
                            int qtd = i.getAmount();
                            ItemMeta m = i.getItemMeta();
                            int life = -1;
                            int level = 0;
                            int extraStrength = -1;
                            HashSet<EfeitoMobs> effects = new HashSet<EfeitoMobs>();
                            String nomeMob = null;
                            if (m != null) {
                                nomeMob = m.getDisplayName();
                                if (m.getLore() != null && m.getLore().size() > 0) {
                                    for (String l : m.getLore()) {
                                        String[] split = l.split(":");
                                        if (split.length == 2) {
                                            if (split[0].equalsIgnoreCase("vida")) { // life
                                                life = Integer.valueOf(split[1]);
                                            } else if (split[0].equalsIgnoreCase("dano")) { // strength
                                                extraStrength = Integer.valueOf(split[1]);
                                            } else if (split[0].equalsIgnoreCase("nivel")) { // level
                                                level = Integer.valueOf(split[1]);
                                            } else if (split[0].equalsIgnoreCase("efeitos")) { // effects
                                                String[] efs = split[1].split(" ");
                                                for (String ef : efs) {
                                                    EfeitoMobs efeito = Mobs.getEfeito(ef);
                                                    if (efeito != null) {
                                                        effects.add(efeito);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if (KoM.debugMode) {
                                KoM.log.info(qtd + " mobs with " + effects.size() + " level effects " + level + " with life " + life);
                            }

                            for (int x = 0; x < qtd; x++) {
                                SpawnEggMeta meta = (SpawnEggMeta) i.getItemMeta();
                                EntityType tipoMob = meta.getSpawnedType();
                                Location l = alvo.getLocation();
                                l.setX(l.getX() + 0.5);
                                l.setZ(l.getZ() + 0.5);
                                final LivingEntity mob = (LivingEntity) alvo.getWorld().spawnEntity(l, tipoMob);

                                if (KoM.debugMode) {
                                    KoM.log.info("spawning a " + tipoMob.toString());
                                }

                                if (nomeMob != null) {
                                    mob.setCustomName(nomeMob);
                                    mob.setCustomNameVisible(true);
                                }

                                if (life != -1) {
                                    life = life;
                                    final double mxLife = life;
                                    Runnable r = new Runnable() {
                                        public void run() {
                                            mob.setMaxHealth(mxLife);
                                            mob.setHealth(mob.getMaxHealth());
                                        }
                                    };
                                    Bukkit.getScheduler().scheduleSyncDelayedTask(plug, r, 20 * 3);
                                    mob.setMaxHealth(mxLife);
                                    mob.setHealth(mob.getMaxHealth());
                                    mob.setMetadata("vidaCustom", new FixedMetadataValue(KoM._instance, life));
                                }
                                if (extraStrength != -1) {
                                    MetaShit.setMetaObject("bonusDano", mob, extraStrength);
                                }
                                MetaShit.setMetaObject("nivel", mob, level);
                                MetaShit.setMetaObject("efeitos", mob, effects);
                                mob.setMetadata("mobCustom", new FixedMetadataValue(KoM._instance, "vida"));
                            }
                        }
                    }
                }
            }

        }
    }
    
   

    public static void givePlayerExperience(double quanto, Player p) {
        XP.changeExp(p, quanto, 1);
    }

    // o unico q precisa instanciar por causa do scheduler
    public static Wizard wizard = new Wizard();
    public static Minerador miner = new Minerador();
    private KoM plug;
    public static List<Material> pistaoNaoEmpurra = Arrays.asList(new Material[]{Material.SUGAR_CANE_BLOCK, Material.SUGAR_CANE, Material.CACTUS, Material.PUMPKIN, Material.NETHER_WARTS, Material.CAULDRON, Material.ANVIL, Material.FURNACE, Material.DISPENSER, Material.DROPPER, Material.SAND, Material.GRAVEL,Material.OBSERVER});
    public static HashMap<UUID, UUID> ultimoDano = new HashMap<UUID, UUID>();
    public static HashMap<UUID, List<ItemStack>> loots = new HashMap<UUID, List<ItemStack>>();
    public static Material gambiarra = Material.REDSTONE_BLOCK;

    public GeneralListener(KoM plugin) {
        plug = plugin;
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public static final int SEGUNDOS_COMBATE = 10;

    public static boolean taPelado(Player p) {
        if (p.getInventory().getBoots() != null && p.getInventory().getBoots().getType() != Material.AIR) {
            return false;
        }
        if (p.getInventory().getHelmet() != null && p.getInventory().getHelmet().getType() != Material.AIR) {
            return false;
        }
        if (p.getInventory().getChestplate() != null && p.getInventory().getChestplate().getType() != Material.AIR) {
            return false;
        }
        if (p.getInventory().getLeggings() != null && p.getInventory().getLeggings().getType() != Material.AIR) {
            return false;
        }
        return true;
    }

    public static int taEmCombate(Player p) {
        if (p.hasMetadata("tempoDano")) {
            long tomouEm = (Long) MetaShit.getMetaObject("tempoDano", p);
            long agora = System.currentTimeMillis() / 1000;
            if (tomouEm + SEGUNDOS_COMBATE > agora) {
                return (int) ((tomouEm + SEGUNDOS_COMBATE) - agora);
            }
        }
        return 0;
    }

    @EventHandler
    public void mudaBloco(EntityChangeBlockEvent ev) {
        if (ev.getEntity().getType() == EntityType.ENDERMAN) {
            ev.setCancelled(true);
        }
    }

    @EventHandler
    public void xplod(EntityExplodeEvent ev) {
        if (!SuperBomba.explosivos.containsKey(ev.getLocation().getBlock())) {
            ev.blockList().clear();
        } else {
            List<Block> blocks = new ArrayList<Block>();
            for (Block b : ev.blockList()) {
                if (SuperBomba.podemExplodir.contains(b.getType())) {
                    b.breakNaturally();
                }
            }
            ev.blockList().clear();
            //ev.blockList().addAll(blocks);
            //ev.blockList().removeAll(removidos);
        }

        if (ev.getEntity().hasMetadata("trap")) {
            for (Entity e : ev.getEntity().getNearbyEntities(8, 8, 8)) {
                if (e instanceof LivingEntity) {
                    double distancia = e.getLocation().distance(ev.getEntity().getLocation());
                    double dano = 30 - (distancia * 3);
                    ((LivingEntity) e).damage(dano);
                }

            }
        }
    }

    @EventHandler
    public void poeColeira(PlayerLeashEntityEvent ev) {

        if (ev.getEntity() instanceof Tameable) {
            Tameable bixo = (Tameable) ev.getEntity();

            if (bixo.getOwner() != null && bixo.getOwner().getName().equalsIgnoreCase(ev.getPlayer().getName())) {
                return;
            }

            int lvl = Jobs.getJobLevel("Fazendeiro", ev.getPlayer());
            int dificuldade = 85;
            int sucesso = Jobs.hasSuccess(dificuldade, "Fazendeiro", ev.getPlayer());
            if (sucesso != Jobs.success) {
                ev.getPlayer().sendMessage(ChatColor.RED + L.m("Voce falhou em colocar a coleira no animal !"));
                ev.setCancelled(true);
                if (ev.getPlayer().getItemInHand().getAmount() == 1) {
                    ev.getPlayer().setItemInHand(null);
                } else {
                    ev.getPlayer().getItemInHand().setAmount(ev.getPlayer().getItemInHand().getAmount() - 1);
                }
            }
        }
    }

    private boolean ehIgual(Location l, Location l2) {
        if (l2.getBlockX() != l.getBlockX()) {
            return false;
        }
        if (l2.getBlockY() != l.getBlockY()) {
            return false;
        }
        if (l2.getBlockY() != l.getBlockY()) {
            return false;
        }
        return true;
    }

    @EventHandler
    public void quandoGospe(BlockDispenseEvent e) {
        if (e.getBlock().getState() instanceof Dropper) {
            Dropper dropper = (Dropper) e.getBlock().getState();

            List lista = PhatLoots.getPhatLoots(e.getBlock());
            if (lista != null && lista.size() > 0) {
                return;
            }

            if (dropper.getInventory().getName().contains("§8Cooldown")) {
                e.setCancelled(true);
                if (dropper.getInventory().getItem(4) != null) {
                    ItemStack item = dropper.getInventory().getItem(4);
                    if (item.getType() == Material.BOOK_AND_QUILL || item.getType() == Material.WRITTEN_BOOK) {
                        int LocX = dropper.getLocation().getBlockX();
                        int LocY = dropper.getLocation().getBlockY();
                        int LocZ = dropper.getLocation().getBlockZ();
                        Location blocoDeRed = new Location(dropper.getWorld(), LocX, LocY - 2, LocZ);
                        BookMeta livro = (BookMeta) item.getItemMeta();
                        long longtempoA = System.currentTimeMillis() / 1000;
                        String tempoA = Long.toString(longtempoA);
                        if (livro.hasPages() == false) {
                            livro.addPage("5");
                            item.setItemMeta(livro);
                        }
                        String Pag1 = livro.getPage(1);
                        if (livro.getDisplayName() == null || !livro.getDisplayName().matches("^[0-9]+")) {
                            livro.setDisplayName(tempoA);
                            item.setItemMeta(livro);
                            blocoDeRed.getBlock().setType(Material.REDSTONE_BLOCK);
                        }
                        long tempoG = Long.valueOf(livro.getDisplayName());
                        long CD = tempoG + (Long.valueOf(Pag1) * 60);
                        if (longtempoA >= CD) {
                            livro.setDisplayName(tempoA);
                            item.setItemMeta(livro);
                            blocoDeRed.getBlock().setType(Material.REDSTONE_BLOCK);
                        }
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void loot1(PreMobDropLootEvent e) {
        if (e.getMob().getType() == EntityType.PLAYER) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void loot2(MobDropLootEvent e) {
        if (e.getMob().getType() == EntityType.PLAYER) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void furnace(FurnaceExtractEvent ev) {
        if (ev.getBlock().getType() == Material.FURNACE) {
            ev.setExpToDrop(0);
        }
    }

    @EventHandler
    public void xpBloco(BlockExpEvent ev) {
        if (ev.getBlock().getType() == Material.FURNACE) {
            ev.setExpToDrop(0);
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void atiraFlecha(final EntityShootBowEvent ev) {
        if (ev.getEntity() instanceof Player && ev.getProjectile() instanceof Arrow && !ev.getProjectile().hasMetadata("zaraba")) {
            Player p = (Player) ev.getEntity();
            MetaShit.setMetaObject("modDano", ev.getProjectile(), 1d);
            Thief.atiraFlecha(p, (Projectile) ev.getProjectile());
        }
    }

    public HashMap<UUID, Integer> bottles = new HashMap<UUID, Integer>();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void atiraCoisa(ProjectileLaunchEvent ev) {
        if (ev.getEntity() instanceof ThrownExpBottle) {
            if (ev.getEntity().getShooter() instanceof Player) {
                Player atirador = (Player) ev.getEntity().getShooter();
                ItemStack mao = atirador.getItemInHand();
                ItemMeta meta = mao.getItemMeta();
                List<String> lore = meta.getLore();
                if (lore != null && lore.size() > 1) {
                    int exp = Integer.valueOf(ChatColor.stripColor(lore.get(1).trim()));
                    bottles.put(ev.getEntity().getUniqueId(), exp);
                }
            }
        }

        // Dispensers fodões
        ProjectileSource atirador = ev.getEntity().getShooter();
        if (atirador == null) {
            KoM.debug("ATIRADOR NULL");
        } else {
            KoM.debug("Atirador class " + atirador.getClass());
            if (atirador instanceof Block) {
                KoM.debug("atirador bloco");
            } else if (atirador instanceof BlockDispenseEvent) {
                KoM.debug("atirador eh um evento");
            }
        }
        if (atirador instanceof CraftBlockProjectileSource) {
            Dispenser dispenser = (Dispenser) atirador;
            KoM.log.info("Dispenser atirando algo");
            if (ev.getEntity().getType() == EntityType.ARROW) {
                MetaShit.setMetaObject("modDano", ev.getEntity(), 3.5);
            } else if (ev.getEntity().getType() == EntityType.PRIMED_TNT) {
                TNTPrimed tnt = (TNTPrimed) ev.getEntity();
                tnt.setFuseTicks(35);
                MetaShit.setMetaObject("trap", tnt, true);
            } else if (ev.getEntity().getType() == EntityType.FIREBALL) {
                MetaShit.setMetaObject("modDano", ev.getEntity(), 2.5);
            }
        }

    }

    @EventHandler
    public void garrafa(ExpBottleEvent ev) {
        if (bottles.containsKey(ev.getEntity().getUniqueId())) {
            int exp = bottles.get(ev.getEntity().getUniqueId());
            bottles.remove(ev.getEntity().getUniqueId());
            ev.setExperience(exp);
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void domaLobo(EntityTameEvent event) {
        Farmer.tameWolf(event);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void estouraPotion(PotionSplashEvent event) {
        Alchemist.splashPotion(event);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void fire(BlockIgniteEvent ev) {
        if (ev.getCause() == IgniteCause.FIREBALL || ev.getCause() == IgniteCause.SPREAD) {
            ev.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBurn(BlockBurnEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void DisbandClan(final DisbandClanEvent event) {
        ClanLand.limpaGuildaEDesfaz(event.getClan().getTag(), false);
        ClanLand.setPoder(event.getClan().getTag(), 0);
    }

    @EventHandler
    public void onBlockSpread(BlockSpreadEvent event) {
        if (event.getBlock().getType() == Material.FIRE) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void unload(ChunkUnloadEvent ev) {
        for (Entity e : ev.getChunk().getEntities()) {
            if (e.getType() == EntityType.WOLF && ((Wolf) e).getOwner() != null) {
                e.remove();
            }
        }
    }

    public boolean isItemSeguro(ItemStack ss) {
        if (ss == null) {
            return false;
        }
        if (ss.getType() == Material.MONSTER_EGG || ss.getType() == Material.MONSTER_EGGS) {
            return true;
        }
        return false;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void load(ChunkLoadEvent ev) {
        KoM.generator.load(ev);
        if (ev.isNewChunk()) {
            ev.getChunk().getBlock(5, 0, 5).setType(gambiarra);
            ev.getChunk().getBlock(5, 1, 5).setType(Material.BEDROCK);
            return;
        }
        if (ev.getChunk().getWorld().getName().equalsIgnoreCase(CFG.mundoGuilda) && ev.getChunk().getBlock(5, 0, 5).getType() != gambiarra) {

            if (ev.getChunk().getBlock(5, 0, 5).hasMetadata("temporegen")) {
                long tempo = (Long) MetaShit.getMetaObject("temporegen", ev.getChunk().getBlock(5, 0, 5));
                long agora = System.currentTimeMillis() / 1000;
                if (agora < tempo) {
                    return;
                }
            }

            ev.getChunk().getBlock(5, 0, 5).setType(gambiarra);
            ev.getChunk().getBlock(5, 1, 5).setType(Material.BEDROCK);
            String type = ClanLand.getTypeAt(ev.getChunk().getBlock(0, 0, 0).getLocation());
            if (type.equalsIgnoreCase("WILD")) {
                //ClanLand.removeClanAt(ev.getChunk().getBlock(0, 0, 0).getLocation());
        
                boolean guildaProxima = Terreno.temGuildaPerto(null, ev.getChunk().getBlock(0, 0, 0).getLocation());
                
                if(!guildaProxima) {
                    ev.getChunk().getWorld().regenerateChunk(ev.getChunk().getX(), ev.getChunk().getZ());
                }

                ev.getChunk().getBlock(5, 0, 5).setType(gambiarra);
                ev.getChunk().getBlock(5, 1, 5).setType(Material.BEDROCK);
            } else {
                ev.getChunk().getBlock(5, 0, 5).setType(gambiarra);
                ev.getChunk().getBlock(5, 1, 5).setType(Material.BEDROCK);
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void mudaPlaca(final SignChangeEvent evt) {
        if (!evt.getPlayer().isOp() && evt.getLine(0) != null && evt.getLine(0).equalsIgnoreCase("[server]")) {
            evt.getPlayer().sendMessage(ChatColor.RED + L.m("Sem chance rapaiz..."));
            evt.setCancelled(true);
            return;
        }
    }
    
    @EventHandler
    public void advancement(PlayerAdvancementDoneEvent ev) {
        
    }

    @EventHandler
    public void loota(PlayerLootEvent ev) {

        if (!ev.getLooter().hasPermission("kom.pegarbaus")) {
            ev.setCancelled(true);
            return;
        }

        if (ev.getPhatLoot().name.equalsIgnoreCase("lvl3") || ev.getPhatLoot().name.equalsIgnoreCase("lvl4") || ev.getPhatLoot().name.equalsIgnoreCase("lvl5")) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.sendMessage(ChatColor.BLUE + L.m("[Dungeon]" + ChatColor.GREEN + " O Jogador " + ChatColor.YELLOW + "%" + ChatColor.GREEN + " acaba de pegar um tesouro raro ! ", ev.getLooter().getName()));
            }
        }

        int level = ClanLand.getMobLevel(ev.getLooter().getLocation()) * 5;

        if (ev.getPhatLoot().name.contains("lvl")) {
            double pctItems = level + 10;

            int money = 1;
            KoM.debug("Dropando " + pctItems + "% do bau q tem " + ev.getItemList().size() + " items");

            double totalItems = ev.getItemList().size();

            totalItems *= (pctItems / 100d);

            KoM.debug("Vou dropar " + (int) totalItems + " items");

            if (totalItems < 0) {
                totalItems = 1;
            }

            Collections.shuffle(ev.getItemList());

            List<ItemStack> dropados = new ArrayList<ItemStack>();

            for (int x = 0; x < ev.getItemList().size(); x++) {
                if (x < totalItems) {
                    dropados.add(ev.getItemList().get(x));
                }
            }

            ev.getItemList().clear();
            for (ItemStack ss : dropados) {
                ev.getItemList().add(ss);
            }

            int xp = XP.getExpPorAcao(level) * 25;
            int pontos = 0;
            if (ev.getPhatLoot().name.equalsIgnoreCase("lvl1")) {
                pontos = 1;
                money = 10;
                if (Jobs.rnd.nextBoolean()) {
                    ev.getItemList().add(EquipGenerator.gera(Raridade.Comum, level));
                } else {
                    if (Jobs.rnd.nextInt(10) == 1) {
                        ev.getItemList().add(EquipGenerator.gera(level));
                    }
                }
            }

            if (ev.getPhatLoot().name.equalsIgnoreCase("lvl2")) {
                pontos = 3;
                money = 35;
                xp *= 3;
                ev.getItemList().add(EquipGenerator.gera(Raridade.Comum, level));
                if (Jobs.rnd.nextBoolean()) {
                    ev.getItemList().add(EquipGenerator.gera(Raridade.Incomum, level));
                } else {
                    if (Jobs.rnd.nextInt(5) == 1) {
                        ev.getItemList().add(EquipGenerator.gera(level));
                    }
                }
            }

            if (ev.getPhatLoot().name.equalsIgnoreCase("lvl3")) {
                pontos = 5;
                xp *= 10;
                money = 100;
                ev.getItemList().add(EquipGenerator.gera(Raridade.Incomum, level));
                ev.getItemList().add(EquipGenerator.gera(Raridade.Incomum, level));
                ev.getItemList().add(EquipGenerator.gera(Raridade.Incomum, level));
                ev.getItemList().add(EquipGenerator.gera(Raridade.Comum, level));
                ev.getItemList().add(EquipGenerator.gera(level));
                ev.getItemList().add(EquipGenerator.gera(level));
                ev.getItemList().add(EquipGenerator.gera(level));
                ev.getItemList().add(EquipGenerator.gera(level));
                if (Jobs.rnd.nextBoolean()) {
                    ev.getItemList().add(EquipGenerator.gera(Raridade.Raro, level));
                }

            }

            if (ev.getPhatLoot().name.equalsIgnoreCase("lvl4")) {
                pontos = 7;
                xp *= 20;
                money = 200;
                ev.getItemList().add(EquipGenerator.gera(level));
                ev.getItemList().add(EquipGenerator.gera(level));
                ev.getItemList().add(EquipGenerator.gera(Raridade.Raro, level));
            }

            if (ev.getPhatLoot().name.equalsIgnoreCase("lvl5")) {
                pontos = 9;
                xp *= 20;
                money = 500;
                ev.getItemList().add(EquipGenerator.gera(level));
                ev.getItemList().add(EquipGenerator.gera(level));
                ev.getItemList().add(EquipGenerator.gera(Raridade.Raro, level));
                ev.getItemList().add(EquipGenerator.gera(Raridade.Raro, level));
                ev.getItemList().add(EquipGenerator.gera(Raridade.Raro, level));
            }

            money = Jobs.rnd.nextInt(money / 2) + money / 2;
            
            double ratioMoney = level/100d;
            
            money = (int)Math.ceil(money * ratioMoney);
            
            ev.getItemList().add(new ItemStack(Material.EMERALD, money));

            if (xp > 0) {
                Player p = ev.getLooter();
                int xpGeral = xp;
                int lvl = p.getLevel();
                if (lvl - 25 > level) {
                    xp /= 20;
                    p.sendMessage(ChatColor.RED + "Você está muito fraco para este baú e ganhou menos XP");
                } else if (lvl + 25 < level) {
                    xp /= 20;
                    p.sendMessage(ChatColor.RED + "Você está muito forte para este baú e ganhou menos XP");
                }
                GeneralListener.givePlayerExperience(xp, p);
            }

            if (pontos != 0) {
                RankDB.addPontoCache(ev.getLooter(), Estatistica.DUNGEONS, pontos);
            }

        }

    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void encanta(EnchantItemEvent event) {
        Wizard.enchants(event);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void cmdblockActivate(BlockRedstoneEvent e) {
        if (e.getBlock().getType().equals(Material.COMMAND)) { //Check if block is command
            if (!(e.getNewCurrent() == 0) && e.getOldCurrent() == 0) { //Check if block was not activated already
                Block b1 = e.getBlock();
                final CommandBlock b2 = (CommandBlock) b1.getState();
                final String command = b2.getCommand();
                if (KoM.debugMode) {
                    KoM.log.info("Comandblock ativado em " + e.getBlock().getLocation().getX() + "/" + e.getBlock().getLocation().getZ());
                }
            }
        }
    }

    @EventHandler
    public void onLeafDecay(LeavesDecayEvent event) {
        if (event.getBlock().getWorld().getName().equalsIgnoreCase("dungeon") || event.getBlock().getWorld().getName().equalsIgnoreCase("vila")) {
            event.setCancelled(true);
            return;
        } else if (ClanLand.getTypeAt(event.getBlock().getLocation()).equalsIgnoreCase("SAFE") || WorldGuardKom.ehSafeZone(event.getBlock().getLocation())) {
            event.setCancelled(true);
        }
    }

}
