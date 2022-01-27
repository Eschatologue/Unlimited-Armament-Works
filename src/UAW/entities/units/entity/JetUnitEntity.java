package UAW.entities.units.entity;

import UAW.content.UAWUnitTypes;
import UAW.entities.units.UAWUnitType;
import arc.math.Mathf;
import mindustry.gen.UnitEntity;

public class JetUnitEntity extends UnitEntity {
	public float engineSizeScl = 1;

	@Override
	public String toString() {
		return "JetUnit#" + id;
	}

	@Override
	public int classId() {
		return UAWUnitTypes.classID(getClass());
	}

	@Override
	public void update() {
		super.update();
		UAWUnitType type = (UAWUnitType) this.type;
		if (moving()) {
			engineSizeScl = Mathf.lerpDelta(engineSizeScl, 0f, type.engineSizeShrink);
		} else {
			engineSizeScl = Mathf.lerpDelta(engineSizeScl, 1f, type.engineSizeShrink);
		}
	}

	public float engineSizeScl() {
		return this.engineSizeScl;
	}
}
