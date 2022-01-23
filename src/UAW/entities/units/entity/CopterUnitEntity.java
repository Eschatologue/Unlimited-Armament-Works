package UAW.entities.units.entity;

import UAW.content.UAWUnitTypes;
import UAW.entities.units.CopterUnitType;
import UAW.type.Rotor;
import UAW.type.Rotor.RotorMount;
import arc.math.*;
import arc.struct.Seq;
import arc.util.Time;
import mindustry.content.Fx;
import mindustry.gen.UnitEntity;

public class CopterUnitEntity extends UnitEntity {
	public final Seq<Rotor> rotor = new Seq<>();
	public RotorMount[] rotors;
	transient float rotorSpeedScl = 1f;

	@Override
	public String toString() {
		return "CopterUnit#" + id;
	}

	@Override
	public int classId() {
		return UAWUnitTypes.classID(getClass());
	}

	@Override
	public void update() {
		CopterUnitType type = (CopterUnitType) this.type;
		float rx = x + Angles.trnsx(rotation - 90, type.fallSmokeX, type.fallSmokeY);
		float ry = y + Angles.trnsy(rotation - 90, type.fallSmokeX, type.fallSmokeY);


		if (dead || health() <= 0) {
			rotation += Time.delta * (type.spinningFallSpeed * vel().len()) * Mathf.signs[id % 2];
			if (Mathf.chanceDelta(type.fallSmokeChance)) {
				Fx.fallSmoke.at(rx, ry);
				Fx.burning.at(rx, ry);
			}
			rotorSpeedScl = Mathf.lerpDelta(rotorSpeedScl, 0f, type.rotorDeathSlowdown);
		} else {
			rotorSpeedScl = Mathf.lerpDelta(rotorSpeedScl, 1f, type.rotorDeathSlowdown);
		}

		for (RotorMount rotor : rotors) {
			rotor.rotorRotation += rotor.rotor.rotorSpeed * rotorSpeedScl * Time.delta;
		}
	}
}
