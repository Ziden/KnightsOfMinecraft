/*

 ╭╮╭━╮╱╱╭━╮╭━╮
 ┃┃┃╭╯╱╱┃┃╰╯┃┃
 ┃╰╯╯╭━━┫╭╮╭╮┃
 ┃╭╮┃┃╭╮┃┃┃┃┃┃
 ┃┃┃╰┫╰╯┃┃┃┃┃┃
 ╰╯╰━┻━━┻╯╰╯╰╯

 Desenvolvedor: ZidenVentania
 Colaboradores: NeT32, Gabripj, Feldmann
 Patrocionio: InstaMC

 */
package nativelevel;

import java.util.Arrays;
import java.util.List;
import nativelevel.Custom.CustomItem;
import nativelevel.Custom.Items.TeleportScroll;
import nativelevel.Equipment.WeaponDamage;
import nativelevel.utils.BungLocation;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class CFG {

    public static int maxLevel = 100;

    public static boolean mundoGuilda(Location l) {
        return l.getWorld().getName().equalsIgnoreCase(mundoGuilda);
    }

    public static String mundoGuilda = "sorathesnova2";

    public static List<String> dungeons = Arrays.asList(new String[]{"dungeon"});
    public static List<String> safeMaps = Arrays.asList(new String[]{"vila"});
    public static List<String> warMaps = Arrays.asList(new String[]{"arena", "woe"});

    public static int landMax = 5;
    public static int landPrice = 10;

    public static BungLocation localInicio = new BungLocation(CFG.mundoGuilda, -109, 63, 33, -180, -90);
    public static BungLocation localTutorial = new BungLocation("dungeon", 10179, 27, 10140, 0, 0);
    public static BungLocation spawnTree = new BungLocation(CFG.mundoGuilda, -200, 153, 438, 0, 0);

    public static ItemStack[] starterItems = {
        new ItemStack(Material.APPLE, 64),
        WeaponDamage.checkForMods(new ItemStack(Material.WOOD_PICKAXE, 1)),
        WeaponDamage.checkForMods(new ItemStack(Material.WOOD_AXE, 1)),
        WeaponDamage.checkForMods(new ItemStack(Material.LEATHER_HELMET, 1)),
        new ItemStack(Material.SAPLING, 10),
        new ItemStack(Material.TORCH, 64),
        WeaponDamage.checkForMods(new ItemStack(Material.LEATHER_BOOTS, 1)),
        WeaponDamage.checkForMods(new ItemStack(Material.LEATHER_CHESTPLATE, 1)),
        WeaponDamage.checkForMods(new ItemStack(Material.LEATHER_LEGGINGS, 1)),
        TeleportScroll.createTeleportScroll(spawnTree, true, 10, "Pergaminho para Rhodes"),
        new ItemStack(Material.EMERALD, 10),
        new ItemStack(Material.EMPTY_MAP, 10)
    };

    // Loot Values --> They do something important..
    public static int custoAbaixarPower = 32;
    public static int custoPegarItemRandom = 1;
    public static int custoPegarBau = 12;
}
