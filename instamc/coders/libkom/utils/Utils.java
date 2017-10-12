/*    */ package instamc.coders.libkom.utils;
/*    */ 
/*    */ import instamc.coders.libkom.InstaMCLibKom;
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.DataOutputStream;
/*    */ import java.io.IOException;
import nativelevel.KoM;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.plugin.messaging.Messenger;
/*    */ import org.bukkit.plugin.messaging.PluginMessageRecipient;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Utils
/*    */ {
/*    */   public static void TeleportarTPBG(String server, Player sender)
/*    */   {
/* 24 */     Bukkit.getMessenger().registerOutgoingPluginChannel(KoM._instance, "BungeeCord");
/* 25 */     ByteArrayOutputStream b = new ByteArrayOutputStream();
/* 26 */     DataOutputStream out = new DataOutputStream(b);
/*    */     try {
/* 28 */       out.writeUTF("Connect");
/* 29 */       out.writeUTF(server);
/*    */     }
/*    */     catch (IOException localIOException) {}
/* 32 */     sender.sendPluginMessage(KoM._instance, "BungeeCord", b.toByteArray());
/*    */   }
/*    */ }
