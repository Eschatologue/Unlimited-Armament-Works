package UAW.entities.bullet;

import UAW.audiovisual.*;
import UAW.audiovisual.UAWFx;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.entities.effect.MultiEffect;
import mindustry.gen.*;

/** Used by the Deadeye */
public class HighVelocityShellBulletType extends TrailBulletType {
	public Effect sideTrail = UAWFx.sideTrail(45f);
	public float sideTrailInterval = 0;

	public HighVelocityShellBulletType(float speed, float damage, String bulletSprite) {
		this.speed = speed;
		this.damage = damage;
		this.sprite = bulletSprite;
		hitSound = Sounds.artillery;
		pierceArmor = true;
		height = 16f;
		width = hitSize = height / 2;
		frontColor = UAWPal.graphiteFront;
		backColor = UAWPal.graphiteMiddle;
		trailEffect = new MultiEffect(
			Fx.disperseTrail,
			Fx.disperseTrail
		);
		trailChance = 0.8f;
		trailInterval = 0.2f;
		trailColor = hitColor = backColor;
		despawnHit = true;
	}

	public HighVelocityShellBulletType(float speed, float damage) {
		this(speed, damage, "bullet");
	}

	@Override
	public void update(Bullet b) {
		super.update(b);
		if (sideTrailInterval > 0f) {
			if (b.timer(1, sideTrailInterval)) {
				sideTrail.at(b.x, b.y, trailRotation ? b.rotation() : trailParam, trailColor);
			}
		}
	}
}
