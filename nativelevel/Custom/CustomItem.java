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
package nativelevel.Custom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nativelevel.Equipment.WeaponDamage;
import nativelevel.Classes.Thief;
import nativelevel.Custom.Items.LogoutTrap;
import nativelevel.Custom.Items.TeleportScroll;
import nativelevel.Jobs;
import nativelevel.utils.MetaUtils;
import nativelevel.KoM;
import nativelevel.sisteminhas.ClanLand;
import nativelevel.Custom.Items.CajadoElemental;
import nativelevel.Lang.L;
import nativelevel.Listeners.GeneralListener;
import nativelevel.MetaShit;
import nativelevel.Attributes.Mana;
import nativelevel.sisteminhas.Tralhas;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.EntityEffect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public abstract class CustomItem {

    Material m;
    List<String> lore;
    String name;
    int raridade;

    public static int COMUM = 0;
    public static int INCOMUM = 1;
    public static int RARO = 2;
    public static int EPICO = 3;
    public static int LENDARIO = 4;
    public static int LIMITADO = 5;

    private ChatColor pegaCorPelaRaridade(int raridade) {
        if (raridade == INCOMUM) {
            return ChatColor.GREEN;
        }
        if (raridade == RARO) {
            return ChatColor.BLUE;
        }
        if (raridade == EPICO) {
            return ChatColor.DARK_PURPLE;
        }
        if (raridade == LIMITADO) {
            return ChatColor.YELLOW;
        }
        return ChatColor.GOLD;
    }

    public static void addEffect(String effect, ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        meta.getLore().add(ChatColor.GREEN + effect);
    }

    public CustomItem(Material m, String name, String descEffect, int raridade) {
        this.m = m;
        this.lore = new ArrayList<String>();
        this.name = name;
        this.raridade = raridade;
        this.lore.add(ChatColor.GRAY + "-" + descEffect);
        this.lore.add(ChatColor.BLACK + ":" + this.name);
    }

    public static <T extends CustomItem> T getItem(Class<T> c) {
        if (ItemLoader.loaded.containsKey(c)) {
            return (T) ItemLoader.loaded.get(c);
        }
        return null;
    }

    public static boolean podeUsar(Player p, ItemStack i) {
        ItemMeta meta = i.getItemMeta();
        if (meta == null) {
            return true;
        }
        List<String> lore = meta.getLore();
        if (lore == null) {
            return true;
        }
        if (lore.size() > 0) {
            for (String l : lore) {
                l = ChatColor.stripColor(l);
                String[] split = l.split(":");
                if (split.length > 1 && split[0].trim().equalsIgnoreCase("Classe")) {
                    int job = Jobs.getJobLevel(split[1].trim(), p);
                    if (KoM.debugMode) {
                        KoM.log.info("vendo se pode equipar coisa de " + split[1].trim());
                    }
                    if (job != 1) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static void hitWithCustomItem(String customItem, EntityDamageByEntityEvent event, Player bateu, Player tomou) {
        if (customItem.equalsIgnoreCase("Logout Trap")
                && !ClanLand.getTypeAt(event.getEntity().getLocation()).equalsIgnoreCase("SAFE")) {

            String nome = ((Player) event.getEntity()).getName();
            if (!LogoutTrap.trapeados.contains(nome)) {
                LogoutTrap.trapeados.add(nome);
                tomou.sendMessage(ChatColor.GREEN + L.m("Uma armadilha de logout foi colocada em voce !"));
                bateu.sendMessage(ChatColor.GOLD + L.m("Voce ativou a armadilha de logou !"));
                tomou.sendMessage(ChatColor.GREEN + L.m("Se voce deslogar agora, voce MORRE !!"));
                tomou.playSound(tomou.getLocation(), Sound.BLOCK_IRON_DOOR_CLOSE, 10, 0);
                Bukkit.getScheduler().scheduleSyncDelayedTask(KoM._instance, CustomItem.getItem(LogoutTrap.class).getLimpaTrap(nome), 20 * 20L);
            }
            return;
        } else if (customItem.equalsIgnoreCase("Adaga da Ponta Diamantada")) {
            try {
                if (Thief.taInvisivel(bateu) && event.getDamage() > 0) {

                    if (!Mana.spendMana(bateu, 35)) {
                        return;
                    }

                    double angle = Tralhas.getAngle(tomou.getLocation().getDirection(), bateu.getLocation().getDirection());
                    Thief.revela(bateu);
                    if (angle > 70) {
                        bateu.sendMessage(L.m("Voce errou o backstab pois precisa estar por traz do alvo !"));
                        event.setDamage(1D);
                        return;
                    }
                   // bateu.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 1));
                    event.setDamage(15);
                    //tomou.damage(15 + Jobs.rnd.nextInt(10), bateu);
                    if (tomou.getLocation().getBlock().getLightLevel() < 6) {
                        bateu.sendMessage(ChatColor.GOLD + L.m("Voce acertou um backstab na escuridão!"));
                        tomou.sendMessage(ChatColor.RED + L.m("Voce foi pego com um backstab no escurinho !"));
                        event.setDamage(event.getDamage() * 2);
                    } else {
                        bateu.sendMessage(ChatColor.GOLD + L.m("Voce acertou um backstab !"));
                        tomou.sendMessage(ChatColor.RED + L.m("Voce levou um backstab !"));
                    }

                    tomou.playSound(tomou.getLocation(), Sound.ENTITY_IRONGOLEM_ATTACK, 10, 0);
                    bateu.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 5, 0));
                    tomou.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 5, 0));
                    tomou.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20 * 20, 0));
                    MetaShit.setMetaObject("wither", tomou, bateu.getUniqueId());
                    //tomou.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 20 * 10, 1));
                    if (Jobs.getJobLevel("Mago", tomou) == 1) {
                        event.setDamage(event.getDamage() * 1.4);
                    }

                }

            } catch (Throwable ex) {
                KoM.log.info(ex.getMessage());
            }
        } else if (customItem.equalsIgnoreCase("Cajado Elemental") && (event.getCause() == DamageCause.ENTITY_ATTACK || event.getCause() == DamageCause.CONTACT)) {

            if (Thief.taInvisivel(bateu)) {
                Thief.revela(bateu);
            }

            if (!Mana.spendMana(bateu, 15)) {
                return;
            }

            if (Jobs.getJobLevel("Mago", bateu) == 1) {
                String elemento = CajadoElemental.getElemento(bateu.getItemInHand()).trim();
                if (elemento == null) {
                    return;
                }
                if (elemento != null) {
                    if (KoM.debugMode) {
                        KoM.log.info("Batendo com elemento " + elemento);
                    }
                    DamageCause cause = null;
                    if (elemento.equalsIgnoreCase("Fogo")) {
                        cause = DamageCause.FIRE;
                    } else if (elemento.equalsIgnoreCase("Agua")) {
                        cause = DamageCause.MAGIC;
                    } else if (elemento.equalsIgnoreCase("Raio")) {
                        cause = DamageCause.LIGHTNING;
                    } else if (elemento.equalsIgnoreCase("Veneno")) {
                        cause = DamageCause.POISON;
                    }
                    if (cause != null) {
                        if (KoM.debugMode) {
                            KoM.log.info("Batendo com cause " + cause.name());
                        }
                        tomou.setNoDamageTicks(0);
                        EntityDamageByEntityEvent ev = new EntityDamageByEntityEvent(bateu, tomou, cause, event.getDamage());
                        Bukkit.getPluginManager().callEvent(ev);
                        bateu.getItemInHand().setDurability((short) (bateu.getItemInHand().getDurability() + 1));
                        tomou.playEffect(EntityEffect.HURT);
                        //if(!ev.isCancelled()) {
                        double dano = ev.getDamage() * 0.6;
                        if (tomou.getHealth() <= dano) {
                            GeneralListener.ultimoDano.put(tomou.getUniqueId(), bateu.getUniqueId());
                        }
                        KoM.dealTrueDamage(tomou, ev.getDamage() * 0.4);
                        GeneralListener.ultimoDano.remove(tomou.getUniqueId());
                        tomou.setNoDamageTicks(20);
                        if (cause == DamageCause.POISON) {
                            tomou.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20 * 10, 0));
                        } else if (cause == DamageCause.FIRE) {
                            tomou.setFireTicks(20 * 10);
                        }
                        // } else if(KnightsOfMania.debugMode) {
                        //     KnightsOfMania.log.info("evento dano elemental cancelado");
                        // }
                        event.setCancelled(true);
                        event.setDamage(0D);
                    }
                }
            }
        }
    }

    public static int getRaridadeItem(ItemStack i) {
        ItemMeta meta = i.getItemMeta();
        String nome = meta.getDisplayName();
        if (nome == null) {
            return CustomItem.COMUM;
        }
        if (!nome.contains("♦")) {
            return CustomItem.COMUM;
        }
        String[] split = nome.split("♦");
        if (split.length == 0) {
            return CustomItem.COMUM;
        }
        String cor = split[0];
        KoM.log.info("COR: " + cor + " nome " + nome);
        return CustomItem.EPICO;
    }

    public String getName() {
        return name;
    }

    public static CustomItem getByName(String name) {
        if (ItemLoader.porNome.containsKey(name)) {
            return ItemLoader.porNome.get(name);
        }
        return null;
    }

    public static CustomItem getItem(ItemStack s) {
        if (s == null) {
            return null;
        }
        ItemMeta m = s.getItemMeta();
        if (m == null || m.getLore() == null || m.getLore().size() == 0) {
            return null;
        }
        String last = m.getLore().get(m.getLore().size() - 1);
        if (last.length() < 3) {
            return null;
        }
        String[] split = last.split(":");
        if (split.length != 2) {
            return null;
        }
        if (ItemLoader.porNome.containsKey(split[1])) {
            return ItemLoader.porNome.get(split[1]);
        }
        return null;
    }

    public static void openCustomItemInventory(Player p, int pagina) {
        int max = 9 * 6;
        int ct = 0;
        Inventory i = Bukkit.createInventory(p, max);
        for (CustomItem item : ItemLoader.loaded.values()) {
            if (pagina == 1 && ct < max) {
                i.addItem(item.generateItem());
            } else if (pagina == 2 && ct >= max) {
                i.addItem(item.generateItem());
            }
            ct++;
        }
        p.openInventory(i);
    }

    public static String getCustomItem(ItemStack ss) {
        CustomItem item = getItem(ss);
        if (item != null) {
            return item.getName();
        }
        return null;
    }
    
    public void interage(PlayerInteractEvent ev) {
        
    }

    public ItemStack generateItem() {
        ItemStack item = new ItemStack(m, 1, (byte) 0);
        item = WeaponDamage.checkForMods(item);
        MetaUtils.setItemNameAndLore(item, pegaCorPelaRaridade(this.raridade) + "♦ " + ChatColor.UNDERLINE + ChatColor.GOLD + name, lore.toArray(new String[lore.size()]));

        posCriacao(item);

        return item;//KoM.addGlow(item);
    }

    public void posCriacao(ItemStack ss) {

    }

    public ItemStack generateItem(int size) {
        ItemStack item = new ItemStack(m, size, (byte) 0);
        item = WeaponDamage.checkForMods(item);
        MetaUtils.setItemNameAndLore(item, pegaCorPelaRaridade(this.raridade) + "♦ " + ChatColor.UNDERLINE + ChatColor.GOLD + name, lore.toArray(new String[lore.size()]));
        posCriacao(item);

        return item; //KoM.addGlow(item);
    }

    public static ItemStack setItemNameAndLore(ItemStack item, String name, String... lore) {
        for (int i = 0; i < lore.length; i++) {
            lore[i] = ChatColor.RESET + lore[i];
        }
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(ChatColor.RESET + name);
        im.setLore(Arrays.asList(lore));
        item.setItemMeta(im);
        return item;
    }

    public abstract boolean onItemInteract(Player p);

}
