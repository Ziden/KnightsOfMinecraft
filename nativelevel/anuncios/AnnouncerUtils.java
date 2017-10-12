/*    */ package nativelevel.anuncios;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FileOutputStream;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import org.bukkit.ChatColor;
/*    */ 
/*    */ public class AnnouncerUtils
/*    */ {
/*    */   public static String colorize(String announce)
/*    */   {
/* 14 */     announce = announce.replaceAll("&AQUA;", ChatColor.AQUA.toString());
/* 15 */     announce = announce.replaceAll("&BLACK;", ChatColor.BLACK.toString());
/* 16 */     announce = announce.replaceAll("&BLUE;", ChatColor.BLUE.toString());
/* 17 */     announce = announce.replaceAll("&DARK_AQUA;", ChatColor.DARK_AQUA.toString());
/* 18 */     announce = announce.replaceAll("&DARK_BLUE;", ChatColor.DARK_BLUE.toString());
/* 19 */     announce = announce.replaceAll("&DARK_GRAY;", ChatColor.DARK_GRAY.toString());
/* 20 */     announce = announce.replaceAll("&DARK_GREEN;", ChatColor.DARK_GREEN.toString());
/* 21 */     announce = announce.replaceAll("&DARK_PURPLE;", ChatColor.DARK_PURPLE.toString());
/* 22 */     announce = announce.replaceAll("&DARK_RED;", ChatColor.DARK_RED.toString());
/* 23 */     announce = announce.replaceAll("&GOLD;", ChatColor.GOLD.toString());
/* 24 */     announce = announce.replaceAll("&GRAY;", ChatColor.GRAY.toString());
/* 25 */     announce = announce.replaceAll("&GREEN;", ChatColor.GREEN.toString());
/* 26 */     announce = announce.replaceAll("&LIGHT_PURPLE;", ChatColor.LIGHT_PURPLE.toString());
/* 27 */     announce = announce.replaceAll("&RED;", ChatColor.RED.toString());
/* 28 */     announce = announce.replaceAll("&WHITE;", ChatColor.WHITE.toString());
/* 29 */     announce = announce.replaceAll("&YELLOW;", ChatColor.YELLOW.toString());
/* 30 */     announce = announce.replaceAll("&MAGIC;", ChatColor.MAGIC.toString());
/* 31 */     announce = announce.replaceAll("&BOLD;", ChatColor.BOLD.toString());
/* 32 */     announce = announce.replaceAll("&STRIKE;", ChatColor.STRIKETHROUGH.toString());
/* 33 */     announce = announce.replaceAll("&UNDERLINE;", ChatColor.UNDERLINE.toString());
/* 34 */     announce = announce.replaceAll("&ITALIC;", ChatColor.ITALIC.toString());
/* 35 */     announce = announce.replaceAll("&RESET;", ChatColor.RESET.toString());
/*    */ 
/* 37 */     announce = announce.replaceAll("&0", ChatColor.BLACK.toString());
/* 38 */     announce = announce.replaceAll("&1", ChatColor.DARK_BLUE.toString());
/* 39 */     announce = announce.replaceAll("&2", ChatColor.DARK_GREEN.toString());
/* 40 */     announce = announce.replaceAll("&3", ChatColor.DARK_AQUA.toString());
/* 41 */     announce = announce.replaceAll("&4", ChatColor.DARK_RED.toString());
/* 42 */     announce = announce.replaceAll("&5", ChatColor.DARK_PURPLE.toString());
/* 43 */     announce = announce.replaceAll("&6", ChatColor.GOLD.toString());
/* 44 */     announce = announce.replaceAll("&7", ChatColor.GRAY.toString());
/* 45 */     announce = announce.replaceAll("&8", ChatColor.DARK_GRAY.toString());
/* 46 */     announce = announce.replaceAll("&9", ChatColor.BLUE.toString());
/* 47 */     announce = announce.replaceAll("&a", ChatColor.GREEN.toString());
/* 48 */     announce = announce.replaceAll("&b", ChatColor.AQUA.toString());
/* 49 */     announce = announce.replaceAll("&c", ChatColor.RED.toString());
/* 50 */     announce = announce.replaceAll("&d", ChatColor.LIGHT_PURPLE.toString());
/* 51 */     announce = announce.replaceAll("&e", ChatColor.YELLOW.toString());
/* 52 */     announce = announce.replaceAll("&f", ChatColor.WHITE.toString());
/* 53 */     announce = announce.replaceAll("&k", ChatColor.MAGIC.toString());
/* 54 */     announce = announce.replaceAll("&l", ChatColor.BOLD.toString());
/* 55 */     announce = announce.replaceAll("&m", ChatColor.STRIKETHROUGH.toString());
/* 56 */     announce = announce.replaceAll("&n", ChatColor.UNDERLINE.toString());
/* 57 */     announce = announce.replaceAll("&o", ChatColor.ITALIC.toString());
/* 58 */     announce = announce.replaceAll("&r", ChatColor.RESET.toString());
/* 59 */     return announce;
/*    */   }
/*    */ 
/*    */   public static void copy(InputStream in, File file) {
/*    */     try {
/* 64 */       OutputStream out = new FileOutputStream(file);
/* 65 */       byte[] buf = new byte[1024];
/*    */       int len;
/* 67 */       while ((len = in.read(buf)) > 0) {
/* 68 */         out.write(buf, 0, len);
/*    */       }
/* 70 */       out.close();
/* 71 */       in.close();
/*    */     } catch (Exception e) {
/* 73 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ }