package nativelevel.Classes.Blacksmithy.recipes;

import nativelevel.Classes.Blacksmithy.CustomCrafting;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Ziden
 *
 */

public class IronBoots extends CustomCrafting {

    public IronBoots() {
        super("Botas de Ferro", "Crie botas de ferro");
    }

    @Override
    public ItemStack[] getRecipe() {
        return new ItemStack[]{
            new ItemStack(Material.LEATHER_BOOTS, 1),
            new ItemStack(Material.LEATHER, 1),
            new ItemStack(Material.IRON_BLOCK),};
    }
    
    @Override
    public double getExpRatio() {
        return 1;
    }

    
    
    @Override
    public int getMinimumSkill() {
        return 65;
    }

    
    
    @Override
    public int getHammerHits() {
        return 3;
    }
    
    
    
    @Override
    public ItemStack aplica(ItemStack ss) {

        if (ss.getType() == Material.CHAINMAIL_BOOTS) {
            ss.setType(Material.IRON_BOOTS);
        }
        return ss;

    }

    
    
    @Override
    public ItemStack aplicaNoCraftNormal(ItemStack ss) {

        if (ss.getType() == Material.IRON_BOOTS) {
            ss.setType(Material.CHAINMAIL_BOOTS);
        }
        return ss;

    }

    @Override
    public ItemStack getItemPrincipal() {
        return new ItemStack(Material.CHAINMAIL_BOOTS);
    }
    
    
    
    

}
