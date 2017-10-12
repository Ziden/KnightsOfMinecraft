package nativelevel.phatloots.commands;

import nativelevel.phatloots.PhatLootsConfig;
import nativelevel.phatloots.commands.CommandHandler.CodCommand;
import java.util.Map;
import nativelevel.phatloots.loot.CommandLoot;
import nativelevel.phatloots.loot.Experience;
import nativelevel.phatloots.loot.Item;
import nativelevel.phatloots.loot.Loot;
import nativelevel.phatloots.loot.LootCollection;
import nativelevel.phatloots.loot.Message;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Executes Player Commands for configuring Loot
 *
 * @author Codisimus
 */
public class ManageLootCommand {
    @CodCommand(
        command = "add",
        subcommand = "hand",
        weight = 190,
        aliases = {"+"},
        usage = {
            "§e     PhatLoots Manage Loot Help Page:",
            "§5A Parameter starts with the 1 character §2id",
            "§2p§f: §5The Name of the PhatLoot ex. §6pEpic",
            "§bIf PhatLoot is not specified then all PhatLoots linked to the target Block will be affected",
            "§2%§f: §5The chance of looting ex. §6%50 §5or §6%0.1 §5(default: §6100§5)",
            "§2c§f: §5The name of the collection to add the loot to ex. §6cFood",
            "§2#§f: §5The amount to be looted ex. §6#10 §5or §6#1-64",
            "§2d§f: §5The data/durability value of the item ex. §6d5",
            "§2t§f: §5Tier the Item (tiers.yml) ex. §6t",
            "§2l§f: §5Generate Lore for the Item (lores.yml) ex. §l",
            "§2e§f: §5The item enchantment ex. §6earrow_fire §5or §6eauto",
            "§bEnchantment levels can be added. ex. §6arrow_fire(2)",
            "§2<command> [Parameter1] [Parameter2]...",
            "§bex. §6<command> #1-16 %32",
            "§bTutorial Video:",
            "§1§nwww.youtu.be/7ViP0dEq7nk"
        },
        permission = "phatloots.manage"
    )
    public boolean addHand(Player player, String[] args) {
        setItem(player, true, player.getInventory().getItemInMainHand().clone(), args);
        return true;
    }

    @CodCommand(
        command = "add",
        subcommand = "coll",
        weight = 191,
        aliases = {"+"},
        usage = {
            "§e     PhatLoots Manage Loot Help Page:",
            "§5A Parameter starts with the 1 character §2id",
            "§2p§f: §5The Name of the PhatLoot ex. §6pEpic",
            "§bIf PhatLoot is not specified then all PhatLoots linked to the target Block will be affected",
            "§2%§f: §5The chance of looting ex. §6%50 §5or §6%0.1 §5(default: §6100§5)",
            "§2c§f: §5The name of the collection to add the loot to ex. §6cFood",
            "§2#§f: §5The amount to be looted ex. §6#10 §5or §6#1-64",
            "§bUse §6#0 §bif you want each Loot in a collection to be rolled for individually",
            "§2<command> <Name> [Parameter1] [Parameter2]...",
            "§bex. §6<command> Weapon %25",
            "§bTutorial Video:",
            "§1§nwww.youtu.be/7ViP0dEq7nk"
        },
        permission = "phatloots.manage"
    )
    public boolean addColl(CommandSender sender, String name, String[] args) {
        setColl(sender, true, name, args);
        return true;
    }

