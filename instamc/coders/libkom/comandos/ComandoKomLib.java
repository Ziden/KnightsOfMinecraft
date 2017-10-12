/*    */ package instamc.coders.libkom.comandos;
/*    */ 
/*    */ import instamc.coders.libkom.listeners.GeralListener;
/*    */ import instamc.coders.libkom.utils.Efeitos;
/*    */ import java.util.HashSet;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.block.Chest;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.inventory.Inventory;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.inventory.PlayerInventory;
/*    */ 
/*    */ 
         
/*    */ 
/*    */ public class ComandoKomLib
/*    */   implements CommandExecutor
/*    */ {
               HashSet<Material> fodase = new HashSet<Material>();
    
/*    */   public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] args)
/*    */   {
/* 26 */     if (!cs.hasPermission("manialibkom.komlib"))
/*    */     {
/* 28 */       return true;
/*    */     }
/* 30 */     Player p = (Player)cs;
/* 31 */     if (args.length == 0)
/*    */     {
/* 33 */       cs.sendMessage("§cUtilize: §f/komlib efeito");
/* 34 */       cs.sendMessage("§cUtilize §f/komlib bauraio <Bau com items do seu inv onde aponta caindo raio>");
/* 35 */       return true;
/*    */     }
/* 37 */     if (args.length == 1)
/*    */     {
/* 39 */       if (args[0].equalsIgnoreCase("efeito"))
/*    */       {
/* 41 */         if (GeralListener.ListaEfeitos.contains(cs.getName()))
/*    */         {
/* 43 */           GeralListener.ListaEfeitos.remove(cs.getName());
/* 44 */           cs.sendMessage("§cEfeitos desativados!");
/* 45 */           return true;
/*    */         }
/*    */         
/*    */ 
/* 49 */         GeralListener.ListaEfeitos.add(cs.getName());
/* 50 */         cs.sendMessage("§cEfeitos ativados!");
/* 51 */         return true;
/*    */       }
/*    */       
/* 54 */       if (args[0].equalsIgnoreCase("bauraio"))
/*    */       {
/* 56 */         Location novloc = p.getTargetBlock(fodase, 600).getLocation().clone();
/* 57 */         novloc.setY(novloc.getWorld().getHighestBlockYAt(novloc));
/* 58 */         novloc.getBlock().setType(Material.CHEST);
/* 59 */         Chest chest = (Chest)novloc.getBlock().getState();
/*    */         
/* 61 */         for (int i = 0; i < 36; i++)
/*    */         {
/* 63 */           if (p.getInventory().getItem(i) != null)
/*    */           {
/* 65 */             if (chest.getBlockInventory().firstEmpty() != -1)
/*    */             {
/*    */ 
/*    */ 
/* 69 */               chest.getBlockInventory().addItem(new ItemStack[] { p.getInventory().getItem(i) });
/*    */             }
/*    */           }
/*    */         }
/* 73 */         Efeitos.effectBats(novloc);
/* 74 */         Efeitos.effectExplosion(novloc);
/* 75 */         Efeitos.effectFlames(novloc);
/* 76 */         Efeitos.effectLightning(novloc);
/* 77 */         Efeitos.effectSmoke(novloc);
/* 78 */         cs.sendMessage("§cColocado!");
/* 79 */         return true;
/*    */       }
/*    */     }
/*    */     
/* 83 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\User\Desktop\REPO\InstaMCLibKom.jar!\instamc\coders\libkom\comandos\ComandoKomLib.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */