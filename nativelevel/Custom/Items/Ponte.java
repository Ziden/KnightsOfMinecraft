/*

 ╭╮╭━╮╱╱╭━╮╭━╮
 ┃┃┃╭╯╱╱┃┃╰╯┃┃
 ┃╰╯╯╭━━┫╭╮╭╮┃
 ┃╭╮┃┃╭╮┃┃┃┃┃┃
 ┃┃┃╰┫╰╯┃┃┃┃┃┃
 ╰╯╰━┻━━┻╯╰╯╰╯

 Desenvolvedor: ZidenVentania
 Colaboradores: NeT32, Gabripj, Feldmann
 Patrocionio: InstaMC

 */
package nativelevel.Custom.Items;

import java.util.HashSet;
import java.util.logging.Level;
import me.fromgate.playeffect.PlayEffect;
import me.fromgate.playeffect.VisualEffect;
import nativelevel.Classes.Thief;
import nativelevel.Custom.CustomItem;
import nativelevel.Jobs;
import nativelevel.KoM;
import nativelevel.Lang.L;
import nativelevel.Attributes.Mana;
import nativelevel.spec.PlayerSpec;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class Ponte extends CustomItem {

    public static HashSet<Block> bloqueios = new HashSet<Block>();
    
    public Ponte() {
        super(Material.GOLD_BLOCK, L.m("Bloco Hidraulico"), L.m("Coloca um bloco hidraulico"), CustomItem.RARO);
        Runnable r = new Runnable() {
            public void run() {
                for(Block b: bloqueios) {
                    PlayEffect.play(VisualEffect.FLAME, b.getLocation(), "num:3");
                }
            }
        };
        Bukkit.getScheduler().scheduleSyncRepeatingTask(KoM._instance, r, 10, 10);
    }

    @Override
    public boolean onItemInteract(Player player) {
        return false;
    }

}
