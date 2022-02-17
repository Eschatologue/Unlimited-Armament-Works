package UAW.entities.units.entity;

import UAW.content.UAWUnitTypes;
import UAW.entities.units.UAWUnitType;
import arc.Events;
import arc.math.*;
import arc.math.geom.*;
import arc.util.*;
import mindustry.Vars;
import mindustry.entities.EntityCollisions;
import mindustry.game.EventType;
import mindustry.gen.UnitEntity;
import mindustry.world.blocks.environment.Floor;

public class TankUnitEntity extends UnitEntity {
//	private final transient Trail tleft = new Trail(1);
//	private final transient Trail tright = new Trail(1);
	public float hullRotation;

	// Tank Unit Entity ID
	@Override
	public String toString() {
		return "TankUnit#" + id;
	}

	@Override
	public int classId() {
		return UAWUnitTypes.classID(getClass());
	}

	// Make tank collides with solid blocks
	@Override
	public EntityCollisions.SolidPred solidity() {
		return isFlying() ? null : EntityCollisions::solid;
	}

	@Override
	public void update() {
		super.update();
		UAWUnitType type = (UAWUnitType) this.type;
		Floor floor = Vars.world.floorWorld(x, y);
		// Tank speed multiplier in terrain
		if (floor.isLiquid || floor.speedMultiplier < 1) {
			speedMultiplier = type.terrainSpeedMultiplier;
		}
		type.flying = false;
		// Tank hull rotation
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
	public Floor drownFloor() {
		if (this.hitSize >= 12.0F && this.canDrown()) {
			Point2[] var1 = Geometry.d8;
			int var2 = var1.length;
			for (int var3 = 0; var3 < var2; ++var3) {
				Point2 p = var1[var3];
				Floor f = Vars.world.floorWorld(this.x + (float) (p.x * 8), this.y + (float) (p.y * 8));
				if (!f.isDeep()) {
					return null;
				}
			}
		}
		return this.canDrown() ? this.floorOn() : null;
	}

	@Override
	public void updateDrowning() {
		Floor floor = this.drownFloor();
		if (floor != null && floor.isLiquid && floor.drownTime > 0f) {
			this.lastDrownFloor = floor;
			this.drownTime += Time.delta / floor.drownTime / this.type.drownTimeMultiplier;
			if (Mathf.chanceDelta(0.05000000074505806d)) {
				floor.drownUpdateEffect.at(this.x, this.y, this.hitSize, floor.mapColor);
			}
			if (this.drownTime >= 0.999f && !Vars.net.client()) {
				this.kill();
				Events.fire(new EventType.UnitDrownEvent(this));
			}
		} else {
			this.drownTime -= Time.delta / 50f;
		}
		this.drownTime = Mathf.clamp(this.drownTime);
	}

	@Override
	public void lookAt(float angle) {
		this.rotation = Angles.moveToward(this.rotation, angle, this.type.rotateSpeed * Time.delta);
	}
}
