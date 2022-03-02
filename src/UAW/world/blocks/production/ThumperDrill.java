package UAW.world.blocks.production;

import UAW.content.UAWItems;
import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import gas.world.blocks.production.GasDrill;
import mindustry.content.Blocks;
import mindustry.game.Team;
import mindustry.type.Item;
import mindustry.world.*;

import static mindustry.Vars.*;

public class ThumperDrill extends GasDrill {
	public Block oreOverlay = Blocks.oreCoal;
	public Item drilledItem = UAWItems.anthracite;

	public ThumperDrill(String name) {
		super(name);
	}

	@Override
	public boolean canPlaceOn(Tile tile, Team team, int rotation) {
		if (tile.overlay() == oreOverlay) {
			if (isMultiblock()) {
				for (var other : tile.getLinkedTilesAs(this, tempTiles)) {
					if (canMine(other)) {
						return true;
					}
				}
				return false;
			} else {
				return canMine(tile);
			}
		} else return false;
	}

	void countOre(Tile tile) {
		returnItem = null;
		returnCount = 0;
		oreCount.clear();
		itemArray.clear();
		for (var other : tile.getLinkedTilesAs(this, tempTiles)) {
			if (canMine(other)) {
				oreCount.increment(getDrop(other), 0, 1);
			}
		}
		for (var item : oreCount.keys()) {
			itemArray.add(item);
		}
		itemArray.sort((item1, item2) -> {
			int type = Boolean.compare(!item1.lowPriority, !item2.lowPriority);
			if (type != 0)
				return type;
			int amounts = Integer.compare(oreCount.get(item1, 0), oreCount.get(item2, 0));
			if (amounts != 0)
				return amounts;
			return Integer.compare(item1.id, item2.id);
		});
		if (itemArray.size == 0) {
			return;
		}
		returnItem = itemArray.peek();
		returnCount = oreCount.get(itemArray.peek(), 0);
	}

	@Override
	public void drawPlace(int x, int y, int rotation, boolean valid) {
		drawPotentialLinks(x, y);
		Tile tile = world.tile(x, y);
		if (tile == null)
			return;
		countOre(tile);
		if (returnItem != null && tile.overlay() == oreOverlay) {
			float width = drawPlaceText(Core.bundle.formatFloat("bar.drillspeed", 60f / (drillTime + hardnessDrillMultiplier * returnItem.hardness) * returnCount, 2), x, y, valid);
			float dx = x * tilesize + offset - width / 2f - 4f, dy = y * tilesize + offset + size * tilesize / 2f + 5, s = iconSmall / 4f;
			Draw.mixcol(Color.darkGray, 1f);
			Draw.rect(drilledItem.fullIcon, dx, dy - 1, s, s);
			Draw.reset();
			Draw.rect(drilledItem.fullIcon, dx, dy, s, s);
			if (drawMineItem) {
				Draw.color(drilledItem.color);
				Draw.rect(itemRegion, tile.worldx() + offset, tile.worldy() + offset);
				Draw.color();
			}
		} else {
			Tile to = tile.getLinkedTilesAs(this, tempTiles).find(t -> tile.overlay() != oreOverlay);
			Item item = to == null ? null : to.drop();
			if (item != null) {
				drawPlaceText(Core.bundle.get("bar.inoperative"), x, y, valid);
			}
		}
	}

	public class ThumperDrillBuild extends GasDrillBuild {

		@Override
		public void drawSelect() {
			if (dominantItem != null) {
				float dx = x - size * tilesize / 2f, dy = y + size * tilesize / 2f, s = iconSmall / 4f;
				Draw.mixcol(Color.darkGray, 1f);
				Draw.rect(drilledItem.fullIcon, dx, dy - 1, s, s);
				Draw.reset();
				Draw.rect(drilledItem.fullIcon, dx, dy, s, s);
			}
		}

		@Override
		public void updateTile() {
			if (dominantItem == null) {
				return;
			}
			if (timer(timerDump, dumpTime)) {
				dump(items.has(drilledItem) ? drilledItem : null);
			}
			timeDrilled += warmup * delta();
			if (items.total() < itemCapacity && dominantItems > 0 && consValid()) {
				float speed = 1f;
				if (cons.optionalValid()) {
					speed = liquidBoostIntensity;
				}
				// Drill slower when not at full power
				speed *= efficiency();
				lastDrillSpeed = (speed * dominantItems * warmup) / (drillTime + hardnessDrillMultiplier * dominantItem.hardness);
				warmup = Mathf.approachDelta(warmup, speed, warmupSpeed);
				progress += delta() * dominantItems * speed * warmup;
				if (Mathf.chanceDelta(updateEffectChance * warmup))
					updateEffect.at(x + Mathf.range(size * 2f), y + Mathf.range(size * 2f));
			} else {
				lastDrillSpeed = 0f;
				warmup = Mathf.approachDelta(warmup, 0f, warmupSpeed);
				return;
			}
			float delay = drillTime + hardnessDrillMultiplier * dominantItem.hardness;
			if (dominantItems > 0 && progress >= delay && items.total() < itemCapacity) {
				offload(drilledItem);
				progress %= delay;
				drillEffect.at(x + Mathf.range(drillEffectRnd), y + Mathf.range(drillEffectRnd), drilledItem.color);
			}
		}
	}
}
