package nativelevel.phatloots.listeners;

import nativelevel.KoM;
import nativelevel.phatloots.ForgettableInventory;
import nativelevel.phatloots.PhatLoot;
import nativelevel.phatloots.PhatLootChest;
import nativelevel.phatloots.PhatLoots;
import nativelevel.phatloots.PhatLootsAPI;
import nativelevel.phatloots.PhatLootsConfig;
import nativelevel.phatloots.PhatLootsUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

/**
 * Listens for interactions with PhatLootChests
 *
 * @author Codisimus
 */
public class PhatLootsListener implements Listener {
    public static boolean autoBreakOnPunch;

    /**
     * Checks if a Player loots a PhatLootChest
     *
     * @param event The PlayerInteractEvent that occurred
     */
    @EventHandler (ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (!event.hasBlock() || event.getHand() != EquipmentSlot.HAND) {
            return;
        }
        
        KoM.debug("PL EVENT");

        boolean autoSpill = false;
        switch (event.getAction()) {
        case RIGHT_CLICK_BLOCK:
            break;
        case LEFT_CLICK_BLOCK:
            if (event.getClickedBlock().getType() == Material.DISPENSER) {
                break;
            } else if (autoBreakOnPunch) {
                autoSpill = true;
                break;
            }
            return;
        default:
            return;
        }

        Player player = event.getPlayer();
        boolean looted = PhatLootsAPI.loot(event.getClickedBlock(), player, autoSpill);

        if (looted) {
            event.setCancelled(true);
        }
    }

    /**
     * Listens for a Player closing a PhatLootChest
     *
     * @param event The InventoryCloseEvent that occurred
     */
    @EventHandler (ignoreCancelled = true)
    public void onPlayerCloseChest(InventoryCloseEvent event) {
        HumanEntity human = event.getPlayer();
        if (human instanceof Player) {
            Player player = (Player) human;
            if (PhatLootChest.openPhatLootChests.containsKey(player.getUniqueId())) {
                PhatLootChest chest = PhatLootChest.openPhatLootChests.get(player.getUniqueId());
                boolean global = ForgettableInventory.has("global@" + chest.toString());
                chest.closeInventory(player, event.getInventory(), global);
            }
        }
    }

    /**
     * Prevents non-admins from breaking PhatLootsChests
     *
     * @param event The BlockBreakEvent that occurred
     */
    @EventHandler (ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        //Return if the Material of the Block is not a linkable type
        Block block = event.getBlock();
        if (!PhatLootsUtil.isLinkableType(block)) {
            return;
        }

        //Return if the Block is not a PhatLootChest
        if (!PhatLootChest.isPhatLootChest(block)) {
            return;
        }

        //Cancel if the Block was not broken by a Player
        Player player = event.getPlayer();
        if (player == null) {
            event.setCancelled(true);
            return;
        }

        //Cancel if the Block was not broken by an Admin
        if (!player.hasPermission("phatloots.admin")) {
            player.sendMessage(PhatLootsConfig.permission);
            event.setCancelled(true);
            return;
        }

        //Unlink the broken Block
        for (PhatLoot phatLoot : PhatLoots.getPhatLoots(block)) {
            phatLoot.removeChest(block);
            player.sendMessage("§5Broken §6" + block.getType().toString() + "§5 has been unlinked from PhatLoot §6" + phatLoot.name);
            phatLoot.saveChests();
        }
    }
}
