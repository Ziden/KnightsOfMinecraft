package nativelevel.phatloots.gui;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * A Tool is used for modifying Loot in the GUI
 *
 * @author Codisimus
 */
public class Tool {
    private static final ArrayList<Tool> tools = new ArrayList<>();
    private final int id;
    private final String name;
    private final ItemStack item;

    static {
        ItemStack item = new ItemStack(Material.LEASH);
        ItemMeta meta = Bukkit.getItemFactory().getItemMeta(item.getType());
        List<String> lore = new ArrayList<>();
        meta.setDisplayName("§2Navigate/Move (Click to change Tool)");
        lore.add("§4LEFT CLICK:");
        lore.add("§6 Enter a Collection");
        lore.add("§4RIGHT CLICK:");
        lore.add("§6 Leave a Collection");
        lore.add("§4SHIFT + LEFT CLICK:");
        lore.add("§6 Picks up a Collection");
        lore.add("§6 Shift a Loot to the Left");
        lore.add("§4SHIFT + RIGHT CLICK:");
        lore.add("§6 Shift a Loot to the Right");
        lore.add("§4SCROLL CLICK:");
        lore.add("§6 Remove a Loot/Add an Item (from inventory)");
        meta.setLore(lore);
        item.setItemMeta(meta);
        new Tool("NAVIGATE_AND_MOVE", item).registerTool();

        item = new ItemStack(Material.NAME_TAG);
        meta = Bukkit.getItemFactory().getItemMeta(item.getType());
        lore.clear();
        meta.setDisplayName("§2Modify Probability/Toggle (Click to change Tool)");
        lore.add("§4LEFT CLICK:");
        lore.add("§6 +1 Probability");
        lore.add("§4DOUBLE LEFT CLICK:");
        lore.add("§6 +10 Probability");
        lore.add("§4RIGHT CLICK:");
        lore.add("§6 -1 Probability");
        lore.add("§4SHIFT + LEFT CLICK:");
        lore.add("§6 Toggle AutoEnchant/FromConsole");
        lore.add("§4SHIFT + RIGHT CLICK:");
        lore.add("§6 Toggle GenerateName/TempOP");
        lore.add("§4SCROLL CLICK:");
        lore.add("§6 Toggle TieredName and Loot table settings");
        meta.setLore(lore);
        item.setItemMeta(meta);
        new Tool("MODIFY_PROBABILITY_AND_TOGGLE", item).registerTool();

        item = new ItemStack(Material.GOLD_NUGGET);
        meta = Bukkit.getItemFactory().getItemMeta(item.getType());
        lore.clear();
        meta.setDisplayName("§2Modify Amount (Click to change Tool)");
        lore.add("§4LEFT CLICK:");
        lore.add("§6 +1 Amount");
        lore.add("§4DOUBLE LEFT CLICK:");
        lore.add("§6 +10 Amount");
        lore.add("§4RIGHT CLICK:");
        lore.add("§6 -1 Amount");
        lore.add("§4SHIFT + LEFT CLICK:");
        lore.add("§6 +1 Amount (Upper Range)");
        lore.add("§4SHIFT + RIGHT CLICK:");
        lore.add("§6 -1 Amount (Upper Range)");
        lore.add("§4SCROLL CLICK:");
        lore.add("§6 Set Amount to 1 and Clear time/exp/money");
        meta.setLore(lore);
        item.setItemMeta(meta);
        new Tool("MODIFY_AMOUNT", item).registerTool();
    }

    /**
     * Constructs a new Tool
     *
     * @param name The unique name of the tool
     * @param item The ItemStack that represents the Tool
     */
    public Tool(String name, ItemStack item) {
        id = tools.size();
        this.name = name;
        this.item = item;
    }

    /**
     * Returns the id of the Tool
     *
     * @return The id of the Tool
     */
    public int getID() {
        return id;
    }

    /**
     * Returns the name of the Tool
     *
     * @return The name of the Tool
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the ItemStack which displays the Tool information
     *
     * @return The Tool's ItemStack
     */
    public ItemStack getItem() {
        return item;
    }

    /**
     * Returns the previous Tool based on it's id
     *
     * @return The previous Tool based on it's id
     */
    public Tool prevTool() {
        int toolID = id - 1;
        if (toolID < 0) {
            toolID = tools.size() - 1;
        }
        return getToolByID(toolID);
    }

    /**
     * Returns the next Tool based on it's id
     *
     * @return The next Tool based on it's id
     */
    public Tool nextTool() {
        int toolID = id + 1;
        if (toolID >= tools.size()) {
            toolID = 0;
        }
        return getToolByID(toolID);
    }

    /**
     * Adds this Tool to the static list of registered tools
     */
    public void registerTool() {
        tools.add(this);
    }

    /**
     * Returns the Tool with the given id
     *
     * @param id The given Tool id
     * @return The Tool with the given id or null if there is no Tool with that id
     */
    public static Tool getToolByID(int id) {
        return id >= tools.size() ? null : tools.get(id);
    }

    /**
     * Returns the Tool with the given name
     *
     * @param name The given Tool name
     * @return The Tool with the given name or null if there is no Tool with that id
     */
    public static Tool getToolByName(String name) {
        for (Tool tool : tools) {
            if (tool.name.equals(name)) {
                return tool;
            }
        }
        return null;
    }

    /**
     * Returns the Tool which is represented by the given ItemStack
     *
     * @param item The given ItemStack
     * @return The Tool that shares the same Material as the ItemStack
     */
    public static Tool getTool(ItemStack item) {
        Material mat = item.getType();
        for (Tool tool : tools) {
            if (tool.item.getType() == mat) {
                return tool;
            }
        }
        return null;
    }
}
