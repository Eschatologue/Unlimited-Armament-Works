package UAW.content;

import arc.graphics.Color;
import gas.type.Gas;
import mindustry.content.StatusEffects;
import mindustry.ctype.ContentList;

public class UAWGas implements ContentList {
	public static Gas placeholder,
		steam, lpg, sulfurDioxide;

	@Override
	public void load() {
		steam = new Gas("gas-steam") {{
			color = Color.valueOf("cccedb");
			explosiveness = 0f;
			temperature = 1f;
			effect = StatusEffects.wet;
		}};
	}
}