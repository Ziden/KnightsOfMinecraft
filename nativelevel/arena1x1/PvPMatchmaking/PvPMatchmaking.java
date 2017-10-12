/*     */ package nativelevel.arena1x1.PvPMatchmaking;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.logging.Logger;
/*     */ import nativelevel.arena1x1.Listeners.DamageListener;
/*     */ import nativelevel.arena1x1.Listeners.DeathListener;
/*     */ import nativelevel.arena1x1.Listeners.Freeze;
/*     */ import nativelevel.arena1x1.Listeners.LeaveListener;
/*     */ import nativelevel.arena1x1.Listeners.NoCommands;
/*     */ import nativelevel.arena1x1.Listeners.SettingUp;
/*     */ import nativelevel.arena1x1.Listeners.Spectators;
/*     */ import nativelevel.arena1x1.Runnables.Disqualified;
/*     */ import nativelevel.arena1x1.Runnables.GameStarter;
import nativelevel.KoM;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.configuration.file.FileConfiguration;
/*     */ import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.configuration.file.YamlConfiguration;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.plugin.PluginDescriptionFile;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ import org.bukkit.plugin.java.JavaPlugin;
/*     */ import org.bukkit.scheduler.BukkitScheduler;
/*     */ 
/*     */ public class PvPMatchmaking implements CommandExecutor
/*     */ {
/*     */   public static PvPMatchmaking plugin;
/*  35 */   public static ChatColor red = ChatColor.RED;
/*  36 */   public static ChatColor blue = ChatColor.GREEN;
/*  37 */   public static ChatColor yellow = ChatColor.YELLOW;
/*  38 */   public static ChatColor green = ChatColor.GREEN;
/*  39 */   public static ChatColor black = ChatColor.BLACK;
/*     */   
/*  41 */   public final LeaveListener ll = new LeaveListener(this);
/*  42 */   public final DeathListener dl = new DeathListener(this);
/*  43 */   public final Freeze fl = new Freeze(this);
/*  44 */   public final Spectators sp = new Spectators(this);
/*  45 */   public final DamageListener d = new DamageListener(this);
/*  46 */   public final NoCommands nc = new NoCommands(this);
/*     */   
/*     */   public static FileConfiguration cfg;
/*     */   
/*  50 */   public final Logger logger = Logger.getLogger("Minecraft");
/*     */   
/*  52 */   public static String Pre = ChatColor.GOLD + "[Torneio]" + ChatColor.GREEN;
/*     */   
/*  54 */   public static boolean gameStarted = false;
/*  55 */   public static boolean gameInitiated = false;
/*  56 */   public static boolean jplayer1 = false;
/*  57 */   public static boolean jplayer2 = false;
/*  58 */   public static boolean jplayer3 = false;
/*  59 */   public static boolean jplayer4 = false;
/*  60 */   public static boolean jplayer5 = false;
/*  61 */   public static boolean jplayer6 = false;
/*  62 */   public static boolean jplayer7 = false;
/*  63 */   public static boolean jplayer8 = false;
/*  64 */   public static boolean jplayer9 = false;
/*  65 */   public static boolean jplayer10 = false;
/*  66 */   public static boolean jplayer11 = false;
/*  67 */   public static boolean jplayer12 = false;
/*  68 */   public static boolean jplayer13 = false;
/*  69 */   public static boolean jplayer14 = false;
/*  70 */   public static boolean jplayer15 = false;
/*  71 */   public static boolean jplayer16 = false;
/*     */   public static boolean madeArena;
/*  73 */   public static boolean beginCreation = false;
/*  74 */   public static boolean a1b2 = false;
/*  75 */   public static boolean sp1 = false;
/*  76 */   public static boolean sp2 = false;
/*  77 */   public static boolean ssp = false;
/*  78 */   public static boolean sel = false;
/*  79 */   public static boolean finished = false;
/*  80 */   public static boolean creating = false;
/*     */   
/*  82 */   public static ArrayList<Player> player1 = new ArrayList();
/*  83 */   public static ArrayList<Player> player2 = new ArrayList();
/*  84 */   public static ArrayList<Player> player3 = new ArrayList();
/*  85 */   public static ArrayList<Player> player4 = new ArrayList();
/*  86 */   public static ArrayList<Player> player5 = new ArrayList();
/*  87 */   public static ArrayList<Player> player6 = new ArrayList();
/*  88 */   public static ArrayList<Player> player7 = new ArrayList();
/*  89 */   public static ArrayList<Player> player8 = new ArrayList();
/*  90 */   public static ArrayList<Player> player9 = new ArrayList();
/*  91 */   public static ArrayList<Player> player10 = new ArrayList();
/*  92 */   public static ArrayList<Player> player11 = new ArrayList();
/*  93 */   public static ArrayList<Player> player12 = new ArrayList();
/*  94 */   public static ArrayList<Player> player13 = new ArrayList();
/*  95 */   public static ArrayList<Player> player14 = new ArrayList();
/*  96 */   public static ArrayList<Player> player15 = new ArrayList();
/*  97 */   public static ArrayList<Player> player16 = new ArrayList();
/*  98 */   public static ArrayList<Player> wplayer1 = new ArrayList();
/*  99 */   public static ArrayList<Player> wplayer2 = new ArrayList();
/* 100 */   public static ArrayList<Player> wplayer3 = new ArrayList();
/* 101 */   public static ArrayList<Player> wplayer4 = new ArrayList();
/* 102 */   public static ArrayList<Player> wplayer5 = new ArrayList();
/* 103 */   public static ArrayList<Player> wplayer6 = new ArrayList();
/* 104 */   public static ArrayList<Player> wplayer7 = new ArrayList();
/* 105 */   public static ArrayList<Player> wplayer8 = new ArrayList();
/* 106 */   public static ArrayList<Player> Quarter1 = new ArrayList();
/* 107 */   public static ArrayList<Player> Quarter2 = new ArrayList();
/* 108 */   public static ArrayList<Player> Quarter3 = new ArrayList();
/* 109 */   public static ArrayList<Player> Quarter4 = new ArrayList();
/* 110 */   public static ArrayList<Player> Semi1 = new ArrayList();
/* 111 */   public static ArrayList<Player> Semi2 = new ArrayList();
/* 112 */   public static ArrayList<Player> Final = new ArrayList();
/* 113 */   public static ArrayList<Player> joinedPlayers = new ArrayList();
/* 114 */   public static ArrayList<Player> spectating = new ArrayList();
/* 115 */   public static HashMap<Player, Long> freeze1 = new HashMap();
/* 116 */   public static HashMap<Player, Long> freeze2 = new HashMap();
/* 117 */   public static ArrayList<Player> inGame = new ArrayList();
/*     */   
/* 119 */   public static boolean round1 = false;
/* 120 */   public static boolean round2 = false;
/* 121 */   public static boolean quarter = false;
/* 122 */   public static boolean semifinal = false;
/* 123 */   public static boolean finals = false;
/*     */   
/* 125 */   public static int joined = 0;
/*     */   public static int playersNeeded;
/*     */   /*     */   
/*     */   public void onEnable() {
/* 129 */     PluginManager pm = KoM._instance.getServer().getPluginManager();
/* 130 */     PluginDescriptionFile pdf = KoM._instance.getDescription();
/*     */     try {
/* 133 */       this.logger.info("[" + pdf.getName() + "] was enabled!");
/* 134 */       File PvP = new File("plugins" + File.separator + "Kom" + File.separator + "configArena.yml");
/* 135 */       File path = KoM._instance.getDataFolder();
/* 136 */       if (!path.exists()) path.mkdir();
/* 137 */       if (!PvP.exists()) PvP.createNewFile();
 cfg = new YamlConfiguration();
 cfg.load(PvP);
                Bukkit.getPluginCommand("pvpm").setExecutor(this);
/* 138 */       Bukkit.getServer().getScheduler().scheduleAsyncRepeatingTask(KoM._instance, new GameStarter(this), 0L, 10L);
/* 140 */       Bukkit.getServer().getScheduler().scheduleAsyncRepeatingTask(KoM._instance, new Disqualified(this), 0L, 10L);
/* 141 */      

                //pm.registerEvents(this.ll, KoM._instance);
/* 142 */       //pm.registerEvents(new SettingUp(this), KoM._instance);
/* 143 */       //pm.registerEvents(this.sp, KoM._instance);
/* 144 */       //pm.registerEvents(this.dl, KoM._instance);
/* 145 */       //pm.registerEvents(this.fl, KoM._instance);
/* 146 */       //pm.registerEvents(this.d, KoM._instance);
/* 147 */       //pm.registerEvents(this.nc, KoM._instance);
/*     */       
/* 149 */       cfg.addDefault("PvPMatchmaking.MadeArena", Boolean.valueOf(false));
/* 150 */       cfg.addDefault("PvPMatchmaking.Locations.World", "NoneSet");
/* 151 */       cfg.addDefault("PvPMatchmaking.Locations.Arena.block1.x", Integer.valueOf(0));
/* 152 */       cfg.addDefault("PvPMatchmaking.Locations.Arena.block1.y", Integer.valueOf(0));
/* 153 */       cfg.addDefault("PvPMatchmaking.Locations.Arena.block1.z", Integer.valueOf(0));
/* 154 */       cfg.addDefault("PvPMatchmaking.Locations.Arena.block2.x", Integer.valueOf(0));
/* 155 */       cfg.addDefault("PvPMatchmaking.Locations.Arena.block2.y", Integer.valueOf(0));
/* 156 */       cfg.addDefault("PvPMatchmaking.Locations.Arena.block2.z", Integer.valueOf(0));
/*     */       
/* 158 */       cfg.addDefault("PvPMatchmaking.Locations.Arena.Player1Spawn.block.x", Integer.valueOf(0));
/* 159 */       cfg.addDefault("PvPMatchmaking.Locations.Arena.Player1Spawn.block.y", Integer.valueOf(0));
/* 160 */       cfg.addDefault("PvPMatchmaking.Locations.Arena.Player1Spawn.block.z", Integer.valueOf(0));
/* 161 */       cfg.addDefault("PvPMatchmaking.Locations.Arena.Player2Spawn.block.x", Integer.valueOf(0));
/* 162 */       cfg.addDefault("PvPMatchmaking.Locations.Arena.Player2Spawn.block.y", Integer.valueOf(0));
/* 163 */       cfg.addDefault("PvPMatchmaking.Locations.Arena.Player2Spawn.block.z", Integer.valueOf(0));
/* 164 */       cfg.addDefault("PvPMatchmaking.Locations.Spectator.Spawn.block.x", Integer.valueOf(0));
/* 165 */       cfg.addDefault("PvPMatchmaking.Locations.Spectator.Spawn.block.y", Integer.valueOf(0));
/* 166 */       cfg.addDefault("PvPMatchmaking.Locations.Spectator.Spawn.block.z", Integer.valueOf(0));
/* 167 */       cfg.addDefault("PvPMatchmaking.Locations.Arena.Exit.world", "");
/* 168 */       cfg.addDefault("PvPMatchmaking.Locations.Arena.Exit.block.x", Integer.valueOf(0));
/* 169 */       cfg.addDefault("PvPMatchmaking.Locations.Arena.Exit.block.y", Integer.valueOf(0));
/* 170 */       cfg.addDefault("PvPMatchmaking.Locations.Arena.Exit.block.z", Integer.valueOf(0));
/*     */       
/* 172 */       cfg.addDefault("PvPMatchmaking.PlayersNeededToStartGame(Even Number)", Integer.valueOf(6));
/*     */       
/* 174 */       cfg.addDefault("PvPMatchmaking.Winners.Round1.Team1", "none");
/* 175 */       cfg.addDefault("PvPMatchmaking.Winners.Round1.Team2", "none");
/* 176 */       cfg.addDefault("PvPMatchmaking.Winners.Round1.Team3", "none");
/* 177 */       cfg.addDefault("PvPMatchmaking.Winners.Round1.Team4", "none");
/* 178 */       cfg.addDefault("PvPMatchmaking.Winners.Round1.Team5", "none");
/* 179 */       cfg.addDefault("PvPMatchmaking.Winners.Round1.Team6", "none");
/* 180 */       cfg.addDefault("PvPMatchmaking.Winners.Round1.Team7", "none");
/* 181 */       cfg.addDefault("PvPMatchmaking.Winners.Round1.Team8", "none");
/* 182 */       cfg.addDefault("PvPMatchmaking.Winners.Quarter.Team1", "none");
/* 183 */       cfg.addDefault("PvPMatchmaking.Winners.Quarter.Team2", "none");
/* 184 */       cfg.addDefault("PvPMatchmaking.Winners.Quarter.Team3", "none");
/* 185 */       cfg.addDefault("PvPMatchmaking.Winners.Quarter.Team4", "none");
/* 186 */       cfg.addDefault("PvPMatchmaking.Winners.SemiFinal.Team1", "none");
/* 187 */       cfg.addDefault("PvPMatchmaking.Winners.SemiFinal.Team2", "none");
/* 188 */       cfg.addDefault("PvPMatchmaking.Winners.Final.Team1", "none");
/* 189 */       cfg.addDefault("PvPMatchmaking.Winners.Final.Winner", "none");
/* 190 */       cfg.options().copyDefaults(true);
/* 191 */       KoM._instance.saveConfig();
/* 192 */       playersNeeded = cfg.getInt("PvPMatchmaking.PlayersNeededToStartGame(Even Number)");
/* 193 */       ResetGame();
/*     */     } catch (Exception e) {
/* 195 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   public void onDisable() {
/* 200 */     cfg.set("PvPMatchmaking.Winners.Round1.Team1", "none");
/* 201 */     cfg.set("PvPMatchmaking.Winners.Round1.Team2", "none");
/* 202 */     cfg.set("PvPMatchmaking.Winners.Round1.Team3", "none");
/* 203 */     cfg.set("PvPMatchmaking.Winners.Round1.Team4", "none");
/* 204 */     cfg.set("PvPMatchmaking.Winners.Round1.Team5", "none");
/* 205 */     cfg.set("PvPMatchmaking.Winners.Round1.Team6", "none");
/* 206 */     cfg.set("PvPMatchmaking.Winners.Round1.Team7", "none");
/* 207 */     cfg.set("PvPMatchmaking.Winners.Round1.Team8", "none");
/* 208 */     cfg.set("PvPMatchmaking.Winners.Quarter.Team1", "none");
/* 209 */     cfg.set("PvPMatchmaking.Winners.Quarter.Team2", "none");
/* 210 */     cfg.set("PvPMatchmaking.Winners.Quarter.Team3", "none");
/* 211 */     cfg.set("PvPMatchmaking.Winners.Quarter.Team4", "none");
/* 212 */     cfg.set("PvPMatchmaking.Winners.SemiFinal.Team1", "none");
/* 213 */     cfg.set("PvPMatchmaking.Winners.SemiFinal.Team2", "none");
/* 214 */     cfg.set("PvPMatchmaking.Winners.Final.Team1", "none");
/* 215 */     cfg.set("PvPMatchmaking.Winners.Final.Winner", "none");
/*     */   }
/*     */   
/*     */   public static void ResetGame() { Player[] arrayOfPlayer;
/* 219 */     for(Player player : Bukkit.getOnlinePlayers()) {
/* 220 */       Location spawn = player.getWorld().getSpawnLocation();
/* 221 */       if ((inGame.contains(player)) || (spectating.contains(player)) || (joinedPlayers.contains(player))) {
/* 222 */         player.teleport(spawn);
/*     */       }
/* 224 */       player1.clear();
/* 225 */       player2.clear();
/* 226 */       player3.clear();
/* 227 */       player4.clear();
/* 228 */       player5.clear();
/* 229 */       player6.clear();
/* 230 */       player7.clear();
/* 231 */       player8.clear();
/* 232 */       player9.clear();
/* 233 */       player10.clear();
/* 234 */       player11.clear();
/* 235 */       player12.clear();
/* 236 */       player13.clear();
/* 237 */       player14.clear();
/* 238 */       player15.clear();
/* 239 */       player16.clear();
/* 240 */       Quarter1.clear();
/* 241 */       Quarter2.clear();
/* 242 */       Quarter3.clear();
/* 243 */       Quarter4.clear();
/* 244 */       Semi1.clear();
/* 245 */       Semi2.clear();
/* 246 */       Final.clear();
/* 247 */       inGame.clear();
/* 248 */       joinedPlayers.clear();
/* 249 */       spectating.clear();
/* 250 */       freeze1.clear();
/* 251 */       freeze2.clear();
/*     */     }
/*     */   }
/*     */   
/*     */   public static void setWinner(String round, int team, Player player)
/*     */   {
/* 257 */     String playerName = player.getName();
/* 258 */     File PvP = new File("plugins" + File.separator + "PvPMatchmaking" + File.separator + "config.yml");
/*     */     try {
/* 260 */       if (round.equalsIgnoreCase("Start")) {
/* 261 */         if (team == 1) {
/* 262 */           cfg.set("PvPMatchmaking.Winners.Round1.Team1", playerName);
/* 263 */           cfg.save(PvP);
/* 264 */         } else if (team == 2) {
/* 265 */           cfg.set("PvPMatchmaking.Winners.Round1.Team2", playerName);
/* 266 */           cfg.save(PvP);
/* 267 */         } else if (team == 3) {
/* 268 */           cfg.set("PvPMatchmaking.Winners.Round1.Team3", playerName);
/* 269 */           cfg.save(PvP);
/* 270 */         } else if (team == 4) {
/* 271 */           cfg.set("PvPMatchmaking.Winners.Round1.Team4", playerName);
/* 272 */           cfg.save(PvP);
/* 273 */         } else if (team == 5) {
/* 274 */           cfg.set("PvPMatchmaking.Winners.Round1.Team5", playerName);
/* 275 */           cfg.save(PvP);
/* 276 */         } else if (team == 6) {
/* 277 */           cfg.set("PvPMatchmaking.Winners.Round1.Team6", playerName);
/* 278 */           cfg.save(PvP);
/* 279 */         } else if (team == 7) {
/* 280 */           cfg.set("PvPMatchmaking.Winners.Round1.Team7", playerName);
/* 281 */           cfg.save(PvP);
/* 282 */         } else if (team == 8) {
/* 283 */           cfg.set("PvPMatchmaking.Winners.Round1.Team8", playerName);
/* 284 */           cfg.save(PvP);
/*     */         }
/* 286 */       } else if (round.equalsIgnoreCase("Quarter")) {
/* 287 */         if (team == 1) {
/* 288 */           cfg.set("PvPMatchmaking.Winners.Quarter.Team1", playerName);
/* 289 */           cfg.save(PvP);
/* 290 */         } else if (team == 2) {
/* 291 */           cfg.set("PvPMatchmaking.Winners.Quarter.Team2", playerName);
/* 292 */           cfg.save(PvP);
/* 293 */         } else if (team == 3) {
/* 294 */           cfg.set("PvPMatchmaking.Winners.Quarter.Team3", playerName);
/* 295 */           cfg.save(PvP);
/* 296 */         } else if (team == 4) {
/* 297 */           cfg.set("PvPMatchmaking.Winners.Quarter.Team4", playerName);
/* 298 */           cfg.save(PvP);
/*     */         }
/* 300 */       } else if (round.equalsIgnoreCase("Semi Final")) {
/* 301 */         if (team == 1) {
/* 302 */           cfg.set("PvPMatchmaking.Winners.SemiFinal.Team1", playerName);
/* 303 */           cfg.save(PvP);
/* 304 */         } else if (team == 2) {
/* 305 */           cfg.set("PvPMatchmaking.Winners.SemiFinal.Team2", playerName);
/* 306 */           cfg.save(PvP);
/*     */         }
/* 308 */       } else if ((round.equalsIgnoreCase("Final")) && 
/* 309 */         (team == 1)) {
/* 310 */         cfg.set("PvPMatchmaking.Winners.Final.Team1", playerName);
/* 311 */         cfg.save(PvP);
/* 312 */         cfg.set("PvPMatchmaking.Winners.Final.Winner", playerName);
/* 313 */         cfg.save(PvP);
/*     */       }
/*     */     }
/*     */     catch (Exception localException) {}
/*     */   }
/*     */   
/*     */   public static boolean isEmpty(ArrayList<Player> players1, ArrayList<Player> players2) {
/* 320 */     if ((players1.isEmpty()) && (players2.isEmpty())) {
/* 321 */       return true;
/*     */     }
/* 323 */     return false;
/*     */   }
/*     */   
/*     */   public static void teleportToArena(String round, int team) {
/* 327 */     Location spawn1 = new Location(Bukkit.getWorld(cfg.getString("PvPMatchmaking.Locations.World")), cfg.getInt("PvPMatchmaking.Locations.Arena.Player1Spawn.block.x"), cfg.getInt("PvPMatchmaking.Locations.Arena.Player1Spawn.block.y"), cfg.getInt("PvPMatchmaking.Locations.Arena.Player1Spawn.block.z"));
/* 328 */     Location spawn2 = new Location(Bukkit.getWorld(cfg.getString("PvPMatchmaking.Locations.World")), cfg.getInt("PvPMatchmaking.Locations.Arena.Player2Spawn.block.x"), cfg.getInt("PvPMatchmaking.Locations.Arena.Player2Spawn.block.y"), cfg.getInt("PvPMatchmaking.Locations.Arena.Player2Spawn.block.z"));
/* 329 */     Location spectator = new Location(Bukkit.getWorld(cfg.getString("PvPMatchmaking.Locations.World")), cfg.getInt("PvPMatchmaking.Locations.Spectator.Spawn.block.x"), cfg.getInt("PvPMatchmaking.Locations.Spectator.Spawn.block.y"), cfg.getInt("PvPMatchmaking.Locations.Spectator.Spawn.block.z"));
/* 330 */     Player[] arrayOfPlayer; for(Player player : Bukkit.getOnlinePlayers()) {
/* 331 */       if (round.equalsIgnoreCase("Start")) {
/* 332 */         if (team == 1) {
/* 333 */           if (player1.contains(player)) {
/* 334 */             player.teleport(spawn1);
/* 335 */             freeze1.put(player, Long.valueOf(System.currentTimeMillis()));
/* 336 */             inGame.add(player);
/* 337 */           } else if (player2.contains(player)) {
/* 338 */             player.teleport(spawn2);
/* 339 */             freeze2.put(player, Long.valueOf(System.currentTimeMillis()));
/* 340 */             inGame.add(player);
/* 341 */           } else if (((!player1.contains(player)) && (!player2.contains(player)) && (joinedPlayers.contains(player))) || (spectating.contains(player))) {
/* 342 */             player.teleport(spectator);
/* 343 */             spectating.add(player);
/*     */           }
/* 345 */         } else if (team == 2) {
/* 346 */           if (player3.contains(player)) {
/* 347 */             player.teleport(spawn1);
/* 348 */             freeze1.put(player, Long.valueOf(System.currentTimeMillis()));
/* 349 */             inGame.add(player);
/* 350 */           } else if (player4.contains(player)) {
/* 351 */             player.teleport(spawn2);
/* 352 */             freeze2.put(player, Long.valueOf(System.currentTimeMillis()));
/* 353 */             inGame.add(player);
/* 354 */           } else if (((!player3.contains(player)) && (!player4.contains(player)) && (joinedPlayers.contains(player))) || (spectating.contains(player))) {
/* 355 */             player.teleport(spectator);
/* 356 */             spectating.add(player);
/*     */           }
/* 358 */         } else if (team == 3) {
/* 359 */           if (player5.contains(player)) {
/* 360 */             player.teleport(spawn1);
/* 361 */             freeze1.put(player, Long.valueOf(System.currentTimeMillis()));
/* 362 */             inGame.add(player);
/* 363 */           } else if (player6.contains(player)) {
/* 364 */             player.teleport(spawn2);
/* 365 */             freeze2.put(player, Long.valueOf(System.currentTimeMillis()));
/* 366 */             inGame.add(player);
/* 367 */           } else if (((!player5.contains(player)) && (!player6.contains(player)) && (joinedPlayers.contains(player))) || (spectating.contains(player))) {
/* 368 */             player.teleport(spectator);
/* 369 */             spectating.add(player);
/*     */           }
/* 371 */         } else if (team == 4) {
/* 372 */           if (player7.contains(player)) {
/* 373 */             player.teleport(spawn1);
/* 374 */             freeze1.put(player, Long.valueOf(System.currentTimeMillis()));
/* 375 */             inGame.add(player);
/* 376 */           } else if (player8.contains(player)) {
/* 377 */             player.teleport(spawn2);
/* 378 */             freeze2.put(player, Long.valueOf(System.currentTimeMillis()));
/* 379 */             inGame.add(player);
/* 380 */           } else if (((!player7.contains(player)) && (!player8.contains(player)) && (joinedPlayers.contains(player))) || (spectating.contains(player))) {
/* 381 */             player.teleport(spectator);
/* 382 */             spectating.add(player);
/*     */           }
/* 384 */         } else if (team == 5) {
/* 385 */           if (player9.contains(player)) {
/* 386 */             player.teleport(spawn1);
/* 387 */             freeze1.put(player, Long.valueOf(System.currentTimeMillis()));
/* 388 */             inGame.add(player);
/* 389 */           } else if (player10.contains(player)) {
/* 390 */             player.teleport(spawn2);
/* 391 */             freeze2.put(player, Long.valueOf(System.currentTimeMillis()));
/* 392 */             inGame.add(player);
/* 393 */           } else if (((!player9.contains(player)) && (!player10.contains(player)) && (joinedPlayers.contains(player))) || (spectating.contains(player))) {
/* 394 */             player.teleport(spectator);
/* 395 */             spectating.add(player);
/*     */           }
/* 397 */         } else if (team == 6) {
/* 398 */           if (player11.contains(player)) {
/* 399 */             player.teleport(spawn1);
/* 400 */             freeze1.put(player, Long.valueOf(System.currentTimeMillis()));
/* 401 */             inGame.add(player);
/* 402 */           } else if (player12.contains(player)) {
/* 403 */             player.teleport(spawn2);
/* 404 */             freeze2.put(player, Long.valueOf(System.currentTimeMillis()));
/* 405 */             inGame.add(player);
/* 406 */           } else if (((!player11.contains(player)) && (!player12.contains(player)) && (joinedPlayers.contains(player))) || (spectating.contains(player))) {
/* 407 */             player.teleport(spectator);
/* 408 */             spectating.add(player);
/*     */           }
/* 410 */         } else if (team == 7) {
/* 411 */           if (player13.contains(player)) {
/* 412 */             player.teleport(spawn1);
/* 413 */             freeze1.put(player, Long.valueOf(System.currentTimeMillis()));
/* 414 */             inGame.add(player);
/* 415 */           } else if (player14.contains(player)) {
/* 416 */             player.teleport(spawn2);
/* 417 */             freeze2.put(player, Long.valueOf(System.currentTimeMillis()));
/* 418 */             inGame.add(player);
/* 419 */           } else if (((!player13.contains(player)) && (!player14.contains(player)) && (joinedPlayers.contains(player))) || (spectating.contains(player))) {
/* 420 */             player.teleport(spectator);
/* 421 */             spectating.add(player);
/*     */           }
/* 423 */         } else if (team == 8) {
/* 424 */           if (player15.contains(player)) {
/* 425 */             player.teleport(spawn1);
/* 426 */             freeze1.put(player, Long.valueOf(System.currentTimeMillis()));
/* 427 */             inGame.add(player);
/* 428 */           } else if (player16.contains(player)) {
/* 429 */             player.teleport(spawn2);
/* 430 */             freeze2.put(player, Long.valueOf(System.currentTimeMillis()));
/* 431 */             inGame.add(player);
/* 432 */           } else if (((!player15.contains(player)) && (!player16.contains(player)) && (joinedPlayers.contains(player))) || (spectating.contains(player))) {
/* 433 */             player.teleport(spectator);
/* 434 */             spectating.add(player);
/*     */           }
/*     */         }
/* 437 */       } else if (round.equalsIgnoreCase("Quarter")) {
/* 438 */         if (team == 1) {
/* 439 */           if (wplayer1.contains(player)) {
/* 440 */             player.teleport(spawn1);
/* 441 */             freeze1.put(player, Long.valueOf(System.currentTimeMillis()));
/* 442 */             inGame.add(player);
/* 443 */           } else if (wplayer2.contains(player)) {
/* 444 */             player.teleport(spawn2);
/* 445 */             freeze2.put(player, Long.valueOf(System.currentTimeMillis()));
/* 446 */             inGame.add(player);
/* 447 */           } else if (((!wplayer1.contains(player)) && (!wplayer2.contains(player)) && (joinedPlayers.contains(player))) || (spectating.contains(player))) {
/* 448 */             player.teleport(spectator);
/* 449 */             spectating.add(player);
/*     */           }
/* 451 */         } else if (team == 2) {
/* 452 */           if (wplayer3.contains(player)) {
/* 453 */             player.teleport(spawn1);
/* 454 */             freeze1.put(player, Long.valueOf(System.currentTimeMillis()));
/* 455 */             inGame.add(player);
/* 456 */           } else if (wplayer4.contains(player)) {
/* 457 */             player.teleport(spawn2);
/* 458 */             freeze2.put(player, Long.valueOf(System.currentTimeMillis()));
/* 459 */             inGame.add(player);
/* 460 */           } else if (((!wplayer3.contains(player)) && (!wplayer4.contains(player)) && (joinedPlayers.contains(player))) || (spectating.contains(player))) {
/* 461 */             player.teleport(spectator);
/* 462 */             spectating.add(player);
/*     */           }
/* 464 */         } else if (team == 3) {
/* 465 */           if (wplayer5.contains(player)) {
/* 466 */             player.teleport(spawn1);
/* 467 */             freeze1.put(player, Long.valueOf(System.currentTimeMillis()));
/* 468 */             inGame.add(player);
/* 469 */           } else if (wplayer6.contains(player)) {
/* 470 */             player.teleport(spawn2);
/* 471 */             freeze2.put(player, Long.valueOf(System.currentTimeMillis()));
/* 472 */             inGame.add(player);
/* 473 */           } else if (((!wplayer5.contains(player)) && (!wplayer6.contains(player)) && (joinedPlayers.contains(player))) || (spectating.contains(player))) {
/* 474 */             player.teleport(spectator);
/* 475 */             spectating.add(player);
/*     */           }
/* 477 */         } else if (team == 4) {
/* 478 */           if (wplayer7.contains(player)) {
/* 479 */             player.teleport(spawn1);
/* 480 */             freeze1.put(player, Long.valueOf(System.currentTimeMillis()));
/* 481 */             inGame.add(player);
/* 482 */           } else if (wplayer8.contains(player)) {
/* 483 */             player.teleport(spawn2);
/* 484 */             freeze2.put(player, Long.valueOf(System.currentTimeMillis()));
/* 485 */             inGame.add(player);
/* 486 */           } else if (((!wplayer7.contains(player)) && (!wplayer8.contains(player)) && (joinedPlayers.contains(player))) || (spectating.contains(player))) {
/* 487 */             player.teleport(spectator);
/* 488 */             spectating.add(player);
/*     */           }
/*     */         }
/* 491 */       } else if (round.equalsIgnoreCase("Semi Final")) {
/* 492 */         if (team == 1) {
/* 493 */           if (Quarter1.contains(player)) {
/* 494 */             player.teleport(spawn1);
/* 495 */             freeze1.put(player, Long.valueOf(System.currentTimeMillis()));
/* 496 */             inGame.add(player);
/* 497 */           } else if (Quarter2.contains(player)) {
/* 498 */             player.teleport(spawn2);
/* 499 */             freeze2.put(player, Long.valueOf(System.currentTimeMillis()));
/* 500 */             inGame.add(player);
/* 501 */           } else if (((!Quarter1.contains(player)) && (!Quarter2.contains(player)) && (joinedPlayers.contains(player))) || (spectating.contains(player))) {
/* 502 */             player.teleport(spectator);
/* 503 */             spectating.add(player);
/*     */           }
/* 505 */         } else if (team == 2) {
/* 506 */           if (Quarter3.contains(player)) {
/* 507 */             player.teleport(spawn1);
/* 508 */             freeze1.put(player, Long.valueOf(System.currentTimeMillis()));
/* 509 */             inGame.add(player);
/* 510 */           } else if (Quarter4.contains(player)) {
/* 511 */             player.teleport(spawn2);
/* 512 */             freeze2.put(player, Long.valueOf(System.currentTimeMillis()));
/* 513 */             inGame.add(player);
/* 514 */           } else if (((!Quarter3.contains(player)) && (!Quarter4.contains(player)) && (joinedPlayers.contains(player))) || (spectating.contains(player))) {
/* 515 */             player.teleport(spectator);
/* 516 */             spectating.add(player);
/*     */           }
/*     */         }
/* 519 */       } else if ((round.equalsIgnoreCase("Final")) && 
/* 520 */         (team == 1)) {
/* 521 */         if (Semi1.contains(player)) {
/* 522 */           player.teleport(spawn1);
/* 523 */           freeze1.put(player, Long.valueOf(System.currentTimeMillis()));
/* 524 */           inGame.add(player);
/* 525 */         } else if (Semi2.contains(player)) {
/* 526 */           player.teleport(spawn2);
/* 527 */           freeze2.put(player, Long.valueOf(System.currentTimeMillis()));
/* 528 */           inGame.add(player);
/* 529 */         } else if (((!Semi1.contains(player)) && (!Semi2.contains(player)) && (joinedPlayers.contains(player))) || (spectating.contains(player))) {
/* 530 */           player.teleport(spectator);
/* 531 */           spectating.add(player);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void showHelp(Player player)
/*     */   {
            
/* 542 */    // player.sendMessage(green + "/pvpm join " + blue + "Join the matchmaking system");
/* 543 */    // player.sendMessage(green + "/pvpm create " + blue + "Create the arena for PvPMatchmaking");
/* 544 */    // player.sendMessage(green + "/pvpm set <player1, player2, spectator, exit> " + blue + "Set the teleport locations for the <first player, second player, spectators, and exit");
/* 545 */    // player.sendMessage(green + "/pvpm done " + blue + "Finish creating the arena.");
/* 546 */    // player.sendMessage(green + "/pvpm spectate " + blue + "Spectate the battles of other players.");
/* 547 */    // player.sendMessage(green + "/pvpm leave " + blue + "Leave the game.");
/*     */   }
/*     */   
/* 550 */   public int getPlayers() { return joined; }
/*     */   
/*     */ /*     */   
/*     */ 
/*     */   public boolean onCommand(CommandSender sender, Command cmd, String commandlabel, String[] args)
/*     */   {
/* 555 */     Player player = (Player)sender;
/*     */     
/* 557 */     Location exit = new Location(Bukkit.getWorld(cfg.getString("PvPMatchmaking.Locations.Arena.Exit.world")), cfg.getInt("PvPMatchmaking.Locations.Arena.Exit.block.x"), cfg.getInt("PvPMatchmaking.Locations.Arena.Exit.block.y"), cfg.getInt("PvPMatchmaking.Locations.Arena.Exit.block.z"));
/* 558 */     madeArena = cfg.getBoolean("PvPMatchmaking.MadeArena");
/* 559 */     Server server = player.getServer();
/* 560 */     if (!(sender instanceof Player)) sender.sendMessage("[Kom] These commands can only be used ingame!");
/* 561 */     if (commandlabel.equalsIgnoreCase("pvpm")) {
/* 562 */       if ((args.length < 1) || (args[0].equalsIgnoreCase("help")) || (args[0].equalsIgnoreCase("?"))) {
/* 563 */         if (player.hasPermission("pvpm.help")) {
/* 564 */           showHelp(player);
/*     */         } else {
/* 566 */           player.sendMessage(red + "You don't have permission to do that!");
/*     */         }
/* 568 */       } else if (args[0].equalsIgnoreCase("join")) {
/* 569 */         if (player.hasPermission("pvpm.join")) {
/* 570 */           if (!madeArena) {
/* 571 */             player.sendMessage(red + "This game has not been set up yet! Please ask your server owner to set it up!");
/*     */           }
/* 573 */           else if (gameStarted) {
/* 574 */             player.sendMessage(red + "Já começou !!");
/* 575 */           } else if ((!gameInitiated) && (!gameStarted)) {
/* 576 */             gameInitiated = true;
/* 577 */             joined += 1;
/* 578 */             server.broadcastMessage(Pre + " começou ! Digite /arena jogar torneio.");
/* 579 */             server.broadcastMessage(Pre + " O torneio ira comecar quando tiver [" + cfg.getInt("PvPMatchmaking.PlayersNeededToStartGame(Even Number)") + "] jogadores.");
/* 580 */             server.broadcastMessage(Pre + " Atualmente tem [" + joined + "] jogadores no torneio.");
/* 581 */             player1.add(player);
/* 582 */             jplayer1 = true;
/* 583 */             joinedPlayers.add(player);
/* 584 */             player.sendMessage(blue + "Voce eh o jogador 1");
/* 585 */           } else if ((gameInitiated) && (!gameStarted)) {
/* 586 */             if ((jplayer1) && (!joinedPlayers.contains(player)) && (player2.isEmpty())) {
/* 587 */               joined += 1;
/* 588 */               jplayer2 = true;
/* 589 */               player2.add(player);
/* 590 */               server.broadcastMessage(Pre + " Atualmente temos [" + joined + "] jogadores no torneio.");
/* 591 */               joinedPlayers.add(player);
/* 592 */               player.sendMessage(blue + "Voce eh o jogador 2");
/* 593 */             } else if ((jplayer2) && (!joinedPlayers.contains(player)) && (player3.isEmpty())) {
/* 594 */               joined += 1;
/* 595 */               jplayer3 = true;
/* 596 */               player3.add(player);
/* 597 */               server.broadcastMessage(Pre + " Atualmente temos [" + joined + "] jogadores no torneio.");
/* 598 */               joinedPlayers.add(player);
/* 599 */               player.sendMessage(blue + "Voce eh o jogador 3");
/* 600 */             } else if ((jplayer3) && (!joinedPlayers.contains(player)) && (player4.isEmpty())) {
/* 601 */               joined += 1;
/* 602 */               jplayer4 = true;
/* 603 */               player4.add(player);
/* 604 */               server.broadcastMessage(Pre + " Atualmente temos [" + joined + "] jogadores no torneio.");
/* 605 */               joinedPlayers.add(player);
/* 606 */               player.sendMessage(blue + "Voce eh o jogador 4");
/* 607 */             } else if ((jplayer4) && (!joinedPlayers.contains(player)) && (player5.isEmpty())) {
/* 608 */               joined += 1;
/* 609 */               jplayer5 = true;
/* 610 */               player5.add(player);
/* 611 */               server.broadcastMessage(Pre + " Atualmente temos [" + joined + "] jogadores no torneio.");
/* 612 */               joinedPlayers.add(player);
/* 613 */               player.sendMessage(blue + "Voce eh o jogador 5");
/* 614 */             } else if ((jplayer5) && (!joinedPlayers.contains(player)) && (player6.isEmpty())) {
/* 615 */               joined += 1;
/* 616 */               jplayer6 = true;
/* 617 */               player6.add(player);
/* 618 */               server.broadcastMessage(Pre + " Atualmente temos [" + joined + "] jogadores no torneio.");
/* 619 */               joinedPlayers.add(player);
/* 620 */               player.sendMessage(blue + "Voce eh o jogador 6");
/* 621 */             } else if ((jplayer6) && (!joinedPlayers.contains(player)) && (player7.isEmpty())) {
/* 622 */               joined += 1;
/* 623 */               jplayer7 = true;
/* 624 */               player7.add(player);
/* 625 */               server.broadcastMessage(Pre + " Atualmente temos [" + joined + "] jogadores no torneio.");
/* 626 */               joinedPlayers.add(player);
/* 627 */               player.sendMessage(blue + "Voce eh o jogador 7");
/* 628 */             } else if ((jplayer7) && (!joinedPlayers.contains(player)) && (player8.isEmpty())) {
/* 629 */               joined += 1;
/* 630 */               jplayer8 = true;
/* 631 */               player8.add(player);
/* 632 */               server.broadcastMessage(Pre + " Atualmente temos [" + joined + "] jogadores no torneio.");
/* 633 */               joinedPlayers.add(player);
/* 634 */               player.sendMessage(blue + "Voce eh o jogador 8");
/* 635 */             } else if ((jplayer8) && (!joinedPlayers.contains(player)) && (player9.isEmpty())) {
/* 636 */               joined += 1;
/* 637 */               jplayer9 = true;
/* 638 */               player9.add(player);
/* 639 */               server.broadcastMessage(Pre + " Atualmente temos [" + joined + "] jogadores no torneio.");
/* 640 */               joinedPlayers.add(player);
/* 641 */               player.sendMessage(blue + "Voce eh o jogador 9");
/* 642 */             } else if ((jplayer9) && (!joinedPlayers.contains(player)) && (player10.isEmpty())) {
/* 643 */               joined += 1;
/* 644 */               jplayer10 = true;
/* 645 */               player10.add(player);
/* 646 */               server.broadcastMessage(Pre + " Atualmente temos [" + joined + "] jogadores no torneio.");
/* 647 */               joinedPlayers.add(player);
/* 648 */               player.sendMessage(blue + "Voce eh o jogador 10");
/* 649 */             } else if ((jplayer10) && (!joinedPlayers.contains(player)) && (player11.isEmpty())) {
/* 650 */               joined += 1;
/* 651 */               jplayer11 = true;
/* 652 */               player11.add(player);
/* 653 */               server.broadcastMessage(Pre + " Atualmente temos [" + joined + "] jogadores no torneio.");
/* 654 */               joinedPlayers.add(player);
/* 655 */               player.sendMessage(blue + "Voce eh o jogador 11");
/* 656 */             } else if ((jplayer11) && (!joinedPlayers.contains(player)) && (player12.isEmpty())) {
/* 657 */               joined += 1;
/* 658 */               jplayer12 = true;
/* 659 */               player12.add(player);
/* 660 */               server.broadcastMessage(Pre + " Atualmente temos [" + joined + "] jogadores no torneio.");
/* 661 */               joinedPlayers.add(player);
/* 662 */               player.sendMessage(blue + "Voce eh o jogador 12");
/* 663 */             } else if ((jplayer12) && (!joinedPlayers.contains(player)) && (player13.isEmpty())) {
/* 664 */               joined += 1;
/* 665 */               jplayer13 = true;
/* 666 */               player13.add(player);
/* 667 */               player.sendMessage(blue + "Voce eh o jogador 13");
/* 668 */               server.broadcastMessage(Pre + " Atualmente temos [" + joined + "] jogadores no torneio.");
/* 669 */               joinedPlayers.add(player);
/* 670 */             } else if ((jplayer13) && (!joinedPlayers.contains(player)) && (player14.isEmpty())) {
/* 671 */               joined += 1;
/* 672 */               jplayer14 = true;
/* 673 */               player14.add(player);
/* 674 */               server.broadcastMessage(Pre + " Atualmente temos [" + joined + "] jogadores no torneio.");
/* 675 */               joinedPlayers.add(player);
/* 676 */               player.sendMessage(blue + "Voce eh o jogador 14");
/* 677 */             } else if ((jplayer14) && (!joinedPlayers.contains(player)) && (player15.isEmpty())) {
/* 678 */               joined += 1;
/* 679 */               jplayer15 = true;
/* 680 */               player15.add(player);
/* 681 */               server.broadcastMessage(Pre + " Atualmente temos [" + joined + "] jogadores no torneio.");
/* 682 */               joinedPlayers.add(player);
/* 683 */               player.sendMessage(blue + "Voce eh o jogador 15");
/* 684 */             } else if ((jplayer15) && (!joinedPlayers.contains(player)) && (player16.isEmpty())) {
/* 685 */               joined += 1;
/* 686 */               jplayer16 = true;
/* 687 */               player16.add(player);
/* 688 */               server.broadcastMessage(Pre + " Atualmente temos [" + joined + "] jogadores no torneio.");
/* 689 */               joinedPlayers.add(player);
/* 690 */               player.sendMessage(blue + "Voce eh o jogador 16");
/* 691 */             } else if (jplayer16) {
/* 692 */               player.sendMessage(red + "I'm sorry! This match is full! Please wait until the match ends. Thank you.");
/*     */             } else {
/* 694 */               player.sendMessage(red + "You can't join the game twice!");
/*     */             }
/*     */             
/*     */           }
/*     */           
/*     */         }
/*     */         else {
/* 701 */           player.sendMessage(red + "You don't have permission to do that!");
/*     */         }
/* 703 */       } else if (args[0].equalsIgnoreCase("create")) {
/* 704 */         if (player.hasPermission("pvpm.create")) {
/* 705 */           madeArena = cfg.getBoolean("PvPMatchmaking.MadeArena");
/* 706 */           if (!madeArena) {
/* 707 */             player.sendMessage(Pre + " Creation of the arena has begun!");
/* 708 */             player.sendMessage(blue + "Please left click the first block of the arena with your fist.");
/* 709 */             creating = true;
/* 710 */             beginCreation = true;
/*     */           } else {
/* 712 */             player.sendMessage(red + "The arena has already been made!");
/*     */           }
/*     */         } else {
/* 715 */           player.sendMessage(red + "You don't have permission to do that!");
/*     */         }
/* 717 */       } else if (args[0].equalsIgnoreCase("set")) {
/* 718 */         if (player.hasPermission("pvpm.set")) {
/* 719 */           String label = args[1];
/* 720 */           Location loc = player.getLocation();
/* 721 */           if (label.equalsIgnoreCase("player1")) {
/* 722 */             if (sp1) {
/* 723 */               int x = loc.getBlockX();
/* 724 */               int y = loc.getBlockY();
/* 725 */               int z = loc.getBlockZ();
/* 726 */               cfg.set("PvPMatchmaking.Locations.Arena.Player1Spawn.block.x", Integer.valueOf(x));
/* 727 */               cfg.set("PvPMatchmaking.Locations.Arena.Player1Spawn.block.y", Integer.valueOf(y));
/* 728 */               cfg.set("PvPMatchmaking.Locations.Arena.Player1Spawn.block.z", Integer.valueOf(z));
/* 729 */               KoM._instance.saveConfig();
/* 730 */               player.sendMessage(blue + "Player 1 spawn location recorded. Please type /pvpm set player2 to set the second player's spawn locations.");
/* 731 */               sp1 = false;
/* 732 */               sp2 = true;
/*     */             } else {
/* 734 */               player.sendMessage(red + "You need to type /pvpm create first!");
/*     */             }
/* 736 */           } else if (label.equalsIgnoreCase("player2")) {
/* 737 */             if (sp2) {
/* 738 */               int x = loc.getBlockX();
/* 739 */               int y = loc.getBlockY();
/* 740 */               int z = loc.getBlockZ();
/* 741 */               cfg.set("PvPMatchmaking.Locations.Arena.Player2Spawn.block.x", Integer.valueOf(x));
/* 742 */               cfg.set("PvPMatchmaking.Locations.Arena.Player2Spawn.block.y", Integer.valueOf(y));
/* 743 */               cfg.set("PvPMatchmaking.Locations.Arena.Player2Spawn.block.z", Integer.valueOf(z));
/* 744 */               KoM._instance.saveConfig();
/* 745 */               player.sendMessage(blue + "Player 2 spawn location recorded. Please type /pvpm set spectator to set the location for the spectators.");
/* 746 */               sp2 = false;
/* 747 */               ssp = true;
/*     */             } else {
/* 749 */               player.sendMessage(red + "You need to type /pvpm create first!");
/*     */             }
/* 751 */           } else if (label.equalsIgnoreCase("spectator")) {
/* 752 */             if (ssp) {
/* 753 */               int x = loc.getBlockX();
/* 754 */               int y = loc.getBlockY();
/* 755 */               int z = loc.getBlockZ();
/* 756 */               cfg.set("PvPMatchmaking.Locations.Spectator.Spawn.block.x", Integer.valueOf(x));
/* 757 */               cfg.set("PvPMatchmaking.Locations.Spectator.Spawn.block.y", Integer.valueOf(y));
/* 758 */               cfg.set("PvPMatchmaking.Locations.Spectator.Spawn.block.z", Integer.valueOf(z));
/* 759 */               KoM._instance.saveConfig();
/* 760 */               player.sendMessage(blue + "Spectator spawn location recorded. Type /pvpm set exit to set the exit location for the arena.");
/* 761 */               ssp = false;
/* 762 */               sel = true;
/*     */             } else {
/* 764 */               player.sendMessage(red + "You need to type /pvpm create first!");
/*     */             }
/* 766 */           } else if ((label.equalsIgnoreCase("exit")) && 
/* 767 */             (sel)) {
/* 768 */             String world = loc.getWorld().getName();
/* 769 */             int x = loc.getBlockX();
/* 770 */             int y = loc.getBlockY();
/* 771 */             int z = loc.getBlockZ();
/* 772 */             cfg.set("PvPMatchmaking.Locations.Arena.Exit.world", world);
/* 773 */             cfg.set("PvPMatchmaking.Locations.Arena.Exit.block.x", Integer.valueOf(x));
/* 774 */             cfg.set("PvPMatchmaking.Locations.Arena.Exit.block.y", Integer.valueOf(y));
/* 775 */             cfg.set("PvPMatchmaking.Locations.Arena.Exit.block.z", Integer.valueOf(z));
/* 776 */             KoM._instance.saveConfig();
/* 777 */             player.sendMessage(blue + "Exit location recorded. Type /pvpm done to finish making the arena.");
/* 778 */             sel = false;
/* 779 */             finished = true;
/* 780 */             creating = false;
/*     */           }
/*     */         }
/*     */         else {
/* 784 */           player.sendMessage(red + "You don't have permission to do that!");
/*     */         }
/* 786 */       } else if (args[0].equalsIgnoreCase("done")) {
/* 787 */         if (player.hasPermission("pvpm.done")) {
/* 788 */           if (finished) {
/* 789 */             player.sendMessage(blue + "Arena creation complete! You can now use /pvpm join to play!");
/* 790 */             cfg.set("PvPMatchmaking.MadeArena", Boolean.valueOf(true));
/* 791 */             KoM._instance.saveConfig();
/* 792 */             finished = false;
/*     */           } else {
/* 794 */             player.sendMessage(red + "You need to type /pvpm create first!");
/*     */           }
/*     */         } else {
/* 797 */           player.sendMessage(red + "You don't have permission to do that!");
/*     */         }
/* 799 */       } else if ((args[0].equalsIgnoreCase("spectate")) || (args[0].equalsIgnoreCase("spec"))) {
/* 800 */         if (player.hasPermission("pvpm.spectate")) {
/* 801 */           spectating.add(player);
/* 802 */           player.sendMessage(blue + "You will now spectate the next game.");
/*     */         } else {
/* 804 */           player.sendMessage(red + "You don't have permission to do that!");
/*     */         }
/* 806 */       } else if (args[0].equalsIgnoreCase("leave")) {
/* 807 */         if (player.hasPermission("pvpm.leave")) {
/* 808 */           if ((joinedPlayers.contains(player)) || (spectating.contains(player))) {
/* 809 */             player1.remove(player);
/* 810 */             player2.remove(player);
/* 811 */             player3.remove(player);
/* 812 */             player4.remove(player);
/* 813 */             player5.remove(player);
/* 814 */             player6.remove(player);
/* 815 */             player7.remove(player);
/* 816 */             player8.remove(player);
/* 817 */             player9.remove(player);
/* 818 */             player10.remove(player);
/* 819 */             player11.remove(player);
/* 820 */             player12.remove(player);
/* 821 */             player13.remove(player);
/* 822 */             player14.remove(player);
/* 823 */             player15.remove(player);
/* 824 */             player16.remove(player);
/* 825 */             wplayer1.remove(player);
/* 826 */             wplayer2.remove(player);
/* 827 */             wplayer3.remove(player);
/* 828 */             wplayer4.remove(player);
/* 829 */             wplayer5.remove(player);
/* 830 */             wplayer6.remove(player);
/* 831 */             wplayer7.remove(player);
/* 832 */             wplayer8.remove(player);
/* 833 */             Quarter1.remove(player);
/* 834 */             Quarter2.remove(player);
/* 835 */             Quarter3.remove(player);
/* 836 */             Quarter4.remove(player);
/* 837 */             Semi1.remove(player);
/* 838 */             Semi2.remove(player);
/* 839 */             Final.remove(player);
/* 840 */             joinedPlayers.remove(player);
/* 841 */             spectating.remove(player);
/* 842 */             player.sendMessage(blue + "Voce saiu do jogo!");
/* 843 */             joined -= 1;
/* 844 */             player.teleport(exit);
/*     */           } else {
/* 846 */             player.sendMessage(red + "Voce nao esta em um jogo!");
/*     */           }
/*     */         } else {
/* 849 */           player.sendMessage(red + "Voce nao tem permissao para isto!");
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 854 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\User\Downloads\PvPMatchmaking_v1.2.jar!\me\Doubts\PvPMatchmaking\PvPMatchmaking.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */