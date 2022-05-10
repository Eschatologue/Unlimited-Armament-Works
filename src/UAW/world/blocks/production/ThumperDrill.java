package UAW.world.blocks.production;

import UAW.content.UAWItems;
import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.Time;
import mindustry.content.Blocks;
import mindustry.game.Team;
import mindustry.type.Item;
import mindustry.world.*;
import mindustry.world.meta.Stat;

import static mindustry.Vars.*;

public class ThumperDrill extends UAWDrill {
	/** This drill placing requirement */
	public Block tileRequirement = Blocks.oreCoal;
	/** The drilling result */
	public Item drilledItem = UAWItems.anthracite;
	/** Whenever if the placing requirement is an overlay or a floor */
	public boolean isFloor = false;

	public ThumperDrill(String name) {
		super(name);
		drawMineItem = false;
	}

	@Override
	public void setStats() {
		super.setStats();
		stats.remove(Stat.drillTier);
	}

	@Override
	public void init() {
		super.init();
	}

	@Override
	public boolean canPlaceOn(Tile tile, Team team, int rotation) {
		if (isMultiblock()) {
			for (var other : tile.getLinkedTilesAs(this, tempTiles)) {
				if (canMine(other) && !isFloor ? tile.overlay() == tileRequirement : tile.floor() == tileRequirement) {
					return true;
				}
			}
			return false;
		} else {
			return canMine(tile) && !isFloor ? tile.overlay() == tileRequirement : tile.floor() == tileRequirement;
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
		if (returnItem != null && !isFloor ? tile.overlay() == tileRequirement : tile.floor() == tileRequirement) {
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
			Tile to = tile.getLinkedTilesAs(this, tempTiles).find(t -> !isFloor ? tile.overlay() != tileRequirement : tile.floor() != tileRequirement);
			Item item = to == null ? null : to.drop();
			if (item != null) {
				drawPlaceText(Core.bundle.get("bar.inoperative"), x, y, valid);
			}
		}
	}

	public class ThumperDrillBuild extends UAWDrillBuild {

		@Override
		public void drawDrillParticles() {
			float base = (Time.time / particleLife);
			rand.setSeed(this.id);
			for (int i = 0; i < particles; i++) {
				float fin = (rand.random(1f) + base) % 1f, fout = 1f - fin;
				float angle = rand.random(360f);
				float len = particleSpreadRadius * Interp.pow2Out.apply(fin);
				Draw.color(drilledItem.color);
				Fill.circle(x + Angles.trnsx(angle, len), y + Angles.trnsy(angle, len), particleSize * fout * warmup);
			}
			Draw.blend();
			Draw.reset();
		}

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
			if (items.total() < itemCapacity && dominantItems > 0 && efficiency > 0) {
				float speed = Mathf.lerp(1f, liquidBoostIntensity, optionalEfficiency) * efficiency;

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
