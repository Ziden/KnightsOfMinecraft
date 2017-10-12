package nativelevel.Classes.Blacksmithy.recipes;

import nativelevel.Classes.Blacksmithy.CustomCrafting;
import nativelevel.KoM;
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
public class IronChestplate extends CustomCrafting {

    public IronChestplate() {
        super("Peitoral de Ferro", "Como criar um peitoral de ferro");
    }

    @Override
    public ItemStack[] getRecipe() {
        return new ItemStack[]{
            new ItemStack(Material.LEATHER_CHESTPLATE, 1),
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
        return 5;
    }

    @Override
    public ItemStack aplica(ItemStack ss) {

        if (ss.getType() == Material.CHAINMAIL_CHESTPLATE) {
            ss.setType(Material.IRON_CHESTPLATE);
        }
        
        return ss;

    }

    @Override
    public ItemStack aplicaNoCraftNormal(ItemStack ss) {

        if (ss.getType() == Material.IRON_CHESTPLATE) {
            KoM.debug("Mudei");
            ss.setType(Material.CHAINMAIL_CHESTPLATE);
        }
        
        return ss;

    }

    @Override
    public ItemStack getItemPrincipal() {
        return new ItemStack(Material.CHAINMAIL_CHESTPLATE);
    }

}
