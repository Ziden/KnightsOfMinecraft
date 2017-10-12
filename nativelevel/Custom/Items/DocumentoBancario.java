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
import nativelevel.KoM;
import nativelevel.Lang.L;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class DocumentoBancario extends CustomItem {

    public DocumentoBancario() {
        super(Material.PAPER, L.m("Documento Bancario"), L.m("Documentos para uso do banco"), CustomItem.RARO);
    }

    @Override
    public boolean onItemInteract(Player p) {
        int slots = KoM.database.getSlotsBanco(p.getUniqueId().toString());
        if (slots > 4) {
            p.sendMessage(ChatColor.RED + L.m("Voce ja tem o máximo de slots !!!"));
            return true;
        }
        slots = slots + 1;
        KoM.database.setSlotsBanco(p.getUniqueId().toString(), slots);
        p.sendMessage(ChatColor.GREEN + L.m("Voce leu os documentos, e ganhou um slot extra no banco !"));
        if (p.getItemInHand().getAmount() == 1) {
            p.setItemInHand(null);
        } else {
            p.getItemInHand().setAmount(p.getItemInHand().getAmount() - 1);
        }
        return true;
    }

}
