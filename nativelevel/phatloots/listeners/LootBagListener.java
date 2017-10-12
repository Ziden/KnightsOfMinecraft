package nativelevel.phatloots.listeners;

import nativelevel.phatloots.PhatLoot;
import nativelevel.phatloots.PhatLoots;
import nativelevel.phatloots.PhatLootsConfig;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Listens for interactions with Loot bags
 *
 * @author Codisimus
 */
public class LootBagListener implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        //Open the backpack when the player clicks while holding it
        switch (event.getAction()) {
        case RIGHT_CLICK_AIR:
        case RIGHT_CLICK_BLOCK:
            //Verify that the item has lore
            if (event.hasItem()) {
                ItemStack hand = event.getItem();
                if (hand.hasItemMeta()) {
                    ItemMeta meta = hand.getItemMeta();
                    if (meta.hasLore()) {
                        //Scan the lore for linked PhatLoots
                        List<String> lore = meta.getLore();
                        for (String line : lore) {
                            if (line.startsWith(PhatLootsConfig.lootBagKey)) {
                                PhatLoot phatLoot = PhatLoots.getPhatLoot(line.substring(PhatLootsConfig.lootBagKey.length()));
                                if (phatLoot != null) { //Valid PhatLoot
                                    event.setCancelled(true);
                                    //Remove the bag from the player's inventory
                                    if (hand.getAmount() > 1) {
                                        hand.setAmount(hand.getAmount() - 1);
                                    } else {
                                        PlayerInventory inv = event.getPlayer().getInventory();
                                        if (inv.getItemInMainHand().equals(hand)) {
                                            inv.setItemInMainHand(new ItemStack(Material.AIR));
                                        } else if (inv.getItemInOffHand().equals(hand)) {
                                            inv.setItemInOffHand(new ItemStack(Material.AIR));
                                        } else {
                                            PhatLoots.logger.warning("Player attempted to use LootBag with an unexpected hand.");
                                            return;
                                        }
                                    }
                                    //Give loot to the player
                                    phatLoot.rollForLoot(event.getPlayer());
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
