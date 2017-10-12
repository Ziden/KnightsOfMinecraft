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
import nativelevel.bencoes.TipoBless;
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
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BencaoAleatoria extends CustomItem {

    public BencaoAleatoria() {
        super(Material.NETHER_STAR, L.m("Benção Suprema de Jabu"), L.m("Lhe concede uma benção super forte"), CustomItem.LENDARIO);
    }

    @Override
    public boolean onItemInteract(final Player p) {

        TipoBless sorteado = TipoBless.values()[Jobs.rnd.nextInt(TipoBless.values().length)];

        for (Player pl : Bukkit.getOnlinePlayers()) {
            if (pl != p) {
                pl.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "" + p.getName() + " ganhou uma benção suprema de " + sorteado.name());
            }
        }
        p.sendMessage(ChatColor.GREEN + "Voce ganhou uma bencao suprema de " + sorteado.name());
        p.sendMessage(ChatColor.GREEN + "Use ela com muita sabedoria...pois seu poder eh grande.");

        ItemStack item = TipoBless.cria(sorteado);

        p.getInventory().addItem(item);

        if (p.getItemInHand().getAmount() > 1) {
            p.getItemInHand().setAmount(p.getItemInHand().getAmount() - 1);
        } else {
            p.setItemInHand(null);
        }
        return true;
    }
}
