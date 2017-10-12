 package nativelevel.anuncios;

import java.util.ArrayList;
import java.util.List;

 public class CommandLineTokenizer
{
   private static void appendToBuffer(List<String> resultBuffer, StringBuffer buf)
   {
     if (buf.length() > 0) {
       resultBuffer.add(buf.toString());
       buf.setLength(0);
     }
  }

 public static String[] tokenize(String[] args)
   {
     if ((args == null) || (args.length == 0)) {
      return tokenize("");
     }
     StringBuilder sb = new StringBuilder(args[0]);
     for (int i = 1; i < args.length; i++) {
/*  69 */       sb.append(" ").append(args[i]);
/*     */     }
/*  71 */     return tokenize(sb.toString());
/*     */   }
/*     */ 
/*     */   public static String[] tokenize(String commandLine)
/*     */   {
/*  85 */     List resultBuffer = new ArrayList();
/*     */ 
/*  87 */     if (commandLine != null) {
/*  88 */       int z = commandLine.length();
/*  89 */       boolean insideQuotes = false;
/*  90 */       StringBuffer buf = new StringBuffer();
/*     */ 
/*  92 */       for (int i = 0; i < z; i++) {
/*  93 */         char c = commandLine.charAt(i);
/*  94 */         if (c == '"') {
/*  95 */           appendToBuffer(resultBuffer, buf);
/*  96 */           insideQuotes = !insideQuotes;
/*  97 */         } else if (c == '\\') {
/*  98 */           if ((z > i + 1) && ((commandLine.charAt(i + 1) == '"') || (commandLine.charAt(i + 1) == '\\')))
/*     */           {
/* 101 */             buf.append(commandLine.charAt(i + 1));
/* 102 */             i++;
/*     */           } else {
/* 104 */             buf.append("\\");
/*     */           }
/*     */         }
/* 107 */         else if (insideQuotes) {
/* 108 */           buf.append(c);
/*     */         }
/* 110 */         else if (Character.isWhitespace(c)) {
/* 111 */           appendToBuffer(resultBuffer, buf);
/*     */         } else {
/* 113 */           buf.append(c);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 118 */       appendToBuffer(resultBuffer, buf);
/*     */     }
/*     */ 
/* 122 */     String[] result = new String[resultBuffer.size()];
/* 123 */     return (String[])(String[])resultBuffer.toArray(result);
/*     */   }
/*     */ }
