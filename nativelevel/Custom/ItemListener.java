package nativelevel.Custom;

import nativelevel.KoM;
import org.bukkit.Material;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

/**
 *
 * @author Ziden
 */
public class ItemListener {

    public static void interage(PlayerInteractEvent ev) {
        if (ev.getHand() == EquipmentSlot.OFF_HAND) {
            return;
        }
        if (ev.getAction() != Action.PHYSICAL) {
            CustomItem ci = CustomItem.getItem(ev.getItem());
            if (ci != null) {
                KoM.debug("Chamando custom item " + ci.name);
                if (ev.getItem().getType() != Material.WRITTEN_BOOK && ev.getItem().getType() != Material.PAPER && ev.getItem().getType() != Material.BOOK && ev.getItem().getType() != Material.TRIPWIRE_HOOK) {
                    ev.setCancelled(true);
                }
                ci.onItemInteract(ev.getPlayer());
                ci.interage(ev);
            }
        }
    }

}
