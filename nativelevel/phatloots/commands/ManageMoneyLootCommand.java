package nativelevel.phatloots.commands;

import nativelevel.phatloots.commands.CommandHandler.CodCommand;
import nativelevel.phatloots.loot.Money;
import org.bukkit.command.CommandSender;

/**
 * Executes Player Commands for configuring Loot Money
 *
 * @author Codisimus
 */
public class ManageMoneyLootCommand {

    @CodCommand(
        command = "add",
        subcommand = "money",
        weight = 191.2,
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
        setMoney(sender, true, args);
        return true;
    }

    @CodCommand(
        command = "remove",
        subcommand = "money",
        weight = 196.2,
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
        setMoney(sender, false, args);
        return true;
    }

    /**
     * Generates the Loot Money and sets it on the PhatLoot(s)
     *
     * @param sender The CommandSender who is setting the Money
     * @param add True if the Money should be added to the PhatLoot(s)
     * @param args The arguments of the Loot Money
     */
    private static void setMoney(CommandSender sender, boolean add, String[] args) {
        String phatLoot = null; //The name of the PhatLoot
        double percent = 100; //The chance of receiving the Loot (defaulted to 100)
        String coll = null; //The Collection to add the Loot to
        int lowerBound = 1; //Lower bound of the money range (defaulted to 1)
        int upperBound = 1; //Upper bound of the money range (defaulted to 1)

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
        Money loot = new Money(lowerBound, upperBound);
        loot.setProbability(percent);

        LootCommandUtil.setLoot(sender, phatLoot, add, coll, loot);
    }
}
