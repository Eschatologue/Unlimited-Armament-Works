package UAW.entities.units;

import UAW.entities.units.entity.*;
import UAW.type.Rotor;
import UAW.type.Rotor.RotorMount;
import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.struct.Seq;
import mindustry.gen.*;
import mindustry.graphics.Layer;
import mindustry.type.UnitType;

public class UAWUnitType extends UnitType {
	// Helicopters
	public final Seq<Rotor> rotors = new Seq<>();
	public float spinningFallSpeed = 0;
	public float rotorDeathSlowdown = 0.01f;
	public float fallSmokeX = 0f, fallSmokeY = -5f, fallSmokeChance = 0.1f;

	// Tanks
	public TextureRegion turretRegion, turretOutlineRegion, hullRegion, hullOutlineRegion, hullCellRegion, turretCellRegion;
	public float tankLayer = Layer.groundUnit;
	public float turretX = 0f, turretY = 0f;
	public float groundTrailSize = 1;
	public float groundTrailInterval = 0.5f;
	public float groundTrailX = 0f, groundTrailY = 0f;
	public float liquidSpeedMultiplier = 1.2f;
	protected float timer;

	public UAWUnitType(String name) {
		super(name);
	}

	@Override
	public void draw(Unit unit) {
		super.draw(unit);
		drawRotor(unit);
	}

	@Override
	public void drawSoftShadow(Unit unit, float alpha) {
		if (unit instanceof CopterUnitEntity) {
			Draw.z(unit.elevation - 0.05f);
			super.drawSoftShadow(unit, alpha);
		} else if (unit instanceof TankUnitEntity) {
			Draw.z(tankLayer - 0.03f);
			super.drawSoftShadow(unit, alpha);
		} else {
			super.drawSoftShadow(unit, alpha);
		}
	}

	// Copter Rotors
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
							rotor.layer = Layer.flyingUnitLow + 0.1f;
						} else rotor.layer = Layer.flyingUnit + 0.1f;
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

	// Tank Hull
	@Override
	public void drawMech(Mechc mech) {
		Unit unit = (Unit) mech;
		if (mech instanceof TankUnitEntity tank) {
			Draw.z(tankLayer - 0.025f);
			Draw.rect(hullOutlineRegion, unit, tank.baseRotation - 90);
			Draw.z(tankLayer - 0.015f);
			Draw.mixcol(Color.white, unit.hitTime);
			Draw.rect(hullRegion, unit, tank.baseRotation - 90);
			Draw.color(cellColor(unit));
			Draw.rect(hullCellRegion, unit, tank.baseRotation - 90);
			Draw.reset();
		} else {
			super.drawMech(mech);
		}
	}

	// Tank Turret
	@Override
	public void drawBody(Unit unit) {
		if (unit instanceof TankUnitEntity tank) {
			float x = unit.x + Angles.trnsx(tank.baseRotation, turretX, turretY);
			float y = unit.y + Angles.trnsy(tank.baseRotation, turretX, turretY);
			applyColor(unit);

			Draw.z(tankLayer - 0.01f);
			Draw.rect(turretOutlineRegion, x, y, unit.rotation - 90);
			Draw.z(tankLayer + 0.02f);
			Draw.rect(turretRegion, x, y, unit.rotation - 90);

			Draw.reset();
		} else {
			super.drawBody(unit);
		}
	}

	@Override
	public void load() {
		super.load();
		rotors.each(Rotor::load);
		turretRegion = Core.atlas.find(name + "-turret");
		turretOutlineRegion = Core.atlas.find(name + "-turret-outline");
		hullRegion = Core.atlas.find(name + "-hull");
		hullOutlineRegion = Core.atlas.find(name + "-hull-outline");
		turretCellRegion = Core.atlas.find(name + "-turret-cell");
		hullCellRegion = Core.atlas.find(name + "-hull-cell");
	}
}




