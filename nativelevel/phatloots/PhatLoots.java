package nativelevel.phatloots;

import nativelevel.phatloots.events.ChestRespawnEvent.RespawnReason;
import nativelevel.phatloots.gui.InventoryListener;
import nativelevel.phatloots.regions.RegionHook;
import nativelevel.phatloots.regions.WorldGuardRegionHook;
import com.google.common.io.Files;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import nativelevel.KoM;
import nativelevel.phatloots.commands.CommandHandler;
import nativelevel.phatloots.commands.LootCommand;
import nativelevel.phatloots.commands.ManageLootCommand;
import nativelevel.phatloots.commands.ManageMoneyLootCommand;
import nativelevel.phatloots.commands.VariableLootCommand;
import nativelevel.phatloots.listeners.CitizensListener;
import nativelevel.phatloots.listeners.DispenserListener;
import nativelevel.phatloots.listeners.FishingListener;
import nativelevel.phatloots.listeners.LootBagListener;
import nativelevel.phatloots.listeners.LootingBonusListener;
import nativelevel.phatloots.listeners.MobDeathListener;
import nativelevel.phatloots.listeners.MobListener;
import nativelevel.phatloots.listeners.MobSpawnListener;
import nativelevel.phatloots.listeners.PhatLootsListener;
import nativelevel.phatloots.loot.CommandLoot;
import nativelevel.phatloots.loot.Experience;
import nativelevel.phatloots.loot.Item;
import nativelevel.phatloots.loot.LootCollection;
import nativelevel.phatloots.loot.Message;
import nativelevel.phatloots.loot.Money;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.plugin.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Loads Plugin and manages Data/Listeners/etc.
 *
 * @author Codisimus
 */
public class PhatLoots {
    public static JavaPlugin plugin;
    public static Logger logger;
    public static Economy econ = null;
    public static String dataFolder;
    public static boolean mythicDropsSupport;
    public static boolean mythicMobsSupport;
    public static long autoSavePeriod;
    public static CommandHandler handler;
    public static final HashMap<String, RegionHook> regionHooks = new HashMap<>(); //Plugin Name -> RegionHook
    public static final EnumMap<Material, HashMap<String, String>> types = new EnumMap(Material.class); //Material -> World Name -> PhatLoot Name
    private static final HashMap<String, PhatLoot> phatLoots = new HashMap<>(); //PhatLoot Name -> PhatLoot

    public static void main(String[] args) {
        //Do Nothing - For debugging within NetBeans IDE
    }

    public void onDisable() {
        saveLootTimes();

        //Respawn all chests
        for (PhatLootChest chest : (Collection<PhatLootChest>) PhatLootChest.chestsToRespawn.clone()) {
            chest.respawn(RespawnReason.PLUGIN_DISABLED);
        }
    }

