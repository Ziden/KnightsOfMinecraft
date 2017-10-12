/*    */ package nativelevel.anuncios;
/*    */ 
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.Logger;
/*    */ import org.bukkit.Bukkit;
/*    */ 
/*    */ public class AnnouncerLog
/*    */ {
/*  9 */   private static final Logger logger = Bukkit.getLogger();
/*    */ 
/*    */   public static void info(String msg) {
/* 12 */     logger.log(Level.INFO, "[AutoAnnouncer] " + msg);
/*    */   }
/*    */ 
/*    */   public static void warning(String msg) {
/* 16 */     logger.log(Level.WARNING, "[AutoAnnouncer] " + msg);
/*    */   }
/*    */ 
/*    */   public static void severe(String msg) {
/* 20 */     logger.log(Level.SEVERE, "[AutoAnnouncer] " + msg);
/*    */   }
/*    */ 
/*    */   public static void info(String msg, Throwable e) {
/* 24 */     logger.log(Level.INFO, "[AutoAnnouncer] " + msg, e);
/*    */   }
/*    */ 
/*    */   public static void warning(String msg, Throwable e) {
/* 28 */     logger.log(Level.WARNING, "[AutoAnnouncer] " + msg, e);
/*    */   }
/*    */ 
/*    */   public static void severe(String msg, Throwable e) {
/* 32 */     logger.log(Level.SEVERE, "[AutoAnnouncer] " + msg, e);
/*    */   }
/*    */ }
