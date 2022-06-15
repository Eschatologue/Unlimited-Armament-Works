package UAW.entities.units;

import UAW.entities.units.entity.*;
import UAW.audiovisual.Outliner;
import UAW.type.Rotor;
import UAW.type.Rotor.RotorMount;
import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.struct.Seq;
import arc.util.Time;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.gen.Unit;
import mindustry.graphics.*;
import mindustry.type.UnitType;
import mindustry.world.blocks.environment.Floor;

import static mindustry.Vars.world;

public class UAWUnitType extends UnitType {
	public final Seq<Rotor> rotors = new Seq<>();
//	public final Seq<TankWeapon> tWeapons = new Seq<>();

	// Helicopters
	public float spinningFallSpeed = 0;
	public float rotorDeathSlowdown = 0.01f;
	public float fallSmokeX = 0f, fallSmokeY = -5f, fallSmokeChance = 0.1f;

	// Tanks
	public TextureRegion hullRegion, hullOutlineRegion, hullCellRegion, turretCellRegion;
	public float turretX = 0f, turretY = 0f;
	public float groundTrailSize = 1;
	public float groundTrailInterval = 0.5f;
	public float groundTrailSpacing = 0f, groundTrailY = 0f;
	public float terrainSpeedMultiplier = 1f;
	public boolean drawHullCell = true;

	// Jets
	public float engineSizeShrink = 0.1f;
	public boolean jetMovement = true;

	protected float timer;

	public UAWUnitType(String name) {
		super(name);
	}

	@Override
	public void draw(Unit unit) {
		float z = unit.elevation > 0.5f ? (lowAltitude ? Layer.flyingUnitLow : Layer.flyingUnit) : groundLayer + Mathf.clamp(hitSize / 4000f, 0, 0.01f);
		super.draw(unit);
		if (unit instanceof TankUnitEntity tank) {
			Draw.z(z - 0.05f);
			drawTankHullOutline(tank);
			drawTankHull(tank);
		}
		Draw.z(z);
		drawRotor(unit);
	}

