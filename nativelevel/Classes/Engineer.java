
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import me.fromgate.playeffect.PlayEffect;
import me.fromgate.playeffect.VisualEffect;
import nativelevel.CFG;
import nativelevel.Listeners.GeneralListener;
import nativelevel.Jobs;
import nativelevel.Lang.PT;
import nativelevel.Menu.Menu;
import nativelevel.KoM;
import nativelevel.Lang.L;
import nativelevel.MetaShit;
import nativelevel.config.ConfigKom;
import nativelevel.config.ItemJob;
import nativelevel.sisteminhas.XP;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Bat;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Engineer {


    public static HashMap<UUID, UUID> leashed = new HashMap<UUID, UUID>();

    public static boolean validaPrisao(PlayerInteractEvent ev) {
        if (ev.getPlayer().hasMetadata("prendeu")) {
            if (ev.getPlayer().hasMetadata("euPrendi")) {
                if (ev.getPlayer().hasMetadata("cabouDePrender")) {
                    ev.getPlayer().removeMetadata("cabouDePrender", KoM._instance);
                    return false;
                }
                UUID prendeu = (UUID) MetaShit.getMetaObject("prendeu", ev.getPlayer());
                Player preso = Bukkit.getPlayer(prendeu);
                if (preso != null) {
                    preso.removeMetadata("prendeu", KoM._instance);
                    preso.sendMessage(ChatColor.GREEN + L.m("Voce foi solto !"));
                }
                ev.getPlayer().removeMetadata("euPrendi", KoM._instance);
                ev.getPlayer().removeMetadata("prendeu", KoM._instance);
                ev.getPlayer().sendMessage(ChatColor.GREEN + L.m("Voce soltou o alvo !"));
                if (preso.getPassenger() != null) {
                    ((Bat) preso.getPassenger()).setLeashHolder(null);
                    preso.getPassenger().remove();
                }
            } else {
                ev.setCancelled(true);
                UUID prendeu = (UUID) MetaShit.getMetaObject("prendeu", ev.getPlayer());
                Player quemPrendeu = Bukkit.getPlayer(prendeu);
                if (quemPrendeu == null) {
                    ev.getPlayer().removeMetadata("prendeu", KoM._instance);
                    ev.getPlayer().sendMessage(ChatColor.GREEN + L.m("Voce se soltou !"));
                    if (ev.getPlayer().getPassenger() != null) {
                        ((Bat) ev.getPlayer().getPassenger()).setLeashHolder(null);
                        ev.getPlayer().getPassenger().remove();
                    }
                    return false;
                } else {
                    if (quemPrendeu.getLocation().distance(ev.getPlayer().getLocation()) > 5) {
                        quemPrendeu.removeMetadata("euPrendi", KoM._instance);
                        quemPrendeu.removeMetadata("prendeu", KoM._instance);
                        ev.getPlayer().removeMetadata("prendeu", KoM._instance);
                        if (ev.getPlayer().getPassenger() != null) {
                            ev.getPlayer().getPassenger().remove();
                        }
                        quemPrendeu.sendMessage(ChatColor.GREEN + L.m("O Alvo se soltou !"));
                        ev.getPlayer().sendMessage(ChatColor.GREEN + L.m("Voce se soltou !"));
                        if (ev.getPlayer().getPassenger() != null) {
                            ((Bat) ev.getPlayer().getPassenger()).setLeashHolder(null);
                            ev.getPlayer().getPassenger().remove();
                        }
                        return false;
                    }
                }
                ev.setCancelled(true);
                ev.setUseItemInHand(Event.Result.DENY);
                ev.setUseInteractedBlock(Event.Result.DENY);
                ev.getPlayer().sendMessage(ChatColor.RED + L.m("Voce esta amarrado e nao consegue usar as maos !"));
                return true;
            }
        }
        return false;
    }

    public static void prende(PlayerInteractEntityEvent ev) {
        if (!CFG.mundoGuilda(ev.getPlayer().getLocation()) && !ev.getPlayer().getWorld().getName().equalsIgnoreCase("woe") && !ev.getPlayer().getWorld().getName().equalsIgnoreCase("arena")) {
            return;
        }
        if (ev.getPlayer().getItemInHand() != null && ev.getPlayer().getItemInHand().getType() == Material.LEASH) {
            if (Jobs.getJobLevel("Engenheiro", ev.getPlayer()) == 1) {
                if (ev.getRightClicked() != null && ev.getRightClicked().getType() == EntityType.PLAYER) {

                    if (!KoM.tem(ev.getPlayer().getInventory(), Material.REDSTONE)) {
                        ev.getPlayer().sendMessage(ChatColor.RED + L.m("Voce precisa redstones para ativar a corrente !"));
                        return;
                    }
                    KoM.removeInventoryItems(ev.getPlayer().getInventory(), Material.REDSTONE, 1);
                    Player alvo = (Player) ev.getRightClicked();
                    if(Jobs.getJobLevel("Ladino", alvo)==1) {
                         ev.getPlayer().sendMessage(ChatColor.RED+L.m("O alvo rapidamente se desenrolou da corda !"));
                         return;
                    }
                    // prendendo
                    Bat porco = (Bat) ev.getPlayer().getWorld().spawnEntity(ev.getPlayer().getLocation(), EntityType.BAT);
                    porco.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999,1));
                   // alvo.addPassenger(porco);
                    MetaShit.setMetaObject("cabouDePrender", ev.getPlayer(), "sim");
                    MetaShit.setMetaObject("prendeu", ev.getRightClicked(), ev.getPlayer().getUniqueId());
                    MetaShit.setMetaObject("prendeu", ev.getPlayer(), ev.getRightClicked().getUniqueId());
                    MetaShit.setMetaObject("euPrendi", ev.getPlayer(), ev.getRightClicked().getUniqueId());
                    porco.setLeashHolder(ev.getPlayer());

                    alvo.addPassenger(porco);
                   
                    ev.getPlayer().sendMessage(ChatColor.GREEN + L.m("Voce prendeu o alvo !"));
                    ev.getPlayer().sendMessage(ChatColor.GREEN + L.m("Se voce fizer algo ou ir muito longe ira soltar o alvo !"));
                    alvo.sendMessage(ChatColor.RED + ev.getPlayer().getName() + L.m(" Te segurou numa corrente eletrica"));
                }
            }
        }
    }

}
