package nativelevel.komquista;

import java.io.File;
import java.util.logging.Logger;
import nativelevel.komquista.comandos.CmdKomQ;
import nativelevel.komquista.listeners.Eventos;
import nativelevel.komquista.listeners.EventosKomQuista;
import nativelevel.komquista.utils.Site;
import nativelevel.KoM;
import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;
import net.sacredlabyrinth.phaed.simpleclans.managers.ClanManager;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;










public class KomQuista
{
  private static ClanManager manager;
  private static KomQuista instancia;
  public static final Logger log = Logger.getLogger("Minecraft");
  

  public void onDisable() {}
  

  public void onEnable()
  {
    instancia = this;
    
    manager = ((SimpleClans)Bukkit.getServer().getPluginManager().getPlugin("SimpleClans")).getClanManager();
    
    File f = KoM._instance.getDataFolder();
    f.mkdirs();
    DB.startDatabase();
    Bukkit.getPluginManager().registerEvents(new Eventos(),  KoM._instance);
    Bukkit.getPluginManager().registerEvents(new EventosKomQuista(),  KoM._instance);
    Bukkit.getPluginCommand("komq").setExecutor(new CmdKomQ());
    Site.geraRankGuildas();
  }
  
  public static ClanManager getClanManager() {
    return manager;
  }
  
  public static KomQuista getInstancia() {
    return instancia;
  }
}
