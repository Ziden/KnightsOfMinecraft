package nativelevel.sisteminhas;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 *
 * @author Ziden
 */
public class DungeonDarkness extends KomSystem {

    public void onEnable() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, verEscuro, 25, 25);
    }
    
    Runnable verEscuro = new Runnable() {
        public void run() {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if(p.getGameMode()==GameMode.CREATIVE)
                    continue;
                if (p.getWorld().getName().equalsIgnoreCase("dungeon")) {
                    if (p.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
                        if (p.hasPotionEffect(PotionEffectType.BLINDNESS)) {
                            p.removePotionEffect(PotionEffectType.BLINDNESS);
                        }
                        continue;
                    }
                    Block local = p.getLocation().getBlock();
                    if(local.getType() != Material.AIR)
                        local = local.getRelative(BlockFace.UP);
                    if ((local.getLightFromBlocks() + local.getLightFromSky()) < Dungeon.LUZ_DO_ESCURO) {
                        if (!p.hasPotionEffect(PotionEffectType.BLINDNESS)) {
                            p.sendMessage(ChatColor.RED + "A escuridão cobre seus olhos. Uma tocha poderia ser útil.");
                        }
                        p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 99999, 1));
                    } else {
                        if (p.hasPotionEffect(PotionEffectType.BLINDNESS)) {
                            p.sendMessage(ChatColor.GREEN + "Você consegue ver novamente.");
                            p.removePotionEffect(PotionEffectType.BLINDNESS);
                        }
                    }
                }
            }
        }
    };
    
    
}
