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

import me.asofold.bpl.simplyvanish.SimplyVanish;
import me.asofold.bpl.simplyvanish.config.Flag;
import me.asofold.bpl.simplyvanish.config.VanishConfig;
import nativelevel.Classes.Thief;
import nativelevel.Custom.CustomItem;
import nativelevel.Jobs;
import nativelevel.KoM;
import nativelevel.Lang.L;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class VisaoEspecial extends CustomItem {

    public VisaoEspecial() {
        super(Material.PUMPKIN, L.m("Capacete da Visao"), L.m("Ve inimigos invisiveis"), CustomItem.RARO);
    }

    @Override
    public boolean onItemInteract(Player p) { 
         String ci = CustomItem.getCustomItem(p.getItemInHand());
                if (ci != null) {
                    if (ci.equalsIgnoreCase(L.m("Capacete da Visao"))) {
                        if (p.getInventory().getHelmet() != null && p.getInventory().getHelmet().getType() != Material.AIR) {
                            p.sendMessage(ChatColor.RED + L.m("Retire o que tiver em sua cabeca !"));
                            return true;
                        }
                        if (Jobs.getJobLevel("Engenheiro", p) != 1) {
                            p.sendMessage(ChatColor.RED + L.m("Apenas bons engenheiros sabem usar isto !"));
                            return true;
                        }
                        if(Thief.taInvisivel(p))
                            Thief.revela(p);
                        p.sendMessage(ChatColor.GREEN + L.m("Voce equipou o capacete da visao !"));
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user "+p.getName()+" add simplyvanish.see-all");
                        VanishConfig cfg = SimplyVanish.getVanishConfig(p.getName(), true);
                        cfg.see.state = true;
                        SimplyVanish.setVanishConfig(p.getName(), cfg, true);
                        SimplyVanish.updateVanishState(p);
                        
                        p.getInventory().setHelmet(p.getItemInHand());
                        p.setItemInHand(null);
                        p.updateInventory();
                    }
                }
        return true;
    }
}
