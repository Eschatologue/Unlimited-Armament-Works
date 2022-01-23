package UAW.entities.units;

import UAW.content.UAWUnitTypes;
import mindustry.gen.UnitEntity;

public class CopterUnitEntity extends UnitEntity {
	@Override
	public String toString() {
		return "CopterUnit#" + id;
	}
	@Override
	public int classId() {
		return UAWUnitTypes.classID(getClass());
	}
}
