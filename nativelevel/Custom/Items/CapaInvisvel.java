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
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import nativelevel.Custom.CustomItem;
import nativelevel.Jobs;
import nativelevel.KoM;
import nativelevel.Lang.L;
import nativelevel.Attributes.Mana;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CapaInvisvel extends CustomItem {

    public static HashSet<UUID> ativos = new HashSet<UUID>();

    public static void ficaInvi(Player p) {
        if (Jobs.getJobLevel("Mago", p) != 1) {
            return;
        }
        if (Mana.spendMana(p, 3)) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 20 * 500, 0));
            ativos.add(p.getUniqueId());
            p.sendMessage(ChatColor.GREEN + "Voce se cobre com a capa");
        }
    }

    public static void aparece(Player p) {
        if (Jobs.getJobLevel("Mago", p) != 1) {
            return;
        }
        if (ativos.contains(p.getUniqueId())) {
            p.removePotionEffect(PotionEffectType.INVISIBILITY);
            ativos.remove(p.getUniqueId());
            p.sendMessage(ChatColor.GREEN + "Voce se descobre e aparece");
        }
    }

    public CapaInvisvel() {
        super(Material.CARPET, L.m("Capa da Invisibilidade"), L.m("Fique invisivel"), CustomItem.RARO);
        Runnable r = new Runnable() {
            public void run() {
                HashSet<UUID> toRemove = new HashSet<UUID>();
                for (UUID u : ativos) {
                    Player p = Bukkit.getPlayer(u);
                    if (p == null) {
                        toRemove.add(u);
                    } else {
                        if (!p.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
                            toRemove.add(u);
                        } else if (!Mana.spendMana(p, 10)) {
                            p.removePotionEffect(PotionEffectType.INVISIBILITY);
                        }
                    }
                }
                for (UUID u : toRemove) {
                    ativos.removeAll(toRemove);
                }
            }
        };
        Bukkit.getScheduler().scheduleSyncRepeatingTask(KoM._instance, r, 20 * 5, 20 * 5);
    }

    @Override
    public boolean onItemInteract(Player p) {
        return true;
    }
}
