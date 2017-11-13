/*    */ package genericos.komzin.libzinha.reboot;
/*    */ 
/*    */ import java.text.DateFormat;
/*    */ import java.text.ParseException;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Date;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RebootUtils
/*    */ {
/* 21 */   public static DateFormat SoData = new SimpleDateFormat("dd-MM-yyyy");
/* 22 */   public static DateFormat DataHoraAtual = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
/* 23 */   public static Date date = new Date();
/* 24 */   public static String datahoje = SoData.format(date);
/* 25 */   public static List<String> ListaReinicio = new ArrayList();
/*    */   
/*    */ 
/* 28 */   public static long ProximoRebootEmSegundos = 0L;
/* 29 */   public static boolean JaLoadou = false;
/* 30 */   public static boolean EmReinicio = false;
/*    */   public static Date ProximoRebootDate;
/*    */   
/*    */   public static void LoadConf()
/*    */     throws ParseException
/*    */   {
/* 36 */     ListaReinicio.add(datahoje + " 07:00:00");
/* 37 */     //ListaReinicio.add(datahoje + " 12:00:00");
/* 38 */     //ListaReinicio.add(datahoje + " 16:00:00");
/* 39 */     //ListaReinicio.add(datahoje + " 20:00:00");
/*    */     
/* 41 */     ListaReinicio.add(datahoje + " 23:59:50");
/* 42 */     new RebootTimerThread().start();
/* 43 */     for (String d : ListaReinicio) {
/* 44 */       Date d1 = new Date();
/* 45 */       Date d2 = DataHoraAtual.parse(d);
/* 46 */       long diff = (d2.getTime() - d1.getTime()) / 1000L;
/* 47 */       if (diff > 0L)
/*    */       {
/*    */ 
/* 50 */         ProximoRebootDate = d2;
/* 51 */         ProximoRebootEmSegundos = diff;
/* 52 */         return;
/*    */       }
/*    */     }
/* 55 */     ProximoRebootEmSegundos = 10800L;
/*    */   }
/*    */   
/*    */   public String RetornaTempo(int Segundos) {
/* 59 */     int minuto = Segundos / 60;
/* 60 */     double aux = Segundos;
/* 61 */     while (!isInteiro(aux / 60.0D)) {
/* 62 */       aux -= 1.0D;
/*    */     }
/* 64 */     int sosegundos = (int)(Segundos - aux);
/* 65 */     String toSegundos = String.valueOf(sosegundos);
/* 66 */     if (toSegundos.length() == 1) {
/* 67 */       toSegundos = 0 + toSegundos;
/*    */     }
/* 69 */     return minuto + ":" + toSegundos;
/*    */   }
/*    */   
/*    */   public boolean isInteiro(double var) {
/* 73 */     return var % 1.0D == 0.0D;
/*    */   }
/*    */ }


/* Location:              C:\Users\User\Desktop\REPO\InstaMCLibKom.jar!\instamc\coders\libkom\reboot\RebootUtils.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */