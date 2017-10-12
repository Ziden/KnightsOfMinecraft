/*    */ package nativelevel.arena1x1.Listeners;
/*    */ 
/*    */ import nativelevel.arena1x1.PvPMatchmaking.PvPMatchmaking;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
/*    */ 
/*    */ public class Spectators implements Listener
/*    */ {
/*    */   public static PvPMatchmaking plugin;
/*    */   
/*    */   public Spectators(PvPMatchmaking instance)
/*    */   {
/* 15 */     plugin = instance;
/*    */   }
/*    */   
/*    */   @org.bukkit.event.EventHandler
/*    */   public void noHit(EntityDamageByEntityEvent event) {
/* 20 */     Entity entity = event.getEntity();
/* 21 */     if ((entity instanceof Player)) {
/* 22 */       Player player = (Player)event.getEntity();
/* 23 */       if (PvPMatchmaking.spectating.contains(player))
/*    */       {
/* 25 */         event.setCancelled(true);
/* 26 */         if ((event.getDamager() instanceof Player)) {
/* 27 */           Player damager = (Player)event.getDamager();
/* 28 */           damager.sendMessage(PvPMatchmaking.red + "No hurting other players while spectating!");
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\User\Downloads\PvPMatchmaking_v1.2.jar!\me\Doubts\Listeners\Spectators.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */