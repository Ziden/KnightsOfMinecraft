/*    */ package genericos.komzin.libzinha.comandos;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import java.util.Set;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.OfflinePlayer;
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
/*    */ public class ComandoOps
/*    */   implements CommandExecutor
/*    */ {
/*    */   public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] strings)
/*    */   {
/* 22 */     if ((cs instanceof Player)) {
/* 23 */       Player p = (Player)cs;
/* 24 */       if ((p.isOp()) || (p.hasPermission("cmdmania.ops"))) {
/* 25 */         p.sendMessage("§c§lTotal de pessoas com OP §f(§2" + Bukkit.getOperators().size() + "§f)");
/* 26 */         StringBuilder sb = new StringBuilder();
/* 27 */         for (OfflinePlayer op : Bukkit.getOperators()) {
/* 28 */           sb.append(op.getName()).append(", ");
/*    */         }
/* 30 */         String operators = sb.toString().substring(0, sb.length() - 2);
/* 31 */         p.sendMessage(operators);
/*    */       } else {
/* 33 */         p.sendMessage("§cVoce nao tem permissao.");
/* 34 */         return true;
/*    */       }
/*    */     } else {
/* 37 */       System.out.println("Comando apenas para Player!");
/* 38 */       return true;
/*    */     }
/* 40 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\User\Desktop\REPO\InstaMCLibKom.jar!\instamc\coders\libkom\comandos\ComandoOps.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */