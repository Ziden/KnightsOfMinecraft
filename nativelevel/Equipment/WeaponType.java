/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.Equipment;

import nativelevel.Jobs;
import nativelevel.KoM;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Ziden
 */
public class WeaponType {
    
    public enum TipoArco {
        
        TESTE(1,(byte)0);
        
        public int id;
        public byte data;
        
        private TipoArco(int id, byte data) {
            this.id = id;
            this.data = data;
        }
    }
    
    public enum TipoArma {
        HOE,SWORD,AXE,PICKAXE,SPADE
    }
    
    public enum MatArma {
        IRON,GOLD,DIAMOND,STONE,WOOD
    }
    
    public enum TipoArmadura {
        HELMET,LEGGINGS,BOOTS,CHESTPLATE
    }
    
    public enum MatArmadura {
        IRON,GOLD,DIAMOND,CHAINMAIL,LEATHER
    }
    
    public static Material randomArma() {
        MatArma random =MatArma.values()[Jobs.rnd.nextInt(MatArma.values().length)];
        TipoArma random2 =TipoArma.values()[Jobs.rnd.nextInt(TipoArma.values().length)];
        return getMaterial(random, random2);
    }
    
    public static Material randomArmor() {
        MatArmadura random =MatArmadura.values()[Jobs.rnd.nextInt(MatArmadura.values().length)];
        TipoArmadura random2 =TipoArmadura.values()[Jobs.rnd.nextInt(TipoArmadura.values().length)];
        return getMaterial(random, random2);
    }
    
    public static TipoArma getType(ItemStack ss) {
        try {
            return TipoArma.valueOf(ss.getType().name().split("_")[1]);
        } catch(Exception e) {
            KoM.debug("Nao encontrei tipo arma "+ss.getType().name());
            return null;
        }
    }
    
     public static MatArmadura getArType(ItemStack ss) {
        try {
            return MatArmadura.valueOf(ss.getType().name().split("_")[0]);
        } catch(Exception e) {
            KoM.debug("Nao encontrei tipo armadura "+ss.getType().name());
            return null;
        }
    }
    
    public static Material getMaterial(MatArma m, TipoArma t) {
        try {
            return Material.valueOf(m.name()+"_"+t.name());
        }catch(Exception e) {
            return null;
        }
    }
    
    public static Material getMaterial(MatArmadura m, TipoArmadura t) {
        try {
            return Material.valueOf(m.name()+"_"+t.name());
        }catch(Exception e) {
            return null;
        }
    }
    
}
