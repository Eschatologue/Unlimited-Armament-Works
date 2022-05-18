package UAW.entities.bullet.ModdedVanillaBullet;

import arc.Core;
import arc.math.Mathf;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.Bullet;
import mindustry.graphics.Pal;

/**
 * BasicBulletType with automatic adjusting trails
 */
public class TrailBulletType extends BasicBulletType {
	/**
	 * How long is the generated trail based on height multiplied by this
	 * <p> 0 to disable | Disabled by default </p>
	 */
	public float trailLenghtScl = 1f;

	public TrailBulletType(float speed, float damage, String bulletSprite) {
		super(speed, damage);
		this.sprite = bulletSprite;
	}

	/**
	 * BasicBulletType that extends of UAWBulletType, adding damage multiplier such as armor penetration and shield
	 * damage multiplier
	 */
	public TrailBulletType(float speed, float damage) {
		this(speed, damage, "bullet");
	}

	public TrailBulletType() {
		this(0f, 0f, "bullet");
	}

	@Override
	public void load() {
		backRegion = Core.atlas.find(sprite + "-back");
		frontRegion = Core.atlas.find(sprite);
	}

	@Override
	public void init(Bullet b) {
		super.init(b);
		if (trailLenghtScl > 0) {
			trailRotation = true;
			trailWidth = width / 3.39f;
			trailLength = Mathf.round(height * trailLenghtScl);
			trailColor = backColor;
		}
	}
}
