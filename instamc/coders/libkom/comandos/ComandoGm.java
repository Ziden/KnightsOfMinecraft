/*    */ package instamc.coders.libkom.comandos;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import org.bukkit.GameMode;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ComandoGm
/*    */   implements CommandExecutor
/*    */ {
/*    */   public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] strings)
/*    */   {
/* 18 */     if ((cs instanceof Player)) {
/* 19 */       Player p = (Player)cs;
/* 20 */       if ((p.isOp()) || (p.hasPermission("maniacomandos.gm"))) {
/* 21 */         if (p.getGameMode().equals(GameMode.CREATIVE)) {
/* 22 */           p.setGameMode(GameMode.SURVIVAL);
/* 23 */           p.sendMessage("§7Setado modo de jogo §2§lSOBREVIVENCIA");
/*    */         } else {
/* 25 */           p.setGameMode(GameMode.CREATIVE);
/* 26 */           p.sendMessage("§7Setado modo de jogo §4§lCRIATIVO");
/*    */         }
/*    */       } else {
/* 29 */         p.sendMessage("§cOh nao, voce nao pode usar esse comando. =/ ");
/* 30 */         return true;
/*    */       }
/*    */     } else {
/* 33 */       System.out.println("Comando apenas para player");
/* 34 */       return true;
/*    */     }
/*    */     
/* 37 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\User\Desktop\REPO\InstaMCLibKom.jar!\instamc\coders\libkom\comandos\ComandoGm.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */