package nativelevel.phatloots.loot;

import nativelevel.phatloots.PhatLoot;
import nativelevel.phatloots.PhatLoots;
import nativelevel.phatloots.PhatLootsUtil;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * An Item is the Loot representation of an ItemStack
 *
 * @author Codisimus
 */
@SerializableAs("Item")
public class Item extends Loot {
    /* SECTIONS */
    private static final String BASE_VALUES = "BaseValues";
    private static final String ENCHANTMENT_VALUES = "EnchantmentValues";
    private static final String TIERS = "TIERS";
    private static final String DIVIDER = ".";
    /* TAGS */
    private static final String ARMOR = "ARMOR";
    private static final String SWORD = "SWORD";
    private static final String AXE = "AXE";
    private static final String BOW = "BOW";
    private static final String PICKAXE = "PICKAXE";
    private static final String SPADE = "SPADE";
    private static final String HOE = "HOE";
    private static final String DAMAGE = "<dam>";
    private static final String HOLY = "<holy>";
    private static final String FIRE = "<fire>";
    private static final String BUG = "<bug>";
    private static final String THORNS = "<thorns>";
    private static final String DEFENSE = "<def>";
    private static final String FIRE_DEFENSE = "<firedef>";
    private static final String RANGE_DEFENSE = "<rangedef>";
    private static final String BLAST_DEFENSE = "<blastdef>";
    private static final String FALL_DEFENSE = "<falldef>";
    /* ENCHANTMENTS */
    private static final Enchantment[] ARMOR_ENCHANTMENTS = {
        Enchantment.PROTECTION_ENVIRONMENTAL, Enchantment.PROTECTION_FIRE,
        Enchantment.PROTECTION_EXPLOSIONS, Enchantment.PROTECTION_PROJECTILE,
        Enchantment.THORNS, Enchantment.DURABILITY
    };
    private static final Enchantment[] SWORD_ENCHANTMENTS = {
        Enchantment.DAMAGE_ALL, Enchantment.DAMAGE_UNDEAD,
        Enchantment.DAMAGE_ARTHROPODS, Enchantment.KNOCKBACK,
        Enchantment.FIRE_ASPECT, Enchantment.LOOT_BONUS_MOBS,
        Enchantment.DURABILITY
    };
    private static final Enchantment[] AXE_ENCHANTMENTS = {
        Enchantment.DAMAGE_ALL, Enchantment.DAMAGE_UNDEAD,
        Enchantment.DAMAGE_ARTHROPODS, Enchantment.KNOCKBACK,
        Enchantment.FIRE_ASPECT, Enchantment.LOOT_BONUS_MOBS,
        Enchantment.DURABILITY
    };
    private static final Enchantment[] BOW_ENCHANTMENTS = {
        Enchantment.ARROW_DAMAGE, Enchantment.ARROW_KNOCKBACK,
        Enchantment.ARROW_FIRE, Enchantment.ARROW_INFINITE,
        Enchantment.DURABILITY
    };
    private static final Enchantment[] PICKAXE_ENCHANTMENTS = {
        Enchantment.DIG_SPEED, Enchantment.DURABILITY
    };
    private static final Enchantment[] SPADE_ENCHANTMENTS = {
        Enchantment.DIG_SPEED, Enchantment.DURABILITY
    };
    private static final Enchantment[] HOE_ENCHANTMENTS = {
        Enchantment.DURABILITY
    };
    /* MATERIALS */
    private static final EnumSet<Material> ARMOR_MATERIAL_SET = EnumSet.of(
        Material.DIAMOND_HELMET, Material.DIAMOND_CHESTPLATE,
        Material.DIAMOND_LEGGINGS, Material.DIAMOND_BOOTS,
        Material.IRON_HELMET, Material.IRON_CHESTPLATE,
        Material.IRON_LEGGINGS, Material.IRON_BOOTS,
        Material.GOLD_HELMET, Material.GOLD_CHESTPLATE,
        Material.GOLD_LEGGINGS, Material.GOLD_BOOTS,
        Material.CHAINMAIL_HELMET, Material.CHAINMAIL_CHESTPLATE,
        Material.CHAINMAIL_LEGGINGS, Material.CHAINMAIL_BOOTS,
        Material.LEATHER_HELMET, Material.LEATHER_CHESTPLATE,
        Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS
    );
    private static final EnumSet<Material> SWORD_MATERIAL_SET = EnumSet.of(
        Material.DIAMOND_SWORD, Material.IRON_SWORD, Material.GOLD_SWORD,
        Material.STONE_SWORD, Material.WOOD_SWORD
    );
    private static final EnumSet<Material> AXE_MATERIAL_SET = EnumSet.of(
        Material.DIAMOND_AXE, Material.IRON_AXE, Material.GOLD_AXE,
        Material.STONE_AXE, Material.WOOD_AXE
    );
    private static final EnumSet<Material> PICKAXE_MATERIAL_SET = EnumSet.of(
        Material.DIAMOND_PICKAXE, Material.IRON_PICKAXE, Material.GOLD_PICKAXE,
        Material.STONE_PICKAXE, Material.WOOD_PICKAXE
    );
    private static final EnumSet<Material> SPADE_MATERIAL_SET = EnumSet.of(
        Material.DIAMOND_SPADE, Material.IRON_SPADE, Material.GOLD_SPADE,
        Material.STONE_SPADE, Material.WOOD_SPADE
    );
    private static final EnumSet<Material> HOE_MATERIAL_SET = EnumSet.of(
        Material.DIAMOND_HOE, Material.IRON_HOE, Material.GOLD_HOE,
        Material.STONE_HOE, Material.WOOD_HOE
    );
    public static int tierNotify;
    public static FileConfiguration loreConfig;
    public static FileConfiguration tiersConfig;
    public static FileConfiguration enchantmentConfig;
    public static boolean damageTags;
    public static String damageString;
    public static String holyString;
    public static String bugString;
    public static String fireString;
    public static String thornsString;
    public static String defenseString;
    public static String fireDefenseString;
    public static String rangeDefenseString;
    public static String blastDefenseString;
    public static String fallDefenseString;

    public ItemStack item;
    public int amountBonus = 0;
    public int durabilityBonus = 0;
    public boolean autoEnchant = false;
    public boolean generateName = false;
    public boolean randomLore = false;
    public boolean tieredName = false;

    /**
     * Constructs a new Loot with the given ItemStack and bonus amount
     *
     * @param item The given ItemStack
     * @param amountBonus The extra amount for the loot
     */
    public Item(ItemStack item, int amountBonus) {
        this.item = item;
        this.amountBonus = amountBonus;
    }

    /**
     * Constructs a new Item from a Configuration Serialized phase
     *
     * @param map The map of data values
     */
    public Item(Map<String, Object> map) {
        String currentLine = null; //The value that is about to be loaded (used for debugging)
        try {
            Object number = map.get(currentLine = "Probability");
            probability = (number instanceof Double) ? (Double) number : (Integer) number;
            item = (ItemStack) map.get(currentLine = "ItemStack");
            if (item == null) {
                throw new RuntimeException("ItemStack cannot be null");
            }
            if (map.containsKey(currentLine = "BonusAmount")) {
                amountBonus = (Integer) map.get(currentLine);
            }
            if (map.containsKey(currentLine = "BonusDurability")) {
                durabilityBonus = (Integer) map.get(currentLine);
            }
            if (map.containsKey(currentLine = "AutoEnchant")) {
                autoEnchant = (Boolean) map.get(currentLine);
            }
            if (map.containsKey(currentLine = "GenerateName")) {
                generateName = (Boolean) map.get(currentLine);
            }
            if (map.containsKey(currentLine = "RandomLore")) {
                randomLore = (Boolean) map.get(currentLine);
            }
            if (map.containsKey(currentLine = "Tiered")) {
                tieredName = (Boolean) map.get(currentLine);
            }
        } catch (Exception ex) {
            //Print debug messages
            PhatLoots.logger.severe("Failed to load Item line: " + currentLine);
            PhatLoots.logger.severe("of PhatLoot: " + (PhatLoot.current == null ? "unknown" : PhatLoot.current));
            PhatLoots.logger.severe("Last successfull load was...");
            PhatLoots.logger.severe("PhatLoot: " + (PhatLoot.last == null ? "unknown" : PhatLoot.last));
            PhatLoots.logger.severe("Loot: " + (Loot.last == null ? "unknown" : Loot.last.toString()));
        }
    }

    /**
     * Adds the item to the item list
     *
     * @param lootBundle The loot that has been rolled for
     * @param lootingBonus The increased chance of getting rarer loots
     */
    @Override
    public void getLoot(LootBundle lootBundle, double lootingBonus) {
        lootBundle.addItem(getItem());
    }

    /**
     * Returns the information of the Item in the form of an ItemStack
     *
     * @return An ItemStack representation of the Loot
     */
    @Override
    public ItemStack getInfoStack() {
        ItemStack infoStack = item.clone();
        boolean air = infoStack.getType() == Material.AIR;
        if (air) {
            infoStack.setType(Material.GLASS_BOTTLE);
        }
        ItemMeta info = infoStack.hasItemMeta() ? infoStack.getItemMeta() : Bukkit.getItemFactory().getItemMeta(infoStack.getType());
        if (air) {
            info.setDisplayName("AIR");
        }

        //Add more specific details of the Item
        List<String> details;
        if (info.hasLore()) {
            details = info.getLore();
            details.add("§1-----------------------------");
        } else {
            details = new ArrayList();
        }
        details.add(ChatColor.DARK_BLUE + "Probability: " + ChatColor.GOLD + probability);
        if (amountBonus == 0) {
            details.add(ChatColor.DARK_BLUE + "Amount: " + ChatColor.GOLD + item.getAmount());
        } else {
            details.add(ChatColor.DARK_BLUE + "Amount: " + ChatColor.GOLD + item.getAmount() + '-' + (item.getAmount() + amountBonus));
        }
        if (autoEnchant) {
            details.add(ChatColor.GOLD + "Auto Enchanted");
        }
        if (generateName) {
            details.add(ChatColor.GOLD + "Generated Name");
        }
        if (randomLore) {
            details.add(ChatColor.GOLD + "Random Lore");
        }
        if (tieredName) {
            details.add(ChatColor.GOLD + "Tiered Name");
        }

        //Construct the ItemStack and return it
        info.setLore(details);
        infoStack.setItemMeta(info);
        return infoStack;
    }

