package nativelevel.titulos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;
import nativelevel.karma.KarmaFameTables;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class EscolheTitulo implements Listener {

    private static String menuname = "§8Titulos";

    public static void open(Player p) {
        TreeMap<String, List<ChatColor>> titulos = Titulos.getTitulos(p);
        String tag = null;

        if (titulos.size() == 0) {
            p.sendMessage(ChatColor.GREEN + "Voce nao tem titulos , ainda...");
            return;
        } else {
            Inventory i = Bukkit.createInventory(null, 36, menuname);
            String titulovelho = TituloDB.getPlayerData(p).getTitulo();
            List<String> titulonew = new ArrayList();
            titulonew.addAll(titulos.keySet());

            for (String titulo : titulonew) {

                ItemStack it = new ItemStack(Material.BOOK_AND_QUILL);

                if (titulovelho != null) {
                    if (titulo.equalsIgnoreCase(titulovelho)) {
                        ItemUtils.AddLore(it, "§a§lSELECIONADO");
                    }
                }
                ChatColor cor = ChatColor.GREEN;
                List<ChatColor> cores = titulos.get(titulo);
                if(cores != null && cores.size() > 0) {
                    cor = cores.get(0);
                }
                
                ItemUtils.AddLore(it, "§0>" + titulo);
                titulo = Titulos.trabalhaTitulo(titulo, p,cor);
                ItemUtils.SetItemName(it, titulo);
                i.addItem(it);
            }
            i.setItem(i.getSize() - 2, ItemUtils.CreateStack(Material.REDSTONE_BLOCK, (byte) 0, 1, "§c§lRemover Titulo"));
           p.openInventory(i);
        }

    }
    @EventHandler
    public void click(InventoryClickEvent ev) {
        Inventory i = ev.getClickedInventory();
        Player p = (Player) ev.getWhoClicked();
        if (i == null) {
            return;
        }
        if (i.getTitle().equalsIgnoreCase(menuname)) {
            ev.setCancelled(true);
            ItemStack item = ev.getCurrentItem();
            if (item != null) {
                if (item.hasItemMeta()) {
                    if (item.getItemMeta().hasDisplayName()) {
                        if (item.getItemMeta().getDisplayName().equalsIgnoreCase("§c§lRemover Titulo")) {
                            
                            if(KarmaFameTables.cacheTitulos.containsKey(p.getUniqueId())) {
                                String titulo = KarmaFameTables.cacheTitulos.get(p.getUniqueId());
                                Titulos.setTitulo(p, titulo, ChatColor.AQUA);
                                p.sendMessage("§c§lVocê removeu seu titulo e ficou com seu título de caráter !");
                            } else {
                                Titulos.setTitulo(p, "", ChatColor.WHITE);
                                p.sendMessage("§c§lVocê removeu seu titulo!");
                            }
                            
                            p.closeInventory();
                            return;
                        }

                        if (item.getItemMeta().getLore() != null) {
                            for (String lore : item.getItemMeta().getLore()) {
                                if (lore.contains("§0>")) {
                                    String titulo = lore.split(">")[1];

                                    
                                    String nome = item.getItemMeta().getDisplayName();
                                    ChatColor cor = ChatColor.getByChar(nome.charAt(1));
                                    
                                    Titulos.setTitulo(p, titulo, cor);
                                    p.sendMessage(ChatColor.GREEN + "Seu titulo agora esta como " + titulo);
                                    p.closeInventory();
                                    break;
                                }

                            }
                        }

                    }
                }

            }
        }
        if (i.getTitle().toLowerCase().startsWith("§c§lcor:")) {
            ev.setCancelled(true);
            if (ev.getCurrentItem() != null) {
                if (ev.getCurrentItem().hasItemMeta() && ev.getCurrentItem().getItemMeta().hasLore()) {
                    for (String s : ev.getCurrentItem().getItemMeta().getLore()) {
                        if (s.startsWith("§0>")) {
                            ChatColor cor = ChatColor.valueOf(s.split(">")[1].trim());
                            String titulo = i.getTitle().split(":")[1].trim();
                            Titulos.setTitulo(p, titulo, cor);
                            p.closeInventory();
                            p.sendMessage("§f§lSeu titulo agora é: " + Titulos.trabalhaTitulo(titulo, p, cor) + " §f§l!");
                            break;
                        }
                    }

                }
            }
        }

    }

    public String getName() {
        return "Menu: Titulo";
    }

}
