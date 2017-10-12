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

import nativelevel.Custom.CustomItem;
import nativelevel.Jobs;
import nativelevel.KoM;
import nativelevel.Lang.L;
import nativelevel.MetaShit;
import nativelevel.Attributes.AttributeInfo;
import nativelevel.Attributes.Stamina;
import nativelevel.spec.PlayerSpec;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Zarabatana extends CustomItem {

    public Zarabatana() {
        super(Material.STICK, L.m("Zarabatana"), L.m("Atira Sticks"), CustomItem.RARO);
    }

    @Override
    public boolean onItemInteract(Player p) {

        if (Jobs.getJobLevel("Alquimista", p) != 1) {
            p.sendMessage(ChatColor.RED + "Apenas alquimistas sabem usar isto !");
            return true;
        }

        if (p.hasPotionEffect(PotionEffectType.SLOW)) {
            return true;
        }

        if (!p.getInventory().contains(Material.STICK)) {
            p.sendMessage(ChatColor.RED + "Voce precisa de Sticks para atirar !");
            return true;
        }

        if (Stamina.spendStamina(p, 8)) {

            if (!KoM.gastaItem(p,p.getInventory(), Material.MELON_SEEDS, (byte) 0)) {
                p.sendMessage(ChatColor.RED + "Voce precisa de sementes de melancia para atirar !");
                return true;
            }

            Arrow a = p.launchProjectile(Arrow.class);
            a.setShooter(p);
            MetaShit.setMetaObject("modDano", a, 0.5 + (p.getLevel()/100d/2d));
            MetaShit.setMetaObject("zaraba", a, true);
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 3, 0));
        }

        return true;
    }
}
