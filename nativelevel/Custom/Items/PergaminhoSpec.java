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
import nativelevel.sisteminhas.ClanLand;
import nativelevel.KoM;
import nativelevel.Lang.L;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PergaminhoSpec extends CustomItem {

    public PergaminhoSpec() {
        super(Material.PAPER, L.m("Pergaminho de Re-Especializacao"), L.m("Reseta suas especializacoes"), CustomItem.EPICO);
    }

    @Override
    public boolean onItemInteract(Player p) {
        KoM.database.atualizaSpecs(p.getUniqueId().toString(), new int[]{0,0,0,0,0,0,0,0,0});
        p.sendMessage(ChatColor.GREEN+"Voce esqueceu suas especializacoes !");
        if (p.getItemInHand().getAmount() > 1) {
            p.getItemInHand().setAmount(p.getItemInHand().getAmount() - 1);
        } else {
            p.setItemInHand(null);
            //p.getInventory().removeItem(new ItemStack(Material.LAPIS_BLOCK, 1));
        }
        return true;
    }
}
