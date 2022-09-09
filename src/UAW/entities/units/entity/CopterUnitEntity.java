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
	public float rotorSpeedScl = 1f;

	@Override
	public String toString() {
		return "CopterUnit#" + id;
	}

	@Override
	public int classId() {
		return UAWUnitTypes.classID(getClass());
	}

	/** @author GlennFolker#6881 */
	@Override
	public void setType(UnitType type) {
		super.setType(type);
		if (type instanceof HelicopterUnitType copter) {
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
		HelicopterUnitType type = (HelicopterUnitType) this.type;
		float rX = x + Angles.trnsx(rotation - 90, type.fallSmokeX, type.fallSmokeY);
		float rY = y + Angles.trnsy(rotation - 90, type.fallSmokeX, type.fallSmokeY);

		// Slows down rotor when dying
		if (dead || health() <= 0) {
			rotation += Time.delta * (type.spinningFallSpeed * vel().len()) * Mathf.signs[id % 2];
			if (Mathf.chanceDelta(type.fallSmokeChance)) {
				Fx.fallSmoke.at(rX, rY);
				Fx.burning.at(rX, rY);
			}
			rotorSpeedScl = Mathf.lerpDelta(rotorSpeedScl, 0f, type.rotorDeathSlowdown);
		} else {
			rotorSpeedScl = Mathf.lerpDelta(rotorSpeedScl, 1f, type.rotorDeathSlowdown);
		}

		for (RotorMount rotor : rotors) {
			rotor.rotorRotation += ((rotor.rotor.rotorSpeed * rotorSpeedScl) + rotor.rotor.minimumRotorSpeed) * Time.delta;
		}
		type.fallSpeed = 0.006f;
	}
}
