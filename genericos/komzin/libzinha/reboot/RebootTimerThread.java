/*    */ package genericos.komzin.libzinha.reboot;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import java.util.Calendar;
/*    */ import java.util.Date;
/*    */ import java.util.Timer;
/*    */ import java.util.TimerTask;
         import nativelevel.KoM;
         import org.bukkit.Bukkit;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RebootTimerThread
/*    */   extends Thread
/*    */ {
/*    */   public void run()
/*    */   {
/* 21 */     TimerTask action = new TimerTask()
/*    */     {
/*    */       public void run() {
/* 24 */         RebootUtils.ProximoRebootEmSegundos -= 1L;
/*    */         
/* 26 */         if (RebootUtils.ProximoRebootEmSegundos <= 0L) {
/* 27 */           System.out.println("VAI REBOOTAR");
/* 28 */           RebootUtils.ProximoRebootEmSegundos = 10800L;
/* 29 */           Calendar cal = Calendar.getInstance();
/* 30 */           cal.setTime(new Date());
/* 31 */           cal.add(11, 3);
/* 32 */           RebootUtils.ProximoRebootDate = cal.getTime();
/* 33 */           RebootUtils.EmReinicio = true;
/* 34 */           new ThreadReinicioRapido().start();
                    Bukkit.getScheduler().scheduleSyncDelayedTask(KoM._instance, new Runnable(){ public void run() {
                        RebootUtils.EmReinicio = false; 
                    }}, 20*60*3);
/*    */         }
/*    */       }
/* 37 */     };
/* 38 */     Timer timer = new Timer();
/* 39 */     timer.schedule(action, 2000L, 1000L);
/*    */   }
/*    */ }


/* Location:              C:\Users\User\Desktop\REPO\InstaMCLibKom.jar!\instamc\coders\libkom\reboot\RebootTimerThread.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */