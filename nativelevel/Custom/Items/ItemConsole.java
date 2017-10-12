/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.Custom.Items;

import java.util.ArrayList;
import java.util.UUID;
import nativelevel.Custom.CustomItem;
import nativelevel.KoM;
import nativelevel.Lang.L;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author USER
 */
public class ItemConsole extends CustomItem {

    public ItemConsole() {
        super(Material.CHEST, L.m("Console Item"), L.m("Faz algum comando no console"), CustomItem.LENDARIO);
    }

    @Override
    public boolean onItemInteract(Player p) {
        usa(p);
        return true;
    }

    public static ItemStack create(String cmd) {
        ItemStack item = new ItemStack(Material.CHEST);
        if (cmd != null) {
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§6Troque esse meu nome jovi!");
            ArrayList lore = new ArrayList<String>();
            lore.add("§0/" + cmd);
            lore.add("§0:" + L.m("Console Item"));
            meta.setLore(lore);
            item.setItemMeta(meta);

        }
        return item;
    }

    public static void usa(Player p) {
        String cmd = null;
        if (p.getItemInHand().getItemMeta().getLore().size() < 2) {
            return;
        }
        ItemStack namao = p.getItemInHand();
        if (namao.getItemMeta().getLore().get(0).contains("/")) {
            cmd = namao.getItemMeta().getLore().get(0).split("/")[1];
            cmd = cmd.replace("@p", p.getName());
        }
        if (cmd.startsWith("op") || cmd.startsWith("gamemode") || cmd.startsWith("pex")) {
            cmd = null;
            p.setItemInHand(null);
            p.kickPlayer("§4SAI FI DO EXU!");

            KoM.log.warning("TEM GENTE TENTANDO USAR CMD BLOQUEADO COM O ITEMCONSOLE NOME DE QUEM TA TENTANDO:" + p.getName());
            return;
        }
        if (cmd != null) {
            /*
             if(cmd.contains("gerador")) {
             cmd = null;
             p.setItemInHand(null);
             p.kickPlayer("§4SAI FI DO EXU!");
             return;
             }
             */
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
        }
        KoM.log.info("Foi enviado o " + cmd + " para o console o player com o item foi " + p.getName() + "!");

        if (p.getItemInHand().getAmount() == 1) {
            p.setItemInHand(null);
        } else {
            p.getItemInHand().setAmount(p.getItemInHand().getAmount() - 1);
        }
        p.updateInventory();
    }
}
