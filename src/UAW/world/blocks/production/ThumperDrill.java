package UAW.world.blocks.production;

import UAW.content.UAWItems;
import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.Time;
import mindustry.content.*;
import mindustry.entities.Effect;
import mindustry.game.Team;
import mindustry.type.Item;
import mindustry.world.*;
import mindustry.world.meta.Stat;

import static arc.math.Mathf.rand;
import static mindustry.Vars.*;

public class ThumperDrill extends UAWGasDrill {
	public Block oreOverlay = Blocks.oreCoal;
	public Item drilledItem = UAWItems.anthracite;
	public Effect gasEffect = Fx.fire;
	public float gasEffectChance = 0.02f;
	public float gasEffectRnd = -1f;

	public ThumperDrill(String name) {
		super(name);
		drawMineItem = false;
	}

	@Override
	public void setStats(){
		super.setStats();
		stats.remove(Stat.drillTier);
	}

	@Override
	public void init() {
		super.init();
		if (gasEffectRnd < 0) gasEffectRnd = size;
	}

	@Override
	public boolean canPlaceOn(Tile tile, Team team, int rotation) {
		if (isMultiblock()) {
			for (var other : tile.getLinkedTilesAs(this, tempTiles)) {
				if (canMine(other) && tile.overlay() == oreOverlay) {
					return true;
				}
			}
			return false;
		} else {
			return canMine(tile) && tile.overlay() == oreOverlay;
		}
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

	public class ThumperDrillBuild extends UAWGasDrillBuild {

		@Override
		public void drawParticles() {
			float base = (Time.time / particleLife);
			rand.setSeed(id);
			for (int i = 0; i < particles; i++) {
				float fin = (rand.random(1f) + base) % 1f, fout = 1f - fin;
				float angle = rand.random(360f);
				float len = particleRad * Interp.pow2Out.apply(fin);
				Draw.color(drilledItem.color);
				Fill.circle(x + Angles.trnsx(angle, len), y + Angles.trnsy(angle, len), particleLength * fout * warmup);
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
			super.updateTile();
			if (Mathf.chanceDelta(gasEffectChance * warmup))
				gasEffect.at(x + Mathf.range(gasEffectRnd), y + Mathf.range(gasEffectRnd));
		}
	}
}
