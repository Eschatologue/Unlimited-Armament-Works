package UAW.ai.types;

import mindustry.ai.types.FlyingAI;
import mindustry.gen.Unit;

import static mindustry.Vars.state;

/** Modified Flying AI that will behave differently depending on the unit its attacking */
public class DynamicFlyingAI extends FlyingAI {
	@Override
	public void updateMovement() {
		unloadPayloads();
		if (target != null && unit.hasWeapons()) {
			if (!unit.type.circleTarget || target instanceof Unit unit && unit.isFlying()) {
				moveTo(target, unit.type.range * 0.8f);
				unit.lookAt(target);
			} else {
				attack(unit.type.range * 0.75f);
				unit.lookAt(unit.vel().angle());
			}
		}

		if (target == null && state.rules.waves && unit.team == state.rules.defaultTeam) {
			moveTo(getClosestSpawner(), state.rules.dropZoneRadius + 130f);
		}
	}
}

