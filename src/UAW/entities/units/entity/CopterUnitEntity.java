package UAW.entities.units.entity;

import UAW.content.UAWUnitTypes;
import UAW.entities.units.*;
import UAW.type.Rotor;
import UAW.type.Rotor.RotorMount;
import arc.math.*;
import arc.util.Time;
import mindustry.content.Fx;
import mindustry.gen.UnitEntity;
import mindustry.type.UnitType;

public class CopterUnitEntity extends UnitEntity {
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

	/** @author GlennFolker */
	@Override
	public void setType(UnitType type) {
		super.setType(type);
		if (type instanceof UAWUnitType copter) {
			rotors = new RotorMount[copter.rotors.size];
			for (int i = 0; i < rotors.length; i++) {
				Rotor rotorType = copter.rotors.get(i);
				rotors[i] = new RotorMount(rotorType);
			}
		}
	}

	@Override
	public void update() {
		super.update();
		UAWUnitType type = (UAWUnitType) this.type;
		float rotorX = x + Angles.trnsx(rotation - 90, type.fallSmokeX, type.fallSmokeY);
		float rotorY = y + Angles.trnsy(rotation - 90, type.fallSmokeX, type.fallSmokeY);

		if (dead || health() <= 0) {
			rotation += Time.delta * (type.spinningFallSpeed * vel().len()) * Mathf.signs[id % 2];
			if (Mathf.chanceDelta(type.fallSmokeChance)) {
				Fx.fallSmoke.at(rotorX, rotorY);
				Fx.burning.at(rotorX, rotorY);
			}
			rotorSpeedScl = Mathf.lerpDelta(rotorSpeedScl, 0f, type.rotorDeathSlowdown);
		} else {
			rotorSpeedScl = Mathf.lerpDelta(rotorSpeedScl, 1f, type.rotorDeathSlowdown);
		}

		for (RotorMount rotor : rotors) {
			rotor.rotorRotation += ((rotor.rotor.rotorSpeed * rotorSpeedScl) + rotor.rotor.minimumRotorSpeed) * Time.delta;
		}
		type.engineSize = 0f;
		type.fallSpeed = 0.006f;
		type.onTitleScreen = false;
	}
}
