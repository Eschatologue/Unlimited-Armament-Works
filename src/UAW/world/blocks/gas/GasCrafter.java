package UAW.world.blocks.gas;

import UAW.graphics.UAWFxS;
import arc.graphics.Color;
import arc.math.Mathf;
import arc.util.Nullable;
import gas.world.blocks.production.GenericCrafterWithGas;
import mindustry.entities.Effect;
import mindustry.graphics.Pal;
import mindustry.world.meta.Attribute;

/** General use GasCrafter that can have attributes */
public class GasCrafter extends GenericCrafterWithGas {
	public Effect steamEffect = UAWFxS.steamSmoke;
	public Color steamColor = Pal.lightishGray;
	public float steamSize = 5f;
	public float steamEffectMult = 1f;

	@Nullable
	public Attribute attribute;

	public float baseEfficiency = 1f;
	public float boostScale = 1f;
	public float maxBoost = 1f;

	public GasCrafter(String name) {
		super(name);
	}

	public class GasCrafterBuild extends GenericCrafterWithGasBuild {
		public float attrsum;

		@Override
		public void updateTile() {
			super.updateTile();
			if (warmup >= 0.001) {
				if (Mathf.chance(warmup * steamEffectMult)) {
					steamEffect.at(x + Mathf.range(size / 3.5f * 4f), y + Mathf.range(size / 3.5f * 4f), steamSize / 10, steamColor);
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

