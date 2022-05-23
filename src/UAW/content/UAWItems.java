package UAW.content;

import UAW.graphics.UAWPal;
import arc.graphics.Color;
import mindustry.type.Item;

public class UAWItems {
	public static Item placeholder,
		cryogel, anthracite, compositeAlloy, dieselCore;

	public static void load() {
		cryogel = new Item("item-cryogel", Color.valueOf("87ceeb")) {{
			flammability = -10f;
			explosiveness = 0f;
		}};
		anthracite = new Item("item-anthracite", Color.valueOf("272727")) {{
			flammability = 1.8f;
			explosiveness = 0.25f;
			hardness = 4;
		}};
		compositeAlloy = new Item("item-composite-alloy", UAWPal.compAlloyMid) {{
			cost = 2.5f;
		}};

	}
}