package UAW.entities.bullet;

import arc.util.*;
import mindustry.content.Fx;
import mindustry.entities.Units;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.MultiEffect;
import mindustry.gen.*;

public class CanisterBulletType extends BasicBulletType {
	public float burstRange = 80;
	float burstDelay = 5f;

	float trailMult = 0.3f, trailSize = 5f;

	public CanisterBulletType(float speed, float size, int mineCount, BulletType mineType) {
		super(speed, size);
		damage = 0f;
		hitEffect = despawnEffect = new MultiEffect(
			Fx.generatespark,
			Fx.blockExplosionSmoke,
			Fx.dooropenlarge,
			Fx.flakExplosionBig
		);
		smokeEffect = new MultiEffect(
			Fx.shootBigSmoke2,
			Fx.blastsmoke
		);
		height = size;
		width = height / 2;
		scaleVelocity = true;
		collides = collidesAir = collidesTiles = false;
		shrinkX = 0.18f;
		shrinkY = 0.68f;
		hitSound = Sounds.shotgun;
		fragBullet = mineType;
		fragBullets = mineCount;
		fragCone = 360f;
		fragAngle = 1;
		trailColor = backColor;
	}

	@Override
	public void update(Bullet b) {
		super.update(b);
		if (b.timer(0, (3 + b.fslope() * 2f) * trailMult)) {
			trailEffect.at(b.x, b.y, b.fslope() * trailSize, backColor);
		}
		if (b.fdata < 0f) return;
		if (b.timer(2, 6)) {
			Units.nearbyEnemies(b.team, Tmp.r1.setSize(burstRange * 2f).setCenter(b.x, b.y), unit -> {
				if (b.fdata < 0f || !unit.checkTarget(collidesAir, collidesGround)) return;
				if (unit.within(b, burstRange)) {
					b.fdata = -1f;
					Time.run(burstDelay, () -> {
						if (b.fdata < 0) {
							b.time = b.lifetime;
						}
					});
				}
			});
		}
	}
}



