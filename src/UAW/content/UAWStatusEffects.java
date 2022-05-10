package UAW.content;

import UAW.graphics.UAWPal;
import mindustry.content.Fx;
import mindustry.entities.effect.MultiEffect;
import mindustry.graphics.Pal;
import mindustry.type.StatusEffect;

public class UAWStatusEffects {
	public static StatusEffect
		concussion, EMP, steamBurn;

	public static void load() {
		concussion = new StatusEffect("concussion") {{
			color = Pal.lightishGray;
			reloadMultiplier = speedMultiplier = 0.6f;
			show = false;
		}};
		EMP = new StatusEffect("emp") {{
			effect = new MultiEffect(
				Fx.hitLancer,
				Fx.smoke
			);
			color = UAWPal.titaniumBlueFront;
			buildSpeedMultiplier = speedMultiplier = 0.2f;
			reloadMultiplier = 0f;
		}};
	}
}