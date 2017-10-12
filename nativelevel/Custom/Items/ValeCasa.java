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

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import java.util.Iterator;
import nativelevel.Custom.CustomItem;
import nativelevel.KoM;
import nativelevel.Lang.L;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class ValeCasa extends CustomItem {

    public ValeCasa() {
        super(Material.PAPER,L.m( "Escritura de Terreno"), L.m("Vale uma casa eterna em Rhodes"), CustomItem.LENDARIO);
    }

    @Override
    public boolean onItemInteract(Player p) {
        if (!p.getWorld().getName().equalsIgnoreCase("vila")) {
            p.sendMessage(ChatColor.RED + L.m("Use isto em Rhodes apenas, em cima do terreno que deseja !"));
            return true;
        }
        KoM l;
        ApplicableRegionSet set = KoM.worldGuard.getRegionManager(p.getWorld()).getApplicableRegions(p.getLocation());
        if (set == null || set.size() < 2) {
            p.sendMessage(ChatColor.RED + L.m("Voce precisa estar em um terreno vazio !"));
            return true;
        }
        int x = 0;
        boolean kato = false;
        Iterator<ProtectedRegion> i = set.iterator();
        while (x < 2 && i.hasNext()) {
            ProtectedRegion regiao = i.next();
            if(KoM.debugMode)
                KoM.log.info("verificando regiao "+regiao.getId());
            
            if (regiao.getId().contains("casa")) {
                if (regiao.getOwners().size() == 0) {
                    p.sendMessage(ChatColor.GREEN + L.m("Esta é sua nova casa !"));
                    regiao.getOwners().addPlayer(p.getName());
                    kato = true;
                    if (p.getItemInHand().getAmount() > 1) {
                        p.getItemInHand().setAmount(p.getItemInHand().getAmount() - 1);
                    } else {
                        p.setItemInHand(null);
                        //p.getInventory().removeItem(new ItemStack(Material.LAPIS_BLOCK, 1));
                    }
                    break;
                }
            }
            x++;
        }
        if (!kato) {
            p.sendMessage(ChatColor.RED + L.m("Este terreno não é válido ! Use isto em Rhodes apenas, em cima do terreno que deseja !"));
            return true;
        }
        return true;
    }
}
