package nativelevel.phatloots.listeners;

import nativelevel.phatloots.PhatLoot;
import nativelevel.phatloots.PhatLootChest;
import nativelevel.phatloots.PhatLoots;
import nativelevel.phatloots.PhatLootsUtil;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;

/**
 * Listens for redstone activating PhatLoot Dispensers
 *
 * @author Codisimus
 */
public class DispenserListener implements Listener {
    /**
     * Checks if a PhatLoot Dispenser is powered
     *
     * @param event The BlockPhysicsEvent that occurred
     */
    @EventHandler (ignoreCancelled = true)
    public void onBlockPowered(BlockPhysicsEvent event) {
        //Check if the Block is a Dispenser/Dropper
        Block block = event.getBlock();
        switch (block.getType()) {
        case DISPENSER: break;
        case DROPPER: break;
        default: return;
        }

        //We don't care if a block loses power
        if (block.getBlockPower() == 0) {
            return;
        }

        //Return if the Dispenser is not a PhatLootChest
        if (!PhatLootChest.isPhatLootChest(block)) {
            return;
        }

        //Return if there are not any player that are close enough
        Player player = PhatLootsUtil.getNearestPlayer(block.getLocation());
        if (player == null) {
            return;
        }

        //Roll for linked loot
        PhatLootChest plChest = PhatLootChest.getChest(block);
        for (PhatLoot phatLoot : PhatLoots.getPhatLoots(block, player)) {
            if (PhatLootsUtil.canLoot(player, phatLoot)) {
                long falta = phatLoot.getTimeRemaining(plChest);
                if(falta > 0) {
                    String tempo = phatLoot.timeToString(falta);
                    for(Player p : Bukkit.getOnlinePlayers()) {
                        if(p.getWorld().getName().equalsIgnoreCase(block.getWorld().getName())) {
                            if(p.getLocation().distance(block.getLocation()) <= 20) {
                                p.sendMessage(ChatColor.AQUA+"Cooldown: "+tempo);
                            }
                        }
                    }
                }
                phatLoot.rollForChestLoot(player, plChest);
            }
        }
    }
}
