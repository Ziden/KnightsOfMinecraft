package nativelevel.Attributes;

import nativelevel.Equipment.Atributo;
import nativelevel.Equipment.EquipManager;
import nativelevel.Equipment.EquipMeta;
import org.bukkit.entity.Player;

/**
 *
 * @author Ziden
 */
public class Health {

    public static double getMaxHealth(Player p, int newlevel) {
        double vida = EquipManager.getPlayerEquipmentMeta(p).getAttribute(Atributo.Vida);
        double total =  (20 + (newlevel / 5));
        return total + (total*vida/100d);
    }

}
