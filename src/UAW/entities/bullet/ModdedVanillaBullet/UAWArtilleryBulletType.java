package UAW.entities.bullet.ModdedVanillaBullet;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import mindustry.content.Fx;
import mindustry.gen.*;

public class UAWArtilleryBulletType extends UAWBasicBulletType {

	/**
	 * The smaller the value, the smaller the interval
	 */
	public float trailMult = 1.5f;
	/**
	 * if true the Trail color will be the same as backColor and lerp to gray
	 */
	public boolean sameTrailColor = true;

	public UAWArtilleryBulletType(float speed, float splashDamage, String bulletSprite) {
		super(speed, splashDamage, bulletSprite);
		this.splashDamage = splashDamage;
		damage = splashDamage;
		height = 20;
		width = height / 2;
		collides = collidesTiles = collidesAir = false;
		pierceBuilding = scaleVelocity = true;
		hitShake = 5f;
		hitSound = Sounds.explosion;
		shootEffect = Fx.shootBig;
		trailEffect = Fx.artilleryTrail;
		trailColor = backColor;

		shrinkX = 0.15f;
		shrinkY = 0.63f;
	}

	public UAWArtilleryBulletType(float speed, float splashDamage) {
		this(speed, splashDamage, "shell");
	}

	public UAWArtilleryBulletType() {
		this(1f, 1f, "shell");
	}

	@Override
	public void update(Bullet b) {
		super.update(b);
		if (sameTrailColor) {
			trailColor = new Color(backColor).lerp(Color.gray, 0.5f);
		}
		if (b.timer(0, (3 + b.fslope() * 2f) * trailMult)) {
			trailEffect.at(b.x, b.y, b.fslope() * (width / 2.4f), trailColor);
		}
	}

	@Override
	public void draw(Bullet b) {
		drawTrail(b);
		float xscale = (1f - shrinkX + b.fslope() * (shrinkX)), yscale = (1f - shrinkY + b.fslope() * (shrinkY)), rot = b.rotation();

		Draw.color(backColor);
		Draw.rect(backRegion, b.x, b.y, width * xscale, height * yscale, rot - 90);
		Draw.color(frontColor);
		Draw.rect(frontRegion, b.x, b.y, width * xscale, height * yscale, rot - 90);
		Draw.color();
	}
}
