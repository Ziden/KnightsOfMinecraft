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
import nativelevel.Menu.netMenu;
import nativelevel.MetaShit;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class PergaminhoResetClasse extends CustomItem {

    public PergaminhoResetClasse() {
        super(Material.PAPER, L.m("Reset De Classe Lendario"),L.m("Reseta classe sem perder nivel/reborn"), CustomItem.LENDARIO);
    }

    @Override
    public boolean onItemInteract(Player p) {
        MetaShit.setMetaObject("rebornGratiz", p, "1");
        MetaShit.setMetaObject("resetFree", p, "1");
        netMenu.escolheClasse(p);
        if (p.getItemInHand().getAmount() > 1) {
            p.getItemInHand().setAmount(p.getItemInHand().getAmount() - 1);
        } else {
            p.setItemInHand(null);
        }
        return true;
    }

}
