/*     */ package nativelevel.arena1x1.Listeners;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import nativelevel.arena1x1.PvPMatchmaking.PvPMatchmaking;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.configuration.file.FileConfiguration;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.player.PlayerQuitEvent;
/*     */ 
/*     */ public class LeaveListener implements org.bukkit.event.Listener
/*     */ {
/*     */   public static PvPMatchmaking plugin;
/*     */   
/*     */   public LeaveListener(PvPMatchmaking instance)
/*     */   {
/*  16 */     plugin = instance;
/*     */   }
/*     */   
/*     */   @org.bukkit.event.EventHandler
/*     */   public void leaveGame(PlayerQuitEvent event) {
/*  21 */     Player e = event.getPlayer();
/*  22 */     org.bukkit.Location exit = new org.bukkit.Location(Bukkit.getWorld(PvPMatchmaking.cfg.getString("PvPMatchmaking.Locations.Arena.Exit.world")), PvPMatchmaking.cfg.getInt("PvPMatchmaking.Locations.Arena.Exit.block.x"), PvPMatchmaking.cfg.getInt("PvPMatchmaking.Locations.Arena.Exit.block.y"), PvPMatchmaking.cfg.getInt("PvPMatchmaking.Locations.Arena.Exit.block.z"));
/*  23 */     if (PvPMatchmaking.joinedPlayers.contains(e)) {
/*  24 */       e.teleport(exit);
/*  25 */       if (PvPMatchmaking.player1.contains(e)) {
/*  26 */         PvPMatchmaking.player1.remove(e);
/*  27 */         PvPMatchmaking.joined -= 1;
/*  28 */         Bukkit.broadcastMessage(PvPMatchmaking.Pre + " Temos [" + PvPMatchmaking.joined + "] jogadores no torneio 1x1..");
/*  29 */         PvPMatchmaking.jplayer1 = false;
/*  30 */         PvPMatchmaking.joinedPlayers.remove(e);
/*     */       }
/*  32 */       if (PvPMatchmaking.player2.contains(e)) {
/*  33 */         PvPMatchmaking.player2.remove(e);
/*  34 */         PvPMatchmaking.joined -= 1;
/*  35 */         Bukkit.broadcastMessage(PvPMatchmaking.Pre + " Temos [" + PvPMatchmaking.joined + "] jogadores no torneio 1x1..");
/*  36 */         PvPMatchmaking.jplayer2 = false;
/*  37 */         PvPMatchmaking.joinedPlayers.remove(e);
/*     */       }
/*  39 */       if (PvPMatchmaking.player3.contains(e)) {
/*  40 */         PvPMatchmaking.player3.remove(e);
/*  41 */         PvPMatchmaking.joined -= 1;
/*  42 */         Bukkit.broadcastMessage(PvPMatchmaking.Pre + " Temos [" + PvPMatchmaking.joined + "] jogadores no torneio 1x1..");
/*  43 */         PvPMatchmaking.jplayer3 = false;
/*  44 */         PvPMatchmaking.joinedPlayers.remove(e);
/*     */       }
/*  46 */       if (PvPMatchmaking.player4.contains(e)) {
/*  47 */         PvPMatchmaking.player4.remove(e);
/*  48 */         PvPMatchmaking.joined -= 1;
/*  49 */         Bukkit.broadcastMessage(PvPMatchmaking.Pre + " Temos [" + PvPMatchmaking.joined + "] jogadores no torneio 1x1..");
/*  50 */         PvPMatchmaking.jplayer4 = false;
/*  51 */         PvPMatchmaking.joinedPlayers.remove(e);
/*     */       }
/*  53 */       if (PvPMatchmaking.player5.contains(e)) {
/*  54 */         PvPMatchmaking.player5.remove(e);
/*  55 */         PvPMatchmaking.joined -= 1;
/*  56 */         Bukkit.broadcastMessage(PvPMatchmaking.Pre + " Temos [" + PvPMatchmaking.joined + "] jogadores no torneio 1x1..");
/*  57 */         PvPMatchmaking.jplayer5 = false;
/*  58 */         PvPMatchmaking.joinedPlayers.remove(e);
/*     */       }
/*  60 */       if (PvPMatchmaking.player6.contains(e)) {
/*  61 */         PvPMatchmaking.player6.remove(e);
/*  62 */         PvPMatchmaking.joined -= 1;
/*  63 */         Bukkit.broadcastMessage(PvPMatchmaking.Pre + " Temos [" + PvPMatchmaking.joined + "] jogadores no torneio 1x1..");
/*  64 */         PvPMatchmaking.jplayer6 = false;
/*  65 */         PvPMatchmaking.joinedPlayers.remove(e);
/*     */       }
/*  67 */       if (PvPMatchmaking.player7.contains(e)) {
/*  68 */         PvPMatchmaking.player7.remove(e);
/*  69 */         PvPMatchmaking.joined -= 1;
/*  70 */         Bukkit.broadcastMessage(PvPMatchmaking.Pre + " Temos [" + PvPMatchmaking.joined + "] jogadores no torneio 1x1..");
/*  71 */         PvPMatchmaking.jplayer7 = false;
/*  72 */         PvPMatchmaking.joinedPlayers.remove(e);
/*     */       }
/*  74 */       if (PvPMatchmaking.player8.contains(e)) {
/*  75 */         PvPMatchmaking.player8.remove(e);
/*  76 */         PvPMatchmaking.joined -= 1;
/*  77 */         Bukkit.broadcastMessage(PvPMatchmaking.Pre + " Temos [" + PvPMatchmaking.joined + "] jogadores no torneio 1x1..");
/*  78 */         PvPMatchmaking.jplayer8 = false;
/*  79 */         PvPMatchmaking.joinedPlayers.remove(e);
/*     */       }
/*  81 */       if (PvPMatchmaking.player9.contains(e)) {
/*  82 */         PvPMatchmaking.player9.remove(e);
/*  83 */         PvPMatchmaking.joined -= 1;
/*  84 */         Bukkit.broadcastMessage(PvPMatchmaking.Pre + " Temos [" + PvPMatchmaking.joined + "] jogadores no torneio 1x1..");
/*  85 */         PvPMatchmaking.jplayer9 = false;
/*  86 */         PvPMatchmaking.joinedPlayers.remove(e);
/*     */       }
/*  88 */       if (PvPMatchmaking.player10.contains(e)) {
/*  89 */         PvPMatchmaking.player10.remove(e);
/*  90 */         PvPMatchmaking.joined -= 1;
/*  91 */         Bukkit.broadcastMessage(PvPMatchmaking.Pre + " Temos [" + PvPMatchmaking.joined + "] jogadores no torneio 1x1..");
/*  92 */         PvPMatchmaking.jplayer10 = false;
/*  93 */         PvPMatchmaking.joinedPlayers.remove(e);
/*     */       }
/*  95 */       if (PvPMatchmaking.player11.contains(e)) {
/*  96 */         PvPMatchmaking.player11.remove(e);
/*  97 */         PvPMatchmaking.joined -= 1;
/*  98 */         Bukkit.broadcastMessage(PvPMatchmaking.Pre + " Temos [" + PvPMatchmaking.joined + "] jogadores no torneio 1x1..");
/*  99 */         PvPMatchmaking.jplayer11 = false;
/* 100 */         PvPMatchmaking.joinedPlayers.remove(e);
/*     */       }
/* 102 */       if (PvPMatchmaking.player12.contains(e)) {
/* 103 */         PvPMatchmaking.player12.remove(e);
/* 104 */         PvPMatchmaking.joined -= 1;
/* 105 */         Bukkit.broadcastMessage(PvPMatchmaking.Pre + " Temos [" + PvPMatchmaking.joined + "] jogadores no torneio 1x1..");
/* 106 */         PvPMatchmaking.jplayer12 = false;
/* 107 */         PvPMatchmaking.joinedPlayers.remove(e);
/*     */       }
/* 109 */       if (PvPMatchmaking.player13.contains(e)) {
/* 110 */         PvPMatchmaking.player13.remove(e);
/* 111 */         PvPMatchmaking.joined -= 1;
/* 112 */         Bukkit.broadcastMessage(PvPMatchmaking.Pre + " Temos [" + PvPMatchmaking.joined + "] jogadores no torneio 1x1..");
/* 113 */         PvPMatchmaking.jplayer13 = false;
/* 114 */         PvPMatchmaking.joinedPlayers.remove(e);
/*     */       }
/* 116 */       if (PvPMatchmaking.player14.contains(e)) {
/* 117 */         PvPMatchmaking.player14.remove(e);
/* 118 */         PvPMatchmaking.joined -= 1;
/* 119 */         Bukkit.broadcastMessage(PvPMatchmaking.Pre + " Temos [" + PvPMatchmaking.joined + "] jogadores no torneio 1x1..");
/* 120 */         PvPMatchmaking.jplayer14 = false;
/* 121 */         PvPMatchmaking.joinedPlayers.remove(e);
/*     */       }
/* 123 */       if (PvPMatchmaking.player15.contains(e)) {
/* 124 */         PvPMatchmaking.player15.remove(e);
/* 125 */         PvPMatchmaking.joined -= 1;
/* 126 */         Bukkit.broadcastMessage(PvPMatchmaking.Pre + " Temos [" + PvPMatchmaking.joined + "] jogadores no torneio 1x1..");
/* 127 */         PvPMatchmaking.jplayer15 = false;
/* 128 */         PvPMatchmaking.joinedPlayers.remove(e);
/*     */       }
/* 130 */       if (PvPMatchmaking.player16.contains(e)) {
/* 131 */         PvPMatchmaking.player16.remove(e);
/* 132 */         PvPMatchmaking.joined -= 1;
/* 133 */         Bukkit.broadcastMessage(PvPMatchmaking.Pre + " Temos [" + PvPMatchmaking.joined + "] jogadores no torneio 1x1..");
/* 134 */         PvPMatchmaking.jplayer16 = false;
/* 135 */         PvPMatchmaking.joinedPlayers.remove(e);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\User\Downloads\PvPMatchmaking_v1.2.jar!\me\Doubts\Listeners\LeaveListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */