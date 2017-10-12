package nativelevel.Classes.Blacksmithy.recipes.Armas.Ferro;

import nativelevel.Equipment.WeaponDamage;
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
public class PicaretaDeFerro extends CustomCrafting {

    public PicaretaDeFerro() {
        super("Picareta de Ferro", "Crie picaretas de ferro aprimoradas");
    }

    @Override
    public ItemStack[] getRecipe() {
        return new ItemStack[]{
            new ItemStack(Material.IRON_INGOT, 1),
            new ItemStack(Material.COAL, 1),
            new ItemStack(Material.SAND),};
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
        double dano = WeaponDamage.getDamage(ss);
      //  dano += 1;
        ss = WeaponDamage.setDano(ss, dano);
        return ss;
    }

    @Override
    public ItemStack aplicaNoCraftNormal(ItemStack ss) {
        if(ss.getType()!=Material.IRON_PICKAXE) 
            return ss;
        double dano = WeaponDamage.getDamage(ss);
        dano -= 1;
        return WeaponDamage.setDano(ss, dano);
    }

    @Override
    public ItemStack getItemPrincipal() {
        return new ItemStack(Material.IRON_PICKAXE);
    }

}