    @CodCommand(
        command = "add",
        subcommand = "exp",
        weight = 191.1,
        aliases = {"+"},
        usage = {
            "§e     PhatLoots Manage Loot Help Page:",
            "§5A Parameter starts with the 1 character §2id",
            "§2p§f: §5The Name of the PhatLoot ex. §6pEpic",
            "§bIf PhatLoot is not specified then all PhatLoots linked to the target Block will be affected",
            "§2%§f: §5The chance of looting ex. §6%50 §5or §6%0.1 §5(default: §6100§5)",
            "§2c§f: §5The name of the collection to add the loot to ex. §6cFood",
            "§2#§f: §5The amount to be looted ex. §6#10 §5or §6#1-100",
            "§2<command> [Parameter1] [Parameter2]...",
            "§bex. §6<command> #20-80 %25",
            "§bTutorial Video:",
            "§1§nNone yet, Submit yours!"
        },
        permission = "phatloots.exp"
    )
    public boolean addExp(CommandSender sender, String[] args) {
        setExp(sender, true, args);
        return true;
    }

    @CodCommand(
        command = "add",
        subcommand = "cmd",
        weight = 192,
        aliases = {"+"},
        usage = {
            "§e     PhatLoots Manage Loot Help Page:",
            "§5A Parameter starts with the 1 character §2id",
            "§2p§f: §5The Name of the PhatLoot ex. §6pEpic",
            "§bIf PhatLoot is not specified then all PhatLoots linked to the target Block will be affected",
            "§2%§f: §5The chance of looting ex. §6%50 §5or §6%0.1 §5(default: §6100§5)",
            "§2c§f: §5The name of the collection to add the loot to ex. §6cFood",
            "§2<command> [Parameter1] [Parameter2]... /<Command>",
            "§bex. §6<command> %50 /broadcast &6<player>&r found the treasure!",
            "§bTutorial Video:",
            "§1§nwww.youtu.be/7ViP0dEq7nk"
        },
        permission = "phatloots.manage"
    )
    public boolean addCmd(CommandSender sender, String[] args) {
        setCmd(sender, true, args);
        return true;
    }

    @CodCommand(
        command = "add",
        subcommand = "msg",
        weight = 193,
        aliases = {"+"},
        usage = {
            "§e     PhatLoots Manage Loot Help Page:",
            "§5A Parameter starts with the 1 character §2id",
            "§2p§f: §5The Name of the PhatLoot ex. §6pEpic",
            "§bIf PhatLoot is not specified then all PhatLoots linked to the target Block will be affected",
            "§2%§f: §5The chance of looting ex. §6%50 §5or §6%0.1 §5(default: §6100§5)",
            "§2c§f: §5The name of the collection to add the loot to ex. §6cFood",
            "§2<command> [Parameter1] [Parameter2]... -<Message>",
            "§bex. §6<command> %50 -Congrats &6<player>&r, You found the treasure!",
            "§bTutorial Video:",
            "§1§nwww.youtu.be/7ViP0dEq7nk"
        },
        permission = "phatloots.manage"
    )
    public boolean addMsg(CommandSender sender, String[] args) {
        setMsg(sender, true, args);
        return true;
    }

    @CodCommand(
        command = "add",
        weight = 194,
        aliases = {"+"},
        usage = {
            "§e     PhatLoots Manage Loot Help Page:",
            "§5A Parameter starts with the 1 character §2id",
            "§2p§f: §5The Name of the PhatLoot ex. §6pEpic",
            "§bIf PhatLoot is not specified then all PhatLoots linked to the target Block will be affected",
            "§2%§f: §5The chance of looting ex. §6%50 §5or §6%0.1 §5(default: §6100§5)",
            "§2c§f: §5The name of the collection to add the loot to ex. §6cFood",
            "§2#§f: §5The amount to be looted ex. §6#10 §5or §6#1-64",
            "§bUse §6#0 §bif you want each Loot in a collection to be rolled for individually",
            "§2d§f: §5The data/durability value of the item ex. §6d5",
            "§2t§f: §5Tier the Item (tiers.yml) ex. §6t",
            "§2l§f: §5Generate Lore for the Item (lores.yml) ex. §l",
            "§2e§f: §5The item enchantment ex. §6earrow_fire §5or §6eauto",
            "§bEnchantment levels can be added. ex. §6arrow_fire(2)",
            "§2<command> <Item|ID|hand> [Parameter1] [Parameter2]...",
            "§bex. §6<command> hand #1-16 %32",
            "§bex. §6<command> diamond_sword efire_aspect(2) edamage_all %75 cWeapon",
            "§2<command> coll <Name> [Parameter1] [Parameter2]...",
            "§bex. §6<command> coll Weapon %25",
            "§2<command> cmd [Parameter1] [Parameter2]... /<Command>",
            "§2<command> msg [Parameter1] [Parameter2]... -<Message>",
            "§bTutorial Video:",
            "§1§nwww.youtu.be/7ViP0dEq7nk"
        },
        permission = "phatloots.manage"
    )
    public boolean add(CommandSender sender, Material mat, String[] args) {
        setItem(sender, true, new ItemStack(mat), args);
        return true;
    }

    @CodCommand(
        command = "remove",
        subcommand = "hand",
        weight = 195,
        aliases = {"+"},
        usage = {
            "§e     PhatLoots Manage Loot Help Page:",
            "§5A Parameter starts with the 1 character §2id",
            "§2p§f: §5The Name of the PhatLoot ex. §6pEpic",
            "§bIf PhatLoot is not specified then all PhatLoots linked to the target Block will be affected",
            "§2%§f: §5The chance of looting ex. §6%50 §5or §6%0.1 §5(default: §6100§5)",
            "§2c§f: §5The name of the collection to add the loot to ex. §6cFood",
            "§2#§f: §5The amount to be looted ex. §6#10 §5or §6#1-64",
            "§2d§f: §5The data/durability value of the item ex. §6d5",
            "§2t§f: §5Tier the Item (tiers.yml) ex. §6t",
            "§2l§f: §5Generate Lore for the Item (lores.yml) ex. §l",
            "§2e§f: §5The item enchantment ex. §6earrow_fire §5or §6eauto",
            "§bEnchantment levels can be added. ex. §6arrow_fire(2)",
            "§2<command> [Parameter1] [Parameter2]...",
            "§bex. §6<command> #1-16 %32",
            "§bTutorial Video:",
            "§1§nwww.youtu.be/7ViP0dEq7nk"
        },
        permission = "phatloots.manage"
    )
    public boolean removeHand(Player player, String[] args) {
        setItem(player, false, player.getInventory().getItemInMainHand(), args);
        return true;
    }

    @CodCommand(
        command = "remove",
        subcommand = "coll",
        weight = 196,
        aliases = {"+"},
        usage = {
            "§e     PhatLoots Manage Loot Help Page:",
            "§5A Parameter starts with the 1 character §2id",
            "§2p§f: §5The Name of the PhatLoot ex. §6pEpic",
            "§bIf PhatLoot is not specified then all PhatLoots linked to the target Block will be affected",
            "§2%§f: §5The chance of looting ex. §6%50 §5or §6%0.1 §5(default: §6100§5)",
            "§2c§f: §5The name of the collection to add the loot to ex. §6cFood",
            "§2#§f: §5The amount to be looted ex. §6#10 §5or §6#1-64",
            "§bUse §6#0 §bif you want each Loot in a collection to be rolled for individually",
            "§2<command> <Name> [Parameter1] [Parameter2]...",
            "§bex. §6<command> Weapon %25",
            "§bTutorial Video:",
            "§1§nwww.youtu.be/7ViP0dEq7nk"
        },
        permission = "phatloots.manage"
    )
    public boolean removeColl(CommandSender sender, String name, String[] args) {
        setColl(sender, false, name, args);
        return true;
    }

