package UAW.entities.bullet;

import UAW.entities.bullet.ModdedVanillaBullet.*;
import UAW.graphics.UAWFxS;
import arc.util.*;
import mindustry.content.Fx;
import mindustry.entities.Units;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.effect.MultiEffect;
import mindustry.gen.*;

public class CanisterBulletType extends UAWArtilleryBulletType {
	public float trailMult = 0.3f, trailSize = 5f;

	public CanisterBulletType(BulletType bulletType, int projectileCount, float speed) {
		this.fragBullet = bulletType;
		this.fragBullets = projectileCount;
		this.speed = speed;
		size = 16;
		explodeRange = 80;
		explodeDelay = 5f;
		sprite = "uaw-canister";
		despawnHit = true;
		hitEffect = new MultiEffect(Fx.generatespark, Fx.steam, Fx.flakExplosionBig);
		smokeEffect = new MultiEffect(Fx.shootBigSmoke2, Fx.blastsmoke);
		shootEffect = UAWFxS.muzzleBreakShootSmoke;
		collidesAir = collidesTiles = false;
		collidesGround = true;
		shrinkX = 0.18f;
		shrinkY = 0.68f;
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
		damage = 0f;
		splashDamage = 0f;
//		if (!isMine){
//
//		}
	}

	@Override
	public void update(Bullet b) {
		super.update(b);
		if (b.timer(0, (3 + b.fslope() * 2f) * trailMult)) {
			trailEffect.at(b.x, b.y, b.fslope() * trailSize, backColor);
		}
	}
}



