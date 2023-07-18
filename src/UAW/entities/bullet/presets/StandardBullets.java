package UAW.entities.bullet.presets;

import UAW.audiovisual.UAWFx;
import UAW.audiovisual.effects.StatusHitEffect;
import UAW.entities.bullet.TrailBulletType;
import mindustry.content.*;
import mindustry.entities.effect.MultiEffect;
import mindustry.graphics.Pal;

public class StandardBullets extends TrailBulletType {
	public StandardBullets(float speed, float damage, String bulletSprite, int presets) {
		super(speed, damage, bulletSprite);
		buildingDamageMultiplier = 0.3f;

		switch (presets) {
			default -> {
			}
			case 0 ->{
				height = 10;
				width = 5.5f;

				shootEffect = Fx.shootSmall;
				smokeEffect = Fx.shootSmallSmoke;

				hitEffect = UAWFx.sparkHit(9, 3, backColor);
				despawnEffect = new MultiEffect(UAWFx.hitBulletSmall(backColor));

				trailChance = 0.4f;
				trailEffect = new StatusHitEffect() {{
					life = 13;
					color = backColor;
					sizeEnd = 0.5f;
					shapeVariant = 2;
				}};
				trailLengthScale = 0.25f;

			}
			case 1 -> {
				height = 12f;
				width = 8f;

				shootEffect = Fx.shootSmall;
				smokeEffect = Fx.shootSmallSmoke;

				hitEffect = UAWFx.sparkHit(9.5f, 4, backColor);
				despawnEffect = new MultiEffect(UAWFx.hitBulletSmall(backColor));

				trailChance = 0.4f;
				trailEffect = new StatusHitEffect() {{
					life = 13;
					color = backColor;
					sizeEnd = 0.65f;
					shapeVariant = 2;
				}};
				trailLengthScale = 0.35f;
			}
			case 2 -> {
				height = 16f;
				width = 9f;

				shootEffect = Fx.shootBigColor;
				smokeEffect = Fx.shootBigSmoke;

				hitEffect = new MultiEffect(
					UAWFx.sparkHit(12f, 5, backColor)
				);
				despawnEffect = new MultiEffect(Fx.hitBulletBig);

				trailChance = 0.4f;
				trailEffect = new StatusHitEffect() {{
					life = 15;
					color = backColor;
					sizeEnd = 0.7f;
					shapeVariant = 2;
				}};
				trailLengthScale = 0.8f;
			}
		}
	}

	public StandardBullets(float speed, float damage, int presets) {
		this(speed, damage, "bullet", presets);
	}

	public StandardBullets(float speed, float damage) {
		this(speed, damage, -1);
	}
}