    public void onEnable () {
        plugin = KoM._instance;
        logger = plugin.getLogger();
        
        /* Create data folders */
        File dir = plugin.getDataFolder();
        if (!dir.isDirectory()) {
            dir.mkdir();
        }

        dataFolder = dir.getPath();

        dir = new File(dataFolder, "LootTables");
        if (!dir.isDirectory()) {
            dir.mkdir();
        }

        dir = new File(dataFolder, "Chests");
        if (!dir.isDirectory()) {
            dir.mkdir();
        }

        dir = new File(dataFolder, "LootTimes");
        if (!dir.isDirectory()) {
            dir.mkdir();
        }

        dir = new File(dataFolder, "Addons");
        if (!dir.isDirectory()) {
            dir.mkdir();
        }

        //Save SampleLoot.yml if it does not exist
        File file = new File(dataFolder, "LootTables" + File.separator + "SampleLoot.yml");
        if (!file.exists()) {
            try (InputStream inputStream = plugin.getResource("SampleLoot.yml")) {
                try (OutputStream outputStream = new FileOutputStream(file)) {
                    int read;
                    byte[] bytes = new byte[1024];

                    while ((read = inputStream.read(bytes)) != -1) {
                        outputStream.write(bytes, 0, read);
                    }
                }
            } catch (IOException ex) {
                logger.log(Level.WARNING, "Could not save resource: SampleLoot.yml", ex);
            }
        }

        mythicDropsSupport = Bukkit.getPluginManager().isPluginEnabled("MythicDrops");
        if (mythicDropsSupport) {
            logger.info("Enabling MythicDrops support");
        } else if (isDebug()) {
            debug("Plugin MythicDrops could not be found, support has been turned off.");
        }

        mythicMobsSupport = Bukkit.getPluginManager().isPluginEnabled("MythicMobs");
        if (mythicMobsSupport) {
            logger.info("Enabling MythicMobs support");
        } else if (isDebug()) {
            debug("Plugin MythicMobs could not be found, support has been turned off.");
        }

        /* Register Buttons */
        LootCollection.registerButton();
        Experience.registerButton();
        if (PhatLoots.econ != null) {
            Money.registerButton();
        }

        /* Register the command found in the plugin.yml */
        String command = "loot";
        handler = new CommandHandler(plugin, command);
        handler.registerCommands(LootCommand.class);
        handler.registerCommands(ManageLootCommand.class);
        handler.registerCommands(VariableLootCommand.class);
        if (PhatLoots.econ != null) {
            handler.registerCommands(ManageMoneyLootCommand.class);
        }

        /* Register RegionHooks */
        registerRegionHook("WorldGuard", new WorldGuardRegionHook());

        /* Register ConfigurationSerializable classes */
        ConfigurationSerialization.registerClass(PhatLoot.class, "PhatLoot");
        ConfigurationSerialization.registerClass(LootCollection.class, "LootCollection");
        ConfigurationSerialization.registerClass(Item.class, "Item");
        ConfigurationSerialization.registerClass(CommandLoot.class, "Command");
        ConfigurationSerialization.registerClass(Message.class, "Message");
        ConfigurationSerialization.registerClass(Experience.class, "Experience");
        ConfigurationSerialization.registerClass(Money.class, "Money");

        /* Load External PhatLoots Addons */
        //Buttons, Tools, CodCommands, RegionHooks, and ConfigurationSerializable classes should be registered from within the Addon
        for (Plugin addon : Bukkit.getPluginManager().loadPlugins(dir)) {
            Bukkit.getPluginManager().enablePlugin(addon);
        }

        /* Register Events */
        registerEvents();

        /* Load PhatLoot/Chest data */
        load();

        /* Start save repeating task */
        if (autoSavePeriod > 0) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    saveLootTimes();
                }
            }.runTaskTimer(plugin, autoSavePeriod, autoSavePeriod);
        }
    }

    /**
     * Reloads the config from the config.yml file.
     * Loads values from the newly loaded config.
     * This method is automatically called when the plugin is enabled
     */
    public void reloadConfig() {
        //Save the config file if it does not already exist
        plugin.saveDefaultConfig();

        //Load values from the config now that it has been reloaded
        PhatLootsConfig.load();

        setupEconomy();
    }

    /**
     * Registers all Event Listeners that PhatLoots uses
     * Most of these Listeners may be turned of from the config
     * Enabled Listeners are logged at the INFO level
     */
    private void registerEvents() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PhatLootsListener(), plugin);
        pm.registerEvents(new InventoryListener(), plugin);
        if (pm.isPluginEnabled("Citizens")) {
            logger.info("Listening for Citizens NPC deaths");
            pm.registerEvents(new CitizensListener(), plugin);
        } else if (isDebug()) {
            debug("Plugin Citizens could not be found, support has been turned off.");
        }
        if (plugin.getConfig().getBoolean("LootBags")) {
            logger.info("Listening for Loot bags");
            pm.registerEvents(new LootBagListener(), plugin);
        } else if (isDebug()) {
            debug("LootBags have been turned off.");
        }
        if (plugin.getConfig().getBoolean("DispenserLoot")) {
            logger.info("Listening for Dispensers");
            pm.registerEvents(new DispenserListener(), plugin);
        } else if (isDebug()) {
            debug("DispenserLoot has been turned off.");
        }

        //Find and set the RegionHook
        String regionPlugin = plugin.getConfig().getString("RegionPlugin");
        if (isDebug()) {
            debug("RegionPlugin is set to " + regionPlugin);
            debug("Registered Region plugins are " + regionHooks.keySet());
        }
        if (regionPlugin.equals("auto")) {
            for (Entry<String, RegionHook> entry : regionHooks.entrySet()) {
                regionPlugin = entry.getKey();
                if (Bukkit.getPluginManager().isPluginEnabled(regionPlugin)) {
                    if (isDebug()) {
                        debug("Plugin " + regionPlugin + " has been found, applying associated RegionHook.");
                    }
                    MobListener.regionHook = entry.getValue();
                    break;
                } else if (isDebug()) {
                    debug("Plugin " + regionPlugin + " could not be found, moving on...");
                }
            }
            if (isDebug()) {
                debug("No compatiable plugins could be found, region support has been turned off");
            }
        } else if (Bukkit.getPluginManager().isPluginEnabled(regionPlugin)) {
            if (isDebug()) {
                debug("Plugin " + regionPlugin + " has been found, applying associated RegionHook.");
            }
            MobListener.regionHook = regionHooks.get(regionPlugin);
        } else if (isDebug()) {
            debug("Plugin " + regionPlugin + " could not be found, moving on...");
        }

        if (plugin.getConfig().getBoolean("MobDropLoot")) {
            StringBuilder sb = new StringBuilder();
            sb.append("Listening for Mob deaths");
            MobDeathListener listener = new MobDeathListener();
            listener.mobWorlds = plugin.getConfig().getBoolean("WorldMobDropLoot");
            listener.mobRegions = plugin.getConfig().getBoolean("RegionMobDropLoot")
                                  && MobListener.regionHook != null;
            if (listener.mobWorlds) {
                sb.append(" w/ MultiWorld support");
            } else if (isDebug()) {
                debug("MultiWorld support has been turned off for Mob loot.");
            }
            if (listener.mobRegions) {
                sb.append(listener.mobWorlds ? " and " : " w/ ");
                sb.append(regionPlugin);
                sb.append(" Regions");
            }
            logger.info(sb.toString());
            pm.registerEvents(listener, plugin);
        } else if (isDebug()) {
            debug("Mob loot has been turned off.");
        }
        if (plugin.getConfig().getBoolean("MobSpawnLoot")) {
            StringBuilder sb = new StringBuilder();
            sb.append("Listening for Mob spawns");
            MobSpawnListener listener = new MobSpawnListener();
            listener.mobWorlds = plugin.getConfig().getBoolean("WorldMobSpawnLoot");
            listener.mobRegions = plugin.getConfig().getBoolean("RegionMobSpawnLoot")
                                  && MobListener.regionHook != null;
            if (listener.mobWorlds) {
                sb.append(" w/ MultiWorld support");
            } else if (isDebug()) {
                debug("MultiWorld support has been turned off for Mob armor/weapon spawning.");
            }
            if (listener.mobRegions) {
                sb.append(listener.mobWorlds ? " and " : " w/ ");
                sb.append(regionPlugin);
                sb.append(" Regions");
            }
            logger.info(sb.toString());
            pm.registerEvents(listener, plugin);
        } else if (isDebug()) {
            debug("Mob armor/weapon spawning has been turned off.");
        }

        if (plugin.getConfig().getBoolean("FishingLoot")) {
            logger.info("Listening for Players fishing");
            pm.registerEvents(new FishingListener(), plugin);
        } else if (isDebug()) {
            debug("Fishing loot has been turned off.");
        }
        if (plugin.getConfig().getBoolean("VotifierLoot")) {

        } else if (isDebug()) {
            debug("Votifier loot has been turned off.");
        }

        List<Double> bonusPermList = plugin.getConfig().getDoubleList("LootBonusPermissions");
        if (!bonusPermList.isEmpty()) {
            LootingBonusListener listener = new LootingBonusListener();
            listener.setLootingBonusAmounts(bonusPermList);
            pm.registerEvents(listener, plugin);
        }
    }

    /**
     * Registers a RegionHook to be loaded by PhatLoots
     *
     * @param pluginName The name of the Plugin that the hook is used for
     * @param regionHook The RegionHook to register
     */
    public static void registerRegionHook(String pluginName, RegionHook regionHook) {
        regionHooks.put(pluginName, regionHook);
        if (isDebug()) {
            debug("A RegionHook has been registered for " + pluginName);
        }
    }

    /**
     * Loads each PhatLoot that has a LootTable file
     */
    public static void load() {
        //Load each YAML file in the LootTables folder
        File dir = new File(dataFolder, "LootTables");
        if (isDebug()) {
            debug(dir.listFiles(PhatLootsUtil.YAML_FILTER).length + " loot table(s) have been found in " + dir.getPath());
        }
        for (File file : dir.listFiles(PhatLootsUtil.YAML_FILTER)) {
            try {
                String name = file.getName();
                name = name.substring(0, name.length() - PhatLootsUtil.YAML_EXTENSION.length());
                YamlConfiguration config = loadConfig(file);

                //Ensure the PhatLoot name matches the file name
                PhatLoot phatLoot = (PhatLoot) config.get(config.contains(name)
                                                          ? name
                                                          : config.getKeys(false).iterator().next());
                if (!phatLoot.name.equals(name)) {
                    if (isDebug()) {
                        debug("PhatLoot name (" + phatLoot.name + ") does not match file name (" + name + "), renaming PhatLoot to " + name);
                    }
                    phatLoot.name = name;
                }
                phatLoots.put(name, phatLoot);
                phatLoot.save();
            } catch (Exception ex) {
                logger.log(Level.SEVERE, "Failed to load " + file.getName(), ex);
            }
        }
        if (isDebug()) {
            debug(phatLoots.size() + " loot tables were succesfully loaded.");
        }
    }

    /**
     * Saves all data for each PhatLoot
     */
    public static void saveAll() {
        for (PhatLoot phatLoot : phatLoots.values()) {
            phatLoot.saveAll();
        }
    }

    /**
     * Returns true if a PhatLoot by the given name exists
     *
     * @param name The given name
     * @return true if a PhatLoot by the given name exists
     */
    public static boolean hasPhatLoot(String name) {
        return phatLoots.containsKey(name);
    }

    /**
     * Returns the Collection of all PhatLoots
     *
     * @return The Collection of all PhatLoots
     */
    public static Collection<PhatLoot> getPhatLoots() {
        return phatLoots.values();
    }

    /**
     * Adds the given PhatLoot to the collection of PhatLoots
     *
     * @param phatLoot The given PhatLoot
     */
    public static void addPhatLoot(PhatLoot phatLoot) {
        phatLoots.put(phatLoot.name, phatLoot);
        phatLoot.save();
    }

    /**
     * Removes the given PhatLoot from the collection of PhatLoots.
     * PhatLoot files are also deleted
     *
     * @param phatLoot The given PhatLoot
     */
    public static void removePhatLoot(PhatLoot phatLoot) {
        phatLoots.remove(phatLoot.name);
        new File(dataFolder, "LootTables" + File.separator + phatLoot.name + PhatLootsUtil.YAML_EXTENSION).delete();
        new File(dataFolder, "Chests" + File.separator + phatLoot.name + PhatLootsUtil.TEXT_EXTENSION).delete();
        new File(dataFolder, "LootTimes" + File.separator + phatLoot.name + PhatLootsUtil.PROPERTIES_EXTENSION).delete();
    }

    /**
     * Returns the PhatLoot of the given name
     *
     * @param name The name of the PhatLoot
     * @return The PhatLoot with the given name or null if not found
     */
    public static PhatLoot getPhatLoot(String name) {
        return name == null ? null : phatLoots.get(name);
    }

    /**
     * Returns a List of all PhatLoots that are linked to the given Block
     * PhatLoots which the given Player does not have permission to loot are not returned
     *
     * @param block The given Block
     * @param player The given Player
     * @return The LinkedList of PhatLoots
     */
    public static LinkedList<PhatLoot> getPhatLoots(Block block, Player player) {
        LinkedList<PhatLoot> phatLootList = getPhatLoots(block);
        Iterator<PhatLoot> itr = phatLootList.iterator();
        while (itr.hasNext()) {
            PhatLoot phatLoot = itr.next();
            if (!PhatLootsUtil.canLoot(player, phatLoot)) {
                phatLootList.remove(phatLoot);
            }
        }
        return phatLootList;
    }

    /**
     * Returns a List of all PhatLoots that are linked to the given Block
     * AutoLinking is first checked and then explicit linking if AutoLinking returns 0 results
     *
     * @param block The given Block
     * @return The LinkedList of PhatLoots
     */
    public static LinkedList<PhatLoot> getPhatLoots(Block block) {
        LinkedList<PhatLoot> phatLootList = getAutoLinkedPhatLoots(block);

        if (phatLootList.isEmpty()) {
            phatLootList = getExplicitlyLinkedPhatLoots(block);
        }

        return phatLootList;
    }

    /**
     * Returns a List of all PhatLoots that are automatically linked to the given Block
     *
     * @param block The given Block
     * @return The LinkedList of PhatLoots
     */
    public static LinkedList<PhatLoot> getAutoLinkedPhatLoots(Block block) {
        LinkedList<PhatLoot> phatLootList = new LinkedList<>();

        if (PhatLootsUtil.isLinkableType(block)) {
            HashMap<String, String> map = types.get(block.getType());
            if (map != null) {
                String world = block.getWorld().getName();
                String pNameList = map.containsKey(world)
                                 ? map.get(world)
                                 : map.get("all");
                if (pNameList != null) {
                    for (String pName : pNameList.split("; ")) {
                        PhatLoot phatLoot = PhatLoots.getPhatLoot(pName);
                        if (phatLoot == null) {
                            PhatLoots.logger.warning("PhatLoot " + pName + " does not exist.");
                            PhatLoots.logger.warning("Please adjust your config or create the PhatLoot");
                        }

                        phatLootList.add(phatLoot);
                    }
                }
            }
        }

        return phatLootList;
    }

    /**
     * Returns a List of all PhatLoots that are explicitly linked to the given Block
     *
     * @param block The given Block
     * @return The LinkedList of PhatLoots
     */
    public static LinkedList<PhatLoot> getExplicitlyLinkedPhatLoots(Block block) {
        LinkedList<PhatLoot> phatLootList = new LinkedList<>();

        if (PhatLootsUtil.isLinkableType(block)) {
            block = PhatLootsUtil.getLeftSide(block);
            if (PhatLootChest.isPhatLootChest(block)) {
                PhatLootChest chest = PhatLootChest.getChest(block);
                for (PhatLoot phatLoot : phatLoots.values()) {
                    if (phatLoot.containsChest(chest)) {
                        phatLootList.add(phatLoot);
                    }
                }
            }
        }

        return phatLootList;
    }

    /**
     * Returns the CommandHandler used to execute the /loot command
     *
     * @return The CommandHandler of PhatLoots
     */
    public static CommandHandler getCommandHandler() {
        return handler;
    }

    /**
     * Retrieves the registered Economy plugin
     *
     * @return true if an Economy plugin has been found
     */
    private boolean setupEconomy() {
        //Return if Vault is not enabled
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider rsp = Bukkit.getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = (Economy) rsp.getProvider();
        return econ != null;
    }

    /**
     * Saves Loot times of each PhatLoot to file
     */
    public static void saveLootTimes() {
        for (PhatLoot phatLoot : getPhatLoots()) {
            //Clean up the loot times before writing to file
            phatLoot.clean(null);
            phatLoot.saveLootTimes();
        }
    }

    /**
     * Reloads PhatLoot data
     */
    public static void rl() {
        rl(null);
    }

    /**
     * Reloads PhatLoot data and settings
     *
     * @param sender The CommandSender reloading the plugin
     */
    public static void rl(CommandSender sender) {
        saveLootTimes();

        phatLoots.clear();
        plugin.reloadConfig();
        load();

        logger.info("PhatLoots reloaded");
        if (sender instanceof Player) {
            sender.sendMessage("§5PhatLoots reloaded");
        }
    }

    /**
     * Does the same as getConfig() but for the given config file.
     *
     * @param file The file to load
     * @return The YamlConfiguration loaded
     */
    private static YamlConfiguration loadConfig(File file) {
        YamlConfiguration fileConfiguration = new YamlConfiguration();
        try {
            if (isValidUTF8(Files.toByteArray(file))) {
                fileConfiguration.loadFromString(Files.toString(file, Charset.forName("UTF-8")));
            } else {
                fileConfiguration.load(file);
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "§4Could not load data from " + file, ex);
        }
        return fileConfiguration;
    }

    /**
     * Checks if the given byte array is UTF-8 encoded.
     *
     * @param bytes The array of bytes to check for validity
     * @return true when validly UTF8 encoded
     */
    private static boolean isValidUTF8(byte[] bytes) {
        try {
            Charset.availableCharsets().get("UTF-8").newDecoder().decode(ByteBuffer.wrap(bytes));
            return true;
        } catch (CharacterCodingException e) {
            return false;
        }
    }

    public static boolean isDebug() {
        return PhatLootsConfig.debug;
    }

    public static void debug(String msg) {
        logger.info("DEBUG: " + msg);
    }
}