    @CodCommand(
        command = "remove",
        subcommand = "exp",
        weight = 196.1,
        aliases = {"-"},
        usage = {
            "§e     PhatLoots Manage Loot Help Page:",
            "§5A Parameter starts with the 1 character §2id",
            "§2p§f: §5The Name of the PhatLoot ex. §6pEpic",
            "§bIf PhatLoot is not specified then all PhatLoots linked to the target Block will be affected",
            "§2%§f: §5The chance of looting ex. §6%50 §5or §6%0.1 §5(default: §6100§5)",
            "§2c§f: §5The name of the collection to add the loot to ex. §6cFood",
            "§2#§f: §5The amount to be looted ex. §6#10 §5or §6#1-100",
            "§2<command> [Parameter1] [Parameter2]...",
            "§bex. §6<command> #20-80 %25",
            "§bTutorial Video:",
            "§1§nNone yet, Submit yours!"
        },
        permission = "phatloots.exp"
    )
    public boolean removeExp(CommandSender sender, String[] args) {
        setExp(sender, false, args);
        return true;
    }

    @CodCommand(
        command = "remove",
        subcommand = "cmd",
        weight = 197,
        aliases = {"-"},
        usage = {
            "§e     PhatLoots Manage Loot Help Page:",
            "§5A Parameter starts with the 1 character §2id",
            "§2p§f: §5The Name of the PhatLoot ex. §6pEpic",
            "§bIf PhatLoot is not specified then all PhatLoots linked to the target Block will be affected",
            "§2%§f: §5The chance of looting ex. §6%50 §5or §6%0.1 §5(default: §6100§5)",
            "§2c§f: §5The name of the collection to add the loot to ex. §6cFood",
            "§2<command> [Parameter1] [Parameter2]... /<Command>",
            "§bex. §6<command> %50 /broadcast &6<player>&r found the treasure!",
            "§bTutorial Video:",
            "§1§nwww.youtu.be/7ViP0dEq7nk"
        },
        permission = "phatloots.manage"
    )
    public boolean removeCmd(CommandSender sender, String[] args) {
        setCmd(sender, false, args);
        return true;
    }

    @CodCommand(
        command = "remove",
        subcommand = "msg",
        weight = 198,
        aliases = {"-"},
        usage = {
            "§e     PhatLoots Manage Loot Help Page:",
            "§5A Parameter starts with the 1 character §2id",
            "§2p§f: §5The Name of the PhatLoot ex. §6pEpic",
            "§bIf PhatLoot is not specified then all PhatLoots linked to the target Block will be affected",
            "§2%§f: §5The chance of looting ex. §6%50 §5or §6%0.1 §5(default: §6100§5)",
            "§2c§f: §5The name of the collection to add the loot to ex. §6cFood",
            "§2<command> [Parameter1] [Parameter2]... -<Message>",
            "§bex. §6<command> %50 -Congrats &6<player>&r, You found the treasure!",
            "§bTutorial Video:",
            "§1§nwww.youtu.be/7ViP0dEq7nk"
        },
        permission = "phatloots.manage"
    )
    public boolean removeMsg(CommandSender sender, String[] args) {
        setMsg(sender, false, args);
        return true;
    }

    @CodCommand(
        command = "remove",
        weight = 199,
        aliases = {"-"},
        usage = {
            "§e     PhatLoots Manage Loot Help Page:",
            "§5A Parameter starts with the 1 character §2id",
            "§2p§f: §5The Name of the PhatLoot ex. §6pEpic",
            "§bIf PhatLoot is not specified then all PhatLoots linked to the target Block will be affected",
            "§2%§f: §5The chance of looting ex. §6%50 §5or §6%0.1 §5(default: §6100§5)",
            "§2c§f: §5The name of the collection to add the loot to ex. §6cFood",
            "§2#§f: §5The amount to be looted ex. §6#10 §5or §6#1-64",
            "§bUse §6#0 §bif you want each Loot in a collection to be rolled for individually",
            "§2d§f: §5The data/durability value of the item ex. §6d5",
            "§2t§f: §5Tier the Item (tiers.yml) ex. §6t",
            "§2l§f: §5Generate Lore for the Item (lores.yml) ex. §l",
            "§2e§f: §5The item enchantment ex. §6earrow_fire §5or §6eauto",
            "§bEnchantment levels can be added. ex. §6arrow_fire(2)",
            "§2<command> <Item|ID|hand> [Parameter1] [Parameter2]...",
            "§bex. §6<command> hand #1-16 %32",
            "§bex. §6<command> diamond_sword efire_aspect(2) edamage_all %75 cWeapon",
            "§2<command> coll <Name> [Parameter1] [Parameter2]...",
            "§bex. §6<command> coll Weapon %25",
            "§2<command> cmd [Parameter1] [Parameter2]... /<Command>",
            "§2<command> msg [Parameter1] [Parameter2]... -<Message>",
            "§bTutorial Video:",
            "§1§nwww.youtu.be/7ViP0dEq7nk"
        },
        permission = "phatloots.manage"
    )
    public boolean remove(CommandSender sender, Material mat, String[] args) {
        setItem(sender, false, new ItemStack(mat), args);
        return true;
    }

