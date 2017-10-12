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
package nativelevel.Custom.Items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import me.fromgate.playeffect.PlayEffect;
import me.fromgate.playeffect.VisualEffect;
import nativelevel.Custom.CustomItem;
import nativelevel.Jobs;
import nativelevel.KoM;
import nativelevel.sisteminhas.ClanLand;
import nativelevel.Lang.L;
import nativelevel.Attributes.Mana;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Detonador extends CustomItem {

    public Detonador() {
        super(Material.WATCH, L.m("Detonador"), L.m("Detona suas armadilhas e bombas"), CustomItem.INCOMUM);
    }

    public static void explodeTrap(Location l, LivingEntity p, Player causador) {
        if (p != null && p instanceof Player) {
            ((Player) p).sendMessage(ChatColor.RED + L.m("Voce disparou uma armadilha !"));
            p.damage(15D);
        }
        l.setY(l.getY() + 1);
        PlayEffect.play(VisualEffect.EXPLOSION_LARGE, l, "num:1");

        Entity alvo = (p != null ? p : l.getWorld().spawnEntity(l, EntityType.ARROW));
        for (Entity e : alvo.getNearbyEntities(3, 3, 3)) {
            if (e instanceof LivingEntity) {
                if (e != p) {
                    EntityDamageByEntityEvent ev = new EntityDamageByEntityEvent(causador, e, EntityDamageEvent.DamageCause.ENTITY_EXPLOSION, 12D);
                    Bukkit.getPluginManager().callEvent(ev);
                    if (!ev.isCancelled()) {
                        KoM.dealTrueDamage((LivingEntity)e, ev.getDamage());
                        if(e.getType()==EntityType.PLAYER) {
                            ((Player)e).sendMessage(ChatColor.RED+"Uma armadilha explodiu !");
                        }
                    }
                }
            }
        }
        if (alvo instanceof Arrow) {
            alvo.remove();
        }
    }

    @Override
    public boolean onItemInteract(final Player p) {
        int lvl = Jobs.getJobLevel("Engenheiro", p);
        if (lvl != 1) {
            p.sendMessage(ChatColor.RED + L.m("Apenas engenheiros experientes sabem usar isto."));
            return true;
        }
        if (p.hasPotionEffect(PotionEffectType.SLOW)) {
            return true;
        }
        if (!Mana.spendMana(p, 5)) {
            return true;
        }
        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 2, 0));
        Block explodiu = null;
        for (Block arma : Armadilha.armadilhas.keySet()) {
            UUID u = Armadilha.armadilhas.get(arma);
            if (u == p.getUniqueId()) {

                explodiu = arma;
                break;
            }
        }
        if (explodiu != null) {
            explodeTrap(explodiu.getLocation(), null, p);
            Armadilha.armadilhas.remove(explodiu);
            p.sendMessage(ChatColor.RED + L.m("Voce explodiu uma armadilha !"));
        }

        return true;
    }
}
