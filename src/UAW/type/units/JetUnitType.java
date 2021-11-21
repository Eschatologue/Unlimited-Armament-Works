package UAW.type.units;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.math.Angles;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.UnitType;

public class JetUnitType extends UnitType {
	public float trailX = 5f;
	public float trailY = 0f;
	public int trailLength = 6;
	public float trailWidth = 4f;
	public Color trailColor = Pal.bulletYellowBack;
	public Trail trailLeft = new Trail(trailLength);
	public Trail trailRight = new Trail(trailLength);

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
		float cx = unit.x + Angles.trnsx(unit.rotation - 90, trailX, -trailY);
		float cy = unit.y + Angles.trnsx(unit.rotation - 90, -trailX, -trailY);
		trailLeft.update(cx, cy);
		trailRight.update(cx, cy);
	}

	@Override
	public void draw(Unit unit) {
		super.draw(unit);
		Draw.z(Layer.effect);
		trailLeft.draw(trailColor, trailWidth);
		trailRight.draw(trailColor, trailWidth);
	}
}
