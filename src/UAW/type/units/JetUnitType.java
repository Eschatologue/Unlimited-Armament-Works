package UAW.type.units;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.math.geom.Vec2;
import arc.struct.Seq;
import arc.util.Tmp;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.UnitType;

public class JetUnitType extends UnitType {
	public static float trailX = 5f;
	public static float trailY = 0f;
	public int trailLength = 6;
	public float trailWidth = 4f;
	public Color trailColor = Pal.bulletYellowBack;

	public static Seq<Vec2> trailPos = Seq.with(new Vec2(trailX, -trailY), new Vec2(-trailX, -trailY));
	public final Seq<Trail> trailSeq = Seq.with(new Trail(trailLength), new Trail(trailLength));

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
		// Code from Sh1penfire Stingray
		Tmp.v1.set(trailPos.get(0)).rotate(unit.rotation - 90);
		trailSeq.get(0).update(unit.x, unit.y + Tmp.v1.y);
		Tmp.v1.set(trailPos.get(1)).rotate(unit.rotation - 90);
		trailSeq.get(1).update(unit.x + Tmp.v1.x, unit.y + Tmp.v1.y);
	}

	@Override
	public void draw(Unit unit) {
		super.draw(unit);
		Draw.z(Layer.effect);
		trailSeq.each(t -> t.draw(trailColor, trailWidth));
	}
}
