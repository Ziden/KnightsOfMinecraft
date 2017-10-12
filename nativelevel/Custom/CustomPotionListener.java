package nativelevel.Custom;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import nativelevel.Classes.Thief;
import nativelevel.Jobs;
import nativelevel.Jobs.TipoClasse;
import nativelevel.KoM;
import nativelevel.Lang.L;
import nativelevel.integration.WorldGuardKom;
import nativelevel.sisteminhas.ClanLand;
import nativelevel.sisteminhas.KomSystem;
import nativelevel.utils.Cooldown;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author vntgasl
 *
 */

public class CustomPotionListener extends KomSystem {

    public HashSet<UUID> bebendo = new HashSet<UUID>();

    @EventHandler(priority=EventPriority.HIGHEST)
    public void potionStack(InventoryClickEvent ev) {
        KoM.debug("Custom potion inv click");
        if (ev.getCurrentItem() != null && ev.getCursor() != null) {
            CustomPotion clicando = CustomPotion.getCustomItem(ev.getCurrentItem());
            CustomPotion pondo = CustomPotion.getCustomItem(ev.getCursor());
            KoM.debug("tentando potion stack");
            if (pondo != null && clicando != null && pondo.name.equalsIgnoreCase(clicando.name)) {
                ev.getCurrentItem().setAmount(ev.getCurrentItem().getAmount() + ev.getCursor().getAmount());
                ev.setCursor(new ItemStack(Material.AIR));
                ev.setCancelled(true);
                KoM.debug("Stackei a potion");
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void dano(EntityDamageEvent ev) {
        if (ev.getEntity().getType() == EntityType.PLAYER && ev.getCause() != DamageCause.POISON && ev.getCause() != DamageCause.WITHER) {
            if (bebendo.contains(ev.getEntity().getUniqueId())) {
                bebendo.remove(ev.getEntity().getUniqueId());
                ((Player) ev.getEntity()).sendMessage(ChatColor.RED + "Voce se babou todo");
                KoM.efeitoBlocos(ev.getEntity(), Material.LAPIS_BLOCK);
            }
        }
    }

    @EventHandler
    public void interage(PlayerInteractEvent ev) {
        if (ev.getItem() != null) {
            CustomPotion potion = CustomPotion.getCustomItem(ev.getItem());
            if (potion != null) {
                bebendo.add(ev.getPlayer().getUniqueId());
            }
        }
    }

    @EventHandler
    public void consume(PlayerItemConsumeEvent ev) {
        if(!ev.getPlayer().getItemInHand().isSimilar(ev.getItem())) {
            ev.setCancelled(true);
            ev.getPlayer().sendMessage(ChatColor.RED+"Voce apenas pode usar isto na mão principal");
            return;
        }
        CustomPotion item = CustomPotion.getCustomItem(ev.getItem());
        if (item != null) {
            if (bebendo.contains(ev.getPlayer().getUniqueId())) {
                bebendo.remove(ev.getPlayer().getUniqueId());
                item.drink(ev);
                if (!ev.isCancelled()) {
                    KoM.act(ev.getPlayer(), ev.getPlayer().getName() + " bebeu uma " + item.name);
                    ev.setCancelled(true);
                    if (ev.getItem().getAmount() > 1) {
                        ev.getPlayer().getInventory().addItem(new ItemStack(Material.GLASS_BOTTLE, 1));
                        ev.getPlayer().getItemInHand().setAmount(ev.getItem().getAmount() - 1);
                    } else {
                        ev.getPlayer().setItemInHand(new ItemStack(Material.GLASS_BOTTLE, 1));
                    }
                }
            } else {
                ev.getPlayer().sendMessage(ChatColor.RED + "Voce nao conseguiu beber a pocao");
                ev.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void potionSplash(PotionSplashEvent event) {

        KoM.debug("potion splash");
        
        if ((event.getPotion().getShooter() == null || !(event.getPotion().getShooter() instanceof LivingEntity)) || ((LivingEntity) event.getPotion().getShooter()).getType() != EntityType.PLAYER) {
            return;
        }

        Player p = ((Player) event.getPotion().getShooter());
        if (Thief.taInvisivel(p)) {
            Thief.revela(p);
        }
        

        
        CustomPotion item = CustomPotion.getCustomItem(event.getEntity().getItem());
        if (item != null) {
            KoM.debug("Achei CI");
            String tipo = ClanLand.getTypeAt(event.getEntity().getLocation());
            KoM.debug("TIPO=" + tipo);
            Set<LivingEntity> fica = new HashSet<LivingEntity>();

            for (Entity e : event.getAffectedEntities()) {
                if (e.getType() != EntityType.PLAYER
                        || (!event.getEntity().getWorld().getName().equalsIgnoreCase("dungeon")
                        && !tipo.equalsIgnoreCase("SAFE")
                        && !WorldGuardKom.ehSafeZone(event.getEntity().getLocation()))) {
                    fica.add((LivingEntity) e);
                } else {
                    event.setIntensity((LivingEntity) e, 0);
                }
            }
            KoM.debug("Tinha " + event.getAffectedEntities().size());

            event.getAffectedEntities().clear();

            KoM.debug("zerei " + event.getAffectedEntities().size());

            for (LivingEntity ficar : fica) {
                event.getAffectedEntities().add(ficar);
            }

            KoM.debug("Agora tem " + event.getAffectedEntities().size());
            item.splashEvent(event, p);
            event.setCancelled(true);
        }

    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void interage2(PlayerInteractEvent ev) {
        if (ev.getAction() == Action.RIGHT_CLICK_BLOCK || ev.getAction() == Action.RIGHT_CLICK_AIR) {
            if (ev.getItem() != null) {
                CustomPotion item = CustomPotion.getCustomItem(ev.getItem());
                if (item != null) {

                    if (item.isSplash) {
                        
                        TipoClasse tipo = Jobs.getJobLevel(Jobs.Classe.Alquimista, ev.getPlayer());

                        if (Cooldown.isCooldown(ev.getPlayer(), "throwPotion")) {
                            ev.setCancelled(true);
                            return;
                        }
                        Cooldown.setMetaCooldown(ev.getPlayer(), "throwPotion", 2000);

                        double alchemySkill = ev.getPlayer().getLevel();
                        double diff = item.getMinimumSkill();
                        if(diff > 100)
                            diff = 100;
                        if (alchemySkill < diff || tipo != TipoClasse.PRIMARIA) {
                            ev.getPlayer().sendMessage(ChatColor.RED + L.m("Você não tem experiência de alquimia suficiente para arremeçar isso !"));
                            ev.setCancelled(true);
                            return;
                        }
                    }
                    item.interage(ev);
                    if (item.blockInteract()) {
                        ev.setCancelled(true);
                    }
                }
            }
        }
    }

}
