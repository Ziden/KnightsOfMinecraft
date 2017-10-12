package nativelevel.mercadinho;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import nativelevel.Lang.L;
import nativelevel.mercadinho.common.MarketItem;
import nativelevel.mercadinho.database.MercadoSQL;
import nativelevel.utils.CategoriaUtils.CategoriaItem;
import nativelevel.utils.UtilsCiro;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


public class MarketManager {

    public static HashMap<UUID, HashMap<Integer, Inventory>> ListaLojaPlayers = new HashMap();
    public static HashMap<UUID, HashMap<String, UUID>> ListaIDOwners = new HashMap();

    //Msgs
    public static String ProximaPag = L.m("§a§lProxima Pagina");
    public static String PagAnterior = L.m("§c§lPagina Anterior");
    public static String Sair = L.m("§4§lSair");
    public static String Vips = L.m("§6§lLoja VIPS");

    public static void OpenMainLoja(Player p, CategoriaItem categoria) {
        List<MarketItem> lista = MercadoSQL.CarregaTodosProdutosCatalogo(categoria);
        OpenMainLoja(p, lista);
    }
    
    public static void OpenMainLoja(Player p, List<MarketItem> lista) {
        double aux = lista.size() / 45.0;
        int npaginas = 0;
        if (UtilsCiro.isInt(aux)) {
            npaginas = (int) aux;
        } else {
            npaginas = (int) aux;
            npaginas++;
        }
        if (lista.size() == 0) {
            p.sendMessage(L.m("§cNenhum item a venda..."));
            return;
        }
        HashMap<Integer, Inventory> Loja = new HashMap();
        ListaIDOwners.put(p.getUniqueId(), new HashMap<String, UUID>());
        for (int i = 1; i <= npaginas; i++) {
            Loja.put(i, Bukkit.createInventory(null, 54, L.m("#Loja Global Pag: ") + i));
            if (i != 1) {
                Loja.get(i).setItem(45, ItemUtils.CreateStack(Material.SUGAR_CANE, (byte) 0, 1, PagAnterior));
            }
            if (npaginas > i) {
                Loja.get(i).setItem(53, ItemUtils.CreateStack(Material.SUGAR_CANE, (byte) 0, 1, ProximaPag));
            }
            Loja.get(i).setItem(47, ItemUtils.CreateStack(Material.DIAMOND_BLOCK, (byte) 0, 1, Vips));

            Loja.get(i).setItem(49, ItemUtils.CreateStack(Material.BONE, (byte) 0, 1, Sair));
            int cont = 0;

            Iterator<MarketItem> itens = lista.iterator();
            while (itens.hasNext()) {
                MarketItem it = itens.next();

                Loja.get(i).setItem(cont, it.getNamedItem());
                ListaIDOwners.get(p.getUniqueId()).put(i + ";" + cont, it.dono);
                itens.remove();
                cont++;
                if (cont >= 45) {
                    break;
                }

            }
        }
        ListaLojaPlayers.put(p.getUniqueId(), Loja);
        p.openInventory(ListaLojaPlayers.get(p.getUniqueId()).get(1));
    }

    public static void openSelfShop(Player p, String nick) {

        List<MarketItem> lista = MercadoSQL.CarregaProdutosPorNick(nick, p.getName().equalsIgnoreCase(nick));

        double aux = lista.size() / 45.0;
        int npaginas = 0;
        if (UtilsCiro.isInt(aux)) {
            npaginas = (int) aux;
        } else {
            npaginas = (int) aux;
            npaginas++;
        }
        if (lista.size() == 0) {
            p.sendMessage(L.m("§cNenhum item a venda deste jogador..."));
            return;
        }
        HashMap<Integer, Inventory> Loja = new HashMap();
        ListaIDOwners.put(p.getUniqueId(), new HashMap<String, UUID>());
        for (int i = 1; i <= npaginas; i++) {
            Loja.put(i, Bukkit.createInventory(null, 54, L.m("#Loja " + nick + ": " + i)));
            if (i != 1) {
                Loja.get(i).setItem(45, ItemUtils.CreateStack(Material.SUGAR_CANE, (byte) 0, 1, PagAnterior));
            }
            if (npaginas > i) {
                Loja.get(i).setItem(53, ItemUtils.CreateStack(Material.SUGAR_CANE, (byte) 0, 1, ProximaPag));
            }
            Loja.get(i).setItem(49, ItemUtils.CreateStack(Material.BONE, (byte) 0, 1, Sair));
            int cont = 0;

            Iterator<MarketItem> itens = lista.iterator();
            while (itens.hasNext()) {
                MarketItem it = itens.next();
                Loja.get(i).setItem(cont, it.getNamedItem());
                ListaIDOwners.get(p.getUniqueId()).put(i + ";" + cont, it.dono);
                itens.remove();
                cont++;
                if (cont >= 45) {
                    break;
                }
            }
        }
        ListaLojaPlayers.put(p.getUniqueId(), Loja);
        p.openInventory(ListaLojaPlayers.get(p.getUniqueId()).get(1));
    }

    public static HashMap<Integer, String> cachenome = new HashMap();

    public static void OpenLojaByName(Player p, String nome) {

        List<MarketItem> lista = MercadoSQL.CarreProdutosPorNome(nome);

        int id = cachenome.size();
        cachenome.put(id, nome);
        double aux = lista.size() / 45.0;
        int npaginas = 0;
        if (UtilsCiro.isInt(aux)) {
            npaginas = (int) aux;
        } else {
            npaginas = (int) aux;
            npaginas++;
        }
        if (lista.size() == 0) {
            p.sendMessage(L.m("§cNenhum item encontrado em sua busca..."));
            return;
        }
        HashMap<Integer, Inventory> Loja = new HashMap();
        ListaIDOwners.put(p.getUniqueId(), new HashMap<String, UUID>());
        for (int i = 1; i <= npaginas; i++) {
            Loja.put(i, Bukkit.createInventory(null, 54, L.m("#Loja " + id + ": " + i)));
            if (i != 1) {
                Loja.get(i).setItem(45, ItemUtils.CreateStack(Material.SUGAR_CANE, (byte) 0, 1, PagAnterior));
            }
            if (npaginas > i) {
                Loja.get(i).setItem(53, ItemUtils.CreateStack(Material.SUGAR_CANE, (byte) 0, 1, ProximaPag));
            }
            Loja.get(i).setItem(49, ItemUtils.CreateStack(Material.BONE, (byte) 0, 1, Sair));
            int cont = 0;

            Iterator<MarketItem> itens = lista.iterator();
            while (itens.hasNext()) {
                MarketItem it = itens.next();
                Loja.get(i).setItem(cont, it.getNamedItem());
                ListaIDOwners.get(p.getUniqueId()).put(i + ";" + cont, it.dono);
                itens.remove();
                cont++;
                if (cont >= 45) {
                    break;
                }
            }
        }
        ListaLojaPlayers.put(p.getUniqueId(), Loja);
        p.openInventory(ListaLojaPlayers.get(p.getUniqueId()).get(1));
    }

}
