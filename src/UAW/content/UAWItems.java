package UAW.content;

import arc.graphics.Color;
import mindustry.ctype.ContentList;
import mindustry.type.Item;

public class UAWItems implements ContentList {
	public static Item
		//compounds
		cryogel,
	//Carbides
	titaniumCarbide, siliconCarbide;

	@Override
	public void load() {
		cryogel = new Item("cryogel", Color.valueOf("87ceeb")) {{
			flammability = -10f;
			explosiveness = 0f;
		}};

		//Carbides
		titaniumCarbide = new Item("titanium-carbide", Color.valueOf("7575C8")) {{
			cost = 2.5f;
			hardness = 10;
		}};

	}
}