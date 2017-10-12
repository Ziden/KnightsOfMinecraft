package nativelevel.Equipment;

import nativelevel.KoM;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Ziden
 * 
 */
public class Equipamento {


    public ItemEquipavel [] armadura;
    public ItemEquipavel hand;
    public ItemEquipavel offHand;
    
    public Equipamento(Player p) {
        KoM.debug("Criando equipamento de "+p.getName());
        setArmadura(p.getInventory().getArmorContents());
        hand = new ItemEquipavel(p.getInventory().getItemInMainHand());
        if(hand.meta == null)
            hand = null;
        offHand = new ItemEquipavel(p.getInventory().getItemInOffHand());
        if(offHand.meta == null)
            offHand = null;
        KoM.debug("Equip criado");
        EquipManager.debug(armadura);
    }
    
    public void setArmadura(ItemStack [] contents) {
        armadura = new ItemEquipavel[contents.length];
        int x = 0;
        for(ItemStack ss : contents) {
            if(ss==null || ss.getType()==Material.AIR) {
                armadura[x] = null;
            } else {
                armadura[x] = new ItemEquipavel(ss);
            }
            x++;
        }
    }
    
}
