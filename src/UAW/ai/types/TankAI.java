package UAW.ai.types;

import arc.math.*;
import mindustry.ai.Pathfinder;
import mindustry.entities.units.*;
import mindustry.gen.*;
import mindustry.world.Tile;
import mindustry.world.meta.BlockFlag;

import static mindustry.Vars.state;

public class TankAI extends AIController {

    @Override
    public void updateMovement() {
        Building core = unit.closestEnemyCore();

        if (target != null && unit.hasWeapons() && command() == UnitCommand.attack) {
            attack(unit.range() / 3);
        }

        if (target == null && command() == UnitCommand.attack && state.rules.waves && unit.team == state.rules.defaultTeam) {
            moveTo(getClosestSpawner(), state.rules.dropZoneRadius + 120f);
        }

        if (command() == UnitCommand.rally) {
            moveTo(targetFlag(unit.x, unit.y, BlockFlag.rally, false), 60f);
        }

        if ((core == null || !unit.within(core, unit.type.range * 0.5f)) && command() == UnitCommand.attack) {
            boolean move = true;

            if (state.rules.waves && unit.team == state.rules.defaultTeam) {
                Tile spawner = getClosestSpawner();
                if (spawner != null && unit.within(spawner, state.rules.dropZoneRadius + 120f)) move = false;
            }

            if (move) pathfind(Pathfinder.fieldCore);
        }
    }

    @Override
    public Teamc findTarget(float x, float y, float range, boolean air, boolean ground) {
        var result = findMainTarget(x, y, range, air, ground);

        //if the main target is in range, use it, otherwise target whatever is closest
        return checkTarget(result, x, y, range) ? target(x, y, range, air, ground) : result;
    }

    @Override
    public Teamc findMainTarget(float x, float y, float range, boolean air, boolean ground) {
        var core = targetFlag(x, y, BlockFlag.core, true);

        if (core != null && Mathf.within(x, y, core.getX(), core.getY(), range)) {
            return core;
        }

        for (var flag : unit.team.isAI() ? unit.type.targetFlags : unit.type.playerTargetFlags) {
            if (flag == null) {
                Teamc result = target(x, y, range, air, ground);
                if (result != null) return result;
            } else if (ground) {
                Teamc result = targetFlag(x, y, flag, true);
                if (result != null) return result;
            }
        }

        return core;
    }

    protected void attack(float circleLength) {
        vec.set(target).sub(unit);

        float ang = unit.angleTo(target);
        float diff = Angles.angleDist(ang, unit.rotation());

        if (diff > 70f && vec.len() < circleLength) {
            vec.setAngle(unit.vel().angle());
        } else {
            vec.setAngle(Angles.moveToward(unit.vel().angle(), vec.angle(), 6f));
        }

        vec.setLength(unit.speed());

        unit.moveAt(vec);
    }
}

