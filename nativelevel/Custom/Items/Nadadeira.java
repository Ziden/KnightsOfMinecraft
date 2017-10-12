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
import nativelevel.Lang.L;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Nadadeira extends CustomItem {

    public Nadadeira() {
        super(Material.LEATHER_BOOTS, L.m("Pe de Pato"), L.m("Permite nadar com facilidade"), CustomItem.EPICO);
    }

    @Override
    public boolean onItemInteract(Player p) {
        return true;
    }

}
