package nativelevel.sisteminhas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import me.blackvein.quests.Quest;
import me.blackvein.quests.Quester;
import static me.blackvein.quests.Quests.ignoreLockedQuests;
import nativelevel.Custom.CustomItem;
import nativelevel.Custom.Items.BussolaMagica;
import nativelevel.KoM;
import nativelevel.Lang.LangMinecraft;
import nativelevel.utils.MetaUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Ziden
 *
 */
public class QuestsIntegracao extends KomSystem {

    public static void abreInventarioQuests(Player p) {
        abreMenu(p);
    }

    public static void abreMenu(Player p) {
        Inventory i = Bukkit.createInventory(p, 9, "Bussola do KoM");
        i.setItem(2, MetaUtils.setItemNameAndLore(new ItemStack(Material.BOOKSHELF), ChatColor.GOLD + "Quests Possíveis", new String[]{"", ChatColor.GREEN + "Veja quais quests você pode fazer"}));
        i.setItem(4, MetaUtils.setItemNameAndLore(new ItemStack(Material.BOOK_AND_QUILL), ChatColor.GOLD + "Minhas Quests", new String[]{"", ChatColor.GREEN + "Veja as quests que você está fazendo"}));
        i.setItem(6, MetaUtils.setItemNameAndLore(new ItemStack(Material.COMPASS), ChatColor.GOLD + "Locais no Mapa", new String[]{"", ChatColor.GREEN + "Mostra locais do seu nivel pelo mapa"}));
        p.openInventory(i);
    }

    @EventHandler
    public void invClick(InventoryClickEvent ev) {
        if (ev.getInventory() != null && ev.getInventory().getName().equalsIgnoreCase("Minhas Quests")) {
            Player p = (Player) ev.getWhoClicked();
            if (ev.getCurrentItem() != null && ev.getCurrentItem().getType() != Material.AIR) {
                ItemMeta meta = ev.getCurrentItem().getItemMeta();
                if (meta.getDisplayName() != null) {
                    String clicado = ChatColor.stripColor(meta.getDisplayName());
                    Quest q = KoM.quests.getQuest(clicado);
                    Quester quester = KoM.quests.getQuester(p.getUniqueId());
                    q.updateCompass(quester, quester.getCurrentStage(q));
                    p.sendMessage(ChatColor.GREEN + "A bussola está apontando para quest " + clicado);
                }
            }
        } else if (ev.getInventory() != null && ev.getInventory().getName().equalsIgnoreCase("Bussola do KoM")) {
            Player p = (Player) ev.getWhoClicked();
            if (ev.getCurrentItem() != null && ev.getCurrentItem().getType() != Material.AIR) {
                ItemMeta meta = ev.getCurrentItem().getItemMeta();
                if (meta.getDisplayName() != null) {
                    String clicado = ChatColor.stripColor(meta.getDisplayName());
                    if (clicado.equalsIgnoreCase("Quests Possíveis")) {
                        p.closeInventory();
                        listaQuests(p);
                    } else if (clicado.equalsIgnoreCase("Minhas Quests")) {
                        p.closeInventory();
                        mostraQuests(p);
                    } else if (clicado.equalsIgnoreCase("Locais no Mapa")) {
                        p.closeInventory();
                        CustomItem.getItem(BussolaMagica.class).onItemInteract(p);
                    }
                }
            }
        } else if (ev.getInventory() != null && ev.getInventory().getName().equalsIgnoreCase("Quests Possiveis")) {
            Player p = (Player) ev.getWhoClicked();
            ev.setCancelled(true);
            if (ev.getCurrentItem() != null && ev.getCurrentItem().getType() != Material.AIR) {
                ItemMeta meta = ev.getCurrentItem().getItemMeta();
                if (meta.getDisplayName() != null) {
                    String nomeQuest = ChatColor.stripColor(meta.getDisplayName());
                    Quest q = KoM.quests.getQuest(nomeQuest);
                    if (q.npcStart != null) {
                        p.setCompassTarget(q.npcStart.getEntity().getLocation());
                        p.sendMessage(ChatColor.GREEN + "Sua bussola aponta agora para a quest " + q.getName());
                    } else {
                        if (q.blockStart != null) {
                            p.setCompassTarget(q.blockStart);
                            p.sendMessage(ChatColor.GREEN + "Sua bussola aponta agora para a quest " + q.getName());
                        } else {
                            p.sendMessage(ChatColor.GREEN + "Sua bussola não consegue localizar essa quest");
                        }
                    }
                }
            }
        }
    }

