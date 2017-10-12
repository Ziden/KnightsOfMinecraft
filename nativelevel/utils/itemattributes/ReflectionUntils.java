package nativelevel.utils.itemattributes;
import org.bukkit.Bukkit;
/**
 * This class uses reflections to get classes and be independent from static import paths
 * 
 * @author Michel_0
 */
public class ReflectionUntils {
	/**
	 * Get a class out of net.minecraft.server.vX_X_RX
	 * 
	 * @param name Name of the class
	 * @return The class itself
	 */
	public static Class<?> getNMSClass (String name) {
		try {
			return Class.forName("net.minecraft.server." + Bukkit.getServer().getClass().getName().split("\\.")[3] + "." + name);
		} catch (ClassNotFoundException e) {
			Bukkit.getLogger().info("[Reflection] Can't find NMS Class! (" + "net.minecraft.server." + Bukkit.getServer().getClass().getName().split("\\.")[3] + "." + name + ")");
			return null;			
		}
	}
	/**
	 * Get a class out of org.bukkit.craftbukkit.vX_X_RX
	 * 
	 * @param name Name of the class
	 * @return The class itself
	 */
	public static Class<?> getCBClass(String name) {
		try {
			return Class.forName("org.bukkit.craftbukkit." + Bukkit.getServer().getClass().getName().split("\\.")[3] + "." + name);
		} catch (ClassNotFoundException e) {
			Bukkit.getLogger().info("[Reflection] Can't find CB Class! (" + "org.bukkit.craftbukkit." + Bukkit.getServer().getClass().getName().split("\\.")[3] + "." + name + ")");
			return null;			
		}
	}
}