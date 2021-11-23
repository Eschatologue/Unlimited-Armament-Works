package UAW.type.units;

import UAW.ai.types.BomberJetAI;
import UAW.graphics.UAWFxDynamic;
import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.Time;
import mindustry.gen.*;
import mindustry.graphics.Pal;
import mindustry.type.UnitType;
import mindustry.world.meta.BlockFlag;

public class JetUnitType extends UnitType {
	public float trailX = 5f;
	public float trailY = 0f;
	public int trailLength = 15;
	public float trailWidth = 4f;
	public float normalRotateSpeed = 2.5f;
	public Color trailColor = Pal.bulletYellowBack;
	/*
	public Trail trailLeft = new Trail(trailLength);
	public Trail trailRight = new Trail(trailLength);
	*/

	public JetUnitType(String name) {
		super(name);
		this.engineSize = trailWidth;
		this.engineOffset = trailX;
		flying = true;
		lowAltitude = false;
		constructor = UnitEntity::create;
		circleTarget = true;
		defaultController = BomberJetAI::new;
		faceTarget = false;
		targetAir = false;
		playerTargetFlags = new BlockFlag[]{null};
	}

	@Override
	public void update(Unit unit) {
		super.update(unit);
		float cx = Angles.trnsx(unit.rotation - 90, trailX, trailY) + unit.x;
		float cy = Angles.trnsy(unit.rotation - 90, trailX, trailY) + unit.y;
		float cx2 = Angles.trnsx(unit.rotation - 90, -trailX, trailY) + unit.x;
		float cy2 = Angles.trnsy(unit.rotation - 90, -trailX, trailY) + unit.y;
		if (unit.moving()) {
			if (Mathf.chanceDelta(1.5f)) {
				UAWFxDynamic.jetTrail(trailLength).at(cx, cy, trailWidth, unit.team.color);
				UAWFxDynamic.jetTrail(trailLength).at(cx2, cy2, trailWidth, unit.team.color);
			}
		}
		if (!unit.moving()) {
			engineSize = Mathf.lerpDelta(0, trailWidth, trailWidth / 8);
		} else engineSize = Mathf.lerpDelta(trailWidth, 0, trailWidth / 4);
		omniMovement = !unit.isPlayer();
		/*
		trailLeft.update(cx, cy);
		trailRight.update(cx2, cy2);*/
	}

	@Override
	public void drawEngine(Unit unit) {
		if (!unit.isFlying()) return;

		float scale = unit.elevation;
		float offset = engineOffset / 2f + engineOffset / 2f * scale;

		Draw.color(unit.team.color);
		for (int i = 0; i < 2; i++) {
			float engineRot = unit.rotation + (i * 360f / 2);
			Fill.circle(
				unit.x + Angles.trnsx(engineRot, offset),
				unit.y + Angles.trnsy(engineRot, offset),
				(engineSize + Mathf.absin(Time.time, 2f, engineSize / 4f)) * scale
			);
		}
		Draw.color(Color.white);
		for (int i = 0; i < 2; i++) {
			float engineRot = unit.rotation + (i * 360f / 2);
			Fill.circle(
				unit.x + Angles.trnsx(engineRot, offset - 1),
				unit.y + Angles.trnsy(engineRot, offset - 1),
				(engineSize + Mathf.absin(Time.time, 2f, engineSize / 4f)) / 2f * scale
			);
		}
		Draw.color();
	}

/*
	@Override
	public void draw(Unit unit) {
		super.draw(unit);
		Draw.z(Layer.effect);
		trailLeft.draw(trailColor, trailWidth);
		trailRight.draw(trailColor, trailWidth);
	}*/
}
