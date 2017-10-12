package nativelevel.Lang;

import java.io.IOException;
import nativelevel.KoM;
import nativelevel.utils.ConfigManager;
import org.bukkit.configuration.InvalidConfigurationException;

/**
 *
 * @author Gabriel
 */
public class L {

    public static ConfigManager ConfLanguage;
    public static ConfigManager Lang;

    public static void LoadLang() {
        try {
            ConfLanguage = new ConfigManager(KoM._instance.getDataFolder() + "/strings.yml");
            init("Classes.Alchemist", "Alquimista");
            init("Classes.AlchemistClass.ThrowTnt", "Apenas bons alquimistas podem jogar TnT !");
            init("HitLeg", "Voce machucou sua perna !");
            init("TooHeavy", "Voce esta levando peso demais em sua armadura !!");
            Lang = new ConfigManager(KoM._instance.getDataFolder() + "/lang.yml");
        } catch (Exception ex) {

        }
    }

    public static String get(String node) {
        return L.ConfLanguage.getConfig().getString(node);
    }

    
    public static String m(String msg) {
        return msg;
    }
    
    public static String m(String msg, String p) {
        return msg.replace("%", p);
    }
    
    public static String m(String msg, int p) {
        return msg.replace("%", p+"");
    }
    
    public static String m(String msg, long p) {
        return msg.replace("%", p+"");
    }
    
    /// OS LANG BUGA AS MAGIA DUSMAGO
    /*
    
    public static String m(String msg) {
         String node = msg.replace(" ", "");
         node = msg.replace(".", "");
         node = msg.replace(":", "");
         if (!Lang.getConfig().contains(node)) {
         Lang.getConfig().set(node, msg);
         Lang.SaveConfig();
         }
         return L.Lang.getConfig().getString(node);
         
        //return msg;
    }

    public static String m(String msg, String parm) {
        String node = msg.replace(" ", "");
        node = msg.replace(".", "");
        node = msg.replace(":", "");
        if (!Lang.getConfig().contains(node)) {
            Lang.getConfig().set(node, msg);
            Lang.SaveConfig();
        }
        return L.Lang.getConfig().getString(node).replace("%", parm);
    }
    
    public static String m(String msg, int parm) {
        String node = msg.replace(" ", "");
         node = msg.replace(".", "");
         node = msg.replace(":", "");
        if (!Lang.getConfig().contains(node)) {
            Lang.getConfig().set(node, msg);
            Lang.SaveConfig();
        }
        return L.Lang.getConfig().getString(node).replace("%", ""+parm);
    }
    
     public static String m(String msg, long parm) {
        String node = msg.replace(" ", "");
        node = msg.replace(".", "");
        node = msg.replace(":", "");
        if (!Lang.getConfig().contains(node)) {
            Lang.getConfig().set(node, msg);
            Lang.SaveConfig();
        }
        return L.Lang.getConfig().getString(node).replace("%", ""+parm);
    }

    */

    public static void i(String node, Object value) {
        if (!Lang.getConfig().contains(node)) {
            Lang.getConfig().set(node, value);
        }
        Lang.SaveConfig();
    }

    public static void init(String node, Object value) {
        if (!ConfLanguage.getConfig().contains(node)) {
            ConfLanguage.getConfig().set(node, value);
        }
        ConfLanguage.SaveConfig();
    }
}
