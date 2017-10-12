package nativelevel.Equipment.Generator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import nativelevel.Equipment.EquipMeta;
import nativelevel.ComandosNovos.Comando;
import nativelevel.KoM;
import nativelevel.gemas.Raridade;
import org.bukkit.Material;

/**
 *
 * @author Ziden
 *
 */
public abstract class PreSet {

    public abstract Raridade getRaridade();

    public abstract Material getMat();
    
    public abstract String nome();

    public abstract EquipMeta getStats();

    public static List<PreSet> sets = new ArrayList<PreSet>();

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
                    PreSet h = (PreSet) addon;
                    sets.add(h);

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
        if (!name.contains("Generator.sets")) {
            return null;
        }
        Class c;
        try {
            c = Class.forName(name);
        } catch (ClassNotFoundException ex) {

            ex.printStackTrace();
            return null;
        }
        if (PreSet.class.isAssignableFrom(c)) {
            PreSet w = null;
            try {

                w = (PreSet) c.newInstance();
                return w;

            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }

        return null;
    }

}
