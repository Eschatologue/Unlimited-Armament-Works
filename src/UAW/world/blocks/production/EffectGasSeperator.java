package UAW.world.blocks.production;

import arc.Core;
import arc.graphics.g2d.*;
import arc.math.Mathf;
import gas.world.blocks.production.GasSeparator;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.graphics.Drawf;

public class EffectGasSeperator extends GasSeparator {
	public TextureRegion topRegion;
	public Effect updateEffect = Fx.none;
	public float updateEffectChance = 0.04f;

	public EffectGasSeperator(String name) {
		super(name);
	}

	@Override
	public void load() {
		super.load();
		topRegion = Core.atlas.find(name + "-top");
	}

	@Override
	public TextureRegion[] icons() {
		if (spinnerRegion.found()) {
			return new TextureRegion[]{region, spinnerRegion, topRegion};
		} else if (topRegion.found()) {
			return new TextureRegion[]{region, topRegion};
		} else return new TextureRegion[]{topRegion};
	}

	public class EffectSeparatorBuild extends GasSeparatorBuild {
		@Override
		public void updateTile() {
			super.updateTile();
			if (consValid()) {
				if (Mathf.chanceDelta(updateEffectChance)) {
					updateEffect.at(x + Mathf.range(size * 4f), y + Mathf.range(size * 4));
				}
			}
		}

		@Override
		public void draw() {
			Draw.rect(region, x, y);
			Drawf.liquid(liquidRegion, x, y, liquids.total() / liquidCapacity, liquids.current().color);
			if (Core.atlas.isFound(spinnerRegion)) {
				Drawf.spinSprite(spinnerRegion, x, y, totalProgress * spinnerSpeed);
			}
			Draw.rect(topRegion, x, y);
		}

	}
}