    public static void mostraQuests(Player p) {

        Inventory i = Bukkit.createInventory(p, 9, "Minhas Quests");

        Quester quester = KoM.quests.getQuester(p.getUniqueId());

        if (quester.currentQuests.isEmpty()) {
            p.sendMessage(ChatColor.RED + "Voce nao está em nenhuma quest");
            return;
        } else {
            for (Quest q : quester.currentQuests.keySet()) {
                ItemStack item = new ItemStack(Material.BOOK_AND_QUILL, 1);
                ItemMeta meta = item.getItemMeta();
                List<String> lore = quester.getObjectivesReal(q);
                meta.setDisplayName(ChatColor.GOLD + q.getName());
                lore = quester.getObjectivesReal(q);
                lore.add(ChatColor.GREEN + "" + ChatColor.BOLD + "Clique para Apontar");
                meta.setLore(lore);
                item.setItemMeta(meta);
                i.addItem(item);
            }
        }

        p.openInventory(i);

    }

    public static int XP_MOD = 50;

    public static void listaQuests(Player p) {

        Inventory i = Bukkit.createInventory(p, 9 * 5, "Quests Possiveis");

        Quester quester = KoM.quests.getQuester(p.getUniqueId());
        List<Quest> paraFazer = new ArrayList<Quest>();

        for (Quest q : KoM.quests.getQuests()) {

            if (q.testRequirements(quester) && !quester.completedQuests.contains(q.name)) {

                try {
                
                ItemStack icone = new ItemStack(Material.BOOK_AND_QUILL, 1);
                ItemMeta meta = icone.getItemMeta();

                meta.setDisplayName(ChatColor.AQUA + q.getName());
                List<String> lore = new ArrayList<String>();

                int nivel = 0;

                if (q.commands.size() > 0) {
                    for (String com : q.commands) {
                        if (com.contains("darxp")) {
                            nivel = Integer.valueOf(com.split(" ")[2]);
                        }
                    }
                }

                int xp = XP.getExpPorAcao(nivel) * XP_MOD;

                if (q.customRequirements != null && q.customRequirements.containsKey("Nivel KoM")) {
                    Map<String, Object> mapa = q.customRequirements.get("Nivel KoM");
                    if (mapa != null) {
                        KoM.debug("MAPA " + mapa.keySet().toString());
                        nivel = Integer.valueOf((String) mapa.get("Level"));
                        lore.add(ChatColor.GREEN + "Nivel Minimo: " + ChatColor.YELLOW + nivel);
                    }

                }

                lore.add(ChatColor.GREEN + "XP: " + ChatColor.YELLOW + xp);
                if (nivel == 0) {
                    lore.add(ChatColor.RED + "[ Quest sem XP ]");
                    //if (p.isOp()) {
                    //    lore.add(ChatColor.RED + "Essa quest ta sem recompensa /darxp <mano> <nivel>");
                    //}
                }

                if (q.npcStart != null) {
                    // no mesmo mundo
                    if (q.npcStart.getEntity() != null && q.npcStart.getEntity().getLocation().getWorld().getName().equalsIgnoreCase("dungeon")) {
                        lore.add(ChatColor.GREEN + "Local: " + ChatColor.YELLOW + " Algum lugar macabro por aí.");
                    } else {
                        if (q.npcStart.getEntity() != null && q.npcStart.getEntity().getLocation().getWorld().getName().equalsIgnoreCase(p.getWorld().getName())) {
                            int distancia = ((Double) q.npcStart.getEntity().getLocation().distance(p.getLocation())).intValue();
                            lore.add(ChatColor.GREEN + "Distância: " + ChatColor.YELLOW + distancia + " blocos");
                            lore.add(ChatColor.GREEN + "NPC: " + ChatColor.YELLOW + q.npcStart.getName());
                        } else {
                            lore.add(ChatColor.GREEN + "NPC não foi encontrado.");
                        }
                    }
                } else {
                    if (q.blockStart != null) {
                        if (q.blockStart.getWorld().getName().equalsIgnoreCase("dungeon")) {
                            lore.add(ChatColor.GREEN + "Local: " + ChatColor.YELLOW + " Algum lugar macabro por aí.");
                        } else {
                            if (q.blockStart.getWorld().getName().equalsIgnoreCase(p.getWorld().getName())) {
                                try {
                                    int distancia = ((Double) q.npcStart.getEntity().getLocation().distance(p.getLocation())).intValue();
                                    lore.add(ChatColor.GREEN + "Distância: " + ChatColor.YELLOW + distancia + " blocos");
                                } catch(Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                lore.add(ChatColor.GREEN + "Bloco não foi encontrado.");
                            }
                        }
                        lore.add(ChatColor.GREEN + "Bloco de Início: " + ChatColor.YELLOW + LangMinecraft.get().get(new ItemStack(q.blockStart.getBlock().getType())));
                    }
                }

                lore.add(ChatColor.YELLOW + "" + ChatColor.BOLD + "Clique para Apontar na Bussola");

                meta.setLore(lore);
                icone.setItemMeta(meta);
                i.addItem(icone);
                
                } catch(Throwable e) {
                    e.printStackTrace();
                }

            }
        }

        p.openInventory(i);
        p.sendMessage(ChatColor.GREEN + "Voce está vendo as quests que voce pode fazer.");
    }
}
