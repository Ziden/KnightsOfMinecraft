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
public class DiamondChestplate extends CustomCrafting {

    public DiamondChestplate() {
        super("Peitoral de Diamante", "Crie peitorais de Diamante");
    }

    @Override
    public ItemStack[] getRecipe() {
        return new ItemStack[]{
            new ItemStack(Material.IRON_CHESTPLATE, 1),
            new ItemStack(Material.LEATHER, 1),
            new ItemStack(Material.DIAMOND_BLOCK),};
    }

    @Override
    public double getExpRatio() {
        return 1;
    }

    @Override
    public int getMinimumSkill() {
        return 110;
    }

    @Override
    public int getHammerHits() {
        return 6;
    }

    @Override
    public ItemStack aplica(ItemStack ss) {

        if (ss.getType() == Material.IRON_CHESTPLATE) {
            ss.setType(Material.DIAMOND_CHESTPLATE);
        }
        return ss;

    }

    @Override
    public ItemStack aplicaNoCraftNormal(ItemStack ss) {

        if (ss.getType() == Material.DIAMOND_CHESTPLATE) {
            ss.setType(Material.AIR);
        }
        return ss;

    }

    @Override
    public ItemStack getItemPrincipal() {
        return new ItemStack(Material.IRON_CHESTPLATE);
    }

}
