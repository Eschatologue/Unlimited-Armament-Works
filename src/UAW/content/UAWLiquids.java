package UAW.content;

import UAW.audiovisual.UAWPal;
import mindustry.content.*;
import mindustry.type.Liquid;

public class UAWLiquids {
	public static Liquid placeholder,
	// Liquid
	phlogiston,
	// Gas
	steam;

	public static void load() {

		phlogiston = new Liquid("liquid-phlogiston", UAWPal.phlogistonMid) {{
			viscosity = 0.75f;
			flammability = 1.4f;
			explosiveness = 2.4f;
			heatCapacity = 0.85f;
			temperature = 4;
			barColor = UAWPal.phlogistonMid;
			boilPoint = -1;
			gasColor = UAWPal.phlogistonFront.a(0.4f);
			canStayOn.add(Liquids.oil);



			color = color.cpy();
			color.a = 0.6f;
			gasColor = color;
			if (barColor == null) {
				barColor = color.cpy().a(1f);
			}
			coolant = false;
		}};

		steam = new Liquid("gas-steam", UAWPal.steamFront) {{
			gas = true;
			alwaysUnlocked = true;
			explosiveness = 0f;
			temperature = 0.75f;
			effect = StatusEffects.wet;
		}};
	}
}
