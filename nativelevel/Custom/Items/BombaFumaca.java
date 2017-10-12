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

import java.util.logging.Level;
import nativelevel.Classes.Thief;
import nativelevel.Custom.CustomItem;
import nativelevel.Jobs;
import nativelevel.KoM;
import nativelevel.Lang.L;
import nativelevel.Attributes.Mana;
import nativelevel.spec.PlayerSpec;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class BombaFumaca extends CustomItem {

    public BombaFumaca() {
        super(Material.SLIME_BALL, L.m("Bomba de Fumaca"), L.m("Fica invisivel"), CustomItem.INCOMUM);
    }

    @Override
    public boolean onItemInteract(Player player) {
        try {
            if (player.hasPotionEffect(PotionEffectType.SLOW)) {
                return true;
            }

            long delay = 20 * 25l;
            if (player.getWorld().getName().equalsIgnoreCase("dungeon")) {
                delay /= 2;
            }

            if (Jobs.getJobLevel("Ladino", player) != 1) {
                player.sendMessage(ChatColor.RED + L.m("Apenas bons ladinos sabem usar isto !"));
                return true;
            }

            if (player.hasPotionEffect(PotionEffectType.POISON)) {
                player.sendMessage(ChatColor.RED + L.m("Voce nao consegue se esconder envenenado"));
                return true;
            }

            //if(!VanishNoPacket.isVanished(player.getName())) {
            int mana = 12;
            if (!Thief.taInvisivel(player)) {

                if (PlayerSpec.temSpec(player, PlayerSpec.Assassino)) {
                    mana = 5;
                } else  if (PlayerSpec.temSpec(player, PlayerSpec.Ranger)) {
                    mana = 25;
                }
                if (!Mana.spendMana(player, mana)) {
                    return true;
                }
                if (player.getItemInHand().getAmount() > 1) {
                    player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
                } else {
                    player.setItemInHand(null);
                }
                player.updateInventory();
                //player.playEffect(player.getLocation(), Effect.SMOKE, 40l);
                player.sendMessage(ChatColor.RED + L.m("Voce sumiu !"));
                player.playSound(player.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 10, 0);
                Thief.ficaInvisivel(player, (int) delay);
                Bukkit.getScheduler().scheduleSyncDelayedTask(KoM._instance, new revela(player), delay);

            }

        } catch (Throwable ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public class revela implements Runnable {

        Player p;

        public revela(Player nome) {
            this.p = nome;
        }

        @Override
        public void run() {

            try {
                //if (p != null && p.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
                Thief.revela(p);
                //}
            } catch (Throwable ex) {
                KoM.log.info(ex.getMessage());
            }
        }

    }

}
