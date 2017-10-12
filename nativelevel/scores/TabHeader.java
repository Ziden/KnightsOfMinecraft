package nativelevel.scores;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import nativelevel.KoM;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_12_R1.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 *
 * @author NeT32
 */
public class TabHeader {

    private static final int PROTOCOL_VERSION = 47;

    /**
     * @param header The header of the tab list.
     */
    public static void broadcastHeader(String header)
    {
        broadcastHeaderAndFooter(header, null);
    }

    /**
     * @param footer The footer of the tab list.
     */
    public static void broadcastFooter(String footer)
    {
        broadcastHeaderAndFooter(null, footer);
    }

    /**
     * @param header The header of the tab list.
     * @param footer The footer of the tab list.
     */
    public static void broadcastHeaderAndFooter(String header, String footer)
    {
        for (Player player : Bukkit.getOnlinePlayers())
        {
            setHeaderAndFooter(player, header, footer);
        }
    }

    /**
     * @param p The Player.
     * @param header The header.
     */
    public static void setHeader(Player p, String header)
    {
        setHeaderAndFooter(p, header, null);
    }

    /**
     * @param p The Player
     * @param footer The footer.
     */
    public static void setFooter(Player p, String footer)
    {
        setHeaderAndFooter(p, null, footer);
    }

    /**
     * @param p The Player.
     * @param rawHeader The header in raw text.
     * @param rawFooter The footer in raw text.
     */
    public static void setHeaderAndFooter(Player p, String rawHeader, String rawFooter)
    {
        CraftPlayer player = (CraftPlayer) p;
        /* if (rawHeader == null || rawFooter == null)
         {
         TabTitleCache titleCache = TabTitleCache.getTabTitle(p.getUniqueId());
         if (titleCache != null)
         {
         if (rawHeader == null)
         {
         String headerString = titleCache.getHeader();
         if (headerString != null)
         {
         rawHeader = headerString;
         }
         }
         if (rawFooter == null)
         {
         String footerString = titleCache.getFooter();
         if (footerString != null)
         {
         rawFooter = footerString;
         }
         }
         }
         else
         {
         TabTitleCache.addTabTitle(p.getUniqueId(), titleCache = new TabTitleCache(rawHeader, rawFooter));
         }
         if (rawFooter != null)
         {
         titleCache.footer = rawFooter;
         }
         if (rawHeader != null)
         {
         titleCache.header = rawHeader;
         }
         }*/
        IChatBaseComponent header = ChatSerializer.a(TextConverter.convert(rawHeader));
        IChatBaseComponent footer = ChatSerializer.a(TextConverter.convert(rawFooter));
        //TabTitleCache.addTabTitle(p.getUniqueId(), new TabTitleCache(rawHeader, rawFooter));
       // ProtocolInjector.PacketTabHeader packet = new ProtocolInjector.PacketTabHeader(header, footer);
       // player.getHandle().playerConnection.sendPacket(packet);
        //setPlayerListHeader(player, rawHeader);
        //setPlayerListFooter(player, rawFooter);
    }
    
    public static void setPlayerListHeader(Player player,String header){
        CraftPlayer cplayer = (CraftPlayer) player;
        PlayerConnection connection = cplayer.getHandle().playerConnection;
        IChatBaseComponent hj = ChatSerializer.a("{'text':'"+header+"'}");
        PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
        try{
            Field headerField = packet.getClass().getDeclaredField("a");
            headerField.setAccessible(true);
            headerField.set(packet, hj);
            headerField.setAccessible(!headerField.isAccessible());
          
        } catch (Exception e){
            e.printStackTrace();
        }
        KoM.debug("Packet header para "+player.getName());
        connection.sendPacket(packet);
    }
  
  
    public static void setPlayerListFooter(Player player,String footer){
        CraftPlayer cp = (CraftPlayer) player;
        PlayerConnection con = cp.getHandle().playerConnection;
        IChatBaseComponent fj = ChatSerializer.a("{'text':'"+footer+"'}");
        PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
        try{
            Field footerField = packet.getClass().getDeclaredField("b");
            footerField.setAccessible(true);
            footerField.set(packet, fj);
            footerField.setAccessible(!footerField.isAccessible());
        }catch(Exception e){
            e.printStackTrace();
        }
        KoM.debug("Packet footer para "+player.getName());
        con.sendPacket(packet);
    }
 

    private static class TextConverter {

        public static String convert(String text)
        {
            if (text == null || text.length() == 0)
            {
                return "\"\"";
            }
            char c;
            int i;
            int len = text.length();
            StringBuilder sb = new StringBuilder(len + 4);
            String t;
            sb.append('"');
            for (i = 0; i < len; i += 1)
            {
                c = text.charAt(i);
                switch (c)
                {
                    case '\\':
                    case '"':
                        sb.append('\\');
                        sb.append(c);
                        break;
                    case '/':
                        sb.append('\\');
                        sb.append(c);
                        break;
                    case '\b':
                        sb.append("\\b");
                        break;
                    case '\t':
                        sb.append("\\t");
                        break;
                    case '\n':
                        sb.append("\\n");
                        break;
                    case '\f':
                        sb.append("\\f");
                        break;
                    case '\r':
                        sb.append("\\r");
                        break;
                    default:
                        if (c < ' ')
                        {
                            t = "000" + Integer.toHexString(c);
                            sb.append("\\u").append(t.substring(t.length() - 4));
                        }
                        else
                        {
                            sb.append(c);
                        }
                }
            }
            sb.append('"');
            return sb.toString();
        }

        public static String setPlayerName(Player player, String text)
        {
            return text.replaceAll("(?i)\\{PLAYER\\}", player.getName());
        }
    }

    private static class TabTitleCache {

        final private static Map<UUID, TabTitleCache> playerTabTitles = new HashMap<UUID, TabTitleCache>();
        private String header;
        private String footer;

        public TabTitleCache(String header, String footer)
        {
            this.header = header;
            this.footer = footer;
        }

        public static TabTitleCache getTabTitle(UUID uuid)
        {
            return playerTabTitles.get(uuid);
        }

        public static void addTabTitle(UUID uuid, TabTitleCache titleCache)
        {
            playerTabTitles.put(uuid, titleCache);
        }

        public static void removeTabTitle(UUID uuid)
        {
            playerTabTitles.remove(uuid);
        }

        public String getHeader()
        {
            return header;
        }

        public String getFooter()
        {
            return footer;
        }
    }
}
