package nativelevel.Attributes;

import nativelevel.Equipment.WeaponType;
import nativelevel.Equipment.WeaponType.TipoArmadura;
import nativelevel.Classes.Blacksmithy.Blacksmith;
import nativelevel.KoM;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Ziden
 */
public class Armors {

    public static int getBaseArmor(ItemStack ss) {
        int baseArmor = 0;
        Material armorType = Blacksmith.getArmorType(ss.getType());

        try {
            
            KoM.debug("TENTANDO TIRAR ARMORTIYPE DE "+armorType.name());
            
            TipoArmadura tipo = TipoArmadura.valueOf(ss.getType().name().split("_")[1]);

            KoM.debug("armor type "+tipo.name());
            
            if (armorType != null) {
                if (armorType == Material.LEATHER) {
                    if (tipo == TipoArmadura.BOOTS) {
                        return 1;
                    }
                    if (tipo == TipoArmadura.HELMET) {
                        return 2;
                    }
                    if (tipo == TipoArmadura.LEGGINGS) {
                        return 3;
                    }
                    if (tipo == TipoArmadura.CHESTPLATE) {
                        return 5;
                    }
                } else if (armorType == Material.IRON_BARDING) {
                    if (tipo == TipoArmadura.BOOTS) {
                        return 2;
                    }
                    if (tipo == TipoArmadura.HELMET) {
                        return 4;
                    }
                    if (tipo == TipoArmadura.LEGGINGS) {
                        return 6;
                    }
                    if (tipo == TipoArmadura.CHESTPLATE) {
                        return 8;
                    }
                } else if (armorType == Material.IRON_INGOT) {
                    if (tipo == TipoArmadura.BOOTS) {
                        return 4;
                    }
                    if (tipo == TipoArmadura.HELMET) {
                        return 6;
                    }
                    if (tipo == TipoArmadura.LEGGINGS) {
                        return 8;
                    }
                    if (tipo == TipoArmadura.CHESTPLATE) {
                        return 10;
                    }
                } else if (armorType == Material.GOLD_INGOT) {
                    if (tipo == TipoArmadura.BOOTS) {
                        return 6;
                    }
                    if (tipo == TipoArmadura.HELMET) {
                        return 8;
                    }
                    if (tipo == TipoArmadura.LEGGINGS) {
                        return 12;
                    }
                    if (tipo == TipoArmadura.CHESTPLATE) {
                        return 14;
                    }
                } else if (armorType == Material.DIAMOND) {
                    if (tipo == TipoArmadura.BOOTS) {
                        return 8;
                    }
                    if (tipo == TipoArmadura.HELMET) {
                        return 10;
                    }
                    if (tipo == TipoArmadura.LEGGINGS) {
                        return 12;
                    }
                    if (tipo == TipoArmadura.CHESTPLATE) {
                        return 14;
                    }
                }
            }
        } catch(Exception e) {
            //e.printStackTrace();
        }
        return baseArmor;
    }

    public static void main(String[] args) {

        int armor = 2;

        double formula = 50 / ((double) armor + 50);
        System.out.println(formula);

    }

}
