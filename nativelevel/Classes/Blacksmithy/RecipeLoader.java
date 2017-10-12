package nativelevel.Classes.Blacksmithy;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import nativelevel.KoM;
import nativelevel.RecipeBooks.RecipePage;

/**
 *
 * @author vntgasl
 */
public class RecipeLoader
{

    public static HashMap<String, CustomCrafting> customItems = new HashMap<String, CustomCrafting>();
    public static HashMap<Class, CustomCrafting> customItemsClass = new HashMap<Class, CustomCrafting>();
 
    public static void load()
    {
        File f = new File(KoM.class.getProtectionDomain().getCodeSource().getLocation().getFile().replaceAll("%20", " ")).getAbsoluteFile();
        JarFile jf;
        try
        {
            jf = new JarFile(f);
        } catch (IOException ex)
        {
            ex.printStackTrace();
            return;
        }
        
        KoM.log.info("Carregando receitas de crafting");
        
        Enumeration en = jf.entries();

        while (en.hasMoreElements())
        {

            Object entry = en.nextElement();
            Object addon = getCarta(entry);

            if (addon != null)
            {
                if (addon instanceof CustomCrafting)
                {
                    CustomCrafting h = (CustomCrafting) addon;
                    KoM.log.info("Carregou custom crafting " + h.name + " !");
                    customItems.put(h.name, h);
                    customItemsClass.put(h.getClass(), h);
                    RecipePage.recipes.set(h.name, h.generateRecipe());
                }
            }
        }

    }

    private static Object getCarta(Object ne)
    {
        JarEntry entry = (JarEntry) ne;
        String name = entry.getName();
        name = name.replaceAll("/", ".");
        if (!name.endsWith(".class"))
        {
            return null;
        }
        name = name.replace(".class", "");
        if (!name.contains("Blacksmithy.recipes"))
        {
            return null;
        }
        Class c;
        try
        {
            c = Class.forName(name);
        } catch (ClassNotFoundException ex)
        {

            ex.printStackTrace();
            return null;
        }
        if (CustomCrafting.class.isAssignableFrom(c))
        {
          
            CustomCrafting w = null;
            try
            {

                w = (CustomCrafting) c.newInstance();
                return w;

            } catch (Exception ex)
            {
                ex.printStackTrace();
                return null;
            }
        }

        return null;
    }

}
