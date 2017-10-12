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
import nativelevel.Lang.L;
import nativelevel.MetaShit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

public class VenenoNatural extends CustomItem {

    public VenenoNatural() {
        super(Material.POTION, L.m("Veneno Natural"), L.m("Um veneninho de leve"), CustomItem.INCOMUM);
    }

    @Override
    public boolean onItemInteract(Player player) {
        if (Jobs.getJobLevel(L.get("Classes.Alchemist"), player) != 1) {
            player.sendMessage(ChatColor.RED + L.m("Apenas alquimistas sabem usar isto !"));
            return true;
        }
        Potion potion = new Potion(PotionType.POISON, 1);
        potion.setSplash(true);
        //potion.getEffects().add(new PotionEffect(PotionEffectType.SLOW, 20 * 5, 1));
        ItemStack itemStack = new ItemStack(Material.POTION);
        potion.apply(itemStack);
        ThrownPotion thrownPotion = player.launchProjectile(ThrownPotion.class);
        MetaShit.setMetaObject("teia", thrownPotion, 1);
        thrownPotion.setItem(itemStack);
        if (player.getItemInHand().getAmount() == 1) {
            player.setItemInHand(null);
        } else {
            player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
        }
        return false;
    }
}
