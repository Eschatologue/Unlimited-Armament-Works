package UAW.world.meta;

import mindustry.world.meta.*;

// Idk what i wll use this for
public class UAWStat extends Stat {
	public static final UAWStat

		armorBreak = new UAWStat("armor-break"),
		hardnessTreshold = new UAWStat("hardness-tresh", StatCat.crafting),
		hardnessUpperTresh = new UAWStat("hardness-upper-tresh", StatCat.crafting),
		hardnessLowerTresh = new UAWStat("hardness-lower-tresh", StatCat.crafting);

	public UAWStat(String name, StatCat category) {
		super(name, category);
	}

	public UAWStat(String name) {
		super(name);
	}
}
