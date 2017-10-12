package nativelevel.Harvesting;

import java.util.Arrays;
import java.util.List;
import nativelevel.KoM;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Ziden
 */
public class HarvestTypes {

    public static boolean engenhoca(Block ss) {
        Material t = ss.getType();
        KoM.debug(t.name()+" - "+t.name().contains("REDSTONE"));
        if(t.name().contains("PISTON") || t.name().contains("REDSTONE") || t.name().contains("DIODE"))
            return true;
        return false;
    }
    
    public static boolean ehMinerio(Block ss) {
        return ss.getType().name().contains("ORE") || ss.getType().name().contains("STONE");
    }
    
    public static boolean ehLenha(Block ss) {
         return ss.getType().name().contains("LOG") || ss.getType().name().contains("WOOD");
    }
    
}
