package UAW.entities.units;

import UAW.ai.types.CopterAI;
import UAW.entities.units.entity.CopterUnitEntity;
import UAW.type.Rotor;
import UAW.type.Rotor.RotorMount;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.math.*;
import arc.struct.Seq;
import mindustry.gen.Unit;
import mindustry.graphics.Layer;
import mindustry.type.UnitType;

public class CopterUnitType extends UnitType {
	public final Seq<Rotor> rotors = new Seq<>();
	public float spinningFallSpeed = 0;
	public float rotorDeathSlowdown = 0.01f;
	public float fallSmokeX = 0f, fallSmokeY = -5f, fallSmokeChance = 0.1f;

	public CopterUnitType(String name) {
		super(name);
		flying = lowAltitude = true;
		constructor = CopterUnitEntity::new;
		engineSize = 0f;
		rotateSpeed = 6f;
		defaultController = CopterAI::new;
		fallSpeed = 0.006f;
		onTitleScreen = false;
	}

	@Override
	public void draw(Unit unit) {
		super.draw(unit);
		drawRotor(unit);
	}

	@Override
	public void drawSoftShadow(Unit unit, float alpha) {
		float layer;
		if (lowAltitude) {
			layer = Layer.flyingUnitLow;
		} else layer = Layer.flyingUnit;
		Draw.z(layer - 0.03f);
		super.drawSoftShadow(unit, alpha);
	}

	public void drawRotor(Unit unit) {
		applyColor(unit);
		if (unit instanceof CopterUnitEntity copter) {
			for (RotorMount mount : copter.rotors) {
				Rotor rotor = mount.rotor;
				float rx = unit.x + Angles.trnsx(unit.rotation - 90, rotor.x, rotor.y);
				float ry = unit.y + Angles.trnsy(unit.rotation - 90, rotor.x, rotor.y);

				for (int i = 0; i < rotor.bladeCount; i++) {
					if (rotor.layer < 0) {
						if (lowAltitude) {
							rotor.layer = Layer.flyingUnitLow + 0.01f;
						} else rotor.layer = Layer.flyingUnit + 0.01f;
					}
					float angle = (i * 360f / rotor.bladeCount + mount.rotorRotation) % 360;
					Draw.z(rotor.layer);
					Draw.rect(rotor.bladeOutlineRegion, rx, ry, rotor.bladeOutlineRegion.width * Draw.scl, rotor.bladeOutlineRegion.height * Draw.scl, angle);
					Draw.mixcol(Color.white, unit.hitTime);
					Draw.rect(rotor.bladeRegion, rx, ry, rotor.bladeRegion.width * Draw.scl, rotor.bladeRegion.height * Draw.scl, angle);
					if (rotor.doubleRotor) {
						Draw.rect(rotor.bladeOutlineRegion, rx, ry, rotor.bladeOutlineRegion.width * Draw.scl * -Mathf.sign(false), rotor.bladeOutlineRegion.height * Draw.scl, -angle);
						Draw.mixcol(Color.white, unit.hitTime);
						Draw.rect(rotor.bladeRegion, rx, ry, rotor.bladeRegion.width * Draw.scl * -Mathf.sign(false), rotor.bladeRegion.height * Draw.scl, -angle);
					}
					if (rotor.drawRotorTop) {
						Draw.z(rotor.layer + 0.001f);
						Draw.rect(rotor.topRegionOutline, rx, ry, rotor.topRegionOutline.width * Draw.scl, rotor.topRegionOutline.height * Draw.scl, unit.rotation - 90);
						Draw.mixcol(Color.white, unit.hitTime);
						Draw.rect(rotor.topRegion, rx, ry, rotor.topRegion.width * Draw.scl, rotor.topRegion.height * Draw.scl, unit.rotation - 90);
					}
				}
			}
		}
	}


	@Override
	public void load() {
		super.load();
		rotors.each(Rotor::load);
	}
}




