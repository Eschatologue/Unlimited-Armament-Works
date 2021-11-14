package UAW.entities.bullet;

import arc.graphics.g2d.Draw;
import mindustry.content.Fx;
import mindustry.gen.*;

public class UAWArtilleryBulletType extends UAWBasicBulletType {

	public float size = 20f, trailMult = 1.5f, trailSize = 4f;

	public UAWArtilleryBulletType(float speed, float splashDamage, String bulletSprite) {
		super(speed, splashDamage, bulletSprite);
		this.splashDamage = splashDamage;
		this.height = size;
		this.width = size / 2;
		damage = 0f;
		collides = collidesTiles = collidesAir = false;
		pierceBuilding = scaleVelocity = true;
		hitShake = 5f;
		hitSound = Sounds.explosion;
		shootEffect = Fx.shootBig;
		trailEffect = Fx.artilleryTrail;

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

		if (b.timer(0, (3 + b.fslope() * 2f) * trailMult)) {
			trailEffect.at(b.x, b.y, b.fslope() * trailSize, backColor);
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
