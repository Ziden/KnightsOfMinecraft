package nativelevel.utils.itemattributes;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;
import org.bukkit.Bukkit;
/**
 * This class represents a single attribute modifier, that can be applied on an item.
 * 
 * @author Michel_0
 */
public class AttributeModifier {
	private String attribute;
	private String name;
	private String slot;
	private int operation;
	private double amount;
	private UUID uuid;
	/**
	 * Constructor:
	 * Generate a new attribute modifier, that can be applied on items.
	 * 
	 * @param attribute Predefined global and unique name of the attribute, that should be modified
	 * @param name Any name of this special modifier
	 * @param slot Predefined global and unique name of the slot, when the modifier should be applied
	 * @param operation Type of the modifier
	 * @param amount Value of modification
	 * @param uuid Some UUID for this modifier
	 */
	public AttributeModifier(Attribute attribute, String name, Slot slot, int operation, double amount, UUID uuid) {
		this.attribute = attribute.getName();
		this.name = name;
		this.slot = slot.getName();
		this.operation = operation;
		this.amount = amount;
		this.uuid = uuid;
	}
	/**
	 * Constructor:
	 * Generate a new attribute modifier, that can be applied on items.
	 * 
	 * @deprecated This method allows you to define any string as attribute and slot.
	 * You should use the ENUM constructor to be on the safe side.
	 * Only use this method, if there is any new attribute or slot, which is not present within the ENUM. 
	 * 
	 * @param attribute Predefined global and unique name of the attribute, that should be modified
	 * @param name Any name of this special modifier
	 * @param slot Predefined global and unique name of the slot, when the modifier should be applied
	 * @param operation Type of the modifier
	 * @param amount Value of modification
	 * @param uuid Some UUID for this modifier
	 */
	@Deprecated
	public AttributeModifier(String attribute, String name, String slot, int operation, double amount, UUID uuid) {
		this.attribute = attribute;
		this.name = name;
		this.slot = slot;
		this.operation = operation;
		this.amount = amount;
		this.uuid = uuid;
	}
	/**
	 * Constructor:
	 * Generate a new attribute modifier out of an existing item NBT.
	 * 
	 * @param modifier NBT compound of the item [net.minecraft.server.NBTTagCompound]
	 */
	public AttributeModifier(Object modifier) {
		try {
			this.attribute = (String) modifier.getClass().getMethod("getString", String.class).invoke(modifier, "AttributeName");
			this.name = (String) modifier.getClass().getMethod("getString", String.class).invoke(modifier, "Name");
			this.slot = (String) modifier.getClass().getMethod("getString", String.class).invoke(modifier, "Slot");
			this.operation = (int) modifier.getClass().getMethod("getInt", String.class).invoke(modifier, "Operation");
			this.amount = (double) modifier.getClass().getMethod("getDouble", String.class).invoke(modifier, "Amount");
			this.uuid = new UUID((long) modifier.getClass().getMethod("getLong", String.class).invoke(modifier, "UUIDMost"), (long) modifier.getClass().getMethod("getLong", String.class).invoke(modifier, "UUIDLeast"));
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			Bukkit.getLogger().info("[ItemAttributeAPI] Incompatible server version! Some methods can't be applied.");
		}
	}
	/**
	 * Generate an NBT compound from this modifier, so it can be applied on items
	 * 
	 * @return The NBT compound, ready to be set on items [net.minecraft.server.NBTTagCompound]
	 */
	public Object getNBT() {
		try {
			Object data = ReflectionUntils.getNMSClass("NBTTagCompound").newInstance();
			if (data != null) {
				data.getClass().getMethod("setString", new Class[] {String.class, String.class}).invoke(data, new Object[] {"AttributeName", this.attribute});
				data.getClass().getMethod("setString", new Class[] {String.class, String.class}).invoke(data, new Object[] {"Name", this.name});
				data.getClass().getMethod("setString", new Class[] {String.class, String.class}).invoke(data, new Object[] {"Slot", this.slot});
				data.getClass().getMethod("setInt", new Class[] {String.class, int.class}).invoke(data, new Object[] {"Operation", this.operation});
				data.getClass().getMethod("setDouble", new Class[] {String.class, double.class}).invoke(data, new Object[] {"Amount", this.amount});
				data.getClass().getMethod("setLong", new Class[] {String.class, long.class}).invoke(data, new Object[] {"UUIDMost", this.uuid.getMostSignificantBits()});
				data.getClass().getMethod("setLong", new Class[] {String.class, long.class}).invoke(data, new Object[] {"UUIDLeast", this.uuid.getLeastSignificantBits()});
				return data;
			} else {
				Bukkit.getLogger().info("[ItemAttributeAPI] Incompatible Server version! Missing classes.");
				return null;
			}
		} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException e) {
			Bukkit.getLogger().info("[ItemAttributeAPI] Incompatible server version! Some methods can't be applied.");
			return null;
		}
	}
}