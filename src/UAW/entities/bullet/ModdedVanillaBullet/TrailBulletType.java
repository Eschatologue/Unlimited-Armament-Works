package UAW.entities.bullet.ModdedVanillaBullet;

import UAW.audiovisual.*;
import arc.Core;
import arc.math.Mathf;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.Bullet;

/**
 * BasicBulletType with automatic adjusting trails
 */
public class TrailBulletType extends BasicBulletType {
	/**
	 * How long is the generated trail based on height multiplied by this
	 * <p> 0 to disable | Disabled by default </p>
	 */
	public float trailLengthScale = 1f;

	public TrailBulletType(float speed, float damage, String bulletSprite) {
		super(speed, damage);
		this.sprite = bulletSprite;
	}

	/**
	 * BasicBulletType that extends of UAWBulletType, adding splashDamage multiplier such as armor penetration and shield
	 * splashDamage multiplier
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
		drawSize = Math.max(drawSize, trailLength * speed * 4.5f);
		if (trailLengthScale > 0) {
			trailRotation = true;
			trailWidth = width / 3.45f;
			trailLength = Mathf.round(height * trailLengthScale);
			trailColor = backColor;
		}
	}

	@Override
	public void removed(Bullet b) {
		if (trailLength > 0 && b.trail != null && b.trail.size() > 0) {
			UAWFx.trailFade.at(b.x, b.y, trailWidth, trailColor, b.trail.copy());
		}
	}
}
