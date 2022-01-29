package UAW.entities.units;

import UAW.entities.units.entity.*;
import UAW.type.Rotor;
import UAW.type.Rotor.RotorMount;
import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.struct.Seq;
import arc.util.*;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.gen.Unit;
import mindustry.graphics.Layer;
import mindustry.type.UnitType;
import mindustry.world.blocks.environment.Floor;

public class UAWUnitType extends UnitType {
	public final Seq<Rotor> rotors = new Seq<>();
	// Helicopter
	public float spinningFallSpeed = 0;
	public float rotorDeathSlowdown = 0.01f;
	public float fallSmokeX = 0f, fallSmokeY = -5f, fallSmokeChance = 0.1f;

	// Tanks
	public TextureRegion turretRegion, turretOutlineRegion, hullRegion, hullOutlineRegion, hullCellRegion, turretCellRegion;
	public float tankLayer = Layer.groundUnit - 5;
	public float turretX = 0f, turretY = 0f;
	public float groundTrailSize = 1;
	public float groundTrailInterval = 0.5f;
	public float groundTrailX = 0f, groundTrailY = 0f;
	public float liquidSpeedMultiplier = 1.2f;

	// Jets
	public float engineSizeShrink = 0.1f;
	public float engineSpacing = 5f;
	public boolean jetMovement = true;

	protected float timer;

	public UAWUnitType(String name) {
		super(name);
	}

	@Override
	public void draw(Unit unit) {
		float z = unit.elevation > 0.5f ? (lowAltitude ? Layer.flyingUnitLow : Layer.flyingUnit) : groundLayer + Mathf.clamp(hitSize / 4000f, 0, 0.01f);
		super.draw(unit);
		drawRotor(unit);
		if (unit instanceof TankUnitEntity tank) {
			drawTankHullOutline(tank);
			drawTankHull(tank);
			drawTurret(tank);
		}
	}

	@Override
	public void drawSoftShadow(Unit unit, float alpha) {
		if (unit instanceof CopterUnitEntity) {
			Draw.z(unit.elevation - 0.05f);
			super.drawSoftShadow(unit, alpha);
		} else if (unit instanceof TankUnitEntity) {
			float rad = 1.6f;
			float size = Math.max(hullRegion.width * 1.2f, hullRegion.height * 1.2f) * Draw.scl;
			Draw.z(Layer.groundUnit - 1);
			Draw.color(0, 0, 0, 0.4f * alpha);
			Draw.rect(softShadowRegion, unit, size * rad * Draw.xscl, size * rad * Draw.yscl, unit.rotation - 90);
			Draw.color();
		} else {
			super.drawSoftShadow(unit, alpha);
		}
	}

	// Copter Rotors
	public void drawRotor(Unit unit) {
		float z = unit.elevation > 0.5f ? (lowAltitude ? Layer.flyingUnitLow : Layer.flyingUnit) : groundLayer + Mathf.clamp(hitSize / 4000f, 0, 0.01f);
		applyColor(unit);
		if (unit instanceof CopterUnitEntity copter) {
			for (RotorMount mount : copter.rotors) {
				Rotor rotor = mount.rotor;
				float rx = unit.x + Angles.trnsx(unit.rotation - 90, rotor.x, rotor.y);
				float ry = unit.y + Angles.trnsy(unit.rotation - 90, rotor.x, rotor.y);

				for (int i = 0; i < rotor.bladeCount; i++) {
					float angle = (i * 360f / rotor.bladeCount + mount.rotorRotation) % 360;
					Draw.z(z + 0.5f);
					Draw.rect(rotor.bladeOutlineRegion, rx, ry, rotor.bladeOutlineRegion.width * Draw.scl, rotor.bladeOutlineRegion.height * Draw.scl, angle);
					Draw.mixcol(Color.white, unit.hitTime);
					Draw.rect(rotor.bladeRegion, rx, ry, rotor.bladeRegion.width * Draw.scl, rotor.bladeRegion.height * Draw.scl, angle);
					if (rotor.doubleRotor) {
						Draw.rect(rotor.bladeOutlineRegion, rx, ry, rotor.bladeOutlineRegion.width * Draw.scl * -Mathf.sign(false), rotor.bladeOutlineRegion.height * Draw.scl, -angle);
						Draw.mixcol(Color.white, unit.hitTime);
						Draw.rect(rotor.bladeRegion, rx, ry, rotor.bladeRegion.width * Draw.scl * -Mathf.sign(false), rotor.bladeRegion.height * Draw.scl, -angle);
					}
					if (rotor.drawRotorTop) {
						Draw.z(z + 0.55f);
						Draw.rect(rotor.topRegionOutline, rx, ry, rotor.topRegionOutline.width * Draw.scl, rotor.topRegionOutline.height * Draw.scl, unit.rotation - 90);
						Draw.mixcol(Color.white, unit.hitTime);
						Draw.rect(rotor.topRegion, rx, ry, rotor.topRegion.width * Draw.scl, rotor.topRegion.height * Draw.scl, unit.rotation - 90);
					}
				}
			}
		}
	}

