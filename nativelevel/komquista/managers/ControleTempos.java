package nativelevel.komquista.managers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.bukkit.Bukkit;










public class ControleTempos
{
  public static long lastdominio = -1L;
  public static String lasttag = "";
  
  public static void domina(String tag) {
    if (!lasttag.equals("")) {
      Guilda.getGuilda(lasttag).addDominio(new Dominio((int)(System.currentTimeMillis() - lastdominio) / 1000, lasttag));
      Bukkit.broadcastMessage("§9• §6A guilda " + lasttag + " conseguiu ficar com o castelo por " + (int)(System.currentTimeMillis() - lastdominio) / 1000 + " segundos!");
    }
    
    lastdominio = System.currentTimeMillis();
    lasttag = tag;
  }
  
  public static void termina()
  {
    if (!lasttag.equals("")) {
      Guilda.getGuilda(lasttag).addDominio(new Dominio((int)(System.currentTimeMillis() - lastdominio) / 1000, lasttag));
    }
    lasttag = "";
    lastdominio = -1L;
  }
  

  public static List<Guilda> getGuildasByTime()
  {
    List<Guilda> re = new ArrayList<Guilda>();
    re.addAll(Guilda.getGuildas());
    Collections.sort(re, new Comparator<Guilda>()
    {
      public int compare(Guilda o1, Guilda o2)
      {
        return o2.getTotalTime() - o1.getTotalTime();
      }
    });
    return re;
  }
}
