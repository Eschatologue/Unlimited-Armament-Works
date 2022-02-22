package UAW.world.blocks.gas;

import UAW.graphics.UAWFxS;
import arc.Core;
import arc.math.Mathf;
import arc.util.Nullable;
import gas.world.blocks.production.GenericCrafterWithGas;
import mindustry.entities.Effect;
import mindustry.graphics.Pal;
import mindustry.world.meta.*;

import static UAW.Vars.tick;

public class LiquidBoiler extends GenericCrafterWithGas {
	public Effect smokeEffect = UAWFxS.steamSmoke;
	@Nullable
	public Attribute attribute;

	public float steamSize = 4f;
	public float smokeEffectMult = 1f;
	public float baseEfficiency = 1f;
	public float boostScale = 1f;
	public float maxBoost = 1f;

	public LiquidBoiler(String name) {
		super(name);
		squareSprite = false;
		liquidCapacity = 60f;
		outputsLiquid = false;
		hasItems = true;
		hasLiquids = true;
		hasGasses = true;
		craftTime = 2f * tick;
	}

	@Override
	public void drawPlace(int x, int y, int rotation, boolean valid) {
		if (attribute != null) {
			drawPlaceText(Core.bundle.format("bar.efficiency",
				(int) ((baseEfficiency + Math.min(maxBoost, boostScale * sumAttribute(attribute, x, y))) * 100f)), x, y, valid);
		}
	}

	@Override
	public void setStats() {
		super.setStats();
		if (attribute != null) stats.add(Stat.affinities, attribute, boostScale * size * size);
	}

	public class LiquidBoilerBuild extends GenericCrafterWithGasBuild {
		public float attrsum;
		float intensity;

		@Override
		public void updateTile() {
			super.updateTile();
			intensity += warmup * edelta();
			if (warmup >= 0.001) {
				if (Mathf.chance(warmup * smokeEffectMult)) {
					smokeEffect.at(x + Mathf.range(size / 3.5f * 4f), y + Mathf.range(size / 3.5f * 4f), steamSize / 10, Pal.lightishGray);
				}
			}
		}

		@Override
		public float getProgressIncrease(float base) {
			if (attribute != null) {
				return super.getProgressIncrease(base) * efficiencyScale();
			} else return super.getProgressIncrease(base);
		}

		public float efficiencyScale() {
			return baseEfficiency + Math.min(maxBoost, boostScale * attrsum) + attribute.env();
		}

		@Override
		public void pickedUp() {
			attrsum = 0f;
		}

		@Override
		public void onProximityUpdate() {
			super.onProximityUpdate();

			attrsum = sumAttribute(attribute, tile.x, tile.y);
		}
	}
}
