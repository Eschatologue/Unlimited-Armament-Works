package UAW.ai.types;

import mindustry.ai.types.FlyingAI;
import mindustry.entities.units.UnitCommand;
import mindustry.world.meta.BlockFlag;

import static mindustry.Vars.state;

public class CopterAI extends FlyingAI {
    @Override
    public void updateMovement() {
        if (target != null && unit.hasWeapons() && command() == UnitCommand.attack) {
            if (!unit.type.circleTarget) {
                unit.lookAt(target);
                moveTo(target, unit.type.range * 0.75f);
            } else {
                unit.lookAt(target);
                attack(unit.type.range * 0.8f);
            }
        }

        if (target == null && command() == UnitCommand.attack && state.rules.waves && unit.team == state.rules.defaultTeam) {
            moveTo(getClosestSpawner(), state.rules.dropZoneRadius + 120f);
        }

        if (command() == UnitCommand.rally) {
            moveTo(targetFlag(unit.x, unit.y, BlockFlag.rally, false), 60f);
        }
    }
}

