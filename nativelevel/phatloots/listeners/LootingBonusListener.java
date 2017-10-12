package nativelevel.phatloots.listeners;

import nativelevel.phatloots.PhatLoots;
import nativelevel.phatloots.events.PreMobDropLootEvent;
import nativelevel.phatloots.events.PrePlayerLootEvent;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

/**
 * Applies looting bonuses based on Player permissions
 *
 * @author Codisimus
 */
public class LootingBonusListener implements Listener {
    private static final String LOOTING_BONUS_PERM_PREFIX = "phatloots.bonus.";
    private List<Double> lootingBonusAmounts;

    /**
     * Bulk adds the given looting bonus amounts
     *
     * @param bonusAmounts The List of looting bonus amounts
     */
    public void setLootingBonusAmounts(List<Double> bonusAmounts) {
        lootingBonusAmounts = bonusAmounts;
        for (double amount : lootingBonusAmounts) {
            createLootBonusPermission(amount);
        }
    }

    /**
     * Adds the specified amount as a permission based looting bonus
     *
     * @param amount The double value to add
     */
    public void addLootingBonusAmount(double amount) {
        lootingBonusAmounts.add(amount);
        createLootBonusPermission(amount);
    }

    /**
     * Creates and registers a new Permission for the given amount
     *
     * @param amount The looting bonus double value
     * @return The newly created Permission
     */
    private static Permission createLootBonusPermission(double amount) {
        Permission perm = new Permission(getPermissionNode(amount));
        perm.setDefault(PermissionDefault.FALSE);
        perm.setDescription("Increases each loot roll of the holder by " + amount);
        Bukkit.getPluginManager().addPermission(perm);
        if (PhatLoots.isDebug()) {
            PhatLoots.debug("Created new Permission '" + perm.getName() + "'");
        }
        return perm;
    }

    /**
     * Gives Players looting bonuses
     *
     * @param event The PreMobDropLootEvent that occurred
     */
    @EventHandler (ignoreCancelled = true)
    public void onPreMobDropLoot(PreMobDropLootEvent event) {
        Player player = event.getKiller();
        if (player != null) {
            double lootingBonus = event.getLootingBonus();
            lootingBonus += getLootingBonusFromPerms(player);
            event.setLootingBonus(lootingBonus);
        }
    }

    /**
     * Gives Players looting bonuses
     *
     * @param event The PrePlayerLootEvent that occurred
     */
    @EventHandler (ignoreCancelled = true)
    public void onPrePlayerLoot(PrePlayerLootEvent event) {
        Player player = event.getLooter();
        double lootingBonus = event.getLootingBonus();
        lootingBonus += getLootingBonusFromPerms(player);
        event.setLootingBonus(lootingBonus);
    }

    /**
     * Calculates the looting bonus for the given Player based on permissions
     *
     * @param player The Player who may or may not have bonus permission nodes
     * @return The accumulative double value of all permission looting bonuses
     */
    public double getLootingBonusFromPerms(Player player) {
        double lootingBonus = 0;
        for (double amount : lootingBonusAmounts) {
            if (player.hasPermission(getPermissionNode(amount))) {
                lootingBonus += amount;
            }
        }
        return lootingBonus;
    }

    private static String getPermissionNode(double d) {
        String s = d == (int) d ? Integer.toString((int) d) : Double.toString(d);
        return LOOTING_BONUS_PERM_PREFIX + s;
    }
}
