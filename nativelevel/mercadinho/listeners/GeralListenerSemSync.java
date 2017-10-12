package nativelevel.mercadinho.listeners;

//<editor-fold defaultstate="collapsed" desc="imports">
import cashgame.loja.TipoCompra;
import cashgame.principal.VipManiaEpic;
import java.util.UUID;
import nativelevel.KoM;
import nativelevel.Lang.L;
import nativelevel.Lang.LangMinecraft;
import nativelevel.mercadinho.MarketManager;
import nativelevel.mercadinho.common.MarketItem;
import nativelevel.mercadinho.database.MercadoSQL;
import nativelevel.sisteminhas.ClanLand;
import nativelevel.sisteminhas.KomSystem;
import nativelevel.titulos.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.inventory.ItemStack;
//</editor-fold>

/**
 *
 * @author Gabriel
 *
 */

public class GeralListenerSemSync extends KomSystem {

    @Override
    public void onEnable() {
        MercadoSQL.InitMysql();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void InvClick(final InventoryClickEvent ev) {

        if (ev.getInventory() == null) {
            return;
        }
        if (ev.getCurrentItem() == null || ev.getCurrentItem().getType() == Material.AIR) {
            return;
        }
        if (!ev.getInventory().getName().toLowerCase().startsWith(L.m("#loja"))) {
            return;
        }

        Player p = (Player) ev.getWhoClicked();
        ev.setCancelled(true);
        if (ev.getCurrentItem().getItemMeta() != null && ev.getCurrentItem().getItemMeta().hasDisplayName()) {
            if (ItemUtils.getItemName(ev.getCurrentItem()).equalsIgnoreCase(MarketManager.Sair)) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(KoM._instance, new Runnable() {
                    @Override
                    public void run() {
                        ev.getWhoClicked().closeInventory();
                    }
                }, 1);
                return;
            }
        }
        int paginaatual = Integer.parseInt(ev.getInventory().getName().split(":")[1].replace(" ", ""));
        if (ev.getCurrentItem().getItemMeta() != null && ev.getCurrentItem().getItemMeta().hasDisplayName()) {
            if (ItemUtils.getItemName(ev.getCurrentItem()).equalsIgnoreCase(MarketManager.ProximaPag)) {
                ev.getWhoClicked().openInventory(MarketManager.ListaLojaPlayers.get(p.getUniqueId()).get(paginaatual + 1));
                return;
            }
            if (ItemUtils.getItemName(ev.getCurrentItem()).equalsIgnoreCase(MarketManager.PagAnterior)) {
                ev.getWhoClicked().openInventory(MarketManager.ListaLojaPlayers.get(ev.getWhoClicked().getUniqueId()).get(paginaatual - 1));
                return;
            }
            if (ItemUtils.getItemName(ev.getCurrentItem()).equalsIgnoreCase(MarketManager.Vips)) {
                return;
            }
            if (ev.getInventory().getName().toLowerCase().startsWith(L.m("#loja vips:"))) {
                String name = ChatColor.stripColor(ev.getCurrentItem().getItemMeta().getDisplayName());
                p.chat(L.m("/loja ver ") + name);
                return;
            }
        }

        UUID DonoItemClick = MarketManager.ListaIDOwners.get(p.getUniqueId()).get(paginaatual + ";" + ev.getSlot());
        double preco = 0;
        String nickdono = "";
        boolean vip = false;
        int shopid = 0;
        for (String inf : ItemUtils.GetLore(ev.getCurrentItem())) {
            if (ChatColor.stripColor(inf).toLowerCase().startsWith(L.m("dono:"))) {
                nickdono = ChatColor.stripColor(inf).split(":")[1];
            } else if (ChatColor.stripColor(inf).toLowerCase().startsWith(L.m("preço:"))) {
                preco = Double.parseDouble(ChatColor.stripColor(inf).split(":")[1]);
            } else if (ChatColor.stripColor(inf).toLowerCase().startsWith(L.m("shopid:"))) {
                shopid = Integer.parseInt(ChatColor.stripColor(inf).split(":")[1].trim());
            }
            if (inf.contains("CASH")) {
                vip = true;
            }
        }
        if (shopid == 0) {
            p.closeInventory();
            return;
        }
        if (p.getUniqueId().toString().equalsIgnoreCase(DonoItemClick.toString())) {
            if (ev.isShiftClick() || ItemUtils.GetLore(ev.getCurrentItem()).contains(MarketItem.itemvencido)) {
                if (p.getInventory().firstEmpty() != -1) {
                    MarketItem mi = new MarketItem(DonoItemClick, ev.getCurrentItem(), preco, nickdono, shopid, vip);
                    if (MercadoSQL.ExisteProduto(mi) && MercadoSQL.RemoverProduto(mi)) {
                        p.getInventory().addItem(mi.getClearItem());
                        p.sendMessage(L.m("§2[Mercado] §cItem removido da venda!"));
                    } else {
                        p.sendMessage(L.m("§2[Mercado] §cEste produto nao esta mais na lista!"));
                    }
                    p.closeInventory();
                    return;
                } else {
                    p.sendMessage(L.m("§2[Mercado] §cVoce esta com inventario cheio!"));
                    return;
                }
            } else {
                p.closeInventory();
                p.sendMessage(L.m("§2[Mercado] §cUtilize Shift-Click p/ remover seu item a venda!"));
                return;
            }
        }

        if ((!vip && ClanLand.econ.getBalance(p.getName()) >= preco) || (vip && VipManiaEpic.databaseCodigos.has(p, (int) preco))) {
            if (p.getInventory().firstEmpty() != -1) {
                MarketItem mi = new MarketItem(DonoItemClick, ev.getCurrentItem(), preco, nickdono, shopid, vip);
                if (MercadoSQL.ExisteProduto(mi) && MercadoSQL.RemoverProduto(mi)) {

                    if (!vip) {
                        ClanLand.econ.withdrawPlayer(p.getName(), (int) preco);
                    } else {
                        VipManiaEpic.databaseCodigos.gastaCash(p, TipoCompra.ITEM, "Mercado:" + ev.getCurrentItem().getType().name(), 1,(int)preco);
                    }

                    Player recebeu = Bukkit.getPlayer(DonoItemClick);
                    // se o dono do item ta online vamo avisar ele q vendeu o item dele :D
                    ItemStack comprando = mi.getClearItem();
                    String nomeItem = LangMinecraft.get().get(comprando);

                    if (!vip) {
                        MercadoSQL.addRetorno(DonoItemClick, (int) preco);
                    } else {
                        VipManiaEpic.databaseCodigos.addCash(DonoItemClick.toString(), (int) preco);
                    }
                    
                    p.getInventory().addItem(comprando);
                    p.sendMessage(L.m("§2[Mercado] §aItem comprado com sucesso!"));
                    String name = null;
                    if (mi.getClearItem().hasItemMeta() && mi.getClearItem().getItemMeta().hasDisplayName()) {
                        name = mi.getClearItem().getItemMeta().getDisplayName();
                    } else {
                        name = LangMinecraft.get().get(mi.getClearItem());
                    }
                    Player dono = Bukkit.getPlayer(mi.nickdono);
                    if (dono != null ) {
                        dono.sendMessage(L.m("§2[Mercado] §3Você vendeu §e") + mi.getClearItem().getAmount() + " §c" + name + L.m(" §3para §c") + p.getName() + L.m("§3 e ganhou: §6$") + preco + (vip ? " CASH" : " Esmeraldas!"));
                        if(!vip)
                            dono.sendMessage("§2[Mercado] §3Vá até o mercado para receber suas esmeraldas");
                    }

                } else {
                    p.sendMessage(L.m("§2[Mercado] §cEste produto nao esta mais na lista!"));
                }
                p.closeInventory();
                return;
            } else {
                p.sendMessage(L.m("§2[Mercado] §cVoce esta com inventario cheio!"));
                return;
            }
        } else {
            p.closeInventory();
            p.sendMessage(L.m("§2[Mercado] §cVoce nao possui "+(vip?"CASH":"Esmeraldas")+" para comprar este item!"));
            return;
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void PlayerJoinEvent(PlayerLoginEvent event) {
        MercadoSQL.DevolveItem(event.getPlayer());
    }

}
