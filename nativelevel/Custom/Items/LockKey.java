/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.Custom.Items;

import java.util.List;
import nativelevel.Custom.CustomItem;
import nativelevel.Jobs;
import nativelevel.Jobs.Sucesso;
import nativelevel.Lang.L;
import nativelevel.gemas.Raridade;
import nativelevel.sisteminhas.XP;
import nativelevel.utils.GeneralUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

/**
 *
 * @author User
 *
 */
public class LockKey extends CustomItem {

    public LockKey() {
        super(Material.TRIPWIRE_HOOK, "Chave", "Abre alguma fechadura", 0);
    }

    public ItemStack create(int id) {
        ItemStack lock = this.generateItem(1);
        ItemMeta meta = lock.getItemMeta();
        List<String> lore = meta.getLore();
        int lockId = Jobs.rnd.nextInt(10000000);
        if (id != -1) {
            lockId = id;
        }
        lore.add(0, ChatColor.GREEN + "Codigo:" + lockId);
        meta.setLore(lore);
        lock.setItemMeta(meta);
        return lock;
    }

    public static int getId(ItemStack lock) {
        ItemMeta meta = lock.getItemMeta();
        return Integer.valueOf(meta.getLore().get(0).split(":")[1]);
    }

    public boolean displayOnItems() {
        return false;
    }

    @Override
    public boolean onItemInteract(Player p) {
        return true;
    }

    @Override
    public void interage(PlayerInteractEvent ev) {

        if (ev.getClickedBlock() != null && ev.getClickedBlock().getType() == Material.ANVIL) {

            if (Jobs.getJobLevel(Jobs.Classe.Ferreiro, ev.getPlayer()) == Jobs.TipoClasse.NADA) {
                ev.getPlayer().sendMessage(ChatColor.RED + "Você bateu com a chave na bigorna sem saber o que fazer direito. Um som de ferro batendo estridente ecoa nos seus ouvidos, mas nada aconteceu. !");
                return;
            }

            if (!GeneralUtils.hasItem(ev.getPlayer().getInventory(), Material.IRON_INGOT, 1, (byte) 0)) {
                ev.getPlayer().sendMessage(ChatColor.RED + L.m("Voce precisa de uma barra de ferro para copiar a chave !"));
                ev.setCancelled(true);
                return;
            } else {
                GeneralUtils.removeInventoryItems(ev.getPlayer().getInventory(), Material.IRON_INGOT, 1, (byte) 0);
                Sucesso sucesso = Jobs.hasSuccess(25, Jobs.Classe.Engenheiro, ev.getPlayer());
                if (sucesso.acertou == false) {
                    ev.getPlayer().sendMessage(ChatColor.RED + L.m("Voce falhou ao copiar a chave !"));
                    return;
                } else {
                    XP.changeExp(ev.getPlayer(), XP.getExpPorAcao(10));
                }
                ev.getPlayer().getItemInHand().setAmount(ev.getPlayer().getItemInHand().getAmount() + 1);
                ev.getPlayer().sendMessage(ChatColor.GREEN + L.m("Voce copiou a chave !"));
            }
        } else if (ev.getClickedBlock() == null || ev.getClickedBlock().getType() != Material.CHEST) {
            if (Jobs.getJobLevel(Jobs.Classe.Engenheiro, ev.getPlayer()) != Jobs.TipoClasse.NADA) {
                ev.getPlayer().sendMessage(ChatColor.GREEN + "Clique direito em uma bigorna para copiar esta chave !");
            }

        }

    }

}
