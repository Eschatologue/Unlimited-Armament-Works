package UAW.entities.bullet;

import UAW.audiovisual.UAWFx;
import UAW.entities.bullet.ModdedVanillaBullet.TrailBulletType;
import arc.Core;
import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import mindustry.gen.*;
import mindustry.graphics.*;

import static UAW.Vars.cruiseMissile_Basic;
import static mindustry.Vars.tilesize;

@Deprecated
public class CruiseMissileBulletType extends TrailBulletType {
	public float sizeScl = 1.4f;
	public float trailLifetime = 33f;

	public CruiseMissileBulletType(float speed, float damage, String sprite) {
		super(speed, damage, sprite);
		this.speed = speed;
		this.damage = damage;
		this.splashDamage = damage * 0.8f;
		height = sizeScl;
		width = sizeScl / 2.4f;
		layer = Layer.effect + 1;
		drag = -0.0125f;
		homingRange = 30 * tilesize;
		homingPower = 0.035f;
		splashDamageRadius = 12 * tilesize;
		hitShake = 12f;
		hitSize = 1.5f * 8;
		hitSoundVolume = 2f;
		hitSound = Sounds.explosionbig;
		backColor = Pal.missileYellowBack;
		frontColor = Pal.missileYellow;
		trailInterval = 0.5f;
		trailRotation = true;
		despawnHit = true;
		keepVelocity = false;
	}

	public CruiseMissileBulletType(float speed, float damage) {
		this(speed, damage, cruiseMissile_Basic);
	}

	public CruiseMissileBulletType() {
		this(1.8f, 225);
	}

	@Override
	public void load() {
		super.load();
		backRegion = Core.atlas.find(sprite + "-outline");
	}

	@Override
	public void init() {
		super.init();
		trailEffect = UAWFx.cruiseMissileTrail(trailLifetime, layer > Layer.effect ? Layer.effect : layer - 0.01f, trailColor);
		shrinkX = shrinkY = 0;
	}

	@Override
	public void updateTrailEffects(Bullet b) {
		if (trailChance > 0) {
			if (Mathf.chanceDelta(trailChance)) {
				trailEffect.at(b.x, b.y, trailRotation ? b.rotation() : trailParam, trailColor);
			}
		}

		if (trailInterval > 0f) {
			if (b.timer(0, trailInterval)) {
				trailEffect.at(b.x, b.y, trailRotation ? b.rotation() : trailParam, trailColor);
			}
		}
	}

	@Override
	public void draw(Bullet b) {
		float mScl = sizeScl * Draw.scl;
		drawTrail(b);
		Draw.rect(frontRegion, b.x, b.y, frontRegion.width * mScl, frontRegion.height * mScl, b.rotation() - 90);
		Draw.rect(backRegion, b.x, b.y, backRegion.width * mScl, backRegion.height * mScl, b.rotation() - 90);
		Draw.reset();
	}
}

