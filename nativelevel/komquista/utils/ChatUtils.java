package nativelevel.komquista.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;










public class ChatUtils
{
  private static String prefix = "§e[§c§lKom§a§lQuista§r§e]§r";
  
  public static void broadcast(String msg) {
    Bukkit.broadcastMessage(prefix + " " + msg);
  }
  
  public static void sendMessage(CommandSender cs, String msg) {
    cs.sendMessage(prefix + " " + msg);
  }
  
  public static void SendPreffix(CommandSender cs) {
    cs.sendMessage("  ");
    cs.sendMessage("§c• §a҉ §e҉ §a҉ §e҉ §a҉ §e҉ §a҉ §e҉ §9§lKomQuista §a҉ §e҉ §a҉ §e҉ §a҉ §e҉ §a҉ §e҉ §c•");
    cs.sendMessage("  ");
  }
  
  public static void SendSuffix(CommandSender cs) {
    cs.sendMessage("  ");
    cs.sendMessage("§c• §a҉ §e҉ §a҉ §e҉ §a҉ §e҉ §a҉ §e҉ §9§lKomQuista §a҉ §e҉ §a҉ §e҉ §a҉ §e҉ §a҉ §e҉ §c•");
    cs.sendMessage("  ");
  }
  
  public static String getNumber(int s)
  {
    if (s == 1) {
      return "❶";
    }
    
    if (s == 2) {
      return "❷";
    }
    
    if (s == 3) {
      return "❸";
    }
    
    if (s == 4) {
      return "❹";
    }
    
    if (s == 5) {
      return "❺";
    }
    
    if (s == 6) {
      return "❻";
    }
    if (s == 7) {
      return "❼";
    }
    if (s == 8) {
      return "❽";
    }
    if (s == 9) {
      return "❾";
    }
    if (s == 10) {
      return "❿";
    }
    return "" + s;
  }
}
