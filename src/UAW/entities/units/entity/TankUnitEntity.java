package UAW.entities.units.entity;

import UAW.content.UAWUnitTypes;
import UAW.entities.units.TankUnitType;
import mindustry.Vars;
import mindustry.entities.EntityCollisions;
import mindustry.gen.TankUnit;
import mindustry.world.blocks.environment.Floor;

public class TankUnitEntity extends TankUnit {
	// Tank Unit Entity ID
	@Override
	public String toString() {
		return "TankUnit#" + id;
	}

	@Override
	public int classId() {
		return UAWUnitTypes.classID(getClass());
	}

	@Override
	public void update() {
		super.update();
		TankUnitType type = (TankUnitType) this.type;
		Floor floor = Vars.world.floorWorld(x, y);
		// Tank speed multiplier in liquid terrain
		if (floor.isLiquid || floor.speedMultiplier != 1) {
			speedMultiplier = type.terrainSpeedMultiplier;
		}
		if (floor.dragMultiplier < 0.8 || floor.dragMultiplier > 1.3f) {
			dragMultiplier = type.terrainDragMultiplier;
		}
	}
}