    /**
     * Generates the Loot Item and sets it on the PhatLoot(s)
     *
     * @param sender The CommandSender who is setting the Item
     * @param add True if the Item should be added to the PhatLoot(s)
     * @param item The ItemStack which is the base of the Loot Item
     * @param args The arguments of the Loot Item
     */
    private static void setItem(CommandSender sender, boolean add, ItemStack item, String[] args) {
        String phatLoot = null; //The name of the PhatLoot
        double percent = 100; //The chance of receiving the Loot (defaulted to 100)
        String coll = null; //The Collection to add the Loot to
        int lowerBound = 1; //Stack size of the Loot item (defaulted to 1)
        int upperBound = 1; //Amount to possibly increase the Stack size of the Loot item (defaulted to 1)
        short durabilityLowerBound = -1; //Durability/damage of the Loot item (defaulted to -1 to indicate full)
        short durabilityUpperBound = -1; //Durability/damage of the Loot item (defaulted to -1 to indicate full)
        boolean autoEnchant = false; //Whether or not the Loot Item should be automatically enchanted at time of Looting
        boolean tiered = false; //Whether or not the Loot Item should be Tiered
        boolean generateName = false; //Whether or not the Loot Item should have a generated name

        //Check each parameter
        int i = 0;
        while (i < args.length) {
            char c = args[i].charAt(0);
            String s = args[i].substring(1);
            switch (c) {
            case 'p': //PhatLoot Name
                phatLoot = s;
                break;

            case '%': //Probability
                percent = LootCommandUtil.getPercent(sender, s);
                if (percent == -1) {
                    sender.sendMessage("§6" + s + "§4 is not a percent");
                    return;
                }
                break;

            case 'c': //Collection Name
                coll = s;
                break;

            case '#': //Amount
                lowerBound = LootCommandUtil.getLowerBound(s);
                upperBound = LootCommandUtil.getUpperBound(s);
                if (lowerBound == -1 || upperBound == -1) {
                    sender.sendMessage("§6" + s + "§4 is not a valid number or range");
                    return;
                }
                item.setAmount(lowerBound);
                break;

            case 'e': //Enchantment
                if (s.equalsIgnoreCase("auto")) {
                    autoEnchant = true;
                } else {
                    Map<Enchantment, Integer> enchantments = LootCommandUtil.getEnchantments(s);
                    if (enchantments == null) {
                        sender.sendMessage("§6" + s + "§4 is not a valid enchantment");
                        return;
                    }
                    item.addUnsafeEnchantments(enchantments);
                }
                break;

            case 'd': //Durability
                durabilityLowerBound = (short) LootCommandUtil.getLowerBound(s);
                durabilityUpperBound = (short) LootCommandUtil.getUpperBound(s);
                if (durabilityLowerBound == -1 || durabilityUpperBound == -1) {
                    sender.sendMessage("§6" + s + "§4 is not a valid number or range");
                    return;
                }
                break;

            case 't': //Tiered
                tiered = true;
                break;

            case 'l': //Automatic Lore
                generateName = true;
                break;

            default: //Invalid Parameter
                sender.sendMessage("§6" + c + "§4 is not a valid parameter ID");
                return;
            }

            i++;
        }

        //Construct the Loot
        Item loot = new Item(item, upperBound - lowerBound);
        loot.setDurability(durabilityLowerBound);
        loot.durabilityBonus = durabilityUpperBound - durabilityLowerBound;
        if (autoEnchant) {
            loot.autoEnchant = true;
        }
        if (tiered) {
            loot.tieredName = true;
        }
        if (generateName) {
            loot.generateName = true;
        }
        loot.setProbability(percent);

        LootCommandUtil.setLoot(sender, phatLoot, add, coll, loot);
    }

