package UAW.type.units;

import UAW.content.UAWStatusEffects;
import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.struct.ObjectSet;
import arc.util.Time;
import mindustry.Vars;
import mindustry.ai.types.GroundAI;
import mindustry.content.*;
import mindustry.gen.*;
import mindustry.graphics.Layer;
import mindustry.type.UnitType;
import mindustry.world.blocks.environment.Floor;
import mindustry.world.meta.BlockFlag;

/**
 * MechUnit but with modified drawMech method
 */
public class TankUnitType extends UnitType {
	public TextureRegion hullOutlineRegion, turretOutlineRegion, hullCellRegion;
	public float unitLayer = Layer.groundUnit;
	public float turretX = 0f, turretY = 0f;
	public float trailSizeMultiplier = 1;
	public float groundTrailInterval = 0.5f;
	public float trailOffsetX = 0f, trailOffsetY = 0f;
	public float liquidSpeedMultiplier = 1.2f;

	protected float timer;

	public TankUnitType(String name) {
		super(name);
		immunities = ObjectSet.with(StatusEffects.disarmed, UAWStatusEffects.EMP, StatusEffects.freezing);
		flying = false;
		forceMultiTarget = true;
		drownTimeMultiplier = 0.6f;
		constructor = MechUnit::create;
		defaultController = GroundAI::new;
		targetFlags = new BlockFlag[]{BlockFlag.turret, BlockFlag.repair, BlockFlag.extinguisher, BlockFlag.core, null};
		mechStride = mechFrontSway = mechSideSway = 0f;
	}

	@Override
	public void load() {
		super.load();
		region = Core.atlas.find(name + "-turret");
		baseRegion = Core.atlas.find(name + "-hull");
		cellRegion = Core.atlas.find(name + "-turret-cell");
		hullCellRegion = Core.atlas.find(name + "-hull-cell");

		turretOutlineRegion = Core.atlas.find(name + "-turret-outline");
		hullOutlineRegion = Core.atlas.find(name + "-hull-outline");
	}

	@Override
	public void drawSoftShadow(Unit unit, float alpha) {
		Draw.z(unitLayer - 0.03f);
		super.drawSoftShadow(unit, alpha);
	}

	// For Turret, dont need to make new method
	@Override
	public void drawBody(Unit unit) {
		Mechc mech = unit instanceof Mechc ? (Mechc) unit : null;
		Unit mechUnit = (Unit) mech;
		float x = unit.x + Angles.trnsx(mech.baseRotation(), turretX, turretY);
		float y = unit.y + Angles.trnsy(mech.baseRotation(), turretX, turretY);
		applyColor(unit);

		Draw.z(unitLayer - 0.01f);
		Draw.rect(turretOutlineRegion, x, y, unit.rotation - 90);
		Draw.z(unitLayer + 0.02f);
		Draw.rect(region, x, y, unit.rotation - 90);

		Draw.reset();
	}

//	public void drawTurret(Mechc mech) {
//		Unit unit = (Unit) mech;
//		float x = unit.x + Angles.trnsx(mech.baseRotation() - 90, turretX, turretY);
//		float y = unit.y + Angles.trnsy(mech.baseRotation() - 90, turretX, turretY);
//		applyColor(unit);
//		applyOutlineColor(unit);
//
//		Draw.z(unitLayer - 0.01f);
//		Draw.rect(turretOutlineRegion, x, y, unit.rotation - 90);
//		Draw.z(unitLayer + 0.02f);
//		Draw.rect(region, x, y, unit.rotation - 90);
//
//		Draw.reset();
//	}

	// For Hull
	@Override
	public void drawMech(Mechc mech) {
		Unit unit = (Unit) mech;
		Draw.z(unitLayer - 0.025f);
		Draw.rect(hullOutlineRegion, unit, mech.baseRotation() - 90);
		Draw.z(unitLayer - 0.015f);
		Draw.mixcol(Color.white, unit.hitTime);
		Draw.rect(baseRegion, unit, mech.baseRotation() - 90);
		Draw.color(cellColor(unit));
		Draw.rect(hullCellRegion, unit, mech.baseRotation() - 90);
		Draw.reset();
	}

	@Override
	public void update(Unit unit) {
		Floor floor = Vars.world.floorWorld(unit.x, unit.y);
		Color floorColor = floor.mapColor;
		super.update(unit);
		// Increased Speed in Liquid
		if (floor.isLiquid) {
			unit.speedMultiplier = liquidSpeedMultiplier;
		}
		// Trail Effect TODO
		if (((timer += Time.delta) >= groundTrailInterval) && !floor.isLiquid && unit.moving() && trailSizeMultiplier > 0) {
			Fx.unitLand.at(
				unit.x + Angles.trnsx(unit.rotation - 90, trailOffsetX, trailOffsetY),
				unit.y + Angles.trnsy(unit.rotation - 90, trailOffsetX, trailOffsetY),
				(hitSize / 6) * trailSizeMultiplier, floorColor);
			timer = 0f;
		}
	}

	@Override
	public void draw(Unit unit) {
		super.draw(unit);
//		Mechc mech = unit instanceof Mechc ? (Mechc)unit : null;
//		if (mech != null){
//			drawTurret(mech);
//		}
	}
}


