package uaw.world.blocks.production;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import arc.struct.Seq;
import mindustry.entities.Effect;
import mindustry.game.Team;
import mindustry.type.Item;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.blocks.environment.OverlayFloor;
import mindustry.world.meta.Stat;

import static mindustry.Vars.*;

public class EXP_ConversionDrill extends ThumperDrill {

    /**
     * Maps a required floor/overlay block to the item this drill outputs when placed on it.
     * Populate with {@link #addConversion(Block, Item)}.
     */
    public final Seq<ConversionEntry> conversions = new Seq<>();

    /** How many output items are produced per ore tile per drill cycle. */
    public int outputMult = 1;

    public EXP_ConversionDrill(String name) {
        super(name);
        drawMineItem = false;
    }


    /** Registers a tile requirement > output item pair.*/
    public EXP_ConversionDrill addConversion(Block requirement, Item output) {
        conversions.add(new ConversionEntry(requirement, output));
        return this;
    }

    /**
     * Returns the {@link ConversionEntry} whose requirement matches {@code tile}, or
     * {@code null} if none match.
     */
    protected ConversionEntry entryFor(Tile tile) {
        for (var entry : conversions) {
            boolean matches = entry.requirement instanceof OverlayFloor
                    ? tile.overlay() == entry.requirement
                    : tile.floor() == entry.requirement;
            if (matches) return entry;
        }
        return null;
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
                if (canMine(other) && entryFor(other) != null) return true;
            }
            return false;
        }
        return canMine(tile) && entryFor(tile) != null;
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid) {
        drawPotentialLinks(x, y);

        Tile tile = world.tile(x, y);
        if (tile == null) return;

        countOre(tile);

        // Scan all linked tiles for a matching entry, same logic as canPlaceOn.
        ConversionEntry entry = null;
        for (var other : tile.getLinkedTilesAs(this, tempTiles)) {
            entry = entryFor(other);
            if (entry != null) break;
        }

        if (returnItem != null && entry != null) {
            // Use getDrillTime() so that any drillMultiplier entries are respected
            float width = drawPlaceText(
                    Core.bundle.formatFloat(
                            "bar.drillspeed",
                            60f / getDrillTime(returnItem) * returnCount,
                            2),
                    x, y, valid);

            float dx = x * tilesize + offset - width / 2f - 4f;
            float dy = y * tilesize + offset + size * tilesize / 2f + 5;
            float s = iconSmall / 4f;

            Draw.mixcol(Color.darkGray, 1f);
            Draw.rect(entry.output.fullIcon, dx, dy - 1, s, s);
            Draw.reset();
            Draw.rect(entry.output.fullIcon, dx, dy, s, s);
        } else if (!valid) {
            drawPlaceText(Core.bundle.get("bar.nodrill"), x, y, false);
        }
    }

    // -------------------------------------------------------------------------
    // ConversionEntry
    // -------------------------------------------------------------------------

    public static class ConversionEntry {
        public final Block requirement;
        public final Item output;

        public ConversionEntry(Block requirement, Item output) {
            this.requirement = requirement;
            this.output = output;
        }
    }

    public class EXP_ConversionDrillBuild extends ThumperDrillBuild {

        /**
         * Cached entry for the tile this drill is currently sitting on.
         * Refreshed in {@link #onProximityUpdate()}, which the game calls both
         * on placement and whenever the surrounding tiles change.
         */
        protected ConversionEntry activeEntry;

        /**
         * Scans linked tiles to find the first matching {@link ConversionEntry}
         * and caches it in {@link #activeEntry}.
         */
        protected void refreshActiveEntry() {
            activeEntry = null;
            for (var t : tile.getLinkedTilesAs(block, tempTiles)) {
                var entry = entryFor(t);
                if (entry != null) {
                    activeEntry = entry;
                    return;
                }
            }
        }

        /**
         * The game calls this both when the building is placed and whenever
         * neighbouring blocks change, so it covers all cases where the active
         * entry might need updating.
         */
        @Override
        public void onProximityUpdate() {
            super.onProximityUpdate(); // sets dominantItem and dominantItems
            refreshActiveEntry();
        }

        /** The item this build outputs — driven by whichever entry is active. */
        protected Item outputItem() {
            return activeEntry != null ? activeEntry.output : null;
        }

        /**
         * Overridden to account for {@link #outputMult} — otherwise the parent
         * would check capacity against {@code dominantItems} alone, and the drill
         * would keep consuming power/liquid even when there isn't room for the
         * full batch of output items.
         */
        @Override
        public boolean shouldConsume() {
            return items.total() <= itemCapacity - dominantItems * outputMult && enabled;
        }

        @Override
        public void drawSelect() {
            if (dominantItem == null || outputItem() == null) return;
            float dx = x - size * tilesize / 2f;
            float dy = y + size * tilesize / 2f;
            float s = iconSmall / 4f;
            Draw.mixcol(Color.darkGray, 1f);
            Draw.rect(outputItem().fullIcon, dx, dy - 1, s, s);
            Draw.reset();
            Draw.rect(outputItem().fullIcon, dx, dy, s, s);
        }

        @Override
        public void updateTile() {
            if (dominantItem == null || outputItem() == null) return;

            if (invertTime > 0f) invertTime -= delta() / invertedTime;

            if (timer(timerDump, dumpTime / timeScale)) {
                dump(items.has(outputItem()) ? outputItem() : null);
            }

            float drillTime = getDrillTime(dominantItem);
            smoothProgress = Mathf.lerpDelta(smoothProgress, progress / (drillTime - 20f), 0.1f);

            if (items.total() <= itemCapacity - dominantItems * outputMult && dominantItems > 0 && efficiency > 0) {
                warmup = Mathf.approachDelta(warmup, progress / drillTime, 0.01f);
                float speed = Mathf.lerp(1f, liquidBoostIntensity, optionalEfficiency) * efficiency;
                timeDrilled += speedCurve.apply(progress / drillTime) * speed;
                lastDrillSpeed = 1f / drillTime * speed * dominantItems;
                progress += delta() * speed;
            } else {
                warmup = Mathf.approachDelta(warmup, 0f, 0.01f);
                lastDrillSpeed = 0f;
                return;
            }

            if (dominantItems > 0 && progress >= drillTime && items.total() < itemCapacity) {
                for (int i = 0; i < dominantItems * outputMult; i++) offload(outputItem());
                invertTime = 1f;
                progress %= drillTime;
                if (wasVisible) {
                    Effect.shake(shake, shake, this);
                    drillSound.at(x, y, 1f + Mathf.range(drillSoundPitchRand), drillSoundVolume);
                    drillEffect.at(x + Mathf.range(drillEffectRnd), y + Mathf.range(drillEffectRnd), outputItem().color);
                }
            }
        }
    }
}