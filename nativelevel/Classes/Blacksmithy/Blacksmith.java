package nativelevel.Classes.Blacksmithy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import me.fromgate.playeffect.PlayEffect;
import me.fromgate.playeffect.VisualEffect;
import nativelevel.Equipment.EquipmentEvents;
import nativelevel.Equipment.Generator.EquipGenerator;
import nativelevel.Classes.Thief;
import nativelevel.Crafting.CraftCache;
import nativelevel.Crafting.Craftable;
import nativelevel.Custom.CustomItem;
import nativelevel.CustomEvents.BeginCraftEvent;
import nativelevel.CustomEvents.FinishCraftEvent;
import nativelevel.Jobs;
import nativelevel.Jobs.Sucesso;
import nativelevel.Menu.Menu;
import nativelevel.KoM;
import nativelevel.Lang.L;
import nativelevel.Lang.LangMinecraft;
import nativelevel.Attributes.Stamina;
import nativelevel.bencoes.TipoBless;
import nativelevel.gemas.Raridade;
import nativelevel.rankings.Estatistica;
import nativelevel.rankings.RankDB;
import nativelevel.sisteminhas.KomSystem;
import nativelevel.spec.PlayerSpec;
import nativelevel.sisteminhas.XP;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Blacksmith extends KomSystem {

    public static boolean usaBigorna(Player p) {

        int x = p.getLocation().getBlockX();
        int z = p.getLocation().getBlockZ();

        for (int xx = x - 2; xx < x + 2; xx++) {
            for (int zz = z - 2; zz < z + 2; zz++) {

                if (KoM.debugMode && p.isOp()) {
                    p.sendMessage("Procurando em " + xx + "-" + p.getLocation().getBlockY() + "-" + zz);
                    p.sendMessage("bloco " + p.getWorld().getBlockAt(xx, p.getLocation().getBlockY(), zz).getType().name());
                }

                if (p.getWorld().getBlockAt(xx, p.getLocation().getBlockY(), zz).getType() == Material.ANVIL) {

                    Block bigorna = p.getWorld().getBlockAt(xx, p.getLocation().getBlockY(), zz);

                    if (Jobs.rnd.nextInt(3) == 1) {
                        int damage = bigorna.getState().getData().getData();
                        damage += 4;
                        if (damage >= 12) {
                            bigorna.setType(Material.AIR);
                        } else {
                            bigorna.setData((byte) damage);
                            bigorna.getState().getData().setData((byte) damage);
                            bigorna.getState().update();
                        }
                    }

                    PlayEffect.play(VisualEffect.SOUND, p.getLocation(), "type:anvil");
                    PlayEffect.play(VisualEffect.FIREWORKS_SPARK, bigorna.getRelative(BlockFace.UP).getLocation(), "");
                    p.sendMessage(ChatColor.GREEN + L.m("Voce usou a bigorna para aumentar as chances de sua criação !"));
                    return true;
                }
            }
        }

        return false;
    }

    public static Material getToolLevel(Material m) {
        if (m == Material.STONE_SWORD || m == Material.STONE_AXE || m == Material.STONE_PICKAXE || m == Material.STONE_HOE) {
            return Material.STONE;
        } else if (m == Material.IRON_SWORD || m == Material.IRON_AXE || m == Material.IRON_PICKAXE || m == Material.IRON_HOE) {
            return Material.IRON_INGOT;
        } else if (m == Material.GOLD_AXE || m == Material.GOLD_PICKAXE || m == Material.GOLD_HOE) {
            return Material.GOLD_INGOT;
        } else if (m == Material.DIAMOND_SWORD || m == Material.DIAMOND_AXE || m == Material.DIAMOND_PICKAXE || m == Material.DIAMOND_HOE) {
            return Material.DIAMOND;
        } else if (m == Material.IRON_BOOTS || m == Material.IRON_CHESTPLATE || m == Material.IRON_HELMET || m == Material.IRON_LEGGINGS) {
            return Material.IRON_INGOT;
        } else if (m == Material.GOLD_BOOTS || m == Material.GOLD_CHESTPLATE || m == Material.GOLD_HELMET || m == Material.GOLD_LEGGINGS) {
            return Material.GOLD_INGOT;
        } else if (m == Material.DIAMOND_BOOTS || m == Material.DIAMOND_CHESTPLATE || m == Material.DIAMOND_HELMET || m == Material.DIAMOND_LEGGINGS) {
            return Material.DIAMOND;
        } else if (m == Material.CHAINMAIL_BOOTS || m == Material.CHAINMAIL_CHESTPLATE || m == Material.CHAINMAIL_HELMET || m == Material.CHAINMAIL_LEGGINGS) {
            return Material.IRON_ORE;
        } else if (m == Material.LEATHER_BOOTS || m == Material.LEATHER_CHESTPLATE || m == Material.LEATHER_HELMET || m == Material.LEATHER_LEGGINGS) {
            return Material.LEATHER;
        }
        return null;
    }

    public static int numeroQueVaiFabricar(ItemStack[][] inv) {
        int min = 999;
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (inv[x][y] != null) {
                    if (inv[x][y].getAmount() < min) {
                        min = inv[x][y].getAmount();
                    }
                }
            }
        }
        return min;
    }

    public static void evitaFogo(EntityDamageEvent event, Player p) {
        int nivel = Jobs.getJobLevel("Ferreiro", p);
        if (nivel == 1) {
            event.setDamage(0D);
            event.setCancelled(true);
        }
    }

    public static void criaItem(BeginCraftEvent ev) {
        boolean usouBigorna = false;
        if (ev.getPlayer().getLevel() > 30) {
            usouBigorna = usaBigorna(ev.getPlayer());
        }
        if (ev.getResult().getType().name().contains("IRON") || ev.getResult().getType().name().contains("DIAMOND") || ev.getResult().getType().name().contains("GOLD") || ev.getResult().getType().name().contains("CHAIN")) {
            if (usouBigorna) {
                ev.setChanceBonus(ev.getChanceBonus() + 1);
            } else {
                ev.setChanceBonus(ev.getChanceBonus() - 5);
            }
        }

        TipoBless ativo = TipoBless.save.getTipo(ev.getPlayer());
        if (ativo != null && ativo == TipoBless.Forjar) {
            ev.setChanceBonus(ev.getChanceBonus() + 15);
            ev.setMultiplyExp(10);
        }
    }

    public static void terminaDeCriarItem(FinishCraftEvent ev) {
        ev.getPlayer().playSound(ev.getPlayer().getLocation(), Sound.BLOCK_ANVIL_USE, 1.2F, 1);

        if(!EquipmentEvents.isWeapon(ev.getResult()) && !EquipmentEvents.isArmor(ev.getResult())) {
            return;
        }
        
        int chanceExceptional = 1 + (ev.getPlayer().getLevel() / 10);
        if (PlayerSpec.temSpec(ev.getPlayer(), PlayerSpec.Forjador)) {
            chanceExceptional *= 2;
        }
        if (Jobs.rnd.nextInt(100) < chanceExceptional) {
            Raridade rar = Raridade.Comum;
            int sorte = Jobs.rnd.nextInt(ev.getPlayer().getLevel());
            if (sorte == 99) {
                rar = Raridade.Epico;
            } else if (sorte > 95) {
                rar = Raridade.Raro;
            } else if (sorte > ev.getPlayer().getLevel() / 2) {
                rar = Raridade.Incomum;
            }
            ItemStack item = ev.getResult();
            EquipGenerator.gera(item, rar);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(rar.getIcone() + " " + LangMinecraft.get().get(item) + " Excepcional");
            item.setItemMeta(meta);
            item.addEnchantment(Enchantment.DURABILITY, Jobs.rnd.nextInt(3)+1);
            ev.getPlayer().sendMessage(ChatColor.GREEN + L.m("Voce fez um item Excepcional !"));
        }
        RankDB.addPontoCache(ev.getPlayer(), Estatistica.FERREIRO, 1);
    }

    public static void tentaDerrubarArmadura(EntityDamageByEntityEvent ev) {

        Player p = (Player) ev.getDamager();
        Player inimigo = (Player) ev.getEntity();
        if (Jobs.getJobLevel("Ferreiro", p) == 1 && p.getLevel() >= 14) {
            int custo = 40;
            if (PlayerSpec.temSpec(p, PlayerSpec.Macador)) {
                custo = 10;
            }
            if (Stamina.spendStamina(p, custo)) {

                if (Thief.taInvisivel(p)) {
                    Thief.revela(p);
                }

                p.sendMessage(ChatColor.GREEN + L.m("Voce deu um golpe esmagador !"));
                int random = Jobs.rnd.nextInt(4);
                if (random == 1 && inimigo.getInventory().getBoots() != null) {
                    if (inimigo.getInventory().getBoots().getType() == Material.LEATHER_BOOTS) {
                        inimigo.getInventory().setBoots(null);
                    } else {
                        inimigo.getInventory().getBoots().setDurability((short) (inimigo.getInventory().getBoots().getDurability() + 20));
                    }
                } else if (random == 2 && inimigo.getInventory().getLeggings() != null) {
                    if (inimigo.getInventory().getLeggings().getType() == Material.LEATHER_LEGGINGS) {
                        inimigo.getInventory().setLeggings(null);
                    } else {
                        inimigo.getInventory().getLeggings().setDurability((short) (inimigo.getInventory().getLeggings().getDurability() + 20));
                    }
                } else if (random == 3 && inimigo.getInventory().getChestplate() != null) {
                    if (inimigo.getInventory().getChestplate().getType() == Material.LEATHER_CHESTPLATE) {
                        inimigo.getInventory().setChestplate(null);
                    } else {
                        inimigo.getInventory().getChestplate().setDurability((short) (inimigo.getInventory().getChestplate().getDurability() + 20));
                    }
                } else if (random == 0 && inimigo.getInventory().getHelmet() != null) {
                    if (inimigo.getInventory().getHelmet().getType() == Material.LEATHER_HELMET) {
                        inimigo.getInventory().setHelmet(null);
                    } else {
                        inimigo.getInventory().getHelmet().setDurability((short) (inimigo.getInventory().getHelmet().getDurability() + 20));
                    }
                }
                inimigo.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 6, 10));
                inimigo.sendMessage(ChatColor.RED + L.m("Tomou um golpe esmagador de ") + p.getName());
                PlayEffect.play(VisualEffect.CRIT_MAGIC, inimigo.getLocation(), "num:10");
            }

        }

    }

    public static void setDurabilityPC(ItemStack item, int percent) {
        if (percent > 100) {
            percent = 100;
        }
        item.setDurability((short) (item.getType().getMaxDurability() - (percent * item.getType().getMaxDurability()) / 100));
    }

    public static Material getArmorType(Material s) {
        if (s == Material.IRON_BOOTS || s == Material.IRON_CHESTPLATE || s == Material.IRON_HELMET || s == Material.IRON_LEGGINGS) {
            return Material.IRON_INGOT;
        }
        if (s == Material.GOLD_BOOTS || s == Material.GOLD_CHESTPLATE || s == Material.GOLD_HELMET || s == Material.GOLD_LEGGINGS) {
            return Material.GOLD_INGOT;
        }
        if (s == Material.CHAINMAIL_BOOTS || s == Material.CHAINMAIL_CHESTPLATE || s == Material.CHAINMAIL_HELMET || s == Material.CHAINMAIL_LEGGINGS) {
            return Material.IRON_BARDING;
        }
        if (s == Material.DIAMOND_BOOTS || s == Material.DIAMOND_CHESTPLATE || s == Material.DIAMOND_HELMET || s == Material.DIAMOND_LEGGINGS) {
            return Material.DIAMOND;
        }
        if (s == Material.LEATHER_BOOTS || s == Material.LEATHER_CHESTPLATE || s == Material.LEATHER_HELMET || s == Material.LEATHER_LEGGINGS) {
            return Material.LEATHER;
        }
        return null;
    }
}
