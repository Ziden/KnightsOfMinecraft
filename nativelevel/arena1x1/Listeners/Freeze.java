/*    */ package nativelevel.arena1x1.Listeners;
/*    */
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import nativelevel.arena1x1.PvPMatchmaking.PvPMatchmaking;
import org.bukkit.Bukkit;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.player.PlayerMoveEvent;
/*    */
/*    */ public class Freeze implements org.bukkit.event.Listener /*    */ {
    /*    */ public static PvPMatchmaking plugin;
    /*    */
    /*    */ public Freeze(PvPMatchmaking instance) /*    */ {
        /* 15 */ plugin = instance;
        /*    */    }
    /*    */
    /* 18 */    int frozenfor = 5;
    /*    */
    /*    */ @org.bukkit.event.EventHandler
    /*    */ public void noMove(PlayerMoveEvent event) {
        /* 22 */ Player player = event.getPlayer();
        /* 23 */ if (PvPMatchmaking.joinedPlayers.contains(player)) /*    */ {
            /* 25 */ if (PvPMatchmaking.freeze1.containsKey(player)) {
                /* 26 */ long diff = (System.currentTimeMillis() - ((Long) PvPMatchmaking.freeze1.get(player)).longValue()) / 1000L;
                /*    */
                /* 28 */ if (diff < this.frozenfor) {
                    /* 29 */ event.setCancelled(true);
                    /* 30 */ return;
                    /*    */                }
                /* 32 */ PvPMatchmaking.freeze1.remove(player);
                /*    */            } /* 34 */ else if (PvPMatchmaking.freeze2.containsKey(player)) {
                /* 35 */ long diff = (System.currentTimeMillis() - ((Long) PvPMatchmaking.freeze2.get(player)).longValue()) / 1000L;
                /*    */
                /* 37 */ if (diff < this.frozenfor) {
                    /* 38 */ event.setCancelled(true);
                    /* 39 */ return;
                    /*    */                }
                /* 41 */ PvPMatchmaking.freeze2.remove(player);
                /* 42 */ Player[] arrayOfPlayer;
                for (Player players : Bukkit.getOnlinePlayers()) {
                    /* 43 */ if ((PvPMatchmaking.joinedPlayers.contains(players)) || (PvPMatchmaking.spectating.contains(players))) {
                        /* 44 */ players.sendMessage(PvPMatchmaking.blue + "GO!!");
                        /*    */                    }
                    /*    */                }
                /*    */            }
            /*    */        }
        /*    */    }
    /*    */ }


/* Location:              C:\Users\User\Downloads\PvPMatchmaking_v1.2.jar!\me\Doubts\Listeners\Freeze.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
