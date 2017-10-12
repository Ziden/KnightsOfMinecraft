package nativelevel.Classes.Blacksmithy.recipes.Armas.Pedra;

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
public class EspadaDePedra extends CustomCrafting {

    public EspadaDePedra() {
        super("Espada de Pedra Melhorada", "Aprimora espadas de pedra");
    }

    @Override
    public ItemStack[] getRecipe() {
        return new ItemStack[]{
            new ItemStack(Material.STONE, 1),
            new ItemStack(Material.COAL, 1),
            new ItemStack(Material.SAND),};
    }

    @Override
    public double getExpRatio() {
        return 1;
    }

    @Override
    public int getMinimumSkill() {
        return 25;
    }

    @Override
    public int getHammerHits() {
        return 7;
    }

    @Override
    public ItemStack aplica(ItemStack ss) {
        double dano = WeaponDamage.getDamage(ss);
        dano += 1;
        ss = WeaponDamage.setDano(ss, dano);
        return ss;
    }

    @Override
    public ItemStack aplicaNoCraftNormal(ItemStack ss) {
        return ss;
    }

    @Override
    public ItemStack getItemPrincipal() {
        return new ItemStack(Material.STONE_SWORD);
    }

}
