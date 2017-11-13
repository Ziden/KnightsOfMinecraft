/*    */ package genericos.komzin.libzinha.utils;
/*    */ 
/*    */ import genericos.komzin.libzinha.InstaMCLibKom;
/*    */ import java.util.HashSet;
/*    */ import java.util.Random;
/*    */ import java.util.Set;
/*    */ import java.util.UUID;
import nativelevel.KoM;
/*    */ import org.bukkit.Effect;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.Server;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.EntityType;
/*    */ import org.bukkit.scheduler.BukkitScheduler;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Efeitos
/*    */ {
/* 26 */   public static Set<UUID> bats = new HashSet();
/*    */   /*    */   
/*    */   public static void effectBats(final Location location) {
/* 29 */     final Set<UUID> batty = new HashSet();
/* 30 */     for (int x = 0; x < 10; x++) {
/* 31 */       batty.add(location.getWorld().spawnEntity(location, EntityType.BAT).getUniqueId());
/*    */     }
/* 33 */     bats.addAll(batty);
/* 34 */     KoM._instance.getServer().getScheduler().runTaskLater(KoM._instance, new Runnable()
/*    */     {
/*    */       public void run() {
/* 37 */         Efeitos.effectBatsCleanup(location.getWorld(), batty);
/* 38 */         Efeitos.bats.removeAll(batty); } }, 60L);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/* 43 */   public static Random r = new Random();
/*    */   
/*    */   private static void effectBatsCleanup(World world, Set<UUID> bats) {
/* 46 */     for (Entity entity : world.getEntities()) {
/* 47 */       if (bats.contains(entity.getUniqueId()))
/*    */       {
/* 49 */         world.playEffect(entity.getLocation(), Effect.SMOKE, r.nextInt(9));
/* 50 */         entity.remove();
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   public static void effectExplosion(Location loc) {
/* 56 */     loc.getWorld().createExplosion(loc.getX(), loc.getY(), loc.getZ(), 0.0F, false, false);
/*    */   }
/*    */   
/*    */   public static void effectFlames(Location location) {
/* 60 */     for (int i = 0; i < 10; i++) {
/* 61 */       location.getWorld().playEffect(location, Effect.MOBSPAWNER_FLAMES, r.nextInt(9));
/*    */     }
/*    */   }
/*    */   
/*    */   public static void effectLightning(Location location) {
/* 66 */     int x = location.getBlockX();
/* 67 */     double y = location.getBlockY();
/* 68 */     int z = location.getBlockZ();
/* 69 */     for (int i = 0; i < 20; i++) {
/*    */       double xToStrike;
/* 72 */       if (r.nextBoolean()) {
/* 73 */         xToStrike = x + r.nextInt(6);
/*    */       } else
/* 75 */         xToStrike = x - r.nextInt(6);
/* 77 */       double zToStrike; if (r.nextBoolean()) {
/* 78 */         zToStrike = z + r.nextInt(6);
/*    */       } else {
/* 80 */         zToStrike = z - r.nextInt(6);
/*    */       }
/* 82 */       Location toStrike = new Location(location.getWorld(), xToStrike, y, zToStrike);
/* 83 */       location.getWorld().strikeLightningEffect(toStrike);
/*    */     }
/*    */   }
/*    */   
/*    */   public static void effectSmoke(Location location) {
/* 88 */     for (int i = 0; i < 10; i++) {
/* 89 */       location.getWorld().playEffect(location, Effect.SMOKE, r.nextInt(9));
/*    */     }
/*    */   }
/*    */ }