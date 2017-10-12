package nativelevel.sc.bugfixes;

import nativelevel.KoM;
import net.sacredlabyrinth.phaed.simpleclans.events.*;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

/**
 *
 * @author NeT32
 */
public class SCBugFixes implements Listener {

    public static final int PROTOCOL_VERSION = 47;

    @EventHandler
    public void PlayerDemote(PlayerDemoteEvent event)
    {
        // Clan cai em ruinas quando não tem Leaders
        if (event.getClan().getLeaders().isEmpty())
        {
            event.getClanPlayer().toPlayer().sendMessage(ChatColor.AQUA + "Você rebaixou você mesmo, sua Guilda caiu em ruinas.");
            event.getClan().disband();
        }
    }
}
