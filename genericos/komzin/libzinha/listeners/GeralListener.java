/*     */ package genericos.komzin.libzinha.listeners;
/*     */

import nativelevel.utils.TitleAPI;
/*     */ import genericos.komzin.libzinha.InstaMCLibKom;
import genericos.komzin.libzinha.reboot.RebootUtils;
/*     */ import genericos.komzin.libzinha.utils.Efeitos;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import java.util.logging.Logger;
/*     */ import me.asofold.bpl.simplyvanish.SimplyVanish;
/*     */ import me.asofold.bpl.simplyvanish.api.events.SimplyVanishStateEvent;
/*     */ import net.milkbowl.vault.permission.Permission;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.GameMode;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.player.PlayerCommandPreprocessEvent;
/*     */ import org.bukkit.event.player.PlayerJoinEvent;
/*     */ import org.bukkit.event.player.PlayerLoginEvent;
/*     */ import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.plugin.java.JavaPlugin;
/*     */ import ru.tehkode.permissions.PermissionManager;
/*     */ import ru.tehkode.permissions.PermissionUser;
/*     */
/*     */ public class GeralListener implements Listener /*     */ {
    /*  33 */ public static HashSet<UUID> ListaVIPs = new HashSet();
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
    /*     */ public GeralListener(JavaPlugin instancia) {
    }

    //}
    /*     */
    /*     */ @EventHandler(priority = EventPriority.MONITOR)
    /*     */ public void LoginEvent(PlayerLoginEvent event) /*     */ {
        if (RebootUtils.EmReinicio) {
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.GOLD + "O Inverno está Chegando.");
        }
        /*  75 */ if (event.getResult() == PlayerLoginEvent.Result.KICK_FULL) /*     */ {
            /*  77 */ if (event.getPlayer().isOp()) /*     */ {
                /*  79 */ event.allow();
                /*     */
                /*     */
                /*     */
                /*     */
                /*     */            } /*  85 */ else if (InstaMCLibKom.permission.has(event.getPlayer(), "kom.vip")) /*     */ {
                /*  87 */ event.allow();
                /*     */
                /*     */            } /*     */ else /*     */ {
                /*  92 */ event.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.GOLD + "Servidor Lotado! Adquira seu Cavaleiro, Templario ou Lord e entre a qualquer momento!");
                /*     */            }
            /*     */        }
        /*     */    }
    /*     */
    /*     */ @EventHandler(priority = EventPriority.NORMAL)
    /*     */ public static void Comandos(PlayerCommandPreprocessEvent ev) /*     */ {
        /* 100 */ if (ev.getMessage().startsWith("/gamemode")) /*     */ {
            /* 102 */ ev.setCancelled(true);
            /* 103 */ ev.getPlayer().sendMessage("§cUse §f/gm §cpara alterar seu modo de jogo ;)");
            /*     */        }
        /* 105 */ if (((ev.getPlayer().isOp()) && (ev.getMessage().startsWith("/sudo")) && (ev.getMessage().contains("reload"))) || (ev.getMessage().startsWith("/reload"))) /*     */ {
            /* 107 */ ev.setCancelled(true);
            /* 108 */ ev.getPlayer().sendMessage("§cReload somente via console! ;)");
            /*     */        }
        /*     */    }
    /*     */
    /*     */ @EventHandler(priority = EventPriority.LOWEST)
    /*     */ public void JoinEvent(PlayerJoinEvent ev) /*     */ {

        /* 115 */ if (ev.getPlayer().isOp()) /*     */ {
            /* 117 */ ev.getPlayer().sendMessage("§c§lTotal de pessoas com OP §f(§2" + Bukkit.getOperators().size() + "§f) §c§lUse §f/ops");
            /*     */        }
        /* 119 */ if (ev.getPlayer().getName().equalsIgnoreCase("Camila")) /*     */ {
            /* 121 */ ev.getPlayer().sendMessage("Oi camila, bem vinda de volta ao KOM !!");
            /* 122 */ ev.getPlayer().sendMessage("Sentimos muita falta sua... muita mesmo !");
            /* 123 */ ev.getPlayer().sendMessage("O dev do server, gosta pakarawlhos de vc !");
            for(Player pl : Bukkit.getOnlinePlayers())
                pl.sendMessage(ChatColor.GREEN+"Camila entrou lindamente no servidor");
            /*     */        }
        /*     */
        /* 146 */     //TitleAPI.sendTabHeader(ev.getPlayer(), ChatColor.GOLD + "Bem-Vindo ao §c§lKnights Of Minecraft");
/* 147 */ ev.getPlayer().sendMessage("");
        /* 148 */ ev.getPlayer().sendMessage("§6§l▄§e§l▄§6§l▄§e§l▄§6§l▄§e§l▄§6§l▄§e§l▄§6§l▄§e§l▄§6§l▄§e§l▄§6§l▄§e§l▄§6§l▄§e§l▄§6§l▄§e§l▄§6§l▄§e§l▄§6§l▄§e§l▄§6§l▄§e§l▄§6§l▄§e§l▄§6§l▄§e§l▄");
        /* 149 */ int online = Bukkit.getOnlinePlayers().size();
        /* 150 */ ev.getPlayer().sendMessage("");
        /* 152 */ String msgOnline;
        if (online > 1) /*     */ {
            /* 154 */ msgOnline = "§aTemos §c§l" + online + " §aaventureiros pelo mundo de Aden!";
            /*     */
            /*     */        } /*     */ else /*     */ {
            /* 159 */ msgOnline = "§aTemos §c§l" + online + " §aaventureiro solitário pelo mundo de Aden!";
            /*     */        }
        /*     */
        /* 162 */ if (ev.getPlayer().getLevel() < 30) /*     */ {
            /* 164 */ ev.getPlayer().sendMessage("   §aBem-vindo ao " + ChatColor.GREEN + ChatColor.BOLD + "KoM §a(RPG), Pequeno Aprendiz!");
            /* 165 */ ev.getPlayer().sendMessage(msgOnline);
            /* 166 */ ev.getPlayer().sendMessage("");
            /* 167 */ ev.getPlayer().sendMessage("   §aSe tiver alguma dúvida no servidor ou achar difícil aprender, não desista!");
            /* 168 */ ev.getPlayer().sendMessage("   §aAcesse: §cwww.knightsofminecraft.com.br/ para ver a WIKI");
            /*     */        } /* 170 */ else if (ev.getPlayer().getLevel() < 110) /*     */ {
            /* 172 */ ev.getPlayer().sendMessage("   §aBem-vindo ao " + ChatColor.GREEN + "KoM (RPG), Aventureiro!");
            /* 173 */ ev.getPlayer().sendMessage(msgOnline);
            /* 174 */ ev.getPlayer().sendMessage("");
            /* 175 */ ev.getPlayer().sendMessage("   §aÉ importante participar da comunidade do KoM!");
            /* 176 */ ev.getPlayer().sendMessage("   §aAcesse: §cforum.knightsofminecraft.com");
            /*     */        } /*     */ else /*     */ {

            /* 180 */ ev.getPlayer().sendMessage("   §aBem-vindo ao " + ChatColor.GREEN + "KoM (RPG), Nobre Guerreiro!");
            /* 181 */ ev.getPlayer().sendMessage(msgOnline);
            /* 182 */ ev.getPlayer().sendMessage("");
            /* 183 */ ev.getPlayer().sendMessage("   §aFacilite sua vida, upe rapidamete ate o 100 e resete!!");
            /* 184 */ ev.getPlayer().sendMessage("   §aCompre cash em §cwww.knightsofminecraft.com");
            /*     */        }
        /* 186 */ ev.getPlayer().sendMessage("");
        /* 187 */ ev.getPlayer().sendMessage("§6§l▄§e§l▄§6§l▄§e§l▄§6§l▄§e§l▄§6§l▄§e§l▄§6§l▄§e§l▄§6§l▄§e§l▄§6§l▄§e§l▄§6§l▄§e§l▄§6§l▄§e§l▄§6§l▄§e§l▄§6§l▄§e§l▄§6§l▄§e§l▄§6§l▄§e§l▄§6§l▄§e§l▄");
        /*     */    }
    /*     */
    /* 190 */    public static HashSet<String> ListaEfeitos = new HashSet();
    /*     */
    /*     */ @EventHandler(priority = EventPriority.NORMAL)
    /*     */ public void VanishEvent(SimplyVanishStateEvent event) /*     */ {
        /* 195 */ if (!event.getPlayer().hasPermission("manialibkom.vanish.efeito")) /*     */ {
            /* 197 */ return;
            /*     */        }
        /* 199 */ if (!event.getPlayer().isOp() && !ListaEfeitos.contains(event.getPlayer().getName())) /*     */ {
            /* 201 */ return;
            /*     */        }
        /* 203 */ if (!event.getVisibleAfter()) /*     */ {
            /* 205 */ if (SimplyVanish.isVanished(event.getPlayer())) {
            }
            /*     */
            /*     */
            /*     */
            /*     */
            /*     */
            /*     */        } /* 212 */ else if (SimplyVanish.isVanished(event.getPlayer())) /*     */ {
            /* 214 */ return;
            /*     */        }
        /*     */
        if (event.getPlayer().getName().equalsIgnoreCase("ZidenVentania")) {
            /* 217 */ Location loc = event.getPlayer().getLocation();
            /* 218 */ Efeitos.effectBats(loc);
            /* 219 */ Efeitos.effectExplosion(loc);
            /* 220 */ Efeitos.effectFlames(loc);
            /* 221 */ Efeitos.effectLightning(loc);
            /* 222 */ Efeitos.effectSmoke(loc);
        }

        /*     */    }
    /*     */ }


/* Location:              C:\Users\User\Desktop\REPO\InstaMCLibKom.jar!\instamc\coders\libkom\listeners\GeralListener.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */
