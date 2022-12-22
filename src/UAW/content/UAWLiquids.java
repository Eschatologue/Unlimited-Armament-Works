package UAW.content;

import UAW.audiovisual.UAWPal;
import mindustry.content.StatusEffects;
import mindustry.type.Liquid;

public class UAWLiquids {
	public static Liquid placeholder,
	// Liquid
	liqOxygen, glycerine,
	// Gas
	steam, lpg;

	public static void load() {

		steam = new Liquid("gas-steam", UAWPal.steamFront) {{
			gas = true;
			alwaysUnlocked = true;
			explosiveness = 0f;
			temperature = 0.75f;
			effect = StatusEffects.wet;
		}};

		lpg = new Liquid("gas-lpg", UAWPal.cryoFront) {{
			gas = true;
			explosiveness = 2.4f;
			temperature = 0f;
		}};
	}
}
