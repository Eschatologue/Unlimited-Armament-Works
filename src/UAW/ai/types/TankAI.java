package UAW.ai.types;

import arc.math.Mathf;
import mindustry.ai.Pathfinder;
import mindustry.entities.units.*;
import mindustry.gen.*;
import mindustry.world.Tile;
import mindustry.world.meta.BlockFlag;

import static mindustry.Vars.*;

public class TankAI extends AIController {

    @Override
    public void updateMovement() {

        Building core = unit.closestEnemyCore();

        if (core != null && unit.within(core, unit.range() / 1.3f + core.block.size * tilesize / 2f)) {
            target = core;
            for (var mount : unit.mounts) {
                if (mount.weapon.controllable && mount.weapon.bullet.collidesGround) {
                    mount.target = core;
                }
            }
        }

        if ((core == null || !unit.within(core, unit.type.range * 0.5f)) && command() == UnitCommand.attack) {
            boolean move = true;

            if (state.rules.waves && unit.team == state.rules.defaultTeam) {
                Tile spawner = getClosestSpawner();
                if (spawner != null && unit.within(spawner, state.rules.dropZoneRadius + 250f)) move = false;
            }

            if (move) pathfind(Pathfinder.fieldCore);
        }

        if (command() == UnitCommand.rally) {
            Teamc target = targetFlag(unit.x, unit.y, BlockFlag.rally, false);

            if (target != null && !unit.within(target, 70f)) {
                pathfind(Pathfinder.fieldRally);
            }
        }

        if (unit.type.canBoost && unit.elevation > 0.001f && !unit.onSolid()) {
            unit.elevation = Mathf.approachDelta(unit.elevation, 0f, unit.type.riseSpeed);
        }

        faceTarget();
    }
}