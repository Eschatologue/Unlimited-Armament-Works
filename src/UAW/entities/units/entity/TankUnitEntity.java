package UAW.entities.units.entity;

import UAW.content.UAWUnitTypes;
import UAW.entities.units.UAWUnitType;
import mindustry.Vars;
import mindustry.gen.MechUnit;
import mindustry.world.blocks.environment.Floor;

public class TankUnitEntity extends MechUnit {
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
		UAWUnitType type = (UAWUnitType) this.type;
		Floor floor = Vars.world.floorWorld(x, y);

		if (floor.isLiquid) {
			speedMultiplier = type.liquidSpeedMultiplier;
		}
		type.mechStride = type.mechFrontSway = type.mechSideSway = 0f;
		type.flying = false;
	}
}