    /**
     * Generates the Loot Collection and sets it on the PhatLoot(s)
     *
     * @param sender The CommandSender who is setting the Collection
     * @param add True if the Collection should be added to the PhatLoot(s)
     * @param name The name of the Collection
     * @param args The arguments of the Loot Collection
     */
    private static void setColl(CommandSender sender, boolean add, String name, String[] args) {
        String phatLoot = null; //The name of the PhatLoot
        double percent = 100; //The chance of receiving the Loot (defaulted to 100)
        String coll = null; //The Collection to add the Loot to
        int lowerBound = PhatLootsConfig.defaultLowerNumberOfLoots;
        int upperBound = PhatLootsConfig.defaultUpperNumberOfLoots;

        //Check each parameter
        int i = 0;
        while (i < args.length) {
            char c = args[i].charAt(0);
            String s = args[i].substring(1);
            switch (c) {
            case 'p': //PhatLoot Name
                phatLoot = s;
                break;

            case '%': //Probability
                percent = LootCommandUtil.getPercent(sender, s);
                if (percent == -1) {
                    sender.sendMessage("§6" + s + "§4 is not a percent");
                    return;
                }
                break;

            case 'c': //Collection Name
                coll = s;
                break;

            case '#': //Amount
                lowerBound = LootCommandUtil.getLowerBound(s);
                upperBound = LootCommandUtil.getUpperBound(s);
                if (lowerBound == -1 || upperBound == -1) {
                    sender.sendMessage("§6" + s + "§4 is not a valid number or range");
                    return;
                }
                break;

            default: //Invalid Parameter
                sender.sendMessage("§6" + c + "§4 is not a valid parameter ID");
                return;
            }

            i++;
        }

        //Construct the Loot
        Loot loot = new LootCollection(name, lowerBound, upperBound);
        loot.setProbability(percent);

        LootCommandUtil.setLoot(sender, phatLoot, add, coll, loot);
    }

    /**
     * Generates the Loot Experience and sets it on the PhatLoot(s)
     *
     * @param sender The CommandSender who is setting the Experience
     * @param add True if the Experience should be added to the PhatLoot(s)
     * @param args The arguments of the Loot Experience
     */
    private static void setExp(CommandSender sender, boolean add, String[] args) {
        String phatLoot = null; //The name of the PhatLoot
        double percent = 100; //The chance of receiving the Loot (defaulted to 100)
        String coll = null; //The Collection to add the Loot to
        int lowerBound = 1; //Lower bound of the experience range (defaulted to 1)
        int upperBound = 1; //Upper bound of the experience range (defaulted to 1)

        //Check each parameter
        int i = 0;
        while (i < args.length) {
            char c = args[i].charAt(0);
            String s = args[i].substring(1);
            switch (c) {
            case 'p': //PhatLoot Name
                phatLoot = s;
                break;

            case '%': //Probability
                percent = LootCommandUtil.getPercent(sender, s);
                if (percent == -1) {
                    sender.sendMessage("§6" + s + "§4 is not a percent");
                    return;
                }
                break;

            case 'c': //Collection Name
                coll = s;
                break;

            case '#': //Amount
                lowerBound = LootCommandUtil.getLowerBound(s);
                upperBound = LootCommandUtil.getUpperBound(s);
                if (lowerBound == -1 || upperBound == -1) {
                    sender.sendMessage("§6" + s + "§4 is not a valid number or range");
                    return;
                }
                break;

            default: //Invalid Parameter
                sender.sendMessage("§6" + c + "§4 is not a valid parameter ID");
                return;
            }

            i++;
        }

        //Construct the Loot
        Experience loot = new Experience(lowerBound, upperBound);
        loot.setProbability(percent);

        LootCommandUtil.setLoot(sender, phatLoot, add, coll, loot);
    }

