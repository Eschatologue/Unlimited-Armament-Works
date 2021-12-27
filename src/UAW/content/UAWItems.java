package UAW.content;

import arc.graphics.Color;
import mindustry.ctype.ContentList;
import mindustry.type.Item;

public class UAWItems implements ContentList {
	public static Item placeholder,
		cryogel, anthracite, titaniumCarbide;

	@Override
	public void load() {
		cryogel = new Item("item-cryogel", Color.valueOf("87ceeb")) {{
			flammability = -10f;
			explosiveness = 0f;
		}};

		anthracite = new Item("item-anthracite", Color.valueOf("272727")) {{
			flammability = 3.6f;
			explosiveness = 0.5f;
			hardness = 6;
		}};

		titaniumCarbide = new Item("item-titanium-carbide", Color.valueOf("7575C8")) {{
			cost = 2.5f;
		}};

	}
}