    /**
     * Toggles a Loot setting depending on the type of Click
     *
     * @param click The type of Click (Only SHIFT_LEFT, SHIFT_RIGHT, and MIDDLE are used)
     * @return true if the Loot InfoStack should be refreshed
     */
    @Override
    public boolean onToggle(ClickType click) {
        switch (click) {
        case SHIFT_LEFT:
            autoEnchant = !autoEnchant;
            break;
        case SHIFT_RIGHT:
            generateName = !generateName;
            break;
        case MIDDLE:
            tieredName = !tieredName;
            break;
        default:
            return false;
        }
        return true;
    }

    /**
     * Modifies the amount associated with the Loot
     *
     * @param amount The amount to modify by (may be negative)
     * @param both true if both lower and upper ranges should be modified, false for only the upper range
     * @return true if the Loot InfoStack should be refreshed
     */
    @Override
    public boolean modifyAmount(int amount, boolean both) {
        if (both) {
            item.setAmount(item.getAmount() + amount);
            //Loop negative amount back to the Max Stack Size
            if (item.getAmount() < 0) {
                item.setAmount(item.getMaxStackSize());
            }
        } else {
            amountBonus += amount;
            //Loop negative amount back to 50
            if (amountBonus < 0) {
                amountBonus = 50;
            }
        }
        return true;
    }

    /**
     * Resets the amount of Loot to 1
     *
     * @return true if the Loot InfoStack should be refreshed
     */
    @Override
    public boolean resetAmount() {
        item.setAmount(1);
        amountBonus = 0;
        return true;
    }

    /**
     * Sets the Durability value of the Loot Item
     *
     * @param durability The Durability to be set
     */
    public void setDurability(short durability) {
        if (durability >= 0) {
            item.setDurability(durability);
        }
    }