    /**
     * Generates the Loot Command and sets it on the PhatLoot(s)
     *
     * @param sender The CommandSender who is setting the Command
     * @param add True if the Command should be added to the PhatLoot(s)
     * @param args The arguments of the Loot Command
     */
    private static void setCmd(CommandSender sender, boolean add, String[] args) {
        String cmd = null; //The command to be added/removed
        String phatLoot = null; //The name of the PhatLoot
        double percent = 100; //The chance of receiving the Loot (defaulted to 100)
        String coll = null; //The Collection to add the Loot to

        //Check each parameter
        int i = 0;
        while (i < args.length) {
            char c = args[i].charAt(0);
            String s = args[i].substring(1);
            switch (c) {
            case 'p': //PhatLoot Name
                phatLoot = s;
                break;

            case '%': //Probability
                percent = LootCommandUtil.getPercent(sender, s);
                if (percent == -1) {
                    sender.sendMessage("§6" + s + "§4 is not a percent");
                    return;
                }
                break;

            case 'c': //Collection Name
                coll = s;
                break;

            case '/': //Command
                cmd = args[i];
                i++;
                while (i < args.length) {
                    cmd += " " + args[i];
                    i++;
                }
                break;

            default: //Invalid Parameter
                sender.sendMessage("§6" + c + "§4 is not a valid parameter ID");
                return;
            }

            i++;
        }

        //Construct the Loot
        Loot loot = new CommandLoot(cmd);
        loot.setProbability(percent);

        LootCommandUtil.setLoot(sender, phatLoot, add, coll, loot);
    }

    /**
     * Generates the Loot Message and sets it on the PhatLoot(s)
     *
     * @param sender The CommandSender who is setting the Message
     * @param add True if the Message should be added to the PhatLoot(s)
     * @param args The arguments of the Loot Message
     */
    private static void setMsg(CommandSender sender, boolean add, String[] args) {
        String msg = null; //The message to be added/removed
        String phatLoot = null; //The name of the PhatLoot
        double percent = 100; //The chance of receiving the Loot (defaulted to 100)
        String coll = null; //The Collection to add the Loot to

        //Check each parameter
        int i = 0;
        while (i < args.length) {
            char c = args[i].charAt(0);
            String s = args[i].substring(1);
            switch (c) {
            case 'p': //PhatLoot Name
                phatLoot = s;
                break;

            case '%': //Probability
                percent = LootCommandUtil.getPercent(sender, s);
                if (percent == -1) {
                    sender.sendMessage("§6" + s + "§4 is not a percent");
                    return;
                }
                break;

            case 'c': //Collection Name
                coll = s;
                break;

            case '-': //Message
                msg = args[i];
                i++;
                while (i < args.length) {
                    msg += " " + args[i];
                    i++;
                }
                break;

            default: //Invalid Parameter
                sender.sendMessage("§6" + c + "§4 is not a valid parameter ID");
                return;
            }

            i++;
        }

        //Construct the Loot
        Loot loot = new Message(msg);
        loot.setProbability(percent);

        LootCommandUtil.setLoot(sender, phatLoot, add, coll, loot);
    }
}
