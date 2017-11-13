package genericos.komzin.libzinha.listeners;

import genericos.komzin.libzinha.InstaMCLibKom;
import genericos.komzin.libzinha.PlayerInfo;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;
import me.asofold.bpl.simplyvanish.SimplyVanish;
import nativelevel.KoM;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;
import net.sacredlabyrinth.phaed.simpleclans.managers.ClanManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.metadata.FixedMetadataValue;
import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class ChatListener implements Listener {

    HashMap<UUID, String> lastmsg = new HashMap();
    HashMap<UUID, Long> delay = new HashMap();

    public static PlayerInfo GetInfo(Player p) {
        if (p.hasMetadata("PlayerInfo")) {
            return (PlayerInfo) p.getMetadata("PlayerInfo").get(0).value();
        } else {
            PlayerInfo meta = new PlayerInfo();
            p.setMetadata("PlayerInfo", new FixedMetadataValue(KoM._instance, meta));
            return meta;
        }
    }

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

        if (p.hasMetadata("Silenciado")) {
            p.sendMessage("§f[§4!§f]§8 Voce esta silenciado. Talves voce tenha falado algo inadequado no chat!");
            ev.setCancelled(true);
            return;
        }
        if (p.hasMetadata("fixglobal")) {
            System.out.println("ev.getMessage()" + ev.getMessage());
            Bukkit.dispatchCommand(p, "g " + ev.getMessage());
            ev.setCancelled(true);
            return;
        }

        String mensagem = ev.getMessage();
        if (p.hasPermission("kom.vip")) {
            mensagem = mensagem.replaceAll("\\&", "§");
        }
        StringBuilder cache = new StringBuilder();

        PlayerInfo infop = InstaMCLibKom.getinfo(p);
        String tagclan = "";
        ClanPlayer cp = InstaMCLibKom.sc.getClanManager().getClanPlayer(ev.getPlayer().getUniqueId());
        String fun = "";
        if ((cp != null) && (cp.getClan() != null)) {
            tagclan = cp.getTagLabel();
        }

        // TELLS
        if (infop.talkingTo != null) {
            Player pto = Bukkit.getPlayer(infop.talkingTo);
            if (pto == null || !pto.isOnline()) {
                ev.getPlayer().sendMessage(ChatColor.RED + "Chat Privado com " + ChatColor.WHITE + infop.talkingTo + ChatColor.RED + " foi Desligado!");
                infop.talkingTo = null;
                ev.setCancelled(true);
                return;
            }
            PlayerInfo InfoTarget = InstaMCLibKom.getinfo(pto);
            if (InfoTarget.ignoreTell == true && pto.isOp()) {
                p.sendMessage(ChatColor.RED + "Nick nao encontrado.");
                infop.talkingTo = null;
                ev.setCancelled(true);
                return;
            }
            if (InfoTarget.ignoreTell == true && !p.isOp()) {
                p.sendMessage(ChatColor.RED + "Este jogador esta ocupado.");
                infop.talkingTo = null;
                ev.setCancelled(true);
                return;
            }
            cache.append(ChatColor.DARK_AQUA).append("De ").append(ev.getPlayer().getName()).append(": ").append(ChatColor.AQUA).append(ev.getMessage());
            pto.sendMessage(cache.toString());
            cache.delete(0, cache.length());
            cache.append(ChatColor.DARK_AQUA).append("Para ").append(pto.getName()).append(": ").append(ChatColor.AQUA).append(ev.getMessage());
            p.sendMessage(cache.toString());
            InfoTarget.lastPlayerMessage = ev.getPlayer().getName();
            ev.setCancelled(true);
            return;
        }

        if (infop.inChannel != null && infop.inChannel.equalsIgnoreCase("staff")) {
            if (!p.hasPermission("craftville.chatbigboss")) {
                infop.inChannel = null;
                ev.setCancelled(true);
                return;
            }
            String channel = ChatColor.BLUE + "[BigBoss]";
            for (Player px : Bukkit.getOnlinePlayers()) {
                if (px.hasPermission("kom.staff")) {
                    px.sendMessage(channel + " " + getFormatedPrefix(p) + "§r" + tagclan + "§f" + ev.getPlayer().getName() + "§7: §e" + ev.getMessage());
                }
            }
            ev.setCancelled(true);
            return;
        }

        ev.setMessage(mensagem);

        if (p.isOp() || p.hasPermission("kom.staff")) {
            fun = "" + ChatColor.GOLD;
        }
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