    /**
     * Returns the item with the bonus amount, enchantments, etc.
     *
     * @return A clone of the Loot item
     */
    public ItemStack getItem() {
        //Clone the item before modifying it
        ItemStack clone = item.clone();
        Material mat = clone.getType();

        if (autoEnchant) {
            //Select Enchantments based on the Material
            String type;
            Enchantment[] enchantments;
            if (ARMOR_MATERIAL_SET.contains(mat)) {
                type = ARMOR;
                enchantments = ARMOR_ENCHANTMENTS;
            } else if (SWORD_MATERIAL_SET.contains(mat)) {
                type = SWORD;
                enchantments = SWORD_ENCHANTMENTS;
            } else if (AXE_MATERIAL_SET.contains(mat)) {
                type = AXE;
                enchantments = AXE_ENCHANTMENTS;
            } else if (PICKAXE_MATERIAL_SET.contains(mat)) {
                type = PICKAXE;
                enchantments = PICKAXE_ENCHANTMENTS;
            } else if (SPADE_MATERIAL_SET.contains(mat)) {
                type = SPADE;
                enchantments = SPADE_ENCHANTMENTS;
            } else if (HOE_MATERIAL_SET.contains(mat)) {
                type = HOE;
                enchantments = HOE_ENCHANTMENTS;
            } else if (mat == Material.BOW) {
                type = BOW;
                enchantments = BOW_ENCHANTMENTS;
            } else {
                type = "";
                enchantments = new Enchantment[0];
            }

            for (Enchantment enchantment : enchantments) {
                String key = type + '.' + enchantment.getName();
                if (enchantmentConfig.contains(key)) {
                    //Roll to discover which level the enchantment should be
                    ConfigurationSection config = enchantmentConfig.getConfigurationSection(key);
                    double totalPercent = 0.0D;
                    int level = 0;
                    double roll = roll();
                    for (String string : config.getKeys(false)) {
                        totalPercent += config.getDouble(string);
                        if (totalPercent > roll) {
                            break;
                        }
                        level++;
                    }

                    if (level > 0) {
                        clone.addUnsafeEnchantment(enchantment, level);
                    }
                }
            }
        }

        if (amountBonus > 0) {
            //Roll the stack size of the item
            clone.setAmount(clone.getAmount() + PhatLootsUtil.rollForInt(amountBonus));
        }

        if (durabilityBonus > 0) {
            //Roll for the durability of the item
            clone.setDurability((short) (clone.getDurability() + PhatLootsUtil.rollForInt(durabilityBonus)));
        }

        ItemMeta meta = clone.hasItemMeta()
                        ? clone.getItemMeta().clone()
                        : Bukkit.getItemFactory().getItemMeta(clone.getType());

        if (generateName || tieredName || randomLore) {
            StringBuilder nameBuilder = new StringBuilder();
            if (randomLore) {
                String folder = clone.getType() + clone.getEnchantments().toString();
                File dir = new File(PhatLoots.dataFolder, "Item Descriptions" + File.separator + folder);
                if (dir.exists()) {
                    //Choose a random file
                    File[] files = dir.listFiles();
                    File file = files[PhatLootsUtil.rollForInt(files.length)];

                    if (file.exists()) {
                        try (FileReader fReader = new FileReader(file)) {
                            try (BufferedReader bReader = new BufferedReader(fReader)) {
                                String line = bReader.readLine();
                                if (line != null) {
                                    if (line.charAt(0) == '&') {
                                        line = line.replace('&', ChatColor.COLOR_CHAR);
                                    }

                                    nameBuilder.append(line);

                                    List lore = new LinkedList();
                                    while ((line = bReader.readLine()) != null) {
                                        line = line.replace('&', ChatColor.COLOR_CHAR);
                                        lore.add(line);
                                    }
                                    meta.setLore(lore);
                                }
                            }
                        } catch (Exception ex) {
                            //Do nothing
                        }
                    } else {
                        PhatLoots.logger.severe("The Item Description " + file.getName() + " cannot be read");
                    }
                } else {
                    PhatLoots.logger.severe("You are attempting to use an undocumented feature (Random Lore), please contact Codisimus if you actually want to know how to use this.");
                }
            } else {
                nameBuilder.append(PhatLootsUtil.getItemName(clone));
            }

            if (generateName) {
                generateName(clone, nameBuilder);
            }

            if (tieredName) {
                getTieredName(clone, nameBuilder);
            }

            //Set the new display name of the item
            meta.setDisplayName(nameBuilder.toString());
            clone.setItemMeta(meta);
        }

        if (damageTags && meta != null && meta.hasLore()) {
            //Check for damage tags based on the Material
            List<String> lore = meta.getLore();
            ListIterator<String> itr = lore.listIterator();
            if (ARMOR_MATERIAL_SET.contains(mat)) {
                while (itr.hasNext()) {
                    String string = itr.next();
                    //Calculate protection based on the enchantments
                    switch (string) {
                    case THORNS:
                        if (clone.containsEnchantment(Enchantment.THORNS)) {
                            int lvl = clone.getEnchantments().get(Enchantment.THORNS);
                            itr.set(thornsString.replace("<chance>", String.valueOf(15 * lvl)));
                        } else {
                            itr.remove();
                        }
                        break;
                    case DEFENSE:
                        int amount = getBaseArmor(clone.getType());
                        if (clone.containsEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL)) {
                            int lvl = clone.getEnchantments().get(Enchantment.PROTECTION_ENVIRONMENTAL);
                            int epf = (int) Math.floor((6 + lvl * lvl) * 0.75 / 3);
                            int low = amount + (int) Math.ceil(epf / 2);
                            int high = amount + epf;
                            itr.set(defenseString.replace("<amount>", low + "-" + high));
                        } else {
                            itr.set(defenseString.replace("<amount>", String.valueOf(amount)));
                        }
                        break;
                    case FIRE_DEFENSE:
                        if (clone.containsEnchantment(Enchantment.PROTECTION_FIRE)) {
                            int lvl = clone.getEnchantments().get(Enchantment.PROTECTION_FIRE);
                            int epf = (int) Math.floor((6 + lvl * lvl) * 1.25 / 3);
                            int low = (int) Math.ceil(epf / 2);
                            int high = epf;
                            itr.set(fireDefenseString.replace("<amount>", low + "-" + high));
                        } else {
                            itr.remove();
                        }
                        break;
                    case RANGE_DEFENSE:
                        if (clone.containsEnchantment(Enchantment.PROTECTION_PROJECTILE)) {
                            int lvl = clone.getEnchantments().get(Enchantment.PROTECTION_PROJECTILE);
                            int epf = (int) Math.floor((6 + lvl * lvl) * 1.5 / 3);
                            int low = (int) Math.ceil(epf / 2);
                            int high = epf;
                            itr.set(rangeDefenseString.replace("<amount>", low + "-" + high));
                        } else {
                            itr.remove();
                        }
                        break;
                    case BLAST_DEFENSE:
                        if (clone.containsEnchantment(Enchantment.PROTECTION_EXPLOSIONS)) {
                            int lvl = clone.getEnchantments().get(Enchantment.PROTECTION_EXPLOSIONS);
                            int epf = (int) Math.floor((6 + lvl * lvl) * 1.5 / 3);
                            int low = (int) Math.ceil(epf / 2);
                            int high = epf;
                            itr.set(blastDefenseString.replace("<amount>", low + "-" + high));
                        } else {
                            itr.remove();
                        }
                        break;
                    case FALL_DEFENSE:
                        if (clone.containsEnchantment(Enchantment.PROTECTION_FALL)) {
                            int lvl = clone.getEnchantments().get(Enchantment.PROTECTION_FALL);
                            int epf = (int) Math.floor((6 + lvl * lvl) * 2.5 / 3);
                            int low = (int) Math.ceil(epf / 2);
                            int high = epf;
                            itr.set(fallDefenseString.replace("<amount>", low + "-" + high));
                        } else {
                            itr.remove();
                        }
                        break;
                    default:
                        break;
                    }
                }
            } else if (mat == Material.BOW) {
                while (itr.hasNext()) {
                    String string = itr.next();
                    //Calculate damages based on the enchantments
                    switch (string) {
                    case DAMAGE:
                        int baseLow = 1;
                        int baseHigh = 10;
                        if (clone.containsEnchantment(Enchantment.DAMAGE_ALL)) {
                            int lvl = clone.getEnchantments().get(Enchantment.ARROW_DAMAGE);
                            double bonus = lvl == 0
                                    ? 0
                                    : 0.25;
                            bonus += (0.25 * lvl);
                            int low = baseLow + (int) (baseLow * bonus);
                            int high = baseHigh + (int) (baseHigh * bonus);
                            itr.set(damageString.replace("<amount>", low + "-" + high));
                        } else {
                            itr.set(damageString.replace("<amount>", baseLow + "-" + baseHigh));
                        }
                        break;
                    case FIRE:
                        if (clone.containsEnchantment(Enchantment.ARROW_FIRE)) {
                            itr.set(fireString.replace("<amount>", "4"));
                        } else {
                            itr.remove();
                        }
                        break;
                    default:
                        break;
                    }
                }
            } else {
                while (itr.hasNext()) {
                    String string = itr.next();
                    //Calculate damages based on the enchantments
                    switch (string) {
                    case DAMAGE:
                        int baseLow = getBaseDamage(clone.getType());
                        int baseHigh = (int) (baseLow * 1.5D) + 2;
                        if (clone.containsEnchantment(Enchantment.DAMAGE_ALL)) {
                            int lvl = clone.getEnchantments().get(Enchantment.DAMAGE_ALL);
                            int low = baseLow + lvl;
                            int high = baseHigh + 3 * lvl;
                            itr.set(damageString.replace("<amount>", low + "-" + high));
                        } else {
                            itr.set(damageString.replace("<amount>", baseLow + "-" + baseHigh));
                        }
                        break;
                    case HOLY:
                        if (clone.containsEnchantment(Enchantment.DAMAGE_UNDEAD)) {
                            int lvl = clone.getEnchantments().get(Enchantment.DAMAGE_UNDEAD);
                            int low = lvl;
                            int high = 4 * lvl;
                            itr.set(holyString.replace("<amount>", low + "-" + high));
                        } else {
                            itr.remove();
                        }
                        break;
                    case BUG:
                        if (clone.containsEnchantment(Enchantment.DAMAGE_ARTHROPODS)) {
                            int lvl = clone.getEnchantments().get(Enchantment.DAMAGE_ARTHROPODS);
                            int low = lvl;
                            int high = 4 * lvl;
                            itr.set(bugString.replace("<amount>", low + "-" + high));
                        } else {
                            itr.remove();
                        }
                        break;
                    case FIRE:
                        if (clone.containsEnchantment(Enchantment.FIRE_ASPECT)) {
                            int lvl = clone.getEnchantments().get(Enchantment.FIRE_ASPECT);
                            int amount = 4 * lvl;
                            itr.set(fireString.replace("<amount>", String.valueOf(amount)));
                        } else {
                            itr.remove();
                        }
                        break;
                    default:
                        break;
                    }
                }
            }

            //Apply the new lore to the item
            meta.setLore(lore);
            clone.setItemMeta(meta);
        }

