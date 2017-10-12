package nativelevel.komquista.managers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;











public class Guilda
{
  private List<Dominio> dominios = new ArrayList();
  
  private String tag;
  private static HashMap<String, Guilda> guildas = new HashMap();
  
  public static Guilda getGuilda(String tag) {
    if (guildas.containsKey(tag)) {
      return (Guilda)guildas.get(tag);
    }
    Guilda g = new Guilda(tag);
    guildas.put(tag, g);
    return g;
  }
  
  public static Collection<Guilda> getGuildas() {
    return guildas.values();
  }
  
  public String getTag()
  {
    return this.tag;
  }
  
  public static void clear() {
    guildas.clear();
  }
  
  public Guilda(String tag) {
    this.tag = tag;
  }
  
  public void addDominio(Dominio d) {
    this.dominios.add(d);
  }
  
  public int getTotalTime() {
    int volta = 0;
    for (Dominio d : this.dominios) {
      volta += d.getSegundos();
    }
    
    return volta;
  }
}
