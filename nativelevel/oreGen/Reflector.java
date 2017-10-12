/*

 ╭╮╭━╮╱╱╭━╮╭━╮
 ┃┃┃╭╯╱╱┃┃╰╯┃┃
 ┃╰╯╯╭━━┫╭╮╭╮┃
 ┃╭╮┃┃╭╮┃┃┃┃┃┃
 ┃┃┃╰┫╰╯┃┃┃┃┃┃
 ╰╯╰━┻━━┻╯╰╯╰╯

 Desenvolvedor: ZidenVentania
 Colaboradores: NeT32, Gabripj, Feldmann
 Patrocionio: InstaMC

 */
package nativelevel.oreGen;

import java.lang.reflect.Field;

public class Reflector {

    public static Object getStaticValue(final String className, final String fieldName) throws SecurityException, NoSuchFieldException, ClassNotFoundException,
            IllegalArgumentException, IllegalAccessException {
        // Get the private field
        final Field field = Class.forName(className).getDeclaredField(fieldName);
        // Allow modification on the field
        field.setAccessible(true);
        // Return the Obect corresponding to the field
        return field.get(Class.forName(className));
    } // asd

    public static void setStaticValue(final String className, final String fieldName, final Object newValue) throws SecurityException, NoSuchFieldException,
            ClassNotFoundException, IllegalArgumentException, IllegalAccessException {
        final Field field = Class.forName(className).getDeclaredField(fieldName);
        field.setAccessible(true);
        final Object oldValue = field.get(Class.forName(className));
        field.set(oldValue, newValue);
    }

    public static Object getInstanceValue(final Object classInstance, final String fieldName) throws SecurityException, NoSuchFieldException,
            ClassNotFoundException, IllegalArgumentException, IllegalAccessException {
        final Field field = classInstance.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(classInstance);
    }

    public static void setInstanceValue(final Object classInstance, final String fieldName, final Object newValue) throws SecurityException,
            NoSuchFieldException, ClassNotFoundException, IllegalArgumentException, IllegalAccessException {
        final Field field = classInstance.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(classInstance, newValue);
    }
}
