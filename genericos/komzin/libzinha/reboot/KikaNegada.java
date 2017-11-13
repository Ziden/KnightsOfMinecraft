package genericos.komzin.libzinha.reboot;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class KikaNegada implements Runnable {

    int totalPlayers = 0;
    
    public KikaNegada() {
        totalPlayers = Bukkit.getOnlinePlayers().size();
    }
    
    @Override
    public void run() {
       int umDecimo = totalPlayers/10;
       int x = 0;
       if(Bukkit.getOnlinePlayers().size()==0) {
           Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop");
       }
       for(Player p : Bukkit.getOnlinePlayers()) {
           x++;
           p.kickPlayer("Voce foi congelado");
           if(x >= umDecimo)
               break;
       } 
    }
    
}
