package nativelevel.lojas;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CmdSetPreco
        implements CommandExecutor {

    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] args) {
        if (!cs.isOp()) {
            return true;
        }
        if (args.length == 0) {
            cs.sendMessage("§cUtilize: §f/setpreco <Compra | Venda> <Valor>");
            cs.sendMessage("§cCompra - O preco que o player vai comprar");
            cs.sendMessage("§cVenda - o preco que o player vai vender para loja");
            return true;
        }
        Player p = (Player) cs;
        if ((p.getItemInHand() == null) || (p.getItemInHand().getType() == Material.AIR)) {
            cs.sendMessage("§cVoce precisa ter algo em sua mao para setar pre§o!");
            return true;
        }
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("compra")) {
                int i = 0;
                try {
                    i = Integer.parseInt(args[1]);
                } catch (Exception e) {
                    cs.sendMessage("§cO segundo argumento deve ser um valor inteiro!");
                    return true;
                }
                ItemMeta in = p.getItemInHand().getItemMeta();
                if (!in.hasLore()) {
                    List l = new ArrayList();
                    l.add("§6§lLoja");
                    l.add("§2Compra: §6" + i);

                    in.setLore(l);
                    p.getItemInHand().setItemMeta(in);
                    p.sendMessage("§cAlterado!");
                    return true;
                }
                List<String> lore = in.getLore();
                lore.add(0, "§6§lLoja");
                lore.add(1, "§2Compra: §6" + i);
                in.setLore(lore);
                p.sendMessage("§cAlterado!");
                if (ChatColor.stripColor((String) in.getLore().get(0)).equalsIgnoreCase("Loja")) {
                    List ls = in.getLore();
                    ls.set(1, "§2Compra: §6" + i);
                    in.setLore(ls);
                    p.getItemInHand().setItemMeta(in);
                    p.sendMessage("§cAlterado!");
                    return true;
                }
            } else if (args[0].equalsIgnoreCase("venda")) {
                int i = 0;
                try {
                    i = Integer.parseInt(args[1]);
                } catch (Exception e) {
                    cs.sendMessage("§cO segundo argumento deve ser um valor inteiro!");
                    return true;
                }
                ItemMeta in = p.getItemInHand().getItemMeta();
                if (!in.hasLore()) {
                    List l = in.getLore();
                    if (l == null) {
                        l = new ArrayList();
                    }
                    l.add("§6§lLoja");

                    l.add("§2Venda: §6" + i);
                    in.setLore(l);
                    p.getItemInHand().setItemMeta(in);
                    p.sendMessage("§cAlterado!");
                } else {
                    List l = in.getLore();
                    l.add("§6§lLoja");

                    l.add("§2Venda: §6" + i);
                    in.setLore(l);
                    p.getItemInHand().setItemMeta(in);
                    p.sendMessage("§cAlterado!");
                }
                if (ChatColor.stripColor((String) in.getLore().get(0)).equalsIgnoreCase("Loja")) {
                    List ls = in.getLore();
                    ls.set(1, "§2Venda: §6" + i);
                    in.setLore(ls);
                    p.getItemInHand().setItemMeta(in);
                    p.sendMessage("§cAlterado!");
                    return true;
                }
            }
        }
        return true;
    }
}
