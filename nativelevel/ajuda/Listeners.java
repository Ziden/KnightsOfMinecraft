package nativelevel.ajuda;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import nativelevel.ajuda.database.DBDefault;
import nativelevel.ajuda.database.MetodosRemover;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class Listeners
  implements Listener
{
  DBDefault db = new DBDefault();
  
  public static String f(String message)
  {
    String messageFormated = message.replace("&", "§");
    
    return messageFormated;
  }
  
  @EventHandler
  public void onJoin(PlayerJoinEvent e)
    throws SQLException
  {
    final Player p = e.getPlayer();
    new BukkitRunnable()
    {
      public void run()
      {
        try
        {
          ResultSet rs = Listeners.this.db.getConnection().createStatement().executeQuery("SELECT * FROM Pixel_Respostas WHERE playerOffline='" + p.getUniqueId() + "'");
          while (rs.next())
          {
            String servidor = rs.getString("servidor");
            if (servidor.equalsIgnoreCase(p.getServer().getServerName()))
            {
              p.sendMessage(" ");
              p.sendMessage(Listeners.f("&a&lChegou uma resposta!"));
              try
              {
                p.sendMessage(Listeners.f("&7Pergunta: &6" + rs.getString("pergunta")));
                p.sendMessage(" ");
                p.sendMessage(Listeners.f("&aResposta: &e" + rs.getString("resposta")));
                MetodosRemover.removerResposta(rs.getInt("id"));
              }
              catch (SQLException ex)
              {
                Logger.getLogger(Listeners.class.getName()).log(Level.SEVERE, null, ex);
              }
            }
          }
        }
        catch (SQLException ex)
        {
          Logger.getLogger(Listeners.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    }
    
      .runTaskLater(KomAjuda.pl, 50L);
  }
}
