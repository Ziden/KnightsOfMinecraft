package nativelevel.scores;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffect;

public class PacotesAPI {


    /**
     * Altera o tamanho da entidade Player.
     * <p>
     * Ocultar: Player, -1F, -1F, -1F;
     * <p>
     * Mostrar: Player, 0F, 0.6F, 1.8F;
     *
     * @param player
     * @param height
     * @param width
     * @param length
     */
    public static void SetWidthHeight(Player player, float height, float width, float length)
    {
        try
        {
            String currentVersion;
            Object propertyManager;
            Object obj = Bukkit.getServer().getClass().getDeclaredMethod("getServer").invoke(Bukkit.getServer());
            propertyManager = obj.getClass().getDeclaredMethod("getPropertyManager").invoke(obj);
            currentVersion = propertyManager.getClass().getPackage().getName();
            Method handle = player.getClass().getMethod("getHandle");
            Class c = Class.forName(currentVersion + ".Entity");
            Field field1 = c.getDeclaredField("height");
            Field field2 = c.getDeclaredField("width");
            Field field3 = c.getDeclaredField("length");
            field1.setFloat(handle.invoke(player), (float) height);
            field2.setFloat(handle.invoke(player), (float) width);
            field3.setFloat(handle.invoke(player), (float) length);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
   
    public static Firework SpawnRandomFirework(Location loc)
    {
        Firework fw = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
        FireworkMeta fwm = fw.getFireworkMeta();
        Random r = new Random();
        int rt = r.nextInt(4) + 1;
        FireworkEffect.Type type = FireworkEffect.Type.BALL;
        if (rt == 1)
        {
            type = FireworkEffect.Type.BALL;
        }
        if (rt == 2)
        {
            type = FireworkEffect.Type.BALL_LARGE;
        }
        if (rt == 3)
        {
            type = FireworkEffect.Type.BURST;
        }
        if (rt == 4)
        {
            type = FireworkEffect.Type.CREEPER;
        }
        if (rt == 5)
        {
            type = FireworkEffect.Type.STAR;
        }
        Color c1 = Color.WHITE;
        Color c2 = Color.YELLOW;
        Color c3 = Color.fromRGB(255,200,80);
        FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(c1).withColor(c2).withFade(c3).with(type).trail(r.nextBoolean()).build();
        fwm.addEffect(effect);
        int rp = r.nextInt(2) + 1;
        fwm.setPower(rp);
        fw.setFireworkMeta(fwm);
        return fw;
    }


    public static void ClearPlayer(Player player, boolean PotionEffets)
    {
        ItemStack none = new ItemStack(Material.AIR);
        player.getInventory().clear();
        player.getInventory().setHelmet(none);
        player.getInventory().setChestplate(none);
        player.getInventory().setLeggings(none);
        player.getInventory().setBoots(none);
        if (PotionEffets)
        {
            for (PotionEffect pPE : player.getActivePotionEffects())
            {
                player.removePotionEffect(pPE.getType());
            }
        }
    }

    public static void ChangeAllLevels(int tempo)
    {
        for (Player pOn : Bukkit.getOnlinePlayers())
        {
            pOn.setLevel(tempo);
        }
    }

    public static String FormataTempo(int Segundos)
    {
        int minuto = (int) Segundos / 60;
        double aux = Segundos;
        while (!isInteiro(aux / 60))
        {
            aux--;
        }
        int sosegundos = (int) (Segundos - aux);
        String toSegundos = String.valueOf(sosegundos);
        if (toSegundos.length() == 1)
        {
            toSegundos = 0 + toSegundos;
        }
        return (minuto + ":" + toSegundos);
    }

    public static boolean isInteiro(double var)
    {
        return var % 1 == 0;
    }

    public static Location String2Location(String locString)
    {
        String loc[] = locString.split(";");
        return new Location(Bukkit.getServer().getWorld(loc[0]), Double.parseDouble(loc[1]), Double.parseDouble(loc[2]), Double.parseDouble(loc[3]), Float.parseFloat(loc[4]), Float.parseFloat(loc[5]));
    }

    public static String Location2String(Location locLocation)
    {
        return locLocation.getWorld().getName() + ";" + locLocation.getX() + ";" + locLocation.getY() + ";" + locLocation.getZ() + ";" + locLocation.getYaw() + ";" + locLocation.getPitch();
    }

    public static void sendTitle(String title, String subtitle, int fadeInTime, int stayTime, int fadeOutTime)
    {
        Title sendThis = new Title(title, subtitle, fadeInTime, stayTime, fadeOutTime);
        sendThis.setTimingsToTicks();
        sendThis.broadcast();
    }

    public static void sendTitle(Player player, String title, String subtitle, int fadeInTime, int stayTime, int fadeOutTime)
    {
        Title sendThis = new Title(title, subtitle, fadeInTime, stayTime, fadeOutTime);
        sendThis.setTimingsToTicks();
        sendThis.send(player);
    }

    public static void sendTabHeader(String header, String footer)
    {
        TabHeader.broadcastHeaderAndFooter(header, footer);
    }

    public static void sendTabHeader(Player player, String header, String footer)
    {
        TabHeader.setHeaderAndFooter(player, header, footer);
    }

    public static void sendActionBar(String msg, int tempo)
    {
       //// ActionBar actionBar = new ActionBar(msg);
       // actionBar.broadcast();
    }

    public static void sendActionBar(Player player, String msg)
    {
       // ActionBar actionBar = new ActionBar(msg);
       // actionBar.send(player);
    }
}
