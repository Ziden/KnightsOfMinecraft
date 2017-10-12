package nativelevel.Custom;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import nativelevel.KoM;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;

public class ItemLoader {

    public static HashMap<Class,CustomItem> loaded = new HashMap<Class,CustomItem>();
    public static HashMap<String,CustomItem> porNome = new HashMap<String,CustomItem>();
    
    public static void load() {
        File f = new File(KoM.class.getProtectionDomain().getCodeSource().getLocation().getFile().replaceAll("%20", " ")).getAbsoluteFile();
        JarFile jf;
        try {
            jf = new JarFile(f);
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }
        Enumeration en = jf.entries();
        while (en.hasMoreElements()) {
            Object entry = en.nextElement();
            Object addon = getCarta(entry);
            String mecanicas = "";
            if (addon != null) {
                if (addon instanceof CustomItem) {
                    CustomItem h = (CustomItem) addon;
                    loaded.put(h.getClass(), h);
                    porNome.put(h.name, h);
                    KoM.log.info("Loaded item "+h.name);
                }
            }
        }
    }

    private static Object getCarta(Object ne) {
        JarEntry entry = (JarEntry) ne;
        String name = entry.getName();
        name = name.replaceAll("/", ".");
        if (!name.endsWith(".class")) {
            return null;
        }
        name = name.replace(".class", "");
        if (!name.contains("Custom.Items")) {
            return null;
        }
        Class c;
        try {
            c = Class.forName(name);
        } catch (ClassNotFoundException ex) {

            ex.printStackTrace();
            return null;
        }
        if (CustomItem.class.isAssignableFrom(c)) {
            CustomItem w = null;
            try {

                w = (CustomItem) c.newInstance();
                return w;

            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }
        return null;
    }
}
