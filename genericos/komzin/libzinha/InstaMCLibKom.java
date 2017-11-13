/*     */ package genericos.komzin.libzinha;
/*     */
/*     */ import genericos.komzin.libzinha.comandos.CmdFechar;
/*     */ import genericos.komzin.libzinha.comandos.ComandoFoguete;
/*     */ import genericos.komzin.libzinha.comandos.ComandoG;
/*     */ import genericos.komzin.libzinha.comandos.ComandoGm;
/*     */ import genericos.komzin.libzinha.comandos.ComandoHat;
/*     */ import genericos.komzin.libzinha.comandos.ComandoKomLib;
/*     */ import genericos.komzin.libzinha.comandos.ComandoOps;
/*     */ import genericos.komzin.libzinha.listeners.ChatListener;
/*     */ import genericos.komzin.libzinha.listeners.GeralListener;
/*     */ import genericos.komzin.libzinha.reboot.ComandoReinicio;
/*     */ import genericos.komzin.libzinha.reboot.RebootUtils;
/*     */ import genericos.komzin.libzinha.utils.ConfigProperties;
/*     */ import java.text.ParseException;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import java.util.logging.Logger;
import nativelevel.KoM;
/*     */ import net.milkbowl.vault.chat.Chat;
/*     */ import net.milkbowl.vault.economy.Economy;
/*     */ import net.milkbowl.vault.permission.Permission;
/*     */ import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.command.PluginCommand;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.metadata.FixedMetadataValue;
/*     */ import org.bukkit.metadata.MetadataValue;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ import org.bukkit.plugin.RegisteredServiceProvider;
/*     */ import org.bukkit.plugin.ServicesManager;
/*     */ import org.bukkit.plugin.java.JavaPlugin;
/*     */
/*     */ public class InstaMCLibKom /*     */ {
    /*  39 */ public static InstaMCLibKom instancia = null;
    /*  40 */    public static final Logger log = Logger.getLogger("Minecraft");
    /*  41 */    public static Permission permission = null;
    /*  42 */    public static Economy economy = null;
    /*  43 */    public static Chat chat = null;
    /*  44 */    public static SimpleClans sc = null;
    /*     */    public static ConfigProperties conf;
    /*     */
    /*     */ public static void addlog(String loga) /*     */ {
        /*  49 */ log.info("[LibKom] " + loga);
        /*     */    }
    /*     */
    /*     *//*     */
    /*     */
    /*     */ public void onEnable() /*     */ {
        /*  55 */ instancia = this;
        /*     */ try /*     */ {
            /*  58 */ conf = new ConfigProperties(KoM._instance.getDataFolder() + "/chat.properties");
            /*     */        } /*     */ catch (Exception ex) /*     */ {
            /*  62 */ ex.printStackTrace();
            /*     */        }
        /*  64 */ if (!conf.getConfig().containsKey("ValorGlobal")) /*     */ {
            /*  66 */ conf.getConfig().setProperty("ValorGlobal", "50");
            /*  67 */ conf.getConfig().setProperty("Moeda", "Coins");
            /*  68 */ conf.saveConfig();
            /*     */        }
        /*  70 */ setupPermissions();
        /*  71 */ setupEconomy();
        /*  72 */ setupChat();
        /*  73 */ Bukkit.getPluginCommand("gm").setExecutor(new ComandoGm());
        /*  74 */ Bukkit.getPluginCommand("ops").setExecutor(new ComandoOps());
        /*  75 */ Bukkit.getPluginCommand("fechar").setExecutor(new CmdFechar());
        /*  76 */ Bukkit.getPluginCommand("komlib").setExecutor(new ComandoKomLib());
        /*  77 */ Bukkit.getPluginCommand("foguete").setExecutor(new ComandoFoguete());
        /*  78 */ Bukkit.getPluginCommand("hat").setExecutor(new ComandoHat());
        /*  80 */ Bukkit.getPluginCommand("reinicio").setExecutor(new ComandoReinicio());
        /*  81 */ Bukkit.getPluginCommand("g").setExecutor(new ComandoG());
        /*  82 */ Bukkit.getServer().getPluginManager().registerEvents(new GeralListener(KoM._instance), KoM._instance);
        /*  83 */ Bukkit.getServer().getPluginManager().registerEvents(new ChatListener(), KoM._instance);
        /*     */ try /*     */ {
            /*  86 */ RebootUtils.LoadConf();
            /*     */        } /*     */ catch (ParseException ex) /*     */ {
            /*  90 */ log.info("ERRO AO CARREGAR CONF DE REINICIO RAPIDO");
            /*     */        }
        /*  92 */ Plugin plug = KoM._instance.getServer().getPluginManager().getPlugin("SimpleClans");
        /*  93 */ if (plug != null) /*     */ {
            /*  95 */ sc = (SimpleClans) plug;
            /*     */        }
        /*     */    }
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     *//*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */ private boolean setupEconomy() /*     */ {
        /* 157 */ RegisteredServiceProvider<Economy> economyProvider = KoM._instance.getServer().getServicesManager().getRegistration(Economy.class);
        /* 158 */ if (economyProvider != null) /*     */ {
            /* 160 */ economy = (Economy) economyProvider.getProvider();
            /*     */        }
        /* 162 */ return economy != null;
        /*     */    }
    /*     *//*     */
    /*     */ private boolean setupPermissions() /*     */ {
        /* 167 */ RegisteredServiceProvider<Permission> permissionProvider = KoM._instance.getServer().getServicesManager().getRegistration(Permission.class);
        /* 168 */ if (permissionProvider != null) /*     */ {
            /* 170 */ permission = (Permission) permissionProvider.getProvider();
            /*     */        }
        /* 172 */ return permission != null;
        /*     */    }
    /*     *//*     */
    /*     */ private boolean setupChat() /*     */ {
        /* 177 */ RegisteredServiceProvider<Chat> chatProvider = KoM._instance.getServer().getServicesManager().getRegistration(Chat.class);
        /* 178 */ if (chatProvider != null) /*     */ {
            /* 180 */ chat = (Chat) chatProvider.getProvider();
            /*     */        }
        /* 182 */ return chat != null;
        /*     */    }
    /*     *//*     */
    /*     */ public static PlayerInfo getinfo(Player p) /*     */ {
        /* 187 */ if (p.hasMetadata("PlayerInfoKomLib")) /*     */ {
            /* 189 */ return (PlayerInfo) ((MetadataValue) p.getMetadata("PlayerInfoKomLib").get(0)).value();
            /*     */        }
        /*     */
        /*     */
        /* 193 */ PlayerInfo meta = new PlayerInfo();
        /* 194 */ p.setMetadata("PlayerInfoKomLib", new FixedMetadataValue(KoM._instance, meta));
        /* 195 */ return meta;
        /*     */    }
    /*     */ }


/* Location:              C:\Users\User\Desktop\REPO\InstaMCLibKom.jar!\instamc\coders\libkom\InstaMCLibKom.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */
