package nativelevel.ComandosNovos;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import nativelevel.KoM;
import org.bukkit.Bukkit;

/**
 *
 * @author Carlos André Feldmann Júnior
 */
public class CommandLoader {

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
        String carregados = "";
        while (en.hasMoreElements()) {

            Object entry = en.nextElement();
            Object addon = getCarta(entry);

            if (addon != null) {
                if (addon instanceof Comando) {
                    Comando h = (Comando) addon;
                    carregados += "/" + h.getName() + " ";
                    KoM.addCommand(h);

                }
            }
        }
        KoM.log.info("");
        KoM.log.info("COMANDOS CARREGADOS: ");
        KoM.log.info(carregados);
        KoM.log.info("");
    }

    private static Object getCarta(Object ne) {
        JarEntry entry = (JarEntry) ne;
        String name = entry.getName();
        name = name.replaceAll("/", ".");
        if (!name.endsWith(".class")) {
            return null;
        }
        name = name.replace(".class", "");
        if (!name.contains("ComandosNovos.list")) {
            return null;
        }
        Class c;
        try {
            c = Class.forName(name);
        } catch (ClassNotFoundException ex) {

            ex.printStackTrace();
            return null;
        }
        if (Comando.class.isAssignableFrom(c)) {
            Comando w = null;
            try {

                w = (Comando) c.newInstance();
                return w;

            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }

        return null;
    }
}
