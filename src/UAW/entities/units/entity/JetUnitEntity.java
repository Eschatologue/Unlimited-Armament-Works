//package UAW.entities.units.entity;
//
//import UAW.content.UAWUnitTypes;
//import UAW.entities.units.UAWUnitType;
//import arc.graphics.Color;
//import arc.graphics.g2d.Draw;
//import arc.math.*;
//import mindustry.gen.UnitEntity;
//import mindustry.graphics.*;
//
///**
// * Flying unit with jet trail
// */
//public class JetUnitEntity extends UnitEntity {
//	private final transient Trail tleft = new Trail(1);
//	private final transient Trail tright = new Trail(1);
//	public float engineSizeScl = 1;
//
//	@Override
//	public String toString() {
//		return "JetUnit#" + id;
//	}
//
//	@Override
//	public int classId() {
//		return UAWUnitTypes.classID(getClass());
//	}
//
//	@Override
//	public void update() {
//		super.update();
//		UAWUnitType type = (UAWUnitType) this.type;
//		if (moving()) {
//			engineSizeScl = Mathf.lerpDelta(engineSizeScl, 0f, type.engineSizeShrink);
//		} else {
//			engineSizeScl = Mathf.lerpDelta(engineSizeScl, 1f, type.engineSizeShrink);
//		}
//		for (int i = 0; i < 2; i++) {
//			Trail trail = i == 0 ? tleft : tright;
//			trail.length = type.jetTrailLength;
//
//			int sign = i == 0 ? -1 : 1;
//			float cx = Angles.trnsx(rotation - 90, type.jetTrailX * sign, type.jetTrailY) + x;
//			float cy = Angles.trnsy(rotation - 90, type.jetTrailX * sign, type.jetTrailY) + y;
//			trail.update(cx, cy, type.jetTrailScl);
//		}
//		if (type.jetMovement) {
//			type.omniMovement = !isPlayer() && isShooting && isAI();
//		}
//	}
//
//	@Override
//	public void draw() {
//		super.draw();
//		Color trailColor = team.color;
//		float z = Draw.z();
//		Draw.z(Layer.effect);
//		tleft.draw(trailColor, type.trailScl);
//		tright.draw(trailColor, type.trailScl);
//		Draw.z(z);
//	}
//
//	public float engineSizeScl() {
//		return this.engineSizeScl;
//	}
//}
