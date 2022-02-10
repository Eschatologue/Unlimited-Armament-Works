package UAW.content;

import arc.graphics.Color;
import gas.type.Gas;
import mindustry.ctype.ContentList;

public class UAWGas implements ContentList {
	public static Gas placeholder,
		steam;

	@Override
	public void load() {
		steam = new Gas("steam") {{
			color = Color.valueOf("bcf9ff");
			explosiveness = 0f;
			temperature = 1f;
		}};
	}
}
