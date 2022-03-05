package UAW.world.blocks.gas;

import arc.Core;
import arc.util.Nullable;
import gas.world.blocks.production.GenericCrafterWithGas;
import mindustry.graphics.Pal;
import mindustry.ui.Bar;
import mindustry.world.meta.Attribute;

/** General use GasCrafter that can have attributes */
public class GasCrafter extends GenericCrafterWithGas {
	@Nullable
	public Attribute attribute;

	public float baseEfficiency = 1f;
	public float boostScale = 1f;
	public float maxBoost = 1f;

	public GasCrafter(String name) {
		super(name);
	}

	@Override
	public void drawPlace(int x, int y, int rotation, boolean valid) {
		if (attribute != null) {
			drawPlaceText(Core.bundle.format("bar.efficiency",
				(int) ((baseEfficiency + Math.min(maxBoost, boostScale * sumAttribute(attribute, x, y))) * 100f)), x, y, valid);
		}
	}

	@Override
	public void setBars() {
		super.setBars();
		if (attribute != null) {
			bars.add("efficiency", (GasCrafterBuild entity) ->
				new Bar(() ->
					Core.bundle.format("bar.efficiency", (int) (entity.efficiencyScale() * 100)),
					() -> Pal.lightOrange,
					entity::efficiencyScale));
		}
	}

	public class GasCrafterBuild extends GenericCrafterWithGasBuild {
		public float attrsum;

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

