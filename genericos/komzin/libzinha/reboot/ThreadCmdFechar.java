/*    */ package genericos.komzin.libzinha.reboot;
/*    */ 
/*    */ import genericos.komzin.libzinha.utils.Utils;
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.Logger;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ThreadCmdFechar
/*    */   extends Thread
/*    */ {
/*    */   public void run()
/*    */   {
/*    */     try
/*    */     {
/* 24 */       Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "save-all");
/*    */     }
/*    */     catch (Exception localException) {}
/* 27 */     Bukkit.broadcastMessage("§d[Reinicio]§e <> Voce foi teleportado para o lobby central!!!");
/*    */     try
/*    */     {
/* 30 */       for (Player p : Bukkit.getOnlinePlayers())
/*    */       {
/*    */ 
/* 33 */         Utils.TeleportarTPBG("mhub", p);
/*    */       }
/*    */     }
/*    */     catch (Exception localException1) {}
/*    */     try {
/* 38 */       sleep(5000L);
/*    */     } catch (InterruptedException ex) {
/* 40 */       Logger.getLogger(ThreadCmdFechar.class.getName()).log(Level.SEVERE, null, ex);
/*    */     }
/* 42 */     Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop");
/*    */   }
/*    */ }


/* Location:              C:\Users\User\Desktop\REPO\InstaMCLibKom.jar!\instamc\coders\libkom\reboot\ThreadCmdFechar.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */