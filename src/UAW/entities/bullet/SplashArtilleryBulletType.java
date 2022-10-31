package UAW.entities.bullet;

import arc.graphics.Color;
import arc.util.Nullable;
import mindustry.entities.bullet.*;
import mindustry.gen.Bullet;

import static mindustry.Vars.tilesize;

public class SplashArtilleryBulletType extends ArtilleryBulletType {

	public @Nullable
	BulletType aftershock = null;

	public boolean autoAdjustTrail = true;

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
	}

	public void createAftershock(Bullet b, float x, float y) {
		if (aftershock != null && aftershock instanceof AftershockBulletType) {
			aftershock.create(b, x, y, 0f, 0);
		}
	}

	@Override
	public void init() {
		if (autoAdjustTrail) trailSize = width / 3.4f;
		super.init();

	}

	@Override
	public void update(Bullet b) {
		super.update(b);
		trailColor = new Color(backColor).lerp(Color.gray, 0.5f);
	}

	@Override
	public void hit(Bullet b) {
		super.hit(b);
		createAftershock(b, b.x, b.y);
	}


}
