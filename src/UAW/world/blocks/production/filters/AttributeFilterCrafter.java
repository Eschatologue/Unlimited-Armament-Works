package UAW.world.blocks.production.filters;

import arc.Core;
import mindustry.game.Team;
import mindustry.graphics.Pal;
import mindustry.ui.Bar;
import mindustry.world.Tile;
import mindustry.world.meta.*;

/** Attribute Crafter but with FilterItem */
public class AttributeFilterCrafter extends FilterGenericCrafter {
	public Attribute attribute = Attribute.heat;
	public float baseEfficiency = 1f;
	public float boostScale = 1f;
	public float maxBoost = 1f;
	public float minEfficiency = -1f;
	public float displayEfficiencyScale = 1f;
	public boolean displayEfficiency = true;

	public AttributeFilterCrafter(String name) {
		super(name);
	}

	@Override
	public void setBars() {
		super.setBars();

		if (!displayEfficiency) return;

		addBar("efficiency", (AttributeFilterCrafterBuild entity) ->
			new Bar(
				() -> Core.bundle.format("bar.uaw-tile-efficiency", (int) (entity.attributeEfficiencyMultiplier() * 100 * displayEfficiencyScale)),
				() -> Pal.lightOrange,
				entity::attributeEfficiencyMultiplier));
	}

	@Override
	public void drawPlace(int x, int y, int rotation, boolean valid) {
		super.drawPlace(x, y, rotation, valid);

		if (!displayEfficiency) return;

		drawPlaceText(Core.bundle.format("bar.efficiency",
			(int) ((baseEfficiency + Math.min(maxBoost, boostScale * sumAttribute(attribute, x, y))) * 100f)), x, y, valid);
	}

	@Override
	public boolean canPlaceOn(Tile tile, Team team, int rotation) {
		//make sure there's enough efficiency at this location
		return baseEfficiency + tile.getLinkedTilesAs(this, tempTiles).sumf(other -> other.floor().attributes.get(attribute)) >= minEfficiency;
	}

	@Override
	public void setStats() {
		super.setStats();

		stats.add(baseEfficiency <= 0.0001f ? Stat.tiles : Stat.affinities, attribute, floating, boostScale * size * size, !displayEfficiency);
	}

	public class AttributeFilterCrafterBuild extends FilterGenericCrafterBuild {
		public float attrsum;


		@Override
		public float getProgressIncrease(float base) {
			return super.getProgressIncrease(base) * attributeEfficiencyMultiplier();
		}

		public float attributeEfficiencyMultiplier() {
			return baseEfficiency + Math.min(maxBoost, boostScale * attrsum) + attribute.env();
		}

		@Override
		public void pickedUp() {
			attrsum = 0f;
			warmup = 0f;
		}

		@Override
		public void onProximityUpdate() {
			super.onProximityUpdate();

			attrsum = sumAttribute(attribute, tile.x, tile.y);
		}

	}

}
