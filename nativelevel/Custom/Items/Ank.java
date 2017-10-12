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

import java.util.HashSet;
import nativelevel.Custom.CustomItem;
import nativelevel.Jobs;
import nativelevel.KoM;
import nativelevel.Lang.L;
import nativelevel.Attributes.Mana;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class Ank extends CustomItem {

    public static HashSet<String> protegidos = new HashSet<String>();

    public Ank() {
        super(Material.NETHER_STAR, L.m("Luz da Graca"), L.m("Ressusita uma vez"), CustomItem.RARO);
    }

    @Override
    public boolean onItemInteract(Player player) {
        if (Jobs.getJobLevel("Paladino", player) != 1) {
            player.sendMessage(ChatColor.GOLD + L.m("Apenas bons paladinos sabem usar isto !"));
            return true;
        } else {
            if (protegidos.contains(player.getName())) {
                player.sendMessage(ChatColor.GOLD +L.m( "Voce ja esta protegido !"));
                return true;
            }
            if(!Mana.spendMana(player, 50)) {
                return true;
            }
            player.sendMessage(ChatColor.GOLD + L.m("A luz comeca a brilhar te circulando por 5 segundos !"));
            player.playSound(player.getLocation(), Sound.ENTITY_CREEPER_PRIMED, 10, 0);
            player.playEffect(player.getLocation(), Effect.ENDER_SIGNAL, 10);
            protegidos.add(player.getName());
            Bukkit.getScheduler().scheduleSyncDelayedTask(KoM._instance, new tiraNome(player.getName()), 20 * 5l);
            player.setItemInHand(null);
        }
        return false;
    }

    public class tiraNome implements Runnable {

        String nome;

        public tiraNome(String nome) {
            this.nome = nome;
        }

        @Override
        public void run() {
            protegidos.remove(nome);
            Player p = Bukkit.getPlayer(nome);
            if (p != null) {
                p.sendMessage(ChatColor.GOLD + L.m("A luz se desfaz"));
            }
        }

    }
}
