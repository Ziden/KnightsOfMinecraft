/*

 ╭╮╭━╮╱╱╭━╮╭━╮
 ┃┃┃╭╯╱╱┃┃╰╯┃┃
 ┃╰╯╯╭━━┫╭╮╭╮┃
 ┃╭╮┃┃╭╮┃┃┃┃┃┃
 ┃┃┃╰┫╰╯┃┃┃┃┃┃
 ╰╯╰━┻━━┻╯╰╯╰╯

 Desenvolvedor: ZidenVentania
 Colaboradores: NeT32, Gabripj, Feldmann
 Patrocionio: InstaMC

 */
package nativelevel.Custom.Items;

import java.util.ArrayList;
import java.util.List;
import nativelevel.sisteminhas.ClanLand;
import nativelevel.Custom.CustomItem;
import nativelevel.KoM;
import nativelevel.Lang.L;
import nativelevel.config.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class DoubleXP extends CustomItem {

    public static boolean ativo = false;

    public DoubleXP() {
        super(Material.PAPER, "Documento de XP Dobrada", "2 x EXP para todos", CustomItem.RARO);
    }

    @Override
    public boolean onItemInteract(Player player) {
        if (ativo) {
            player.sendMessage(ChatColor.RED + L.m("O double XP ja esta ativo, aguarde terminar !"));
            return true;
        } else {
            ativo = true;
            if (player.getItemInHand().getAmount() == 1) {
                player.setItemInHand(null);
            } else {
                player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
            }
            int ganhou = Config.getDoacoes();
            int ganhout = ganhou;

            ClanLand.econ.depositPlayer(player.getName(), ganhou);
            Config.setDoacao(0);
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p != null) {
                    p.sendMessage(ChatColor.AQUA + L.m("-> % ativou um double XP !!!", player.getName()));
                    p.sendMessage(ChatColor.AQUA + L.m("-> Todos ganharao 2x EXP por MEIA HORA !! Aproveitem !"));
                    p.sendMessage(ChatColor.AQUA + L.m("-> Todos devem agradecer %", player.getName()));
                    p.sendMessage(ChatColor.AQUA + L.m("-> Ele ganhou " + ganhout + " ativando o Double de Jabu"));
                }
            }
            Runnable termina = new Runnable() {
                public void run() {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (p != null) {
                            p.sendMessage(ChatColor.AQUA + L.m("[ O Double XP Terminou !!! ]"));
                            ativo = false;
                        }
                    }
                }
            };
            Bukkit.getScheduler().scheduleSyncDelayedTask(KoM._instance, termina, 20 * 60 * 30);
        }
        return true;
    }

    /*
     public static List<ItemStack> getEsmeraldas(int valor) {
     int normal = valor;
     int blocos = 0;
     int especial = 0;
     while (normal >= 576) {
     especial++;
     normal -= 576;

     }
     while (normal >= 9) {
     blocos++;
     normal -= 9;
     }
     List<ItemStack> itens = new ArrayList<>();
     while (especial >= 64) {
     especial -= 64;
     itens.add(getItemEsmeralda576(64));
     }
     if (especial > 0) {
     itens.add(getItemEsmeralda576(especial));

     }
     while (blocos >= 64) {
     blocos -= 64;
     itens.add(new ItemStack(Material.EMERALD_BLOCK, 64));
     }
     if (blocos > 0) {
     itens.add(new ItemStack(Material.EMERALD_BLOCK, blocos));
     }
     while (normal >= 64) {
     normal -= 64;
     itens.add(new ItemStack(Material.EMERALD, 64));
     }
     if (normal > 0) {
     itens.add(new ItemStack(Material.EMERALD, normal));
     }
     return itens;
     }
     */

    /*
     public static ItemStack getItemEsmeralda576(int qtd) {
     ItemStack i = new ItemStack(Material.EMERALD, qtd);
     ItemMeta meta = i.getItemMeta();
     List<String> lore = meta.getLore();
     if(lore==null)
     lore = new ArrayList<String>(); 
     meta.setDisplayName("§r§8♦ §6Esmeralda");
     lore.add("§7Esta esmeralda tem o valor de:");
     lore.add("§7576 Esmeraldas (64 Blocos)");
     lore.add("§7Season 1, por PixelMC");
     i.setItemMeta(meta);
     return i;
     }
     */
}
