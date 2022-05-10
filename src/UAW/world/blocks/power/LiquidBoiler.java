package UAW.world.blocks.power;

import UAW.content.UAWLiquids;
import UAW.world.blocks.production.AdvancedGenericCrafter;
import arc.Core;
import arc.math.Mathf;
import arc.util.Nullable;
import mindustry.content.Liquids;
import mindustry.game.Team;
import mindustry.graphics.Pal;
import mindustry.type.Liquid;
import mindustry.ui.Bar;
import mindustry.world.Tile;
import mindustry.world.consumers.ConsumeItemFilter;
import mindustry.world.meta.Stat;

import static UAW.Vars.tick;

/** Boils crafter to convert liquid to gas based on conversion ratio */
public class LiquidBoiler extends AdvancedGenericCrafter {

	/** The amount of liquid unit it consumes */
	public float liquidAmount = 30f;
	/** Liquid to gas conversion ratio */
	public float conversionMultiplier = 3f;
	/** Block inventory capacity multipler */
	public float capacityMultiplier = 1.5f;

	public @Nullable
	ConsumeItemFilter filterItem;

	public Liquid liquidInput = Liquids.water;
	public Liquid gasResult = UAWLiquids.steam;

	public LiquidBoiler(String name) {
		super(name);
		warmupSpeed = 0.01f;
		craftTime = 1.5f * tick;
		hasItems = true;
		hasLiquids = true;
		outputsLiquid = true;
	}

	@Override
	public void setStats() {
		super.setStats();
		stats.remove(Stat.productionTime);
	}

	@Override
	public boolean canPlaceOn(Tile tile, Team team, int rotate) {
		if (attribute == null) {
			return super.canPlaceOn(tile, team, rotate);
		} else
			return tile.getLinkedTilesAs(this, tempTiles).sumf(other -> other.floor().attributes.get(attribute)) > 0.01f;
	}

	@Override
	public void setBars() {
		super.setBars();
		addBar("heat", (LiquidBoilerBuild entity) ->
			new Bar(() ->
				Core.bundle.format("bar.heat", (int) (entity.steamBuildUp)),
				() -> Pal.lightOrange,
				entity::steamBuildUpProgress
			));
	}

	public class LiquidBoilerBuild extends AdvancedGenericCrafterBuild {
		public float steamBuildUp;

		public float steamBuildUpProgress() {
			return steamBuildUp * filterItem.efficiency(this);
		}

		@Override
		public void updateTile() {
			super.updateTile();
			if (efficiency > 0.1f) {
				steamBuildUp = Mathf.approachDelta(steamBuildUp, 1f, warmupSpeed * (attribute != null ? efficiencyScale() : 1));
			} else
				steamBuildUp = Mathf.approachDelta(steamBuildUp, 0f, warmupSpeed * (attribute != null ? efficiencyScale() : 1));
		}

		@Override
		public float getProgressIncrease(float base) {
			return super.getProgressIncrease(base) * steamBuildUpProgress();
		}

	}
}