	@Override
	public void drawSoftShadow(Unit unit, float alpha) {
		float z = unit.elevation > 0.5f ? (lowAltitude ? Layer.flyingUnitLow : Layer.flyingUnit) : groundLayer + Mathf.clamp(hitSize / 4000f, 0, 0.01f);
		if (unit instanceof CopterUnitEntity) {
			Draw.z(z - 3f);
			super.drawSoftShadow(unit, alpha);
		} else if (unit instanceof TankUnitEntity) {
			float rad = 1.6f;
			float size = Math.max(hullRegion.width * 0.7f, hullRegion.height * 0.7f) * Draw.scl;
			Draw.z(z - 3f);
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
					Draw.rect(
						rotor.bladeOutlineRegion, rx, ry,
						rotor.bladeOutlineRegion.width * Draw.scl,
						rotor.bladeOutlineRegion.height * Draw.scl,
						angle
					);
					Draw.mixcol(Color.white, unit.hitTime);
					Draw.rect(rotor.bladeRegion, rx, ry,
						rotor.bladeRegion.width * Draw.scl,
						rotor.bladeRegion.height * Draw.scl,
						angle)
					;
					if (rotor.doubleRotor) {
						Draw.rect(
							rotor.bladeOutlineRegion, rx, ry,
							rotor.bladeOutlineRegion.width * Draw.scl * -Mathf.sign(false),
							rotor.bladeOutlineRegion.height * Draw.scl,
							-angle
						);
						Draw.mixcol(Color.white, unit.hitTime);
						Draw.rect(rotor.bladeRegion, rx, ry,
							rotor.bladeRegion.width * Draw.scl * -Mathf.sign(false),
							rotor.bladeRegion.height * Draw.scl,
							-angle
						);
					}
					if (rotor.drawRotorTop) {
						Draw.z(z + 0.55f);
						Draw.rect(
							rotor.topRegionOutline, rx, ry,
							rotor.topRegionOutline.width * Draw.scl,
							rotor.topRegionOutline.height * Draw.scl,
							unit.rotation - 90);
						Draw.mixcol(Color.white, unit.hitTime);
						Draw.rect(
							rotor.topRegion, rx, ry,
							rotor.topRegion.width * Draw.scl,
							rotor.topRegion.height * Draw.scl,
							unit.rotation - 90
						);
					}
				}
			}
		}
	}

	// Tank Hull Outline
	public void drawTankHullOutline(TankUnitEntity tank) {
		Draw.reset();
		applyColor(tank);
		applyOutlineColor(tank);
		Draw.rect(hullOutlineRegion, tank, tank.hullRotation - 90);
	}

	// Tank Hull
	public void drawTankHull(TankUnitEntity tank) {
		Draw.mixcol(Color.white, tank.hitTime);
		applyColor(tank);
		Draw.rect(hullRegion, tank, tank.hullRotation - 90);
		if (drawHullCell) {
			Draw.color(cellColor(tank));
			Draw.rect(hullCellRegion, tank, tank.hullRotation - 90);
		}
		Draw.mixcol();
		Draw.reset();
	}

	// Tank Turret, new method is unnecessary
	@Override
	public void drawBody(Unit unit) {
		if (unit instanceof TankUnitEntity tank) {
			float x = tank.x + Angles.trnsx(tank.hullRotation, turretX, turretY);
			float y = tank.y + Angles.trnsy(tank.hullRotation, turretX, turretY);
			applyColor(unit);
			Draw.rect(region, x, y, unit.rotation - 90);
			Draw.reset();
		} else super.drawBody(unit);
	}

	// Tank Trail
	public void drawTankTrail(Unit unit) {
		Floor floor = Vars.world.floorWorld(unit.x, unit.y);
		Color floorColor = floor.mapColor;
		if (unit instanceof TankUnitEntity tank) {
			if (((timer += Time.delta) >= groundTrailInterval)
				&& !floor.isLiquid && unit.moving() && groundTrailSize > 0) {
				for (int i : Mathf.zeroOne) {
					int side = Mathf.signs[i];
					float tankTrailOffset = groundTrailSpacing * side;
					Fx.unitLandSmall.at(
						unit.x + Angles.trnsx(tank.hullRotation - 90, tankTrailOffset, groundTrailY),
						unit.y + Angles.trnsy(tank.hullRotation - 90, tankTrailOffset, groundTrailY),
						(hitSize / 24) * groundTrailSize,
						floorColor
					);
				}
				timer = 0f;
			}
		}
	}


	@Override
	public void drawShadow(Unit unit) {
		if (unit instanceof TankUnitEntity tank) {
			float e = Math.max(unit.elevation, shadowElevation) * (1f - unit.drownTime);
			float x = unit.x + shadowTX * e, y = unit.y + shadowTY * e;
			Floor floor = world.floorWorld(x, y);
			float dest = floor.canShadow ? 1f : 0f;
			unit.shadowAlpha = unit.shadowAlpha < 0 ? dest : Mathf.approachDelta(unit.shadowAlpha, dest, 0.11f);
			Draw.color(Pal.shadow, Pal.shadow.a * unit.shadowAlpha);
			Draw.rect(hullRegion, unit.x + shadowTX * e, unit.y + shadowTY * e, tank.hullRotation - 90);
			Draw.color();
		} else super.drawShadow(unit);
	}

	@Override
	public void init() {
		Unit example = constructor.get();
		super.init();
		if (example instanceof TankUnitEntity) {
			groundLayer = Layer.groundUnit - 2;
			if (shadowElevation < 0f) {
				shadowElevation = 0.12f;
			}
		}
	}

	@Override
	public void update(Unit unit) {
		super.update(unit);
		drawTankTrail(unit);
	}

	@Override
	public void createIcons(MultiPacker packer) {
		super.createIcons(packer);
		// Helicopter Rotors
		for (Rotor rotor : rotors) {
			Outliner.outlineRegion(packer, rotor.bladeRegion, outlineColor, rotor.name + "-outline", outlineRadius);
			Outliner.outlineRegion(packer, rotor.topRegion, outlineColor, rotor.name + "-top-outline", outlineRadius);
		}
		// Tanks
//		Outliner.outlineRegion(packer, hullRegion, outlineColor, name + "-hull-outline", outlineRadius);
//		for (TankWeapon tankWeapon : tWeapons) {
//			Outliner.outlineRegion(packer, tankWeapon.gunOutline, outlineColor, tankWeapon.name + "-outline", outlineRadius);
//		}
	}

	@Override
	public void load() {
		super.load();
		rotors.each(Rotor::load);
		hullRegion = Core.atlas.find(name + "-hull");
		hullOutlineRegion = Core.atlas.find(name + "-hull-outline");
		turretCellRegion = Core.atlas.find(name + "-turret-cell");
		hullCellRegion = Core.atlas.find(name + "-hull-cell");
	}
}




