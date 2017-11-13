/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nativelevel.mercadinho;

import java.util.ArrayList;
import java.util.List;
import nativelevel.Custom.CustomItem;
import nativelevel.KoM;
import nativelevel.Lang.L;
import nativelevel.MetaShit;
import nativelevel.mercadinho.common.MarketItem;
import nativelevel.mercadinho.database.MercadoSQL;
import nativelevel.sisteminhas.ClanLand;
import nativelevel.sisteminhas.KomSystem;
import nativelevel.utils.CategoriaUtils;
import nativelevel.utils.CategoriaUtils.CategoriaItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author User
 *
 */
public class MenuMercado extends KomSystem {

    public static ItemStack create(Material m, short b, String nome, String[] lore) {
        ItemStack ss = new ItemStack(m);
        ItemMeta meta = ss.getItemMeta();
        meta.setDisplayName("§6" + nome);
        List<String> lores = new ArrayList<String>();

        for (String s : lore) {
            lores.add(s);
        }

        meta.setLore(lores);
        ss.setItemMeta(meta);
        ss.setDurability(b);
        return ss;
    }

    public static ItemStack create(Material m, short b, String nome, String[] lore, String linhaExtra) {
        ItemStack ss = new ItemStack(m);
        ItemMeta meta = ss.getItemMeta();
        meta.setDisplayName("§6" + nome);
        List<String> lores = new ArrayList<String>();
        for (String s : lore) {
            lores.add(s);
        }
        if (linhaExtra != null) {
            lores.add(linhaExtra);
        }
        meta.setLore(lores);
        ss.setItemMeta(meta);
        ss.setDurability(b);
        return ss;
    }

