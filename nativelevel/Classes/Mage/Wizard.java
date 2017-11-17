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
package nativelevel.Classes.Mage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import me.asofold.bpl.simplyvanish.SimplyVanish;
import me.asofold.bpl.simplyvanish.config.VanishConfig;
import me.fromgate.playeffect.PlayEffect;
import me.fromgate.playeffect.VisualEffect;
import nativelevel.sisteminhas.ClanLand;
import nativelevel.Custom.CustomItem;
import nativelevel.Custom.Items.CajadoElemental;
import org.bukkit.block.EnchantingTable;
import nativelevel.Jobs;
import nativelevel.KoM;
import nativelevel.Lang.L;
import nativelevel.Listeners.GeneralListener;
import nativelevel.Menu.Menu;
import nativelevel.MetaShit;
import nativelevel.Attributes.AttributeInfo;
import nativelevel.Attributes.Mana;
import nativelevel.Classes.Thief;
import nativelevel.Equipment.Atributo;
import nativelevel.Equipment.EquipManager;
import nativelevel.Equipment.EquipMeta;
import nativelevel.Equipment.ItemAttributes;
import nativelevel.bencoes.TipoBless;
import nativelevel.integration.SimpleClanKom;
import nativelevel.integration.WG;
import nativelevel.skills.SkillMaster;
import nativelevel.spec.PlayerSpec;
import nativelevel.utils.Targeter;
import nativelevel.sisteminhas.Tralhas;
import net.minecraft.server.v1_12_R1.ContainerEnchantTable;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.StatisticList;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.EnchantingInventory;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.SmallFireball;
import org.bukkit.entity.Snowball;
import org.bukkit.event.block.Action;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class Wizard {

    public static Enchantment getRandomEnchant() {
        int rnd = Jobs.rnd.nextInt(9);
        switch (rnd) {
            case 0:
                return Enchantment.DAMAGE_ARTHROPODS;
            case 1:
                return Enchantment.LOOT_BONUS_BLOCKS;
            case 2:
                return Enchantment.PROTECTION_PROJECTILE;
            case 3:
                return Enchantment.DURABILITY;
            case 4:
                return Enchantment.FIRE_ASPECT;
            case 5:
                return Enchantment.DAMAGE_ALL;
            case 6:
                return Enchantment.PROTECTION_ENVIRONMENTAL;
            case 7:
                return Enchantment.DAMAGE_UNDEAD;
            case 8:
                return Enchantment.LOOT_BONUS_MOBS;
        }
        return null;
    }

    public static String FIRE = ChatColor.RED + "☣";
    public static String LIGHT = ChatColor.YELLOW + "☼";
    public static String TERRA = ChatColor.GREEN + "☢";

    public static void blink(Player p) {
        if (p.getWorld().getName().equalsIgnoreCase("dungeon")) {
            p.sendMessage(ChatColor.RED + L.m("Esta magia parece nao funcionar aqui !"));
            return;
        }

        double magia = EquipManager.getPlayerAttribute(Atributo.Magia, p);
        
        int distance = (int)Math.round(8 + (p.getLevel() / 10) * (1+magia/100/2));
        HashSet<Material> m = null;
        Block b = p.getTargetBlock(m, 12);
        if (b == null) {
            return;
        }
        Block up = b.getRelative(BlockFace.UP);
        Location l = up.getLocation();
        l.setX(l.getX() + 0.5);
        l.setZ(l.getZ() + 0.5);
        if (up.getType() == Material.AIR && up.getRelative(BlockFace.UP).getType() == Material.AIR) {
            blinka(p, l);
        } else if (b.getType() == Material.AIR && b.getRelative(BlockFace.UP).getType() == Material.AIR) {
            l = b.getLocation();
            l.setX(l.getX() + 0.5);
            l.setZ(l.getZ() + 0.5);
            blinka(p, b.getLocation());
        }
    }

    public static void blinka(Player p, Location onde) {
        onde.setPitch(p.getLocation().getPitch());
        onde.setYaw(p.getLocation().getYaw());
        p.teleport(onde);
        Location l = p.getLocation();
        PlayEffect.play(VisualEffect.SMOKE, onde, "num:1");
        PlayEffect.play(VisualEffect.SMOKE, p.getLocation(), "num:1");
    }

    public static void castRepel(Player p, int intel) {
        if (p.getWorld().getName().equalsIgnoreCase("dungeon")) {
            p.sendMessage(ChatColor.RED + L.m("Esta magia parece nao funcionar aqui !"));
            return;
        }
        ClanPlayer cp = ClanLand.manager.getAnyClanPlayer(p.getUniqueId());
        double magia = EquipManager.getPlayerAttribute(Atributo.Magia, p);
        int area = 2 + intel / 30;
        if (PlayerSpec.temSpec(p, PlayerSpec.Sacerdote)) {
            area += 4;
        }

        for (Entity e : p.getNearbyEntities(area, 2, area)) {
            if (e.getType() == EntityType.PLAYER) {
                ClanPlayer t = ClanLand.manager.getAnyClanPlayer(((Player) e).getUniqueId());
                if (t != null && cp != null && (t.getTag().equalsIgnoreCase(cp.getTag()) || t.isAlly(p))) {
                    continue;
                }
            }
            Vector ve = e.getLocation().toVector();
            Vector v = ve.subtract(p.getLocation().toVector()).normalize().multiply(2.2);
            v.setY(0.32 + intel / 80);
            e.setVelocity(v);
        }
        PlayEffect.play(VisualEffect.EXPLOSION_HUGE, p.getLocation(), "");
    }

    public static void markRecall(Player p) {
        p.sendMessage(ChatColor.GREEN + L.m("Abra seu inventario e clique na runa !"));
        MetaShit.setMetaObject("RUNA", p, "1");
    }

    public static final void enchants(EnchantItemEvent event) {

        int nivel = event.getExpLevelCost() * 2;
        if (nivel > 90) {
            nivel = 90;
        }
        if (nivel < 20) {
            nivel = 20;
        }

        if (event.getEnchantsToAdd().containsKey(Enchantment.SILK_TOUCH)) {
            event.getEnchantsToAdd().remove(Enchantment.SILK_TOUCH);
        }

        int maxLevel = 2;
        Enchantment en = Enchantment.DAMAGE_ALL;
        if (event.getEnchantsToAdd().containsKey(en)) {
            int lvl = event.getEnchantsToAdd().get(en);
            lvl = lvl - 1;
            if (lvl <= 0) {
                event.getEnchantsToAdd().put(en, 1);
            } else {
                event.getEnchantsToAdd().put(en, lvl);
            }
        }

        maxLevel = 2;
        en = Enchantment.ARROW_DAMAGE;
        if (event.getEnchantsToAdd().containsKey(en)) {
            int lvl = event.getEnchantsToAdd().get(en);
            lvl = lvl - 1;
            if (lvl <= 0) {
                event.getEnchantsToAdd().put(en, 1);
            } else {
                event.getEnchantsToAdd().put(en, lvl);
            }
        }

        maxLevel = 2;
        en = Enchantment.PROTECTION_ENVIRONMENTAL;
        if (event.getEnchantsToAdd().containsKey(en)) {
            int lvl = event.getEnchantsToAdd().get(en);
            lvl = lvl - 1;
            if (lvl <= 0) {
                event.getEnchantsToAdd().remove(en);
            } else {
                event.getEnchantsToAdd().put(en, lvl);
            }
        }

        en = Enchantment.PROTECTION_PROJECTILE;
        if (event.getEnchantsToAdd().containsKey(en)) {
            int lvl = event.getEnchantsToAdd().get(en);
            lvl = lvl - 1;
            if (lvl <= 0) {
                event.getEnchantsToAdd().remove(en);
            } else {
                event.getEnchantsToAdd().put(en, lvl);
            }
        }

        /*
         if(event.getEnchantsToAdd().containsKey(Enchantment.DAMAGE_ALL)) {
         int lvl = event.getEnchantsToAdd().get(Enchantment.DAMAGE_ALL);
         if(lvl>=4) {
         event.getEnchantsToAdd().put(Enchantment.DAMAGE_ALL, 3);
         }
         }
         */
        int preco = event.getExpLevelCost() / 2;
        if (preco < 1) {
            preco = 1;
        }

        EnchantingInventory inv = (EnchantingInventory) event.getInventory();

        if (inv.getSecondary() == null || inv.getSecondary().getAmount() < event.getExpLevelCost()) {
            event.getEnchanter().sendMessage(ChatColor.AQUA + Menu.getSimbolo("Mago") + " " + ChatColor.GOLD + "Voce precisa colocar mais lapiz lazuli na mesa para encantar items !");
            //event.getEnchantsToAdd().clear();
            event.setCancelled(true);
            return;
        }

        if (event.getEnchanter().getInventory().contains(Material.GLOWSTONE, preco)) {
            KoM.removeInventoryItems(event.getEnchanter().getInventory(), Material.GLOWSTONE, preco);
        } else {
            event.getEnchanter().sendMessage(ChatColor.AQUA + Menu.getSimbolo("Mago") + " " + ChatColor.GOLD + "Voce precisa de glowstone para encantar items !");
            //event.getEnchantsToAdd().clear();
            event.setCancelled(true);
            return;
        }

        int dificuldade = 120;
        if (event.getExpLevelCost() < 3) {
            dificuldade = 75;
        } else if (event.getExpLevelCost() < 9) {
            dificuldade = 100;
        }

        int xp = event.getExpLevelCost();
        TipoBless ativo = TipoBless.save.getTipo(event.getEnchanter());
        if (ativo != null && ativo == TipoBless.Encantamento) {
            dificuldade -= 12;
            if (dificuldade < 0) {
                dificuldade = 0;
            }
            xp = xp * 10;
        }

        int qtdNova = inv.getSecondary().getAmount() - event.getExpLevelCost();
        if (inv.getSecondary().getAmount() == event.getExpLevelCost()) {
            inv.setSecondary(null);
        } else {
            inv.getSecondary().setAmount(qtdNova);
        }

        /*
         this.enchantSlots.update();
         this.f = entityhuman.cj();
         a(this.enchantSlots);
         */
        Player p = event.getEnchanter();

        event.setExpLevelCost(0);

        int sucesso = Jobs.hasSuccess(dificuldade, "Mago", p);
        if (sucesso == Jobs.success) {
            // int jobLevel = Jobs.getJobLevel("Mago", p);
            // if (jobLevel == 0) {
            
            EnchantingTable table = (EnchantingTable)event.getEnchantBlock().getState();
            
            //event.setCancelled(true);
            
            if(event.getEnchantsToAdd().size()==0) {
                event.getEnchantsToAdd().put(Enchantment.DURABILITY, 1);
                KoM.debug("Nao tinha enchants");
            }
            
            /*
            for (Enchantment e : event.getEnchantsToAdd().keySet()) {
                int level = event.getEnchantsToAdd().get(e);
                event.getItem().addEnchantment(e, level);
            }
            event.getEnchantsToAdd().clear();
            */
            
            GeneralListener.givePlayerExperience(xp, p);
            ItemMeta meta = event.getItem().getItemMeta();
            List<String> lore = meta.getLore();
            if (lore == null) {
                lore = new ArrayList<String>();
            }
            lore.add(0, ChatColor.GREEN + L.m("Encantado por ") + ChatColor.YELLOW + p.getName());
            meta.setLore(lore);
            event.getItem().setItemMeta(meta);
            p.sendMessage(ChatColor.AQUA + Menu.getSimbolo("Mago") + " " + ChatColor.GOLD + L.m("Voce encantou o item !"));

            /*
             if (sucesso == Jobs.BONUS && Jobs.rnd.nextInt(5) == 1) {
             ItemStack copia = event.getItem();
             int slot = p.getInventory().firstEmpty();
             if (slot != -1) {
             p.getInventory().setItem(slot, copia);
             p.sendMessage(ChatColor.AQUA + Menu.getSimbolo("Mago") + " " + ChatColor.GREEN + L.m("Voce clonou o equipamento !!!"));
             }
             }
             */
            // }
        } else {
            GeneralListener.givePlayerExperience(event.getExpLevelCost(), p);
            event.getItem().setDurability((short) 0);
            event.getItem().setData(new MaterialData(Material.SULPHUR));
            event.getItem().setType(Material.SULPHUR);
            event.getEnchantsToAdd().clear();
            p.sendMessage(ChatColor.GOLD + L.m("Voce falhou ao tentar encantar o item e acabou transformando o item em polvora !"));
            p.closeInventory();
        }
        
        EntityPlayer player = ((CraftPlayer) p).getHandle();
        ContainerEnchantTable enchant = (ContainerEnchantTable) player.activeContainer;
        //player.enchantDone(0);
       
        player.enchantDone(CraftItemStack.asNMSCopy(event.getItem()), 0);
        player.b(StatisticList.W);
        //player.b(StatisticList.W);
        enchant.f = player.cL();
        enchant.enchantSlots.update();
        enchant.a(enchant.enchantSlots);
    }

    public static void soltaRaio(Player p) {

        HashSet<Material> m = null;
        Block target = p.getTargetBlock(m, 100);
        if (target.getWorld().getHighestBlockYAt(target.getLocation()) != target.getY() + 1) {
            target = null;
        }
        if (target != null) {

            int range = 1 + p.getLevel() / 120;
            ClanPlayer cp = ClanLand.manager.getAnyClanPlayer(p.getUniqueId());
            Entity frexa = target.getWorld().spawnEntity(target.getLocation(), EntityType.ARROW);
            boolean segurou = false;
            for (Entity e : frexa.getNearbyEntities(range + 5, range, range + 5)) {
                if (e instanceof LivingEntity) {
                    if (e.getType() == EntityType.PLAYER) {
                        Player alvo = (Player) e;
                        String ci = CustomItem.getCustomItem(alvo.getItemInHand());
                        if (ci != null && ci.equalsIgnoreCase(L.m("Para Raio")) && Jobs.getJobLevel("Engenheiro", alvo) == 1) {
                            if (Mana.spendMana(alvo, 30)) {
                                alvo.sendMessage(ChatColor.GREEN + L.m("Seu para raios segurou o raio !"));
                                PlayEffect.play(VisualEffect.SMOKE_LARGE, alvo.getLocation(), "");
                                frexa.remove();
                                segurou = true;
                                return;
                            }
                        }
                    }
                }
            }
            LightningStrike strike = target.getWorld().strikeLightningEffect(target.getLocation());
            for (Entity e : frexa.getNearbyEntities(range, range, range)) {
                if (e instanceof LivingEntity) {
                    if (e.getType() == EntityType.PLAYER) {
                        Player alvo = (Player) e;

                        ClanPlayer t = ClanLand.manager.getAnyClanPlayer(((Player) e).getUniqueId());
                        if (t != null && cp != null && (t.getTag().equalsIgnoreCase(cp.getTag()) || t.isAlly(p))) {
                            continue;
                        }
                    }
                    LivingEntity le = ((LivingEntity) e);
                    if (le instanceof Player && le.getLocation().getWorld().getName().equalsIgnoreCase("dungeon")) {
                    } else {
                        if (!ClanLand.isSafeZone(le.getLocation())) {
                            if (le.getType() == EntityType.PLAYER) {
                                int job = Jobs.getJobLevel("Engenheiro", (Player) le);
                                if (job == 1) {
                                    continue;
                                }
                                ItemStack mao = ((Player) le).getItemInHand();
                                String ci = CustomItem.getCustomItem(mao);
                                String cajadoElemental = null;
                                if (ci != null && ci.equalsIgnoreCase("Cajado Elemental") && Jobs.getJobLevel("Mago", (Player) le) == 1) {
                                    cajadoElemental = CajadoElemental.getElemento(p.getItemInHand());
                                    if (cajadoElemental == null) {
                                        cajadoElemental = "Nulo";
                                    }
                                }
                                if (cajadoElemental != null) {
                                    if (cajadoElemental.equalsIgnoreCase("Nulo")) {
                                        CajadoElemental.botaElemento(mao, "Raio");
                                        p.sendMessage(ChatColor.GREEN + "Seu cajado absorveu o elemento Raio");
                                    }
                                }
                            }
                            double damage = 6D;
                            double magia = EquipManager.getPlayerAttribute(Atributo.Magia, p);
                            damage *= 1+(magia/100);
                            if (PlayerSpec.temSpec(p, PlayerSpec.Sabio)) {
                                damage *= 1.3;
                            } else if (PlayerSpec.temSpec(p, PlayerSpec.Sacerdote)) {
                                damage *= 0.6;
                            }
                            if (le.getType() == EntityType.PLAYER) {
                                damage = damage * 0.6;
                                if (Jobs.getJobLevel("Paladino", (Player) le) == 1) {
                                    damage = damage * 1.4;
                                }
                                if (Thief.taInvisivel((Player) le));
                                Thief.revela((Player) le);
                            }

                            // if (!ev.isCancelled()) {
                            if (le.getHealth() <= damage) {
                                GeneralListener.ultimoDano.put(le.getUniqueId(), p.getUniqueId());
                            }
                            le.damage(damage);
                            if (GeneralListener.ultimoDano.containsKey(le.getUniqueId())) {
                                GeneralListener.ultimoDano.remove(le.getUniqueId());
                            }
                            Tralhas.doRandomKnock(le, 0.9f);

                            //   }
                        }
                    }
                }
            }
            frexa.remove();
        }
    }

    /*
     public static boolean erraMagia(Player p) {
     PlayEffect.play(VisualEffect.SPELL, p.getLocation(), "num:1");
     p.sendMessage(ChatColor.AQUA + Menu.getSimbolo("Mago") + " " + ChatColor.GOLD + L.m("Voce falhou ao soltar a magia !"));
     return true;
     }
     */
    /*
     public static boolean consomeReagentes(Player p, int qtos) {
     //       int polvora = p.getInventory().first(reagente);
     //       if (polvora == -1) {
     //           p.sendMessage(ChatColor.GOLD + "Voce precisa de polvora para poder soltar magias !");
     //          return false;
     //       }
     //       int qtd = p.getInventory().getItem(polvora).getAmount();
     //       if (qtd > 1) {
     //          p.getInventory().getItem(polvora).setAmount(qtd - 1);
     //      } else {
     //          p.getInventory().setItem(polvora, new ItemStack(Material.AIR, 1));
     //      }
     if (!KnightsOfMania.gastaReagentes(p, p.getInventory(), 1)) {
     p.sendMessage(ChatColor.AQUA + Menu.getSimbolo("Mago") + " " + ChatColor.GOLD + L.m("Voce precisa de pocoes de mana para usar magias !"));
     return false;
     }
     return true;
     }
     */
    //public int velocidadeFogo = 4;
    //HashSet<Player> fireImmunity = new HashSet<Player>();
    public void soltaFireNova(Player p) {
        if (p.getWorld().getName().equalsIgnoreCase("dungeon")) {
            p.sendMessage(ChatColor.RED + L.m("Esta magia parece nao funcionar aqui !"));
            return;
        }
        double magia = EquipManager.getPlayerAttribute(Atributo.Magia, p);
        //fireImmunity.add(p);
        //new FirenovaAnimation(p);
        p.sendMessage(ChatColor.GREEN + "Voce emite um calor intenso");
        PlayEffect.play(VisualEffect.FLAME_SPAWNER, p.getLocation(), "num:5");
        for (Entity e : p.getNearbyEntities(6, 6, 6)) {
            if (e.getType() == EntityType.PLAYER) {
                Player pe = (Player) e;
                if (!SimpleClanKom.canPvp(p, pe)) {
                    continue;
                }
            }
            e.setFireTicks((int)Math.round(20 * 5 * (1+magia/100/2)));
            PlayEffect.play(VisualEffect.LAVA, e.getLocation(), "num:10");
            if (e.getType() == EntityType.PLAYER) {
                ((Player) e).sendMessage(ChatColor.RED + p.getName() + " fez seu corpo arder em chamas");
            }
        }
    }

    public void conversaoDeAlma(Player p, int intel) {
        for (Entity coisa : p.getNearbyEntities(10, 10, 10)) {
            if (coisa instanceof Player) {
                Player pl = (Player) coisa;
                ClanPlayer cp = ClanLand.manager.getAnyClanPlayer(pl.getUniqueId());
                if (cp == null || cp.isRival(p) || !cp.isAlly(p)) {
                    continue;
                }

                double duracao = 100 + 20 * (intel / 1.5);
                if (PlayerSpec.temSpec(p, PlayerSpec.Sabio)) {
                    duracao -= 20;
                } else if (PlayerSpec.temSpec(p, PlayerSpec.Sacerdote)) {
                    duracao += 100;
                }
                if (!p.getWorld().getName().equalsIgnoreCase("dungeon")) {
                    pl.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, (int) duracao, 0));
                }
                pl.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, (int) duracao, 0));
                pl.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, (int) duracao, 0));
                pl.sendMessage(ChatColor.AQUA + Menu.getSimbolo("Mago") + " " + ChatColor.GOLD + p.getName() + L.m(" te abencoou !"));

            }
        }
        p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20 * 60, 0));
        p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 60, 0));
        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 60, 0));
        p.sendMessage(ChatColor.AQUA + Menu.getSimbolo("Mago") + " " + ChatColor.GOLD + L.m("Voce se sente abencoado !"));
        PlayEffect.play(VisualEffect.FIREWORKS_EXPLODE, p.getLocation(), "type:ball color:white");
    }

    /*
     public static void fazCustomItem(InventoryClickEvent event, ItemStack queFiz) {
     Player p = ((Player) event.getWhoClicked());
     if (event.getCursor() != null && event.getCursor().getAmount() > 63) {
     return;
     }
     int dificuldade = 0;
     //if (queFiz instanceof Runa) {
     //    dificuldade = 65;
     //}
     //if (queFiz instanceof GemaBrilhante) {
     //    dificuldade = 55;
     //}
     int suc = Jobs.hasSuccess(dificuldade, "Mago", p);
     if (suc == Jobs.fail) {
     p.sendMessage(ChatColor.AQUA + Menu.getSimbolo("Mago") + " " + ChatColor.RED + L.m("Voce falhou ao criar o item magico !"));
     event.setCurrentItem(new ItemStack(Material.SULPHUR, 1));
     return;
     }
     p.sendMessage(ChatColor.AQUA + Menu.getSimbolo("Mago") + " " + ChatColor.GOLD + L.m("Voce fez o item com sucesso !"));
     }
     */

    /*
     private class FirenovaAnimation implements Runnable {

     Player player;
     int i;
     Block center;
     HashSet<Block> fireBlocks;
     int taskId;

     public FirenovaAnimation(Player player) {
     this.player = player;
     //Events.ganhaExp(15, player);
     PlayEffect.play(VisualEffect.LAVA, player.getLocation(), "num:2");
     player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 140, 1));
     player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 80, 1));
     player.getWorld().createExplosion(player.getLocation(), 0);
     this.i = 0;
     this.center = player.getLocation().getBlock();
     this.fireBlocks = new HashSet();

     this.taskId = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(KnightsOfMania._instance, this, 0L, Wizard.this.velocidadeFogo);
     }

     public void run() {
     for (Block block : this.fireBlocks) {
     if (block.getType() == Material.FIRE) {
     byte b = 0;
     block.setTypeIdAndData(0, b, false);
     }
     }
     this.fireBlocks.clear();

     this.i += 1;
     if (this.i <= 5) {
     byte byt = 15;
     int bx = this.center.getX();
     int y = this.center.getY();
     int bz = this.center.getZ();
     for (int x = bx - this.i; x <= bx + this.i; x++) {
     for (int z = bz - this.i; z <= bz + this.i; z++) {
     if ((Math.abs(x - bx) == this.i) || (Math.abs(z - bz) == this.i)) {
     Block b = this.center.getWorld().getBlockAt(x, y, z);
     if ((b.getType() == Material.AIR) || ((b.getType() == Material.LONG_GRASS))) {
     Block under = b.getRelative(BlockFace.DOWN);
     if ((under.getType() == Material.AIR) || ((under.getType() == Material.LONG_GRASS))) {
     b = under;
     }

     b.setTypeIdAndData(Material.FIRE.getId(), byt, false);
     this.fireBlocks.add(b);
     } else if ((b.getRelative(BlockFace.UP).getType() == Material.AIR) || ((b.getRelative(BlockFace.UP).getType() == Material.LONG_GRASS))) {
     b = b.getRelative(BlockFace.UP);
     b.setTypeIdAndData(Material.FIRE.getId(), byt, false);
     this.fireBlocks.add(b);
     }
     }
     }
     }
     } else if (this.i > 5 + 1) {
     Bukkit.getServer().getScheduler().cancelTask(this.taskId);
     // Mago.this.fireImmunity.remove(this.player);
     }
     }
     }
     */
    public HashSet<Block> paredesCriadas = new HashSet<Block>();

    public void fazParede(Player player, boolean fraco) {
        Projectile fb = null;
        //if (player.isSneaking()) {
        fb = player.launchProjectile(Fireball.class);
        //} else {
        fb = player.launchProjectile(SmallFireball.class);
        //}
        fb.setShooter(player);
    }
    public HashSet<Block> teiasCriadas = new HashSet<Block>();

    public void prende(Player p) {

        if (p.getWorld().getName().equalsIgnoreCase("dungeon")) {
            p.sendMessage(ChatColor.RED + L.m("Esta magia parece nao funcionar aqui !"));
            return;
        }
        HashSet<Material> m = null;
        Block target = p.getTargetBlock(m, 30);

        if (target != null) {
            PlayEffect.play(VisualEffect.FIREWORKS_EXPLODE, target.getLocation(), "type:ball color:white");
            int x = target.getLocation().getBlockX();
            int y = target.getLocation().getBlockY();
            int z = target.getLocation().getBlockZ();

            Location loc = new Location(target.getLocation().getWorld(), x + 0.5D, y + 0.5D, z + 0.5D, target.getLocation().getYaw(), target.getLocation().getPitch());

            ArrayList<Block> tombBlocks = new ArrayList();
            Block feet = target.getLocation().getBlock();

            Block temp = feet.getRelative(1, 0, 0);
            //Events.ganhaExp(15, p);
            if (temp.getType() == Material.AIR) {
                temp.setType(Material.WEB);

                tombBlocks.add(temp);
            }
            temp = feet.getRelative(1, 1, 0);
            if (temp.getType() == Material.AIR) {
                temp.setType(Material.WEB);
                tombBlocks.add(temp);
            }
            temp = feet.getRelative(-1, 0, 0);
            if (temp.getType() == Material.AIR) {
                temp.setType(Material.WEB);
                tombBlocks.add(temp);
            }
            temp = feet.getRelative(-1, 1, 0);
            if (temp.getType() == Material.AIR) {
                temp.setType(Material.WEB);
                tombBlocks.add(temp);
            }
            temp = feet.getRelative(0, 0, 1);
            if (temp.getType() == Material.AIR) {
                temp.setType(Material.WEB);
                tombBlocks.add(temp);
            }
            temp = feet.getRelative(0, 1, 1);
            if (temp.getType() == Material.AIR) {
                temp.setType(Material.WEB);
                tombBlocks.add(temp);
            }
            temp = feet.getRelative(0, 0, -1);
            if (temp.getType() == Material.AIR) {
                temp.setType(Material.WEB);
                tombBlocks.add(temp);
            }
            temp = feet.getRelative(0, 1, -1);
            if (temp.getType() == Material.AIR) {
                temp.setType(Material.WEB);
                tombBlocks.add(temp);
            }
            temp = feet.getRelative(0, -1, 0);
            if (temp.getType() == Material.AIR) {
                temp.setType(Material.WEB);
                tombBlocks.add(temp);
                temp = feet.getRelative(0, 2, 0);
                if (temp.getType() == Material.AIR) {
                    temp.setType(Material.WEB);
                    tombBlocks.add(temp);
                }
            }
            //this.blocks.addAll(tombBlocks);
            teiasCriadas.addAll(tombBlocks);
            for (Block bl : tombBlocks) {
                KoM.rewind.put(bl, Material.AIR);
            }
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(KoM._instance, new LimpaTeia(tombBlocks), 250);
        }
    }

    public void prendeEnt(Entity e) {

        Location loc = e.getLocation();
        PlayEffect.play(VisualEffect.FIREWORKS_EXPLODE, e.getLocation(), "type:ball color:white");
        ArrayList<Block> tombBlocks = new ArrayList<Block>();
        Block feet = loc.getBlock();

        Block temp = feet.getRelative(1, 0, 0);
        if (temp.getType() == Material.AIR) {
            temp.setType(Material.WEB);
            tombBlocks.add(temp);
        }
        temp = feet.getRelative(1, 1, 0);
        if (temp.getType() == Material.AIR) {
            temp.setType(Material.WEB);
            tombBlocks.add(temp);
        }
        temp = feet.getRelative(-1, 0, 0);
        if (temp.getType() == Material.AIR) {
            temp.setType(Material.WEB);
            tombBlocks.add(temp);
        }
        temp = feet.getRelative(-1, 1, 0);
        if (temp.getType() == Material.AIR) {
            temp.setType(Material.WEB);
            tombBlocks.add(temp);
        }
        temp = feet.getRelative(0, 0, 1);
        if (temp.getType() == Material.AIR) {
            temp.setType(Material.WEB);
            tombBlocks.add(temp);
        }
        temp = feet.getRelative(0, 1, 1);
        if (temp.getType() == Material.AIR) {
            temp.setType(Material.WEB);
            tombBlocks.add(temp);
        }
        temp = feet.getRelative(0, 0, -1);
        if (temp.getType() == Material.AIR) {
            temp.setType(Material.WEB);
            tombBlocks.add(temp);
        }
        temp = feet.getRelative(0, 1, -1);
        if (temp.getType() == Material.AIR) {
            temp.setType(Material.WEB);
            tombBlocks.add(temp);
        }
        temp = feet.getRelative(0, -1, 0);
        if (temp.getType() == Material.AIR) {
            temp.setType(Material.WEB);
            tombBlocks.add(temp);
            temp = feet.getRelative(0, 2, 0);
            if (temp.getType() == Material.AIR) {
                temp.setType(Material.WEB);
                tombBlocks.add(temp);
            }
        }

        //this.blocks.addAll(tombBlocks);
        teiasCriadas.addAll(tombBlocks);
        for (Block b : tombBlocks) {
            KoM.rewind.put(b, Material.AIR);
        }
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(KoM._instance, new LimpaTeia(tombBlocks), 250);

    }

    public boolean protegeTeia(Block teia) {
        if (this.teiasCriadas.contains(teia) || this.paredesCriadas.contains(teia)) {
            return true;
        }
        return false;
    }

    public class LimpaTeia implements Runnable {

        ArrayList<Block> teias;

        public LimpaTeia(ArrayList<Block> teeeeia) {
            this.teias = teeeeia;
        }

        public void run() {
            for (Block block : this.teias) {
                if (block.getType() == Material.WEB) {
                    block.setType(Material.AIR);
                }
                KoM.rewind.remove(block);
            }

            Wizard.this.teiasCriadas.removeAll(this.teias);
        }
    }

    public class LimpaMuro implements Runnable {

        ArrayList<Block> teias;

        public LimpaMuro(ArrayList<Block> teeeeia) {
            this.teias = teeeeia;
        }

        public void run() {
            for (Block block : this.teias) {
                if (block.getType() == Material.GLOWSTONE) {
                    block.setType(Material.AIR);
                }
            }
            Wizard.this.paredesCriadas.removeAll(this.teias);
        }
    }
}
