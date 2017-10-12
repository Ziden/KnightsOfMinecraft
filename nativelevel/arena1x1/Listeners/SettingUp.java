/*    */ package nativelevel.arena1x1.Listeners;
/*    */ 
/*    */ import nativelevel.arena1x1.PvPMatchmaking.PvPMatchmaking;
import nativelevel.KoM;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.configuration.file.FileConfiguration;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.player.PlayerInteractEvent;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class SettingUp implements org.bukkit.event.Listener
/*    */ {
/*    */   public static KoM plugin;
/*    */   /*    */   
/*    */   public SettingUp(PvPMatchmaking instance)
/*    */   {
/* 16 */     plugin = KoM._instance;
/*    */   }
/*    */   
/*    */   @org.bukkit.event.EventHandler
/*    */   public void onClick(PlayerInteractEvent event) {
/* 21 */     Block clicked = event.getClickedBlock();
/* 22 */     Player player = event.getPlayer();
/* 23 */     if (PvPMatchmaking.creating)
/*    */     {
/* 25 */       if (PvPMatchmaking.beginCreation) {
/* 26 */         if (player.getItemInHand().getType() == org.bukkit.Material.AIR) {
/* 27 */           int x = clicked.getX();
/* 28 */           int y = clicked.getY();
/* 29 */           int z = clicked.getZ();
/* 30 */           PvPMatchmaking.cfg.set("PvPMatchmaking.Locations.Arena.block1.x", Integer.valueOf(x));
/* 31 */           PvPMatchmaking.cfg.set("PvPMatchmaking.Locations.Arena.block1.y", Integer.valueOf(y));
/* 32 */           PvPMatchmaking.cfg.set("PvPMatchmaking.Locations.Arena.block1.z", Integer.valueOf(z));
/* 33 */           PvPMatchmaking.cfg.set("PvPMatchmaking.Locations.World", clicked.getWorld());
/* 34 */           plugin.saveConfig();
/* 35 */           player.sendMessage(PvPMatchmaking.blue + "First block recorded, now please click the second block of the arena with your fist.");
/* 36 */           PvPMatchmaking.beginCreation = false;
/* 37 */           PvPMatchmaking.a1b2 = true;
/*    */         }
/* 39 */       } else if ((PvPMatchmaking.a1b2) && 
/* 40 */         (player.getItemInHand().getType() == org.bukkit.Material.AIR)) {
/* 41 */         int x = clicked.getX();
/* 42 */         int y = clicked.getY();
/* 43 */         int z = clicked.getZ();
/* 44 */         PvPMatchmaking.cfg.set("PvPMatchmaking.Locations.Arena.block2.x", Integer.valueOf(x));
/* 45 */         PvPMatchmaking.cfg.set("PvPMatchmaking.Locations.Arena.block2.y", Integer.valueOf(y));
/* 46 */         PvPMatchmaking.cfg.set("PvPMatchmaking.Locations.Arena.block2.z", Integer.valueOf(z));
/* 47 */         plugin.saveConfig();
/* 48 */         player.sendMessage(PvPMatchmaking.blue + "Second block recorded. Now type /pvpm set player1 to set the spawn location for player 1.");
/* 49 */         PvPMatchmaking.a1b2 = false;
/* 50 */         PvPMatchmaking.sp1 = true;
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\User\Downloads\PvPMatchmaking_v1.2.jar!\me\Doubts\Listeners\SettingUp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */