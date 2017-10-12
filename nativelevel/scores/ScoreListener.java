package nativelevel.scores;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ScoreListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void JoinEvent(PlayerJoinEvent event)
    {
        //final Player p = event.getPlayer();
        //new ThreadScoreJoinEvent(p).run();
    }

}
