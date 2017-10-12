package nativelevel.utils.itemattributes;
/**
 * An ENUM of all valid attributes for item attribute modifiers.
 * 
 * @author Michel_0
 */
public enum Attribute {
	MAX_HEALTH ("generic.maxHealth"),
	FOLLOW_RANGE ("generic.followRange"),
	KNOCKBACK_RESISTANCE ("generic.knockbackResistance"),
	MOVEMENT_SPEED ("generic.movementSpeed"),
	ATTACK_DAMAGE ("generic.attackDamage"),
	ARMOR ("generic.armor"),
	ARMOR_THOUGHNESS ("generic.armorToughness"),
	ATTACK_SPEED ("generic.attackSpeed"),
	LUCK ("generic.luck"),
	JUMP_STRENGTH ("horse.jumpStrength"),
	SPAWN_REINFORCEMENTS ("zombie.spawnReinforcements");
	private String name;
	Attribute(String name) {
		this.name = name;
	}
	/**
	 * Get the predefined, global and unique name of this slot.
	 * 
	 * @return The name
	 */
	public String getName() {
		return this.name;
	}
}