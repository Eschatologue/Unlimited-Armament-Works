package UAW.entities.bullet;

import arc.math.*;
import arc.util.Nullable;
import mindustry.content.Fx;
import mindustry.entities.bullet.*;
import mindustry.gen.*;

import static mindustry.Vars.tilesize;

public class SplashArtilleryBulletType extends BasicBulletType {
	public float trailMult = 0f, trailSize = 4f;

	public float trailLengthScale = 1f;
	public float trailWidthScale = 0.368f;

	public @Nullable
	BulletType aftershock = null;

	public boolean generateTrail = true;

	public SplashArtilleryBulletType() {
		this(1f, 1f);
	}

	public SplashArtilleryBulletType(float speed, float splashDamage) {
		this(speed, splashDamage, 4 * tilesize);
	}

	public SplashArtilleryBulletType(float speed, float splashDamage, float splashDamageRadius) {
		this(speed, splashDamage, splashDamageRadius, "shell");
	}

	public SplashArtilleryBulletType(float speed, float splashDamage, float splashDamageRadius, String bulletSprite) {
		this.splashDamage = splashDamage;
		this.damage = splashDamage;
		this.splashDamageRadius = splashDamageRadius;
		this.sprite = bulletSprite;
		this.speed = speed;

		collidesTiles = false;
		collides = false;
		collidesAir = false;
		scaleLife = true;
		hitShake = 1f;
		hitSound = Sounds.explosion;
		hitEffect = Fx.flakExplosion;
		shootEffect = Fx.shootBig;
		trailEffect = Fx.artilleryTrail;

		//default settings:
		shrinkX = 0.15f;
		shrinkY = 0.63f;
		shrinkInterp = Interp.slope;
	}

	public void createAftershock(Bullet b, float x, float y) {
		if (aftershock != null && aftershock instanceof AftershockBulletType) {
			aftershock.create(b, x, y, 0f, 0);
		}
	}

	public void generateTrail() {
		trailInterp = Interp.slope;
		trailWidth = width * trailWidthScale;
		trailLength = Mathf.round(height * trailLengthScale);
		trailColor = backColor;
	}

	@Override
	public void init() {
	super.init();
	if (generateTrail) generateTrail();
	}


	@Override
	public void update(Bullet b) {
		super.update(b);
		if (b.timer(2, (3 + b.fslope() * 2f) * trailMult) && trailMult > 0) {
			trailEffect.at(b.x, b.y, b.fslope() * trailSize, backColor);
		}
	}

	@Override
	public void hit(Bullet b) {
		super.hit(b);
		createAftershock(b, b.x, b.y);
	}

}
