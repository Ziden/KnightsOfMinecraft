/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.Custom.Items;

import java.util.List;
import nativelevel.Custom.CustomItem;
import nativelevel.Jobs;
import nativelevel.Jobs.TipoClasse;
import nativelevel.Lang.L;
import nativelevel.sisteminhas.XP;
import nativelevel.utils.GeneralUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author User
 */
public class Lock extends CustomItem {

    public Lock() {
        super(Material.OBSERVER, L.m("Tranca"), L.m("Coloque em um baú para tranca-lo"), 0);
    }

    public ItemStack create(int lockLevel) {
        ItemStack lock = this.generateItem(1);
        ItemMeta meta = lock.getItemMeta();
        List<String> lore = meta.getLore();
        lore.add(0, ChatColor.GREEN + "Nivel:" + lockLevel);
        int lockId = Jobs.rnd.nextInt(10000000);
        lore.add(1, ChatColor.GREEN + "Codigo:" + lockId);
        meta.setLore(lore);
        lock.setItemMeta(meta);
        return lock;
    }

    public static int getLockLevel(ItemStack lock) {
        ItemMeta meta = lock.getItemMeta();
        return Integer.valueOf(meta.getLore().get(0).split(":")[1]);
    }

    public static int getId(ItemStack lock) {
        ItemMeta meta = lock.getItemMeta();
        return Integer.valueOf(meta.getLore().get(1).split(":")[1]);
    }

    @Override
    public void interage(PlayerInteractEvent ev) {
        
        if(Jobs.getJobLevel(Jobs.Classe.Engenheiro, ev.getPlayer())==TipoClasse.NADA) {
            ev.getPlayer().sendMessage("Voce nao sabe o que fazer com isto.");
            return;
        }
        
        if (ev.getClickedBlock() != null && ev.getClickedBlock().getType() == Material.ANVIL) {
            if (!GeneralUtils.hasItem(ev.getPlayer().getInventory(), Material.IRON_INGOT, 1, (byte) 0)) {
                ev.getPlayer().sendMessage(ChatColor.RED + L.m("Voce precisa de uma barra de ferro para fazer a chave desta fechadura !"));
                ev.setCancelled(true);
                return;
            } else {
                /////// CREATE KEY FOR THE LOCK
                GeneralUtils.removeInventoryItems(ev.getPlayer().getInventory(), Material.IRON_INGOT, 1, (byte) 0);
                Jobs.Sucesso sucesso = Jobs.hasSuccess(25, Jobs.Classe.Engenheiro, ev.getPlayer());
                if (sucesso.acertou == false) {
                    ev.getPlayer().sendMessage(ChatColor.RED + L.m("Voce falhou em fazer a chave !"));
                    return;
                } else {
                    XP.changeExp(ev.getPlayer(), XP.getExpPorAcao(10));
                }
                Lock lock = (Lock) CustomItem.getItem(Lock.class);
                LockKey lockKey = (LockKey) CustomItem.getItem(LockKey.class);

                int lockId = lock.getId(ev.getPlayer().getItemInHand());
                ItemStack key = lockKey.create(lockId);
                ev.getPlayer().getInventory().addItem(key);
                ev.getPlayer().sendMessage(ChatColor.GREEN + L.m("Voce criou a chave para esta tranca !"));
            }
            //////// COPY THE LOCK
        } else if (ev.getClickedBlock() != null && ev.getClickedBlock().getType() == Material.DISPENSER) {
            if (!GeneralUtils.hasItem(ev.getPlayer().getInventory(), Material.IRON_INGOT, 3, (byte) 0)) {
                ev.getPlayer().sendMessage(ChatColor.RED + L.m("Voce precisa de 3 lingotes de ferro para criar a Tranca !"));
                return;
            } else {
                GeneralUtils.removeInventoryItems(ev.getPlayer().getInventory(), Material.IRON_INGOT, 3, (byte) 0);
                int difficulty = getLockLevel(ev.getPlayer().getItemInHand());
                Jobs.Sucesso sucesso = Jobs.hasSuccess(25, Jobs.Classe.Engenheiro, ev.getPlayer());
                if (sucesso.acertou == false) {
                    ev.getPlayer().sendMessage(ChatColor.RED + L.m("Voce falhou em copiar a tranca !"));
                    return;
                } else {
                    XP.changeExp(ev.getPlayer(), XP.getExpPorAcao(10));
                }
                ItemStack newKey = ev.getPlayer().getItemInHand().clone();
                newKey.setItemMeta(ev.getPlayer().getItemInHand().getItemMeta().clone());
                ev.getPlayer().getInventory().addItem(newKey);
                ev.getPlayer().sendMessage(ChatColor.GREEN + L.m("Voce copiou a tranca !"));
            }
        } else {
            ev.getPlayer().sendMessage(ChatColor.GREEN + "Coloque isto em um baú para tranca-lo !");
            ev.getPlayer().sendMessage(ChatColor.GREEN + "Clique direito com isto em uma bigorna para fazer uma chave para a tranca !");
            ev.getPlayer().sendMessage(ChatColor.GREEN + "Clique direito em um dispenser para copiar esta tranca !");
        }
    }

    public boolean displayOnItems() {
        return false;
    }

    @Override
    public boolean onItemInteract(Player p) {
        return true;
    }

}
