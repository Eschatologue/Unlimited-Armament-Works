package UAW.entities.bullet;

import UAW.audiovisual.UAWFx;
import arc.util.*;
import mindustry.content.Fx;
import mindustry.entities.Units;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.MultiEffect;
import mindustry.gen.*;

import static UAW.Vars.modName;

public class CanisterBulletType extends ArtilleryBulletType {
	public float burstRange = 80f;
	public float burstDelay = 5f;

	public CanisterBulletType(BulletType bulletType, int projectileCount, float speed) {
		this.fragBullet = bulletType;
		this.fragBullets = projectileCount;
		this.speed = speed;
		trailMult = 0.3f;
		trailSize = 5f;
		height = 32;
		width = 16;
		sprite = modName + "canister";
		despawnHit = true;
		hitEffect = new MultiEffect(Fx.generatespark, Fx.steam, Fx.flakExplosionBig);
		smokeEffect = new MultiEffect(Fx.shootBigSmoke2, Fx.blastsmoke);
		shootEffect = UAWFx.shootSmoke(width, backColor, true);
		hitSound = Sounds.mineDeploy;
		trailColor = backColor;
		displayAmmoMultiplier = false;
	}

	public CanisterBulletType(BulletType bulletType, int projectileCount) {
		this(bulletType, projectileCount, 3f);
	}

	public CanisterBulletType(BulletType bulletType) {
		this(bulletType, 4, 3f);
	}

	@Override
	public void init(Bullet b) {
		super.init(b);
		this.damage = 0f;
		this.splashDamage = 0f;
	}

	@Override
	public void update(Bullet b) {
		super.update(b);
		if (b.fdata < 0f) return;

		if (b.timer(2, 6)) {
			Units.nearbyEnemies(b.team, Tmp.r1.setSize(burstRange * 2f).setCenter(b.x, b.y), unit -> {
				//fadata < 0 means it's primed to explode
				if (b.fdata < 0f || !unit.checkTarget(collidesAir, collidesGround)) return;

				if (unit.within(b, burstRange)) {
					//mark as primed
					b.fdata = -1f;
					Time.run(burstDelay, () -> {
						//explode
						if (b.fdata < 0) {
							b.time = b.lifetime;
						}
					});
				}
			});
		}
	}
}



