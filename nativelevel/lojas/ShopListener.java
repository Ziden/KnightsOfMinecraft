package nativelevel.lojas;

import java.util.List;
import nativelevel.sisteminhas.ClanLand;
import nativelevel.sisteminhas.KomSystem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class ShopListener
        extends KomSystem {

    public static ItemStack RemoveLore(ItemStack i) {
        ItemMeta meta = i.getItemMeta();
        if (meta.getLore().size() > 2) {
            List<String> lore = meta.getLore();
            lore.remove(1);
            lore.remove(0);
            meta.setLore(lore);
        } else {
            meta.setLore(null);
        }
        i.setItemMeta(meta);
        return i;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void InterageComBau(PlayerInteractEvent ev) {

        if (ev.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        String title = "§lLOJA";
        if (ev.getPlayer().getGameMode() == GameMode.CREATIVE) {
            return;
        }
        if ((ev.getClickedBlock().getType() != Material.CHEST) && (ev.getClickedBlock().getType() != Material.TRAPPED_CHEST)) {
            return;
        }
        if (ev.getClickedBlock().getRelative(BlockFace.DOWN).getType() != Material.SPONGE) {
            return;
        }
        if (ev.getClickedBlock().getRelative(0, -2, 0).getType() == Material.DIAMOND_BLOCK) {
            title = title + " VIP";
        }
        ev.setCancelled(true);
        Chest chest = (Chest) ev.getClickedBlock().getState();

        Inventory v = Bukkit.createInventory(null, chest.getInventory().getSize(), title);
        v.setContents(chest.getInventory().getContents());
        ev.getPlayer().openInventory(v);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void ProtegeItems(InventoryClickEvent ev) {
        if (ev.getInventory() == null) {
            return;
        }
        if ((ev.getInventory().getType() == InventoryType.PLAYER)
                && (ev.getCurrentItem() != null)
                && (ev.getCurrentItem().getItemMeta() != null)
                && (ev.getCurrentItem().getItemMeta().getLore() != null) && (ev.getCurrentItem().getItemMeta().getLore().size() > 0)
                && (((String) ev.getCurrentItem().getItemMeta().getLore().get(0)).contains("LOJA"))) {
            ev.getCurrentItem().setType(Material.AIR);
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void invEv(InventoryClickEvent ev) {
        if (!ev.getInventory().getTitle().startsWith("§lLOJA")) {
            return;
        }
        ev.setCancelled(true);

        boolean vip = false;
        if (ev.getInventory().getTitle().contains("VIP")) {
            vip = true;
        }
        if ((ev.getCurrentItem() == null) || (ev.getCurrentItem().getType() == Material.AIR)) {
            return;
        }
        if (ev.getCurrentItem().getItemMeta() == null) {
            return;
        }
        if (ev.getCurrentItem().getItemMeta().getLore() == null) {
            return;
        }
        Player p = (Player) ev.getWhoClicked();
        if (p.isOp()) {
            p.sendMessage(ev.getAction().name());
        }
        ev.setCancelled(true);
        if ((vip) && (!p.hasPermission("instamc.vip"))) {
            p.sendMessage("§b§lSOMENTE VIPS PODEM COMPRAR NESTA LOJA!");
            return;
        }
        List<String> l = ev.getCurrentItem().getItemMeta().getLore();
        for (String lore : l) {
            lore = ChatColor.stripColor(lore);
            String[] str = lore.split(" ");
            if (str[0].startsWith("Compra")) {
                int preco = Integer.parseInt(str[1]);
                if (p.getInventory().firstEmpty() == -1) {
                    p.sendMessage("§cSeu inventario esta cheio!");
                    return;
                }
                if (!ClanLand.econ.has(p.getName(), preco)) {
                    p.sendMessage("§cVoce nao possui dinheiro para comprar este item!");
                    return;
                }
                
                //FactionMoney.gasta(p, preco + 0.0D);
                ClanLand.econ.withdrawPlayer(p.getName(), preco);
                ItemStack item = ev.getCurrentItem().clone();
                item = RemoveLore(item);
                p.getInventory().addItem(new ItemStack[]{item});
                p.sendMessage("§cItem comprado!");
                return;
            }
            if (str[0].startsWith("Venda")) {
                int preco = Integer.parseInt(str[1]);

                ItemStack item = ev.getCurrentItem().clone();
                if (!containsInv(p, item.getType(), item.getDurability(), item.getAmount())) {
                    p.sendMessage("§cVoce nao possue items suficiente para vender aqui!");
                    return;
                }
                removeInventoryItems(p.getInventory(), item.getType(), item.getDurability(), item.getAmount());

                ClanLand.econ.depositPlayer(p.getName(), preco);
                
                //FactionMoney.ganha(p, preco + 0.0D);

                p.sendMessage("§cItem Vendido! Ganhou: " + preco + " Coins");
                return;
            }
        }
    }

    public static boolean containsInv(Player p, Material m, short data, int qtd) {
        if (qtd == 0) {
            return true;
        }
        Inventory i = p.getInventory();
        int x = 0;
        for (ItemStack it : i.getContents()) {
            if ((it != null) && (it.getType() == m) && (it.getItemMeta().getLore() == null) && (it.getItemMeta().getDisplayName() == null) && (it.getDurability() == data)) {
                x += it.getAmount();
            }
        }
        return x >= qtd;
    }

    public static void removeInventoryItems(Inventory inv, Material type, short data, int amount) {
        for (ItemStack is : inv.getContents()) {
            if ((is != null) && (is.getType() == type) && (is.getDurability() == data)) {
                int slot = inv.first(is.getType());
                int newamount = is.getAmount() - amount;
                if (newamount > 0) {
                    is.setAmount(newamount);
                } else {
                    inv.setItem(slot, null);
                    amount = -newamount;
                    if (amount == 0) {
                        break;
                    }
                }
            }
        }
    }
}
