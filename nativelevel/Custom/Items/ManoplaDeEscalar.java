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

public class ManoplaDeEscalar extends CustomItem {

    public ManoplaDeEscalar() {
        super(Material.LEATHER_LEGGINGS,L.m( "Calcas de Escalar"),L.m( "Permite escalar vinhas"), CustomItem.EPICO);
    }

    @Override
    public boolean onItemInteract(Player p) {
        return true;
    }

}
