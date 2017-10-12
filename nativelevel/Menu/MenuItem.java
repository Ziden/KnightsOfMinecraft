package nativelevel.Menu;

import nativelevel.Language.LNG;
import nativelevel.Language.MSG;
import org.bukkit.Material;

/**
 *
 * @author Nosliw
 */
public enum MenuItem {
    MINER(0, MSG.MINER, "♢", Material.DIAMOND_PICKAXE),
    BLACKSMITH(1, MSG.BLACKSMITH, "☭", Material.ANVIL),
    LUMBERJACK(2, MSG.LUMBERJACK, "♧", Material.GOLD_AXE),
    FARMER(3, MSG.FARMER, "☮", Material.WHEAT),
    PALADIN(4, MSG.PALADIN, "☥", Material.IRON_SWORD),
    ALCHEMIST(5, MSG.ALCHEMIST, "✡", Material.BREWING_STAND_ITEM),
    WIZARD(6, MSG.WIZARD, "☯", Material.BOOK),
    THEIF(7, MSG.THIEF, "➹", Material.FEATHER),
    ENGINEER(8, MSG.ENGINEER, "⌘", Material.REDSTONE);
    
    private final int ID;
    private final MSG title;
    private final String symbol;
    private final Material material;

    private MenuItem(int ID, MSG title, String symbol, Material material) {
        this.ID = ID;
        this.title = title;
        this.symbol = symbol;
        this.material = material;
    }

    public int getID() {
        return ID;
    }

    public MSG getTitle() {
        return title;
    }
    
    public String getTitle(LNG language) {
        return title.get(language);
    }

    public String getSymbol() {
        return symbol;
    }

    public Material getMaterial() {
        return material;
    }
}
