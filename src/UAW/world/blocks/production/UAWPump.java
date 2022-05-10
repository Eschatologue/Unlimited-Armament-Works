package UAW.world.blocks.production;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.util.Nullable;
import mindustry.entities.Effect;
import mindustry.world.blocks.production.Pump;

/**
 * Liquid Pump but with rotating rotor and liquid at different layer
 */
public class UAWPump extends Pump {
	public TextureRegion rotatorRegion, heatRegion, topRegion;
	@Nullable
	public Effect updateEffect;
	public Color heatColor = Color.valueOf("ff5512");

	public float updateEffectChance = 1f;
	public float rotateSpeed = -1.5f;

	public UAWPump(String name) {
		super(name);
		placeableLiquid = true;
		squareSprite = false;
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

	public class UAWPumpBuild extends PumpBuild {
		public float warmup;
		public float pumpTime;

		@Override
		public void updateTile() {
			if (efficiency > 0 && liquidDrop != null) {
				float maxPump = Math.min(liquidCapacity - liquids.get(liquidDrop), amount * pumpAmount * edelta());
				liquids.add(liquidDrop, maxPump);
				if (Mathf.chance(efficiency * updateEffectChance) && updateEffect != null)
					updateEffect.at(x + Mathf.range(size / 3f * 4f), y + Mathf.range(size / 3f * 4f));
			}
			dumpLiquid(liquids.current());
			pumpTime += warmup * edelta();
		}
	}
}
