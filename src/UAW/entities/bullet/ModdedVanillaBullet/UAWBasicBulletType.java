package UAW.entities.bullet.ModdedVanillaBullet;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.math.Mathf;
import arc.util.Tmp;
import mindustry.gen.Bullet;
import mindustry.graphics.Pal;

/**
 * Basically BasicBulletType that extends of UAWBulletType, adding damage multiplier such as armor penetration and
 * shield damage multiplier
 */
public class UAWBasicBulletType extends UAWBulletType {
	public Color backColor = Pal.bulletYellowBack, frontColor = Pal.bulletYellow;
	public Color mixColorFrom = new Color(1f, 1f, 1f, 0f), mixColorTo = new Color(1f, 1f, 1f, 0f);
	/** If > 0, the bullet height will use this value, and the width will be multiplied by the size ratio */
	public float size = 0f, sizeRatio = 0.5f;
	public float width = 5f, height = 7f;
	public float shrinkX = 0f, shrinkY = 0.5f;
	public float spin = 0;
	public String sprite;

	/**
	 * How long is the generated trail based on height multiplied by this
	 * <p> 0 to disable | Disabled by default </p>
	 */
	public float trailLenghtScl = 0f;

	public TextureRegion backRegion;
	public TextureRegion frontRegion;

	public UAWBasicBulletType(float speed, float damage, String bulletSprite) {
		super(speed, damage);
		this.sprite = bulletSprite;
	}

	/**
	 * BasicBulletType that extends of UAWBulletType, adding damage multiplier such as armor penetration and shield
	 * damage multiplier
	 */
	public UAWBasicBulletType(float speed, float damage) {
		this(speed, damage, "bullet");
	}

	public UAWBasicBulletType() {
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
		if (size > 0) {
			height = size;
			width = size * sizeRatio;
		}
		if (trailLenghtScl > 0) {
			trailRotation = true;
			trailWidth = size > 0 ? size : width / 3.45f;
			trailLength = Mathf.round(size > 0 ? size : height * trailLenghtScl);
			trailColor = backColor;
		}
	}

	@Override
	public void draw(Bullet b) {
		super.draw(b);
		float height = this.height * ((1f - shrinkY) + shrinkY * b.fout());
		float width = this.width * ((1f - shrinkX) + shrinkX * b.fout());
		float offset = -90 + (spin != 0 ? Mathf.randomSeed(b.id, 360f) + b.time * spin : 0f);

		Color mix = Tmp.c1.set(mixColorFrom).lerp(mixColorTo, b.fin());

		Draw.mixcol(mix, mix.a);

		Draw.color(backColor);
		Draw.rect(backRegion, b.x, b.y, width, height, b.rotation() + offset);
		Draw.color(frontColor);
		Draw.rect(frontRegion, b.x, b.y, width, height, b.rotation() + offset);

		Draw.reset();
	}
}
