package nativelevel.mercadinho;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Pattern;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 *
 * @author gabripj
 */
public class Utils {

    public static void logplS(Player p, String log)
    {
        p.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "KOM" + ChatColor.WHITE + ChatColor.DARK_GRAY + " > " + ChatColor.RESET + log);
    }

    public static double trim(int degree, double d)
    {
        String format = "0.0";
        for (int i = 1; i < degree; i++)
        {
            format = format + "0";
        }
        DecimalFormat twoDForm = new DecimalFormat(format);
        return Double.valueOf(twoDForm.format(d).replace(",", "."));
    }

    public static void AddLog(String msg)
    {
       
    }

//DESISTO ODEIO PORTUGUES (HUE)
    public static String getPlural(String pl)
    {
        char ultimaletra = pl.charAt(pl.length() - 1);
        String ultimas = pl.charAt(pl.length() - 2) + ultimaletra + "";
        String plural = pl.substring(0, pl.length() - 2);
        if (ultimaletra == 'm')
        {
            plural += "ns";
        }
        else if (ultimaletra == 'l')
        {
            plural += "ls";
        }
        return plural;
    }

    public static String GetIp(String domain)
    {
        InetAddress giriAddress = null;
        try
        {
            giriAddress = java.net.InetAddress.getByName(domain);
        }
        catch (UnknownHostException ex)
        {
            ex.printStackTrace();
            //REINICIA EVITAR PAU;
            Bukkit.shutdown();
        }
        String address = giriAddress.getHostAddress();
        return address;
    }

    public static String GetNomeServerOLD()
    {
        File f1 = Bukkit.getUpdateFolderFile().getParentFile();
        String[] Aux = f1.getAbsolutePath().split("/");
        return (Aux[(Aux.length - 2)]);
    }

    public static String GetNomeServer()
    {
        String path = null;
        try
        {
            path = new File(".").getCanonicalPath();
            path = path.substring(path.lastIndexOf(File.separator) + 1);
            return path;
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            return "";
        }
    }

    /*
     *Bom para quebrar linhas de texto para lores sem precisar pensar mto
     */
    public static List<String> quebra(int praquebra, String s)
    {
        List<String> list = new ArrayList();
        int sempula = 0;
        int lastpulo = 0;
        for (int x = 0; x < s.length(); x++)
        {
            sempula++;
            if (sempula >= praquebra)
            {
                if (s.charAt(x) == ' ')
                {
                    list.add(s.substring(lastpulo, x).trim());
                    lastpulo = x;
                    sempula = 0;
                }
            }
        }
        list.add(s.substring(lastpulo, s.length()).trim());
        return list;
    }
    /*public static void TeleportarTPBG(Player sender, String server) {
     Bukkit.getMessenger().registerOutgoingPluginChannel(NewLibrarySystem.instancia, "BungeeCord");
     ByteArrayOutputStream b = new ByteArrayOutputStream();
     DataOutputStream out = new DataOutputStream(b);
     try {
     out.writeUTF("Connect");
     out.writeUTF(server);
     } catch (IOException localIOException) {
     }
     ((PluginMessageRecipient) sender).sendPluginMessage(NewLibrarySystem.instancia, "BungeeCord", b.toByteArray());
     }*/

    public static int randInt(int min, int max)
    {
        //Funciona
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    public static double randDouble(double min, double max)
    {
        //Também funciona, o meu é melhor tem decimal
        double random = new Random().nextDouble();
        double result = min + (random * (max - min));
        return result;
    }
    public static Pattern chatColorPattern = Pattern.compile("(?i)&([0-9A-F])");
    //public static Pattern chatMagicPattern = Pattern.compile("(?i)&([K])"); // Cor Ofuscante
    public static Pattern chatBoldPattern = Pattern.compile("(?i)&([L])");
    public static Pattern chatStrikethroughPattern = Pattern.compile("(?i)&([M])");
    public static Pattern chatUnderlinePattern = Pattern.compile("(?i)&([N])");
    public static Pattern chatItalicPattern = Pattern.compile("(?i)&([O])");
    public static Pattern chatResetPattern = Pattern.compile("(?i)&([R])");

    public static String translateColorCodes(String string)
    {
        if (string == null)
        {
            return "";
        }
        String newstring = string;
        newstring = chatColorPattern.matcher(newstring).replaceAll("\u00A7$1");
        //newstring = chatMagicPattern.matcher(newstring).replaceAll("\u00A7$1"); // Cor Ofuscante
        newstring = chatBoldPattern.matcher(newstring).replaceAll("\u00A7$1");
        newstring = chatStrikethroughPattern.matcher(newstring).replaceAll("\u00A7$1");
        newstring = chatUnderlinePattern.matcher(newstring).replaceAll("\u00A7$1");
        newstring = chatItalicPattern.matcher(newstring).replaceAll("\u00A7$1");
        newstring = chatResetPattern.matcher(newstring).replaceAll("\u00A7$1");
        return newstring;
    }
    public static HashMap<UUID, Integer> stopSchedulers = new HashMap<UUID, Integer>();

}