        return clone;
    }

    /**
     * Generates a custom name for the given item based on its enchantments
     *
     * @param item The given ItemStack
     * @param nameBuilder The StringBuilder that will be changed to the new name
     */
    private void generateName(ItemStack item, StringBuilder nameBuilder) {
        Material mat = item.getType();
        Map enchantments = item.getEnchantments();
        String type;
        Enchantment enchantment;
        int level;
        String lore;
        //Check enchantments based on the Material
        if (ARMOR_MATERIAL_SET.contains(mat)) {
            type = ARMOR;
            enchantment = Enchantment.PROTECTION_FIRE;
            level = getLevel(enchantments, enchantment);
            lore = loreConfig.getString(type + '.' + enchantment.getName() + '.' + level);
            if (lore != null) {
                nameBuilder.insert(0, ' ');
                nameBuilder.insert(0, lore);
            }
            enchantment = enchantments.containsKey(Enchantment.THORNS)
                          ? Enchantment.THORNS
                          : Enchantment.DURABILITY;
            level = getLevel(enchantments, enchantment);
            lore = loreConfig.getString(type + '.' + enchantment.getName() + '.' + level);
            if (lore != null) {
                nameBuilder.insert(0, ' ');
                nameBuilder.insert(0, lore);
            }
            enchantment = getTrump(enchantments, Enchantment.PROTECTION_ENVIRONMENTAL, Enchantment.PROTECTION_PROJECTILE, Enchantment.PROTECTION_EXPLOSIONS);
            level = getLevel(enchantments, enchantment);
            lore = loreConfig.getString(type + '.' + enchantment.getName() + '.' + level);
            if (lore != null) {
                nameBuilder.append(' ');
                nameBuilder.append(lore);
            }
        } else if (SWORD_MATERIAL_SET.contains(mat)) {
            type = SWORD;
            enchantment = getTrump(enchantments, Enchantment.DAMAGE_ARTHROPODS, Enchantment.DAMAGE_ALL);
            level = getLevel(enchantments, enchantment);
            lore = loreConfig.getString(type + '.' + enchantment.getName() + '.' + level);
            if (lore != null) {
                nameBuilder.replace(nameBuilder.length() - 5, nameBuilder.length(), lore);
            }
            enchantment = enchantments.containsKey(Enchantment.FIRE_ASPECT)
                          ? Enchantment.FIRE_ASPECT
                          : Enchantment.DURABILITY;
            level = getLevel(enchantments, enchantment);
            lore = loreConfig.getString(type + '.' + enchantment.getName() + '.' + level);
            if (lore != null) {
                nameBuilder.insert(0, ' ');
                nameBuilder.insert(0, lore);
            }
            enchantment = Enchantment.LOOT_BONUS_MOBS;
            level = getLevel(enchantments, enchantment);
            lore = loreConfig.getString(type + '.' + enchantment.getName() + '.' + level);
            if (lore != null) {
                nameBuilder.append(' ');
                nameBuilder.append(lore);
            }
            enchantment = getTrump(enchantments, Enchantment.KNOCKBACK, Enchantment.DAMAGE_UNDEAD);
            level = getLevel(enchantments, enchantment);
            lore = loreConfig.getString(type + '.' + enchantment.getName() + '.' + level);
            if (lore != null) {
                nameBuilder.append(' ');
                nameBuilder.append(lore);
            }
        } else if (AXE_MATERIAL_SET.contains(mat)) {
            type = AXE;
            enchantment = getTrump(enchantments, Enchantment.DAMAGE_ARTHROPODS, Enchantment.DAMAGE_ALL);
            level = getLevel(enchantments, enchantment);
            lore = loreConfig.getString(type + '.' + enchantment.getName() + '.' + level);
            if (lore != null) {
                nameBuilder.replace(nameBuilder.length() - 3, nameBuilder.length(), lore);
            }
            enchantment = enchantments.containsKey(Enchantment.FIRE_ASPECT)
                          ? Enchantment.FIRE_ASPECT
                          : Enchantment.DURABILITY;
            level = getLevel(enchantments, enchantment);
            lore = loreConfig.getString(type + '.' + enchantment.getName() + '.' + level);
            if (lore != null) {
                nameBuilder.insert(0, ' ');
                nameBuilder.insert(0, lore);
            }
            enchantment = Enchantment.LOOT_BONUS_MOBS;
            level = getLevel(enchantments, enchantment);
            lore = loreConfig.getString(type + '.' + enchantment.getName() + '.' + level);
            if (lore != null) {
                nameBuilder.append(' ');
                nameBuilder.append(lore);
            }
            enchantment = getTrump(enchantments, Enchantment.KNOCKBACK, Enchantment.DAMAGE_UNDEAD);
            level = getLevel(enchantments, enchantment);
            lore = loreConfig.getString(type + '.' + enchantment.getName() + '.' + level);
            if (lore != null) {
                nameBuilder.append(' ');
                nameBuilder.append(lore);
            }
        } else if (PICKAXE_MATERIAL_SET.contains(mat)) {
            type = PICKAXE;
            enchantment = Enchantment.DIG_SPEED;
            level = getLevel(enchantments, enchantment);
            lore = loreConfig.getString(type + '.' + enchantment.getName() + '.' + level);
            if (lore != null) {
                nameBuilder.replace(nameBuilder.length() - 7, nameBuilder.length(), lore);
            }
            enchantment = Enchantment.DURABILITY;
            level = getLevel(enchantments, enchantment);
            lore = loreConfig.getString(type + '.' + enchantment.getName() + '.' + level);
            if (lore != null) {
                nameBuilder.insert(0, ' ');
                nameBuilder.insert(0, lore);
            }
        } else if (SPADE_MATERIAL_SET.contains(mat)) {
            type = SPADE;
            enchantment = Enchantment.DIG_SPEED;
            level = getLevel(enchantments, enchantment);
            lore = loreConfig.getString(type + '.' + enchantment.getName() + '.' + level);
            if (lore != null) {
                nameBuilder.replace(nameBuilder.length() - 5, nameBuilder.length(), lore);
            }
            enchantment = Enchantment.DURABILITY;
            level = getLevel(enchantments, enchantment);
            lore = loreConfig.getString(type + '.' + enchantment.getName() + '.' + level);
            if (lore != null) {
                nameBuilder.insert(0, ' ');
                nameBuilder.insert(0, lore);
            }
        } else if (HOE_MATERIAL_SET.contains(mat)) {
            type = HOE;
            enchantment = Enchantment.DURABILITY;
            level = getLevel(enchantments, enchantment);
            lore = loreConfig.getString(type + '.' + enchantment.getName() + '.' + level);
            if (lore != null) {
                nameBuilder.insert(0, ' ');
                nameBuilder.insert(0, lore);
            }
        } else if (mat == Material.BOW) {
            type = BOW;
            enchantment = Enchantment.ARROW_DAMAGE;
            level = getLevel(enchantments, enchantment);
            lore = loreConfig.getString(type + '.' + enchantment.getName() + '.' + level);
            if (lore != null) {
                nameBuilder.replace(0, 3, lore);
            }
            enchantment = Enchantment.DURABILITY;
            level = getLevel(enchantments, enchantment);
            lore = loreConfig.getString(type + '.' + enchantment.getName() + '.' + level);
            if (lore != null) {
                nameBuilder.insert(0, ' ');
                nameBuilder.insert(0, lore);
            }
            enchantment = Enchantment.ARROW_FIRE;
            level = getLevel(enchantments, enchantment);
            lore = loreConfig.getString(type + '.' + enchantment.getName() + '.' + level);
            if (lore != null) {
                nameBuilder.insert(0, ' ');
                nameBuilder.insert(0, lore);
            }
            enchantment = Enchantment.ARROW_INFINITE;
            level = getLevel(enchantments, enchantment);
            lore = loreConfig.getString(type + '.' + enchantment.getName() + '.' + level);
            if (lore != null) {
                nameBuilder.append(' ');
                nameBuilder.append(lore);
            }
            enchantment = Enchantment.ARROW_KNOCKBACK;
            level = getLevel(enchantments, enchantment);
            lore = loreConfig.getString(type + '.' + enchantment.getName() + '.' + level);
            if (lore != null) {
                nameBuilder.append(' ');
                nameBuilder.append(lore);
            }
        }
    }

    /**
     * Modifys the name of the given item to add a tier based on its enchantments and material
     *
     * @param item The given ItemStack
     * @param nameBuilder The StringBuilder that will be changed to the new name
     */
    private void getTieredName(ItemStack item, StringBuilder nameBuilder) {
        int tier = tiersConfig.getInt(BASE_VALUES + DIVIDER + item.getType().name());

        Map<Enchantment, Integer> enchantments = item.getEnchantments();
        for (Enchantment e : enchantments.keySet()) {
            tier += tiersConfig.getInt(ENCHANTMENT_VALUES + DIVIDER + e.getName() + DIVIDER + enchantments.get(e));
        }

        //Add the suffix and prefix for the given tier
        for (String string : tiersConfig.getConfigurationSection(TIERS).getKeys(false)) {
            if (tier > Integer.parseInt(string)) {
                //Hack to fix encoding issues
                String prefix = tiersConfig.getString(TIERS + DIVIDER + string + ".Prefix");
                if (prefix.startsWith("Â")) {
                    prefix = prefix.substring(1);
                }
                nameBuilder.insert(0, prefix);
                nameBuilder.append(tiersConfig.getString(TIERS + DIVIDER + string + ".Suffix"));
                break;
            }
        }

        if (tier > tierNotify) {
            PhatLoots.logger.info(nameBuilder.toString() + " [Tier " + tier + "] has been generated");
        }
    }

    /**
     * Returns the amount of damage that an uncritical hit from the given Material would deal
     *
     * @param type The given Material
     * @return The base damage of the Material
     */
    private int getBaseDamage(Material type) {
        switch (type) {
        case WOOD_SPADE: return 1;
        case WOOD_PICKAXE: return 2;
        case WOOD_AXE: return 3;
        case WOOD_SWORD: return 4;
        case GOLD_SPADE: return 1;
        case GOLD_PICKAXE: return 2;
        case GOLD_AXE: return 3;
        case GOLD_SWORD: return 4;
        case STONE_SPADE: return 2;
        case STONE_PICKAXE: return 3;
        case STONE_AXE: return 4;
        case STONE_SWORD: return 5;
        case IRON_SPADE: return 3;
        case IRON_PICKAXE: return 4;
        case IRON_AXE: return 5;
        case IRON_SWORD: return 6;
        case DIAMOND_SPADE: return 4;
        case DIAMOND_PICKAXE: return 5;
        case DIAMOND_AXE: return 6;
        case DIAMOND_SWORD: return 7;
        default: return 1;
        }
    }

    /**
     * Returns the amount of damage that is prevented by the given Material
     *
     * @param type The given Material
     * @return The base armor of the Material
     */
    private int getBaseArmor(Material type) {
        switch (type) {
        case LEATHER_BOOTS: return 1;
        case LEATHER_LEGGINGS: return 2;
        case LEATHER_CHESTPLATE: return 3;
        case LEATHER_HELMET: return 1;
        case GOLD_BOOTS: return 1;
        case GOLD_LEGGINGS: return 3;
        case GOLD_CHESTPLATE: return 5;
        case GOLD_HELMET: return 1;
        case CHAINMAIL_BOOTS: return 2;
        case CHAINMAIL_LEGGINGS: return 4;
        case CHAINMAIL_CHESTPLATE: return 5;
        case CHAINMAIL_HELMET: return 1;
        case IRON_BOOTS: return 2;
        case IRON_LEGGINGS: return 5;
        case IRON_CHESTPLATE: return 6;
        case IRON_HELMET: return 2;
        case DIAMOND_BOOTS: return 3;
        case DIAMOND_LEGGINGS: return 6;
        case DIAMOND_CHESTPLATE: return 8;
        case DIAMOND_HELMET: return 3;
        default: return 0;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        int amount = item.getAmount();
        sb.append(amount);
        if (amountBonus > 0) {
            sb.append('-');
            sb.append(amount + amountBonus);
        }

        sb.append(" of ");
        if (tieredName) {
            sb.append("tiered ");
        }
        sb.append(PhatLootsUtil.getItemName(item));

        sb.append(" @ ");
        //Only display the decimal values if the probability is not a whole number
        sb.append(Math.floor(probability) == probability ? String.valueOf((int) probability) : String.valueOf(probability));

        sb.append("%");

        return sb.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Item)) {
            return false;
        }

        Item loot = (Item) object;
        return loot.item.equals(item)
                && loot.amountBonus == amountBonus
                && loot.durabilityBonus == durabilityBonus
                && loot.autoEnchant == autoEnchant
                && loot.generateName == generateName
                && loot.randomLore == randomLore
                && loot.tieredName == tieredName;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (item != null ? item.hashCode() : 0);
        hash = 37 * hash + amountBonus;
        hash = 37 * hash + durabilityBonus;
        hash = 37 * hash + (int) (Double.doubleToLongBits(probability) ^ Double.doubleToLongBits(probability) >>> 32);
        hash = 37 * hash + (autoEnchant ? 1 : 0);
        hash = 37 * hash + (generateName ? 1 : 0);
        hash = 37 * hash + (randomLore ? 1 : 0);
        hash = 37 * hash + (tieredName ? 1 : 0);
        return hash;
    }

    /**
     * Returns the Enchantment which has the highest level
     *
     * @param enchantments The map of Enchantments and their levels
     * @param enchants The list of Enchantments that we care about
     * @return the Enchantment which has the highest level
     */
    private Enchantment getTrump(Map<Enchantment, Integer> enchantments, Enchantment... enchants) {
        int highestLevel = -1;
        Enchantment trump = null;
        for (Enchantment enchant : enchants) {
            int level = getLevel(enchantments, enchant);
            if (level > highestLevel) {
                trump = enchant;
                highestLevel = level;
            }
        }
        return trump;
    }

    /**
     * Returns the level of the given Enchantment
     *
     * @param enchantments The map of Enchantments and their levels
     * @param enchantment The given Enchantment
     * @return the level of the Enchantment or 0 if the Enchantment is not present
     */
    private int getLevel(Map<Enchantment, Integer> enchantments, Enchantment enchantment) {
        Integer level = enchantments.get(enchantment);
        return level == null ? 0 : level;
    }

    @Override
    public Map<String, Object> serialize() {
        Map map = new TreeMap();
        map.put("Probability", probability);
        map.put("ItemStack", item);
        if (amountBonus > 0) {
            map.put("BonusAmount", amountBonus);
        }
        if (durabilityBonus > 0) {
            map.put("BonusDurability", durabilityBonus);
        }
        if (autoEnchant) {
            map.put("AutoEnchant", autoEnchant);
        }
        if (generateName) {
            map.put("GenerateName", generateName);
        }
        if (randomLore) {
            map.put("RandomLore", randomLore);
        }
        if (tieredName) {
            map.put("Tiered", tieredName);
        }
        return map;
    }
}
