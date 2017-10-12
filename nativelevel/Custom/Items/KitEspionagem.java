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
import nativelevel.sisteminhas.ClanLand;
import nativelevel.Lang.L;
import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class KitEspionagem extends CustomItem {

    public KitEspionagem() {
        super(Material.BOWL, L.m("Kit de Espionagem"), L.m("Espia guildas rivais"), CustomItem.INCOMUM);
    }

    @Override
    public boolean onItemInteract(Player p) {
        int lvl = Jobs.getJobLevel("Ladino", p);
        if (lvl != 1) {
            p.sendMessage(ChatColor.RED + L.m("Apenas bons ladinos sabem usar este kit !"));
            return true;
        }
        ClanPlayer cp = ClanLand.manager.getClanPlayer(p);
        if (cp == null || cp.getClan() == null) {
            p.sendMessage(ChatColor.RED + L.m("Voce precisa de uma guilda para usar isto !"));
            return true;
        }
        Clan aqui = ClanLand.getClanAt(p.getLocation());
        if (aqui == null || aqui.getTag().equalsIgnoreCase(cp.getClan().getTag())) {
            p.sendMessage(ChatColor.RED + L.m("Nao existem pontos de pilhagem para ver aqui !"));
            return true;
        }
        int pontos = ClanLand.getPtosPilagem(cp.getTag(), aqui.getTag());
        int pontosInimigos = ClanLand.getPtosPilagem(aqui.getTag(), cp.getTag());
        p.sendMessage(ChatColor.GREEN + L.m("Sua guilda tem % pontos de pilhagem aqui !", pontos));
        p.sendMessage(ChatColor.GREEN + L.m("Sua esta guilda tem % pontos de pilhagem sob sua guilda !",pontosInimigos));
        if (p.getItemInHand().getAmount() == 1) {
            p.setItemInHand(null);
        } else {
            p.getItemInHand().setAmount(p.getItemInHand().getAmount() - 1);
        }
        return true;
    }
}
