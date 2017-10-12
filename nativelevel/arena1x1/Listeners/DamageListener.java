/*    */ package nativelevel.arena1x1.Listeners;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import nativelevel.arena1x1.PvPMatchmaking.PvPMatchmaking;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
/*    */ 
/*    */ public class DamageListener implements org.bukkit.event.Listener
/*    */ {
/*    */   public static PvPMatchmaking plugin;
/*    */   
/*    */   public DamageListener(PvPMatchmaking instance)
/*    */   {
/* 15 */     plugin = instance;
/*    */   }
/*    */   
/*    */   @org.bukkit.event.EventHandler
/*    */   public void YesHit(EntityDamageByEntityEvent event) {
/* 20 */     Entity entity = event.getEntity();
/* 21 */     if ((entity instanceof Player)) {
/* 22 */       Player player = (Player)event.getEntity();
/* 23 */       if ((event.getDamager() instanceof Player)) {
/* 24 */         Player attacker = (Player)event.getDamager();
/* 25 */         if ((PvPMatchmaking.inGame.contains(player)) && (PvPMatchmaking.inGame.contains(attacker)) && 
/* 26 */           (event.isCancelled())) {
/* 27 */           event.setCancelled(false);
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\User\Downloads\PvPMatchmaking_v1.2.jar!\me\Doubts\Listeners\DamageListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */