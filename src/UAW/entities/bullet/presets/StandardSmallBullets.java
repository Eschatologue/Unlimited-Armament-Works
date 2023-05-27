package UAW.entities.bullet.presets;

import UAW.audiovisual.UAWFx;
import UAW.audiovisual.effects.StatusHitEffect;
import UAW.entities.bullet.TrailBulletType;
import mindustry.content.Fx;
import mindustry.entities.effect.MultiEffect;

public class StandardSmallBullets extends TrailBulletType {
	public StandardSmallBullets(float speed, float damage, String bulletSprite) {
		super(speed, damage, "bullet");
		shootEffect = Fx.shootSmall;
		smokeEffect = Fx.shootSmallSmoke;
		hitEffect = UAWFx.sparkHit(9, 3, backColor);
		despawnEffect = new MultiEffect(
			UAWFx.hitBulletSmall(backColor)
		);
		trailChance = 0.4f;
		trailEffect = new StatusHitEffect() {{
			life = 13;
			color1 = backColor;
			sizeEnd = 0.5f;
			shapeVariant = 2;
		}};
		trailLengthScale = 0.25f;
	}

	public StandardSmallBullets(float speed, float damage) {
		this(speed, damage, "bullet");
	}
}
