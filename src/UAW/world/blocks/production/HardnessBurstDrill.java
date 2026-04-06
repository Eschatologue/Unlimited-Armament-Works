package UAW.world.blocks.production;

import UAW.entities.Calc;
import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.scene.ui.layout.Scl;
import arc.util.*;
import arc.util.pooling.Pools;
import mindustry.entities.Effect;
import mindustry.graphics.Pal;
import mindustry.type.Item;
import mindustry.ui.*;
import mindustry.world.Tile;
import mindustry.world.blocks.production.BurstDrill;

import static UAW.Vars.tick;
import static mindustry.Vars.*;

/**
 * Like regular burst drill, but specialised torwards softer materials or materials below its h_threshold, anything
 * above that will get exponentially slower
 */
public class HardnessBurstDrill extends BurstDrill {
	/** Item hardness limit */
	public float h_threshold = 1;
	public float h_boostMult = 0.5f;
	public float h_penaltyMult = 1.5f;

	public String drillType = "bore";
	public boolean dominantItemBarColor = true;

	public HardnessBurstDrill(String name) {
		super(name);

		drawRim = true;
		hasLiquids = false;
		squareSprite = false;
		speedCurve = Interp.pow5In;

		arrows = 0;

		ambientSoundVolume = 0.18f * 0.5f;
		drillEffectChance = 0.5f;
		liquidBoostIntensity = 1;
		updateEffectChance = 0.025f;
		warmupSpeed = 0.002f;
	}

	public void drawText(String text, int x, int y, boolean valid) {
		if (renderer.pixelator.enabled()) return;

		Color color = valid ? Pal.accent : Pal.remove;
		Font font = Fonts.outline;
		GlyphLayout layout = Pools.obtain(GlyphLayout.class, GlyphLayout::new);
		boolean ints = font.usesIntegerPositions();
		font.setUseIntegerPositions(false);
		font.getData().setScale(1f / 4f / Scl.scl(1f));
		layout.setText(font, text);

		font.setColor(color);
		float dx = x * tilesize + offset, dy = y * tilesize + offset + size * tilesize / 2f + 3;
		font.draw(text, dx, dy + layout.height + 1, Align.center);

		font.setUseIntegerPositions(ints);
		font.setColor(Color.white);
		font.getData().setScale(1f);
		Draw.reset();
		Pools.free(layout);

	}

	@Override
	public void drawPlace(int x, int y, int rotation, boolean valid) {
		HardnessBurstDrillBuild hBuild = new HardnessBurstDrillBuild();

		drawPotentialLinks(x, y);
		drawOverlay(x * tilesize + offset, y * tilesize + offset, rotation);

		Tile tile = world.tile(x, y);
		if (tile == null) return;

		countOre(tile);

		if (returnItem != null) {
			float mult = hBuild.calculateDrillTimeMult(returnItem);
			float drillTime = getDrillTime(returnItem);
			float drillTimeM = drillTime / drillMultipliers.get(returnItem, 1);
			float formatValue = 60f / (drillTime * mult) * returnCount;
			float width = drawPlaceText(Core.bundle.formatFloat("bar.uaw-blastspeed", formatValue, 2), x, y, valid);

			float dx = x * tilesize + offset - width / 2f - 4f;
			float dy = y * tilesize + offset + size * tilesize / 2f + 5;
			float s = iconSmall / 4f;


			float drillBoostPercent = Calc.percentageChange(drillTime, drillTime * mult);

			switch (Float.compare(drillBoostPercent, 0)) {
				case 1 ->
					// drillBoostPercent > 0
					drawText(Core.bundle.formatFloat("bar.uaw-drillBoost.pos", drillBoostPercent, 1), x, y, valid);
				case 0 ->
					// drillBoostPercent == 0
					drawText(Core.bundle.formatFloat("bar.uaw-drillBoost.net", drillBoostPercent, 1), x, y, valid);
				case -1 ->
					// drillBoostPercent < 0
					drawText(Core.bundle.formatFloat("bar.uaw-drillBoost.neg", drillBoostPercent, 1), x, y, valid);
			}


			Draw.mixcol(Color.darkGray, 1f);
			Draw.rect(returnItem.fullIcon, dx, dy - 1, s, s);
			Draw.reset();
			Draw.rect(returnItem.fullIcon, dx, dy, s, s);

			if (drawMineItem) {
				Draw.color(returnItem.color);
				Draw.rect(itemRegion, tile.worldx() + offset, tile.worldy() + offset);
				Draw.color();
			}
		} else {
			Tile to = tile.getLinkedTilesAs(this, tempTiles).find(t -> t.drop() != null && (t.drop().hardness > tier || t.drop() == blockedItem));
			Item item = to == null ? null : to.drop();
			if (item != null) {
				drawPlaceText(Core.bundle.get("bar.drilltierreq"), x, y, valid);
			}
		}
	}

	@Override
	public void setBars() {
		super.setBars();
		removeBar("drillspeed");
		addBar("drillspeed", (HardnessBurstDrillBuild e) ->
			new Bar(
				() -> Core.bundle.format("bar.drillspeed", Strings.fixed(e.lastDrillSpeed * tick * e.timeScale(), 2)),
				() -> Pal.accent,
				() -> e.warmup
			));
	}

	public class HardnessBurstDrillBuild extends BurstDrillBuild {

		public float tierDifference(Item item) {
			return h_threshold - item.hardness;
		}

		public float calculateDrillTimeMult(Item item) {
			float spdMult = 1;

			// Calculate the difference between the hardness threshold and the item's hardness.
			float tierDiff = tierDifference(item);

			// Adjust the drill speed multiplier based on the drill type and tierDiff.
			switch (drillType) {
				case "bore" -> {
					if (tierDiff > 0) {
						// Decrease the drill speed multiplier using h_boostMult.
						spdMult = 1 / tierDiff * h_boostMult;
					} else if (tierDiff < 0) {
						// Increase the drill speed multiplier using h_penaltyMult.
						spdMult = Math.abs(tierDiff) * h_penaltyMult;
					}
				}
				case "auger" -> {
					if (tierDiff > 0) {
						// Increase the drill speed multiplier using h_penaltyMult.
						spdMult = Math.abs(tierDiff) * h_penaltyMult;
					} else if (tierDiff < 0) {
						// Decrease the drill speed multiplier using h_boostMult.
						spdMult = 1 / Math.abs(tierDiff) * h_boostMult;
					}
				}
			}

			// Return the modified drill speed multiplier.
			return spdMult;
		}

		@Override
		public void updateTile() {
			if (dominantItem == null) {
				return;
			}

			if (invertTime > 0f) invertTime -= delta() / invertedTime;

			if (timer(timerDump, dumpTime)) {
				dump(items.has(dominantItem) ? dominantItem : null);
			}

			float drillTime = (getDrillTime(dominantItem) * calculateDrillTimeMult(dominantItem));

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
					offload(dominantItem);
				}

				invertTime = 1f;
				progress %= drillTime;

				if (wasVisible) {
					Effect.shake(shake, shake, this);
					drillSound.at(x, y, 1f + Mathf.range(drillSoundPitchRand), drillSoundVolume);
					drillEffect.at(x + Mathf.range(drillEffectRnd), y + Mathf.range(drillEffectRnd), dominantItem.color);
				}
			}
		}
	}
}
