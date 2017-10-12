/*     */ package nativelevel.arena1x1.Runnables;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import nativelevel.arena1x1.PvPMatchmaking.PvPMatchmaking;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
/*     */ 
/*     */ 
/*     */ public class Disqualified
/*     */   implements Runnable
/*     */ {
/*     */   public static PvPMatchmaking plugin;
/*     */   
/*  12 */   public Disqualified(PvPMatchmaking instance) { plugin = instance; }
/*     */   
/*     */   public void run() {
/*     */     org.bukkit.entity.Player[] arrayOfPlayer;
/*  16 */     for(Player player : Bukkit.getOnlinePlayers()) {
/*  17 */       if (PvPMatchmaking.round1) {
/*  18 */         if ((PvPMatchmaking.player1.isEmpty()) && (!PvPMatchmaking.player2.isEmpty()) && (PvPMatchmaking.inGame.contains(player))) {
/*  19 */           PvPMatchmaking.inGame.remove(player);
/*  20 */           if (PvPMatchmaking.player1.contains(player)) {
/*  21 */             PvPMatchmaking.setWinner("Start", 1, player);
/*  22 */             PvPMatchmaking.teleportToArena("Start", 2);
/*     */           }
/*  24 */           return; }
/*  25 */         if ((PvPMatchmaking.player2.isEmpty()) && (!PvPMatchmaking.player1.isEmpty()) && (PvPMatchmaking.inGame.contains(player))) {
/*  26 */           PvPMatchmaking.inGame.remove(player);
/*  27 */           if (PvPMatchmaking.player2.contains(player)) {
/*  28 */             PvPMatchmaking.setWinner("Start", 1, player);
/*  29 */             PvPMatchmaking.teleportToArena("Start", 2);
/*     */           }
/*  31 */           return; }
/*  32 */         if ((PvPMatchmaking.player3.isEmpty()) && (!PvPMatchmaking.player4.isEmpty()) && (PvPMatchmaking.inGame.contains(player))) {
/*  33 */           PvPMatchmaking.inGame.remove(player);
/*  34 */           if (PvPMatchmaking.player3.contains(player)) {
/*  35 */             PvPMatchmaking.setWinner("Start", 2, player);
/*  36 */             PvPMatchmaking.teleportToArena("Start", 3);
/*     */           }
/*  38 */           return; }
/*  39 */         if ((PvPMatchmaking.player4.isEmpty()) && (!PvPMatchmaking.player3.isEmpty()) && (PvPMatchmaking.inGame.contains(player))) {
/*  40 */           PvPMatchmaking.inGame.remove(player);
/*  41 */           if (PvPMatchmaking.player4.contains(player)) {
/*  42 */             PvPMatchmaking.setWinner("Start", 2, player);
/*  43 */             PvPMatchmaking.teleportToArena("Start", 3);
/*     */           }
/*  45 */           return; }
/*  46 */         if ((PvPMatchmaking.player5.isEmpty()) && (!PvPMatchmaking.player6.isEmpty()) && (PvPMatchmaking.inGame.contains(player))) {
/*  47 */           PvPMatchmaking.inGame.remove(player);
/*  48 */           if (PvPMatchmaking.player5.contains(player)) {
/*  49 */             PvPMatchmaking.setWinner("Start", 3, player);
/*  50 */             PvPMatchmaking.teleportToArena("Start", 4);
/*     */           }
/*  52 */           return; }
/*  53 */         if ((PvPMatchmaking.player6.isEmpty()) && (!PvPMatchmaking.player5.isEmpty()) && (PvPMatchmaking.inGame.contains(player))) {
/*  54 */           PvPMatchmaking.inGame.remove(player);
/*  55 */           if (PvPMatchmaking.player6.contains(player)) {
/*  56 */             PvPMatchmaking.setWinner("Start", 3, player);
/*  57 */             PvPMatchmaking.teleportToArena("Start", 4);
/*     */           }
/*  59 */           return; }
/*  60 */         if ((PvPMatchmaking.player7.isEmpty()) && (!PvPMatchmaking.player8.isEmpty()) && (PvPMatchmaking.inGame.contains(player))) {
/*  61 */           PvPMatchmaking.inGame.remove(player);
/*  62 */           if (PvPMatchmaking.player7.contains(player)) {
/*  63 */             PvPMatchmaking.setWinner("Start", 4, player);
/*  64 */             PvPMatchmaking.teleportToArena("Start", 5);
/*     */           }
/*  66 */           return; }
/*  67 */         if ((PvPMatchmaking.player8.isEmpty()) && (!PvPMatchmaking.player7.isEmpty()) && (PvPMatchmaking.inGame.contains(player))) {
/*  68 */           PvPMatchmaking.inGame.remove(player);
/*  69 */           if (PvPMatchmaking.player8.contains(player)) {
/*  70 */             PvPMatchmaking.setWinner("Start", 4, player);
/*  71 */             PvPMatchmaking.teleportToArena("Start", 5);
/*     */           }
/*  73 */           return; }
/*  74 */         if ((PvPMatchmaking.player9.isEmpty()) && (!PvPMatchmaking.player10.isEmpty()) && (PvPMatchmaking.inGame.contains(player))) {
/*  75 */           PvPMatchmaking.inGame.remove(player);
/*  76 */           if (PvPMatchmaking.player9.contains(player)) {
/*  77 */             PvPMatchmaking.setWinner("Start", 5, player);
/*  78 */             PvPMatchmaking.teleportToArena("Start", 6);
/*     */           }
/*  80 */           return; }
/*  81 */         if ((PvPMatchmaking.player10.isEmpty()) && (!PvPMatchmaking.player9.isEmpty()) && (PvPMatchmaking.inGame.contains(player))) {
/*  82 */           PvPMatchmaking.inGame.remove(player);
/*  83 */           if (PvPMatchmaking.player10.contains(player)) {
/*  84 */             PvPMatchmaking.setWinner("Start", 5, player);
/*  85 */             PvPMatchmaking.teleportToArena("Start", 6);
/*     */           }
/*  87 */           return; }
/*  88 */         if ((PvPMatchmaking.player11.isEmpty()) && (!PvPMatchmaking.player12.isEmpty()) && (PvPMatchmaking.inGame.contains(player))) {
/*  89 */           PvPMatchmaking.inGame.remove(player);
/*  90 */           if (PvPMatchmaking.player11.contains(player)) {
/*  91 */             PvPMatchmaking.setWinner("Start", 6, player);
/*  92 */             PvPMatchmaking.teleportToArena("Start", 7);
/*     */           }
/*  94 */           return; }
/*  95 */         if ((PvPMatchmaking.player12.isEmpty()) && (!PvPMatchmaking.player11.isEmpty()) && (PvPMatchmaking.inGame.contains(player))) {
/*  96 */           PvPMatchmaking.inGame.remove(player);
/*  97 */           if (PvPMatchmaking.player12.contains(player)) {
/*  98 */             PvPMatchmaking.setWinner("Start", 6, player);
/*  99 */             PvPMatchmaking.teleportToArena("Start", 7);
/*     */           }
/* 101 */           return; }
/* 102 */         if ((PvPMatchmaking.player13.isEmpty()) && (!PvPMatchmaking.player14.isEmpty()) && (PvPMatchmaking.inGame.contains(player))) {
/* 103 */           PvPMatchmaking.inGame.remove(player);
/* 104 */           if (PvPMatchmaking.player13.contains(player)) {
/* 105 */             PvPMatchmaking.setWinner("Start", 7, player);
/* 106 */             PvPMatchmaking.teleportToArena("Start", 8);
/*     */           }
/* 108 */           return; }
/* 109 */         if ((PvPMatchmaking.player14.isEmpty()) && (!PvPMatchmaking.player13.isEmpty()) && (PvPMatchmaking.inGame.contains(player))) {
/* 110 */           PvPMatchmaking.inGame.remove(player);
/* 111 */           if (PvPMatchmaking.player14.contains(player)) {
/* 112 */             PvPMatchmaking.setWinner("Start", 7, player);
/* 113 */             PvPMatchmaking.teleportToArena("Start", 8);
/*     */           }
/* 115 */           return; }
/* 116 */         if ((PvPMatchmaking.player15.isEmpty()) && (!PvPMatchmaking.player16.isEmpty()) && (PvPMatchmaking.inGame.contains(player))) {
/* 117 */           PvPMatchmaking.inGame.remove(player);
/* 118 */           if (PvPMatchmaking.player15.contains(player)) {
/* 119 */             PvPMatchmaking.setWinner("Start", 8, player);
/* 120 */             PvPMatchmaking.teleportToArena("Quarter", 1);
/*     */           }
/* 122 */           return; }
/* 123 */         if ((PvPMatchmaking.player16.isEmpty()) && (!PvPMatchmaking.player15.isEmpty()) && (PvPMatchmaking.inGame.contains(player))) {
/* 124 */           PvPMatchmaking.inGame.remove(player);
/* 125 */           if (PvPMatchmaking.player16.contains(player)) {
/* 126 */             PvPMatchmaking.setWinner("Start", 8, player);
/* 127 */             PvPMatchmaking.teleportToArena("Quarter", 1);
/*     */           }
/*     */         }
/*     */       } else {
/* 131 */         if ((PvPMatchmaking.wplayer1.isEmpty()) && (!PvPMatchmaking.wplayer2.isEmpty()) && (PvPMatchmaking.inGame.contains(player))) {
/* 132 */           PvPMatchmaking.inGame.remove(player);
/* 133 */           if (PvPMatchmaking.wplayer1.contains(player)) {
/* 134 */             PvPMatchmaking.setWinner("Quarter", 1, player);
/* 135 */             PvPMatchmaking.teleportToArena("Quarter", 2);
/*     */           }
/* 137 */           return; }
/* 138 */         if ((PvPMatchmaking.wplayer2.isEmpty()) && (!PvPMatchmaking.wplayer1.isEmpty()) && (PvPMatchmaking.inGame.contains(player))) {
/* 139 */           PvPMatchmaking.inGame.remove(player);
/* 140 */           if (PvPMatchmaking.wplayer2.contains(player)) {
/* 141 */             PvPMatchmaking.setWinner("Quarter", 1, player);
/* 142 */             PvPMatchmaking.teleportToArena("Quarter", 2);
/*     */           }
/* 144 */           return; }
/* 145 */         if ((PvPMatchmaking.wplayer3.isEmpty()) && (!PvPMatchmaking.wplayer4.isEmpty()) && (PvPMatchmaking.inGame.contains(player))) {
/* 146 */           PvPMatchmaking.inGame.remove(player);
/* 147 */           if (PvPMatchmaking.wplayer3.contains(player)) {
/* 148 */             PvPMatchmaking.setWinner("Quarter", 2, player);
/* 149 */             PvPMatchmaking.teleportToArena("Quarter", 3);
/*     */           }
/* 151 */           return; }
/* 152 */         if ((PvPMatchmaking.wplayer4.isEmpty()) && (!PvPMatchmaking.wplayer3.isEmpty()) && (PvPMatchmaking.inGame.contains(player))) {
/* 153 */           PvPMatchmaking.inGame.remove(player);
/* 154 */           if (PvPMatchmaking.wplayer4.contains(player)) {
/* 155 */             PvPMatchmaking.setWinner("Quarter", 2, player);
/* 156 */             PvPMatchmaking.teleportToArena("Quarter", 3);
/*     */           }
/* 158 */           return; }
/* 159 */         if ((PvPMatchmaking.wplayer5.isEmpty()) && (!PvPMatchmaking.wplayer6.isEmpty()) && (PvPMatchmaking.inGame.contains(player))) {
/* 160 */           PvPMatchmaking.inGame.remove(player);
/* 161 */           if (PvPMatchmaking.wplayer5.contains(player)) {
/* 162 */             PvPMatchmaking.setWinner("Quarter", 3, player);
/* 163 */             PvPMatchmaking.teleportToArena("Quarter", 4);
/*     */           }
/* 165 */           return; }
/* 166 */         if ((PvPMatchmaking.wplayer6.isEmpty()) && (!PvPMatchmaking.wplayer5.isEmpty()) && (PvPMatchmaking.inGame.contains(player))) {
/* 167 */           PvPMatchmaking.inGame.remove(player);
/* 168 */           if (PvPMatchmaking.wplayer6.contains(player)) {
/* 169 */             PvPMatchmaking.setWinner("Quarter", 3, player);
/* 170 */             PvPMatchmaking.teleportToArena("Quarter", 4);
/*     */           }
/* 172 */           return; }
/* 173 */         if ((PvPMatchmaking.wplayer7.isEmpty()) && (!PvPMatchmaking.wplayer8.isEmpty()) && (PvPMatchmaking.inGame.contains(player))) {
/* 174 */           PvPMatchmaking.inGame.remove(player);
/* 175 */           if (PvPMatchmaking.wplayer7.contains(player)) {
/* 176 */             PvPMatchmaking.setWinner("Quarter", 4, player);
/* 177 */             PvPMatchmaking.teleportToArena("Semi Final", 1);
/*     */           }
/* 179 */           return; }
/* 180 */         if ((PvPMatchmaking.wplayer8.isEmpty()) && (!PvPMatchmaking.wplayer7.isEmpty()) && (PvPMatchmaking.inGame.contains(player))) {
/* 181 */           PvPMatchmaking.inGame.remove(player);
/* 182 */           if (PvPMatchmaking.wplayer8.contains(player)) {
/* 183 */             PvPMatchmaking.setWinner("Quarter", 4, player);
/* 184 */             PvPMatchmaking.teleportToArena("Semi Final", 1);
/*     */           }
/* 186 */           return; }
/* 187 */         if ((PvPMatchmaking.Quarter1.isEmpty()) && (!PvPMatchmaking.Quarter2.isEmpty()) && (PvPMatchmaking.inGame.contains(player))) {
/* 188 */           PvPMatchmaking.inGame.remove(player);
/* 189 */           if (PvPMatchmaking.Quarter1.contains(player)) {
/* 190 */             PvPMatchmaking.setWinner("Semi Final", 1, player);
/* 191 */             PvPMatchmaking.teleportToArena("Semi Final", 2);
/*     */           }
/* 193 */           return; }
/* 194 */         if ((PvPMatchmaking.Quarter2.isEmpty()) && (!PvPMatchmaking.Quarter1.isEmpty()) && (PvPMatchmaking.inGame.contains(player))) {
/* 195 */           PvPMatchmaking.inGame.remove(player);
/* 196 */           if (PvPMatchmaking.Quarter2.contains(player)) {
/* 197 */             PvPMatchmaking.setWinner("Semi Final", 1, player);
/* 198 */             PvPMatchmaking.teleportToArena("Semi Final", 2);
/*     */           }
/* 200 */           return; }
/* 201 */         if ((PvPMatchmaking.Quarter3.isEmpty()) && (!PvPMatchmaking.Quarter4.isEmpty()) && (PvPMatchmaking.inGame.contains(player))) {
/* 202 */           PvPMatchmaking.inGame.remove(player);
/* 203 */           if (PvPMatchmaking.Quarter3.contains(player)) {
/* 204 */             PvPMatchmaking.setWinner("Semi Final", 2, player);
/* 205 */             PvPMatchmaking.teleportToArena("Final", 1);
/*     */           }
/* 207 */           return; }
/* 208 */         if ((PvPMatchmaking.Quarter4.isEmpty()) && (!PvPMatchmaking.Quarter3.isEmpty()) && (PvPMatchmaking.inGame.contains(player))) {
/* 209 */           PvPMatchmaking.inGame.remove(player);
/* 210 */           if (PvPMatchmaking.Quarter4.contains(player)) {
/* 211 */             PvPMatchmaking.setWinner("Semi Final", 2, player);
/* 212 */             PvPMatchmaking.teleportToArena("Final", 1);
/*     */           }
/* 214 */           return; }
/* 215 */         if ((PvPMatchmaking.Semi1.isEmpty()) && (!PvPMatchmaking.Semi2.isEmpty()) && (PvPMatchmaking.inGame.contains(player))) {
/* 216 */           PvPMatchmaking.inGame.remove(player);
/* 217 */           if (PvPMatchmaking.Semi1.contains(player)) {
/* 218 */             PvPMatchmaking.setWinner("Final", 1, player);
/*     */           }
/* 220 */           return; }
/* 221 */         if ((PvPMatchmaking.Semi2.isEmpty()) && (!PvPMatchmaking.Semi1.isEmpty()) && (PvPMatchmaking.inGame.contains(player))) {
/* 222 */           PvPMatchmaking.inGame.remove(player);
/* 223 */           if (PvPMatchmaking.Semi2.contains(player)) {
/* 224 */             PvPMatchmaking.setWinner("Final", 1, player);
/*     */           }
/* 226 */           return;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\User\Downloads\PvPMatchmaking_v1.2.jar!\me\Doubts\Runnables\Disqualified.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */