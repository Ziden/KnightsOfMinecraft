package nativelevel.utils;

//<editor-fold defaultstate="collapsed" desc="imports">
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
//</editor-fold>

/**
 *
 * @author ciro
 */
public class UtilsCiro {

    public static boolean isInt(String s) {
        try {
            int i = Integer.parseInt(s);
            return true;
        } catch (Exception e) {
        }
        return false;
    }
    
    public static boolean isInt(double s) {
        if ((s == Math.floor(s)) && !Double.isInfinite(s)) {
            return true;
        }
        return false;
    }

    public static int parseInt(String n) {
        int num = 0;
        try {
            num = Integer.parseInt(n);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return num;
    }

    public static float parseFloat(String n) {
        float num = 0;
        try {
            num = Float.parseFloat(n);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return num;
    }

    public static boolean hasBlockDown(Location loc, Material material, int limit) {
        for (int i = 0; i < limit; i++) {
            if (loc.add(0, -1, 0).getBlock().getType() == material) {
                return true;
            }
        }
        return false;
    }

    public static double parseDouble(String n) {
        double num = 0;
        try {
            num = Double.parseDouble(n);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return num;
    }

    public static String getProgressBar(int value, int total) {
        float percent = (value * 100.0f) / total;
        String percentage = "";
        for (int i = 1; i < 100; i++) {

            if (percent >= i) {
                percentage += "§a┃";
            } else {
                percentage += "§f┃";
            }
        }
        return percentage + " " + ChatColor.WHITE + Math.round(percent) + "%";
    }

}
