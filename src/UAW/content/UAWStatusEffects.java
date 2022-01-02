package UAW.content;

import UAW.graphics.UAWPal;
import mindustry.content.Fx;
import mindustry.ctype.ContentList;
import mindustry.entities.effect.MultiEffect;
import mindustry.graphics.Pal;
import mindustry.type.StatusEffect;

public class UAWStatusEffects implements ContentList {
	public static StatusEffect
		concussion, EMP, frostBurn;

	@Override
	public void load() {
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
			buildSpeedMultiplier = reloadMultiplier = speedMultiplier = 0.05f;
		}};
	}
}