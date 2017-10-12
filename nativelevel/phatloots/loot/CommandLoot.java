package nativelevel.phatloots.loot;

import nativelevel.phatloots.PhatLoot;
import nativelevel.phatloots.PhatLoots;
import nativelevel.phatloots.PhatLootsCommandSender;
import nativelevel.phatloots.PhatLootsUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * A CommandLoot is a Command which may be executed from the player or the console
 *
 * @author Codisimus
 */
@SerializableAs("Command")
public class CommandLoot extends Loot {
    private static PhatLootsCommandSender cs = new PhatLootsCommandSender();
    public String command;
    public long delay = 0;
    public boolean fromConsole;
    public boolean tempOP;

    /**
     * Constructs a new CommandLoot for the given command
     *
     * @param command The given command
     */
    public CommandLoot(String command) {
        //The command should not start with '/'
        if (command.startsWith("/")) {
            command = command.substring(1);
        }
        //Check if the command should be run by the player rather than the console
        if (command.startsWith("sudo ")) {
            fromConsole = false;
            tempOP = true;
            //Remove 'sudo ' from the command
            this.command = command.substring(5);
        } else {
            fromConsole = true;
            this.command = command;
        }
    }

    /**
     * Constructs a new CommandLoot from a Configuration Serialized phase
     *
     * @param map The map of data values
     */
    public CommandLoot(Map<String, Object> map) {
        String currentLine = null; //The value that is about to be loaded (used for debugging)
        try {
            probability = (Double) map.get(currentLine = "Probability");
            command = (String) map.get(currentLine = "Command");
            if (map.containsKey(currentLine = "Delay")) {
                delay = ((Number) map.get(currentLine)).longValue();
            }
            fromConsole = (Boolean) map.get(currentLine = "FromConsole");
            tempOP = (Boolean) map.get(currentLine = "TempOP");
        } catch (Exception ex) {
            //Print debug messages
            PhatLoots.logger.severe("Failed to load CommandLoot line: " + currentLine);
            PhatLoots.logger.severe("of PhatLoot: " + (PhatLoot.current == null ? "unknown" : PhatLoot.current));
            PhatLoots.logger.severe("Last successfull load was...");
            PhatLoots.logger.severe("PhatLoot: " + (PhatLoot.last == null ? "unknown" : PhatLoot.last));
            PhatLoots.logger.severe("Loot: " + (Loot.last == null ? "unknown" : Loot.last.toString()));
        }
    }

    /**
     * Adds this command to the LootBundle
     *
     * @param lootBundle The loot that has been rolled for
     * @param lootingBonus The increased chance of getting rarer loots
     */
    @Override
    public void getLoot(LootBundle lootBundle, double lootingBonus) {
        lootBundle.addCommand(this);
    }

    /**
     * Executes the command for the looting player
     *
     * @param player The Player looting or null if no Player is involved
     */
    public void execute(final Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                String cmd  = command;
                if (player == null) {
                    if (!fromConsole || command.contains("<player>") || command.contains("<killer>")) {
                        return;
                    }
                } else {
                    cmd = cmd.replace("<player>", player.getName());
                    if (command.contains("<killer>")) {
                        Player killer = player.getKiller();
                        if (killer == null) {
                            return;
                        } else {
                            cmd = cmd.replace("<killer>", killer.getName());
                        }
                    }
                }
                if (fromConsole) { //From console
                    Bukkit.dispatchCommand(cs, cmd);
                } else if (tempOP) { //From Player as OP
                    //Make the player OP for long enough to execute the command
                    player.setOp(true);
                    Bukkit.dispatchCommand(player, cmd);
                    player.setOp(false);
                } else { //From Player
                    Bukkit.dispatchCommand(player, cmd);
                }
            }
        }.runTaskLater(PhatLoots.plugin, delay);
    }

    /**
     * Returns the information of the CommandLoot in the form of an ItemStack
     *
     * @return An ItemStack representation of the Loot
     */
    @Override
    public ItemStack getInfoStack() {
        //A CommandLoot is represented by a Command Block
        ItemStack infoStack = new ItemStack(Material.COMMAND);

        //Set the display name of the item
        ItemMeta info = Bukkit.getItemFactory().getItemMeta(infoStack.getType());
        info.setDisplayName("§2Command");

        //Add more specific details of the command
        List<String> details = new ArrayList();
        details.add("§4Probability: §6" + probability);
        details.add("§4Command: §6" + command);
        details.add(fromConsole ? "§6From Console" : "§6From Player");
        details.add("§4Delayed: " + PhatLootsUtil.timeToString(delay));
        if (tempOP) {
            details.add("§6Player is temporarily OPed");
        }

        //Construct the ItemStack and return it
        info.setLore(details);
        infoStack.setItemMeta(info);
        return infoStack;
    }

    /**
     * Toggles a Loot setting depending on the type of Click
     *
     * @param click The type of Click (Only SHIFT_LEFT and SHIFT_RIGHT are used)
     * @return true if the Loot InfoStack should be refreshed
     */
    @Override
    public boolean onToggle(ClickType click) {
        switch (click) {
        case SHIFT_LEFT:
            fromConsole = !fromConsole;
            break;
        case SHIFT_RIGHT:
            tempOP = !tempOP;
            break;
        default:
            return false;
        }
        return true;
    }

    /**
     * @return false because this type of Loot has no amount
     */
    @Override
    public boolean modifyAmount(int amount, boolean both) {
        return false;
    }

    /**
     * @return false because this type of Loot has no amount
     */
    @Override
    public boolean resetAmount() {
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("/");
        if (!fromConsole) {
            sb.append("sudo ");
        }
        sb.append("command @ ");
        //Only display the decimal values if the probability is not a whole number
        sb.append(String.valueOf(Math.floor(probability) == probability ? (int) probability : probability));
        sb.append("%");
        if (delay > 0) {
            sb.append("executed ");
            sb.append(PhatLootsUtil.timeToString(delay));
            sb.append(" after looted");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof CommandLoot) {
            CommandLoot loot = (CommandLoot) object;
            return loot.fromConsole == fromConsole
                    && loot.delay == delay
                    && loot.tempOP == tempOP
                    && loot.command.equals(command);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.command);
        hash = 37 * hash + (int) (this.delay ^ (this.delay >>> 32));
        hash = 37 * hash + (this.fromConsole ? 1 : 0);
        hash = 37 * hash + (this.tempOP ? 1 : 0);
        return hash;
    }

    @Override
    public Map<String, Object> serialize() {
        Map map = new TreeMap();
        map.put("Probability", probability);
        map.put("Command", command);
        map.put("Delay", delay);
        map.put("FromConsole", fromConsole);
        map.put("TempOP", tempOP);
        return map;
    }
}
