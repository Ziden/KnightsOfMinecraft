package nativelevel.komquista.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import nativelevel.komquista.DB;
import nativelevel.komquista.KomQuista;
import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.managers.ClanManager;








public class Site
{
  public static void geraRankGuildas()
  {
    String h = "<html>";
    FileWriter fstream = null;
    try
    {
      File fil = new File("plugins" + File.separator + "kom" + File.separator + "site" + File.separator + "KomQuista.html");
      new File("plugins" + File.separator + "kom" + File.separator + "site").mkdirs();
      if (!fil.exists()) {
        fil.createNewFile();
      }
      fstream = new FileWriter(fil);
      BufferedWriter out = new BufferedWriter(fstream);
      h = h + "<head>";
      h = h + "<meta charset=\"utf-8\">";
      h = h + "<title> Rank do KomQuista </title>";
      h = h + "<link rel=\"stylesheet\" href=\"./styler.css\">";
      h = h + "<link href=\"./favicon.ico\" rel=\"shortcut icon\" type=\"image/x-icon\" />";
      h = h + "</head>";
      h = h + "<body>";
      h = h + "<img src=\"http://www.knightsofmania.com.br/img/company-logo.png\" class=\"logo\" alt=\"Logo KoM\"></h1>";
      h = h + "<h2 class=\"komqh2\">Vencedores do KomQuista</h2>";
      
      h = h + "<table border=1 class=\"komqtable\">";
      h = h + "<tr class=\"komq\">";
      h = h + "<td class=\"cabekomq\">Posicao</td>";
      h = h + "<td class=\"cabekomq\">Guilda</td>";
      h = h + "<td class=\"cabekomq\">Vitorias</td>";
      h = h + "</tr>";
      
      List<String> glds = DB.getWinners();
      
      int pos = 1;
      for (int y = 0; y < glds.size(); y++) {
        String tag = ((String)glds.get(y)).split(";")[0];
        int qtd = Integer.valueOf(((String)glds.get(y)).split(";")[1]).intValue();
        if (pos == 51) {
          break;
        }
        
        Clan c = KomQuista.getClanManager().getClan(tag);
        if (c != null)
        {


          h = h + "<tr class=\"komq\">";
          h = h + "<td class=\"komtd\">" + pos + "</td>";
          
          h = h + "<td class=\"komtd\"><a href=\"./guildas/" + tag + ".html\"\">" + c.getName() + " - " + tag + "</a>" + "</td>";
          h = h + "<td class=\"komtd\">" + qtd + "</td>";
          h = h + "</tr>";
          pos++;
        }
      }
      h = h + "</table><br>";
      
      h = h + "</body>";
      h = h + "</html>";
      out.write(h);
      out.close();
      fstream.close();
    }
    catch (IOException ex) {
      ex = 
      

        ex;ex.printStackTrace();
    }
    finally {}
  }
}
