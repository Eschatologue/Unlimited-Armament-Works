package UAW.entities.bullet;

import UAW.graphics.UAWFxS;
import arc.util.*;
import mindustry.content.Fx;
import mindustry.entities.Units;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.MultiEffect;
import mindustry.gen.*;

public class MineCanisterBulletType extends BasicBulletType {
	public float burstRange = 80, burstDelay = 5f;
	public float size = 16;
	public float trailMult = 0.3f, trailSize = 5f;

	public MineCanisterBulletType(BulletType mineType, int mineCount, float speed) {
		this.fragBullet = mineType;
		this.fragBullets = mineCount;
		this.speed = speed;
		this.height = size * 1.8f;
		this.width = size;
		sprite = "uaw-canister";
		damage = 0f;
		despawnHit = true;
		hitEffect = new MultiEffect(Fx.generatespark, Fx.steam, Fx.flakExplosionBig);
		smokeEffect = new MultiEffect(Fx.shootBigSmoke2, Fx.blastsmoke);
		shootEffect = UAWFxS.muzzleBreakShootSmoke;
		scaleVelocity = true;
		collides = collidesAir = collidesTiles = false;
		shrinkX = 0.18f;
		shrinkY = 0.68f;
		hitSound = Sounds.mineDeploy;
		fragCone = 360f;
		fragAngle = 1;
		trailColor = backColor;
		displayAmmoMultiplier = false;
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



