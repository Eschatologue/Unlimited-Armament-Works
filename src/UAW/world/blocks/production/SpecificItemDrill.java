package UAW.world.blocks.production;

import UAW.content.UAWItems;
import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import mindustry.content.Blocks;
import mindustry.entities.Effect;
import mindustry.game.Team;
import mindustry.type.Item;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.blocks.production.BurstDrill;
import mindustry.world.meta.Stat;

import static mindustry.Vars.*;

public class SpecificItemDrill extends BurstDrill {
	/** This drill placing requirement */
	public Block tileRequirement = Blocks.oreCoal;
	/** The drilling result */
	public Item drilledItem = UAWItems.anthracite;

	/** Dont tamper with this */
	public boolean placeable;

	public SpecificItemDrill(String name) {
		super(name);
		drawMineItem = false;
	}

	@Override
	public void setStats() {
		super.setStats();
		stats.remove(Stat.drillTier);
	}

	@Override
	public boolean canPlaceOn(Tile tile, Team team, int rotation) {
		if (isMultiblock()) {
			for (var other : tile.getLinkedTilesAs(this, tempTiles)) {
				if (canMine(other) && tileRequirement instanceof OverlayFloor ? tile.overlay() == tileRequirement : tile.floor() == tileRequirement) {
					placeable = true;
					return true;
				}
			}
			placeable = false;
			return false;
		} else {
			return canMine(tile) && tileRequirement instanceof OverlayFloor ? tile.overlay() == tileRequirement : tile.floor() == tileRequirement;
		}
	}

	@Override
	protected void countOre(Tile tile) {
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

		if (returnItem != null && tileRequirement instanceof OverlayFloor ? tile.overlay() == tileRequirement : tile.floor() == tileRequirement) {
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
			Tile to = tile.getLinkedTilesAs(this, tempTiles).find(t -> tileRequirement instanceof OverlayFloor ? tile.overlay() != tileRequirement : tile.floor() != tileRequirement);
			Item item = to == null ? null : to.drop();
			if (item != null || !placeable) {
				drawPlaceText(Core.bundle.get("bar.inoperative"), x, y, valid);

			}
		}
	}

	public class ThumpDrillBuild extends BurstDrillBuild {
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

			if (invertTime > 0f) invertTime -= delta() / invertedTime;

			if (timer(timerDump, dumpTime)) {
				dump(items.has(drilledItem) ? drilledItem : null);
			}

			float drillTime = getDrillTime(drilledItem);

			smoothProgress = Mathf.lerpDelta(smoothProgress, progress / (drillTime - 20f), 0.1f);

			if (items.total() <= itemCapacity - dominantItems && dominantItems > 0 && efficiency > 0) {
				warmup = Mathf.approachDelta(warmup, progress / drillTime, 0.01f);

				float speed = efficiency;

				timeDrilled += speedCurve.apply(progress / drillTime) * speed;

				lastDrillSpeed = 1f / drillTime * speed * dominantItems;
				progress += delta() * speed;
			} else {
				warmup = Mathf.approachDelta(warmup, 0f, 0.01f);
				lastDrillSpeed = 0f;
				return;
			}

			if (dominantItems > 0 && progress >= drillTime && items.total() < itemCapacity) {
				for (int i = 0; i < dominantItems; i++) {
					offload(drilledItem);
				}

				invertTime = 1f;
				progress %= drillTime;

				if (wasVisible) {
					Effect.shake(shake, shake, this);
					drillSound.at(x, y, 1f + Mathf.range(drillSoundPitchRand), drillSoundVolume);
					drillEffect.at(x + Mathf.range(drillEffectRnd), y + Mathf.range(drillEffectRnd), drilledItem.color);
				}
			}
		}

	}
}
