package UAW.entities;

import mindustry.entities.Units.Sortf;

public class UAWUnitSorts {
	public static Sortf placeholder,

	/** Target enemies with the highest current HP */
	mostHitPoints = (u, x, y) -> -u.health,
	/** Target enemies with the least current HP */
	leastHitPoints = (u, x, y) -> u.health,

	/** Target enemies with the biggest {@code hitSize} */
	biggest = (u, x, y) -> -u.hitSize,
	/** Target enemies with the smallest {@code hitSize} */
	smallest = (u, x, y) -> u.hitSize,

	/** Target enemies with the highest {@code armor} value */
	mostArmored = (u, x, y) -> -u.armor,
	/** Target enemies with the lowest {@code armor} value */
	leastArmored = (u, x, y) -> u.armor,

	/** Target fastest enemies */
	fastest = (u, x, y) -> -u.speed(),
	/** Target slowest enemies */
	slowest = (u, x, y) -> u.speed(),

	/** Target highest flying enemies */
	highest = (u, x, y) -> -u.elevation,
	/** Target lowest flying enemies */
	lowest = (u, x, y) -> u.elevation;

}
