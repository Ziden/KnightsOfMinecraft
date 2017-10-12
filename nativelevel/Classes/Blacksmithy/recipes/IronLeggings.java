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
public class IronLeggings extends CustomCrafting {

    public IronLeggings() {
        super("Calças de Ferro", "Como criar armaduras de ferro");
    }

    @Override
    public ItemStack[] getRecipe() {
        return new ItemStack[]{
            new ItemStack(Material.LEATHER_LEGGINGS, 1),
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
        return 4;
    }

    @Override
    public ItemStack aplica(ItemStack ss) {

        if (ss.getType() == Material.CHAINMAIL_LEGGINGS) {
            ss.setType(Material.IRON_LEGGINGS);
        }
        
        return ss;

    }

    @Override
    public ItemStack aplicaNoCraftNormal(ItemStack ss) {

        if (ss.getType() == Material.IRON_LEGGINGS) {
            ss.setType(Material.CHAINMAIL_LEGGINGS);
        }
        
        return ss;

    }

    @Override
    public ItemStack getItemPrincipal() {
        return new ItemStack(Material.CHAINMAIL_LEGGINGS);
    }

}
