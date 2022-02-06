package UAW.entities.units.entity;

import UAW.content.UAWUnitTypes;
import UAW.entities.units.UAWUnitType;
import arc.math.*;
import arc.math.geom.Vec2;
import arc.util.*;
import mindustry.Vars;
import mindustry.entities.EntityCollisions;
import mindustry.gen.UnitEntity;
import mindustry.world.blocks.environment.Floor;

public class TankUnitEntity extends UnitEntity {
	//	private final transient Trail tleft = new Trail(1);
//	private final transient Trail tright = new Trail(1);
	public float hullRotation;

	@Override
	public String toString() {
		return "TankUnit#" + id;
	}

	@Override
	public int classId() {
		return UAWUnitTypes.classID(getClass());
	}

	@Override
	public EntityCollisions.SolidPred solidity() {
		return isFlying() ? null : EntityCollisions::solid;
	}

	@Override
	public void update() {
		super.update();
		UAWUnitType type = (UAWUnitType) this.type;
		Floor floor = Vars.world.floorWorld(x, y);
		if (floor.isLiquid) {
			speedMultiplier = type.liquidSpeedMultiplier;
		}
		type.flying = false;
		if (moving()) {
			float len = deltaLen();
			hullRotation = Angles.moveToward(hullRotation, deltaAngle(), type().baseRotateSpeed * Mathf.clamp(len / type().speed / Time.delta) * Time.delta);
		}
	}

	@Override
	public void rotateMove(Vec2 vec) {
		moveAt(Tmp.v2.trns(hullRotation, vec.len()));
		if (!vec.isZero()) {
			hullRotation = Angles.moveToward(hullRotation, vec.angle(), type.rotateSpeed * Math.max(Time.delta, 1));
		}
	}

	@Override
	public void lookAt(float angle) {
		this.rotation = Angles.moveToward(this.rotation, angle, this.type.rotateSpeed * Time.delta);
	}
}
