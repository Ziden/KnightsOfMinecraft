/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.gemas;

import java.util.List;
import nativelevel.Jobs;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class SocketListener implements Listener {

    @EventHandler
    public void interage(PlayerInteractEvent ev) {
        if (ev.getItem() != null && (ev.getItem().getType() == Material.STAINED_GLASS)) {
            Gema gema = Gema.getGema(ev.getItem());
            if (gema != null) {
                ev.getPlayer().sendMessage("§4§lPara usar esta gema, abra seu inventario, clique na Gema e arraste ela para a arma ou armadura que possui um Encaixe.");
            }
        }
    }

    @EventHandler
    public void invClick(InventoryClickEvent ev) {
        if (ev.getCurrentItem() != null && ev.getCurrentItem().getType() != Material.AIR) {
            if (ev.getCursor() != null && ev.getCursor().getType() != Material.AIR && (ev.getCursor().getType() == Material.STAINED_GLASS)) {
                Player p = (Player) ev.getWhoClicked();
                Gema gema = Gema.getGema(ev.getCursor());
                if (gema != null) {
                    List<Gema> sockets = Gema.getSocketsLivres(ev.getCurrentItem());
                    if (sockets.size() == 0) {
                        p.sendMessage("§4Este item não tem encaixes disponíveis");
                        return;
                    }
                    int job = Jobs.getJobLevel("Mago", p);
                    if(job!=1) {
                        p.sendMessage("§4Apenas magos primários podem fazer isto.");
                        return;
                    }
                    
                    boolean pois = false;
                    for (Gema possivel : sockets) {
                        if (possivel == gema) {
                            
                            int sucesso = Jobs.hasSuccess(80, "Mago", p);
                            if(sucesso==Jobs.fail) {
                                ev.setCursor(new ItemStack(Material.GLASS));
                                ev.getCurrentItem().setDurability((short)(ev.getCurrentItem().getDurability()+20));
                                if(ev.getCurrentItem().getDurability()>ev.getCurrentItem().getType().getMaxDurability()) {
                                    ev.setCurrentItem(null);
                                }
                                p.sendMessage("§4Você não conseguiu colocar a gema magica no equipamento, danificando ambos.");
                                return;
                            }
                            
                            Gema.addGemaToItem(ev.getCurrentItem(), gema, ev.getCursor());
                            ev.setCursor(null);
                            ev.setCancelled(true);
                            p.sendMessage("§aVoce adicionou a gema no encaixe !");
                            pois = true;
                            break;
                        }
                    }
                    if (!pois) {
                        p.sendMessage("§4Sua gema é " + gema.name() + " e este item nao possui encaixe desta cor !");
                    }
                }
            }
        }
    }

}
