package UAW.entities.bullet.ModdedVanillaBullet;

import arc.graphics.Color;
import mindustry.content.Fx;
import mindustry.entities.bullet.ArtilleryBulletType;
import mindustry.gen.Bullet;

import static mindustry.Vars.tilesize;

public class SplashArtilleryBulletType extends ArtilleryBulletType {

	public SplashArtilleryBulletType(float speed, float splashDamage, float splashDamageRadius, String bulletSprite) {
		super(speed, splashDamage, bulletSprite);
		this.splashDamage = splashDamage;
		this.damage = splashDamage;
		this.splashDamageRadius = splashDamageRadius;
	}

	public SplashArtilleryBulletType(float speed, float splashDamage, float splashDamageRadius) {
		this(speed, splashDamage, splashDamageRadius, "shell");
	}

	public SplashArtilleryBulletType(float speed, float splashDamage) {
		this(speed, splashDamage, 4 * tilesize, "shell");
	}

	public SplashArtilleryBulletType() {
		this(1f, 1f, 4 * tilesize, "shell");
	}

	@Override
	public void init() {
		super.init();
		this.damage = 0;
		trailSize = width / 3.4f;
	}

	@Override
	public void update(Bullet b) {
		super.update(b);
		trailColor = new Color(backColor).lerp(Color.gray, 0.5f);
	}
}
