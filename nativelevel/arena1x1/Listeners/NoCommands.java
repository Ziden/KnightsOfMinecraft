/*    */ package nativelevel.arena1x1.Listeners;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import nativelevel.arena1x1.PvPMatchmaking.PvPMatchmaking;
/*    */ import org.bukkit.event.player.PlayerCommandPreprocessEvent;
/*    */ 
/*    */ public class NoCommands implements org.bukkit.event.Listener
/*    */ {
/*    */   public static PvPMatchmaking plugin;
/*    */   
/*    */   public NoCommands(PvPMatchmaking instance)
/*    */   {
/* 13 */     plugin = instance;
/*    */   }
/*    */   
/*    */   public void noCommand(PlayerCommandPreprocessEvent event) {
/* 17 */     org.bukkit.entity.Player player = event.getPlayer();
/* 18 */     if ((PvPMatchmaking.inGame.contains(player)) || (PvPMatchmaking.joinedPlayers.contains(player)) || (PvPMatchmaking.spectating.contains(player))) {
/* 19 */       event.setCancelled(true);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\User\Downloads\PvPMatchmaking_v1.2.jar!\me\Doubts\Listeners\NoCommands.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */