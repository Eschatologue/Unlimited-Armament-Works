package UAW.world.blocks.power;

import UAW.graphics.UAWFxD;
import arc.Core;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.Mathf;
import arc.util.Time;
import mindustry.content.Fx;
import mindustry.entities.*;
import mindustry.entities.effect.MultiEffect;
import mindustry.graphics.Drawf;
import mindustry.world.blocks.power.ImpactReactor;

/**
 * Modified version of ImpactReactor
 * <p>
 * Power generation block which can use items, liquids or both as input sources for power production. Can have rotators,
 * using DrawRotator is not preferred due to on how weird it rotates.
 * </p>
 */
public class WarmUpGenerator extends ImpactReactor {
	public TextureRegion liquidRegion, rotatorRegion, heatRegion, topRegion;
	public Effect smokeEffect = new MultiEffect(Fx.burning, Fx.fireSmoke, Fx.fire);
	public Color heatColor = Color.valueOf("ff5512");
	public float rotationSpeed = 15f;


	public WarmUpGenerator(String name) {
		super(name);
		warmupSpeed = 0.005f;
		squareSprite = false;
		hasItems = false;
		baseExplosiveness = 16f;
		consumesPower = outputsPower = true;
		explosionRadius = (size * 5);
		explosionDamage = size * 125;
		explodeEffect = UAWFxD.dynamicExplosion(explosionRadius);
		consumes.power(3f);
	}

	@Override
	public void load() {
		bottomRegion = Core.atlas.find(name);
		liquidRegion = Core.atlas.find(name + "-liquid");
		rotatorRegion = Core.atlas.find(name + "-rotator");
		topRegion = Core.atlas.find(name + "-top");
		heatRegion = Core.atlas.find(name + "-heat");
	}

	@Override
	public TextureRegion[] icons() {
		if (rotatorRegion.found()) {
			return new TextureRegion[]{bottomRegion, rotatorRegion, topRegion};
		} else if (topRegion.found()) {
			return new TextureRegion[]{bottomRegion, topRegion};
		} else return new TextureRegion[]{bottomRegion};
	}

	public class WarmUpGeneratorBuild extends ImpactReactorBuild {
		float intensity;

		@Override
		public void updateTile() {
			super.updateTile();
			intensity += warmup * edelta();
			if (warmup >= 0.001) {
				if (Mathf.chance(warmup)) {
					smokeEffect.at(x + Mathf.range(size / 3.5f * 4f), y + Mathf.range(size / 3.5f * 4f));
				}
			}
		}

		@Override
		public void draw() {
			Draw.rect(bottomRegion, x, y);
			if (liquidRegion.found()) {
				Drawf.liquid(liquidRegion, x, y, liquids.total() / liquidCapacity, liquids.current().color);
			}
			if (rotatorRegion.found()) {
				Drawf.spinSprite(rotatorRegion, x, y, rotationSpeed * intensity);
			}
			if (topRegion.found()) {
				Draw.rect(topRegion, x, y);
			}

			if (heatRegion.found()) {
				Draw.color(heatColor);
				Draw.alpha(warmup * 0.6f * (1f - 0.3f + Mathf.absin(Time.time, 3f, 0.3f)));
				Draw.blend(Blending.additive);
				Draw.rect(heatRegion, x, y);
				Draw.blend();
				Draw.color();
			}

			Draw.reset();
		}

		@Override
		public void onDestroyed() {
			super.onDestroyed();
			Fires.create(tile);
		}
	}
}