package nativelevel.scores;

import nativelevel.KoM;
import nativelevel.karma.Criminoso;
import nativelevel.mercadinho.Utils;
import nativelevel.sisteminhas.ClanLand;
import nativelevel.sisteminhas.KomSystem;
import nativelevel.titulos.TituloDB;
import nativelevel.titulos.Titulos;
import static nativelevel.titulos.Titulos.trabalhaTitulo;
import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerInfo;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.scoreboard.DisplaySlot;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

/**
 *
 * @author NeT32
 *
 */
public class SBCoreListener extends KomSystem {

    public SBCoreListener() {

        Bukkit.getScheduler().scheduleSyncRepeatingTask(KoM._instance, new Runnable() {
            @Override
            public void run() {
                for (Player pl : Bukkit.getOnlinePlayers()) {
                    for (Player observer : Bukkit.getOnlinePlayers()) {

                        PacketPlayOutPlayerInfo pf = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.UPDATE_DISPLAY_NAME, ((CraftPlayer) pl).getHandle());
                        // PacketPlayOutPlayerInfo pf = PacketPlayOutPlayerInfo.updateDisplayName(((CraftPlayer) pl).getHandle(), (CraftScoreboard) observer.getScoreboard());
                        ((CraftPlayer) observer).getHandle().playerConnection.sendPacket(pf);
                    }
                }
            }
        }, 20 * 60, 20 * 60);

    }

    @EventHandler(priority = EventPriority.HIGH)
    public void Aoentrar(final PlayerJoinEvent ev) {

        ev.setJoinMessage(null);
        final Player nego = ev.getPlayer();
        ScoreboardManager.registerScoreboard(nego);
        Bukkit.getScheduler().runTaskLater(KoM._instance, new Runnable() {
            @Override
            public void run() {
                if (nego != null && nego.isOnline()) {

                    SBCoreListener.AdicionarPlayerScores(nego);
                    //
                    SBCore.setLevelPoints(nego, nego.getLevel());

                    SBCore.CriarObjetivos();
                    //SBCore.AtualizaObjetivos(nego);

                    Bukkit.getScheduler().scheduleSyncDelayedTask(KoM._instance, new Runnable() {

                        @Override
                        public void run() {
                            if (ev.getPlayer() != null) {
                                ScoreboardManager.makeScore(ev.getPlayer(), DisplaySlot.BELOW_NAME, ev.getPlayer().getName(), (int) ev.getPlayer().getHealth());
                            }
                        }
                    }, 40);

                }
            }
        }, 20);
    }

    public static void addPlayerToPlayer(Player zoiando, Player sendovisto) {
        String title = null;
        TituloDB.PData data = TituloDB.getPlayerData(sendovisto);
        if (data != null) {
            title = data.getTitulo();
            if (title == null || title.trim().equals("")) {
                title = null;
            } else {
                title = " " + Titulos.trabalhaTitulo(title, sendovisto, data.getCortitulo());
            }
        }
        String prefix = getIconeKarma(sendovisto) + " " + getIcone(sendovisto);
        String corNome = ChatColor.RESET + "";
        corNome = ChatColor.WHITE + "";

        ClanPlayer cp1 = ClanLand.manager.getClanPlayer(zoiando);
        ClanPlayer cp2 = ClanLand.manager.getClanPlayer(sendovisto);

        if (cp2 != null) {
            if (cp1 != null && cp1.isAlly(sendovisto)) {
                corNome = ChatColor.AQUA + "";
            } else if (cp1 != null && cp1.getTag().equalsIgnoreCase(cp2.getTag())) {
                corNome = ChatColor.GREEN + "";
            } else if (cp1 != null && cp1.isRival(sendovisto)) {
                corNome = ChatColor.RED + "";
            }
        }
        if (sendovisto.isOp() || sendovisto.hasPermission("kom.staff")) {
            corNome = ChatColor.GOLD + "";
        }
        if (Criminoso.isCriminoso(sendovisto)) {
            corNome = "§c";
        }
        String prefixo = prefix + corNome + " ";
        if (prefixo != null && prefixo.length() > 16) {
            prefixo = prefixo.substring(0, 16);
        }
        if (title != null && title.length() > 16) {
            title = title.substring(0, 16);
        }

        ScoreboardManager.addToTeam(zoiando, sendovisto.getName(), sendovisto.getName(), prefixo, title, false);
    }

    public static String getIconeKarma(Player p) {
        int karma = KoM.database.getKarma(p.getUniqueId());
        String icone = "";
        if (karma < 0) {
            icone = "☠";
            if (karma < -20000) {
                icone = "§4" + icone;
            } else if (karma < - 10000) {
                icone = "§c" + icone;
            } else if (karma < - 2000) {
                icone = ChatColor.GOLD + icone;
            } else if (karma < - 100) {
                icone = ChatColor.YELLOW + icone;
            }
        } else if (karma > 0) {
            icone = "☺";
            if (karma > 20000) {
                icone = "§9" + icone;
            } else if (karma > -10000) {
                icone = ChatColor.AQUA + icone;
            } else if (karma > 2000) {
                icone = "§a" + icone;
            } else if (karma > 100) {
                icone = "§2" + icone;
            }
        }

        return icone;
    }

    public static String getIcone(Player p) {

        if (p.hasPermission("kom.cor.diretor")) {
            return ChatColor.AQUA + "" + ChatColor.BOLD + "DEV ";
        }
        if (p.hasPermission("kom.cor.subdiretor")) {
            return ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "OP ";
        }
        if (p.hasPermission("kom.cor.coordenador")) {
            return ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "COORD ";
        }
        if (p.hasPermission("kom.cor.admin")) {
            return ChatColor.DARK_RED + "" + ChatColor.BOLD + "ADMIN ";
        }
        if (p.hasPermission("kom.cor.mestre")) {
            return "§3" + ChatColor.BOLD + "GM ";
        }
        if (p.hasPermission("kom.cor.mod")) {
            return ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "MOD ";
        }
        if (p.hasPermission("kom.cor.lorer")) {
            return "§e" + ChatColor.WHITE + "LORER ";
        }
        if (p.hasPermission("kom.cor.ajd")) {
            return ChatColor.GREEN + "" + ChatColor.BOLD + "AJD ";
        }
        if (p.hasPermission("kom.cor.mapper")) {
            return  "§e§lBUILDER ";
        }

        if (p.hasPermission("kom.cor.yt")) {
            return ChatColor.RED + "" + ChatColor.BOLD + "YouTuber ";
        }
        if (p.hasPermission("kom.lord")) {
            return ChatColor.GOLD + "✠ ";
        }
        if (p.hasPermission("kom.templario")) {
            return ChatColor.BLUE + "✠ ";
        }
        if (p.hasPermission("kom.vip")) {
            return ChatColor.RED + "✠ ";
        }

        return ChatColor.WHITE + "";
    }

    public static void AdicionarMudancaMeuScore(Player p) {
        for (Player playerOn : Bukkit.getServer().getOnlinePlayers()) {
            addPlayerToPlayer(playerOn, p);
        }
    }

    public static void AdicionarPlayerScores(Player player) {
        for (Player playerOn : Bukkit.getServer().getOnlinePlayers()) {
            addPlayerToPlayer(player, playerOn);
            if (playerOn.getUniqueId() != player.getUniqueId()) {
                addPlayerToPlayer(playerOn, player);
            }
        }
    }

    /*
     public static void updatePlayer(Player p) {
     for (Player player : Bukkit.getOnlinePlayers()) {
     if (p == player) {
     continue;
     }
     addPlayerToPlayer(p, player);
     addPlayerToPlayer(player, p);
     }

     }
     */
}
