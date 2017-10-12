package instamc.coders.libkom.listeners;

import instamc.coders.libkom.InstaMCLibKom;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;
import me.asofold.bpl.simplyvanish.SimplyVanish;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;
import net.sacredlabyrinth.phaed.simpleclans.managers.ClanManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class ChatListener implements Listener {

    HashMap<UUID, String> lastmsg = new HashMap();
    HashMap<UUID, Long> delay = new HashMap();

    @EventHandler(priority = EventPriority.MONITOR)
    public void ascync(AsyncPlayerChatEvent ev) {
        if (ev.isCancelled()) {
            return;
        }
        Player p = ev.getPlayer();
        if (this.delay.containsKey(p.getUniqueId())) {
            if (((Long) this.delay.get(p.getUniqueId())).longValue() > System.currentTimeMillis()) {
                p.sendMessage("§aEspere um pouco para falar novamente!");
                this.delay.put(p.getUniqueId(), Long.valueOf(System.currentTimeMillis() + 1500L));
                ev.setCancelled(true);
                return;
            }
        }
        if (this.lastmsg.containsKey(p.getUniqueId())) {
            String last = (String) this.lastmsg.get(p.getUniqueId());
            if (ChatColor.stripColor(last).equalsIgnoreCase(ev.getMessage())) {
                ev.getPlayer().sendMessage("§c§lNão floode :D");
                ev.setCancelled(true);
                return;
            }
        }
        String mensagem = ev.getMessage();
        if (p.hasPermission("kom.vip")) {
            mensagem = mensagem.replaceAll("\\&", "§");
        }
        ev.setMessage(mensagem);
        String tagclan = "";
        ClanPlayer cp = InstaMCLibKom.sc.getClanManager().getClanPlayer(ev.getPlayer().getUniqueId());
        String fun = "";
        if ((cp != null) && (cp.getClan() != null)) {
            tagclan = cp.getTagLabel();
        }
        if(p.isOp() || p.hasPermission("kom.staff"))
            fun = ""+ChatColor.GOLD;
        String pp = "§e[l] " + getFormatedPrefix(p) + "§r" + tagclan + "§r§f" + fun + p.getName() + "§r§7:§e ";
        ev.setFormat(pp + "%2$s");
        ev.getRecipients().clear();
        for (Entity t : p.getNearbyEntities(50.0D, 50.0D, 50.0D)) {
            if ((t instanceof Player)) {
                if (!SimplyVanish.isVanished((Player) t)) {
                    ev.getRecipients().add((Player) t);
                } else {
                    String s = pp + mensagem;
                    ((Player) t).sendMessage(s);
                }
            }
        }
        if (ev.getRecipients().isEmpty()) {
            ev.setCancelled(true);
            p.sendMessage("§b ~ ninguém perto para te ouvir ~");
            return;
        }
        if (!p.hasPermission("instamc.staff")) {
            this.lastmsg.put(p.getUniqueId(), mensagem);
            this.delay.put(p.getUniqueId(), Long.valueOf(System.currentTimeMillis() + 1500L));
        }
        ev.getRecipients().add(p);
    }

    public static String getFormatedPrefix(Player p) {
        PermissionUser u = PermissionsEx.getPermissionManager().getUser(p);
        String prefixo = u.getPrefix();
        prefixo = prefixo.replaceAll("\\&", "§");;
        return prefixo;
    }
}


/* Location:              C:\Users\User\Desktop\REPO\InstaMCLibKom.jar!\instamc\coders\libkom\listeners\ChatListener.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */
