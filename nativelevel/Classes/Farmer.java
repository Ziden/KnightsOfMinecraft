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

import nativelevel.Classes.Blacksmithy.Blacksmith;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import me.fromgate.playeffect.PlayEffect;
import me.fromgate.playeffect.VisualEffect;
import nativelevel.Equipment.Generator.EquipGenerator;
import nativelevel.Listeners.GeneralListener;
import nativelevel.sisteminhas.Mobs;
import nativelevel.Jobs;
import nativelevel.Lang.PT;
import nativelevel.Menu.Menu;
import nativelevel.MetaShit;
import nativelevel.KoM;
import nativelevel.sisteminhas.ClanLand;
import nativelevel.Custom.CustomItem;
import nativelevel.Custom.Items.FolhaDeMana;
import nativelevel.CustomEvents.BlockHarvestEvent;
import nativelevel.CustomEvents.BlockPlantEvent;
import nativelevel.CustomEvents.FinishCraftEvent;
import nativelevel.Lang.L;
import nativelevel.Language.MSG;
import nativelevel.Menu.MenuItem;
import nativelevel.Attributes.Mana;
import nativelevel.Attributes.Stamina;
import nativelevel.Jobs.TipoClasse;
import nativelevel.Lang.LangMinecraft;
import nativelevel.bencoes.TipoBless;
import nativelevel.config.ConfigKom;
import nativelevel.config.ItemJob;
import nativelevel.gemas.Raridade;
import nativelevel.integration.SimpleClanKom;
import nativelevel.integration.WorldGuardKom;
import nativelevel.rankings.Estatistica;
import nativelevel.rankings.RankDB;
import nativelevel.spec.PlayerSpec;
import nativelevel.sisteminhas.Horses;
import nativelevel.sisteminhas.KomSystem;
import nativelevel.sisteminhas.Tralhas;
import nativelevel.sisteminhas.XP;
import nativelevel.utils.Cooldown;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.CropState;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NetherWartsState;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Horse;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.MushroomCow;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.entity.Rabbit;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Tameable;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityTameEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFactory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SpawnEggMeta;
import org.bukkit.material.Crops;
import org.bukkit.material.NetherWarts;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Farmer extends KomSystem {

    public static void tameWolf(EntityTameEvent event) {
        int jobLvl = Jobs.getJobLevel("Fazendeiro", ((Player) event.getOwner()));
        if (jobLvl == 0 || (Jobs.rnd.nextInt(5) != 1)) {
            ((Player) event.getOwner()).sendMessage(ChatColor.AQUA + Menu.getSimbolo("Fazendeiro") + " " + ChatColor.RED + L.m("Voce falhou em domesticar o animal!"));
            if (event.getEntity() instanceof Wolf) {
                ((Wolf) event.getEntity()).setAngry(true);
                ((Wolf) event.getEntity()).setTamed(false);
            }
            event.setCancelled(true);
        }
    }

    public static void poisonaSlimeball(PlayerPickupItemEvent ev) {

        if (!ev.getPlayer().getWorld().getName().equalsIgnoreCase("dungeon") && Jobs.getJobLevel("Fazendeiro", ev.getPlayer()) != 1) {
            ev.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 3));
            ev.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.POISON, 200, 3));
            ev.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 0));
            PlayEffect.play(VisualEffect.SPELL_MOB, ev.getPlayer().getLocation(), "num:2");
            ev.getItem().remove();
            ev.getPlayer().sendMessage(ChatColor.AQUA + Menu.getSimbolo("Fazendeiro") + " " + ChatColor.RED + L.m("Voce foi envenenado por uma slimeball envenenada e está decompondo!"));
            ev.getPlayer().playSound(ev.getPlayer().getLocation(), Sound.ENTITY_PLAYER_BURP, 10, 0);
            Thief.revela(ev.getPlayer());
            ev.setCancelled(true);
        } else {
            if (ev.getPlayer().getLevel() >= 9) {
                if (!ev.getPlayer().isSneaking()) {
                    double veloCima = 0.3D;
                    double forcaChute = 2D;
                    Mobs.kick(veloCima, forcaChute, ev.getPlayer(), ev.getItem());
                    ev.setCancelled(true);
                }
            }
        }
    }

    public static void cortaFolha(Player p, Block folha) {
        if (folha.hasMetadata("jogadorpois")) {
            return;
        }
        if (p.getItemInHand() != null && p.getItemInHand().getType() == Material.SHEARS) {
            return;
        }
        int level = Jobs.getJobLevel("Fazendeiro", p);
        if (level == 1 && Jobs.rnd.nextBoolean()) {
            folha.getWorld().dropItemNaturally(folha.getLocation(), CustomItem.getItem(FolhaDeMana.class).generateItem());
            GeneralListener.givePlayerExperience(2, p);
            if (Jobs.rnd.nextInt(3) == 1) {
                folha.getWorld().dropItemNaturally(folha.getLocation(), new ItemStack(Material.APPLE));
            }
        } else if (level == 2 && Jobs.rnd.nextInt(10) == 1) {
            folha.getWorld().dropItemNaturally(folha.getLocation(), CustomItem.getItem(FolhaDeMana.class).generateItem());
            GeneralListener.givePlayerExperience(2, p);
        }
    }
    public static final Enchantment[] enchantsRandom = {Enchantment.LOOT_BONUS_MOBS, Enchantment.DURABILITY, Enchantment.LOOT_BONUS_MOBS, Enchantment.PROTECTION_EXPLOSIONS, Enchantment.WATER_WORKER, Enchantment.PROTECTION_ENVIRONMENTAL, Enchantment.PROTECTION_PROJECTILE, Enchantment.PROTECTION_FIRE};

    public static void blockHarvest(BlockHarvestEvent ev) {

    }

    @EventHandler
    public static void interageCavalo(PlayerInteractEntityEvent ev) {
        
         if (ev.getRightClicked() != null && ev.getRightClicked().getType() == EntityType.DONKEY) {
             ev.setCancelled(true);
             return;
         }
        
        
        if (ev.getRightClicked() != null && ev.getRightClicked().getType() == EntityType.HORSE) {
            Horse h = (Horse) ev.getRightClicked();
            if (h.getOwner() != null && h.getOwner().getUniqueId() == ev.getPlayer().getUniqueId()) {
                if (h.getPassengers() == null) {
                    ItemStack ovo = Horses.getCavalo(h);
                    ev.setCancelled(true);
                    ev.getRightClicked().remove();
                    ev.getPlayer().getInventory().addItem(ovo);
                }
            }
        }
    }

    /*
    
     FAZER COLHEITA
     
     */
    public static void depoisCraft(FinishCraftEvent ev) {
        Player p = ev.getPlayer();
        Material feitoDe = Blacksmith.getToolLevel(ev.getResult().getType());
        if (feitoDe == Material.LEATHER) {

            int chanceExp = ev.getPlayer().getLevel();

            LeatherArmorMeta lMeta = (LeatherArmorMeta) ev.getResult().getItemMeta();
            KoM.debug("fazendo armor lea");
            if (lMeta.getColor() == Bukkit.getItemFactory().getDefaultLeatherColor()) {

                int chanceExceptional = 5 + (ev.getPlayer().getLevel() / 2);

                if (Jobs.getJobLevel(Jobs.Classe.Fazendeiro, p) == TipoClasse.PRIMARIA) {
                    if (Jobs.rnd.nextInt(100) < chanceExceptional) {
                        Raridade rar = Raridade.Comum;
                        int sorte = Jobs.rnd.nextInt(ev.getPlayer().getLevel());
                        if (sorte == 99) {
                            rar = Raridade.Epico;
                        } else if (sorte > 65) {
                            rar = Raridade.Raro;
                        } else if (sorte > 20) {
                            rar = Raridade.Incomum;
                        }
                        ItemStack item = ev.getResult();
                        EquipGenerator.gera(item, rar);
                        ItemMeta meta = item.getItemMeta();
                        meta.setDisplayName(rar.getIcone() + " " + LangMinecraft.get().get(item) + " Excepcional");
                        item.setItemMeta(meta);
                        item.addEnchantment(Enchantment.DURABILITY, 3);
                        ev.getPlayer().sendMessage(ChatColor.GREEN + L.m("Voce fez uma armadura Excepcional !"));
                    } else {
                        p.sendMessage(ChatColor.AQUA + Menu.getSimbolo("Fazendeiro") + " " + ChatColor.GOLD + L.m("Voce fez uma armadurade couro !"));
                    }
                } else {
                    p.sendMessage(ChatColor.AQUA + Menu.getSimbolo("Fazendeiro") + " " + ChatColor.GOLD + L.m("Voce fez uma armadurade couro !"));
                }
            } else {
                if (Jobs.rnd.nextInt(3) == 1) {
                    for (int x = 0; x < 2; x++) {
                        try {
                            if (Mana.spendMana(p, 5)) {
                                Enchantment e = enchantsRandom[Jobs.rnd.nextInt(enchantsRandom.length)];
                                if (!ev.getResult().containsEnchantment(e)) {
                                    int srt = Jobs.rnd.nextInt(100);
                                    int level = 1;
                                    if (srt == 1) {
                                        level = 4;
                                    } else if (srt > 1 && srt < 3) {
                                        level = 3;
                                    } else if (srt > 3 && srt < 50) {
                                        level = 2;
                                    }
                                    ev.getResult().addEnchantment(e, level);
                                }
                            }
                        } catch (Exception e) {
                            KoM.log.info("tentei adicionar o encantamento " + e.toString() + " no item " + ev.getResult().getType().name() + " e falhei...");
                        }
                    }
                    p.sendMessage(ChatColor.AQUA + Menu.getSimbolo("Fazendeiro") + " " + ChatColor.GOLD + L.m("Voce pintou a armadura !"));
                } else {
                    p.sendMessage(ChatColor.AQUA + Menu.getSimbolo("Fazendeiro") + " " + ChatColor.GOLD + L.m("Voce falhou em pintar a armadura !"));
                    lMeta.setColor(Bukkit.getItemFactory().getDefaultLeatherColor());
                }

            }
        } else {
            p.sendMessage(Menu.getSimbolo("Fazendeiro") + " " + ChatColor.GOLD + PT.craft_sucess);
        }
        ItemMeta meta = ev.getResult().getItemMeta();
        List<String> lore = meta.getLore();
        if (lore == null) {
            lore = new ArrayList<String>();
        }
        ev.getResult().setItemMeta(meta);
    }

    public static void handleEgg(final PlayerInteractEvent ev) {

        if (ev.getAction() == Action.PHYSICAL) {
            return;
        }

        if (ev.getHand() == EquipmentSlot.OFF_HAND) {
            ev.setCancelled(true);
            return;
        }

        SpawnEggMeta meta = (SpawnEggMeta) ev.getPlayer().getItemInHand().getItemMeta();

        KoM.debug("hangleEgg: " + ev.getPlayer().getItemInHand().getType().name() + " - " + ev.getPlayer().getItemInHand().getDurability());

        if (ClanLand.getTypeAt(ev.getPlayer().getLocation()).equalsIgnoreCase("SAFE") && meta.getSpawnedType() != EntityType.HORSE) {
            ev.getPlayer().sendMessage(ChatColor.AQUA + Menu.getSimbolo("Fazendeiro") + " " + L.m("Voce nao pode fazer isto em uma Cidade"));
            ev.setCancelled(true);
            return;
        }

        if (!ev.getPlayer().isOp() && ev.getPlayer().getWorld().getName().equalsIgnoreCase("dungeon")) {
            ev.getPlayer().sendMessage(ChatColor.RED + L.m("Voce nao pode botar ovos aqui !"));
            ev.setCancelled(true);
            return;
        }

        // villager
        /*
         if (ev.getPlayer().getItemInHand().getDurability() == 120 && ev.getClickedBlock() != null) {
         String aqui = ClanLand.getTypeAt(ev.getClickedBlock().getLocation());
         if (!aqui.equalsIgnoreCase("Safe")) {
         ev.getPlayer().sendMessage(L.m("Voce nao pode fazer isto aqui !"));
         ev.setCancelled(true);
         return;
         }
         HashSet<Material> m = null;
         if (!SimpleClanKom.canBuild(ev.getPlayer(), ev.getPlayer().getTargetBlock(m, 20).getLocation(), ev.getPlayer().getTargetBlock(m, 20), false)) {
         ev.getPlayer().sendMessage(ChatColor.RED + L.m("Voce só pode criar um lojista na sua casa !"));
         ev.setCancelled(true);
         return;
         }
         }
         */
        if (meta == null) {
            return;
        }
        if (meta.getSpawnedType() == EntityType.WOLF) {
            ev.setCancelled(true);
            //cavalo
        } else if (meta.getSpawnedType() == EntityType.HORSE) {
            Horses.takeOutFromEgg(ev);
        }

    }

    public static void pesca(PlayerFishEvent ev) {
        if (ev.getPlayer().getWorld().getName().equalsIgnoreCase("dungeon")) {
            return;
        }

        if (Jobs.getJobLevel("Fazendeiro", ev.getPlayer()) != 1) {
            return;
        }

        if (ev.getCaught() != null) {
            if (!ev.getCaught().getWorld().getName().equalsIgnoreCase(ev.getPlayer().getWorld().getName())) {
                return;
            }

            if (ev.getCaught().getLocation().distance(ev.getPlayer().getLocation()) > 20) {
                return;
            }
            if (Stamina.spendStamina(ev.getPlayer(), 50)) {
                if (ev.getCaught() instanceof Player) {
                    ev.getPlayer().sendMessage(ChatColor.GREEN + L.m("Voce puxou a linha da vara"));
                }
                ev.getCaught().teleport(ev.getPlayer());
            }
        } else {

            if (ev.getPlayer().hasMetadata("hook")) {

                KoM.log.info("HOOK");

                UUID hookado = (UUID) MetaShit.getMetaObject("hook", ev.getPlayer());
                ev.getPlayer().removeMetadata("hook", KoM._instance);

                ev.getPlayer().sendMessage(ChatColor.RED + L.m("Voce puxou a linha da vara"));

                ev.setCancelled(true);

                Player hooked = Bukkit.getPlayer(hookado);
                if (hooked == null) {
                    ev.getPlayer().sendMessage(ChatColor.RED + L.m("Nada havia no gancho"));
                    return;
                }

                if (!Stamina.spendStamina(ev.getPlayer(), 50)) {
                    return;
                }

                if (ev.getPlayer().getWorld().getName().equalsIgnoreCase("dungeon") || WorldGuardKom.ehSafeZone(hooked.getLocation())) {
                    ev.getPlayer().removeMetadata("hook", KoM._instance);
                    return;
                }

                if (!hooked.getWorld().getName().equalsIgnoreCase(ev.getPlayer().getWorld().getName())) {
                    ev.getPlayer().sendMessage(ChatColor.RED + L.m("O que voce tinha pego esta muito longe"));
                    return;
                }

                if (hooked.getLocation().distance(ev.getPlayer().getLocation()) > 20) {
                    ev.getPlayer().sendMessage(ChatColor.RED + L.m("O que voce tinha pego esta muito longe"));
                    return;
                }

                hooked.teleport(ev.getPlayer());
                KoM.act(hooked, hooked.getName() + " foi pescado");
                KoM.act(ev.getPlayer(), ev.getPlayer().getName() + " pescou um(a) " + hooked.getName());
                return;
            }

            if (ev.getPlayer().hasPotionEffect(PotionEffectType.SLOW)) {
                ev.getPlayer().sendMessage(ChatColor.GREEN + L.m("Aguarde para fazer isto novamente"));
                ev.setCancelled(true);
                return;
            }
            ev.getPlayer().sendMessage(ChatColor.GREEN + L.m("Voce arremeca o gancho da vara de pescar"));
            int cd = 120;
            if (PlayerSpec.temSpec(ev.getPlayer(), PlayerSpec.Pescador)) {
                cd = 20;
            }
            ev.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, cd, 1));
        }
    }

    @EventHandler
    public void onRodLand(ProjectileHitEvent e) {
        if (!(e.getEntityType() == EntityType.FISHING_HOOK)) {
            return;
        }
        if(e.getEntity().getWorld().getName().equalsIgnoreCase("dungeon")) {
            return;
        }
        
  
        
        Player atirador = null;
        if (e.getEntity().getShooter() instanceof Player) {
            atirador = (Player) e.getEntity().getShooter();
        }

        if (atirador == null) {
            return;
        }
        
              if(Thief.taInvisivel(atirador))
                  Thief.revela(atirador);

        if(e.getHitBlock().getType().name().contains("DOOR"))
            return;
        
        for (Entity entity : Bukkit.getWorld(e.getEntity().getLocation().getWorld().getName()).getNearbyEntities(e.getEntity().getLocation(), 2, 2, 2)) {
            if (!(entity instanceof Player)) {
                continue;
            }
            FishHook hook = (FishHook) e.getEntity();
            Player rodder = (Player) hook.getShooter();
            Player player = (Player) entity;
            if (player.getName().equalsIgnoreCase(rodder.getName())) {
                continue;
            }

            MetaShit.setMetaObject("hook", atirador, player.getUniqueId());
            atirador.sendMessage(ChatColor.GREEN + "Fiscou um " + player.getName() + " de agua doce.");
            Location loc = player.getLocation().add(0, 0.5, 0);
            player.teleport(loc);
            player.setVelocity(rodder.getLocation().getDirection().multiply(+0.4));
            hook.remove();
            player.sendMessage(ChatColor.RED + "Voce sentiu um anzol grudando em vc");
            rodder.updateInventory();
            return;
        }
    }

    public static Horse.Color pegaCor(String s) {
        s = s.trim();
        if (KoM.debugMode) {
            KoM.log.info("pegando tipo " + s);
        }
        for (Horse.Color cor : Horse.Color.values()) {
            if (KoM.debugMode) {
                KoM.log.info("verificando " + cor.name());
            }
            if (cor.name().equalsIgnoreCase(s)) {
                if (KoM.debugMode) {
                    KoM.log.info("retornei " + cor.name());
                }
                return cor;
            }
        }
        return Horse.Color.CREAMY;
    }

    public static Horse.Style pegaTipo(String s) {
        s = s.trim();
        if (KoM.debugMode) {
            KoM.log.info("pegando tipo " + s);
        }
        for (Horse.Style cor : Horse.Style.values()) {
            if (KoM.debugMode) {
                KoM.log.info("verificando " + cor.name());
            }
            if (cor.name().equalsIgnoreCase(s)) {
                if (KoM.debugMode) {
                    KoM.log.info("retornei " + cor.name());
                }
                return cor;
            }
        }
        return Horse.Style.NONE;
    }

    public static Horse.Variant pegaVariant(String s) {
        s = s.trim();
        return Horse.Variant.HORSE;
    }

    public static void fixLeatherArmor(PlayerInteractEvent ev) {
        if (Jobs.getJobLevel("Fazendeiro", ev.getPlayer()) == 1) {
            if (ev.getPlayer().getInventory().contains(Material.LEATHER)) {
                if (ev.getPlayer().getItemInHand().getDurability() == 0) {
                    ev.getPlayer().sendMessage(ChatColor.RED + "Este item esta como novo");
                } else {
                    if (Mana.spendMana(ev.getPlayer(), 50)) {
                        PlayEffect.play(VisualEffect.SPELL_INSTANT, ev.getPlayer().getLocation(), "num:40");
                        ev.getPlayer().sendMessage(ChatColor.GREEN + "Voce arrumou a armadura");
                        ev.getPlayer().getItemInHand().setDurability((short) 0);
                        KoM.removeInventoryItems(ev.getPlayer().getInventory(), Material.LEATHER, 1);
                    }
                }
            } else {
                ev.getPlayer().sendMessage(ChatColor.RED + "Voce precisa de couro");
            }
        }

    }

    public static boolean coletaDropExtraDeAnimal(Player p, Entity animal) {

        Material mat = null;

        if (animal instanceof Chicken) {

            mat = Material.EGG;
        } else if (animal instanceof Sheep) {

            mat = Material.WOOL;
        } else if (animal instanceof Cow) {

            mat = Material.LEATHER;
        }

        if (mat == null) {
            return false;
        }

        if (!animal.hasMetadata("recurso")) {
            if (p.getItemInHand().getType() != Material.SHEARS) {
                KoM.msgUnica(p, ChatColor.GREEN + L.m("Bata no animal com uma tesoura para obter recursos extras !"));
                return false;
            }
        }

        if (animal.hasMetadata("recurso")) {
            long recurso = Long.valueOf(MetaShit.getMetaString("recurso", animal));
            long agora = System.currentTimeMillis() / 1000;
            if (recurso + 60 * 20 > agora) {
                return false;
            }
        }
        int exp = XP.getExpPorAcao(p.getLevel());
        int dificuldade = 35;
        int sucesso = Jobs.hasSuccess(dificuldade, "Fazendeiro", p);
        if (sucesso == Jobs.success) {
            p.sendMessage(ChatColor.AQUA + Menu.getSimbolo("Fazendeiro") + " " + ChatColor.GOLD + L.m("Voce extraiu recursos extras do animal !"));
            MetaShit.setMetaString("recurso", animal, "" + System.currentTimeMillis() / 1000);
            p.getWorld().dropItemNaturally(animal.getLocation(), new ItemStack(mat, 1));
            GeneralListener.givePlayerExperience(5 + (p.getLevel() / 10), p);

        }
        ((LivingEntity) animal).damage(4D);
        return true;

    }

    public static boolean transformaEmOvO(Player p, Entity animal) {
        // soh vale pra fazendeiro primário
        if (p.getItemInHand().getType() != Material.EGG) {
            return false;
        }
        
        if(p.getWorld().getName().equalsIgnoreCase("dungeon")) {
            return false;
        }

        KoM.debug("transforma em ovo");

        if (animal.hasMetadata("recurso")) {
            long recurso = Long.valueOf(MetaShit.getMetaString("recurso", animal));
            long agora = System.currentTimeMillis() / 1000;
            if (recurso + 60 * 20 > agora) {
                p.sendMessage(ChatColor.RED + L.m("Este animal foi coletado recurso a muito pouco tempo, aguarde..."));
                return false;
            }
        }

        int dificuldade = 60;
        short idMonstro = 0;
        if (animal instanceof Pig) {
            idMonstro = 90;
        } else if (animal instanceof Sheep) {
            idMonstro = 91;
        } else if (animal instanceof Cow) {
            idMonstro = 92;
        } else if (animal instanceof Horse) {
            idMonstro = 100;
            dificuldade = 70;
        } else if (animal instanceof Chicken) {
            idMonstro = 93;
        } else if (animal instanceof Wolf) {
            // idMonstro = 95;
        } else if (animal instanceof Ocelot) {
            idMonstro = 98;
        } else if (animal instanceof Spider) {
            dificuldade = 80;
            idMonstro = 52;
        } else if (animal instanceof Zombie) {
            idMonstro = 54;
            dificuldade = 80;
        } else if (animal instanceof Skeleton) {
            idMonstro = 51;
            dificuldade = 80;
        } else if (animal instanceof MushroomCow) {
            idMonstro = 96;
        } else if (animal instanceof Rabbit) {
            idMonstro = 101;
        }
        if (idMonstro == 0) {
            idMonstro = animal.getType().getTypeId();
        }

        KoM.debug("id monstro = " + idMonstro);

        if (animal instanceof Tameable) {
            AnimalTamer dono = ((Tameable) animal).getOwner();
            if (dono != null && !dono.getName().equalsIgnoreCase(p.getName())) {
                p.sendMessage(ChatColor.RED + L.m("Este animal ja tem dono !"));
            }
        }
        if (animal.hasMetadata("recurso")) {
            long recurso = Long.valueOf(MetaShit.getMetaString("recurso", animal));
            long agora = System.currentTimeMillis() / 1000;
            if (recurso + 60 * 10 > agora) {
                p.sendMessage(ChatColor.RED + L.m("Aguarde para colocar este animal no ovo !"));
                return false;
            }
        }

        if (idMonstro != 0) {

            if (p.getLevel() < 30) {
                return false; // skill de por em ovo
            }

            if (!Mana.spendMana(p, 40)) {
                return false;
            }

            if (idMonstro == 95) {
                dificuldade = 30;
            }
            ItemStack ovo = new ItemStack(Material.MONSTER_EGG, 1);
            SpawnEggMeta meta = (SpawnEggMeta) ovo.getItemMeta();
            meta.setSpawnedType(EntityType.fromId(idMonstro));
            ovo.setItemMeta(meta);

            int sucesso = Jobs.hasSuccess(dificuldade, "Fazendeiro", p);
            if (sucesso == Jobs.fail) {
                if (dificuldade < 70) {
                    animal.remove();
                }
                if (p.getItemInHand().getAmount() > 1) {
                    p.getItemInHand().setAmount(p.getItemInHand().getAmount() - 1);
                } else {
                    p.setItemInHand(null);
                }
                p.sendMessage(ChatColor.AQUA + Menu.getSimbolo("Fazendeiro") + " " + ChatColor.RED + L.m("Voce falhou em colocar o animal no ovo !"));
                return true;
            }
            if (animal instanceof Horse) {

                Horse h = (Horse) animal;
                if (h.getOwner() == null || !h.getOwner().getName().equalsIgnoreCase(p.getName())) {
                    p.sendMessage(ChatColor.RED + L.m("Voce precisa domar o cavalo primeiro !"));
                    return false;
                }
                if (h.getInventory().getSaddle() == null || h.getInventory().getSaddle().getType() != Material.SADDLE) {
                    p.sendMessage(ChatColor.RED + L.m("Voce precisa por uma cela no cavalo para transforma-lo em ovo !"));
                    return false;
                }
                if (h.getLeashHolder() == null || !(h.getLeashHolder() instanceof Player) || !((Player) h.getLeashHolder()).getName().equalsIgnoreCase(p.getName())) {
                    p.sendMessage(ChatColor.RED + L.m("Voce precisa por uma coleira no cavalo para transforma-lo em ovo !"));
                    return false;
                }
                meta.setDisplayName(ChatColor.GREEN + "Montaria (Não Dropa Qnd Morre)");
                meta.setLore(Arrays.asList(new String[]{
                    ChatColor.GREEN + "Nome" + ChatColor.YELLOW + ": " + ((h.getCustomName() == null || h.getCustomName().length() == 0) ? "Pocotó" : h.getCustomName()),
                    ChatColor.GREEN + "Cor" + ChatColor.YELLOW + ": " + h.getColor().name(),
                    ChatColor.GREEN + "Tipo" + ChatColor.YELLOW + ": " + h.getStyle().name(),
                    ChatColor.GREEN + "Raça" + ChatColor.YELLOW + ": " + h.getVariant().name()
                // ChatColor.YELLOW + "- Não Dropa Quando Morre"
                }));
                ovo.setItemMeta(meta);
            }
            //cavalo
            if (p.getItemInHand().getAmount() > 1) {
                p.getItemInHand().setAmount(p.getItemInHand().getAmount() - 1);
            } else {
                p.setItemInHand(null);
            }
            animal.getWorld().dropItemNaturally(animal.getLocation(), ovo);
            animal.remove();
            GeneralListener.givePlayerExperience(15, p);
            p.sendMessage(ChatColor.AQUA + Menu.getSimbolo("Fazendeiro") + " " + ChatColor.RED + L.m("Voce colocou o animal no ovo !"));
            return true;
        }
        return false;
    }
}
