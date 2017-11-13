/*    */ package genericos.komzin.libzinha.comandos;
/*    */ 
/*    */ import genericos.komzin.libzinha.reboot.ThreadCmdFechar;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
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
/*    */ 
/*    */ public class CmdFechar
/*    */   implements CommandExecutor
/*    */ {
/*    */   public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] strings)
/*    */   {
/* 23 */     if ((cs instanceof Player)) {
/* 24 */       Player p = (Player)cs;
/* 25 */       if (!p.getPlayer().isOp()) {
/* 26 */         return true;
/*    */       }
/* 28 */       new ThreadCmdFechar().start();
/*    */     } else {
/* 30 */       new ThreadCmdFechar().start();
/*    */     }
/* 32 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\User\Desktop\REPO\InstaMCLibKom.jar!\instamc\coders\libkom\comandos\CmdFechar.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */