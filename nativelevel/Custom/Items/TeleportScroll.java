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

import java.util.Arrays;
import java.util.List;
import me.fromgate.playeffect.PlayEffect;
import me.fromgate.playeffect.VisualEffect;
import nativelevel.Custom.CustomItem;
import nativelevel.MetaShit;
import nativelevel.KoM;
import nativelevel.Lang.L;
import nativelevel.integration.BungeeCordKom;
import nativelevel.utils.BungLocation;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TeleportScroll extends CustomItem {

    public TeleportScroll() {
        super(Material.MAP,"Pergaminho de Teleporte","Um magico pergaminho..", CustomItem.INCOMUM);
    }

    public static ItemStack createTeleportScroll(Location l, boolean multiWorld, int cargas) {
        ItemStack scroll = new ItemStack(Material.PAPER, 1);
        ItemMeta meta = scroll.getItemMeta();
        meta.setLore(Arrays.asList(new String[]{
            ChatColor.BLACK + ":" + l.getWorld().getName(),
            ChatColor.BLACK + ":" + l.getBlockX(),
            ChatColor.BLACK + ":" + l.getBlockY(),
            ChatColor.BLACK + ":" + l.getBlockZ(),
            ChatColor.BLACK + ":" + Bukkit.getServer().getPort(), ChatColor.BLACK + ":" + (multiWorld ? "S" : "N"),
            ChatColor.GOLD + "Cargas:" + ChatColor.GREEN + cargas + ChatColor.GOLD + "/" + ChatColor.GREEN + cargas,
            ChatColor.BLACK + ":Pergaminho de Teleporte",}
        ));
        meta.setDisplayName(ChatColor.GOLD + "Me Renomeie :)");
        scroll.setItemMeta(meta);
        return scroll;
    }
    
    public static ItemStack createTeleportScroll(BungLocation l, boolean multiWorld, int cargas, String nome) {
        ItemStack scroll = new ItemStack(Material.PAPER, 1);
        ItemMeta meta = scroll.getItemMeta();
        meta.setLore(Arrays.asList(new String[]{
            ChatColor.BLACK + ":" + l.mundo,
            ChatColor.BLACK + ":" + l.x,
            ChatColor.BLACK + ":" + l.y,
            ChatColor.BLACK + ":" + l.z,
            ChatColor.BLACK + ":" + Bukkit.getServer().getPort(), ChatColor.BLACK + ":" + (multiWorld ? "S" : "N"),
            ChatColor.GOLD + "Cargas:" + ChatColor.GREEN + cargas + ChatColor.GOLD + "/" + ChatColor.GREEN + cargas,
            ChatColor.BLACK + ":Pergaminho de Teleporte",}
        ));
        meta.setDisplayName(ChatColor.WHITE + nome);
        scroll.setItemMeta(meta);
        return scroll;
    }

    public static void use(final Player p) {
        ItemStack scroll = p.getItemInHand();
        if (scroll != null && scroll.getType() == Material.PAPER) {
            p.playEffect(EntityEffect.SHEEP_EAT);
            if (KoM.debugMode) {
                KoM.log.info("Usando pergaminho de teleporte !");
            }
            if(scroll.getAmount()>1) {
                p.sendMessage(ChatColor.RED+L.m("Tire o item do stack para usar !"));
                return;
            }
            if(p.hasMetadata("Pergaminho")) {
                p.sendMessage(ChatColor.RED+L.m("Aguarde para usar outro pergaminho !"));
                return;
            }
            ItemMeta meta = scroll.getItemMeta();
            //  if(meta.getLore().size()==8) {
            //World w = Bukkit.getWorld(meta.getLore().get(0).split(":")[1]);
            
            //final Location l = new Location(w, Integer.valueOf(meta.getLore().get(1).split(":")[1]), Integer.valueOf(meta.getLore().get(2).split(":")[1]), Integer.valueOf(meta.getLore().get(3).split(":")[1]));
            
            final String mundo = meta.getLore().get(0).split(":")[1];
            final double x = Double.valueOf(meta.getLore().get(1).split(":")[1]);
            final double y = Double.valueOf(meta.getLore().get(2).split(":")[1]);
            final double z = Double.valueOf(meta.getLore().get(3).split(":")[1]);
            String multiworld = meta.getLore().get(5).split(":")[1];
            String cargas = ChatColor.stripColor(meta.getLore().get(6).split(":")[1]);
            String[] cargaTotal = cargas.split("/");
            int tem = Integer.valueOf(cargaTotal[0]);
            int max = Integer.valueOf(cargaTotal[1]);
            if (!multiworld.equalsIgnoreCase("S")) {
                if (!mundo.equalsIgnoreCase(p.getWorld().getName())) {
                    p.sendMessage(ChatColor.RED + L.m("Este pergaminho aponta para outro continente, e nao eh forte suficiente para te levar la."));
                    return;
                }
            }

            Runnable r = new Runnable() {
                public void run() {
                    BungeeCordKom.tp(p, mundo, x, y, z, 0,0);
                    p.sendMessage(ChatColor.DARK_PURPLE + "* poof *");
                    PlayEffect.play(VisualEffect.FIREWORKS_SPARK, p.getLocation(), "color:purple type:burst");
                    p.removeMetadata("Pergaminho", KoM._instance);
                }
            };

            if (tem == 1) {
                p.setItemInHand(new ItemStack(Material.AIR, 1));
                p.sendMessage(ChatColor.AQUA +L.m( "* O pergaminho se desfez em farelos *"));
            } else {
                if(!p.hasPermission("kom.lord")) {
                    tem = tem - 1;
                    List<String> lore = meta.getLore();
                    lore.remove(6);
                    lore.add(6, ChatColor.GOLD + "Cargas:" + ChatColor.GREEN + tem + ChatColor.GOLD + "/" + ChatColor.GREEN + max);
                    p.sendMessage(ChatColor.AQUA + L.m("* O papel do pergaminho envelheceu e ficou com  % cargas *",tem));
                    meta.setLore(lore);
                    scroll.setItemMeta(meta);
                }
            }

            int idTask = Bukkit.getScheduler().scheduleSyncDelayedTask(KoM._instance, r, 20 * 5);
            MetaShit.setMetaString("Pergaminho", p, "" + idTask);
            p.sendMessage(ChatColor.GREEN + L.m("Aguarde 5 segundos enquanto voce foca no teleporte !"));

              // }
        }
    }

    @Override
    public boolean onItemInteract(Player p) {
        use(p);
        return true;
    }
}
