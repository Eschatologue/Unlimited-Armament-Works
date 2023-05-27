package UAW.entities.bullet;

import UAW.audiovisual.UAWFx;
import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.util.*;
import mindustry.Vars;
import mindustry.content.*;
import mindustry.entities.Effect;
import mindustry.entities.effect.MultiEffect;
import mindustry.game.EventType;
import mindustry.gen.*;
import mindustry.graphics.Layer;
import mindustry.world.blocks.environment.Floor;

import static mindustry.Vars.tilesize;

/**
 * Water based bullet that deals extra splashDamage based one enemy hitSize
 */
public class TorpedoBulletType extends TrailBulletType {
	static final EventType.UnitDamageEvent bulletDamageEvent = new EventType.UnitDamageEvent();

	public Color floorColor;
	/**
	 * Drag in non Deep liquid terrain
	 */
	public float deepDrag = -0.005f;

	public float torpRippleInterval = 10f;
	public float torpRippleLifetime = 60, torpRippleSize = 14;


	public TorpedoBulletType(float speed, float damage) {
		super(speed, damage);
		height = 7f;
		width = 5f;
		layer = Layer.scorch;
		homingDelay = 30f;
		homingPower = 0.035f;
		homingRange = 20 * tilesize;
		hitShake = 24;
		knockback = 8f;
		hitSize = 16f;
		collideTerrain = collideFloor = true;
		pierceArmor = true;
		keepVelocity = collidesAir = absorbable = hittable = reflectable = false;
		trailEffect = UAWFx.torpedoCruiseTrail;
		trailInterval = 0.4f;
		despawnHit = true;
		hitEffect = new MultiEffect(
			Fx.smokeCloud,
			Fx.blastExplosion,
			UAWFx.torpedoRippleHit
		);
		status = StatusEffects.slow;
		statusDuration = 3 * 60;
		hitSoundVolume = 2.5f;
		hitSoundPitch = 0.7f;
		hitSound = Sounds.explosionbig;
	}

	@Override
	public void init(Bullet b) {
		super.init(b);
		Floor floor = Vars.world.floorWorld(b.x, b.y);
		floorColor = floor.mapColor.cpy().mul(1.5f);
		hitColor = trailColor = floorColor;
	}

	@Override
	public void update(Bullet b) {
		Floor floor = Vars.world.floorWorld(b.x, b.y);
		rippleTrail(b);
		if (floor.isDeep()) {
			b.vel.scl(Math.max(1f - deepDrag * Time.delta, 0.01f));
		}
		super.update(b);
	}

	public void rippleTrail(Bullet b) {
		Floor floor = Vars.world.floorWorld(b.x, b.y);
		floorColor = floor.mapColor.cpy().mul(1.5f);

		if (torpRippleInterval > 0f) {
			if (b.timer(1, torpRippleInterval)) {

				// Ripple Effect
				new Effect(torpRippleLifetime, e -> {
					Draw.z(layer - 0.001f);
					Draw.color(Tmp.c1.set(floorColor));
					Lines.stroke(e.fout() * torpRippleSize / 10);
					Lines.circle(e.x, e.y, 3 + e.finpow() * torpRippleSize);
					Draw.reset();
				}).at(b.x, b.y);
			}
		}

	}

	@Override
	public void draw(Bullet b) {
		drawTrail(b);
	}
}