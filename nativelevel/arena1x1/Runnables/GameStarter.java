/*    */ package nativelevel.arena1x1.Runnables;
/*    */ 
/*    */ import nativelevel.arena1x1.PvPMatchmaking.PvPMatchmaking;
/*    */ import org.bukkit.Bukkit;
/*    */ 
/*    */ public class GameStarter implements Runnable
/*    */ {
/*    */   public static PvPMatchmaking plugin;
/*  9 */   public static int StartingTime = 120;
/* 10 */   public static int nowTime = 0;
/* 11 */   boolean saidMessage = false;
/* 12 */   boolean didtele = false;
/*    */   
/*    */   public GameStarter(PvPMatchmaking instance) {
/* 15 */     plugin = instance;
/*    */   }
/*    */   
/*    */   public void run()
/*    */   {
/* 20 */     if (nowTime == StartingTime) {
/* 21 */       if (!this.saidMessage) {
/* 22 */         Bukkit.broadcastMessage(PvPMatchmaking.blue + "The game is starting!");
/* 23 */         this.saidMessage = true;
/*    */       }
/* 25 */       if (!this.didtele) {
/* 26 */         PvPMatchmaking.teleportToArena("Start", 1);
/* 27 */         this.didtele = true;
/* 28 */         PvPMatchmaking.gameStarted = true;
/* 29 */         PvPMatchmaking.round1 = true;
/*    */       }
/*    */     }
/* 32 */     else if ((plugin.getPlayers() >= PvPMatchmaking.playersNeeded) && (nowTime < StartingTime)) {
/* 33 */       if (nowTime == 60) {
/* 34 */         Bukkit.broadcastMessage(PvPMatchmaking.blue + "PvPMatchmaking Game starting in: " + PvPMatchmaking.green + "60 seconds");
/* 35 */       } else if (nowTime == 105) {
/* 36 */         Bukkit.broadcastMessage(PvPMatchmaking.blue + "PvPMatchmaking game starting in: " + PvPMatchmaking.green + "15 seconds");
/* 37 */       } else if (nowTime == 110) {
/* 38 */         Bukkit.broadcastMessage(PvPMatchmaking.blue + "PvPMatchmaking game starting in: " + PvPMatchmaking.green + "5 seconds");
/* 39 */       } else if (nowTime == 111) {
/* 40 */         Bukkit.broadcastMessage(PvPMatchmaking.blue + "PvPMatchmaking game starting in: " + PvPMatchmaking.green + "4 seconds");
/* 41 */       } else if (nowTime == 112) {
/* 42 */         Bukkit.broadcastMessage(PvPMatchmaking.blue + "PvPMatchmaking game starting in: " + PvPMatchmaking.green + "3 seconds");
/* 43 */       } else if (nowTime == 113) {
/* 44 */         Bukkit.broadcastMessage(PvPMatchmaking.blue + "PvPMatchmaking game starting in: " + PvPMatchmaking.green + "2 seconds");
/* 45 */       } else if (nowTime == 114) {
/* 46 */         Bukkit.broadcastMessage(PvPMatchmaking.blue + "PvPMatchmaking game starting in: " + PvPMatchmaking.green + "1 seconds");
/*    */       }
/* 48 */       if (nowTime < StartingTime) {
/* 49 */         nowTime += 1;
/* 50 */       } else if (nowTime > StartingTime) {
/* 51 */         nowTime = StartingTime;
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\User\Downloads\PvPMatchmaking_v1.2.jar!\me\Doubts\Runnables\GameStarter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */