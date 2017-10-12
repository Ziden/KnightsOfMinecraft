package nativelevel.utils;

import java.util.Arrays;
import java.util.List;
import nativelevel.Custom.CustomItem;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Ziden
 */
public class CategoriaUtils {

    public static enum CategoriaItem {

        Utilidades(Material.BOOK_AND_QUILL), //
        Arma(Material.IRON_SWORD), // 
        Armadura(Material.IRON_CHESTPLATE), //
        Recurso(Material.SEEDS), //
        Minerio(Material.IRON_INGOT), //
        Pocao(Material.POTION), //
        Documentos(Material.PAPER), //
        Bloco(Material.STONE), // 
        Etc(Material.BOOKSHELF), // 
        Engenharia(Material.REDSTONE), //
        ItemsKom(Material.SLIME_BALL), // 
        Comida(Material.BREAD); //

        public Material icone;

        private CategoriaItem(Material i) {
            this.icone = i;
        }
    }

    private static List<Material> utilidades = Arrays.asList(new Material[]{
        Material.COMPASS, Material.WATCH, Material.TORCH, Material.WRITTEN_BOOK
    });

    private static List<Material> comidas = Arrays.asList(new Material[]{
        Material.COOKIE, Material.BREAD, Material.PUMPKIN_PIE, Material.CAKE, Material.APPLE, Material.GOLDEN_APPLE,
        Material.MUSHROOM_SOUP, Material.RAW_FISH, Material.COOKED_FISH, Material.RAW_BEEF, Material.COOKED_BEEF, Material.RAW_CHICKEN,
        Material.COOKED_CHICKEN, Material.COOKED_MUTTON, Material.MUTTON, Material.RABBIT_FOOT, Material.RABBIT_STEW
    });

    private static List<Material> recursos = Arrays.asList(new Material[]{
        Material.SUGAR_CANE, Material.SUGAR, Material.SEEDS, Material.MELON_SEEDS, Material.PUMPKIN_SEEDS,
        Material.MELON, Material.PUMPKIN, Material.RED_ROSE, Material.DOUBLE_PLANT, Material.YELLOW_FLOWER,
        Material.COCOA, Material.CACTUS, Material.LEAVES, Material.LEAVES_2, Material.LOG, Material.LOG_2,
        Material.GRAVEL, Material.CLAY, Material.GLASS, Material.SAND, Material.CARROT, Material.POTATO, Material.CARROT_ITEM,Material.LEATHER,
    });

    public static CategoriaItem getCategoria(ItemStack ss) {
        if (CustomItem.getItem(ss) != null) {
            return CategoriaItem.ItemsKom;
        } else {
            return getCategoria(ss.getType());
        }

    }

    public static CategoriaItem getCategoria(Material m) {

        if (m.name().contains("ORE") || m.name().contains("INGOT") || m==Material.DIAMOND || m==Material.GLOWSTONE || m==Material.GLOWSTONE_DUST) {
            return CategoriaItem.Minerio;
        } else if (m.name().contains("REDSTONE") || m.name().contains("PISTON") || m.name().contains("DIODE")) {
            return CategoriaItem.Engenharia;
        } else if (m == Material.POTION || m == Material.GLASS_BOTTLE || m.name().contains("Pocao") || m.name().contains("Potion")) {
            return CategoriaItem.Pocao;
        } else if (m == Material.PAPER) {
            return CategoriaItem.Documentos;
        } else if (utilidades.contains(m)) {
            return CategoriaItem.Utilidades;
        } else if (recursos.contains(m)) {
            return CategoriaItem.Recurso;
        } else if (comidas.contains(m)) {
            return CategoriaItem.Comida;
        } else if (m.name().contains("_AXE")
                || m.name().contains("_SWORD")
                || m.name().contains("_PICKAXE")
                || m.name().contains("_PICKAXE")
                || m.name().contains("_HOE")
                || m.name().contains("_SPADE")
                || m==Material.BOW) {
            return CategoriaItem.Arma;
        } else if (m.name().contains("_CHESTPLATE")
                || m.name().contains("_LEGGINGS")
                || m.name().contains("_HELMET")
                || m.name().contains("_BOOTS")
                || m.name().contains("Capacete") || m==Material.SHIELD) {
            return CategoriaItem.Armadura;
        } else if (m.isBlock()) {
            return CategoriaItem.Bloco;
        }

        return CategoriaItem.Etc;
    }

    public static CategoriaItem getCategoria(Block ss) {
        return getCategoria(ss.getType());
    }

}
