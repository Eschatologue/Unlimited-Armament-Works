package UAW.entities.units.entity;

import UAW.content.UAWUnitTypes;
import UAW.entities.units.TankUnitType;
import mindustry.Vars;
import mindustry.entities.EntityCollisions;
import mindustry.gen.TankUnit;
import mindustry.world.blocks.environment.Floor;

public class TankUnitEntity extends TankUnit {
	//	private final transient Trail tleft = new Trail(1);
//	private final transient Trail tright = new Trail(1);
	public float hullRotation;

	// Tank Unit Entity ID
	@Override
	public String toString() {
		return "TankUnit#" + id;
	}

	@Override
	public int classId() {
		return UAWUnitTypes.classID(getClass());
	}

	// Make tank collides with solid blocks
	@Override
	public EntityCollisions.SolidPred solidity() {
		return isFlying() ? null : EntityCollisions::solid;
	}

	@Override
	public void update() {
		super.update();
		TankUnitType type = (TankUnitType) this.type;
		Floor floor = Vars.world.floorWorld(x, y);
		// Tank speed multiplier in liquid terrain
		if (floor.isLiquid || floor.speedMultiplier < 1) {
			speedMultiplier = type.terrainSpeedMultiplier;
		}
		if (floor.dragMultiplier < 1) {
			dragMultiplier = type.terrainDragMultiplier;
		}
	}
}
