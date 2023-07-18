package UAW.world.meta;

import mindustry.world.meta.*;

public class UAWStat extends Stat {
	public static final UAWStat

		armorBreak = new UAWStat("armor-break"),
		hardnessTreshold = new UAWStat("hardness-tresh", StatCat.crafting),
		hardnessUpperTresh = new UAWStat("hardness-upper-tresh", StatCat.crafting),
		hardnessLowerTresh = new UAWStat("hardness-lower-tresh", StatCat.crafting),
		fuelEfficiencyMult = new UAWStat("fuel-efficiency-mult", StatCat.general);

	public UAWStat(String name, StatCat category) {
		super(name, category);
	}

	public UAWStat(String name) {
		super(name);
	}
}
