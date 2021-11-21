package UAW.type.units;

import UAW.graphics.UAWFxDynamic;
import arc.graphics.Color;
import arc.math.*;
import mindustry.content.Fx;
import mindustry.gen.*;
import mindustry.graphics.Pal;
import mindustry.type.UnitType;

public class JetUnitType extends UnitType {
	public float trailX = 5f;
	public float trailY = 0f;
	public int trailLength = 15;
	public float trailWidth = 4f;
	public Color trailColor = Pal.bulletYellowBack;
	/*
	public Trail trailLeft = new Trail(trailLength);
	public Trail trailRight = new Trail(trailLength);
	*/

	public JetUnitType(String name) {
		super(name);
		engineSize = 0f;
		flying = true;
		omniMovement = false;
		lowAltitude = false;
		constructor = UnitEntity::create;
	}

	@Override
	public void update(Unit unit) {
		super.update(unit);
		float cx = unit.x + Angles.trnsx(unit.rotation - 90, -trailX, trailY);
		float cy = unit.y + Angles.trnsx(unit.rotation - 90, -trailX, trailY);
		float cx2 = unit.x + Angles.trnsx(unit.rotation - 90, trailX, -trailY);
		float cy2 = unit.y + Angles.trnsx(unit.rotation - 90, trailX, -trailY);
		if (unit.moving()) {
			if (Mathf.chanceDelta(1.5f)) {
				UAWFxDynamic.jetTrail(trailLength).at(cx, cy, trailWidth, trailColor);
				UAWFxDynamic.jetTrail(trailLength).at(cx2, cy2, trailWidth, trailColor);
			}
		}
		/*
		trailLeft.update(cx, cy);
		trailRight.update(cx2, cy2);*/
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
