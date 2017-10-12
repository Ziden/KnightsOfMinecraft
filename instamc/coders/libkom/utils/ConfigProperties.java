/*    */ package instamc.coders.libkom.utils;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.FileNotFoundException;
/*    */ import java.io.FileOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.util.Properties;
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.Logger;
/*    */ 
/*    */ public class ConfigProperties
/*    */ {
/*    */   private File dbFile;
/* 15 */   Properties prop = new Properties();
/*    */   
/*    */   public ConfigProperties(String arquivo) throws IOException, FileNotFoundException
/*    */   {
/* 19 */     this.dbFile = new File(arquivo);
/* 20 */     if (!this.dbFile.exists())
/*    */     {
/* 22 */       this.dbFile.getParentFile().mkdirs();
/* 23 */       this.prop.store(new FileOutputStream(arquivo), null);
/*    */     }
/*    */     else
/*    */     {
/* 27 */       this.prop.load(new FileInputStream(arquivo));
/*    */     }
/*    */   }
/*    */   
/*    */   public void saveConfig()
/*    */   {
/*    */     try
/*    */     {
/* 35 */       this.prop.store(new FileOutputStream(this.dbFile.getAbsolutePath()), null);
/*    */     }
/*    */     catch (FileNotFoundException ex)
/*    */     {
/* 39 */       Logger.getLogger(ConfigProperties.class.getName()).log(Level.SEVERE, null, ex);
/*    */     }
/*    */     catch (IOException ex)
/*    */     {
/* 43 */       Logger.getLogger(ConfigProperties.class.getName()).log(Level.SEVERE, null, ex);
/*    */     }
/*    */   }
/*    */   
/*    */   public Properties getConfig()
/*    */   {
/* 49 */     return this.prop;
/*    */   }
/*    */ }