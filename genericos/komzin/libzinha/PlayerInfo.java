/*    */ package genericos.komzin.libzinha;
/*    */ 
/*    */ import java.util.HashSet;
/*    */ import org.bukkit.Location;
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
/*    */ 
/*    */ public class PlayerInfo
/*    */ {
/* 18 */   public long TempoTexto = 0L;
/* 19 */   public HashSet<Location> MsgJaLida = new HashSet();
/* 20 */   public long TempoFoguete = 0L;
/* 21 */   public long TempoAnunciar = 0L;
  public boolean ignoreTell = false;
    public String inChannel = null;
    public String lastPlayerMessage = null;
    public String talkingTo = null;
    public boolean recebelocal = true;
    public boolean recebeglobal = true;
/*    */ }


/* Location:              C:\Users\User\Desktop\REPO\InstaMCLibKom.jar!\instamc\coders\libkom\PlayerInfo.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */