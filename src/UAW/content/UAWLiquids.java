package UAW.content;

import mindustry.content.StatusEffects;
import mindustry.ctype.ContentList;
import mindustry.graphics.Pal;
import mindustry.type.Liquid;

public class UAWLiquids implements ContentList {
	public static Liquid
		surgeSolvent;

	@Override
	public void load() {
		surgeSolvent = new Liquid("liquid-surge-solvent", Pal.surge) {{
			viscosity = 0.50f;
			temperature = 0.5f;
			heatCapacity = 0.8f;
			barColor = Pal.surge;
			effect = StatusEffects.electrified;
			lightColor = Pal.surge.a(0.4f);
		}};
	}
}