    public static void abreCategorias(Player p) {
        KoM.debug("CATEGORIAS");
        Inventory categorias = Bukkit.createInventory(p, InventoryType.CHEST, "Categorias Mercado");
        int slot = 1;
        for (CategoriaItem categoria : CategoriaItem.values()) {
            ItemStack icone = new ItemStack(categoria.icone, 1);
            ItemMeta meta = icone.getItemMeta();
            meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + categoria.name());
            icone.setItemMeta(meta);
            categorias.setItem(slot, icone);
            slot += 2;
        }
        ItemStack icone = create(Material.SIGN, (byte) 0, "Procurar por Nome", new String[]{}, null);
        categorias.setItem(slot, icone);
        p.openInventory(categorias);
    }

    public static void abre(Player p) {
        Inventory i = Bukkit.createInventory(p, 27, L.m("Menu Mercado"));
        i.setItem(11, create(Material.IRON_INGOT, (byte) 0, L.m("Comprar"), new String[]{L.m("§aCompre de outros jogadores !")}));
        i.setItem(13, create(Material.GOLD_INGOT, (byte) 0, L.m("Vender"), new String[]{L.m("§aVenda para outros jogadores !")}));
        i.setItem(15, create(Material.CHEST, (byte) 0, L.m("Minhas Vendas"), new String[]{L.m("§aVeja suas vendas !"), L.m("§aResgate vendas vencidas")}));
        int receber = MercadoSQL.getRetorno(p);
        i.setItem(13 + 9, create(Material.ENDER_CHEST, (byte) 4, L.m("Receber Vendas"), new String[]{L.m("§aReceba suas esmeraldas aqui !"), L.m("§aVoce tem §6§l" + receber + " §aesms para receber."), receber > 0 ? L.m("§a§lClique para receber") : L.m("§aVenda items para receber esmeraldas")}));
        p.openInventory(i);
    }

    public void abreMenuCompra(Player p, CategoriaItem cat) {
        MarketManager.OpenMainLoja(p, cat);
    }

    public void abreMenuVenda(Player p) {
        Inventory i = Bukkit.createInventory(p, 9 * 5, L.m("Vender Item"));

        int index = 0;
        for (ItemStack ss : p.getInventory().getContents()) {

            if (ss != null) {

                boolean pode = true;

                if (p.getInventory().getItemInOffHand().isSimilar(ss)) {
                    pode = false;
                    KoM.debug("Nao pode offhand");
                }

                if (pode) {
                    for (ItemStack armadura : p.getInventory().getArmorContents()) {
                        if (armadura != null) {
                            KoM.debug("Comparando " + armadura.getType().name() + " com " + ss.getType().name());
                            if (armadura.getType() == ss.getType()) {
                                KoM.debug("tipo igual");
                                if (armadura.hasItemMeta() && ss.hasItemMeta()) {
                                    KoM.debug("os dois tem meta");
                                    if ((armadura.getItemMeta().getDisplayName() == ss.getItemMeta().getDisplayName()) || (armadura.getItemMeta().getDisplayName() != null && armadura.getItemMeta().getDisplayName().equals(ss.getItemMeta().getDisplayName()))) {
                                        KoM.debug("Achei e pulei");
                                        pode = false;
                                    }
                                }
                            }
                        }

                    }
                }

                if (pode) {
                    i.setItem(index, ss);
                    index++;
                }
            }
        }
        p.openInventory(i);
        p.sendMessage(ChatColor.GREEN + L.m("Selecione o item que deseja vender"));
    }

    public void segundaParteMenuVenda(Player p, ItemStack vendendo) {
        Inventory i = Bukkit.createInventory(p, 9 * 3, L.m("Setar Preço"));
        i.setItem(3, vendendo);
        ItemMeta meta = vendendo.getItemMeta();
        List<String> lore = meta.getLore() == null ? new ArrayList<String>() : new ArrayList<String>(meta.getLore());
        lore.add("!");
        meta.setLore(lore);
        vendendo.setItemMeta(meta);
        i.setItem(5, create(Material.GOLD_INGOT, (short) 0, L.m("Preco:10"), new String[]{L.m("§aClick direito para aumentar !"), L.m("§aClick esquerdo para diminuir"), L.m("§aSegure shift para ir de 50 em 50")}));
        i.setItem(21, create(Material.WOOL, (short) 5, L.m("Vender por Esmeraldas"), new String[]{L.m("§aConfirma a Venda por Esmeraldas !")}));
        i.setItem(23, create(Material.WOOL, (short) 5, L.m("Vender por Cash"), new String[]{L.m("§aConfirma a Venda por Cash !")}));
        p.openInventory(i);
    }

    @EventHandler
    public void dropaItem(PlayerDropItemEvent ev) {
        if (ev.getPlayer().getOpenInventory() != null) {
            Inventory aberto = ev.getPlayer().getOpenInventory().getTopInventory();
            if (aberto.getName() != null && (aberto.getName().equalsIgnoreCase("Menu Mercado") || aberto.getName().equalsIgnoreCase("Vender Item") || aberto.getName().equalsIgnoreCase("Setar Preço"))) {
                ev.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void chatEv(AsyncPlayerChatEvent ev) {
        if (ev.getPlayer().hasMetadata("procurando")) {
            String nomeItem = ChatColor.stripColor(ev.getMessage());
            List<MarketItem> lista = MercadoSQL.CarreProdutosPorNome(nomeItem);
            ev.setCancelled(true);
            ev.getPlayer().removeMetadata("procurando", plugin);
            if (lista.size() > 0) {
                MarketManager.OpenMainLoja(ev.getPlayer(), lista);
            } else {
                ev.getPlayer().sendMessage(ChatColor.RED + "Nenhum item com este nome encontrado.");
            }
        }
    }

    @EventHandler
    public void click(InventoryClickEvent ev) {
        if (ev.getWhoClicked() instanceof Player) {
            Player p = (Player) ev.getWhoClicked();

            if (ev.getInventory() != null && ev.getInventory().getName() != null && ev.getInventory().getName().equalsIgnoreCase(L.m("Categorias Mercado"))) {
                ev.setCancelled(true);

                if (ev.getCurrentItem() != null && ev.getCurrentItem().getType() != Material.AIR) {
                    String nome = ChatColor.stripColor(ev.getCurrentItem().getItemMeta().getDisplayName());
                    p.closeInventory();
                    ev.setCancelled(true);
                    if (nome.equalsIgnoreCase("Procurar por Nome")) {
                        if (!p.hasPermission("kom.vip")) {
                            p.sendMessage(ChatColor.RED + "Apenas busco no estoque por nome para Lords Templarios ou Cavaleiros... Da muito trabalho !");
                            return;
                        }
                        p.sendMessage(ChatColor.GREEN + "Digite o nome do item que deseja procurar no chat");
                        MetaShit.setMetaObject("procurando", p, true);
                    } else {
                        CategoriaItem categoria = CategoriaItem.valueOf(nome);
                        abreMenuCompra(p, categoria);
                    }

                }
            } else if (ev.getInventory() != null && ev.getInventory().getName() != null && ev.getInventory().getName().equalsIgnoreCase(L.m("Menu Mercado"))) {
                ev.setCancelled(true);
                KoM.debug("Abrindo menu de mercado");
                if (ev.getCurrentItem() != null && ev.getCurrentItem().getType() != Material.AIR) {
                    ItemMeta meta = ev.getCurrentItem().getItemMeta();
                    String opcao = ChatColor.stripColor(meta.getDisplayName());
                    if (opcao.equalsIgnoreCase(L.m(ChatColor.stripColor("Comprar")))) {
                        p.closeInventory();
                        abreCategorias(p);
                    } else if (opcao.equalsIgnoreCase(L.m(ChatColor.stripColor("Vender")))) {
                        p.closeInventory();
                        abreMenuVenda(p);
                    } else if (opcao.equalsIgnoreCase(L.m(ChatColor.stripColor("Minhas Vendas")))) {
                        MarketManager.openSelfShop(p, p.getName());
                        p.sendMessage(ChatColor.GREEN + L.m("Vendo suas vendas"));
                    } else if (opcao.equalsIgnoreCase(L.m(ChatColor.stripColor("Receber Vendas")))) {
                        p.closeInventory();
                        int vendas = MercadoSQL.getRetorno(p);
                        if (vendas == 0) {
                            p.sendMessage(ChatColor.RED + "Voce nao tem nada a receber");
                            if (!ClanLand.econ.has(p.getName(), 30)) {
                                if (p.getLevel() <= 30 && !p.hasMetadata("recebeu")) {
                                    MetaShit.setMetaObject("recebeu", p, true);
                                    p.sendMessage(ChatColor.GREEN + "Mas ei, eu tenho coração... estou vendo que voce está em uma situação difícil.");
                                    p.sendMessage(ChatColor.GREEN + "Tome essas esmeraldas, acho que vão te ajudar...");
                                    p.getInventory().addItem(new ItemStack(Material.EMERALD, 10));
                                }
                            }
                        } else {
                            p.sendMessage(ChatColor.GREEN + "Voce recebeu suas " + ChatColor.GOLD + vendas + " esmeraldas !");
                            ClanLand.econ.depositPlayer(p.getName(), vendas);
                            MercadoSQL.zeraRetorno(p);
                        }
                    }

                }
            } else if (ev.getInventory() != null && ev.getInventory().getName() != null && ev.getInventory().getName().equalsIgnoreCase(L.m("Vender Item"))) {

                ev.setCancelled(true);

                if (!ev.getClickedInventory().getName().equalsIgnoreCase(ev.getInventory().getName())) {
                    return;
                }

                if (ev.getCurrentItem() != null && ev.getCurrentItem().getType() != Material.AIR) {
                    ItemStack vouVender = ev.getCurrentItem();
                    /*
                     CustomItem custom = CustomItem.getItem(vouVender);
                     if (custom != null) {
                        
                     return;
                     }
                     */
                    p.closeInventory();
                    segundaParteMenuVenda(p, vouVender);
                }
            } else if (ev.getInventory() != null && ev.getInventory().getName() != null && ev.getInventory().getName().equalsIgnoreCase(L.m("Setar Preço"))) {
                ev.setCancelled(true);
                if (ev.getCurrentItem() != null && ev.getCurrentItem().getType() != Material.AIR) {
                    ItemMeta meta = ev.getCurrentItem().getItemMeta();
                    String opcao = meta.getDisplayName();
                    if (opcao == null) {
                        return;
                    }
                    if (opcao.contains(L.m("Preco"))) {
                        String[] split = opcao.split(":");
                        int preco = Integer.valueOf(split[1]);
                        int mod = 0;
                        if (ev.isLeftClick()) {
                            mod = -1;
                            if (ev.isShiftClick()) {
                                mod = -50;
                            }
                        } else if (ev.isRightClick()) {
                            mod = 1;
                            if (ev.isShiftClick()) {
                                mod = 50;
                            }
                        }
                        int novoPreco = preco + mod;
                        if (novoPreco < 1) {
                            p.sendMessage(ChatColor.GREEN + L.m("Preço Mínimo: 1"));
                            return;
                        }
                        meta.setDisplayName(L.m("§6§lPreco:") + novoPreco);
                        ev.getCurrentItem().setItemMeta(meta);

                        if (ev.isShiftClick()) {
                            ev.setResult(Event.Result.DENY);
                        }
                        p.playSound(p.getLocation(), Sound.UI_BUTTON_CLICK, 1, 0);
                    } else if (ChatColor.stripColor(opcao).equalsIgnoreCase(L.m("Vender por Esmeraldas"))) {

                        int maxVendas = 1;
                        if (p.hasPermission("kom.lord")) {
                            maxVendas = 10;
                        } else if (p.hasPermission("kom.vip")) {
                            maxVendas = 3;
                        }

                        if (MercadoSQL.QuantidadeProdutos(p) > maxVendas) {
                            p.sendMessage(ChatColor.RED + L.m("Voce so pode ter " + maxVendas + " items a venda por vez"));
                            if (!p.hasMetadata("vipmercado")) {
                                MetaShit.setMetaObject("vipmercado", p, true);
                                p.sendMessage(ChatColor.GREEN + "Voce será " + ChatColor.GOLD + "VIP" + ChatColor.GREEN + " e poderá vender mais items.");
                            }
                            return;
                        }

                        ItemStack emerald = ev.getInventory().getItem(5);
                        meta = emerald.getItemMeta();
                        String[] split = meta.getDisplayName().split(":");
                        int preco = Integer.valueOf(split[1]);
                        ItemStack aVenda = ev.getInventory().getItem(3);
                        ItemMeta metaVenda = aVenda.getItemMeta();

                        aVenda.setItemMeta(metaVenda);

                        //  p.getInventory().remove(aVenda);
                        boolean ok = KoM.gastaItem(p, aVenda);

                        if (ok) {
                            MercadoSQL.InserirProduto(new MarketItem(p.getUniqueId(), aVenda, preco, p.getName(), 0, false));
                            p.sendMessage(ChatColor.GREEN + L.m("Item colocado a venda por ") + preco);

                        } else {
                            p.sendMessage(ChatColor.RED + "Um erro ocorreu ! Fale com um admin e explique como causar este erro !");
                        }
                        p.closeInventory();
                    } else if (ChatColor.stripColor(opcao).equalsIgnoreCase(L.m("Vender por Cash"))) {

                        if (MercadoSQL.QuantidadeProdutos(p) > 2) {
                            p.sendMessage(ChatColor.RED + L.m("Voce so pode ter 2 items a venda por vez"));
                            return;
                        }

                        ItemStack emerald = ev.getInventory().getItem(5);
                        meta = emerald.getItemMeta();
                        String[] split = meta.getDisplayName().split(":");
                        int preco = Integer.valueOf(split[1]);
                        ItemStack aVenda = ev.getInventory().getItem(3);
                        ItemMeta metaVenda = aVenda.getItemMeta();
                        //List<String> lore = metaVenda.getLore()==null ? new ArrayList<String>() : new ArrayList<String>(metaVenda.getLore());
                        //lore.remove(lore.size()-1);
                        // metaVenda.setLore(lore);
                        aVenda.setItemMeta(metaVenda);

                        // ZIDEN : GASTAR O ITEM VENDENDO
                        /*
                         List<Itemzin> gastar = new ArrayList<Itemzin>();
                         Itemzin vendendo = new Itemzin(aVenda.getType(), aVenda.getData().getData());
                         vendendo.qtd = aVenda.getAmount();
                         gastar.add(vendendo);
                         if (!CraftMenus.gasta(p, gastar)) {
                         return;
                         }
                         */
                        //p.getInventory().remove(aVenda);
                        boolean ok = KoM.gastaItem(p, aVenda);

                        if (ok) {
                            MercadoSQL.InserirProduto(new MarketItem(p.getUniqueId(), aVenda, preco, p.getName(), 0, true));
                            p.sendMessage(ChatColor.GREEN + L.m("Item colocado a venda por ") + preco + " CASH");
                        } else {
                            p.sendMessage(ChatColor.RED + "Erro, contate um admin e explique como replicar este erro e ganhe recompensas !");
                        }

                        p.closeInventory();
                    }
                }
            }
        }
    }

}
