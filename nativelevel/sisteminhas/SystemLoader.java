package nativelevel.sisteminhas;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import nativelevel.KoM;

/**
 *
 * @author Ziden
 */
public class SystemLoader {

    public static HashSet<String> loaded = new HashSet<String>();
    
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
                if (addon instanceof KomSystem) {
                    KomSystem h = (KomSystem) addon;
                    h.plugin = KoM._instance;
                    try {
                        h.onEnable();
                    } catch (Exception ex) {
                        KoM.log.info("Erro ao carregar Mecanica: " + h.getClass().getName());
                        ex.printStackTrace();
                    }
                    mecanicas = mecanicas + h.getClass().getName()+ ",";
                    if (mecanicas.split(",").length >= 15) {
                        KoM.log.info("Sistemas Carregados: " + mecanicas);
                        mecanicas = "";
                    }
                    KoM._instance.getServer().getPluginManager().registerEvents(h, KoM._instance);
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
        if (!name.contains("nativelevel.")) {
            return null;
        }
        Class c;
        try {
            c = Class.forName(name);
        } catch (ClassNotFoundException ex) {

            ex.printStackTrace();
            return null;
        }
        if (KomSystem.class.isAssignableFrom(c) && !c.getName().equalsIgnoreCase("nativelevel.sisteminhas.KomSystem")) {
            KomSystem w = null;
            try {
                w = (KomSystem) c.newInstance();
                return w;
            } catch (Exception ex) {
                KoM.log.info("ERRO AO CARRREGAR SISTEMA "+c.getName());
                ex.printStackTrace();
                return null;
            }
        }
        return null;
    }
    
}