	// Tank Hull
	public void drawTankHull(TankUnitEntity tank) {
		Unit unit = (Unit) tank;
		Draw.reset();
		applyColor(unit);
		Draw.mixcol(Color.white, unit.hitTime);
		if (unit.lastDrownFloor != null) {
			Draw.color(Color.white, Tmp.c1.set(unit.lastDrownFloor.mapColor).mul(0.83f), unit.drownTime * 0.9f);
		} else {
			Draw.color(Color.white);
		}
		Draw.rect(hullRegion, unit, tank.baseRotation() - 90);
		Draw.color(unit.team.color);
		Draw.rect(hullCellRegion, unit, tank.baseRotation() - 90);
		Draw.mixcol();
	}

	public void drawTankHullOutline(TankUnitEntity tank) {
		Unit unit = (Unit) tank;
		applyColor(unit);
		applyOutlineColor(unit);
		Draw.rect(hullOutlineRegion, unit, tank.baseRotation() - 90);
	}

	public void drawTurret(TankUnitEntity tank) {
		Unit unit = (Unit) tank;
		float x = tank.x + Angles.trnsx(tank.baseRotation(), turretX, turretY);
		float y = tank.y + Angles.trnsy(tank.baseRotation(), turretX, turretY);
		applyColor(unit);
		applyOutlineColor(unit);
		Draw.rect(turretOutlineRegion, x, y, tank.rotation - 90);
		Draw.rect(turretRegion, x, y, tank.rotation - 90);
	}

	// Tank Trail
	public void drawTankTrail(Unit unit) {
		Floor floor = Vars.world.floorWorld(unit.x, unit.y);
		Color floorColor = floor.mapColor;
		if (unit instanceof TankUnitEntity) {
			if (((timer += Time.delta) >= groundTrailInterval)
				&& !floor.isLiquid && unit.moving() && groundTrailSize > 0) {
				Fx.unitLand.at(
					unit.x + Angles.trnsx(unit.rotation - 90, groundTrailX, groundTrailY),
					unit.y + Angles.trnsy(unit.rotation - 90, groundTrailX, groundTrailY),
					(hitSize / 6) * groundTrailSize,
					floorColor
				);
				Fx.unitLand.at(
					unit.x + Angles.trnsx(unit.rotation - 90, -groundTrailX, groundTrailY),
					unit.y + Angles.trnsy(unit.rotation - 90, -groundTrailX, groundTrailY),
					(hitSize / 6) * groundTrailSize,
					floorColor
				);
				timer = 0f;
			}
		}
	}

	// Jet Engine
	@Override
	public void drawEngine(Unit unit) {
		if (unit instanceof JetUnitEntity jetUnit) {
			if (!unit.isFlying()) return;
			float scale = unit.elevation;
			float offset = engineOffset / 2f + engineOffset / 2f * scale;
			for (int i : Mathf.zeroOne) {
				int side = Mathf.signs[i];
				float sideOffset = engineSpacing * side;
				Draw.color(unit.team.color);
				Fill.circle(
					unit.x + Angles.trnsx(unit.rotation + 90, sideOffset, offset),
					unit.y + Angles.trnsy(unit.rotation + 90, sideOffset, offset),
					((engineSize + Mathf.absin(Time.time, 2f, engineSize / 4f)) * scale) * jetUnit.engineSizeScl()
				);
				Draw.color(Color.white);
				Fill.circle(
					unit.x + Angles.trnsx(unit.rotation + 90, sideOffset, offset - 1f),
					unit.y + Angles.trnsy(unit.rotation + 90, sideOffset, offset - 1f),
					((engineSize + Mathf.absin(Time.time, 2f, engineSize / 4f)) / 2f * scale) * jetUnit.engineSizeScl()
				);
			}
		} else {
			super.drawEngine(unit);
		}
	}

	@Override
	public void update(Unit unit) {
		super.update(unit);
		drawTankTrail(unit);
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




