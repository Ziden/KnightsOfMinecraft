/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.Attributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import nativelevel.CFG;
import nativelevel.Menu.Menu;
import nativelevel.utils.MetaUtils;
import nativelevel.KoM;
import nativelevel.Lang.L;
import nativelevel.integration.BungeeCordKom;
import nativelevel.sisteminhas.XP;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Wool;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class MenuAtributos implements Listener {
    
    /*
    public static final String NomeMenu = L.m("Atributos !");
    public static HashMap<String, Inventory> ListaMenus = new HashMap();
    public static HashMap<String, Inventory> ListaMenusDisabled = new HashMap();
    
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inv = event.getInventory();
        String title = event.getView().getTitle();
        int slot = event.getRawSlot();
        try {
            if (slot < 0 || slot >= inv.getSize() || inv.getItem(slot) == null) {
                return;
            }
        } catch (Exception e) {
            return;
        }
        if (title.equals(NomeMenu)) {
            try {
                event.setCancelled(true);
                if (slot <= 44 && inv.getItem(slot) != null) {
                    event.setCancelled(true);
                    //event.setCursor(null);
                    event.setResult(Event.Result.DENY);
                    //player.updateInventory();
                    AttributeInfo info = KnightsOfMania.database.getAtributos(player);
                    ItemStack clicado = event.getCurrentItem();
                    if (clicado != null) {
                        if (clicado.getType() == Material.WOOL) {
                            Wool w = new Wool(clicado.getType(), clicado.getData().getData());
                            for (Attr atributo : info.attributes.keySet()) {
                                if (atributo.color == w.getColor()) {
                                    if (event.isShiftClick()) {
                                        
                                        int custoTotal = 0;
                                        int nivelAtributo = info.attributes.get(atributo);
                                        if (nivelAtributo + 10 > 100) {
                                            player.sendMessage(ChatColor.RED + L.m("Maximo 100 por atributo !"));
                                            return;
                                        }
                                        int pontos = info.points;
                                        for (int x = 0; x < 10; x++) {
                                            custoTotal += Attributes.calcCost(nivelAtributo);
                                            nivelAtributo++;
                                        }
                                        if (pontos < custoTotal) {
                                            player.sendMessage(ChatColor.RED + L.m("Voce precisa de % pontos para subir sua ", custoTotal + "") + atributo.name);
                                            return;
                                        }
                                        pontos -= custoTotal;
                                        KnightsOfMania.database.changePoints(player, pontos);
                                        KnightsOfMania.database.mudaAtributo(player, atributo, info.attributes.get(atributo) + 10);
                                        player.sendMessage(ChatColor.GREEN + L.m("Voce sente que sua % aumentou muito !", atributo.name));
                                        player.closeInventory();
                                        mostraAtributos(player);
                                        break;
                                    }
                                    int nivelAtributo = info.attributes.get(atributo);
                                    if (nivelAtributo + 1 > 100) {
                                        player.sendMessage(ChatColor.RED + L.m("Maximo 100 por atributo !"));
                                        return;
                                    }
                                    int custo = Attributes.calcCost(nivelAtributo);
                                    int pontos = info.points;
                                    if (pontos < custo) {
                                        player.sendMessage(ChatColor.RED + L.m("Voce precisa de % pontos para subir sua ", custo + "") + atributo.name);
                                        return;
                                    }
                                    pontos -= custo;
                                    KnightsOfMania.database.changePoints(player, pontos);
                                    KnightsOfMania.database.mudaAtributo(player, atributo, nivelAtributo + 1);
                                    player.sendMessage(ChatColor.GREEN + L.m("Voce sente que sua % aumentou !", atributo.name));
                                    player.closeInventory();
                                    mostraAtributos(player);
                                    break;
                                }
                            }
                            int agi = info.attributes.get(Attr.agility);
                            if (agi >= 80) {
                                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 9999, 2));
                            } else if (agi >= 60) {
                                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 9999, 1));
                            } else if (agi >= 40) {
                                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 9999, 0));
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                event.setCancelled(true);
            }
        }
    }
    
    public static void mostraAtributos(Player p) {
        Inventory i = Bukkit.getServer().createInventory(null, 9 * 2, NomeMenu);
        i.clear();
        AttributeInfo atrs = KnightsOfMania.database.getAtributos(p);
        int slot = 0;
        for (Attr atributo : atrs.attributes.keySet()) {
            int valor = atrs.attributes.get(atributo);
            Wool la = new Wool(atributo.color);
            ItemStack icone = la.toItemStack();
            icone.setAmount(1);
            ItemMeta meta = icone.getItemMeta();
            meta.setDisplayName(ChatColor.GREEN + atributo.name + " !");
            List<String> lore = new ArrayList<String>();
            lore.add(ChatColor.YELLOW + atributo.desc);
            lore.add(ChatColor.YELLOW + L.m("Sua ") + atributo.name + ": " + ChatColor.GREEN + valor);
            lore.add(ChatColor.YELLOW + L.m("Pontos para aumentar: ") + ChatColor.GREEN + Attributes.calcCost(valor));
            lore.add(ChatColor.YELLOW + L.m("Clique para aumentar ! "));
            lore.add(ChatColor.YELLOW + L.m("Clique com shift para aumentar 10 ! "));
            meta.setLore(lore);
            icone.setItemMeta(meta);
            i.setItem(slot, icone);
            slot++;
        }
        ItemStack papel = new ItemStack(Material.PAPER);
        ItemMeta meta = papel.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + L.m("Voce tem % para colocar", atrs.points + ""));
        papel.setItemMeta(meta);
        i.setItem(13, papel);
        p.openInventory(i);
    }
*/
    
}
