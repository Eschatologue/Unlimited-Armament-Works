package UAW.entities.bullet;

import UAW.entities.bullet.ModdedVanillaBullet.UAWBasicBulletType;
import arc.graphics.Color;
import mindustry.content.Fx;
import mindustry.entities.effect.MultiEffect;

import static mindustry.Vars.tilesize;

public class BuckshotBulletType extends UAWBasicBulletType {

	public BuckshotBulletType(float speed, float damage) {
		super(speed, damage);
		drag = 0.02f;
		height = width = 15;
		hitSize = width / 2;
		shrinkX = shrinkY = 0.5f;
		despawnHit = true;
		splashDamageRadius = 1.6f * tilesize;
		splashDamage = damage / 1.8f;
		pierceCap = 2;
		knockback = 2;
		trailInterval = 4.5f;
		trailColor = Color.lightGray;
		shootEffect = new MultiEffect(Fx.shootBig2, Fx.shootPyraFlame);
		smokeEffect = Fx.shootBigSmoke2;
		hitEffect = Fx.hitBulletBig;
	}
}
