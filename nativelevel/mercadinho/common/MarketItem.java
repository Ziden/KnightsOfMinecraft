/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.mercadinho.common;

import java.util.UUID;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import java.util.List;
import nativelevel.Lang.L;
import nativelevel.gemas.Raridade;
import nativelevel.mercadinho.ItemUtils;
import nativelevel.utils.CategoriaUtils;
import nativelevel.utils.CategoriaUtils.CategoriaItem;

/**
 *
 * @author Gabriel
 */
public class MarketItem {

    public ItemStack ItemStack;
    public double Valor;
    public UUID dono;
    public String nickdono;
    public int ID = 0;
    boolean vencido = false;
    public CategoriaItem categoria;
    public boolean cash = false;

    public MarketItem(UUID id, ItemStack i, double val, String nick, int iditem, boolean ehCash) {
        this.dono = id;
        this.ItemStack = ClearItemStack(i);
        this.Valor = val;
        this.nickdono = nick.trim();
        this.ID = iditem;
        this.categoria = CategoriaUtils.getCategoria(i);
        this.cash = ehCash;
    }

    public void setVencido(boolean vencido) {
        this.vencido = vencido;
    }

    public ItemStack getNamedItem() {
        ItemStack it = ItemStack.clone();
        //ItemUtils.ClearLore(it);
        if (!vencido) {
            ItemUtils.AddLore(it, L.m("§e§lDono: §a") + this.nickdono);
            ItemUtils.AddLore(it, L.m("§e§lPreço: §6") + this.Valor);
        } else {
            ItemUtils.AddLore(it, itemvencido);
        }
        if (cash) {
            ItemUtils.AddLore(it, CASH_STR);
        } else {
            ItemUtils.AddLore(it, ChatColor.GOLD + " - Esmeraldas - ");
        }
        ItemUtils.AddLore(it, "§eShopID: §3" + this.ID);
        return it;
    }
    
    public static String CASH_STR = ChatColor.YELLOW + Raridade.Lendario.getIcone() + " CASH " + Raridade.Lendario.getIcone(); 

    public static String itemvencido = L.m("§c§lITEM VENCIDO! Para retirar ele clique!!");

    public ItemStack getClearItem() {
        ItemStack it = ItemStack.clone();
        List<String> listalore = ItemUtils.GetLore(it);
        List<String> listaaux = ItemUtils.GetLore(it);
        if (it.hasItemMeta()) {
            if (it.getItemMeta().getLore() != null) {
                for (String inf : listaaux) {
                    if (ChatColor.stripColor(inf).toLowerCase().startsWith(L.m("dono:"))) {
                        listalore.remove(inf);
                    } else if (ChatColor.stripColor(inf).toLowerCase().startsWith(L.m("preço:"))) {
                        listalore.remove(inf);
                    } else if (ChatColor.stripColor(inf).toLowerCase().startsWith(L.m("shopid:"))) {
                        listalore.remove(inf);
                    } else if (inf.equals(itemvencido)) {
                        listalore.remove(inf);
                    } else if (ChatColor.stripColor(inf).toLowerCase().contains(L.m("- esmeraldas -"))) {
                        listalore.remove(inf);
                    } else if (ChatColor.stripColor(inf).toLowerCase().contains(L.m("- moedas -"))) {
                        listalore.remove(inf);
                    }  else if (ChatColor.stripColor(inf).toLowerCase().contains(MarketItem.CASH_STR.toLowerCase())) {
                        listalore.remove(inf);
                    }
                }
                ItemUtils.SetLore(it, listalore);
            }
        }

        //ItemUtils.ClearLore(it);
        return it;
    }

    private ItemStack ClearItemStack(ItemStack item) {
        ItemStack it = item.clone();
        if (it.hasItemMeta()) {
            if (it.getItemMeta().getLore() != null) {
                List<String> listalore = ItemUtils.GetLore(it);
                List<String> listaaux = ItemUtils.GetLore(it);
                for (String inf : listaaux) {
                    if (ChatColor.stripColor(inf).toLowerCase().startsWith(L.m("dono:"))) {
                        listalore.remove(inf);
                    } else if (ChatColor.stripColor(inf).toLowerCase().startsWith(L.m("preço:"))) {
                        listalore.remove(inf);
                    } else if (ChatColor.stripColor(inf).toLowerCase().startsWith(L.m("shopid:"))) {
                        listalore.remove(inf);
                    } else if (inf.equals(itemvencido)) {
                        listalore.remove(inf);
                    }
                }
                ItemUtils.SetLore(it, listalore);
            }
        }
        return it;
    }

}
