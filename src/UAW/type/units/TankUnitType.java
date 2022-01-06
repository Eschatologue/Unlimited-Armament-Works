package UAW.type.units;

import UAW.content.UAWStatusEffects;
import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.struct.ObjectSet;
import mindustry.Vars;
import mindustry.ai.types.GroundAI;
import mindustry.content.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.UnitType;
import mindustry.world.blocks.environment.Floor;
import mindustry.world.meta.BlockFlag;

/**
 * MechUnit but with modified drawMech method
 */
public class TankUnitType extends UnitType {
	public TextureRegion hullCellRegion;
	public float trailChance = 0.5f;
	public float trailOffsetX = 0f, trailOffsetY = 0f;
	public float liquidSpeedMultiplier = 1.2f;

	public TankUnitType(String name) {
		super(name);
		immunities = ObjectSet.with(StatusEffects.disarmed, UAWStatusEffects.EMP, StatusEffects.freezing);
		flying = false;
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
	}

	// Modifies drawMech
//	@Override
//	public void drawMech(Mechc mech) {
//		Unit unit = (Unit) mech;
//		Draw.z(Layer.groundUnit - 0.1f);
//		Draw.rect(baseRegion, unit.x, unit.y, unit.rotation - 90);
//		Draw.reset();
//	}

	@Override
	public void drawSoftShadow(Unit unit) {
		drawSoftShadow(unit, 0f);
	}

	@Override
	public void drawBody(Unit unit) {
		super.drawBody(unit);
		drawCell(unit);
	}

	@Override
	public void draw(Unit unit) {
		super.draw(unit);

		Draw.z(Layer.darkness);
		Drawf.shadow(region, unit.x, unit.y, unit.rotation - 90);
	}

	@Override
	public void drawMech(Mechc mech) {
		super.drawMech(mech);
		Unit unit = (Unit) mech;
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
		// Trail Effect
		if (Mathf.chanceDelta(trailChance) && !floor.isLiquid && unit.moving()) {
			Fx.unitLand.at(
				unit.x + Angles.trnsx(unit.rotation - 90, trailOffsetX, trailOffsetY),
				unit.y + Angles.trnsy(unit.rotation - 90, trailOffsetX, trailOffsetY),
				unit.hitSize / 6, floorColor);
		}
	}
}


