/*    */ package instamc.coders.libkom.reboot;
/*    */ 
/*    */ import java.util.Date;
import nativelevel.KoM;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
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
/*    */ 
/*    */ 
/*    */ public class ComandoReinicio
/*    */   implements CommandExecutor
/*    */ {
/*    */   public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] args)
/*    */   {
/* 24 */     Date date = new Date();
/* 25 */     long diff = RebootUtils.ProximoRebootDate.getTime() - date.getTime();
/* 26 */     long diffSeconds = diff / 1000L % 60L;
/* 27 */     long diffMinutes = diff / 60000L % 60L;
/* 28 */     long diffHours = diff / 3600000L;
/* 29 */     if (!cs.isOp()) {
/* 30 */       if (RebootUtils.EmReinicio) {
/* 31 */         cs.sendMessage("§d<> Reinicio em andamento");
/* 32 */         return true;
/*    */       }
/* 34 */       cs.sendMessage("§dProximo reinicio em: §6" + diffHours + "§d horas §6" + diffMinutes + "§d minutos e §6" + diffSeconds + " §dsegundos");
/* 35 */       return true;
/*    */     }
/*    */     
/* 38 */     if (args.length == 0) {
/* 39 */       if (RebootUtils.EmReinicio) {
/* 40 */         cs.sendMessage("§d<> Reinicio em andamento");
/*    */       } else {
/* 42 */         cs.sendMessage("§dProximo reinicio em: §6" + diffHours + "§d horas §6" + diffMinutes + "§d minutos e §6" + diffSeconds + " §dsegundos");
/*    */       }
/* 44 */       cs.sendMessage("§cUso: §f/reinicio rapido | cancelar");
/* 45 */       return true; }
/* 46 */     if (args.length == 1) {
/* 47 */       if (args[0].equalsIgnoreCase("rapido")) {
/* 48 */         RebootUtils.ProximoRebootEmSegundos = 1L;
/* 49 */         cs.sendMessage("§cReinicio rapido iniciado!");
/* 50 */         return true; }
/* 51 */       if (args[0].equalsIgnoreCase("cancelar")) {
/* 52 */         RebootUtils.EmReinicio = false;
                 KoM.reiniciando = false;
/* 53 */         cs.sendMessage("§cReinicio cancelado!");
/*    */       }
/*    */     }
/* 56 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\User\Desktop\REPO\InstaMCLibKom.jar!\instamc\coders\libkom\reboot\ComandoReinicio.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */