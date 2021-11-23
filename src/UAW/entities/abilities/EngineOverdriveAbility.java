package UAW.entities.abilities;

import mindustry.entities.abilities.Ability;
import mindustry.gen.Unit;

public class EngineOverdriveAbility extends Ability {
	public float healthPercent = 0.4f;
	public float speedMult = 2f;

	EngineOverdriveAbility() {
	}

	public EngineOverdriveAbility(float healthPercent, float speedMultiplier) {
		this.healthPercent = healthPercent;
		this.speedMult = speedMultiplier;
	}

	@Override
	public void update(Unit unit) {
		if (unit.health < unit.maxHealth * healthPercent) {
			unit.speedMultiplier = speedMult;
		}
	}
}
