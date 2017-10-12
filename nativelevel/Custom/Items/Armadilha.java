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

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;
import nativelevel.Custom.CustomItem;
import nativelevel.Jobs;
import nativelevel.KoM;
import nativelevel.sisteminhas.ClanLand;
import nativelevel.Lang.L;
import nativelevel.Attributes.Mana;
import nativelevel.integration.SimpleClanKom;
import nativelevel.integration.WG;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Armadilha extends CustomItem {

    private class MudaBlock implements Runnable {

        private Player p;
        private Material m;
        private Block b;

        public MudaBlock(Player p, Material m, Block b) {
            this.p = p;
            this.m = m;
            this.b = b;
        }

        @Override
        public void run() {
            p.sendBlockChange(b.getLocation(), m, (byte) 0);
        }
    }

    public void muda(Player p, Block b, Material m) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(KoM._instance, new MudaBlock(p, m, b), 20);
    }

    public static HashSet<Material> m = null;

    public Armadilha() {
        super(Material.PAPER, L.m("Armadilha"), L.m("Monta uma armadilha explosiva"), CustomItem.INCOMUM);
    }

    public static HashMap<Block, UUID> armadilhas = new HashMap<Block, UUID>();

    @Override
    public boolean onItemInteract(final Player p) {

        final Block b = p.getTargetBlock(m, 10);
        if (b == null || b.getType() == Material.AIR) {
            p.sendMessage(ChatColor.RED + L.m("Selecione um bloco no chao para colocar a armadilha !"));
            return true;
        }
        if (WG.ehSafeZone(b.getLocation())) {
            p.sendMessage(ChatColor.RED + L.m("Voce nao pode usar isto aqui !"));
            return false;
        }
        int lvl = Jobs.getJobLevel("Engenheiro", p);
        if (lvl != 1) {
            p.sendMessage(ChatColor.RED + L.m("Apenas engenheiros experientes sabem usar isto."));
            return true;
        }
        //if(armadilhas.containsValue(p.getUniqueId())) {

        //}
        if (!Mana.spendMana(p, 15)) {
            return true;
        }
        final Location local = b.getLocation();
        // local.setY(local.getY()+1);
        for (Entity e : p.getNearbyEntities(20, 6, 20)) {
            if (e instanceof Player) {
                Player nego = (Player) e;
                ClanPlayer cp = ClanLand.getPlayer(nego.getName());
                if (cp == null) {
                    continue;
                }
                if (cp.isAlly(p)) {
                    muda(nego, local.getBlock(), Material.REDSTONE_BLOCK);
                }
            }
        }
        muda(p, local.getBlock(), Material.TNT);
        p.sendMessage(ChatColor.GREEN + L.m("Voce colocou a armadilha !"));
        armadilhas.put(b, p.getUniqueId());
        if (p.getItemInHand().getAmount() > 1) {
            p.getItemInHand().setAmount(p.getItemInHand().getAmount() - 1);
        } else {
            p.setItemInHand(null);
            //p.getInventory().removeItem(new ItemStack(Material.LAPIS_BLOCK, 1));
        }
        return true;
    }
}
