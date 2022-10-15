package UAW.content;

import UAW.audiovisual.*;
import UAW.type.UAWStatusEffect;
import mindustry.content.Fx;
import mindustry.entities.effect.MultiEffect;
import mindustry.graphics.Pal;
import mindustry.type.StatusEffect;

import static UAW.Vars.tick;
import static mindustry.content.StatusEffects.*;

public class UAWStatusEffects {
	public static StatusEffect placeholder,

	// Basic
	concussion,
	// Thermal / Elemental
	cryoBurn, EMP, thermalShock,
	// Reactions
	superConduct;

	public static void load() {
		concussion = new UAWStatusEffect("concussion") {{
			color = Pal.lightishGray;
			reloadMultiplier = speedMultiplier = 0.6f;
		}};

		cryoBurn = new UAWStatusEffect("cryoburn") {{
			color = UAWPal.cryoMiddle;
			effect = UAWFx.statusEffectSquare.wrap(color);
			reloadMultiplier = 0.6f;
			speedMultiplier = 0.45f;
			healthMultiplier = 0.8f;
			damage = 0.55f;
			transitionDamage = 25;

			init(() -> {
				affinity(burning, (unit, result, time) -> {
					result.set(thermalShock, 2 * tick);
					unit.damagePierce(transitionDamage * 2f);
				});
				affinity(melting, (unit, result, time) -> {
					result.set(thermalShock, 5 * tick);
					unit.damagePierce(transitionDamage * 3f);
				});
				affinity(EMP, (unit, result, time) -> {
					result.set(superConduct, 15 * tick);
					unit.damagePierce(transitionDamage * 0.5f);
				});
			});
		}};
		EMP = new UAWStatusEffect("emp") {{
			color = UAWPal.surgeBack;
			effect = new MultiEffect(
				UAWFx.statusEffectSquare.wrap(color),
				Fx.hitLancer,
				Fx.smoke
			);
			buildSpeedMultiplier = speedMultiplier = 0.3f;
			reloadMultiplier = 0.2f;
			init(() -> {
				affinity(cryoBurn, (unit, result, time) -> {
					result.set(superConduct, 15 * tick);
					unit.damagePierce(transitionDamage * 0.5f);
				});
			});
		}};

		superConduct = new UAWStatusEffect("superconduct") {{
			dragMultiplier = 1.5f;
			healthMultiplier = 0.7f;
			color = Pal.surge.lerp(UAWPal.cryoMiddle, 0.5f);
			effect = new MultiEffect(
				UAWFx.statusEffectSquare.wrap(Pal.surge.lerp(UAWPal.cryoMiddle, 0.5f)),
				Fx.hitLancer
			);
		}};
		thermalShock = new UAWStatusEffect("thermalshock") {{
			healthMultiplier = 0.4f;
			speedMultiplier = 0.8f;
			reloadMultiplier = 0.4f;
			color = Pal.lightPyraFlame;
			effect = new MultiEffect(
				UAWFx.statusEffectSquare.wrap(Pal.lightPyraFlame),
				Fx.burning,
				Fx.smoke
			);
		}};
	}
}