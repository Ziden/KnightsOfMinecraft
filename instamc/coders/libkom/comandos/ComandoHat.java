/*    */ package instamc.coders.libkom.comandos;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.inventory.PlayerInventory;
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
/*    */ public class ComandoHat
/*    */   implements CommandExecutor
/*    */ {
/*    */   public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] args)
/*    */   {
/* 27 */     if (!(cs instanceof Player)) {
/* 28 */       cs.sendMessage("Este Comando Apenas Funciona com o Jogador.");
/* 29 */       return false;
/*    */     }
/* 31 */     Player p = (Player)cs;
/* 32 */     if (!cs.hasPermission("manialib.hat")) {
/* 33 */       cs.sendMessage("§cSem permissão!");
/* 34 */       return true;
/*    */     }
/* 36 */     if (p.getItemInHand().getType() == Material.AIR) {
/* 37 */       p.sendMessage("§cColoque algo em sua mão para usar de hat!");
/* 38 */       return true;
/*    */     }
/* 40 */     if ((p.getItemInHand().getTypeId() >= 256) && (p.getItemInHand().getTypeId() <= 358)) {
/* 41 */       p.sendMessage("§cEste item nao pode usar usado como hat!");
/* 42 */       return true;
/*    */     }
/* 44 */     if (p.getItemInHand().getAmount() > 1) {
/* 45 */       cs.sendMessage("§cColoque apenas 1 de quantidade do item em sua mão para usar!");
/* 46 */       return true;
/*    */     }
/* 48 */     ItemStack aux = new ItemStack(Material.AIR);
/* 49 */     if (p.getInventory().getHelmet() != null) {
/* 50 */       aux = p.getInventory().getHelmet().clone();
/*    */     }
/* 52 */     p.getInventory().setHelmet(p.getItemInHand());
/* 53 */     p.setItemInHand(aux);
/* 54 */     p.sendMessage("§cChapeu colocado!");
/* 55 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\User\Desktop\REPO\InstaMCLibKom.jar!\instamc\coders\libkom\comandos\ComandoHat.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */