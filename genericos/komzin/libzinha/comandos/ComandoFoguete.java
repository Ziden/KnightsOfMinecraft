/*     */ package genericos.komzin.libzinha.comandos;
/*     */ 
/*     */ import genericos.komzin.libzinha.InstaMCLibKom;
/*     */ import genericos.komzin.libzinha.PlayerInfo;
/*     */ import java.util.Random;
import nativelevel.MetaShit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Color;
/*     */ import org.bukkit.FireworkEffect;
/*     */ import org.bukkit.FireworkEffect.Builder;
/*     */ import org.bukkit.FireworkEffect.Type;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandExecutor;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Firework;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.meta.FireworkMeta;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ComandoFoguete
/*     */   implements CommandExecutor
/*     */ {
/*     */   public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] args)
/*     */   {
/*  33 */     if (!(cs instanceof Player)) {
/*  34 */       cs.sendMessage("somente players pode usar este comando");
/*  35 */       return false;
/*     */     }
/*  37 */     if (args.length != 0) {
/*  38 */       cs.sendMessage(ChatColor.RED + "Utilize /foguete somente");
/*  39 */       return false;
/*     */     }
/*  41 */     if (!cs.hasPermission("manialib.foguete")) {
/*  42 */       cs.sendMessage(ChatColor.RED + "Voce precisa ser §6vip para utilizar o §f/foguete");
/*  43 */       return false;
/*     */     }
/*  45 */     Player p = (Player)cs;
/*  46 */     PlayerInfo info = InstaMCLibKom.getinfo(p);
/*  47 */     if (System.currentTimeMillis() < info.TempoFoguete) {
/*  48 */       cs.sendMessage("§cAguarde alguns segundos para utilizar o comando novamente!");
/*  49 */       return true;
/*     */     }
/*  51 */     SoltaFoguete(p);
/*  52 */     p.sendMessage("§cSoltou um foguete =D");
/*  53 */     if (!p.isOp()) {
/*  54 */       info.TempoFoguete = (System.currentTimeMillis() + 15000L);
/*     */     }
/*     */     
/*  57 */     return true;
/*     */   }
/*     */   
/*     */   private void SoltaFoguete(Player player) {
/*  61 */     Firework fw = (Firework)player.getWorld().spawn(player.getLocation(), Firework.class);
/*  62 */     FireworkMeta fm = fw.getFireworkMeta();
/*  63 */     int fType = (int)(Math.random() * 6.0D);
/*  64 */     FireworkEffect.Type type = null;
/*  65 */     Random r = new Random();
/*  66 */     switch (fType) {
/*     */     case 1: 
/*     */     default: 
/*  69 */       type = FireworkEffect.Type.BALL;
/*  70 */       break;
/*     */     case 2: 
/*  72 */       type = FireworkEffect.Type.BALL_LARGE;
/*  73 */       break;
/*     */     case 3: 
/*  75 */       type = FireworkEffect.Type.BURST;
/*  76 */       break;
/*     */     case 4: 
/*  78 */       type = FireworkEffect.Type.CREEPER;
/*  79 */       break;
/*     */     case 5: 
/*  81 */       type = FireworkEffect.Type.STAR;
/*     */     }
/*  83 */     int c1i = (int)(Math.random() * 17.0D);
/*  84 */     int c2i = (int)(Math.random() * 17.0D);
/*  85 */     Color c1 = getColour(c1i);
/*  86 */     Color c2 = getColour(c2i);
/*  87 */     FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(c1).withFade(c2).with(type).trail(r.nextBoolean()).build();
/*  88 */     fm.addEffect(effect);
/*  89 */     int power = (int)(Math.random() * 3.0D);
/*  90 */     fm.setPower(power);
/*  91 */     fw.setFireworkMeta(fm);
                MetaShit.setMetaObject("visual", fw, true);
                fw.setCustomName("Foguete VIP");
/*     */   }
/*     */   
/*     */   public Color getColour(int c) {
/*  95 */     switch (c) {
/*     */     case 1: 
/*     */     default: 
/*  98 */       return Color.AQUA;
/*     */     case 2: 
/* 100 */       return Color.BLACK;
/*     */     case 3: 
/* 102 */       return Color.BLUE;
/*     */     case 4: 
/* 104 */       return Color.FUCHSIA;
/*     */     case 5: 
/* 106 */       return Color.GRAY;
/*     */     case 6: 
/* 108 */       return Color.GREEN;
/*     */     case 7: 
/* 110 */       return Color.LIME;
/*     */     case 8: 
/* 112 */       return Color.MAROON;
/*     */     case 9: 
/* 114 */       return Color.NAVY;
/*     */     case 10: 
/* 116 */       return Color.OLIVE;
/*     */     case 11: 
/* 118 */       return Color.PURPLE;
/*     */     case 12: 
/* 120 */       return Color.RED;
/*     */     case 13: 
/* 122 */       return Color.SILVER;
/*     */     case 14: 
/* 124 */       return Color.TEAL;
/*     */     case 15: 
/* 126 */       return Color.WHITE;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\User\Desktop\REPO\InstaMCLibKom.jar!\instamc\coders\libkom\comandos\ComandoFoguete.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */