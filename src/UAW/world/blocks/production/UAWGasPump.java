package UAW.world.blocks.production;

import UAW.graphics.UAWFxS;
import arc.Core;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.Mathf;
import arc.util.Time;
import gas.world.blocks.production.GasPump;
import mindustry.entities.Effect;
import mindustry.graphics.*;

/**
 * Liquid Pump but with rotating rotor and liquid at different layer
 */
public class UAWGasPump extends GasPump {
	public TextureRegion rotatorRegion, heatRegion, topRegion;
	public Effect steamEffect = UAWFxS.steamSmoke;
	public Color heatColor = Color.valueOf("ff5512");

	public float steamSize = 5f;
	public float steamLifetime = 45f;
	public Color steamColor = Pal.lightishGray;
	public float steamEffectMult = 1f;
	public float rotateSpeed = -1.5f;

	public UAWGasPump(String name) {
		super(name);
		placeableLiquid = true;
		hasPower = false;
	}

	@Override
	public TextureRegion[] icons() {
		return new TextureRegion[]{region, rotatorRegion, topRegion};
	}

	@Override
	public void load() {
		super.load();
		heatRegion = Core.atlas.find(name + "-heat");
		rotatorRegion = Core.atlas.find(name + "-rotator");
		topRegion = Core.atlas.find(name + "-top");
	}

	public class UAWGasPumpBuild extends GasPumpBuild {
		public float warmup;
		public float pumpTime;

		@Override
		public void updateTile() {
			if (consValid() && liquidDrop != null) {
				float maxPump = Math.min(liquidCapacity - liquids.total(), amount * pumpAmount * edelta());
				liquids.add(liquidDrop, maxPump);
				warmup = Mathf.lerpDelta(warmup, 1f, 0.02f);
				if (Mathf.chance(warmup * steamEffectMult))
					if (steamEffect == UAWFxS.steamSmoke) {
						steamEffect.at(x + Mathf.range(size / 3f * 4f), y + Mathf.range(size / 3f * 4f), steamSize / 10, steamColor, steamLifetime);
					} else steamEffect.at(x + Mathf.range(size / 3f * 4f), y + Mathf.range(size / 3f * 4f));
			} else {
				warmup = Mathf.lerpDelta(warmup, 0f, 0.02f);
			}
			dumpLiquid(liquids.current());
			pumpTime += warmup * edelta();
		}

		@Override
		public void draw() {
			Draw.rect(name, x, y);
			Drawf.liquid(liquidRegion, x, y, liquids.currentAmount() / liquidCapacity, liquids.current().color);
			Drawf.spinSprite(rotatorRegion, x, y, rotateSpeed * pumpTime);
			Draw.rect(topRegion, x, y);

			if (heatRegion.found()) {
				Draw.color(heatColor);
				Draw.alpha(warmup * 0.6f * (1f - 0.3f + Mathf.absin(Time.time, 3f, 0.3f)));
				Draw.blend(Blending.additive);
				Draw.rect(heatRegion, x, y);
				Draw.blend();
				Draw.color();
			}
		}
	}
}
