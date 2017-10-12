package nativelevel.utils;

import net.minecraft.server.v1_12_R1.ChatMessageType;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutChat;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle.EnumTitleAction;
import net.minecraft.server.v1_12_R1.PlayerConnection;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 *
 * @author User
 */
public class TitleAPI {
    
     @Deprecated
  public static void sendTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String message)
  {
    sendTitle(player, fadeIn, stay, fadeOut, message, null);
  }
  
  @Deprecated
  public static void sendSubtitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String message)
  {
    sendTitle(player, fadeIn, stay, fadeOut, null, message);
  }
  
  @Deprecated
  public static void sendFullTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle)
  {
    sendTitle(player, fadeIn, stay, fadeOut, title, subtitle);
  }
  
  public static void sendTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle)
  {
    PlayerConnection connection = ((CraftPlayer)player).getHandle().playerConnection;
    
    IChatBaseComponent b = null;
    PacketPlayOutTitle packetPlayOutTimes = new PacketPlayOutTitle(EnumTitleAction.TIMES, b, fadeIn.intValue(), stay.intValue(), fadeOut.intValue());
    connection.sendPacket(packetPlayOutTimes);
    if (subtitle != null)
    {
      subtitle = subtitle.replaceAll("%player%", player.getDisplayName());
      subtitle = ChatColor.translateAlternateColorCodes('&', subtitle);
      IChatBaseComponent titleSub = ChatSerializer.a("{\"text\": \"" + subtitle + "\"}");
      PacketPlayOutTitle packetPlayOutSubTitle = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, titleSub);
      connection.sendPacket(packetPlayOutSubTitle);
    }
    if (title != null)
    {
      title = title.replaceAll("%player%", player.getDisplayName());
      title = ChatColor.translateAlternateColorCodes('&', title);
      IChatBaseComponent titleMain = ChatSerializer.a("{\"text\": \"" + title + "\"}");
      PacketPlayOutTitle packetPlayOutTitle = new PacketPlayOutTitle(EnumTitleAction.TITLE, titleMain);
      connection.sendPacket(packetPlayOutTitle);
    }
  }
  
  public static void sendActionBar(Player player, String message)
  {
    CraftPlayer cPlayer = (CraftPlayer)player;
    String string = ChatColor.translateAlternateColorCodes('&', message);
    IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + string + "\"}");
    PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, ChatMessageType.GAME_INFO);
    cPlayer.getHandle().playerConnection.sendPacket(ppoc);
  }
    
}
