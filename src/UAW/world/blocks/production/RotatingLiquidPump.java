package UAW.world.blocks.production;

import arc.Core;
import arc.graphics.g2d.*;
import arc.math.Mathf;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.graphics.Drawf;
import mindustry.world.blocks.production.Pump;

public class RotatingLiquidPump extends Pump {
	public TextureRegion rotatorRegion, heat, topRegion;
	public Effect updateEffect = Fx.steam;
	public float rotateSpeed = -1.5f;
	public float updateEffectChance = 0.2f;

	public RotatingLiquidPump(String name) {
		super(name);
		placeableLiquid = true;
		hasPower = true;
		squareSprite = false;
	}

	@Override
	public TextureRegion[] icons() {
		return new TextureRegion[]{region, rotatorRegion, topRegion};
	}

	@Override
	public void load() {
		super.load();
//		heat = Core.atlas.find(name + "-heat");
		rotatorRegion = Core.atlas.find(name + "-rotator");
		topRegion = Core.atlas.find(name + "-top");
	}

	public class RotatingLiquidPumpBuild extends PumpBuild {
		public float warmup;
		public float pumpTime;

		@Override
		public void updateTile() {
			if (consValid() && liquidDrop != null) {
				float maxPump = Math.min(liquidCapacity - liquids.total(), amount * pumpAmount * edelta());
				liquids.add(liquidDrop, maxPump);
				warmup = Mathf.lerpDelta(warmup, 1f, 0.02f);
				if (Mathf.chance(delta() * updateEffectChance))
					updateEffect.at(x + Mathf.range(size * 2f), y + Mathf.range(size * 2f));
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
		}
	}
}
