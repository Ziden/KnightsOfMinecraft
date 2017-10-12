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

import nativelevel.utils.TitleAPI;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import nativelevel.sisteminhas.ClanLand;
import nativelevel.Custom.CustomItem;
import nativelevel.Jobs;
import nativelevel.KoM;
import nativelevel.Lang.L;
import nativelevel.config.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class ValeSorteio extends CustomItem {

    public static boolean ativo = false;

    private static void SoltaFoguete(Player player) {
        Firework fw = (Firework) player.getWorld().spawn(player.getLocation(), Firework.class);
        FireworkMeta fm = fw.getFireworkMeta();
        int fType = (int) (Math.random() * 6);
        FireworkEffect.Type type = null;
        Random r = new Random();
        switch (fType) {
            default:
            case 1:
                type = type.BALL;
                break;
            case 2:
                type = type.BALL_LARGE;
                break;
            case 3:
                type = type.BURST;
                break;
            case 4:
                type = type.CREEPER;
                break;
            case 5:
                type = type.STAR;
        }
        int c1i = (int) (Math.random() * 17);
        int c2i = (int) (Math.random() * 17);
        Color c1 = getColour(c1i);
        Color c2 = getColour(c2i);
        FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(c1).withFade(c2).with(type).trail(r.nextBoolean()).build();
        fm.addEffect(effect);
        int power = (int) (Math.random() * 3);
        fm.setPower(power);
        fw.setFireworkMeta(fm);
    }

    public static Color getColour(int c) {
        switch (c) {
            default:
            case 1:
                return Color.AQUA;
            case 2:
                return Color.BLACK;
            case 3:
                return Color.BLUE;
            case 4:
                return Color.FUCHSIA;
            case 5:
                return Color.GRAY;
            case 6:
                return Color.GREEN;
            case 7:
                return Color.LIME;
            case 8:
                return Color.MAROON;
            case 9:
                return Color.NAVY;
            case 10:
                return Color.OLIVE;
            case 11:
                return Color.PURPLE;
            case 12:
                return Color.RED;
            case 13:
                return Color.SILVER;
            case 14:
                return Color.TEAL;
            case 15:
                return Color.WHITE;
            case 16:
                return Color.YELLOW;
        }
    }

    public ValeSorteio() {
        super(Material.PAPER, "Documento de Sorteio", "Participe do Sorteio", CustomItem.RARO);
    }

    public static ArrayList<UUID> players = new ArrayList<UUID>();

    public static String nomeSorteio = null;

    public static void fazSorteio() {
        nomeSorteio = null;
        boolean roda = true;

        String ganhou = null;

        if (players.size() == 0) {
            players.clear();
            ativo = false;
            return;
        }

        while (roda) {
            int random = Jobs.rnd.nextInt(players.size());
            Player sorteado = Bukkit.getPlayer(players.get(random));
            if (sorteado != null) {
                roda = false;
                sorteado.sendMessage(ChatColor.AQUA + "[Sorteio] Você ganhou o sorteio !! Aguarde, seus premios irão aparecer no seu inventario já já !");
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.sendMessage(ChatColor.AQUA + "[Sorteio] " + sorteado.getName() + " ganhou o sorteio !!!");

                }
                ganhou = sorteado.getName();
                players.clear();
                break;
            } else {
                OfflinePlayer op = Bukkit.getOfflinePlayer(players.get(random));
                if (op != null) {
                    roda = false;
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.sendMessage(ChatColor.AQUA + "[Sorteio] " + op.getName() + " ganhou o sorteio !!!");
                    }
                    ganhou = op.getName();
                    players.clear();
                    break;
                }
            }
        }

        for (UUID u : players) {
            Player p = Bukkit.getPlayer(u);
            if (p != null) {
                SoltaFoguete(p);
                p.sendMessage(ChatColor.AQUA + "ganhou o sorteio");
            }
        }
    }

    public static ItemStack cria(String nome) {
        ItemStack item = CustomItem.getItem(ValeSorteio.class).generateItem();
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        List<String> lore2 = new ArrayList<String>();
        lore2.add(ChatColor.YELLOW + nome);
        for (String s : lore) {
            lore2.add(s);
        }
        meta.setLore(lore2);
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public boolean onItemInteract(Player player) {

        ItemMeta item = player.getItemInHand().getItemMeta();
        String nome = ChatColor.stripColor(item.getLore().get(0));

        if (!nome.equalsIgnoreCase(nomeSorteio)) {
            player.sendMessage(ChatColor.RED + "Este ticket nao vale para este sorteio");
            return true;
        }
        players.add(player.getUniqueId());
        player.sendMessage(ChatColor.GREEN + "Voce está agora participando do sorteio.");
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p != player) {
                p.sendMessage(ChatColor.AQUA + "[Sorteio] " + player.getName() + " usou um ticket de sorteio.");
            }
        }
        if (player.getItemInHand().getAmount() == 1) {
            player.setItemInHand(null);
        } else {
            player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
        }
        player.updateInventory();
        return true;
    }
}
