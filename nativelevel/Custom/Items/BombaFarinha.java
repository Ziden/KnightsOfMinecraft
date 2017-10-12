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
import me.fromgate.playeffect.PlayEffect;
import me.fromgate.playeffect.VisualEffect;
import nativelevel.sisteminhas.ClanLand;
import nativelevel.Classes.Thief;
import nativelevel.Custom.CustomItem;
import nativelevel.Jobs;
import nativelevel.KoM;
import nativelevel.Lang.L;
import nativelevel.Attributes.Mana;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class BombaFarinha extends CustomItem {

    public BombaFarinha() {
        super(Material.SLIME_BALL, L.m("Bomba de Farinha"), L.m("Revela invisivel"), CustomItem.INCOMUM);
    }

    @Override
    public boolean onItemInteract(Player player) {
        try {
            if (player.hasPotionEffect(PotionEffectType.SLOW)) {
                return true;
            }
            
            String type = ClanLand.getTypeAt(player.getLocation());
            if(type.equalsIgnoreCase("SAFE")) {
                player.sendMessage(ChatColor.RED+L.m("Voce nao pode jogar isto aqui !"));
                return true;
            }

            int distancia = 8;
            
            
            if (Jobs.getJobLevel("Alquimista", player) != 1) {
                distancia = 3;
            }
           //     player.sendMessage(ChatColor.RED +L.m( "Apenas bons alquimistas sabem usar isto !"));
           //     return true;
           // }
            
            if(player.hasPotionEffect(PotionEffectType.POISON)) {
                player.sendMessage(ChatColor.RED+L.m("Voce nao consegue usar isto envenenado"));
                return true;
            }
            
           player.sendMessage(ChatColor.GREEN+L.m("Voce jogou uma bomba de farinha !"));
            for(Player p : Bukkit.getOnlinePlayers()) {
                if(Thief.taInvisivel(p)) {
                    if(p.getLocation().distance(player.getLocation()) < 5) {
                        Thief.revela(p);
                        PlayEffect.play(VisualEffect.EXPLOSION, p.getLocation(), "num:2");
                        player.sendMessage(ChatColor.GREEN+L.m("Voce revelou % !",p.getName()));
                    }
                }
            }
             if (player.getItemInHand().getAmount() > 1) {
                player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
            } else {
                player.setItemInHand(null);
            }
            player.updateInventory();
            
        } catch (Throwable ex) {
           ex.printStackTrace();
        }
        return false;
    }
}
